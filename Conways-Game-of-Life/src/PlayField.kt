import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.*
import javax.swing.JPanel
import javax.swing.SwingUtilities

// class that defines 'play field' where cells are displayed
class PlayField: JPanel() {

    // static methods and variables
    companion object {

        // dimensions
        private const val WIDTH = 900
        private const val HEIGHT = 600

        // cell arrays
        private var currentGeneration = Array(60, {IntArray(90)})
        private var nextGeneration = Array(60, {IntArray(90)})

        // helpful values
        private val shift = WIDTH / currentGeneration[0].size
        private var isRunning = false
        private val ROW_SIZE = currentGeneration.size
        private val COL_SIZE = currentGeneration[0].size

        // colors
        private var cellColor = Color.WHITE
        private var backgroundColor = Color.BLACK

        // speed
        private var speed = 50.toLong()

        // changes running status
        fun update() { isRunning = !isRunning }

        // creates random field of cells
        fun generateCells() {
            val random = Random()
            for (r in 0 until ROW_SIZE) {
                for (c in 0 until COL_SIZE) {
                    val i = random.nextInt(8)
                    if (i == 0) currentGeneration[r][c] = 1
                    else currentGeneration[r][c] = 0
                }
            }
            isRunning = false
            Controls.updateGenerationCount(false)
        }

        // clears cell field
        fun clearCells() {
            for (r in 0 until ROW_SIZE)
                for (c in 0 until COL_SIZE)
                    currentGeneration[r][c] = 0

            isRunning = false
            Controls.updateGenerationCount(false)
        }

        // adds glider pattern
        fun addGlider() {
            clearCells()

            val pointArray = intArrayOf(0, 1, 0,
                                        0, 0, 1,
                                        1, 1, 1)

            for (i in 0 until pointArray.size)
                currentGeneration[(i / 3) + 1][(i % 3) + 1] = pointArray[i]
        }

        // adds gosper's glider gun pattern
        fun addGosper() {
            clearCells()

            val pointArray = intArrayOf(
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 ,0 ,0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1,
                    1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

            for (i in 0 until pointArray.size)
                currentGeneration[(i / 36) + 1][(i % 36) + 1] = pointArray[i]
        }

        // adds lightweight spaceship pattern
        fun addLightweight() {
            clearCells()

            val pointArray = intArrayOf(1, 0, 0, 1, 0,
                                        0, 0, 0, 0, 1,
                                        1, 0, 0, 0, 1,
                                        0, 1, 1, 1, 1)

            for (i in 0 until pointArray.size)
                currentGeneration[(i / 5) + 28][(i % 5) + 1] = pointArray[i]
        }

        // adds pulsar pattern
        fun addPulsar() {
            clearCells()

            val pointArray = intArrayOf(0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0 ,0,
                                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0,
                                        1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1,
                                        1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1,
                                        1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1,
                                        0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0 ,0,
                                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0,
                                        0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0 ,0,
                                        1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1,
                                        1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1,
                                        1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1,
                                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0,
                                        0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0 ,0)

            for (i in 0 until pointArray.size)
                currentGeneration[(i / 13) + 24][(i % 13) + 39] = pointArray[i]
        }

        // adds pentadecathlon pattern
        fun addPentadecathlon() {
            clearCells()

            val pointArray = intArrayOf(0, 0, 1, 0, 0, 0, 0, 1, 0, 0,
                                        1, 1, 0, 1, 1, 1, 1, 0, 1, 1,
                                        0, 0, 1, 0, 0, 0, 0, 1, 0, 0)

            for (i in 0 until pointArray.size)
                currentGeneration[(i / 10) + 29][(i % 10) + 40] = pointArray[i]
        }

        // computes next generation
        fun nextGeneration() {
            // cycles through all cells
            for (r in 0 until ROW_SIZE) {
                for (c in 0 until COL_SIZE) {

                    var count = 0

                    // cycles through all surrounding cells to count the number of living ones
                    for (i in r - 1 until r + 2)
                        for (j in c - 1 until c + 2)
                            if (!(i == r && j == c) && i >= 0 && i < ROW_SIZE && j >= 0 && j < COL_SIZE)
                                if (currentGeneration[i][j] == 1)
                                    count++

                    // cell dies if it has less than 2 or more than 3 neighbors
                    if (count > 3 || count < 2) nextGeneration[r][c] = 0

                    // dead cell comes alive if exactly 3 cells surround it
                    else if (count == 3 && currentGeneration[r][c] == 0) nextGeneration[r][c] = 1

                    // cell stays alive if it has 2 or 3 neighbors, or remains dead if above conditions not met
                    else nextGeneration[r][c] = currentGeneration[r][c]
                }
            }

            // copies next generation multidimensional array to current generation multidimensional array
            for (i in 0 until ROW_SIZE) currentGeneration[i] = nextGeneration[i].copyOf()

            Conway.update()
            Controls.updateGenerationCount(true)
        }

        // computes next generation and stops program from running
        fun next() {
            isRunning = false
            nextGeneration()
        }

        // returns cell color
        fun getCellColor(): Color { return cellColor }

        // sets cell color
        fun setCellColor(color: Color) { cellColor = color }

        // returns background color
        fun getBackgroundColor(): Color { return backgroundColor }

        // sets background color
        fun setBackgroundColor(color: Color) { backgroundColor = color }

        // sets program speed
        fun setSpeed(newSpeed: Long) { speed = newSpeed }
    }

    // initializer
    init {

        // configures panel
        preferredSize = Dimension(WIDTH, HEIGHT)
        minimumSize = Dimension(WIDTH, HEIGHT)
        maximumSize = Dimension(WIDTH, HEIGHT)
        background = Color.CYAN
        val mouseListener = MouseListener()
        addMouseListener(mouseListener)
        addMouseMotionListener(mouseListener)
    }

    // paint component
    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        // draws cells
        for (r in 0 until ROW_SIZE) {
            for (c in 0 until COL_SIZE) {
                if (currentGeneration[r][c] == 1) g.color = cellColor
                else g.color = backgroundColor
                g.fillRect(c * shift, r * shift, shift, shift)
            }
        }

        // draws grid
        g.color = Color(50, 50, 50)
        for (i in 0 until ROW_SIZE) g.drawLine(0, i * shift, WIDTH, i * shift)
        for (j in 0 until COL_SIZE) g.drawLine(j * shift, 0, j * shift, HEIGHT)

        // advances generation if play is enabled
        if (isRunning) {
            Thread.sleep(speed)
            nextGeneration()
        }
    }

    // listener for mouse
    inner class MouseListener: MouseAdapter() {

        // if mouse is pressed
        override fun mousePressed(arg0: MouseEvent) {

            // gets mouse position and adjusts to cell position
            val r = arg0.y / shift
            val c = arg0.x / shift

            // checks that row and column values are valid
            if (r in 0 until ROW_SIZE && c in 0 until COL_SIZE) {

                // left click adds cells, right click deletes cells
                if (arg0.button == MouseEvent.BUTTON1) currentGeneration[r][c] = 1
                else currentGeneration[r][c] = 0
                repaint()
            }
        }

        // if mouse is dragged
        override fun mouseDragged(e: MouseEvent) {

            // gets mouse position and adjusts to cell position
            val r = e.y / shift
            val c = e.x / shift

            // checks that row and column values are valid
            if (r in 0 until ROW_SIZE && c in 0 until COL_SIZE) {

                // left click adds cells, right click deletes cells
                if (SwingUtilities.isLeftMouseButton(e)) currentGeneration[r][c] = 1
                else currentGeneration[r][c] = 0
                repaint()
            }
        }
    }
}