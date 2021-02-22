/*
 *
 * @author Paul Scott
 *
 * @version 27 May 2018
 *
 * this conway's game of life simulator is very similar to the many that exist today,
 * however, this specific simulator was created by your's truly (:
 *
 * conway's game of life follows four basic rules:
 *
 * 1. if a cell has less than two neighbors, it dies due to underpopulation
 * 2. if a cell has more than three neighbors, it dies due to overpopulation
 * 3. if a cell has two or three neighbors, it lives onto the next generation
 * 4. if a dead cell has exactly three living neighbors, it comes to life due to reproduction
 *
 * read more: https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
 *
 */

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.imageio.ImageIO
import javax.swing.*

// class that defines frame for program
class Conway: JFrame() {

    // static methods and variables
    companion object {

        // instance data
        private val playField = PlayField()
        private val color = Color(200, 200, 200)

        // dimensions
        private const val WIDTH = 940
        private const val HEIGHT = 740

        // main method
        @JvmStatic fun main(args: Array<String>) { Conway() }

        // repaints play field
        fun update() { playField.repaint() }
    }

    // initializer
    init {

        // configure frame
        title = "Conway's Game of Life"
        iconImage = ImageIO.read(Conway::class.java.getResource("img/conway_icon.png"))
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        preferredSize = Dimension(WIDTH, HEIGHT)
        layout = BorderLayout()

        // add menu bar to frame
        addMenuBar()

        // container for play field
        val container = JPanel()
        container.layout = BoxLayout(container, BoxLayout.Y_AXIS)
        container.add(Box.createVerticalGlue())
        container.add(playField)
        container.add(Box.createVerticalGlue())
        container.background = color

        // add play field and controls
        add(container, BorderLayout.CENTER)
        add(Controls(), BorderLayout.SOUTH)

        // make frame visible
        pack()
        isVisible = true
    }

    // adds menu bar to frame
    private fun addMenuBar() {

        val menuListener = MenuListener()

        // set fonts and colors of menu components
        val font = Font("Arial", Font.PLAIN, 12)
        UIManager.put("Menu.foreground", Color.BLACK)
        UIManager.put("Menu.font", font)
        UIManager.put("MenuItem.foreground", Color.BLACK)
        UIManager.put("MenuItem.font", font)
        UIManager.put("RadioButtonMenuItem.font", font)

        // menu bar
        val menuBar = JMenuBar()
        menuBar.background = color
        menuBar.border = null

        // game menu
        val game = JMenu("Game")
        game.mnemonic = KeyEvent.VK_G
        menuBar.add(game)

        // new game option
        val newGame = JMenuItem("New Game")
        newGame.mnemonic = KeyEvent.VK_N
        newGame.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK)
        newGame.addActionListener(menuListener)
        game.add(newGame)

        // random game option
        val randomGame = JMenuItem("Random Game")
        randomGame.mnemonic = KeyEvent.VK_R
        randomGame.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_MASK)
        randomGame.addActionListener(menuListener)
        game.add(randomGame)

        game.addSeparator()

        // quit option
        val quit = JMenuItem("Quit")
        quit.mnemonic = KeyEvent.VK_Q
        quit.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK)
        quit.addActionListener(menuListener)
        game.add(quit)

        // patterns menu
        val patterns = JMenu("Patterns")
        patterns.mnemonic = KeyEvent.VK_P
        menuBar.add(patterns)

        // glider option
        val glider = JMenuItem("Glider")
        glider.mnemonic = KeyEvent.VK_G
        glider.addActionListener(menuListener)
        patterns.add(glider)

        // gosper's glider gun option
        val gosper = JMenuItem("Gosper's Glider Gun")
        gosper.mnemonic = KeyEvent.VK_O
        gosper.addActionListener(menuListener)
        patterns.add(gosper)

        // lightweight spaceship option
        val lightweight = JMenuItem("Lightweight Spaceship")
        lightweight.mnemonic = KeyEvent.VK_L
        lightweight.addActionListener(menuListener)
        patterns.add(lightweight)

        // pentadecathlon option
        val pentadecathlon = JMenuItem("Pentadecathlon")
        pentadecathlon.mnemonic = KeyEvent.VK_P
        pentadecathlon.addActionListener(menuListener)
        patterns.add(pentadecathlon)

        // pulsar option
        val pulsar = JMenuItem("Pulsar")
        pulsar.mnemonic = KeyEvent.VK_U
        pulsar.addActionListener(menuListener)
        patterns.add(pulsar)

        // edit menu
        val edit = JMenu("Edit")
        edit.mnemonic = KeyEvent.VK_E
        menuBar.add(edit)

        // cell color chooser option
        val cellEditor = JMenuItem("Cell Color...")
        cellEditor.mnemonic = KeyEvent.VK_C
        cellEditor.addActionListener(menuListener)
        edit.add(cellEditor)

        // background color chooser option
        val backgroundEditor = JMenuItem("Background Color...")
        backgroundEditor.mnemonic = KeyEvent.VK_B
        backgroundEditor.addActionListener(menuListener)
        edit.add(backgroundEditor)

        edit.addSeparator()

        // speed menu
        val speed = JMenu("Speed")
        speed.mnemonic = KeyEvent.VK_S
        edit.add(speed)

        val group = ButtonGroup()

        // quarter speed
        val quarter = JRadioButtonMenuItem("x0.25")
        quarter.addActionListener(menuListener)
        group.add(quarter)
        speed.add(quarter)

        // half speed
        val half = JRadioButtonMenuItem("x0.5")
        half.addActionListener(menuListener)
        group.add(half)
        speed.add(half)

        // normal speed
        val normal = JRadioButtonMenuItem("x1")
        normal.addActionListener(menuListener)
        normal.isSelected = true
        group.add(normal)
        speed.add(normal)

        // double speed
        val double = JRadioButtonMenuItem("x2")
        double.addActionListener(menuListener)
        group.add(double)
        speed.add(double)

        // quadruple speed
        val quadruple = JRadioButtonMenuItem("x4")
        quadruple.addActionListener(menuListener)
        group.add(quadruple)
        speed.add(quadruple)

        jMenuBar = menuBar
    }

    // listener for menu items
    inner class MenuListener: ActionListener {

        override fun actionPerformed(e: ActionEvent?) {

            when (e!!.actionCommand) {

                // menu items
                "Background Color..." -> PlayField.setBackgroundColor(
                        JColorChooser.showDialog(null, "Background Color", PlayField.getBackgroundColor()))
                "Cell Color..." -> PlayField.setCellColor(
                        JColorChooser.showDialog(null, "Cell Color", PlayField.getCellColor()))
                "New Game" -> PlayField.clearCells()
                "Random Game" -> PlayField.generateCells()
                "Quit" -> System.exit(0)

                // patterns
                "Glider" -> PlayField.addGlider()
                "Gosper's Glider Gun" -> PlayField.addGosper()
                "Lightweight Spaceship" -> PlayField.addLightweight()
                "Pulsar" -> PlayField.addPulsar()
                "Pentadecathlon" -> PlayField.addPentadecathlon()

                // speed
                "x0.25" -> PlayField.setSpeed(200)
                "x0.5" -> PlayField.setSpeed(100)
                "x1" -> PlayField.setSpeed(50)
                "x2" -> PlayField.setSpeed(25)
                "x4" -> PlayField.setSpeed(12)
            }
            Conway.update()
        }
    }
}