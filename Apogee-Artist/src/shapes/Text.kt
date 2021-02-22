package shapes

import java.awt.*

// class that defines text object
class Text(points: ArrayList<Point>, color: Color, private var text: String, private var fontName: String,
           private var size: Int, translateX: Int = 0, translateY: Int = 0,
           rotation: Double = 0.0): PaintObject(points, color, BasicStroke(), translateX, translateY, rotation) {

    // sets location of text on canvas
    override fun setPoint(p: Point) { getPoints()[0] = p }

    // returns text from text object
    fun getText(): String { return text }

    // returns font name
    fun getFontName(): String { return fontName }

    // returns font size
    fun getSize(): Int { return size }

    // draws text
    override fun draw(g: Graphics2D) {
        val p1 = getPoints()[0]

        // sets color, stroke, font, translation, and rotation
        g.color = getColor()
        g.stroke = getStroke()
        g.font = Font(fontName, Font.PLAIN, size)
        g.translate(getTranslationX(), getTranslationY())
        g.rotate(Math.toRadians(getRotation()), p1.x.toDouble(), p1.y.toDouble())

        // draws text
        g.drawString(text, p1.x, p1.y)

        // resets rotation and translation
        g.rotate(-1 * Math.toRadians(getRotation()), p1.x.toDouble(), p1.y.toDouble())
        g.translate(-1 * getTranslationX(), -1 * getTranslationY())
    }
}