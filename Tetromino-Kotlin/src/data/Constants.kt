/**
 * @author Paul Scott
 * @version 20 February 2019
 *
 * class containing all constants
 */

package data

import java.awt.Dimension
import java.awt.Font

class Constants {

    companion object {

        /*
        SIZING
        sizing of ui components
         */
        val WINDOW_SIZE = Dimension(600, 700)
        val SCOREBOARD_SIZE = Dimension(150, 100)
        val NEXTPIECE_SIZE = Dimension(150, 450)
        val PLAYFIELD_SIZE = Dimension(300, 600)
        const val BLOCK_SIZE = 30
        const val BLOCK_SIZE_ALT = 20

        /*
        FONTS
        fonts for ui
         */
        val MENU_FONT = Font("Arial", Font.PLAIN, 16)
        val PLAYFIELD_FONT = Font("Monospaced", Font.BOLD, 36)
        val SCORE_LABEL_FONT = Font("Arial", Font.PLAIN, 24)
        val SCORE_FONT = Font("Arial", Font.PLAIN, 36)

        /*
        SCORING MULTIPLIERS
        multiplies score based on number
        of lines cleared
         */
        const val MULT_TWO = 2
        const val MULT_THREE = 3
        const val MULT_FOUR = 4

        /*
        SPEED
        rate at which blocks fall
         */
        const val SPEED_DEFAULT: Long = 1000
        const val SPEED_ONE: Long = 750
        const val SPEED_TWO: Long = 500
        const val SPEED_THREE: Long = 250
        const val SPEED_FOUR: Long = 100
        const val SPEED_FIVE: Long = 50

        /*
        SPEED SCORES
        these value determine what scores
        trigger faster block speeds
        */
        const val SPEED_ONE_SCORE = 25
        const val SPEED_TWO_SCORE = 50
        const val SPEED_THREE_SCORE = 100
        const val SPEED_FOUR_SCORE = 250
        const val SPEED_FIVE_SCORE = 500

        /*
        BLOCK INTEGERS
        integers representing block shapes
         */
        const val I_BLOCK_INT = 0
        const val J_BLOCK_INT = 1
        const val L_BLOCK_INT = 2
        const val O_BLOCK_INT = 3
        const val S_BLOCK_INT = 4
        const val T_BLOCK_INT = 5
        const val Z_BLOCK_INT = 6
    }
}