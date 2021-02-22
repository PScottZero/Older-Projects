package shapes

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point

// class that defines a paint stroke
class PaintStroke(points: ArrayList<Point>, color: Color, stroke: BasicStroke, translateX: Int = 0, translateY: Int = 0,
                  rotation: Double = 0.0): PaintObject(points, color, stroke, translateX, translateY, rotation) {

    // adds point to stroke
    override fun setPoint(p: Point) {
        getPoints().add(p)
    }

    // draws stroke
    override fun draw(g: Graphics2D) {
        val ovalSize = getStroke().lineWidth.toInt()
        val shift = ovalSize / 2

        // sets color, stroke, translation, and rotation
        g.color = getColor()
        g.stroke = getStroke()
        g.translate(getTranslationX(), getTranslationY())
        g.rotate(Math.toRadians(getRotation()), getPoints()[0].x.toDouble(), getPoints()[0].y.toDouble())

        // draws starting point
        g.fillOval(getPoints()[0].x - shift, getPoints()[0].y - shift, ovalSize, ovalSize)

        // connects all points to forms stroke
        for (i in 1 until getPoints().size) {
            val p1 = getPoints()[i-1]
            val p2 = getPoints()[i]
            g.drawLine(p1.x, p1.y, p2.x, p2.y)
        }

        // resets rotation and translation
        g.rotate(-1 * Math.toRadians(getRotation()), getPoints()[0].x.toDouble(), getPoints()[0].y.toDouble())
        g.translate(-1 * getTranslationX(), -1 * getTranslationY())
    }
}