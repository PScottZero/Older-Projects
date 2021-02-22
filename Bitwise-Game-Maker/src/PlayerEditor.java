import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerEditor extends JFrame
{
    private PlayerField playerEdit;
    private Player player;
    private JButton colorChoose;
    private Color currColor;
    private ButtonListener bl;

    public PlayerEditor(Player player)
    {
        setTitle("Player Editor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(220 + 6, 470 + 29));
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(Color.GRAY);

        this.player = player;
        currColor = Color.BLACK;

        playerEdit = new PlayerField(this.player);
        playerEdit.setLocation(10, 10);
        add(playerEdit);

        bl = new ButtonListener();

        colorChoose = new JButton("Color");
        colorChoose.setBackground(Color.BLACK);
        colorChoose.setForeground(Color.WHITE);
        colorChoose.addActionListener(bl);
        colorChoose.setSize(80, 40);
        colorChoose.setLocation(70, 420);
        add(colorChoose);

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
                PlayerField.setColor(currColor);
                int redN = 255 - currColor.getRed();
                int greenN = 255 - currColor.getGreen();
                int blueN = 255 - currColor.getBlue();
                colorChoose.setForeground(new Color(redN, greenN, blueN));
            }
        }
    }
}
