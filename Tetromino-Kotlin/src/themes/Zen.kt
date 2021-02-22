/**
 * @author Paul Scott
 * @version 21 February 2019
 *
 * zen theme
 */

package themes

import java.awt.Color

class Zen: Theme() {
    // ui colors
    override val playfield = Color(219, 237, 215)
    override val divider = Color(92, 114, 87)
    override val window = Color(173, 214, 164)
    override val scoreboard = Color(219, 237, 215)
    override val scoreFont = Color(92, 114, 87)
    override val menu = Color(219, 237, 215)
    override val menuFont = Color(124, 170, 114)
    override val playfieldFont = Color(42, 109, 26)

    // block color
    override val iBlock = Color(157, 204, 146)
    override  val jBlock = Color(135, 198, 121)
    override  val lBlock = Color(135, 198, 121)
    override  val oBlock = Color(100, 186, 80)
    override  val sBlock = Color(100, 186, 80)
    override  val tBlock = Color(74, 142, 58)
    override  val zBlock = Color(74, 142, 58)

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