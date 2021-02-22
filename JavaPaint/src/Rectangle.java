import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;

/**
 * 
 * @author Paul Scott
 * @version 7 May 2017
 * 
 * This is the Rectangle class which is used to derive 
 * all other rectangle shapes.
 *
 */
public class Rectangle extends Line
{
	/**
	 * default constructor
	 * @param st
	 * @param c
	 * @param fill
	 * @param stroke
	 */
	public Rectangle(Point st, Color c, boolean fill, int stroke)
	{
		super(st, c, fill, stroke);
	}
	
	/**
	 * returns rectangle polygon
	 * @return r
	 */
	public Polygon getRectangle()
	{
		int startX = (int) super.getPoint().getX();
		int startY = (int) super.getPoint().getY();
		int endX = (int) super.getEnd().getX();
		int endY = (int) super.getEnd().getY();
		int xpoints[] = { startX, startX, endX, endX };
		int ypoints[] = { startY, endY, endY, startY };
		Polygon r = new Polygon(xpoints, ypoints, 4);
		return r;
	}
}
