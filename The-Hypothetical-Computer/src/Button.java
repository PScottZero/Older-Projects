import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Button extends JButton
{
	private static final long serialVersionUID = -3335917105115543214L;
	private boolean isPressed;
	
	private Color pressed;
	private Color normal;

	public Button(String name, Color n, Color p)
	{
		setText(name);
		setBorder(null);
		setSize(70, 40);
		
		normal = n;
		pressed = p;
		
		isPressed = false;
		
		addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent arg0) 
			{
				if (getModel().isPressed())
					isPressed = true;
				else
					isPressed = false;
			}
			
		});
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if (isPressed == false)
			g.setColor(normal);
		else
			g.setColor(pressed);
		
		g.fillRect(0, 0, 70, 40);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Courier", Font.BOLD, 15));
		g.drawString(getText(), 4, 23);
	}
}
