import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Image
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.imageio.ImageIO
import javax.swing.*

// class that adds controls to lower part of interface
class Controls: JPanel() {

    // static methods and variables
    companion object {

        // generation count label
        private val generationLabel = JLabel("Generation: 0")
        private var generationCount = 0

        // background color
        private val color = Color(152, 175, 209)

        // updates generation count
        fun updateGenerationCount(increment: Boolean) {
            if (increment) generationCount++
            else generationCount = 0
            generationLabel.text = "Generation: $generationCount"
        }
    }

    // initializer
    init {

        // local variables
        val eventListener = EventListener()
        val font = Font("Arial", Font.PLAIN, 20)

        // configure panel
        layout = BoxLayout(this, BoxLayout.X_AXIS)
        preferredSize = Dimension(0, 40)
        background = color

        add(Box.createHorizontalGlue())

        // play pause button
        val playPause = JButton()
        playPause.icon = ImageIcon(ImageIO.read(Controls::class.java.getResource("img/play_pause.png")).getScaledInstance(
                40, 20, Image.SCALE_SMOOTH))
        playPause.size = Dimension(40, 20)
        playPause.background = color
        playPause.border = null
        playPause.actionCommand = "Play"
        playPause.addActionListener(eventListener)
        add(playPause)

        add(Box.createHorizontalStrut(40))

        // next generation button
        val next = JButton("Next Generation")
        next.font = font
        next.size = Dimension(80, 20)
        next.background = color
        next.foreground = Color.BLACK
        next.border = null
        next.addActionListener(eventListener)
        add(next)

        add(Box.createHorizontalStrut(40))

        // generation count label
        generationLabel.font = font
        generationLabel.size = Dimension(0, 20)
        generationLabel.foreground = Color.DARK_GRAY
        add(generationLabel)

        add(Box.createHorizontalGlue())
    }

    // listener for control buttons
    inner class EventListener: ActionListener {

        override fun actionPerformed(e: ActionEvent?) {

            when(e!!.actionCommand) {
                "Next Generation" -> PlayField.next()
                "Play" -> PlayField.update()
            }
            Conway.update()
        }
    }
}