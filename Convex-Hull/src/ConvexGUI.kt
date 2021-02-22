/**
 * @author Paul Scott
 * @version 25 January 2019
 *
 * class representing user interface
 */

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JFrame
import javax.swing.JPanel

class ConvexGUI: JFrame() {

    // instance data
    private val ch = ConvexHull()

    // main method to start program
    companion object {
        @JvmStatic fun main(args: Array<String>) { ConvexGUI() }
    }

    // initialize interface
    init {
        title = "Convex Hull"
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane.preferredSize = Dimension(500, 500)

        // container that holds main graphic
        val container = JPanel()
        container.layout = BoxLayout(container, BoxLayout.Y_AXIS)
        container.background = Color(66, 215, 244)
        container.add(Box.createVerticalGlue())
        container.add(ch)
        container.add(Box.createVerticalGlue())
        add(container, BorderLayout.CENTER)

        // make UI visible
        pack()
        isVisible = true
        ch.convexHull()
    }
}
