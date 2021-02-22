/**
 * @author Paul Scott
 * @version 20 February 2019
 *
 * class representing scoreboard panel
 */

import data.Constants
import data.Strings
import java.awt.*
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

class Scoreboard: JPanel() {

    // score banner
    private val banner = JLabel(Strings.SCORE_LABEL)

    /**
     * initializer
     */
    init {

        // configure panel
        size = Constants.SCOREBOARD_SIZE
        background = TetriK.theme.scoreboard
        layout = BorderLayout()

        // configure score banner
        banner.font = Constants.SCORE_LABEL_FONT
        banner.foreground = TetriK.theme.scoreFont
        banner.border = EmptyBorder(10, 10, 10, 10)
        add(banner, BorderLayout.NORTH)

        // configure score label
        score.font = Constants.SCORE_FONT
        score.foreground = TetriK.theme.scoreFont
        score.border = EmptyBorder(10, 10, 10, 10)
        add(score, BorderLayout.CENTER)
    }

    /**
     * updates ui colors on theme change
     */
    fun updateColors() {
        background = TetriK.theme.scoreboard
        banner.foreground = TetriK.theme.scoreFont
        score.foreground = TetriK.theme.scoreFont
    }

    companion object {

        // score label
        val score = JLabel(Strings.SCORE_ZERO)

        /**
         * increments score
         * @param value - amount to be added to score
         */
        fun addScore(value: Int) {
            var currentScore = Integer.parseInt(score.text)
            currentScore += value
            score.text = currentScore.toString()

            // change speed of game based on score
            when {
                currentScore >= Constants.SPEED_FIVE_SCORE -> GameLoop.setSpeed(Constants.SPEED_FIVE)
                currentScore >= Constants.SPEED_FOUR_SCORE -> GameLoop.setSpeed(Constants.SPEED_FOUR)
                currentScore >= Constants.SPEED_THREE_SCORE -> GameLoop.setSpeed(Constants.SPEED_THREE)
                currentScore >= Constants.SPEED_TWO_SCORE -> GameLoop.setSpeed(Constants.SPEED_TWO)
                currentScore >= Constants.SPEED_ONE_SCORE -> GameLoop.setSpeed(Constants.SPEED_ONE)
            }
        }
    }
}