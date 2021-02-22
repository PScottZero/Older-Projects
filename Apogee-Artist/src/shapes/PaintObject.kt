package shapes

import java.awt.*

// class that defines all objects painted on canvas
abstract class PaintObject(private var points: ArrayList<Point>, private var color: Color,
                           private val stroke: BasicStroke = BasicStroke(), private var translateX: Int = 0,
                           private var translateY: Int = 0, private var rotation: Double = 0.0) {

    // returns points that define shape or stroke
    fun getPoints(): ArrayList<Point> { return points }

    // returns primary color
    fun getColor(): Color { return color }

    // returns stroke width
    fun getStroke(): BasicStroke { return stroke }

    // returns rotation
    fun getRotation(): Double { return rotation }

    // sets rotation
    fun setRotation(rotate: Double) { rotation = rotate }

    // returns x translation
    fun getTranslationX(): Int { return translateX }

    // sets x translation
    fun setTranslationX(tX: Int) { translateX = tX }

    // returns y translation
    fun getTranslationY(): Int { return translateY }

    // sets y translation
    fun setTranslationY(tY: Int) { translateY = tY }

    // can either set second point or add points to stroke
    abstract fun setPoint(p: Point)

    // draws shape or stroke
    abstract fun draw(g: Graphics2D)
}