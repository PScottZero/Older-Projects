import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class Switch extends JButton
{
	private static final long serialVersionUID = 595297379914455519L;
	private boolean state;
	
	private Color switchBack;
	private Color switchFore;
	
	public Switch(String switchID, boolean color)
	{
		state = false;
		
		if (color == true)
		{
			switchBack = new Color (130, 16, 16);
			switchFore = new Color (180, 66, 66);
		}
		else 
		{
			switchBack = new Color (0, 118, 205);
			switchFore = new Color (0, 168, 255);
		}
			
		setText(switchID);
		setSize(25, 40);
		setBorder(null);
	}
	
	public void setState()
	{
		if (state == false)
			state = true;
		else
			state = false;
	}
	
	public boolean getState()
	{
		return state;
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(switchBack);
		g.fillRect(0, 0, 25, 40);
		g.setColor(switchFore);
		
		if (state == false)
			g.fillRect(0, 30, 25, 10);
		else
			g.fillRect(0, 0, 25, 10);
	}
}
