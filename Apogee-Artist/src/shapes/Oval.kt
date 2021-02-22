package shapes

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point

// class that defines an oval
class Oval(points: ArrayList<Point>, color: Color, fill: Color, isFilled: Boolean,
           stroke: BasicStroke, translateX: Int = 0, translateY: Int = 0,
           rotation: Double = 0.0): AdvancedPaintObject(points, color, fill, isFilled, stroke, translateX, translateY, rotation) {

    // sets second point of oval
    override fun setPoint(p: Point) { getPoints()[1] = p }

    // draws oval
    override fun draw(g: Graphics2D) {
        val p1 = getPoints()[0]
        val p2 = getPoints()[1]
        val x = Math.min(p1.x, p2.x)
        val y = Math.min(p1.y, p2.y)
        val width = Math.abs(p2.x - p1.x)
        val height = Math.abs(p2.y - p1.y)
        val centerX = (x + width/2).toDouble()
        val centerY = (y + height/2).toDouble()

        // sets stroke, translation, and rotation
        g.stroke = getStroke()
        g.translate(getTranslationX(), getTranslationY())
        g.rotate(Math.toRadians(getRotation()), centerX, centerY)

        // fills oval
        if (isFilled()) {
            g.color = getFill()
            g.fillOval(x, y, width, height)
        }

        // sets color and draws oval
        g.color = getColor()
        g.drawOval(x, y, width, height)

        // resets rotation and translation
        g.rotate(-1 * Math.toRadians(getRotation()), centerX, centerY)
        g.translate(-1 * getTranslationX(), -1 * getTranslationY())
    }
}