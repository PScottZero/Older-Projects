/**
 * @author Paul Scott
 * @version 20 February 2019
 *
 * class representing next block panel
 */

import data.Constants
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JPanel

class NextBlock: JPanel() {

    /**
     * initializer
     */
    init {
        size = Constants.NEXTPIECE_SIZE
        background = TetriK.theme.playfield
    }

    /**
     * updates ui colors on theme change
     */
    fun updateColors() { background = TetriK.theme.playfield }

    /**
     * draws panel and the next five blocks to spawn
     * @param g - graphics variable
     */
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        val g2d = g as Graphics2D

        // draws next five blocks
        for (i in 0 until GameLoop.nextBlocks.size) {
            val block = Tetromino(GameLoop.nextBlocks[i])
            when {
                GameLoop.nextBlocks[i] == Constants.I_BLOCK_INT ->
                    block.drawNext(g2d, TetriK.theme.colors, 35, i * 90 - 5)
                GameLoop.nextBlocks[i] == Constants.O_BLOCK_INT ->
                    block.drawNext(g2d, TetriK.theme.colors, 35, i * 90 + 25)
                else -> block.drawNext(g2d, TetriK.theme.colors, 45, i * 90 + 25)
            }
        }

        // draw dividers
        for (i in 90 until 450 step 90) {
            g2d.color = TetriK.theme.divider
            g2d.drawLine(0, i, 150, i)
        }
    }
}