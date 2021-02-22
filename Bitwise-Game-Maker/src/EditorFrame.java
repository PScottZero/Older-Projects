import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditorFrame
{
    private static final int WIDTH = 820, HEIGHT = 450;
    private static Editor editor;
    private static int liveCount;

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Bitwise Editor");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setResizable(false);
        editor = new Editor();
        frame.setJMenuBar(getMenuBar());
        frame.add(editor);
        frame.pack();
        frame.setVisible(true);

        liveCount = 3;
    }

    private static JMenuBar getMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(58, 106, 132));
        menuBar.setBorderPainted(false);

        ButtonListener bl = new ButtonListener();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.setForeground(Color.WHITE);
        menuBar.add(fileMenu);

        JMenuItem open = new JMenuItem("Open");
        open.setMnemonic(KeyEvent.VK_O);
        open.addActionListener(bl);
        fileMenu.add(open);

        JMenuItem save = new JMenuItem("Save");
        save.setMnemonic(KeyEvent.VK_S);
        save.addActionListener(bl);
        fileMenu.add(save);

        fileMenu.addSeparator();

        JMenuItem quit = new JMenuItem("Quit");
        quit.setMnemonic(KeyEvent.VK_Q);
        quit.addActionListener(bl);
        fileMenu.add(quit);

        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        editMenu.setForeground(Color.WHITE);
        menuBar.add(editMenu);

        JCheckBoxMenuItem editBlocks = new JCheckBoxMenuItem("Edit Blocks");
        editBlocks.setMnemonic(KeyEvent.VK_B);
        editBlocks.addItemListener(new CheckListener());
        editMenu.add(editBlocks);

        JMenuItem editPlayer = new JMenuItem("Edit Player");
        editPlayer.setMnemonic(KeyEvent.VK_P);
        editPlayer.addActionListener(bl);
        editMenu.add(editPlayer);

        JMenu play = new JMenu("Play");
        play.setMnemonic(KeyEvent.VK_P);
        play.setForeground(Color.WHITE);
        menuBar.add(play);

        JMenu liveCount = new JMenu("Live Count");
        liveCount.setMnemonic(KeyEvent.VK_L);

        JRadioButtonMenuItem livesOne = new JRadioButtonMenuItem("1");
        livesOne.setMnemonic(KeyEvent.VK_1);
        livesOne.addActionListener(bl);
        liveCount.add(livesOne);

        JRadioButtonMenuItem livesTwo = new JRadioButtonMenuItem("2");
        livesTwo.setMnemonic(KeyEvent.VK_2);
        livesTwo.addActionListener(bl);
        liveCount.add(livesTwo);

        JRadioButtonMenuItem livesThree = new JRadioButtonMenuItem("3");
        livesThree.setMnemonic(KeyEvent.VK_3);
        livesThree.addActionListener(bl);
        livesThree.setSelected(true);
        liveCount.add(livesThree);

        JRadioButtonMenuItem livesFour = new JRadioButtonMenuItem("4");
        livesFour.setMnemonic(KeyEvent.VK_4);
        livesFour.addActionListener(bl);
        liveCount.add(livesFour);

        JRadioButtonMenuItem livesFive = new JRadioButtonMenuItem("5");
        livesFive.setMnemonic(KeyEvent.VK_5);
        livesFive.addActionListener(bl);
        liveCount.add(livesFive);

        JRadioButtonMenuItem livesInfinite = new JRadioButtonMenuItem("Infinite");
        livesInfinite.setMnemonic(KeyEvent.VK_I);
        livesInfinite.addActionListener(bl);
        liveCount.add(livesInfinite);

        ButtonGroup bg = new ButtonGroup();
        bg.add(livesOne);
        bg.add(livesTwo);
        bg.add(livesThree);
        bg.add(livesFour);
        bg.add(livesFive);
        bg.add(livesInfinite);

        play.add(liveCount);

        play.addSeparator();

        JMenuItem playGame = new JMenuItem("Play Game");
        playGame.setMnemonic(KeyEvent.VK_P);
        playGame.addActionListener(bl);
        play.add(playGame);

        return menuBar;
    }

    private static class CheckListener implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            editor.setBlocksEditable(e.getStateChange() == ItemEvent.SELECTED);
        }
    }

    private static class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equals("Edit Player")) editor.editPlayer();
            else if (e.getActionCommand().equals("Play Game")) editor.play(liveCount);
            else if (e.getActionCommand().equals("Open")) editor.open();
            else if (e.getActionCommand().equals("Save")) editor.save();
            else if (e.getActionCommand().equals("Quit")) System.exit(0);
            else if (e.getActionCommand().equals("1")) liveCount = 1;
            else if (e.getActionCommand().equals("2")) liveCount = 2;
            else if (e.getActionCommand().equals("3")) liveCount = 3;
            else if (e.getActionCommand().equals("4")) liveCount = 4;
            else if (e.getActionCommand().equals("5")) liveCount = 5;
            else if (e.getActionCommand().equals("Infinite")) liveCount = -1;
        }
    }
}
