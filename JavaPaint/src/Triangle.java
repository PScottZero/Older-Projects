import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;

/**
 * 
 * @author Paul Scott
 * @version 7 May 2017
 * 
 * This is the Triangle class for JavaPaint.
 *
 */
public class Triangle extends Line
{
	/**
	 * default constructor
	 * @param p
	 * @param c
	 * @param fill
	 * @param stroke
	 */
	public Triangle(Point p, Color c, boolean fill, int stroke)
	{
		super(p, c, fill, stroke);
	}
	
	/**
	 * returns triangle polygon
	 * @return tr
	 */
	public Polygon getTriangle()
	{
		int endx = (int) super.getEnd().getX();
		int endy = (int) super.getEnd().getY();
		int startx = (int) super.getPoint().getX();
		int starty = (int) super.getPoint().getY();
		int d2 = (int)((endx - startx) / 2);
		int xpoints[] = { startx, startx + d2, endx };
		int ypoints[] = { endy, starty, endy };
		Polygon tr = new Polygon(xpoints, ypoints, 3);
		return tr;
	}
}
