import data.Colors
import data.Constants
import java.awt.*
import javax.swing.JButton

class Tool(toolName: String, private var image: Image): JButton() {

    init {
        minimumSize = Dimension(Constants.ICON_SIZE, Constants.ICON_SIZE)
        preferredSize = Dimension(Constants.ICON_SIZE, Constants.ICON_SIZE)
        maximumSize = Dimension(Constants.ICON_SIZE, Constants.ICON_SIZE)
        border = null
        background = Colors.DARK_GRAY
        actionCommand = toolName
        alignmentX = Component.CENTER_ALIGNMENT
        image = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH)
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        val g2d = g as Graphics2D
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.drawImage(image, 0, 0, null)
    }
}