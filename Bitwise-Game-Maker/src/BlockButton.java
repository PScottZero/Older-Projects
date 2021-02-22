import javax.swing.*;
import java.awt.*;

public class BlockButton extends JButton
{
    private Block block;

    public BlockButton(String name, Block block)
    {
        setActionCommand(name);
        this.block = block;
        setSize(32, 32);
    }

    public Block getBlock()
    {
        return block;
    }

    public void setBlock(Block block)
    {
        this.block = block;
    }

    public void paintComponent(Graphics g)
    {
        block.draw(g, 0, 0, 4);
    }
}
