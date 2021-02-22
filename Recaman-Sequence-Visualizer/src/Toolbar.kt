import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import javax.swing.*
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener

class Toolbar: JPanel() {

    private val slider = JSlider()
    private val label = JLabel("0")

    init {
        layout = BoxLayout(this, BoxLayout.X_AXIS)
        preferredSize = Dimension(0, 60)
        background = Color.GRAY

        add(Box.createHorizontalGlue())

        slider.maximum = 200
        slider.minimum = 0
        slider.value = 0
        slider.majorTickSpacing = 25
        slider.paintTicks = true
        slider.background = Color.GRAY
        slider.foreground = Color.WHITE
        slider.preferredSize = Dimension(400, 60)
        slider.maximumSize = Dimension(400, 60)
        slider.minimumSize = Dimension(400, 60)
        slider.addChangeListener(SliderListener())
        add(slider)

        add(Box.createHorizontalStrut(20))

        label.font = Font("Arial", Font.PLAIN, 24)
        label.foreground = Color.WHITE
        add(label)

        add(Box.createHorizontalGlue())
    }

    inner class SliderListener: ChangeListener {
        override fun stateChanged(e: ChangeEvent?) {
            Recaman.updateVisualizer(slider.value)
            label.text = slider.value.toString()
        }
    }
}