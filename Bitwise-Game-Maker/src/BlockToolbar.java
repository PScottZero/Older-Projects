import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlockToolbar extends JPanel
{
    private ButtonListener bl;
    private boolean isEditable;
    private BlockButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9;

    public BlockToolbar(Color c)
    {
        setSize(356, 32);
        setLayout(null);
        setBackground(c);

        isEditable = false;

        bl = new ButtonListener();

        b0 = addButton("block0", new Block(), 0);
        add(b0);
        b1 = addButton("block1", new Block(), 36);
        add(b1);
        b2 = addButton("block2", new Block(), 72);
        add(b2);
        b3 = addButton("block3", new Block(), 108);
        add(b3);
        b4 = addButton("block4", new Block(), 144);
        add(b4);
        b5 = addButton("block5", new Block(), 180);
        add(b5);
        b6 = addButton("block6", new Block(), 216);
        add(b6);
        b7 = addButton("block7", new Block(), 252);
        add(b7);
        b8 = addButton("block8", new Block(), 288);
        add(b8);
        b9 = addButton("block9", new Block(), 324);
        add(b9);
    }

    public Block[] getBlocks()
    {
        Block[] blocks = new Block[10];

        blocks[0] = b0.getBlock();
        blocks[1] = b1.getBlock();
        blocks[2] = b2.getBlock();
        blocks[3] = b3.getBlock();
        blocks[4] = b4.getBlock();
        blocks[5] = b5.getBlock();
        blocks[6] = b6.getBlock();
        blocks[7] = b7.getBlock();
        blocks[8] = b8.getBlock();
        blocks[9] = b9.getBlock();

        return blocks;
    }

    public void setBlocks(Block[] blocks)
    {
        b0.setBlock(blocks[0]);
        b1.setBlock(blocks[1]);
        b2.setBlock(blocks[2]);
        b3.setBlock(blocks[3]);
        b4.setBlock(blocks[4]);
        b5.setBlock(blocks[5]);
        b6.setBlock(blocks[6]);
        b7.setBlock(blocks[7]);
        b8.setBlock(blocks[8]);
        b9.setBlock(blocks[9]);

        b0.repaint();
        b1.repaint();
        b2.repaint();
        b3.repaint();
        b4.repaint();
        b5.repaint();
        b6.repaint();
        b7.repaint();
        b8.repaint();
        b9.repaint();
    }

    private BlockButton addButton(String name, Block b, int x)
    {
        BlockButton bb = new BlockButton(name, b);
        bb.setLocation(x, 0);
        bb.addActionListener(bl);
        return bb;
    }

    public void setEditable(boolean editable)
    {
         isEditable = editable;
    }

    private class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (isEditable)
            {
                if (e.getActionCommand().equals("block0"))
                {
                    new BlockEditor(b0.getBlock());
                }
                else if (e.getActionCommand().equals("block1"))
                {
                    new BlockEditor(b1.getBlock());
                }
                else if (e.getActionCommand().equals("block2"))
                {
                    new BlockEditor(b2.getBlock());
                }
                else if (e.getActionCommand().equals("block3"))
                {
                    new BlockEditor(b3.getBlock());
                }
                else if (e.getActionCommand().equals("block4"))
                {
                    new BlockEditor(b4.getBlock());
                }
                else if (e.getActionCommand().equals("block5"))
                {
                    new BlockEditor(b5.getBlock());
                }
                else if (e.getActionCommand().equals("block6"))
                {
                    new BlockEditor(b6.getBlock());
                }
                else if (e.getActionCommand().equals("block7"))
                {
                    new BlockEditor(b7.getBlock());
                }
                else if (e.getActionCommand().equals("block8"))
                {
                    new BlockEditor(b8.getBlock());
                }
                else if (e.getActionCommand().equals("block9"))
                {
                    new BlockEditor(b9.getBlock());
                }
            }
            else
            {
                if (e.getActionCommand().equals("block0"))
                {
                    Block b = b0.getBlock();
                    Level.setBlock(b);
                }
                else if (e.getActionCommand().equals("block1"))
                {
                    Block b = b1.getBlock();
                    Level.setBlock(b);
                }
                else if (e.getActionCommand().equals("block2"))
                {
                    Block b = b2.getBlock();
                    Level.setBlock(b);
                }
                else if (e.getActionCommand().equals("block3"))
                {
                    Block b = b3.getBlock();
                    Level.setBlock(b);
                }
                else if (e.getActionCommand().equals("block4"))
                {
                    Block b = b4.getBlock();
                    Level.setBlock(b);
                }
                else if (e.getActionCommand().equals("block5"))
                {
                    Block b = b5.getBlock();
                    Level.setBlock(b);
                }
                else if (e.getActionCommand().equals("block6"))
                {
                    Block b = b6.getBlock();
                    Level.setBlock(b);
                }
                else if (e.getActionCommand().equals("block7"))
                {
                    Block b = b7.getBlock();
                    Level.setBlock(b);
                }
                else if (e.getActionCommand().equals("block8"))
                {
                    Block b = b8.getBlock();
                    Level.setBlock(b);
                }
                else if (e.getActionCommand().equals("block9"))
                {
                    Block b = b9.getBlock();
                    Level.setBlock(b);
                }
            }
        }
    }
}
