/**
 * @author Paul Scott
 * @version 20 February 2019
 *
 * gameboy theme
 */

package themes

import java.awt.Color

class GameBoy: Theme() {

    // ui colors
    override val playfield = Color(202, 211, 171)
    override val divider = Color(90, 95, 76)
    override val window = Color(146, 154, 122)
    override val scoreboard = Color(202, 211, 171)
    override val scoreFont = Color(50, 61, 53)
    override val menu = Color(90, 95, 76)
    override val menuFont = Color(202, 211, 171)
    override val playfieldFont = Color(50, 61, 53)

    // block color
    override val iBlock = Color(146, 154, 122)
    override val jBlock = Color(90, 95, 76)
    override val lBlock = Color(146, 154, 122)
    override val oBlock = Color(50, 61, 53)
    override val sBlock = Color(90, 95, 76)
    override val tBlock = Color(146, 154, 122)
    override val zBlock = Color(50, 61, 53)

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