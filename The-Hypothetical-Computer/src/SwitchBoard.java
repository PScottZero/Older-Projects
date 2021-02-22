import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SwitchBoard extends JPanel
{
	private static final long serialVersionUID = 8409929967948617928L;
	private Computer com;
	private int currentA;
	
	private Color off = new Color(50, 0, 0);
	private Color on = Color.RED;
	private Color bkg = new Color (40, 40, 40);
	
	private Switch[] dataSwitches;
	private switchListener s;
	
	private Button runB;
	private JPanel hide;
	private JLabel title;
	
	private boolean[] flags;
	private boolean run;
	
	private final int FHEIGHT = 60;
	private final int SHEIGHT = 300;
	private final int DHEIGHT = 140;
	private final int MHEIGHT = 220;
	private final int BHEIGHT = 370;
	
	public SwitchBoard()
	{
		setLayout(null);
		
		com = new Computer();
		com.update();
		
		currentA = 0x00;
		dataSwitches = new Switch[8];
		s = new switchListener();
		
		flags = new boolean[4];
		run = false;
		
		boolean type = true;
		
		for (int a = 0; a < dataSwitches.length; a++)
		{
			if (a > 3)
				type = false;
			
			dataSwitches[a] = new Switch(a + "", type);
			dataSwitches[a].setLocation(a * 45 + 30, SHEIGHT);
			dataSwitches[a].addActionListener(s);
			add(dataSwitches[a]);
		}
		
		title = new JLabel("THE HYPOTHETICAL COMPUTER");
		title.setFont(new Font("Courier", Font.BOLD, 23));
		title.setForeground(Color.WHITE);
		title.setBackground(bkg);
		title.setOpaque(true);
		title.setSize(380, 40);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setLocation(10,10);
		add(title);
		
		hide = new JPanel();
		hide.setSize(80,50);
		hide.setLocation(0,0);
		hide.setBackground(Color.GRAY);
		hide.setLayout(null);
		add(hide);
		
		runB = new Button("Run>>>>", new Color (0, 168, 255), new Color (0, 118, 205));
		runB.setLocation(30, BHEIGHT + 60);
		runB.addActionListener(s);
		add(runB);
		
		addButton("Examine", new Color (150, 150, 150), new Color (100, 100, 100), 30, BHEIGHT);
		addButton("Exam-->", new Color (150, 150, 150), new Color (100, 100, 100), 120, BHEIGHT);
		addButton("Deposit", new Color (150, 150, 150), new Color (100, 100, 100), 210, BHEIGHT);
		addButton("Depo-->", new Color (150, 150, 150), new Color (100, 100, 100), 300, BHEIGHT);
		addButton("Step-->", new Color (0, 168, 255), new Color (0, 118, 205), 120, BHEIGHT + 60);
		addButton("Stop]]]", new Color (180, 66, 66), new Color (130, 16, 16), 210, BHEIGHT + 60);
		addButton("Clear><", new Color (180, 66, 66), new Color (130, 16, 16), 300, BHEIGHT + 60);
	}
	
	private void addButton(String name, Color n, Color p, int x, int y)
	{
		Button btn = new Button(name, n, p);
		btn.setLocation(x, y);
		btn.addActionListener(s);
		add(btn);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(bkg);
		g.fillRect(0, 0, 400, 500);		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Courier", Font.PLAIN, 15));
		
		String[] flagNames = { "Carry", "Equal", "Zero", ">Than" };
		
		for(int x = 0; x < flags.length; x++)
		{
			if (flags[x] == true)
				g.setColor(on);
			else
				g.setColor(off);
			
			g.fillOval((int)(x * 92.8 + 30), FHEIGHT, 15, 15);
			g.setColor(Color.WHITE);
			g.drawString(flagNames[x], (int)(x * 92.8 + 30) + 20, FHEIGHT + 12);
		}
		
		g.drawLine(30, FHEIGHT + 35, 370, FHEIGHT + 35);
		g.drawString("FLAGS", 177, FHEIGHT + 50);
		
		Register r = com.getRam().getMemory(currentA);
		int data = r.getData();
		String addressString = Integer.toBinaryString(currentA);
		String dataString = Integer.toBinaryString(data);
		
		while (addressString.length() < 8)
		{
			addressString = "0" + addressString;
		}
		
		while (dataString.length() < 8)
		{
			dataString = "0" + dataString;
		}
		
		for (int x = 0; x < dataString.length(); x++)
		{
			if (dataString.charAt(x) == '0')
			{
				g.setColor(off);
			}
			else
			{
				g.setColor(on);
			}		
			g.fillOval((int)(x * 46.4 + 30), DHEIGHT, 15, 15);
			g.setColor(Color.WHITE);
			g.drawString((7 - x) + "", (int)(x * 46.4 + 30) + 3, DHEIGHT + 30);
		}
		
		g.drawLine(30, DHEIGHT + 35, 370, DHEIGHT + 35);
		g.drawString("DATA", 181, DHEIGHT + 50);
		
		for (int x = 0; x < addressString.length(); x++)
		{
			if (addressString.charAt(x) == '0')
			{
				g.setColor(off);
			}
			else
			{
				g.setColor(on);
			}		
			g.fillOval((int)(x * 46.4 + 30), MHEIGHT, 15, 15);
			g.setColor(Color.WHITE);
			g.drawString((7 - x) + "", (int)(x * 46.4 + 30) + 3, MHEIGHT + 30);
		}
		
		g.drawLine(30, MHEIGHT + 35, 370, MHEIGHT + 35);
		g.drawString("MEMORY", 173, MHEIGHT + 50);
		
		g.setColor(Color.gray);
		g.fillRect(0, 0, 10, 500);
		g.fillRect(0, 0, 400, 10);
		g.fillRect(390, 0, 10, 500);
		g.fillRect(0, 490, 400, 10);
		
		if (run == true)
		{
			runB.doClick();
		}
	}
	
	public class switchListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			if (arg0.getActionCommand().equals("Deposit"))
			{
				deposit();
			}
			else if (arg0.getActionCommand().equals("Depo-->"))
			{
				currentA++;
				if (currentA > 0xFF)
				{
					currentA = 0x00;
				}
				deposit();
			}
			else if (arg0.getActionCommand().equals("Examine"))
			{
				String bin = "";
				for (int x = 0; x < dataSwitches.length; x++)
				{
					if (dataSwitches[x].getState() == false)
						bin += "0";
					else
						bin += "1";
				}
				
				currentA = Integer.parseInt(Integer.toHexString(Integer.parseInt(bin, 2)), 16);
			}
			else if (arg0.getActionCommand().equals("Exam-->"))
			{
				currentA++;
				if (currentA > 0xFF)
				{
					currentA = 0x00;
				}
			}
			else if (arg0.getActionCommand().equals("Clear><"))
			{
				run = false;
				com.clear();
				com.update();
				flags = com.getFlags();
			}
			else if (arg0.getActionCommand().equals("Run>>>>"))
			{
				run = true;
				com.step(currentA);
				currentA = com.getNextAddress();
				flags = com.getFlags();
			}
			else if (arg0.getActionCommand().equals("Step-->"))
			{
				com.step(currentA);
				currentA = com.getNextAddress();
				flags = com.getFlags();
			}
			else if (arg0.getActionCommand().equals("Stop]]]"))
			{
				run = false;
			}
			else
			{
				String sw = arg0.getActionCommand();
				for (int x = 0; x < dataSwitches.length; x++)
				{
					if (dataSwitches[x].getText().equals(sw))
					{
						dataSwitches[x].setState();
					}
				}
			}
			repaint();
		}
		
		public void deposit()
		{
			String bin = "";
			for (int x = 0; x < dataSwitches.length; x++)
			{
				if (dataSwitches[x].getState() == false)
					bin += "0";
				else
					bin += "1";
			}
			
			int data = Integer.parseInt(Integer.toHexString(Integer.parseInt(bin, 2)), 16);
			
			com.getRam().setMemory(currentA, data);
			com.update();
			repaint();
		}
	}
}
