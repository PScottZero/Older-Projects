/**
 * @author Paul Scott
 * @version 25 January 2019
 *
 * convex hull graham-scan algorithm
 */

import java.awt.*
import java.util.*
import javax.swing.JPanel

class ConvexHull: JPanel() {

    // instance data
    private val points = ArrayList<PointAngle>() // points in set
    private val stack = Stack<PointAngle>() // stack
    private var finished = false
    private val delay = 500.toLong()
    private val dim = Dimension(500, 500)

    // initialization
    init {
        minimumSize = dim
        preferredSize = dim
        maximumSize = dim
        background = Color(66, 215, 244)
    }

    // starts graham scan algorithm
    fun convexHull() {
        generatePoints() // creates random set of points
        val minIndex = getMinY() // gets point with minimum y value
        getAngles(minIndex) // calculates angles of each point
        Collections.sort(points, Comparator.comparing(PointAngle::getAngle)) // sorts points by angle ccw
        update()

        stack.push(points[0]) // push first point onto stack
        stack.push(points[1]) // push second point onto stack
        update()
        stack.push(points[2]) // push third point onto stack
        update()

        // check whether remaining points are in convex hull
        for (i in 3 until points.size) {
            while (stack.isNotEmpty()) {
                val pA = stack.pop() // top element of stack
                val pB = stack.pop() // 2nd element of stack
                stack.push(pB)
                stack.push(pA)
                val pC = points[i] // 3rd point

                // calculates cross product
                // pops top element if 'right turn'
                if (crossProduct(pA, pB, pC) < 0) {
                    stack.pop().setStatus(false)
                    update()
                }

                // pushes 3rd point onto stack
                // if 'left turn'
                else {
                    stack.push(pC)
                    update()
                    break
                }
            }
        }
        finished = true
        repaint()
    }

    /**
     * updates graphic and pauses algorithm
     */
    private fun update() {
        repaint()
        Thread.sleep(delay)
    }

    /**
     * returns point with lowest y value
     * @return minIndex
     */
    private fun getMinY(): Int {
        var minY = 301
        var minIndex = -1
        for (i in 0 until points.size) {
            if (points[i].getY() < minY) {
                minY = points[i].getY()
                minIndex = i
            }
        }
        return minIndex
    }

    /**
     * calculates cross product using three points
     * @param p1 - point 1
     * @param p2 - point 2
     * @param p3 - point 3
     * @return cross product of vectors made from the three points
     */
    private fun crossProduct(p1: PointAngle, p2: PointAngle, p3: PointAngle): Int {
        val vector1 = Point(p1.getX() - p2.getX(), p1.getY() - p2.getY())
        val vector2 = Point(p3.getX() - p2.getX(), p3.getY() - p2.getY())
        return (vector1.x * vector2.y) - (vector1.y * vector2.x)
    }

    /**
     * calculates angles of all points based
     * on the point with the lowest y value
     * @param minIndex - point with lowest y value
     */
    private fun getAngles(minIndex: Int) {
        val scaleX = points[minIndex].getX()
        val scaleY = points[minIndex].getY()
        for (i in 0 until points.size) {
            var x = points[i].getX()
            var y = points[i].getY()
            x -= scaleX
            y -= scaleY
            val xDouble = x.toDouble()
            val yDouble = y.toDouble()
            var angle = Math.toDegrees(Math.atan(yDouble/xDouble))
            if (!angle.isNaN()) {
                if (angle < 0) angle += 180
                points[i].setAngle(angle)
            }
        }
    }

    /**
     * creates random set of points
     */
    private fun generatePoints() {
        val rand = Random()
        val pointCount = rand.nextInt(20) + 10
        for (i in 0..pointCount) {
            val pointX = rand.nextInt(400)
            val pointY = rand.nextInt(400)
            points.add(PointAngle(pointX, pointY, 0.0, true))
        }
    }

    /**
     * draws visual of graham-scan algorithm
     * @param g - graphics variable
     */
    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2d = g as Graphics2D
        g2d.stroke = BasicStroke(4.toFloat(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        // draws points in set
        var j = 1
        for (point in points) {
            if (!point.getStatus()) g2d.color = Color.GRAY
            else g2d.color = Color.WHITE
            g2d.fillOval(point.getX() + 50 - 4, 450 - point.getY() - 4, 8, 8)
            g2d.drawString("$j", point.getX() + 50 + 6, 450 - point.getY() + 4)
            j++
        }

        // draws lines to show progress of algorithm
        g2d.color = Color.RED
        for (i in 1 until stack.size) {
            if ((i == stack.size - 1) && !finished)
                g2d.color = Color.BLUE
            g2d.drawLine(stack[i-1].getX() + 50, 450 - stack[i-1].getY(),
                stack[i].getX() + 50, 450 - stack[i].getY())
        }

        // draws line connecting first and last point of convex hall
        if (finished) {
            g2d.drawLine(stack[0].getX() + 50, 450 - stack[0].getY(),
                stack[stack.size-1].getX() + 50, 450 - stack[stack.size-1].getY())
        }
    }
}