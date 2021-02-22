/**
 * @author Paul Scott
 * @version 20 February 2019
 *
 * theme class
 */

package themes

import java.awt.Color

abstract class Theme {

    // ui colors
    abstract val playfield: Color
    abstract val divider: Color
    abstract val window: Color
    abstract val scoreboard: Color
    abstract val scoreFont: Color
    abstract val menu: Color
    abstract val menuFont: Color
    abstract val playfieldFont: Color

    // block color
    abstract val iBlock: Color
    abstract val jBlock: Color
    abstract val lBlock: Color
    abstract val oBlock: Color
    abstract val sBlock: Color
    abstract val tBlock: Color
    abstract val zBlock: Color

    // block color array [do not modify!!!]
    abstract val colors: Array<Color>
}