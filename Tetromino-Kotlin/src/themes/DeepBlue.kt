/**
 * @author Paul Scott
 * @version 21 February 2019
 *
 * deep blue theme
 */

package themes

import java.awt.Color

class DeepBlue: Theme() {

    // ui colors
    override val playfield = Color(20, 29, 45)
    override val divider = Color(46, 68, 104)
    override val window = Color(46, 68, 104)
    override val scoreboard = Color(20, 29, 45)
    override val scoreFont = Color(153, 170, 201)
    override val menu = Color(67, 94, 140)
    override val menuFont = Color(153, 170, 201)
    override val playfieldFont = Color(255, 255, 255)

    // block color
    override val iBlock = Color(153, 170, 201)
    override val jBlock = Color(132, 158, 206)
    override val lBlock = Color(110, 142, 201)
    override val oBlock = Color(86, 127, 201)
    override val sBlock = Color(61, 112, 204)
    override val tBlock = Color(37, 95, 198)
    override val zBlock = Color(16, 83, 204)

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