package fileio

import shapes.*
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Point
import java.io.File

// opens file containing object data
class Open(dir: String) {

    // constants
    companion object {
        private const val MODE_STROKE = 0
        private const val MODE_LINE = 1
        private const val MODE_RECTANGLE = 2
        private const val MODE_POLYGON = 3
        private const val MODE_OVAL = 4
        private const val MODE_TEXT = 5

        private val objects = ArrayList<PaintObject>()
    }

    // opens file
    init {
        objects.clear()

        val lines: List<String> = File(dir).readLines()

        var mode = 0

        var i = 0
        while(i < lines.size) {

            when (lines[i]) {
                "-----STRK-----" -> mode = MODE_STROKE
                "-----LINE-----" -> mode = MODE_LINE
                "-----RTGL-----" -> mode = MODE_RECTANGLE
                "-----POLY-----" -> mode = MODE_POLYGON
                "-----OVAL-----" -> mode = MODE_OVAL
                "-----TEXT-----" -> mode = MODE_TEXT
                else -> {

                    // gets shape points
                    val pointLine = lines[i].split(" ")
                    val points = ArrayList<Point>()
                    for (j in 0 until pointLine.size - 1 step 2) {
                        points.add(Point(pointLine[j].toInt(), pointLine[j+1].toInt()))
                    }; i++

                    // gets shape color
                    val colorLine = lines[i].split(" ")
                    val red = colorLine[0].toInt()
                    val green = colorLine[1].toInt()
                    val blue = colorLine[2].toInt()
                    val alpha = colorLine[3].toInt()
                    val color = Color(red, green, blue, alpha); i++

                    when (mode) {

                        // reads stroke data
                        MODE_STROKE -> {

                            // gets shape stroke
                            val stroke = BasicStroke(lines[i].toFloat(),
                                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND); i++

                            // gets translation and rotation
                            val translationLine = lines[i].split(" "); i++
                            val tX = translationLine[0].toInt()
                            val tY = translationLine[1].toInt()
                            val rotation = lines[i].toDouble()

                            // add line
                            objects.add(PaintStroke(points, color, stroke, tX, tY, rotation))
                        }

                        // reads line data
                        MODE_LINE -> {

                            // gets shape stroke
                            val stroke = BasicStroke(lines[i].toFloat(),
                                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND); i++

                            // gets translation and rotation
                            val translationLine = lines[i].split(" "); i++
                            val tX = translationLine[0].toInt()
                            val tY = translationLine[1].toInt()
                            val rotation = lines[i].toDouble()

                            // add line
                            objects.add(Line(points, color, stroke, tX, tY, rotation))
                        }

                        // reads rectangle data
                        MODE_RECTANGLE -> {

                            // gets shape fill
                            val fillLine = lines[i].split(" ")
                            val redF = fillLine[0].toInt()
                            val greenF = fillLine[1].toInt()
                            val blueF = fillLine[2].toInt()
                            val alphaF = fillLine[3].toInt()
                            val fill = Color(redF, greenF, blueF, alphaF); i++

                            // gets shape fill
                            val isFilled = lines[i].toBoolean(); i++

                            // gets shape stroke
                            val stroke = BasicStroke(lines[i].toFloat(),
                                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND); i++

                            // gets translation and rotation
                            val translationLine = lines[i].split(" "); i++
                            val tX = translationLine[0].toInt()
                            val tY = translationLine[1].toInt()
                            val rotation = lines[i].toDouble(); i++

                            // gets rectangle radius
                            val radius = lines[i].toInt()

                            // add rectangle
                            objects.add(Rectangle(points, color, fill, isFilled, stroke, radius, tX, tY, rotation))
                        }

                        // reads polygon data
                        MODE_POLYGON -> {
                            // gets shape fill
                            val fillLine = lines[i].split(" ")
                            val redF = fillLine[0].toInt()
                            val greenF = fillLine[1].toInt()
                            val blueF = fillLine[2].toInt()
                            val alphaF = fillLine[3].toInt()
                            val fill = Color(redF, greenF, blueF, alphaF); i++

                            // gets shape fill
                            val isFilled = lines[i].toBoolean(); i++

                            // gets shape stroke
                            val stroke = BasicStroke(lines[i].toFloat(),
                                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND); i++

                            // gets translation and rotation
                            val translationLine = lines[i].split(" "); i++
                            val tX = translationLine[0].toInt()
                            val tY = translationLine[1].toInt()
                            val rotation = lines[i].toDouble(); i++

                            // gets point count
                            val pointCount = lines[i].toInt()

                            // add polygon
                            objects.add(Polygon(points, color, fill, isFilled, stroke, pointCount, tX, tY, rotation))
                        }

                        // reads oval data
                        MODE_OVAL -> {

                            // gets shape fill
                            val fillLine = lines[i].split(" ")
                            val redF = fillLine[0].toInt()
                            val greenF = fillLine[1].toInt()
                            val blueF = fillLine[2].toInt()
                            val alphaF = fillLine[3].toInt()
                            val fill = Color(redF, greenF, blueF, alphaF); i++

                            // gets shape fill
                            val isFilled = lines[i].toBoolean(); i++

                            // gets shape stroke
                            val stroke = BasicStroke(lines[i].toFloat(),
                                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND); i++

                            // gets translation and rotation
                            val translationLine = lines[i].split(" "); i++
                            val tX = translationLine[0].toInt()
                            val tY = translationLine[1].toInt()
                            val rotation = lines[i].toDouble()

                            // add rectangle
                            objects.add(Oval(points, color, fill, isFilled, stroke, tX, tY, rotation))
                        }

                        // reads text data
                        MODE_TEXT -> {
                            // gets translation and rotation
                            val translationLine = lines[i].split(" "); i++
                            val tX = translationLine[0].toInt()
                            val tY = translationLine[1].toInt()
                            val rotation = lines[i].toDouble(); i++

                            // gets text
                            val textString = lines[i]; i++
                            val fontName = lines[i]; i++
                            val fontSize = lines[i].toInt()

                            // add text
                            objects.add(Text(points, color, textString, fontName, fontSize, tX, tY, rotation))
                        }
                    }
                }
            }
            i++
        }
    }

    fun getObjects(): ArrayList<PaintObject> { return objects }
}