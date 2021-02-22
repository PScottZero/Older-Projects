/**
 * @author Paul Scott
 * @version 21 February 2019
 *
 * tetromino block class
 */

import data.Constants
import java.awt.Color
import java.awt.Graphics2D

class Tetromino(private val shape: Int) {

    // block grid
    private lateinit var grid: Array<IntArray>

    /**
     * initializer
     */
    init {

        // determine shape of block
        when (shape) {
            Constants.I_BLOCK_INT -> grid = arrayOf(
                intArrayOf(0, 0, 0, 0),
                intArrayOf(0, 0, 0, 0),
                intArrayOf(1, 1, 1, 1),
                intArrayOf(0, 0, 0, 0)
            )
            Constants.J_BLOCK_INT -> grid = arrayOf(
                intArrayOf(2, 0, 0),
                intArrayOf(2, 2, 2),
                intArrayOf(0, 0, 0)
            )
            Constants.L_BLOCK_INT -> grid = arrayOf(
                intArrayOf(0, 0, 3),
                intArrayOf(3, 3, 3),
                intArrayOf(0, 0, 0)
            )
            Constants.O_BLOCK_INT -> grid = arrayOf(
                intArrayOf(0, 4, 4, 0),
                intArrayOf(0, 4, 4, 0),
                intArrayOf(0, 0, 0, 0)
            )
            Constants.S_BLOCK_INT -> grid = arrayOf(
                intArrayOf(0, 5, 5),
                intArrayOf(5, 5, 0),
                intArrayOf(0, 0, 0)
            )
            Constants.T_BLOCK_INT -> grid = arrayOf(
                intArrayOf(0, 6, 0),
                intArrayOf(6, 6, 6),
                intArrayOf(0, 0, 0)
            )
            Constants.Z_BLOCK_INT -> grid = arrayOf(
                intArrayOf(7, 7, 0),
                intArrayOf(0, 7, 7),
                intArrayOf(0, 0, 0)
            )
        }
    }

    /**
     * rotate block grid clockwise
     */
    private fun rotateCW() {
        if (grid.size == grid[0].size) {

            // transpose matrix
            for (r in 0 until grid.size) {
                for (c in r until grid[r].size) {
                    val temp = grid[r][c]
                    grid[r][c] = grid[c][r]
                    grid[c][r] = temp
                }
            }

            // flip matrix horizontally
            for (c in 0 until (grid[0].size / 2)) {
                for (r in 0 until grid.size) {
                    val temp = grid[r][c]
                    grid[r][c] = grid[r][grid.size - c - 1]
                    grid[r][grid.size - c - 1] = temp
                }
            }
        }
    }

    /**
     * rotate block grid counter clockwise
     */
    private fun rotateCCW() {
        if (grid.size == grid[0].size) {

            // transpose matrix
            for (r in 0 until grid.size) {
                for (c in r until grid[r].size) {
                    val temp = grid[r][c]
                    grid[r][c] = grid[c][r]
                    grid[c][r] = temp
                }
            }

            // flip matrix vertically
            for (r in 0 until (grid.size / 2)) {
                for (c in 0 until grid[r].size) {
                    val temp = grid[r][c]
                    grid[r][c] = grid[grid.size - r - 1][c]
                    grid[grid.size - r - 1][c] = temp
                }
            }
        }
    }

    /**
     * rotate block clockwise
     */
    fun clockwise() {
        rotateCW()
        if (collision()) rotateCCW()
    }

    /**
     * rotate block counter clockwise
     */
    fun counterClockwise() {
        rotateCCW()
        if (collision()) rotateCW()
    }

    /**
     * move block left
     */
    fun left() {
        PlayField.currC--
        if (collision()) PlayField.currC++
    }

    /**
     * move block right
     */
    fun right() {
        PlayField.currC++
        if (collision()) PlayField.currC--
    }

    /**
     * move block down
     */
    fun down(){
        PlayField.currR++
        if (collision()) {
            PlayField.currR--
            PlayField.currBlock.merge()
            PlayField.currR = 0
            PlayField.currC = 3
            GameLoop.newBlock = true
            PlayField.clearLines()
        }
    }

    /**
     * check if new block can be spawned
     * @return true in new block cannot be spawned, false otherwise
     */
    fun newBlockCollision(): Boolean {
        if (collision()) {
            merge()
            return true
        }
        return false
    }

    /**
     * check if collision occurs when
     * a given move is performed
     * @return true if collision occurs, false otherwise
     */
    private fun collision(): Boolean {
        for (r in 0 until grid.size) {
            for (c in 0 until grid[r].size) {
                if (grid[r][c] != 0) {

                    // out of playfield bounds
                    if (PlayField.currC + c < 0 ||
                        PlayField.currC + c >= PlayField.grid[0].size ||
                        PlayField.currR + r >= PlayField.grid.size)
                        return true

                    // block collision
                    if (PlayField.grid[PlayField.currR + r][PlayField.currC + c] != 0)
                        return true
                }
            }
        }
        return false
    }

    /**
     * merge current block with
     * playfield blocks
     */
    private fun merge() {
        for (r in 0 until grid.size) {
            for (c in 0 until grid[0].size) {
                if (grid[r][c] != 0) {
                    PlayField.grid[PlayField.currR + r][PlayField.currC + c] = grid[r][c]
                }
            }
        }
    }

    /**
     * draws block on playfield
     */
    fun draw(g2d: Graphics2D, colors: Array<Color>, x: Int, y: Int) {
        for (r in 0 until grid.size) {
            for (c in 0 until grid[0].size) {
                if (grid[r][c] != 0) g2d.color = colors[shape]
                else g2d.color = Color(0, 0, 0, 0)
                g2d.fillRect(x + Constants.BLOCK_SIZE * c, y + Constants.BLOCK_SIZE * r,
                    Constants.BLOCK_SIZE, Constants.BLOCK_SIZE)
            }
        }
    }

    /**
     * draws block on next block panel
     */
    fun drawNext(g2d: Graphics2D, colors: Array<Color>, x: Int, y: Int) {
        for (r in 0 until grid.size) {
            for (c in 0 until grid[0].size) {
                if (grid[r][c] != 0) g2d.color = colors[shape]
                else g2d.color = Color(0, 0, 0, 0)
                g2d.fillRect(x + Constants.BLOCK_SIZE_ALT * c, y + Constants.BLOCK_SIZE_ALT * r,
                    Constants.BLOCK_SIZE_ALT, Constants.BLOCK_SIZE_ALT)
            }
        }
    }
}