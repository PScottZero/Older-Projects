package shapes

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Point

// class for 2d shapes
abstract class AdvancedPaintObject(points: ArrayList<Point>, color: Color, private var fill: Color,
                          private var isFilled: Boolean, stroke: BasicStroke, translateX: Int = 0, translateY: Int = 0,
                          rotation: Double = 0.0): PaintObject(points, color, stroke, translateX, translateY, rotation) {

    // returns whether shape is to be filled
    fun isFilled(): Boolean { return isFilled }

    // returns fill color
    fun getFill(): Color { return fill }
}