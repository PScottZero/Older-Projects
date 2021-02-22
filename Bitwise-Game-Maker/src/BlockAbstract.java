import java.awt.*;

public abstract class BlockAbstract
{
    private Color[][] config;
    private int width, height;

    public BlockAbstract(int width, int height)
    {
        this.width = width;
        this.height = height;

        Color[][] colorArr = new Color[width][height];

        for (int i = 0; i < colorArr.length; i++)
            for (int j = 0; j < colorArr[i].length; j++)
                colorArr[i][j] = Color.BLACK;

        this.config = colorArr;
    }

    public void draw(Graphics g, int x, int y, int scale)
    {
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                g.setColor(config[i][j]);
                g.fillRect(x + scale*j, y + scale*i, scale, scale);
            }
        }
    }

    public Color[][] getConfig() { return config; }

    public void setConfig(Color[][] config) { this.config = config; }

    public void setPixel(int r, int c, Color color) { config[r][c] = color; }
}
