import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlockEditor extends JFrame
{
    private BlockField blockEdit;
    private Block block;
    private JButton colorChoose;
    private JRadioButton bkg, plt, enm;
    private ButtonGroup bg;
    private Color currColor;
    private ButtonListener bl;

    public BlockEditor(Block block)
    {
        setTitle("Block Editor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(420 + 6, 470 + 29));
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(Color.GRAY);

        this.block = block;
        currColor = Color.BLACK;

        blockEdit = new BlockField(this.block);
        blockEdit.setLocation(10, 10);
        add(blockEdit);

        bl = new ButtonListener();

        colorChoose = new JButton("Color");
        colorChoose.setBackground(Color.BLACK);
        colorChoose.setForeground(Color.WHITE);
        colorChoose.addActionListener(bl);
        colorChoose.setSize(80, 40);
        colorChoose.setLocation(10, 420);
        add(colorChoose);

        bkg = new JRadioButton("Background");
        bkg.setSize(100,20);
        bkg.setLocation(100, 430);
        bkg.setBackground(Color.GRAY);
        bkg.addActionListener(bl);

        plt = new JRadioButton("Platform");
        plt.setSize(100,20);
        plt.setLocation(220, 430);
        plt.setBackground(Color.GRAY);
        plt.addActionListener(bl);

        enm = new JRadioButton("Enemy");
        enm.setSize(100,20);
        enm.setLocation(340, 430);
        enm.setBackground(Color.GRAY);
        enm.addActionListener(bl);

        bg = new ButtonGroup();
        bg.add(bkg);
        bg.add(plt);
        bg.add(enm);

        if (block.getFunction() == 0)
            bkg.setSelected(true);
        else if (block.getFunction() == 1)
            plt.setSelected(true);
        else if (block.getFunction() == 2)
            enm.setSelected(true);

        add(bkg);
        add(plt);
        add(enm);

        pack();
        setVisible(true);
    }

    private class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equals("Color"))
            {
                currColor = JColorChooser.showDialog(null, "Choose Color", currColor);
                colorChoose.setBackground(currColor);
                BlockField.setColor(currColor);
                int redN = 255 - currColor.getRed();
                int greenN = 255 - currColor.getGreen();
                int blueN = 255 - currColor.getBlue();
                colorChoose.setForeground(new Color(redN, greenN, blueN));
            }
            else if (e.getActionCommand().equals("Background"))
                block.setFunction(0);
            else if (e.getActionCommand().equals("Platform"))
                block.setFunction(1);
            else if (e.getActionCommand().equals("Enemy"))
                block.setFunction(2);
        }
    }
}
