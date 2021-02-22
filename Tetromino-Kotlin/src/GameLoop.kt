/**
 * @author Paul Scott
 * @version 21 February 2019
 *
 * class representing tetris game loop
 */

import data.Constants
import java.util.*

class GameLoop: Thread() {

    /**
     * run game loop
     */
    override fun run() {

        // function step is called to move block downward
        timer = Timer()
        timer.scheduleAtFixedRate(Step(), speed, speed)

        // boolean values
        isRunning = true
        isPaused = false
        newBlock = true

        // generates first 5 blocks
        val rand = Random()
        for (i in 0 until 5) {
            nextBlocks.add(rand.nextInt(7))
        }

        // game loop
        while (isRunning) {

            if (!isPaused) {
                // spawn new block
                if (newBlock) {
                    PlayField.currBlock = Tetromino(nextBlocks.removeAt(0))
                    nextBlocks.add(rand.nextInt(7))

                    // check if new block can be spawned else end game
                    if (PlayField.currBlock.newBlockCollision()) {
                        isRunning = false
                        timer.cancel()
                    }
                    newBlock = false
                }
                TetriK.repaint()
            }
        }
    }

    companion object {

        // static variables
        private var speed = Constants.SPEED_DEFAULT
        var timer = Timer()
        var newBlock = false
        var isRunning = false
        var isPaused = false
        val nextBlocks = ArrayList<Int>()

        /**
         * set speed of game
         * @param speed - speed in ms
         */
        fun setSpeed(speed: Long) {
            timer.cancel()
            timer = Timer()
            timer.scheduleAtFixedRate(Step(), speed, speed)
        }

        // moves block downward over a constant period
        class Step: TimerTask() {

            /**
             * moves block downward
             */
            override fun run() {
                if (!isPaused) {
                    PlayField.currBlock.down()
                    TetriK.repaint()
                }
            }
        }
    }
}