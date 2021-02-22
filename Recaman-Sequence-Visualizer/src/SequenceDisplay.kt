import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import java.awt.Insets
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea

class SequenceDisplay: JPanel() {

    private val textArea = JTextArea()

    init {
        layout = BorderLayout()
        preferredSize = Dimension(0, 60)

        textArea.isEditable = false
        textArea.margin = Insets(8, 8, 8, 8)
        textArea.font = Font("Arial", Font.PLAIN, 20)

        val scroll = JScrollPane(textArea)
        scroll.horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS

        add(scroll, BorderLayout.CENTER)
    }

    fun setText(text: String) { textArea.text = text }
}