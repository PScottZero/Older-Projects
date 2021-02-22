/**
 * @author Paul Scott
 * @version 21 February 2019
 *
 * class that initializes window
 * and ui elements
 */

import themes.*
import data.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.KeyStroke.getKeyStroke

class TetriK: JFrame() {

    // instance data
    private val scoreboard = Scoreboard()
    private val gamePanel = JPanel()
    private val menu = JMenuBar()
    private val game = JMenu(Strings.GAME_MENU)
    private val themeChooser = JMenu(Strings.THEME_MENU)

    /**
     * initializer
     */
    init {

        // window configuration
        title = Strings.WINDOW_TITLE
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane.preferredSize = Constants.WINDOW_SIZE
        isResizable = false

        // set default theme for ui
        theme = Default()

        // add ui components to window
        addMenu()
        addIcons()
        addGamePanel()

        // make ui visible
        pack()
        isVisible = true
    }

    /**
     * adds window icons of multiple sizes
     */
    private fun addIcons() {
        val icons = ArrayList<Image>()
        icons.add(ImageIO.read(javaClass.getResource("img/icon_16.png"))) // 16x16 px
        icons.add(ImageIO.read(javaClass.getResource("img/icon_32.png"))) // 32x32 px
        icons.add(ImageIO.read(javaClass.getResource("img/icon_64.png"))) // 64x64 px
        icons.add(ImageIO.read(javaClass.getResource("img/icon_128.png"))) // 128x128 px
        iconImages = icons
    }

    /**
     * adds menu bar to window
     */
    private fun addMenu() {

        // action listener
        val listener = MenuListener()

        // menu styles
        UIManager.put("MenuItem.font", Constants.MENU_FONT)
        UIManager.put("MenuItem.foreground", Color.BLACK)
        UIManager.put("RadioButtonMenuItem.font", Constants.MENU_FONT)
        UIManager.put("RadioButtonMenuItem.foreground", Color.BLACK)
        menu.background = theme.menu
        menu.isBorderPainted = false

        // game menu
        game.mnemonic = KeyEvent.VK_G
        game.foreground = theme.menuFont
        game.font = Constants.MENU_FONT
        menu.add(game)

        // new game option
        val newGame = JMenuItem(Strings.NEWGAME_OPTION)
        newGame.mnemonic = KeyEvent.VK_N
        newGame.accelerator = getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK)
        newGame.addActionListener(listener)
        game.add(newGame)

        // pause option
        val pause = JMenuItem(Strings.PAUSE_OPTION)
        pause.mnemonic = KeyEvent.VK_P
        pause.accelerator = getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK)
        pause.addActionListener(listener)
        game.add(pause)

        game.addSeparator()

        // quit option
        val quit = JMenuItem(Strings.QUIT_OPTION)
        quit.mnemonic = KeyEvent.VK_Q
        quit.accelerator = getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK)
        quit.addActionListener(listener)
        game.add(quit)

        // theme menu
        themeChooser.foreground = theme.menuFont
        themeChooser.mnemonic = KeyEvent.VK_T
        themeChooser.font = Constants.MENU_FONT
        addThemes(themeChooser, listener)
        menu.add(themeChooser)

        // set window menu
        jMenuBar = menu
    }

    /**
     * adds theme options to theme menu
     * @param menu - themes menu
     * @param listener - action listener
     */
    private fun addThemes(menu: JMenu, listener: MenuListener) {

        // button group for radio buttons
        val buttonGroup = ButtonGroup()

        // deep blue themes
        val deepblue = JRadioButtonMenuItem(Strings.DEEP_BLUE_THEME)
        deepblue.addActionListener(listener)
        buttonGroup.add(deepblue)
        menu.add(deepblue)

        // default theme
        val default = JRadioButtonMenuItem(Strings.DEFAULT_THEME)
        default.addActionListener(listener)
        default.isSelected = true
        buttonGroup.add(default)
        menu.add(default)

        // gameboy theme
        val gameboy = JRadioButtonMenuItem(Strings.GAMEBOY_THEME)
        gameboy.addActionListener(listener)
        buttonGroup.add(gameboy)
        menu.add(gameboy)

        // kotlin theme
        val kotlin = JRadioButtonMenuItem(Strings.KOTLIN_THEME)
        kotlin.addActionListener(listener)
        buttonGroup.add(kotlin)
        menu.add(kotlin)

        // monochrome theme
        val monochrome = JRadioButtonMenuItem(Strings.MONOCHROME_THEME)
        monochrome.addActionListener(listener)
        buttonGroup.add(monochrome)
        menu.add(monochrome)

        // pastel theme
        val pastel = JRadioButtonMenuItem(Strings.PASTEL_THEME)
        pastel.addActionListener(listener)
        buttonGroup.add(pastel)
        menu.add(pastel)

        // zen theme
        val zen = JRadioButtonMenuItem(Strings.ZEN_THEME)
        zen.addActionListener(listener)
        buttonGroup.add(zen)
        menu.add(zen)
    }

    /**
     * adds games panel to window
     */
    private fun addGamePanel() {

        // panel configuration
        gamePanel.size = Constants.WINDOW_SIZE
        gamePanel.background = theme.window
        gamePanel.layout = null

        // playfield
        field.location = Point(50, 50)
        gamePanel.add(field)

        // scoreboard
        scoreboard.location = Point(400, 50)
        gamePanel.add(scoreboard)

        // next piece panel
        nextpiece.location = Point(400, 200)
        gamePanel.add(nextpiece)
        add(gamePanel)
    }

    // action listener
    inner class MenuListener: ActionListener {

        /**
         * responds to menu input
         * @param e - action event
         */
        override fun actionPerformed(e: ActionEvent?) {
            when (e!!.actionCommand) {

                // quit program
                Strings.QUIT_OPTION -> {
                    System.exit(0)
                }

                // new game
                Strings.NEWGAME_OPTION -> {
                    GameLoop.timer.cancel()
                    GameLoop.isRunning = false

                    isStarted = true

                    PlayField.restart()
                    Scoreboard.score.text = Strings.SCORE_ZERO

                    val loop = GameLoop()
                    Thread(loop).start()
                }

                Strings.PAUSE_OPTION -> {
                    GameLoop.isPaused = !GameLoop.isPaused
                }

                // themes
                Strings.DEEP_BLUE_THEME -> updateTheme(DeepBlue())
                Strings.DEFAULT_THEME -> updateTheme(Default())
                Strings.GAMEBOY_THEME -> updateTheme(GameBoy())
                Strings.KOTLIN_THEME -> updateTheme(Kotlin())
                Strings.MONOCHROME_THEME -> updateTheme(Monochrome())
                Strings.PASTEL_THEME -> updateTheme(Pastel())
                Strings.ZEN_THEME -> updateTheme(Zen())
            }
        }
    }

    /**
     * update ui when theme is changed
     */
    fun updateTheme(newTheme: Theme) {
        theme = newTheme
        field.updateColors()
        nextpiece.updateColors()
        scoreboard.updateColors()
        gamePanel.background = theme.window
        menu.background = theme.menu
        game.foreground = theme.menuFont
        themeChooser.foreground = theme.menuFont
    }

    companion object {

        // static variables
        var isStarted = false
        var theme: Theme = Default()
        private val field = PlayField()
        private val nextpiece = NextBlock()

        /**
         * main method to start program
         * @param args - string array
         */
        @JvmStatic fun main(args: Array<String>) { TetriK() }

        /**
         * repaint static ui components
         */
        fun repaint() {
            field.repaint()
            nextpiece.repaint()
        }
    }
}