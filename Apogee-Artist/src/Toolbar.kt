import data.Colors
import data.Constants
import data.Strings
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.swing.*

// painting and shape toolbar
class Toolbar: JPanel() {

    companion object {

        // instance data
        private var buttonListener = ButtonListener()
        private var color_one = colorTool(Strings.MODE_COLOR_ONE)
        private var color_two = colorTool(Strings.MODE_COLOR_TWO)

        // creates color tool
        private fun colorTool(name: String): JButton {
            val colorTool = JButton()
            colorTool.actionCommand = name
            colorTool.border = null
            colorTool.background = Color.BLACK
            colorTool.preferredSize = Dimension(Constants.ICON_SIZE, Constants.ICON_SIZE)
            colorTool.minimumSize = Dimension(Constants.ICON_SIZE, Constants.ICON_SIZE)
            colorTool.maximumSize = Dimension(Constants.ICON_SIZE, Constants.ICON_SIZE)
            colorTool.alignmentX = Box.CENTER_ALIGNMENT
            colorTool.addActionListener(buttonListener)
            return colorTool
        }
    }

    // initializer
    init {

        // panel customization
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        background = Colors.DARK_GRAY
        preferredSize = Dimension(Constants.TOOL_WIDTH, 0)

        add(Box.createVerticalStrut(Constants.TOOL_SEPARATION))

        // add img.tools to panel
        addTool(Strings.MODE_BRUSH)
        addTool(Strings.MODE_LINE)
        addTool(Strings.MODE_RECTANGLE)
        addTool(Strings.MODE_POLYGON)
        addTool(Strings.MODE_OVAL)
        addTool(Strings.MODE_TEXT)
        addTool(Strings.MODE_MOVE)
        addTool(Strings.MODE_ROTATE)

        // add color img.tools to panel
        add(color_one)
        add(Box.createVerticalStrut(Constants.TOOL_SEPARATION))
        add(color_two)
    }

    // adds tool to toolbar
    private fun addTool(name: String) {
        val image = ImageIO.read(Toolbar::class.java.getResource("img/tools/$name.png"))
        val tool = Tool(name, image)
        tool.addActionListener(buttonListener)
        tool.toolTipText = name
        add(tool)
        add(Box.createVerticalStrut(Constants.TOOL_SEPARATION))
    }

    // listener for tool buttons
    class ButtonListener: ActionListener {

        override fun actionPerformed(e: ActionEvent?) {

            // checks to see which button was pressed
            when (e!!.actionCommand) {

                // primary color button
                Strings.MODE_COLOR_ONE -> {
                    val color = JColorChooser.showDialog(null, "Color Chooser", Canvas.getColor())
                    Canvas.setColor(color)
                    color_one.background = color
                }

                // fill color buttons
                Strings.MODE_COLOR_TWO -> {
                    val color = JColorChooser.showDialog(null, "Color Chooser", Canvas.getFill())
                    Canvas.setFill(color)
                    color_two.background = color
                }

                // any other button
                else -> {
                    Canvas.setMode(e.actionCommand)
                    Apogee.setToolMode(e.actionCommand)
                }
            }
        }
    }
}