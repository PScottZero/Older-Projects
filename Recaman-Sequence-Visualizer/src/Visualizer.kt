import java.awt.*
import javax.swing.Box
import javax.swing.JPanel

class Visualizer: JPanel() {

    companion object {
        const val WIDTH = 600
        const val HEIGHT = 600

        private var recamanArray = IntArray(1)

        fun recamanString(): String {
            var recamanString = ""
            for (i in 1 until recamanArray.size) {
                recamanString += recamanArray[i]
                if (i < recamanArray.size - 1) { recamanString += ", " }
            }
            return recamanString
        }
    }

    init {
        preferredSize = Dimension(WIDTH, HEIGHT)
        maximumSize = Dimension(WIDTH, HEIGHT)
        minimumSize = Dimension(WIDTH, HEIGHT)
        background = Color.WHITE
        alignmentX = Box.CENTER_ALIGNMENT
    }

    override fun paintComponent(g: Graphics) {

        super.paintComponent(g)
        val g2d = g as Graphics2D

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

        val max = recamanArray.max() as Int
        val modifier = if (max != 0) (WIDTH.toDouble() / max.toDouble()) else 0.0

        for (i in 1 until recamanArray.size) {

            val width = (recamanArray[i] - recamanArray[i - 1]) * modifier
            val x = if (width > 0) recamanArray[i] * modifier - width else recamanArray[i] * modifier
            val y = (HEIGHT / 2) - (Math.abs(width) / 2)
            val angle = if (i % 2 == 0) -180 else 180

            g2d.drawArc(x.toInt(), y.toInt(), Math.abs(width).toInt(), Math.abs(width).toInt(), 0, angle)
        }

        g2d.stroke = BasicStroke(1f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0f, floatArrayOf(10f), 0f)
        g2d.drawLine(0, HEIGHT / 2, WIDTH, HEIGHT / 2)
    }

    fun setMax(max: Int) { recamanArray = recaman(max) }

    private fun recaman(max: Int): IntArray {

        val recamanArray = IntArray(max + 1)
        var jump = 1

        for (i in 1 until recamanArray.size) {

            val backward = recamanArray[i - 1] - jump

            if (backward <= 0 || recamanArray.contains(backward)) {
                recamanArray[i] = recamanArray[i - 1] + jump
            } else {
                recamanArray[i] = backward
            }

            jump++
        }

        return recamanArray
    }
}