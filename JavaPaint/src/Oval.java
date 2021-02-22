import java.awt.Color;
import java.awt.Point;

/**
 * 
 * @author Paul Scott
 * @version 7 May 2017
 * 
 * This is the Oval class for JavaPaint.
 *
 */
public class Oval extends PaintData
{
	//width and height of oval
	private int width, height;
	
	/**
	 * default constructor
	 * @param p
	 * @param c
	 * @param fill
	 * @param stroke
	 */
	public Oval(Point p, Color c, boolean fill, int stroke)
	{
		super(p, c, fill, stroke);
		width = 0;
		height = 0;
	}
	
	/**
	 * sets width of oval
	 * @param w
	 */
	public void setWidth(int w)
	{
		width = w - (int)super.getPoint().getX();
	}
	
	/**
	 * sets height of oval
	 * @param h
	 */
	public void setHeight(int h)
	{
		height = h - (int)super.getPoint().getY();
	}
	
	/**
	 * returns width of oval
	 * @return width
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * returns height of oval
	 * @return height
	 */
	public int getHeight()
	{
		return height;
	}
}
