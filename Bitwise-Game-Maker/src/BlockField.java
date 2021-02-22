import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BlockField extends JPanel
{
    private Block block;
    private static Color currColor;
    private ClickListener cl;

    public BlockField(Block block)
    {
        this.block = block;
        setSize(400, 400);

        cl = new ClickListener();
        addMouseListener(cl);
        addMouseMotionListener(cl);

        currColor = Color.BLACK;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        block.draw(g, 0, 0, 50);

        g.setColor(Color.GRAY);
        for (int i = 0; i < 400; i += 50)
        {
            for (int j = 0; j < 400; j += 50)
            {
                g.drawLine(j, 0, j, 400);
            }
            g.drawLine(0, i, 400, i);
        }
    }

    public static void setColor(Color color)
    {
        currColor = color;
    }

    private class ClickListener extends MouseAdapter
    {

        @Override
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX() / 50;
            int y = e.getY() / 50;

            if (x >= 8) x = 7;
            if (x < 0) x = 0;
            if (y >= 8) y = 7;
            if (y < 0) y = 0;

            block.setPixel(y, x, currColor);

            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            int x = e.getX() / 50;
            int y = e.getY() / 50;

            if (x >= 8) x = 7;
            if (x < 0) x = 0;
            if (y >= 8) y = 7;
            if (y < 0) y = 0;

            block.setPixel(y, x, currColor);

            repaint();
        }
    }
}
