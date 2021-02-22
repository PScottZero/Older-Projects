package fileio

import shapes.*
import java.io.BufferedWriter
import java.io.FileWriter

class Save (dir: String, objects: ArrayList<PaintObject>) {

    init {
        val write = BufferedWriter(FileWriter(dir))

        for (obj in objects) {

            when (obj) {

                // saves rectangle data
                is Rectangle -> {

                    // writes shape type
                    write.append("-----RTGL-----\n")

                    // writes shape properties
                    writeShape(obj, write)

                    // writes radius size
                    write.append(obj.getRadius().toString() + "\n")
                }

                // saves oval data
                is Oval -> {

                    // writes shape type
                    write.append("-----OVAL-----\n")

                    // writes shape properties
                    writeShape(obj, write)
                }

                // saves polygon data
                is Polygon -> {

                    // writes shape type
                    write.append("-----POLY-----\n")

                    // writes shape properties
                    writeShape(obj, write)

                    // writes point count
                    write.append(obj.getPointCount().toString() + "\n")
                }

                // saves line data
                is Line -> {

                    // writes shape type
                    write.append("-----LINE-----\n")

                    // writes line properties
                    writeLine(obj, write)
                }

                // saves stroke data
                is PaintStroke -> {

                    // writes shape type
                    write.append("-----STRK-----\n")

                    // writes line properties
                    writeLine(obj, write)
                }

                // saves text data
                is Text -> {

                    // writes shape type
                    write.append("-----TEXT-----\n")

                    // writes points
                    writePoints(obj, write)

                    // writes color
                    val main = obj.getColor()

                    write.append("" + main.red +
                            " " + main.green +
                            " " + main.blue +
                            " " + main.alpha + "\n")

                    // writes translation
                    write.append("" + obj.getTranslationX() + " " + obj.getTranslationY() + "\n")

                    // writes rotation
                    write.append(obj.getRotation().toString() + "\n")

                    // writes text
                    write.append(obj.getText() + "\n")

                    // writes font name
                    write.append(obj.getFontName() + "\n")

                    // writes font size
                    write.append(obj.getSize().toString() + "\n")
                }
            }
        }

        write.close()
    }

    // writes object points to file
    private fun writePoints(obj: PaintObject, write: BufferedWriter) {
        for (point in obj.getPoints()) {
            write.append("" + point.x + " " + point.y + " ")
        }
        write.append("\n")
    }

    // writes 2d shape data
    private fun writeShape(obj: PaintObject, write: BufferedWriter) {

        obj as AdvancedPaintObject

        // writes points
        writePoints(obj, write)

        // writes main color and fill color
        val main = obj.getColor()
        val fill = obj.getFill()

        write.append("" + main.red +
                " " + main.green +
                " " + main.blue +
                " " + main.alpha + "\n")

        write.append("" + fill.red +
                " " + fill.green +
                " " + fill.blue +
                " " + fill.alpha + "\n")

        // writes fill status
        write.append(obj.isFilled().toString() + "\n")

        writeStrokeTranslateRotate(obj, write)
    }

    // writes line and paint stroke data
    private fun writeLine(obj: PaintObject, write: BufferedWriter) {

        // writes points
        writePoints(obj, write)

        // writes main color and fill color
        val main = obj.getColor()

        write.append("" + main.red +
                " " + main.green +
                " " + main.blue +
                " " + main.alpha + "\n")

        writeStrokeTranslateRotate(obj, write)
    }

    // writes stroke, translation, and rotation data
    private fun writeStrokeTranslateRotate(obj: PaintObject, write: BufferedWriter) {

        // writes stroke size
        write.append(obj.getStroke().lineWidth.toString() + "\n")

        // writes translation
        write.append("" + obj.getTranslationX() + " " + obj.getTranslationY() + "\n")

        // writes rotation
        write.append(obj.getRotation().toString() + "\n")
    }
}