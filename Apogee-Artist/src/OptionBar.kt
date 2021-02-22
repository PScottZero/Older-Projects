import data.Colors
import data.Constants
import data.Strings
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.ItemEvent
import java.awt.event.ItemListener
import javax.swing.*
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener

// toolbar that contains shape specific settings
class OptionBar: JPanel() {

    // instance data
    private val isFilled = JCheckBox(Strings.ST_FILL)
    private val fontChooser = JComboBox(getFonts().toTypedArray())
    private val fontLabel = JLabel(Strings.ST_FONT)
    private val fontSize = JTextField(Canvas.getFontSize().toString())
    private val fontSizeLabel = JLabel(Strings.ST_FONT_SIZE)
    private val pointOptions = intArrayOf(3, 4, 5, 6, 7, 8, 9, 10)
    private val points = JComboBox(pointOptions.toTypedArray())
    private val pointsLabel = JLabel(Strings.ST_POINTS)
    private val radius = JTextField(Canvas.getRadius().toString())
    private val radiusLabel = JLabel(Strings.ST_RADIUS)
    private val strokeSize = JSlider()
    private val strokeLabel = JLabel(Strings.ST_STROKE + Canvas.getStrokeSize())
    private val textLabel = JLabel(Strings.ST_TEXT)
    private val toolName = JLabel(Strings.MODE_BRUSH)

    companion object {
        private val text = JTextField(Canvas.getText())
        fun getText(): String { return text.text }
    }

    /**
     * class initializer
     */
    init {

        val font = Font("Arial", Font.PLAIN, 16)
        val renderer = CustomRenderer()

        // panel customization
        layout = BoxLayout(this, BoxLayout.X_AXIS)
        preferredSize = Dimension(0, Constants.ST_HEIGHT)
        background = Colors.GRAY

        // shape name label
        toolName.foreground = Colors.LIGHTER_GRAY
        toolName.font = Font("Arial", Font.ITALIC, 16)

        // stroke size slider
        strokeSize.isOpaque = false
        strokeSize.paintTicks = true
        strokeSize.foreground = Colors.LIGHTER_GRAY
        strokeSize.value = Canvas.getStrokeSize()
        strokeSize.maximum = Constants.ST_MAX_STROKE
        strokeSize.minimum = Constants.ST_MIN_STROKE
        strokeSize.majorTickSpacing = Constants.ST_MAJOR_SPACING
        strokeSize.minorTickSpacing = Constants.ST_MINOR_SPACING
        strokeSize.preferredSize = Dimension(Constants.ST_SLIDER_WIDTH, Constants.ST_SLIDER_HEIGHT)
        strokeSize.minimumSize = Dimension(Constants.ST_SLIDER_WIDTH, Constants.ST_SLIDER_HEIGHT)
        strokeSize.maximumSize = Dimension(Constants.ST_SLIDER_WIDTH, Constants.ST_SLIDER_HEIGHT)
        strokeSize.addChangeListener(SliderListener())

        // stroke size label
        strokeLabel.foreground = Colors.LIGHTER_GRAY
        strokeLabel.font = font

        // fill status checkbox
        isFilled.isOpaque = false
        isFilled.foreground = Colors.LIGHTER_GRAY
        isFilled.font = font
        isFilled.addItemListener(CheckListener())

        // rectangle corner radius label
        radiusLabel.foreground = Colors.LIGHTER_GRAY
        radiusLabel.font = font

        // rectangle corner radius input field
        radius.preferredSize = Dimension(Constants.ST_RADIUS_WIDTH, Constants.ST_RADIUS_HEIGHT)
        radius.minimumSize = Dimension(Constants.ST_RADIUS_WIDTH, Constants.ST_RADIUS_HEIGHT)
        radius.maximumSize = Dimension(Constants.ST_RADIUS_WIDTH, Constants.ST_RADIUS_HEIGHT)
        radius.horizontalAlignment = SwingConstants.CENTER
        radius.addActionListener(EnterListener())
        radius.setActionCommand(Strings.ST_ACTION_RADIUS)

        // polygon point count label
        pointsLabel.foreground = Colors.LIGHTER_GRAY
        pointsLabel.font = font

        // polygon point count combo box
        points.preferredSize = Dimension(Constants.ST_COMBO_WIDTH, Constants.ST_COMBO_HEIGHT)
        points.minimumSize = Dimension(Constants.ST_COMBO_WIDTH, Constants.ST_COMBO_HEIGHT)
        points.maximumSize = Dimension(Constants.ST_COMBO_WIDTH, Constants.ST_COMBO_HEIGHT)
        points.addActionListener(EnterListener())
        points.actionCommand = Strings.ST_ACTION_POINTS

        // text object label
        textLabel.foreground = Colors.LIGHTER_GRAY
        textLabel.font = font

        // text object text field
        text.preferredSize = Dimension(Constants.ST_TEXT_WIDTH, Constants.ST_TEXT_HEIGHT)
        text.minimumSize = Dimension(Constants.ST_TEXT_WIDTH, Constants.ST_TEXT_HEIGHT)
        text.maximumSize = Dimension(Constants.ST_TEXT_WIDTH, Constants.ST_TEXT_HEIGHT)
        text.border = BorderFactory.createEmptyBorder(0, 4, 0, 0)
        text.setActionCommand(Strings.ST_ACTION_TEXT)
        text.addActionListener(EnterListener())

        // font label
        fontLabel.foreground = Colors.LIGHTER_GRAY
        fontLabel.font = font

        // font chooser combo box
        fontChooser.preferredSize = Dimension(Constants.ST_TEXT_WIDTH, Constants.ST_TEXT_HEIGHT)
        fontChooser.minimumSize = Dimension(Constants.ST_TEXT_WIDTH, Constants.ST_TEXT_HEIGHT)
        fontChooser.maximumSize = Dimension(Constants.ST_TEXT_WIDTH, Constants.ST_TEXT_HEIGHT)
        fontChooser.actionCommand = Strings.ST_ACTION_FONT
        fontChooser.addActionListener(EnterListener())
        fontChooser.selectedItem = Canvas.getFont()
        fontChooser.background = Color.WHITE
        fontChooser.renderer = renderer
        fontChooser.font = Font((fontChooser.selectedItem as Font).name, Font.PLAIN, 12)


        // font size label
        fontSizeLabel.foreground = Colors.LIGHTER_GRAY
        fontSizeLabel.font = font

        // font size input field
        fontSize.preferredSize = Dimension(Constants.ST_TEXT_WIDTH, Constants.ST_TEXT_HEIGHT)
        fontSize.minimumSize = Dimension(Constants.ST_TEXT_WIDTH, Constants.ST_TEXT_HEIGHT)
        fontSize.maximumSize = Dimension(Constants.ST_TEXT_WIDTH, Constants.ST_TEXT_HEIGHT)
        fontSize.horizontalAlignment = SwingConstants.CENTER
        fontSize.setActionCommand(Strings.ST_ACTION_SIZE)
        fontSize.addActionListener(EnterListener())
        fontSize.border = null
    }

    /**
     * changes toolbar state based on current mode
     * @param mode - string representing the current mode
     */
    fun setMode(mode: String) {

        // removes any components on toolbar
        removeAll()
        add(Box.createHorizontalGlue())

        // adds shape name to toolbars
        toolName.text = mode
        add(toolName)

        // adds stroke size slider if current mode is any paint object except text
        if ((mode != Strings.MODE_MOVE) && (mode != Strings.MODE_ROTATE) && (mode != Strings.MODE_TEXT)) {
            add(Box.createHorizontalStrut(Constants.ST_LARGE_STRUT))
            add(strokeSize)
            add(Box.createHorizontalStrut(Constants.ST_SMALL_STRUT))
            add(strokeLabel)

            // adds filled checkbox if current mode is a shape
            if ((mode != Strings.MODE_BRUSH) && (mode != Strings.MODE_LINE)) {
                add(Box.createHorizontalStrut(Constants.ST_LARGE_STRUT))
                add(isFilled)
            }
        }

        // checks which mode is being used
        when (mode) {

            // rectangle mode
            Strings.MODE_RECTANGLE -> {

                // adds rectangle radius option
                add(Box.createHorizontalStrut(Constants.ST_LARGE_STRUT))
                add(radiusLabel)
                add(Box.createHorizontalStrut(Constants.ST_SMALL_STRUT))
                add(radius)
            }

            // polygon mode
            Strings.MODE_POLYGON -> {

                // adds point count option
                add(Box.createHorizontalStrut(Constants.ST_LARGE_STRUT))
                add(pointsLabel)
                add(Box.createHorizontalStrut(Constants.ST_SMALL_STRUT))
                add(points)
            }

            // text mode
            Strings.MODE_TEXT -> {

                // adds text options
                add(Box.createHorizontalStrut(Constants.ST_LARGE_STRUT))
                add(textLabel)
                add(Box.createHorizontalStrut(Constants.ST_SMALL_STRUT))
                add(text)
                add(Box.createHorizontalStrut(Constants.ST_LARGE_STRUT))
                add(fontLabel)
                add(Box.createHorizontalStrut(Constants.ST_SMALL_STRUT))
                add(fontChooser)
                add(Box.createHorizontalStrut(Constants.ST_LARGE_STRUT))
                add(fontSizeLabel)
                add(Box.createHorizontalStrut(Constants.ST_SMALL_STRUT))
                add(fontSize)
            }
        }

        add(Box.createHorizontalGlue())
        revalidate()
        repaint()
    }

    /**
     * get all system font names
     * @return fontList - list of all system fonts
     */
    private fun getFonts(): ArrayList<Font> {
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        val fonts = ge.allFonts
        val fontList = ArrayList<Font>()
        for (font in fonts)
            fontList.add(Font(font.name, Font.PLAIN, 16))
        return fontList
    }

    /**
     * listener for stroke size slider
     */
    inner class SliderListener: ChangeListener {

        /**
         * changes stroke size
         * @param e - change event
         */
        override fun stateChanged(e: ChangeEvent?) {

            // change stroke size
            val stroke = strokeSize.value
            Canvas.setStroke(BasicStroke(stroke.toFloat(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND))
            strokeLabel.text = Strings.ST_STROKE + stroke.toString()
        }
    }

    /**
     * listener for 'is filled' checkbox
     */
    inner class CheckListener: ItemListener {

        /**
         * changes filled state of shape
         * @param e - item event
         */
        override fun itemStateChanged(e: ItemEvent?) {
            Canvas.setFilled(isFilled.isSelected)
        }
    }

    /**
     * listener for text fields and combo boxes
     */
    inner class EnterListener: ActionListener {

        /**
         * checks which text field or combo box is active
         * @param e - action event
         */
        override fun actionPerformed(e: ActionEvent?) {

            // changes canvas variables based on options changed on the toolbar
            when (e!!.actionCommand) {
                Strings.ST_ACTION_RADIUS -> Canvas.setRadius(radius.text.toInt())
                Strings.ST_ACTION_POINTS -> Canvas.setPoints(points.selectedItem.toString().toInt())
                Strings.ST_ACTION_SIZE -> Canvas.setFontSize(fontSize.text.toInt())
                Strings.ST_ACTION_FONT -> {
                    Canvas.setFont((fontChooser.selectedItem as Font).name)
                    fontChooser.font = Font((fontChooser.selectedItem as Font).name, Font.PLAIN, 12)
                }
            }
        }
    }

    /**
     * custom renderer for font combo box
     */
    inner class CustomRenderer: ListCellRenderer<Font>, JLabel() {

        /**
         * sets font to name of font in cell
         * @param list
         * @param value
         * @param index
         * @param isSelected
         * @param cellHasFocus
         */
        override fun getListCellRendererComponent(list: JList<out Font>?, value: Font?,
          index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component {
            background = Color.WHITE
            font = (value as Font).deriveFont(12)
            text = value.name
            return this
        }
    }
}