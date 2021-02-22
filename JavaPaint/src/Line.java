import java.awt.Color;
import java.awt.Point;

/**
 * 
 * @author Paul Scott
 * @version 7 May 2017
 * 
 * This is the Line class which is the parent of the Rectangle,
 * RoundedRectangle, and Triangle class.
 *
 */
public class Line extends PaintData
{
	//end point of line
	private Point end;
	
	/**
	 * default constructor
	 * @param st
	 * @param c
	 * @param fill
	 * @param stroke
	 */
	public Line(Point st, Color c, boolean fill, int stroke)
	{
		super(st, c, fill, stroke);
		end = new Point(st);
	}
	
	/**
	 * sets end point
	 * @param en
	 */
	public void setEnd(Point en)
	{
		end = en;
	}
	
	/**
	 * return end point
	 * @return end
	 */
	public Point getEnd()
	{
		return end;
	}
}
