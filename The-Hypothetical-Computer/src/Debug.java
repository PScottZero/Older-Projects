import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Debug extends JPanel
{
	private static final long serialVersionUID = -5499733977219779507L;
	private JTextPane memoryMap;
	private JButton save, load;
	
	public Debug()
	{
		setLayout(null);
		setSize(600,500);
		setBackground(Color.LIGHT_GRAY);
		
		memoryMap = new JTextPane();
		memoryMap.setFont(new Font("Courier", Font.PLAIN, 16));
		memoryMap.setBounds(0, 30, 600, 470);
		memoryMap.setEditable(false);
		memoryMap.setMargin(new Insets(40,0,0,0));
		
		StyledDocument doc = memoryMap.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		add(memoryMap);
		
		save = new JButton("Save Memory");
		save.setBounds(5,5,90,20);
		save.setMargin(new Insets(0,0,0,0));
		add(save);
	}
	
	public void setText(String text)
	{
		memoryMap.setText(text);
	}
}
