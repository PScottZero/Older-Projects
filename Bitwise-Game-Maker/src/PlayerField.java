import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayerField extends JPanel
{
    private Player player;
    private static Color currColor;
    private ClickListener cl;

    public PlayerField(Player player)
    {
        this.player = player;
        setSize(200, 400);

        cl = new ClickListener();
        addMouseListener(cl);
        addMouseMotionListener(cl);

        currColor = Color.BLACK;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        player.draw(g, 0, 0, 25);

        g.setColor(Color.GRAY);
        for (int i = 0; i < 400; i += 25)
        {
            for (int j = 0; j < 200; j += 25)
            {
                g.drawLine(j, 0, j, 400);
            }
            g.drawLine(0, i, 200, i);
        }
    }

    public static void setColor(Color color)
    {
        currColor = color;
    }

    public Player getPlayer()
    {
        return player;
    }

    private class ClickListener extends MouseAdapter
    {

        @Override
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX() / 25;
            int y = e.getY() / 25;

            if (x >= 8) x = 7;
            if (x < 0) x = 0;
            if (y >= 16) y = 15;
            if (y < 0) y = 0;

            player.setPixel(y, x, currColor);

            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            int x = e.getX() / 25;
            int y = e.getY() / 25;

            if (x >= 8) x = 7;
            if (x < 0) x = 0;
            if (y >= 16) y = 15;
            if (y < 0) y = 0;

            player.setPixel(y, x, currColor);

            repaint();
        }
    }
}
