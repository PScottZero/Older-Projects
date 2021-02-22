import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Play extends JPanel
{
    private Block[][] blockMatrix;
    private Player player;
    private Point pLoc;
    private boolean left_pressed, right_pressed, jumping;
    private int life_count, jump_val;
    private final int MOVE = 1, JUMP = 100, START_X = 24, START_Y = 440;
    private final int SCALE = 3, BLOCK_SIZE = 8 * SCALE;
    private final int WIDTH = 1200, HEIGHT = 600, W_ADJUST = WIDTH + 6, H_ADJUST = HEIGHT + 29;

    public Play(Block[][] blockMatrix, Player player, int lives)
    {
        setSize(WIDTH, HEIGHT);
        setFocusable(true);

        this.blockMatrix = blockMatrix;
        this.player = player;
        this.life_count = lives;

        pLoc = new Point(START_X, START_Y);

        left_pressed = false;
        right_pressed = false;
        jumping = false;
        jump_val = 0;

        addKeyListener(new ControlListener());

        JFrame playFrame = new JFrame("Bitwise Game");
        playFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        playFrame.setLayout(null);
        playFrame.setResizable(false);
        playFrame.add(this);
        playFrame.pack();
        playFrame.setSize(W_ADJUST, H_ADJUST);
        playFrame.setVisible(true);

        Timer t = new Timer();
        t.schedule(new Update(), 0, 5);
    }

    private void exit() { SwingUtilities.getWindowAncestor(this).dispose(); }

    private class Update extends TimerTask
    {
        public void run()
        {
            Point p1 = player.getBottomLeft(pLoc.x, pLoc.y, BLOCK_SIZE);
            Point p2 = player.getBottomRight(pLoc.x, pLoc.y, BLOCK_SIZE);
            Point p3 = player.getTopLeft(pLoc.x, pLoc.y, BLOCK_SIZE);
            Point p4 = player.getTopRight(pLoc.x, pLoc.y, BLOCK_SIZE);

            int x1 = p1.x / BLOCK_SIZE;
            int x2 = p2.x / BLOCK_SIZE;
            int x3 = p3.x / BLOCK_SIZE;
            int x4 = p4.x / BLOCK_SIZE;
            int botY = p1.y / BLOCK_SIZE;
            int topY = p3.y / BLOCK_SIZE;
            int x1c = (p1.x - MOVE) / BLOCK_SIZE;
            int x2c = (p2.x + MOVE) / BLOCK_SIZE;
            int botYc = (p1.y + MOVE) / BLOCK_SIZE;
            int topYc = (p3.y - MOVE) / BLOCK_SIZE;

            int y = p1.y / BLOCK_SIZE;

            if (x2c >= 50) exit();

            else if (botYc > 24) restart();

            else {
                if (enemyCollision(x1, x2, x3, x4, botY, topY)) restart();

                if (left_pressed && blockMatrix[y][x1c].getFunction() != 1)
                    pLoc.x -= MOVE;
                else if (right_pressed && blockMatrix[y][x2c].getFunction() != 1)
                    pLoc.x += MOVE;

                if (pLoc.x < 0) pLoc.x = 0;

                if (pLoc.y < 0)
                {
                    pLoc.y = 0;
                    jump_val = 0;
                }

                if (jump_val == 0)
                {
                    if (botYc < 25)
                    {
                        if (blockMatrix[botYc][x1].getFunction() != 1 && blockMatrix[botYc][x2].getFunction() != 1)
                            pLoc.y += MOVE;
                        else
                            jumping = false;
                    }
                }
                else {
                    if (blockMatrix[topYc][x3].getFunction() == 1 || blockMatrix[topYc][x4].getFunction() == 1)
                    {
                        jump_val = 0;
                    }
                    else {
                        pLoc.y -= MOVE;
                        jump_val--;
                    }
                }
            }

            if (life_count == 0) exit();

            repaint();
        }
    }

    public void restart()
    {
        pLoc.x = START_X;
        pLoc.y = START_Y;
        jump_val = 0;
        life_count--;
    }

    public boolean enemyCollision(int x1, int x2, int x3, int x4, int botY, int topY)
    {
        return (blockMatrix[botY][x1].getFunction() == 2
                || blockMatrix[botY][x2].getFunction() == 2
                || blockMatrix[topY][x3].getFunction() == 2
                || blockMatrix[topY][x4].getFunction() == 2);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        for (int i = 0; i < blockMatrix.length; i++)
            for (int j = 0; j < blockMatrix[i].length; j++)
                blockMatrix[i][j].draw(g, j * BLOCK_SIZE, i * BLOCK_SIZE, SCALE);

        player.draw(g, pLoc.x, pLoc.y, 3);

        g.setColor(Color.WHITE);
        g.setFont(new Font("ARIAL", Font.BOLD, 24));
        if (life_count < 0)
        {
            g.drawString("Lives: ", 20, 580);
            g.setFont(new Font("ARIAL", Font.BOLD, 36));
            g.drawString("\u221E", 90, 587);
        }
        else
            g.drawString("Lives: " + life_count, 20, 580);

    }

    private class ControlListener extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            if (e.getKeyCode() == KeyEvent.VK_D)
            {
                right_pressed = true;
                left_pressed = false;
            }

            else if (e.getKeyCode() == KeyEvent.VK_A)
            {
                left_pressed = true;
                right_pressed = false;
            }

            else if (e.getKeyCode() == KeyEvent.VK_SPACE)
            {
                if (jump_val == 0 && !jumping)
                {
                    jump_val = JUMP;
                    jumping = true;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e)
        {
            if (e.getKeyCode() == KeyEvent.VK_D) right_pressed = false;
            else if (e.getKeyCode() == KeyEvent.VK_A) left_pressed = false;
        }
    }
}
