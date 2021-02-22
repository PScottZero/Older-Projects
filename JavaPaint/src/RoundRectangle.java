import java.awt.Color;
import java.awt.Point;

/**
 * 
 * @author Paul Scott
 * @version 7 May 2017
 * 
 * This is the RoundedRectangle class for JavaPaint.
 *
 */
public class RoundRectangle extends Rectangle
{
	//radius of rounded rectangle
	private final int radius = 50;
	
	/**
	 * default constructor
	 * @param p
	 * @param c
	 * @param fill
	 * @param stroke
	 */
	public RoundRectangle(Point p, Color c, boolean fill, int stroke) 
	{
		super(p, c, fill, stroke);
	}
	
	/**
	 * returns radius of rounded rectangle
	 * @return radius
	 */
	public int getRadius()
	{
		return radius;
	}

}
