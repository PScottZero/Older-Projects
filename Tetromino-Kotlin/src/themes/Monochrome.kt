/**
 * @author Paul Scott
 * @version 20 February 2019
 *
 * monochrome theme
 */

package themes

import java.awt.Color

class Monochrome: Theme() {
    // ui colors
    override val playfield = Color(0, 0, 0)
    override val divider = Color(50, 50, 50)
    override val window = Color(50, 50, 50)
    override val scoreboard = Color(0, 0, 0)
    override val scoreFont = Color(255, 255, 255)
    override val menu = Color(60, 60, 60)
    override val menuFont = Color(255, 255, 255)
    override val playfieldFont = Color(255, 255, 255)

    // block color
    override val iBlock = Color(220, 220, 220)
    override  val jBlock = Color(180, 180, 180)
    override  val lBlock = Color(140, 140, 140)
    override  val oBlock = Color(100, 100, 100)
    override  val sBlock = Color(80, 80, 80)
    override  val tBlock = Color(60, 60, 60)
    override  val zBlock = Color(40, 40, 40)

    // block color array [do not modify!!!]
    override val colors = arrayOf(
        iBlock,
        jBlock,
        lBlock,
        oBlock,
        sBlock,
        tBlock,
        zBlock
    )
}