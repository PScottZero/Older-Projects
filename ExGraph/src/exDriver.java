import java.awt.Dimension;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * 
 * @author Paul Scott
 * @version April 12th, 2017
 * 
 * This is the driver file for ExGraph
 *
 */
public class exDriver 
{
	//width height and JFrame initialization
	private static final int WIDTH = 530, HEIGHT = 620;
	private static JFrame exFrame;
	
	/**
	 * main method for ExGraph
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException 
	{
		//sets up JFrame
		exFrame = new JFrame("ExGraph Function, Parametric, & Polar");
		exFrame.setIconImage(ImageIO.read(exDriver.class.getResource("icon/sigma.png")));
		exFrame.getContentPane().setPreferredSize(new Dimension(WIDTH,HEIGHT));
		exFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		exFrame.setResizable(false);
		exFrame.add(new exGUI(WIDTH,HEIGHT));
		exFrame.pack();
		exFrame.setVisible(true);
	}
}
