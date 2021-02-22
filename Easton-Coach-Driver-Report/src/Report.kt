/**
 *
 * @author Paul Scott
 * @version 11 August 2018
 *
 * This class is the gui for the program
 *
 */

import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.filechooser.FileNameExtensionFilter

class Report: JFrame() {

    // static methods and variables
    companion object {
        val reportArea = JTextArea()
        val fileLabel = JLabel("No File Selected")

        @JvmStatic fun main(args: Array<String>) { Report() }
    }

    // frame initializer
    init {

        // frame customization
        title = "Easton Coach Driver Report"
        iconImage = ImageIO.read(Report::class.java.getResource("img/icon.png")).getScaledInstance(
                32, 32, Image.SCALE_SMOOTH)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        preferredSize = Dimension(700, 700)
        layout = BorderLayout()

        // side panel which holds buttons and logo
        val topPanel = JPanel()
        topPanel.background = Color(220, 220, 220)
        topPanel.preferredSize = Dimension(0, 60)
        topPanel.layout = BorderLayout()
        topPanel.border = EmptyBorder(10, 10, 10, 10)

        // open button
        val open = JButton("Open Asset...")
        open.addActionListener(ButtonListener())
        open.preferredSize = Dimension(120, 30)
        topPanel.add(open, BorderLayout.WEST)

        // file directory label
        fileLabel.font = Font("Arial", Font.PLAIN, 20)
        topPanel.add(fileLabel, BorderLayout.EAST)

        // adds top panel to gui
        add(topPanel, BorderLayout.NORTH)

        // container that holds report scroll pane and text area
        val reportContainer = JPanel()
        reportContainer.background = Color(51, 102, 153)
        reportContainer.layout = BorderLayout()
        reportContainer.border = EmptyBorder(10, 10, 10, 10)

        // report text area
        reportArea.margin = Insets(20, 20, 20, 20)
        reportArea.isEditable = false
        reportArea.lineWrap = true
        reportArea.wrapStyleWord = true
        reportArea.font = Font("Arial", Font.PLAIN, 20)
        reportArea.text = "No report generated yet..."

        // report scroll pane
        val scrollPane = JScrollPane(reportArea)
        add(scrollPane, BorderLayout.CENTER)

        // adds report container to frame
        reportContainer.add(scrollPane, BorderLayout.CENTER)
        add(reportContainer, BorderLayout.CENTER)

        // makes frame visible
        pack()
        isVisible = true
    }

    inner class ButtonListener: ActionListener {

        override fun actionPerformed(e: ActionEvent?) {

            // creates file chooser with csv filter
            val chooser = JFileChooser(System.getProperty("user.home") + "/Downloads")
            val filter = FileNameExtensionFilter("Comma-Separated Values File (*.csv)", "csv")
            chooser.fileFilter = filter

            val option = chooser.showOpenDialog(null)

            // if open dialog is confirmed
            if (option == JFileChooser.APPROVE_OPTION) {
                val directory = chooser.selectedFile.toString()
                fileLabel.text = directory
                if (directory == "No File Selected") JOptionPane.showMessageDialog(null,
                        "No file has been selected", "Error", JOptionPane.ERROR_MESSAGE)
                ReportParse(directory)
            }
        }
    }
}