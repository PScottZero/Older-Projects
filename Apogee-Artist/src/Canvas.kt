import data.Constants
import data.Strings
import shapes.*
import shapes.Polygon
import shapes.Rectangle
import java.awt.*
import java.awt.event.*
import java.util.*
import javax.swing.Box
import javax.swing.JPanel

class Canvas: JPanel() {

    // static object
    companion object {

        // paint object array and undo stack
        private var objects = ArrayList<PaintObject>()
        private var undoObjects = Stack<PaintObject>()

        // default values
        private var currColor: Color = Color.BLACK
        private var currFill: Color = Color.BLACK
        private var currFont = Strings.CANVAS_FONT
        private var currFontSize = Constants.CANVAS_FONT_SIZE
        private var currPoints = Constants.CANVAS_POINTS
        private var currRadius = Constants.CANVAS_RADIUS
        private var currRotation = 0.0
        private var currStroke = BasicStroke(Constants.CANVAS_STROKE.toFloat(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
        private var currText = Strings.CANVAS_TEXT
        private var currTranslationX = 0
        private var currTranslationY = 0
        private var filled = false
        private var mode = Strings.MODE_BRUSH
        private var startPoint = Point(0, 0)

        // sets canvas mode
        fun setMode(newMode: String) { mode = newMode }

        // sets primary color
        fun setColor(newColor: Color) { currColor = newColor }

        // returns primary color
        fun getColor(): Color { return currColor }

        // sets fill color
        fun setFill(newFill: Color) { currFill = newFill }

        // returns fill color
        fun getFill(): Color { return currFill }

        // sets fill status used for shapes
        fun setFilled(newFilled: Boolean) { filled = newFilled }

        // sets paint stroke width
        fun setStroke(newStroke: BasicStroke) { currStroke = newStroke }

        // returns paint stroke width
        fun getStrokeSize(): Int { return currStroke.lineWidth.toInt() }

        // sets corner radius used for rectangles
        fun setRadius(newRadius: Int) { currRadius = newRadius }

        // returns corner radius used for rectangles
        fun getRadius(): Int { return currRadius }

        // returns text used for text object
        fun getText(): String { return currText }

        // sets font used for text object
        fun setFont(newFont: String) { currFont = newFont }

        // returns font used for text object
        fun getFont(): String { return currFont }

        // sets font size used for text object
        fun setFontSize(newSize: Int) { currFontSize = newSize }

        // returns font size used for text object
        fun getFontSize(): Int { return currFontSize }

        // sets point count used for polygons
        fun setPoints(newPoints: Int) { currPoints = newPoints }

        // sets object array
        fun setObjects(obj: ArrayList<PaintObject>) {
            undoObjects.clear()
            objects = ArrayList(obj)
        }

        // returns objects array
        fun getObjects(): ArrayList<PaintObject> { return objects }

        // checks if objects array is empty
        fun updateUndo(): Boolean { return objects.size > 0 }

        // checks if undo stack is empty
        fun updateRedo(): Boolean { return undoObjects.size > 0 }
    }

    // initializes canvas
    init {
        minimumSize = Dimension(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT)
        preferredSize = Dimension(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT)
        maximumSize = Dimension(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT)
        background = Color.WHITE
        alignmentX = Box.CENTER_ALIGNMENT
        addMouseListener(MouseEventListener())
        addMouseMotionListener(MouseEventListener())
    }

    // undoes previous object
    fun undo() {
        undoObjects.add(objects.last())
        objects.remove(objects.last())
        repaint()
    }

    // redoes previously undone object
    fun redo() {
        objects.add(undoObjects.pop())
        repaint()
    }

    // sets dimension of canvas
    fun setDimension(dim: Dimension) {
        minimumSize = dim
        preferredSize = dim
        maximumSize = dim
        revalidate()
        repaint()
    }

    // returns dimension of canvas
    fun getDimension(): Dimension { return preferredSize }

    // method for drawing canvas
    override fun paintComponent(g: Graphics) {

        super.paintComponent(g)
        val g2d = g as Graphics2D

        // rendering hints
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY)
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY)
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE)

        // draws objects
        for(obj in objects) {
            obj.draw(g2d)
        }
    }

    // mouse listener
    inner class MouseEventListener: MouseAdapter() {

        // if mouse is pressed
        override fun mousePressed(e: MouseEvent?) {

            val pointList = ArrayList<Point>()

            // checks which mode is currently set
            when (mode) {

                // brush mode
                Strings.MODE_BRUSH -> {
                    pointList.add(e!!.point)
                    objects.add(PaintStroke(pointList, currColor, currStroke))
                }

                // line mode
                Strings.MODE_LINE -> {
                    pointList.add(e!!.point)
                    pointList.add(e.point)
                    objects.add(Line(pointList, currColor, currStroke))
                }

                // rectangle mode
                Strings.MODE_RECTANGLE -> {
                    pointList.add(e!!.point)
                    pointList.add(e.point)
                    objects.add(Rectangle(pointList, currColor, currFill, filled, currStroke, currRadius))
                }

                // polygon mode
                Strings.MODE_POLYGON -> {
                    pointList.add(e!!.point)
                    pointList.add(e.point)
                    objects.add(Polygon(pointList, currColor, currFill, filled, currStroke, currPoints))
                }

                // oval mode
                Strings.MODE_OVAL -> {
                    pointList.add(e!!.point)
                    pointList.add(e.point)
                    objects.add(Oval(pointList, currColor, currFill, filled, currStroke))
                }

                // text mode
                Strings.MODE_TEXT -> {
                    pointList.add(e!!.point)
                    objects.add(Text(pointList, currColor, OptionBar.getText(), currFont, currFontSize))
                }

                // move mode
                Strings.MODE_MOVE -> {
                    if (objects.isNotEmpty()) {
                        startPoint = e!!.point
                        currTranslationX = objects.last().getTranslationX()
                        currTranslationY = objects.last().getTranslationY()
                    }
                }

                // rotate mode
                Strings.MODE_ROTATE -> {
                    if (objects.isNotEmpty()) {
                        startPoint = e!!.point
                        currRotation = objects.last().getRotation()
                    }
                }
            }

            repaint()
            undoObjects.clear()
            Apogee.update()
        }

        // if mouse is dragged
        override fun mouseDragged(e: MouseEvent?) {

            //checks which mode is currently set
            when (mode) {

                // move mode
                Strings.MODE_MOVE -> {
                    if (objects.isNotEmpty()) {

                        // shifts shape by how far the mouse is moved
                        val shiftX = e!!.point.x - startPoint.x
                        val shiftY = e.point.y - startPoint.y
                        objects.last().setTranslationX(currTranslationX + shiftX)
                        objects.last().setTranslationY(currTranslationY + shiftY)
                    }
                }

                // rotate mode
                Strings.MODE_ROTATE -> {
                    if (objects.isNotEmpty()) {

                        // rotates shape based on change in horizontal mouse direction
                        val endPoint = e!!.point
                        val shift = (startPoint.x.toDouble() - endPoint.x.toDouble()) / 10.0
                        objects.last().setRotation(currRotation + shift)
                    }
                }

                // resize shape based on new mouse position
                else -> objects.last().setPoint(e!!.point)
            }
            repaint()
        }
    }
}