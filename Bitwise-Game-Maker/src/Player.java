import java.awt.*;

public class Player extends BlockAbstract
{
    private Color[][] config;

    public Player()
    {
        super(16, 8);
    }

    public Point getTopRight(int x, int y, int scale) { return new Point(x + scale, y); }

    public Point getTopLeft(int x, int y, int scale) { return new Point(x, y); }

    public Point getBottomRight(int x, int y, int scale) { return new Point(x + scale, y + (2 * scale)); }

    public Point getBottomLeft(int x, int y, int scale) { return new Point(x, y + (2 * scale)); }
}
