import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * 
 * @author Paul Scott
 * @version 7 May 2017
 * 
 * This is the driver file for the JavaPaint program.
 *
 */
public class jpDriver 
{
	//instance data
	private static JFrame jpFrame;
	private static int WIDTH = 940, HEIGHT = 640;
	private static String path = "";
	
	/**
	 * main method for JavaPaint
	 * @param args
	 */
	public static void main(String[] args)
	{
		//attempts to open config.txt file
		try 
		{
			//opens config.txt
			Scanner input = new Scanner(new File("config.txt"));
			while (input.hasNextLine())
			{
				String line = input.nextLine();
				
				//gets size of canvas
				if (line.contains("Size: "))
				{
					String[] size = line.split(" ");
					WIDTH = Integer.parseInt(size[1]) + 140;
					HEIGHT = Integer.parseInt(size[2]) + 40;
				}
				
				//gets background image
				else if (line.contains("Background: "))
				{
					String[] bkg = line.split(" ");
					
					if (bkg.length > 1)
						path = bkg[1];
					else
						path = "";
				}
			}
			input.close();
		} 
		
		//creates config.txt file if it does not exist
		catch (FileNotFoundException e) 
		{		
			try {
				PrintWriter writer = new PrintWriter("config.txt", "UTF-8");
				writer.write("#This is the configuration file for Java Paint.\n");
				writer.write("#Here you can set the background and size for the program.\n\n");
				writer.write("Size: 800 600\n");
				writer.write("Background: ");
				writer.close();	
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}

		//creates window of program
		jpFrame = new JFrame("Java Paint Ver 1.00 By Paul Scott");
		jpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//sets icon for program
		try {
			BufferedImage icon = ImageIO.read(jpDriver.class.getResource("image/icon.png"));
			jpFrame.setIconImage(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//restricts height if canvas height is smaller than 640px
		if (HEIGHT < 640)
			jpFrame.getContentPane().setPreferredSize(new Dimension(WIDTH, 640));
		else
			jpFrame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		//adds components to GUI and displays window
		jpFrame.setResizable(false);
		jpFrame.add(new jpGUI(WIDTH, HEIGHT, path));
		jpFrame.pack();
		jpFrame.setVisible(true);
	}
}
