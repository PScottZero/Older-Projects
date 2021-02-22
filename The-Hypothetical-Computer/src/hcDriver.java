import java.awt.Dimension;

import javax.swing.JFrame;

public class hcDriver 
{
	public static void main(String[] args) 
	{
		JFrame hcFrame = new JFrame("The Hypothetical Computer");
		hcFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hcFrame.getContentPane().setPreferredSize(new Dimension(400, 500));
		hcFrame.setResizable(false);
		hcFrame.add(new SwitchBoard());
		hcFrame.pack();
		hcFrame.setVisible(true);
	}
}
