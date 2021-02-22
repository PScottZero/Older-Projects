/**
 * @author Paul Scott
 * @version 20 February 2019
 *
 * kotlin theme
 */

package themes

import java.awt.Color

class Kotlin: Theme() {

    // ui colors
    override val playfield = Color(50, 50, 50)
    override val divider = Color(70, 70, 70)
    override val window = Color(0, 149, 213)
    override val scoreboard = Color(50, 50, 50)
    override val scoreFont = Color(255, 255, 255)
    override val menu = Color(226, 115, 89)
    override val menuFont = Color(255, 185, 159)
    override val playfieldFont = Color(255, 255, 255)

    // block color
    override val iBlock = Color(100, 169, 233)
    override val jBlock = Color(74, 126, 221)
    override val lBlock = Color(240, 129, 37)
    override val oBlock = Color(226, 115, 89)
    override val sBlock = Color(127, 110, 227)
    override val tBlock = Color(199, 87, 188)
    override val zBlock = Color(215, 103, 130)

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