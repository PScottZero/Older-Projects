import java.awt.*
import javax.imageio.ImageIO
import javax.swing.*

class Recaman: JFrame() {

    companion object {

        private val toolbar = Toolbar()
        private val visualizer = Visualizer()
        private val display = SequenceDisplay()

        @JvmStatic fun main(args: Array<String>) { Recaman() }

        fun updateVisualizer(max: Int) {
            visualizer.setMax(max)
            visualizer.repaint()
            display.setText(Visualizer.recamanString())
        }
    }

    init {
        title = "Recamán Sequence Visualizer"
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        preferredSize = Dimension(800, 900)
        iconImage = ImageIO.read(Recaman::class.java.getResource("img/icon.png")).getScaledInstance(32, 32, Image.SCALE_SMOOTH)

        val container = JPanel()
        container.layout = BoxLayout(container, BoxLayout.Y_AXIS)
        container.background = Color.LIGHT_GRAY
        container.add(Box.createVerticalGlue())
        container.add(visualizer)
        container.add(Box.createVerticalGlue())

        add(toolbar, BorderLayout.NORTH)
        add(container, BorderLayout.CENTER)
        add(display, BorderLayout.SOUTH)

        pack()
        isVisible = true
    }
}