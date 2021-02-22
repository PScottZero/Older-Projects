import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Editor extends JPanel
{
    private Level level;
    private BlockToolbar bt;
    private Player player;
    private final Color BKG = new Color(98, 146, 172);

    public Editor()
    {
        setLayout(null);
        setBackground(BKG);

        level = new Level(50, 25);
        level.setLocation(10, 40);
        add(level);

        bt = new BlockToolbar(BKG);
        bt.setLocation(232, 4);
        add(bt);

        player = new Player();
    }

    public void setBlocksEditable(boolean editable)
    {
        bt.setEditable(editable);
    }

    public void editPlayer()
    {
        new PlayerEditor(player);
    }

    public void play(int lives)
    {
        new Play(level.getBlockMatrix(), player, lives);
    }

    public void open()
    {
        try {
            Load l = new Load();
            bt.setBlocks(l.getBlocks());
            level.setBlockMatrix(l.getBlockMatrix());
            player.setConfig(l.getPlayerMatrix());
            level.repaint();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void save()
    {
        new Save(level.getBlockMatrix(), bt.getBlocks(), player.getConfig());
    }
}
