package shapes

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point

// class representing a line
class Line(points: ArrayList<Point>, color: Color, stroke: BasicStroke, translateX: Int = 0, translateY: Int = 0,
           rotation: Double = 0.0): PaintObject(points, color, stroke, translateX, translateY, rotation) {

    // sets second point of line
    override fun setPoint(p: Point) { getPoints()[1] = p }

    // draws line
    override fun draw(g: Graphics2D) {
        val p1 = getPoints()[0]
        val p2 = getPoints()[1]
        val x = Math.min(p1.x, p2.x)
        val y = Math.min(p1.y, p2.y)
        val width = Math.abs(p2.x - p1.x)
        val height = Math.abs(p2.y - p1.y)
        val centerX = (x + width/2).toDouble()
        val centerY = (y + height/2).toDouble()

        // sets color, stroke, translation, and rotation
        g.color = getColor()
        g.stroke = getStroke()
        g.translate(getTranslationX(), getTranslationY())
        g.rotate(Math.toRadians(getRotation()), centerX, centerY)

        // draws line
        g.drawLine(p1.x, p1.y, p2.x, p2.y)

        // resets rotation and translation
        g.rotate(-1 * Math.toRadians(getRotation()), centerX, centerY)
        g.translate(-1 * getTranslationX(), -1 * getTranslationY())
    }
}