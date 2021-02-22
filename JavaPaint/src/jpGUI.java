import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @author Paul Scott
 * @version 7 May 2017
 * 
 * This file creates the components for the GUI of the JavaPaint program.
 *
 */
public class jpGUI extends JPanel
{
	//instance data
	private static final long serialVersionUID = -7580222830839760744L;
	private final int WIDTH, HEIGHT;
	private String bkgPath;
	
	//listeners
	private ButtonListener b;
	private sliderListener l;
	private canvasListener c;
	
	//components
	private Canvas canv;
	private JPanel toolbar;
	private JButton colorChooser;
	private JButton undo;
	private JButton redo;
	private JSlider strokeSize;
	
	/**
	 * default constructor
	 * @param width
	 * @param height
	 * @param path
	 */
	public jpGUI(int width, int height, String path)
	{
		//initializes instance data
		WIDTH = width;
		HEIGHT = height;
		bkgPath = path;
		b = new ButtonListener();
		l = new sliderListener();
		c = new canvasListener();
		
		//loads paint cursor by default
		setPaintCursor("image/paintC.png", 0, 0);
		
		//initializes JPanel
		if (HEIGHT < 640)
			setSize(WIDTH, 640);
		else
			setSize(WIDTH, HEIGHT);
		setLayout(null);
		setBackground(Color.GRAY);
		
		//adds tools to toolbar
		add(addTool("Paint", "image/paint.png", 30, 30, 25, 25));
		add(addTool("Line", "image/line.png", 65, 30, 25, 25));
		add(addTool("Rectangle", "image/rectangle.png", 30, 65, 25, 25));
		add(addTool("Rounded Rectangle", "image/rrectangle.png", 65, 65, 25, 25));
		add(addTool("Triangle", "image/triangle.png", 30, 100, 25, 25));
		add(addTool("Oval", "image/oval.png", 65, 100, 25, 25));
		add(addTool("Rectangle Filled", "image/rectangleF.png", 30, 135, 25, 25));
		add(addTool("Rounded Rectangle Filled", "image/rrectangleF.png", 65, 135, 25, 25));
		add(addTool("Triangle Filled", "image/triangleF.png", 30, 170, 25, 25));
		add(addTool("Oval Filled", "image/ovalF.png", 65, 170, 25, 25));
		add(addTool("Erase", "image/erase.png", 30, 205, 25, 25));
		add(addTool("Rectangle Erase", "image/rectangleErase.png", 65, 205, 25, 25));
		add(addTool("Open", "image/open.png", 30, 530, 25, 25));
		add(addTool("Save", "image/save.png", 65, 530, 25, 25));
		
		//adds undo button
		undo = new JButton("Undo");
		undo.setBounds(30, 240, 25, 25);
		undo.setBorder(null);
		undo.setHorizontalTextPosition(SwingConstants.CENTER);
		undo.setForeground(new Color(0, 0, 0, 0));
		undo.setBackground(Color.WHITE);
		undo.setFocusPainted(false);
		undo.addActionListener(b);
		add(undo);
		
		//adds redo button
		redo = new JButton("Redo");
		redo.setBounds(65, 240, 25, 25);
		redo.setBorder(null);
		redo.setHorizontalTextPosition(SwingConstants.CENTER);
		redo.setForeground(new Color(0, 0, 0, 0));
		redo.setBackground(Color.WHITE);
		redo.setFocusPainted(false);
		redo.addActionListener(b);
		add(redo);
		
		//adds color chooser
		colorChooser = new JButton("Color Chooser");
		colorChooser.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		colorChooser.setBounds(25, 565, 70, 50);
		colorChooser.setBackground(Color.BLACK);
		colorChooser.setForeground(new Color(0,0,0,0));
		colorChooser.setFocusPainted(false);
		colorChooser.addActionListener(b);
		add(colorChooser);
		
		//adds stroke size slider
		strokeSize = new JSlider(JSlider.VERTICAL, 1, 25, 4);
		strokeSize.setMajorTickSpacing(4);
		strokeSize.setMinorTickSpacing(1);
		strokeSize.setPaintTicks(true);
		strokeSize.setPaintLabels(true);
		strokeSize.setFont(new Font("Courier New", Font.BOLD, 12));
		strokeSize.addChangeListener(l);
		strokeSize.setBounds(30, 275, 45, 245);
		strokeSize.setBackground(Color.WHITE);
		strokeSize.setForeground(Color.BLACK);
		add(strokeSize);
		
		//adds toolbar background
		toolbar = new JPanel();
		toolbar.setBounds(20, 20, 80, 600);
		toolbar.setBackground(Color.WHITE);
		add(toolbar);
		
		//adds canvas
		canv = new Canvas(WIDTH - 140, HEIGHT - 40);
		canv.setLocation(120, 20);
		canv.addMouseListener(c);
		add(canv);
		
		//initializes undo/redo images
		setUndoRedoImage();
	}
	
	/**
	 * initializes toolbar button
	 * @param name
	 * @param img
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return JButton of tool
	 */
	public JButton addTool(String name, String img, int x, int y, int width, int height)
	{
		//initializes JButton
		JButton btn = new JButton(name);
		btn.setBounds(x, y, width, height);
		btn.setBorder(null);
		btn.setHorizontalTextPosition(SwingConstants.CENTER);
		btn.setForeground(new Color(0, 0, 0, 0));
		btn.setBackground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.addActionListener(b);
		
		//sets tool image
		try {
			Image icon = ImageIO.read(getClass().getResource(img));
			btn.setIcon(new ImageIcon(icon));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//returns JButton
		return btn;
	}
	
	/**
	 * sets mouse cursor
	 * @param img
	 * @param x
	 * @param y
	 */
	public void setPaintCursor(String img, int x, int y)
	{
		try {
			Toolkit t = Toolkit.getDefaultToolkit();
			Image sqCursor = ImageIO.read(getClass().getResource(img));
			Cursor cur = t.createCustomCursor(sqCursor, new Point(x, y), "cursor");
			setCursor(cur);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * sets image for the redo and undo images
	 */
	public void setUndoRedoImage()
	{
		//sets which images will be used
		String undoImg = "image/undo.png";
		String redoImg = "image/redoNull.png";
		
		if (canv.getDataSize() == 0)
			undoImg = "image/undoNull.png";
		
		if (canv.getStackSize() != 0)
			redoImg = "image/redo.png";
		
		//sets undo image
		try {
			Image iconU = ImageIO.read(getClass().getResource(undoImg));
			undo.setIcon(new ImageIcon(iconU));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//sets redo image
		try {
			Image iconR = ImageIO.read(getClass().getResource(redoImg));
			redo.setIcon(new ImageIcon(iconR));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * paints background and other features onto GUI
	 */
	public void paintComponent(Graphics g)
	{	
		super.paintComponent(g);
		
		//chooses default background unless specified otherwise in config.txt
		try 
		{
			//custom image
			Image img = ImageIO.read(new File(bkgPath));
			img = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
			g.drawImage(img, 0, 0, null);
		} 
		catch (IOException e) 
		{
			//default image
			try {
				Image img = ImageIO.read(getClass().getResource("image/back.png"));
				img = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
				g.drawImage(img, 0, 0, null);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		//shadows behind canvas and toolbar
		g.setColor(Color.BLACK);
		g.fillRect(15, 25, 80, toolbar.getHeight());
		g.fillRect(115, 25, canv.getWidth(), canv.getHeight());
	}
	
	//Action Listener for toolbar buttons
	public class ButtonListener implements ActionListener
	{
		/**
		 * checks what tool has been selected, then sets cursor and mode
		 * @param arg0
		 */
		public void actionPerformed(ActionEvent arg0) 
		{		
			
			//checks if the Rectangle tool is selected
			if (arg0.getActionCommand().equals("Rectangle"))
			{
				setPaintCursor("image/rectangleC.png", 0, 0);
				canv.setMode("rectangle");
			}
			
			//checks if the Oval tool is selected
			else if (arg0.getActionCommand().equals("Oval"))
			{
				setPaintCursor("image/ovalC.png", 0, 0);
				canv.setMode("oval");
			}
			
			//checks if the Paint tool is selected
			else if (arg0.getActionCommand().equals("Paint"))
			{
				setPaintCursor("image/paintC.png", 0, 0);
				canv.setMode("paint");
			}
			
			//checks if the Line tool is selected
			else if (arg0.getActionCommand().equals("Line"))
			{
				setPaintCursor("image/lineC.png", 0, 0);
				canv.setMode("line");
			}
			
			//checks if the Triangle tool is selected
			else if (arg0.getActionCommand().equals("Triangle"))
			{
				setPaintCursor("image/triangleC.png", 0, 0);
				canv.setMode("triangle");
			}
			
			//checks if the Rounded Rectangle tool is selected
			else if (arg0.getActionCommand().equals("Rounded Rectangle"))
			{
				setPaintCursor("image/rrectangleC.png", 0, 0);
				canv.setMode("rrectangle");
			}
			
			//checks if the Filled Rectangle tool is selected
			else if (arg0.getActionCommand().equals("Rectangle Filled"))
			{
				setPaintCursor("image/rectangleFC.png", 0, 0);
				canv.setMode("rectangleF");
			}
			
			//checks if the Filled Oval tool is selected
			else if (arg0.getActionCommand().equals("Oval Filled"))
			{
				setPaintCursor("image/ovalFC.png", 0, 0);
				canv.setMode("ovalF");
			}
			
			//checks if the Filled Triangle tool is selected
			else if (arg0.getActionCommand().equals("Triangle Filled"))
			{
				setPaintCursor("image/triangleFC.png", 0, 0);
				canv.setMode("triangleF");
			}
			
			//checks if the Filled Rounded Rectangle tool is selected
			else if (arg0.getActionCommand().equals("Rounded Rectangle Filled"))
			{
				setPaintCursor("image/rrectangleFC.png", 0, 0);
				canv.setMode("rrectangleF");
			}
			
			//checks if the Erase tool is selected
			else if (arg0.getActionCommand().equals("Erase"))
			{
				setPaintCursor("image/eraseC.png", 10, 10);
				canv.setMode("erase");
			}
			
			//checks if the Rectangular Eraser tool is selected
			else if (arg0.getActionCommand().equals("Rectangle Erase"))
			{
				setPaintCursor("image/rectangleEraseC.png", 0, 0);
				canv.setMode("rectangleErase");
			}
			
			//checks if the Undo button is selected
			else if (arg0.getActionCommand().equals("Undo"))
			{
				canv.undo();
				setUndoRedoImage();
			}
			
			//checks if the Redo button is selected
			else if (arg0.getActionCommand().equals("Redo"))
			{
				canv.redo();
				setUndoRedoImage();
			}
			
			//checks if the Save button is selected
			else if (arg0.getActionCommand().equals("Save"))
			{
				//initializes file chooser
				JFileChooser choose = new JFileChooser(System.getProperty("user.home") + "/desktop");
				int val = choose.showDialog(null, "Save");
				String path = null;
				
				//checks to see if user did not cancel save
				if (val == JFileChooser.APPROVE_OPTION)
				{
					//gets path of save
					path = choose.getSelectedFile().getPath();
					BufferedImage img = new BufferedImage(canv.getWidth(), canv.getHeight(), BufferedImage.TYPE_INT_RGB);
					canv.paint(img.createGraphics());
					File imgFile = new File(path + ".png");
					
					//saves image
					try {
						imgFile.createNewFile();
						ImageIO.write(img, "png", imgFile);
					} catch (IOException e) {
						e.printStackTrace();
					}	
				}
			}
			
			//checks if the Open button is selected
			else if (arg0.getActionCommand().equals("Open"))
			{
				//initializes file chooser
				JFileChooser choose = new JFileChooser(System.getProperty("user.home") + "/desktop");
				int val = choose.showDialog(null, "Open");
				String path = null;
				
				//checks to see if user did not cancel open
				if (val == JFileChooser.APPROVE_OPTION)
				{
					//opens image
					path = choose.getSelectedFile().getPath();
					canv.setBackground(path);
				}
			}			
			
			//checks if the Color Chooser is selected
			else if (arg0.getActionCommand().equals("Color Chooser"))
			{
				Color c = JColorChooser.showDialog(null, "Choose A Color", null);
				canv.setColor(c);
				colorChooser.setBackground(c);
			}
		}	
	}
	
	//Change Listener for stroke size slider
	public class sliderListener implements ChangeListener
	{
		/**
		 * updates stroke size after slider is moved
		 * @param arg0
		 */
		public void stateChanged(ChangeEvent arg0) 
		{
			canv.setStroke(strokeSize.getValue());
		}
	}
	
	//Mouse Adapter for canvas
	public class canvasListener extends MouseAdapter
	{
		/**
		 * Changes undo and redo images if canvas is drawn on
		 * @param arg0
		 */
		public void mousePressed(MouseEvent arg0) 
		{
			setUndoRedoImage();
		}	
	}
}
