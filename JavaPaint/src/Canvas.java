import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 
 * @author Paul Scott
 * @version 7 May 2017
 * 
 * This is the canvas for JavaPaint where all elements are drawn.
 *
 */
public class Canvas extends JLabel
{
	//instance data
	private static final long serialVersionUID = -8887889886609855007L;
	private final int WIDTH, HEIGHT;
	private int currentStroke;
	private Color currentColor;
	private String mode, backPath = null;
	
	//PaintData Array Lists
	private ArrayList<PaintData> data;
	private Stack<PaintData> undo;
	
	//listeners
	private MouseList m;
	
	//components
	private JLabel background;
	
	/**
	 * default constructor
	 * @param width
	 * @param height
	 */
	public Canvas(int width, int height)
	{
		//initializes instance data
		WIDTH = width;
		HEIGHT = height;
		currentColor = Color.BLACK;
		data = new ArrayList<PaintData>();
		undo = new Stack<PaintData>();	
		m = new MouseList();
		mode = "paint";
		currentStroke = 4;
		
		//sets dimensions for canvas
		setSize(WIDTH, HEIGHT);
		setLayout(null);
		setBackground(Color.WHITE);
		addMouseListener(m);
		addMouseMotionListener(m);
		setOpaque(true);
		
		//initializes background so an image can be opened
		background = new JLabel();
		background.setBounds(0, 0, getWidth(), getHeight());
	}
	
	/**
	 * paintComponent that draws all paint data
	 * @param g
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		//paint background image if opened
		if (backPath != null)
		{
			Image img;
			try {
				img = ImageIO.read(new File(backPath));
				img = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
				g2.drawImage(img, 0, 0, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//paints all shapes and strokes
		for (int a = 0; a < data.size(); a++)
		{
			//sets color and stroke size
			PaintData current = data.get(a);
			g2.setColor(current.getColor());
			g2.setStroke(new BasicStroke(current.getStroke()));
			
			//draws rounded rectangle
			if (data.get(a) instanceof RoundRectangle)
			{
				RoundRectangle r = (RoundRectangle) current;
				
				//filled
				if (r.isFilled())
				{
					g2.fillRoundRect((int)r.getRectangle().getBounds().getX(), (int)r.getRectangle().getBounds().getY(), 
							(int)r.getRectangle().getBounds().getWidth(), (int)r.getRectangle().getBounds().getHeight(),
							r.getRadius(), r.getRadius());
				}
				
				//not filled
				else
				{
					g2.drawRoundRect((int)r.getRectangle().getBounds().getX(), (int)r.getRectangle().getBounds().getY(), 
							(int)r.getRectangle().getBounds().getWidth(), (int)r.getRectangle().getBounds().getHeight(),
							r.getRadius(), r.getRadius());
				}
			}
			
			//draws rectangle
			else if (data.get(a) instanceof Rectangle)
			{
				Rectangle r = (Rectangle) current;
				
				//filled
				if (r.isFilled())
					g2.fillPolygon(r.getRectangle());
				
				//not filled
				else
					g2.drawPolygon(r.getRectangle());
			}
			
			//draws triangle
			else if (data.get(a) instanceof Triangle)
			{
				Triangle r = (Triangle) current;
				
				//filled
				if (r.isFilled())
					g2.fillPolygon(r.getTriangle());
				
				//not filled
				else
					g2.drawPolygon(r.getTriangle());
			}
			
			//draws oval
			else if (data.get(a) instanceof Oval)
			{
				Oval o = (Oval) current;
				int x, y, w, h;
				
				//sets dimensions of oval
				if (o.getWidth() < 0)
				{
					x = ((int)o.getPoint().getX()) + o.getWidth();
					w = Math.abs(o.getWidth());
				}
				else
				{
					x = (int)o.getPoint().getX();
					w = o.getWidth();
				}
				if (o.getHeight() < 0)
				{
					y = ((int)o.getPoint().getY()) + o.getHeight();
					h = Math.abs(o.getHeight());
				}
				else
				{
					y = (int)o.getPoint().getY();
					h = o.getHeight();
				}
				
				//filled
				if (o.isFilled())
					g2.fillOval(x, y, w, h);
				
				//not filled
				else
					g2.drawOval(x, y, w, h);
			}
			
			//draws line
			else if (data.get(a) instanceof Line)
			{
				Line l = (Line) current;
				g2.drawLine((int)l.getPoint().getX(), (int)l.getPoint().getY(), (int)l.getEnd().getX(), (int)l.getEnd().getY());
			}
			
			//draws stroke
			else
			{
				g2.setStroke(new BasicStroke(current.getStroke(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				if (a > 0 && current.getPoint().getX() != -1 && data.get(a - 1).getPoint().getX() != -1)
				{
					PaintData prev = data.get(a - 1);
					g2.drawLine((int)current.getPoint().getX(), (int)current.getPoint().getY(), (int)prev.getPoint().getX(), (int)prev.getPoint().getY());
				}
				else
				{
					g2.fillOval((int)(current.getPoint().getX() - (current.getStroke() / 2)), (int)(current.getPoint().getY() - (current.getStroke() / 2)),
							current.getStroke(), current.getStroke());
				}
			}
		}
	}
	
	/**
	 * sets color of brush
	 * @param c
	 */
	public void setColor(Color c)
	{
		currentColor = c;
	}
	
	/**
	 * sets paint mode
	 * @param m
	 */
	public void setMode(String m)
	{
		mode = m;
	}
	
	/**
	 * sets stroke size
	 * @param size
	 */
	public void setStroke(int size)
	{
		currentStroke = size;
	}
	
	/**
	 * sets background image when image is opened
	 * @param path
	 */
	public void setBackground(String path)
	{
		try {
			Image img = ImageIO.read(new File(path));
			img = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
			setIcon(new ImageIcon(img));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		data.clear();
	}
	
	/**
	 * adds back undone paint data
	 */
	public void redo()
	{
		if (undo.size() > 0)
		{
			while (undo.peek().getColor() != null)
				data.add(undo.pop());
			data.add(undo.pop());
		}
		repaint();
	}
	
	/**
	 * undoes paint data
	 */
	public void undo()
	{
		int a = 0;		
		while (a < 2 && data.size() > 0)
		{
			PaintData p = data.get(data.size() - 1);
			
			if (p.getColor() == null)
				a++;
			
			if (a != 2)
			{
				undo.add(p);
				data.remove(data.size() - 1);
			}
		}		
		repaint();
	}
	
	/**
	 * returns size of paint data ArrayList
	 * @return data.size()
	 */
	public int getDataSize()
	{
		return data.size();
	}
	
	/**
	 * returns size of undo Stack
	 * @return undo.size()
	 */
	public int getStackSize()
	{
		return undo.size();
	}
	
	// Mouse Listener for canvas
	public class MouseList extends MouseAdapter
	{

		/**
		 * checks if mouse is being dragged
		 * @param arg0
		 */
		public void mouseDragged(MouseEvent arg0) 
		{
			//adds PaintData point
			if (mode.equals("paint"))
				data.add(new PaintData(arg0.getPoint(), currentColor, false, currentStroke));
			
			//adds eraser point
			else if (mode.equals("erase"))
				data.add(new PaintData(arg0.getPoint(), Color.WHITE, false, currentStroke));
			
			//resizes rectangle
			else if (mode.equals("rectangle") || mode.equals("rectangleF") || mode.equalsIgnoreCase("rectangleErase"))
			{
				Rectangle rAlias = (Rectangle) data.get(data.size()-1);
				rAlias.setEnd(arg0.getPoint());
			}
			
			//resizes line
			else if (mode.equals("line"))
			{
				Line lAlias = (Line) data.get(data.size()-1);
				lAlias.setEnd(arg0.getPoint());
			}
			
			//resizes oval
			else if (mode.equals("oval") || mode.equals("ovalF"))
			{
				Oval oAlias = (Oval) data.get(data.size()-1);
				oAlias.setWidth(arg0.getX());
				oAlias.setHeight(arg0.getY());
			}
			
			//resizes rounded rectangle
			else if (mode.equals("rrectangle") || mode.equals("rrectangleF"))
			{
				RoundRectangle rrAlias = (RoundRectangle) data.get(data.size()-1);
				rrAlias.setEnd(arg0.getPoint());
			}
			
			//resizes triangle
			else if (mode.equals("triangle") || mode.equals("triangleF"))
			{
				Triangle tAlias = (Triangle) data.get(data.size()-1);
				tAlias.setEnd(arg0.getPoint());
			}
			
			repaint();
		}

		/**
		 * checks if mouse has been pressed
		 * @param e
		 */
		public void mousePressed(MouseEvent e) 
		{
			undo.clear();
			
			//adds starting point of PaintData
			if (mode.equals("paint"))
				data.add(new PaintData(e.getPoint(), currentColor, false, currentStroke));
			
			//adds starting point of Rectangle
			else if (mode.equals("rectangle"))
				data.add(new Rectangle(e.getPoint(), currentColor, false, currentStroke));
			
			//adds starting point of Line
			else if (mode.equals("line"))
				data.add(new Line(e.getPoint(), currentColor, false, currentStroke));
			
			//adds starting point of Oval
			else if (mode.equals("oval"))
				data.add(new Oval(e.getPoint(), currentColor, false, currentStroke));
			
			//adds starting point of Rounded Rectangle
			else if (mode.equals("rrectangle"))
				data.add(new RoundRectangle(e.getPoint(), currentColor, false, currentStroke));
			
			//adds starting point of Triangle
			else if (mode.equals("triangle"))
				data.add(new Triangle(e.getPoint(), currentColor, false, currentStroke));
			
			//adds starting point of Filled Rectangle
			else if (mode.equals("rectangleF"))
				data.add(new Rectangle(e.getPoint(), currentColor, true, currentStroke));
			
			//adds starting point of Filled OVal
			else if (mode.equals("ovalF"))
				data.add(new Oval(e.getPoint(), currentColor, true, currentStroke));
			
			//adds starting point of Filled Rounded Rectangle
			else if (mode.equals("rrectangleF"))
				data.add(new RoundRectangle(e.getPoint(), currentColor, true, currentStroke));
			
			//adds starting point of Filled Triangle
			else if (mode.equals("triangleF"))
				data.add(new Triangle(e.getPoint(), currentColor, true, currentStroke));
			
			//adds starting point of Eraser
			else if (mode.equals("erase"))
				data.add(new PaintData(e.getPoint(), Color.WHITE, false, currentStroke));
			
			//adds starting point of Rectangular Eraser
			else if (mode.equals("rectangleErase"))
				data.add(new Rectangle(e.getPoint(), Color.WHITE, true, currentStroke));
			
			repaint();
		}

		/**
		 * checks if mouse has been released and adds a false point for separation
		 * @param e
		 */
		public void mouseReleased(MouseEvent e) 
		{
			data.add(new PaintData(new Point(-1,-1), null, false, 0));
		}
	}
}
