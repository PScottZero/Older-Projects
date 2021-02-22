package shapes

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point

// class that defines a polygon
class Polygon(points: ArrayList<Point>, color: Color, fill: Color, isFilled: Boolean, stroke: BasicStroke,
              private val pointCount: Int, translateX: Int = 0, translateY: Int = 0,
              rotation: Double = 0.0): AdvancedPaintObject(points, color, fill, isFilled, stroke, translateX, translateY, rotation) {

    // sets second point for polygon
    override fun setPoint(p: Point) { getPoints()[1] = p }

    // returns point count of polygon
    fun getPointCount(): Int { return pointCount }

    // calculates points need to draw polygon of a specific number of points
    private fun calculatePoints(): ArrayList<Point> {
        val points = ArrayList<Point>()
        val p1 = getPoints()[0]
        val p2 = getPoints()[1]

        // defines ellipse that points will rotate around
        val a = Math.abs(p2.x - p1.x) / 2
        val b = Math.abs(p2.y - p1.y) / 2
        val centerX = (p1.x + p2.x) / 2
        val centerY = (p1.y + p2.y) / 2

        // rotates points until they form basis of polygon
        for (i in 0 until pointCount) {
            val theta: Double = ((2 * Math.PI * i) / pointCount) + (3 * Math.PI / 2)
            val newX = a * Math.cos(theta) + centerX
            val newY = b * Math.sin(theta) + centerY
            points.add(Point(newX.toInt(), newY.toInt()))
        }

        return points
    }

    // draws polygon
    override fun draw(g: Graphics2D) {
        val calcPoints = calculatePoints()
        val pointsX = IntArray(calcPoints.size)
        val pointsY = IntArray(calcPoints.size)
        val p1 = getPoints()[0]
        val p2 = getPoints()[1]
        val x = Math.min(p1.x, p2.x)
        val y = Math.min(p1.y, p2.y)
        val width = Math.abs(p2.x - p1.x)
        val height = Math.abs(p2.y - p1.y)
        val centerX = (x + width/2).toDouble()
        val centerY = (y + height/2).toDouble()

        for (i in 0 until pointCount) {
            pointsX[i] = calcPoints[i].x
            pointsY[i] = calcPoints[i].y
        }

        // sets stroke, translation, and rotation
        g.stroke = getStroke()
        g.translate(getTranslationX(), getTranslationY())
        g.rotate(Math.toRadians(getRotation()), centerX, centerY)

        // fills polygon
        if (isFilled()) {
            g.color = getFill()
            g.fillPolygon(pointsX, pointsY, pointCount)
        }

        // sets color and draws polygon
        g.color = getColor()
        g.drawPolygon(pointsX, pointsY, pointCount)

        // resets rotation and translation
        g.rotate(-1 * Math.toRadians(getRotation()), centerX, centerY)
        g.translate(-1 * getTranslationX(), -1 * getTranslationY())
    }
}