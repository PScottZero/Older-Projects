/**
 *
 * @author Paul J. Scott
 * @version 23 May 2018
 *
 * Apogee Artist Painting Program 1.00
 *
 */

import data.Colors
import data.Constants
import data.Strings
import fileio.Open
import fileio.Save
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter
import java.util.ArrayList
import java.awt.Image
import javax.swing.KeyStroke.getKeyStroke


class Apogee: JFrame() {

    // listener for menu bar
    private val menuListener = MenuListener()
    private var isSaved = false
    private var dir = ""

    // static object
    companion object {

        // instance data
        private val canvas = Canvas()
        private val shapeTools = OptionBar()
        private val undo = JMenuItem(Strings.MENU_UNDO)
        private val redo = JMenuItem(Strings.MENU_REDO)

        @JvmStatic fun main(args: Array<String>) { Apogee() }

        // sets shape mode for shape toolbar
        fun setToolMode(mode: String) { shapeTools.setMode(mode) }

        // updates undo and redo buttons
        fun update() {
            undo.isEnabled = Canvas.updateUndo()
            redo.isEnabled = Canvas.updateRedo()
        }
    }

    // initializer
    init {
        // create frame
        preferredSize = Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        title = Strings.APP_TITLE
        layout = BorderLayout()

        // set icons
        addIcons()

        // add menu bar
        addMenu()

        // finalize frame
        add(shapeTools, BorderLayout.NORTH)
        add(Toolbar(), BorderLayout.WEST)

        val container = JPanel()
        container.layout = BoxLayout(container, BoxLayout.Y_AXIS)
        container.background = Colors.DARKER_GRAY
        container.add(Box.createVerticalGlue())
        container.add(canvas)
        container.add(Box.createVerticalGlue())
        add(container, BorderLayout.CENTER)

        pack()
        isVisible = true

        // set initial shape tool
        setToolMode(Strings.MODE_BRUSH)
    }

    private fun addIcons() {
        val icons = ArrayList<Image>()
        icons.add(ImageIO.read(javaClass.getResource("img/icon/icon_16.png"))) // 16x16 px
        icons.add(ImageIO.read(javaClass.getResource("img/icon/icon_32.png"))) // 32x32 px
        icons.add(ImageIO.read(javaClass.getResource("img/icon/icon_64.png"))) // 64x64 px
        icons.add(ImageIO.read(javaClass.getResource("img/icon/icon_128.png"))) // 128x128 px
        iconImages = icons
    }

    // adds menu to frame
    private fun addMenu() {

        // custom font
        val font = Font("Arial", Font.PLAIN, 16)
        UIManager.put("Menu.font", font)
        UIManager.put("Menu.foreground", Colors.LIGHTER_GRAY)
        UIManager.put("MenuItem.font", font)

        // menu bar
        val menu = JMenuBar()
        menu.background = Colors.LIGHT_GRAY
        menu.isBorderPainted = false

        // file menu
        val file = JMenu(Strings.MENU_FILE)
        file.mnemonic = KeyEvent.VK_F

        // open file
        val open = JMenuItem(Strings.MENU_OPEN)
        open.mnemonic = KeyEvent.VK_O
        open.accelerator = getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK)
        open.addActionListener(menuListener)
        file.add(open)

        // save file
        val save = JMenuItem(Strings.MENU_SAVE)
        save.mnemonic = KeyEvent.VK_S
        save.accelerator = getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK)
        save.addActionListener(menuListener)
        file.add(save)

        // save file as
        val saveAs = JMenuItem(Strings.MENU_SAVE_AS)
        saveAs.mnemonic = KeyEvent.VK_A
        saveAs.addActionListener(menuListener)
        file.add(saveAs)

        // save file as png
        val savePNG = JMenuItem(Strings.MENU_SAVE_PNG)
        savePNG.mnemonic = KeyEvent.VK_P
        savePNG.addActionListener(menuListener)
        file.add(savePNG)

        file.addSeparator()

        // quit
        val quit = JMenuItem(Strings.MENU_QUIT)
        quit.mnemonic = KeyEvent.VK_Q
        quit.addActionListener(menuListener)
        file.add(quit)

        // edit menu
        val edit = JMenu(Strings.MENU_EDIT)
        edit.mnemonic = KeyEvent.VK_E

        // undo
        undo.mnemonic = KeyEvent.VK_U
        undo.accelerator = getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK)
        undo.isEnabled = false
        undo.font = font
        undo.addActionListener(menuListener)
        edit.add(undo)

        // redo
        redo.mnemonic = KeyEvent.VK_R
        redo.accelerator = getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK)
        redo.isEnabled = false
        redo.font = font
        redo.addActionListener(menuListener)
        edit.add(redo)

        edit.addSeparator()

        // change canvas size
        val canvasSize = JMenuItem(Strings.MENU_CANVAS)
        canvasSize.mnemonic = KeyEvent.VK_C
        canvasSize.addActionListener(menuListener)
        edit.add(canvasSize)

        menu.add(file)
        menu.add(edit)

        jMenuBar = menu
    }

    // listener for menu bar
    inner class MenuListener: ActionListener {

        override fun actionPerformed(e: ActionEvent?) {

            when (e!!.actionCommand) {

                // undo
                Strings.MENU_UNDO -> canvas.undo()

                // redo
                Strings.MENU_REDO -> canvas.redo()

                // open
                Strings.MENU_OPEN -> {

                    // open dialog
                    val chooser = JFileChooser(System.getProperty("user.home"))
                    val filter = FileNameExtensionFilter("Apogee Artist Files (*.art)", "art")
                    chooser.fileFilter = filter
                    val option = chooser.showOpenDialog(null)

                    if (option == JFileChooser.APPROVE_OPTION) {

                        // opens objects
                        dir = chooser.selectedFile.toString()
                        isSaved = true
                        Canvas.setObjects(Open(dir).getObjects())
                    }
                    canvas.repaint()
                }

                // save
                Strings.MENU_SAVE -> {
                    if (isSaved) { Save(dir, Canvas.getObjects()) }
                    else { menuListener.actionPerformed(ActionEvent(this, 0, Strings.MENU_SAVE_AS)) }
                }

                // save as
                Strings.MENU_SAVE_AS -> {

                    // save dialog
                    val chooser = JFileChooser(System.getProperty("user.home"))
                    val option = chooser.showSaveDialog(null)

                    if (option == JFileChooser.APPROVE_OPTION) {

                        // saves objects
                        dir = chooser.selectedFile.toString() + ".art"
                        isSaved = true
                        Save(dir, Canvas.getObjects())
                    }
                }

                // save as png
                Strings.MENU_SAVE_PNG -> {

                    // save dialog
                    val chooser = JFileChooser()
                    val option = chooser.showSaveDialog(null)

                    if (option == JFileChooser.APPROVE_OPTION) {

                        // creates image from canvas
                        val imageWidth = canvas.getDimension().width
                        val imageHeight = canvas.getDimension().height
                        val image = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB)
                        val graphics = image.createGraphics()

                        canvas.paint(graphics)
                        graphics.dispose()
                        ImageIO.write(image, "png", File(chooser.selectedFile.toString() + ".png"))
                    }
                }

                // canvas size
                Strings.MENU_CANVAS -> {

                    // opens dialog to change canvas size
                    val widthField = JTextField(canvas.getDimension().width.toString())
                    widthField.size = Dimension(Constants.OPTION_TEXT_WIDTH, Constants.OPTION_TEXT_HEIGHT)
                    val heightField = JTextField(canvas.getDimension().height.toString())
                    heightField.size = Dimension(Constants.OPTION_TEXT_WIDTH, Constants.OPTION_TEXT_HEIGHT)
                    val inputs = arrayOf(Strings.OPTION_WIDTH, widthField, Strings.OPTION_HEIGHT, heightField)
                    val option = JOptionPane.showConfirmDialog(null,
                            inputs, Strings.OPTION_TITLE, JOptionPane.OK_CANCEL_OPTION)

                    if (option == JOptionPane.OK_OPTION) {
                        canvas.setDimension(Dimension(widthField.text.toInt(),
                                heightField.text.toInt()))
                    }
                }

                // quit
                Strings.MENU_QUIT -> System.exit(0)
            }
            update()
        }
    }
}