/**
 * @author Paul Scott
 * @version 21 February 2019
 *
 * pastel theme
 */

package themes

import java.awt.Color

class Pastel: Theme() {
    
    // ui colors
    override val playfield = Color(29, 34, 35)
    override val divider = Color(50, 59, 61)
    override val window = Color(65, 77, 81)
    override val scoreboard = Color(29, 34, 35)
    override val scoreFont = Color(255, 255, 255)
    override val menu = Color(142, 168, 173)
    override val menuFont = Color(255, 255, 255)
    override val playfieldFont = Color(255, 255, 255)

    // block color
    override val iBlock = Color(152, 214, 210)
    override val jBlock = Color(120, 151, 204)
    override val lBlock = Color(201, 165, 120)
    override val oBlock = Color(239, 231, 165)
    override val sBlock = Color(126, 201, 148)
    override val tBlock = Color(178, 149, 193)
    override val zBlock = Color(191, 120, 120)

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