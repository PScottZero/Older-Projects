/**
 * @author Paul Scott
 * @version 20 February 2019
 *
 * default theme
 */

package themes

import java.awt.Color

class Default: Theme() {
        
    // ui colors
    override val playfield = Color(0, 0, 0)
    override val divider = Color(80, 80, 80)
    override val window = Color(80, 80, 80)
    override val scoreboard = Color(0, 0, 0)
    override val scoreFont = Color(230, 230, 230)
    override val menu = Color(100, 100, 100)
    override val menuFont = Color(230, 230, 230)
    override val playfieldFont = Color(255, 255, 255)

    // block color
    override val iBlock = Color(0, 255, 255)
    override  val jBlock = Color(0, 0, 255)
    override  val lBlock = Color(255, 128, 0)
    override  val oBlock = Color(255, 255, 0)
    override  val sBlock = Color(0, 255, 0)
    override  val tBlock = Color(128, 0, 255)
    override  val zBlock = Color(255, 0, 0)

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