import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class metarGUI extends JPanel
{
	private static final long serialVersionUID = 541925352440352891L;
	private JTextField airportField, icaoField, timeField, windField, visiField, cloudField, tempField, dewField, altField, remarkField;
	private JTextArea metarArea;
	private enterListener e;
	
	public metarGUI()
	{
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		
		e = new enterListener();
		
		add(createLabel("Enter ICAO:", 20, Color.BLACK));
		add(createLabel("ICAO:", 60, Color.BLUE));
		add(createLabel("Time Issued:", 90, Color.BLUE));
		add(createLabel("Winds:", 120, Color.BLUE));
		add(createLabel("Visibility:", 150, Color.BLUE));
		add(createLabel("Clouds:", 180, Color.BLUE));
		add(createLabel("Temperature:", 210, Color.BLUE));
		add(createLabel("Dewpoint:", 240, Color.BLUE));
		add(createLabel("Altimeter:", 270, Color.BLUE));
		add(createLabel("Remarks:", 300, Color.BLUE));
		add(createLabel("METAR:", 330, Color.BLUE));
		
		add(airportField = createField(20, true));
		add(icaoField = createField(60,  false));
		add(timeField = createField(90,  false));
		add(windField = createField(120,  false));
		add(visiField = createField(150,  false));
		add(cloudField = createField(180,  false));
		add(tempField = createField(210,  false));
		add(dewField = createField(240,  false));
		add(altField = createField(270,  false));
		add(remarkField = createField(300,  false));
		
		metarArea = new JTextArea();
		metarArea.setBounds(100, 330, 380, 40);
		metarArea.setBorder(new EmptyBorder(1, 3, 0, 0));
		metarArea.setLineWrap(true);
		metarArea.setWrapStyleWord(true);
		metarArea.setEditable(false);
		metarArea.setForeground(Color.BLACK);
		metarArea.setOpaque(false);
		add(metarArea);
	}
	
	private JLabel createLabel(String text, int y, Color c)
	{
		JLabel j = new JLabel(text);
		j.setBounds(20, y, 80, 20);
		j.setForeground(c);
		return j;
	}
	
	private JTextField createField(int y, boolean edit)
	{
		JTextField j = new JTextField();
		j.setBounds(100, y, 380, 20);
		j.setBorder(new EmptyBorder(1, 3, 0, 0));
		j.setEditable(edit);
		j.setForeground(Color.BLACK);
		j.setOpaque(false);
		
		if (edit == true)
		{
			j.addActionListener(e);
		}
		
		return j;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		try 
		{
			Image img = ImageIO.read(getClass().getResource("background/bkg.jpg"));
			img = img.getScaledInstance(500, 400, Image.SCALE_SMOOTH);
			g.drawImage(img, 0, 0, null);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private class enterListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			METAR m = new METAR(airportField.getText());
			icaoField.setText(m.getICAO());
			timeField.setText(m.getHours() + ":" + m.getMinutes() + " UTC Day " + m.getDay());
			windField.setText(m.getWindDirection() + "\u00b0 at " + m.getWindSpeed() + " kts");
			visiField.setText(m.getVisibility() + " mi");
			
			ArrayList<String> cloudTypes = m.getCloudTypes();
			ArrayList<Integer> cloudHeights = m.getCloudHeights();
			String allClouds = "";
			for (int i = 0; i < cloudTypes.size(); i++)
			{
				allClouds += cloudTypes.get(i) + " at " + cloudHeights.get(i) + " ft / ";
			}
			allClouds = allClouds.substring(0, allClouds.length() - 3);
			
			cloudField.setText(allClouds);
			tempField.setText(m.getTemperature() + "\u00b0C");
			dewField.setText(m.getDewpoint() + "\u00b0C");
			altField.setText(m.getAltimeter() + " inHg");
			
			ArrayList<String> remarks = m.getRemarks();
			String allRemarks = "";
			for (String r : remarks)
			{
				allRemarks += r + " ";
			}
			
			remarkField.setText(allRemarks);
			metarArea.setText(m.getMETAR());
		}
	}
}
