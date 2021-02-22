import java.awt.Dimension;

import javax.swing.JFrame;

public class metarDriver 
{
	private static JFrame metarFrame;
	public static void main(String[] args) 
	{
		metarFrame = new JFrame("Airport METAR Decoder");
		metarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		metarFrame.setResizable(false);
		metarFrame.getContentPane().setPreferredSize(new Dimension(500,400));
		metarFrame.add(new metarGUI());
		metarFrame.pack();
		metarFrame.setVisible(true);
	}
}
