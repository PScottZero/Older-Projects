/**
 * @author Paul Scott
 * @version 21 February 2019
 *
 * class representing main tetris grid
 */

import data.Constants
import data.Strings
import java.awt.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JPanel

class PlayField: JPanel() {

    /**
     * initializer
     */
    init {

        // configure panel
        size = Constants.PLAYFIELD_SIZE
        background = TetriK.theme.playfield
        addKeyListener(InputListener())
        isFocusable = true
    }

    /**
     * paint component for playfield panel
     * @param g - graphics variable
     */
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        val g2d = g as Graphics2D

        // antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

        // draw existing pieces
        for (r in 0 until grid.size) {
            for (c in 0 until grid[0].size) {
                if (grid[r][c] != 0) g2d.color = colors[grid[r][c] - 1]
                else g2d.color = Color(0, 0, 0, 0)
                g2d.fillRect(c * Constants.BLOCK_SIZE, r * Constants.BLOCK_SIZE,
                    Constants.BLOCK_SIZE, Constants.BLOCK_SIZE)
            }
        }

        // draw current piece
        if (GameLoop.isRunning)
            currBlock.draw(g2d, colors, currC * Constants.BLOCK_SIZE, currR * Constants.BLOCK_SIZE)

        // draw dividers
        g2d.color = TetriK.theme.divider
        for (i in Constants.BLOCK_SIZE until 300 step Constants.BLOCK_SIZE)
            g2d.drawLine(i, 0, i, 600)
        for (j in Constants.BLOCK_SIZE until 600 step Constants.BLOCK_SIZE)
            g2d.drawLine(0, j, 300, j)

        // print game over if game ends
        if (!GameLoop.isRunning && TetriK.isStarted) {
            g2d.color = TetriK.theme.playfieldFont
            g2d.font = Constants.PLAYFIELD_FONT
            g2d.drawString(Strings.GAME_OVER, 55, 310)
        }

        // print pause if game is paused
        if (GameLoop.isPaused && GameLoop.isRunning) {
            g2d.color = TetriK.theme.playfieldFont
            g2d.font = Constants.PLAYFIELD_FONT
            g2d.drawString(Strings.PAUSED, 85, 310)
        }
    }

    /**
     * updates ui colors on theme change
     */
    fun updateColors() {
        background = TetriK.theme.playfield
        colors = TetriK.theme.colors
    }

    // key listener for game input
    inner class InputListener: KeyAdapter() {

        /**
         * checks if key has been pressed
         * @param e - key event
         */
        override fun keyPressed(e: KeyEvent?) {

            if (!GameLoop.isPaused) {
                when (e!!.keyCode) {
                    KeyEvent.VK_LEFT -> { currBlock.left() } // move left
                    KeyEvent.VK_RIGHT -> { currBlock.right() } // move right
                    KeyEvent.VK_DOWN -> { currBlock.down() } // move down
                    KeyEvent.VK_X, KeyEvent.VK_SPACE -> { currBlock.clockwise() } // rotate clockwise
                    KeyEvent.VK_Z -> { currBlock.counterClockwise() } // rotate counter clockwise
                }
            }
        }
    }

    companion object {

        // static variables
        val grid = Array(20){IntArray(10)}
        var colors = TetriK.theme.colors
        var currBlock = Tetromino(0)
        var currR = 0
        var currC = 3

        /**
         * clears full lines
         */
        fun clearLines() {
            var count = 0
            for (r in 0 until grid.size) {

                // checks if line is full
                if (!grid[r].contains(0)) {
                    for (i in r downTo 1) grid[i] = grid[i-1].copyOf()
                    grid[0] = IntArray(10)
                    count++
                }
            }

            // multiplies score based on
            // number of lines cleared
            when (count) {
                2 -> count *= Constants.MULT_TWO
                3 -> count *= Constants.MULT_THREE
                4 -> count *= Constants.MULT_FOUR
            }
            Scoreboard.addScore(count)
        }

        /**
         * restart game
         */
        fun restart() {
            currR = 0
            currC = 3

            // clear playfield grid
            for (r in 0 until grid.size)
                for (c in 0 until grid[r].size)
                    grid[r][c] = 0
        }
    }
}