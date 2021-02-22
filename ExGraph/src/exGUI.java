import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author Paul Scott
 * @version April 12th, 2017
 * 
 * This file sets up the GUI of ExGraph
 *
 */
public class exGUI extends JPanel
{
	private static final long serialVersionUID = 1L;
	private final int WIDTH, HEIGHT;
	
	//Colors
	private Color redX = new Color(244,70,70);
	private Color blueX = new Color(0,141,229);
	private Color greenX = new Color(41,155,89);
	private Color blueB = new Color(0,81,108);
	
	//JComponents
	private JTextField eq1, eq2, eq3, ba1, bb1, ba2, bb2, ba3, bb3;
	private JLabel eqL1, eqL2, eqL3;
	private exGraph graph;
	private eqListener list;
	
	/**
	 * constructor
	 * @param width
	 * @param height
	 */
	public exGUI(int width, int height)
	{
		//sets up JPanel
		WIDTH = width;
		HEIGHT = height;
		setSize(WIDTH,HEIGHT);
		setLayout(null);
		setBackground(blueB);
		
		//initializes listener
		list = new eqListener();
		
		//first equation label & text field
		eqL1 = new JLabel("RPN1 =", SwingConstants.CENTER);
		eqL1.setBounds(15,15,50,20);
		eqL1.setBackground(redX);
		eqL1.setForeground(Color.WHITE);
		eqL1.setOpaque(true);
		add(eqL1);
		
		eq1 = new JTextField();
		eq1.setBounds(65,15,354,20);
		eq1.setBorder(new EmptyBorder(0,5,0,0));
		eq1.addActionListener(list);
		add(eq1);
		
		//first equation upper and lower bounds
		ba1 = new JTextField();
		ba1.setHorizontalAlignment(JTextField.CENTER);
		ba1.setBounds(422,15,45,20);
		ba1.setBorder(new EmptyBorder(0,5,0,0));
		ba1.addActionListener(list);
		add(ba1);
		
		bb1 = new JTextField();
		bb1.setHorizontalAlignment(JTextField.CENTER);
		bb1.setBounds(470,15,45,20);
		bb1.setBorder(new EmptyBorder(0,5,0,0));
		bb1.addActionListener(list);
		add(bb1);
		
		//second equation label & text field
		eqL2 = new JLabel("RPN2 =", SwingConstants.CENTER);
		eqL2.setBounds(15,45,50,20);
		eqL2.setBackground(blueX);
		eqL2.setForeground(Color.WHITE);
		eqL2.setOpaque(true);
		add(eqL2);
		
		eq2 = new JTextField();
		eq2.setBounds(65,45,354,20);
		eq2.setBorder(new EmptyBorder(0,5,0,0));
		eq2.addActionListener(list);
		add(eq2);
		
		//second equation upper and lower bounds
		ba2 = new JTextField();
		ba2.setHorizontalAlignment(JTextField.CENTER);
		ba2.setBounds(422,45,45,20);
		ba2.setBorder(new EmptyBorder(0,5,0,0));
		ba2.addActionListener(list);
		add(ba2);
		
		bb2 = new JTextField();
		bb2.setHorizontalAlignment(JTextField.CENTER);
		bb2.setBounds(470,45,45,20);
		bb2.setBorder(new EmptyBorder(0,5,0,0));
		bb2.addActionListener(list);
		add(bb2);
		
		//third equation label & text field
		eqL3 = new JLabel("RPN3 =", SwingConstants.CENTER);
		eqL3.setBounds(15,75,50,20);
		eqL3.setBackground(greenX);
		eqL3.setForeground(Color.WHITE);
		eqL3.setOpaque(true);
		add(eqL3);
		
		eq3 = new JTextField();
		eq3.setBounds(65,75,354,20);
		eq3.setBorder(new EmptyBorder(0,5,0,0));
		eq3.addActionListener(list);
		add(eq3);
		
		//third equation upper and lower bounds
		ba3 = new JTextField();
		ba3.setHorizontalAlignment(JTextField.CENTER);
		ba3.setBounds(422,75,45,20);
		ba3.setBorder(new EmptyBorder(0,5,0,0));
		ba3.addActionListener(list);
		add(ba3);
		
		bb3 = new JTextField();
		bb3.setHorizontalAlignment(JTextField.CENTER);
		bb3.setBounds(470,75,45,20);
		bb3.setBorder(new EmptyBorder(0,5,0,0));
		bb3.addActionListener(list);
		add(bb3);
		
		//initializes graph
		graph = new exGraph();
		add(graph);
	}
	
	/**
	 * 
	 * Action Listener Class
	 *
	 */
	public class eqListener implements ActionListener
	{
		/**
		 * plugs equation into graph
		 * @param args0
		 */
		public void actionPerformed(ActionEvent arg0) 
		{
			String equation1 = eq1.getText();
			String equation2 = eq2.getText();
			String equation3 = eq3.getText();
			
			if (ba1.getText().trim().equals("")) 
				ba1.setText("0");
			if (ba2.getText().trim().equals("")) 
				ba2.setText("0");
			if (ba3.getText().trim().equals("")) 
				ba3.setText("0");
			if (bb1.getText().trim().equals("")) 
				bb1.setText("0");
			if (bb2.getText().trim().equals("")) 
				bb2.setText("0");
			if (bb3.getText().trim().equals("")) 
				bb3.setText("0");
			
			String[][] bounds = {{ba1.getText(), bb1.getText()}, {ba2.getText(), bb2.getText()}, {ba3.getText(), bb3.getText()}};
			graph.setEquations(equation1, equation2, equation3, bounds);
			graph.repaint();
		}
	}
}
