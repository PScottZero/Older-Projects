import java.awt.Color;
import java.awt.Point;

/**
 * 
 * @author Paul Scott
 * @version 7 May 2017
 * 
 * This is the PaintData class which all shapes are derived from.
 *
 */
public class PaintData
{
	//data for paint point, color, stroke size, and filled status
	private Point pnt;
	private Color col;
	private int strokeSize;
	private boolean filled = false;
	
	/**
	 * default constructor
	 * @param p
	 * @param c
	 * @param fill
	 * @param stroke
	 */
	public PaintData(Point p, Color c, boolean fill, int stroke)
	{
		pnt = p;
		col = c;
		filled = fill;
		strokeSize = stroke;
	}
	
	/**
	 * returns point
	 * @return pnt
	 */
	public Point getPoint()
	{
		return pnt;
	}
	
	/**
	 * returns color
	 * @return col
	 */
	public Color getColor()
	{
		return col;
	}
	
	/**
	 * sets shape as filled or not filled
	 * @param f
	 */
	public void setFilled(boolean f)
	{
		filled = f;
	}
	
	/**
	 * checks if shape is filled
	 * @return filled
	 */
	public boolean isFilled()
	{
		return filled;
	}
	
	/**
	 * returns stroke size
	 * @return strokeSize
	 */
	public int getStroke()
	{
		return strokeSize;
	}
	
	/**
	 * sets stroke size
	 * @param size
	 */
	public void setStroke(int size)
	{
		strokeSize = 4;
	}
}
