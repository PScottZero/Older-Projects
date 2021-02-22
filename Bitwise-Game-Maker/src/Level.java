import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Level extends JPanel
{
    private Block[][] blockMatrix;
    private int width;
    private int height;
    private ListenForClick lfc;
    private static Block currBlock = new Block();

    public Level(int width, int height)
    {
        this.width = width;
        this.height = height;

        lfc = new ListenForClick();

        setSize(width * 16, height * 16);

        blockMatrix = getBlankMatrix();

        addMouseListener(lfc);
        addMouseMotionListener(lfc);
    }

    public static void setBlock(Block b)
    {
        currBlock = b;
    }

    private Block[][] getBlankMatrix()
    {
        Block[][] matrix = new Block[height][width];

        for (int r = 0; r < height; r++)
            for (int c = 0; c < width; c++)
                matrix[r][c] = new Block();

        return matrix;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                blockMatrix[i][j].draw(g, j * 16, i * 16, 2);

        g.setColor(Color.GRAY);
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                g.drawLine(j * 16, 0, j * 16, height * 16);
            }
            g.drawLine(0, i * 16, width * 16, i * 16);
        }
    }

    public Block[][] getBlockMatrix()
    {
        return blockMatrix;
    }

    public void setBlockMatrix(Block[][] blockMatrix)
    {
        this.blockMatrix = blockMatrix;
    }

    public class ListenForClick extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent e)
        {
            Color[] colorArr = new Color[64];
            for (int i = 0; i < colorArr.length; i++)
                colorArr[i] = Color.BLUE;

            int x = e.getX() / 16;
            int y = e.getY() / 16;

            if (x >= width) x = width - 1;
            if (x < 0) x = 0;
            if (y >= height) y = height - 1;
            if (y < 0) y = 0;

            blockMatrix[y][x] = currBlock;
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            int x = e.getX() / 16;
            int y = e.getY() / 16;

            if (x >= width) x = width - 1;
            if (x < 0) x = 0;
            if (y >= height) y = height - 1;
            if (y < 0) y = 0;

            blockMatrix[y][x] = currBlock;
            repaint();
        }
    }
}
