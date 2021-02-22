import java.awt.Color;
import java.awt.Graphics;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JPanel;


/**
 * 
 * @author Paul Scott
 * @version April 12th, 2017
 * 
 * This file draws the graph and interprets equations as
 * either function, polar, or parametric
 *
 */
public class exGraph extends JPanel
{
	//instance data
	private static final long serialVersionUID = 1L;
	private String[][] allEquations;
	private double[][] allBounds;
	
	//Colors
	private Color redX = new Color(244,70,70);
	private Color blueX = new Color(0,141,229);
	private Color greenX = new Color(41,155,89);
	
	//Equation Parser
	private exEquation parse = new exEquation();

	/**
	 * constructor
	 */
	public exGraph()
	{
		setBounds(15,105,500,500);
		setBackground(Color.WHITE);
		setEquations("","","", null);
	}
	
	/**
	 * displays graphs
	 * @param g
	 */
	public void paintComponent(Graphics g)
	{
		//super call
		super.paintComponent(g);
		
		//draws axes
		g.drawLine(250, 0, 250, 500);
		g.drawLine(0, 250, 500, 250);
		for (int x = 50; x < 500; x += 50)
		{
			int add = 4;
			if (x != 250) g.drawString(((x-250) / 25) + "", 260, (500 - x) + add);
			g.drawLine(245, x, 255, x);
			g.drawLine(x, 245, x, 255);
		}
		
		//runs through all equations
		for (int e = 0; e < allEquations.length; e++)
		{
			if (e == 0) g.setColor(redX);
			else if (e == 1) g.setColor(blueX);
			else g.setColor(greenX);
			
			double xPrev = 0;
			double yPrev = 0;
			
			//checks if equations are blank
			if (allEquations[e].length > 0)
			{
				boolean found = false;
				
				//checks if equation is a function or polar
				for (int check = 0; check < allEquations[e].length; check++)
				{
					////////////////////////////////////////////////////////////////////////////////////////////////////// Function ///////////////////////////////////////////////////////////////////////////////////
					
					if ((allEquations[e][check].equals("x") || allEquations[e][check].equals("(x)"))&& found == false)
					{
						//checks to see if range is logical
						if (allBounds[e][0] < allBounds[e][1])
						{
							//calculates number of "steps" between the range
							double difference = allBounds[e][1] - allBounds[e][0];
							double addition = (difference / ((int)(difference) * 25));
							
							//calculates values to graph
							for (double x = allBounds[e][0]; x <= allBounds[e][1]; x += addition)
							{
								BigDecimal bigX = new BigDecimal(x).setScale(5, RoundingMode.HALF_EVEN);
								x = bigX.doubleValue();
								
								String[] split = new String[allEquations[e].length];
								for (int var = 0; var < allEquations[e].length; var++) split[var] = allEquations[e][var];
								
								double y = (parse.solveEquation(split, x) * -25.0) + 250.0;
								
								if (!(Double.isNaN(y)) && !(Double.isInfinite(y)))
								{
									BigDecimal bigY = new BigDecimal(y).setScale(5, RoundingMode.HALF_EVEN);
									y = bigY.doubleValue();
								}
								
								double distance = Math.sqrt(Math.pow(x - xPrev, 2.0) + Math.pow(y - yPrev, 2.0));
								
								double xGraph = (x * 25.0) + 250.0;
								
								if (x > allBounds[e][0] && distance < 500 && !(Double.isNaN(y)) && !(Double.isInfinite(y)) && !(Double.isNaN(yPrev)) && !(Double.isInfinite(yPrev))) 
									g.drawLine((int)xPrev, (int)yPrev, (int)xGraph, (int)y);
								
								xPrev = xGraph;
								yPrev = y;
							}
							
							found = true;
						}
					}
					
					////////////////////////////////////////////////////////////////////////////////////////////////////// Parametric ///////////////////////////////////////////////////////////////////////////////////
					
					else if (allEquations[e][check].equals("t") && found == false)
					{
						String[] parX = null;
						String[] parY = null;
						
						//checks to see if range is logical
						if (allBounds[e][0] < allBounds[e][1])
						{
							//calculates number of "steps" between the range
							double difference = allBounds[e][1] - allBounds[e][0];
							double addition = difference / (((int)difference) * 50);
							
							//calculates values to graph
							for (double t = allBounds[e][0]; t <= allBounds[e][1]; t += addition)
							{
								BigDecimal bigT = new BigDecimal(t).setScale(5, RoundingMode.HALF_EVEN);
								t = bigT.doubleValue();
								
								String[] split = new String[allEquations[e].length];
								for (int var = 0; var < allEquations[e].length; var++) split[var] = allEquations[e][var];
								
								//splits parametric into its x and y equation
								for (int andCheck = 0; andCheck < split.length; andCheck++)
								{
									if (split[andCheck].equals("&"))
									{
										String[] parX1 = new String[andCheck];
										String[] parY1 = new String[split.length - andCheck -1];
										
										for (int i = 0; i < split.length; i++)
										{
											if (i < andCheck) 
												parX1[i] = split[i];
											
											else if (i > andCheck) 
												parY1[i - parX1.length - 1] = split[i];
										}
										
										parX = parX1;
										parY = parY1;
									}
								}
								
								double x = (parse.solveEquation(parX, t) * 25.0) + 250.0;
								double y = (parse.solveEquation(parY, t) * -25.0) + 250.0;
								
								if (!(Double.isNaN(x)) && !(Double.isInfinite(x)))
								{
									BigDecimal bigX = new BigDecimal(x).setScale(5, RoundingMode.HALF_EVEN);
									x = bigX.doubleValue();
								}
								
								if (!(Double.isNaN(y)) && !(Double.isInfinite(y)))
								{
									BigDecimal bigY = new BigDecimal(y).setScale(5, RoundingMode.HALF_EVEN);
									y = bigY.doubleValue();
								}
								
								double distance = Math.sqrt(Math.pow(x - xPrev, 2.0) + Math.pow(y - yPrev, 2.0));
								
								if (t > allBounds[e][0] && distance < 500 && !(Double.isNaN(y)) && !(Double.isInfinite(y)) && !(Double.isNaN(yPrev)) && !(Double.isInfinite(yPrev))) 
									g.drawLine((int)xPrev, (int)yPrev, (int)x, (int)y);
	
								xPrev = x;
								yPrev = y;
							}
							
							found = true;
						}
					}
					
					////////////////////////////////////////////////////////////////////////////////////////////////////// Polar ///////////////////////////////////////////////////////////////////////////////////
					
					else if ((allEquations[e][check].equals("theta") || allEquations[e][check].equals("(theta)")) && found == false)
					{
						//checks to see if range is logical
						if (allBounds[e][0] < allBounds[e][1])
						{
							//calculates number of "steps" between the range
							double difference = allBounds[e][1] - allBounds[e][0];
							double addition = difference / (((int)difference) * 80);
							
							//calculates values to graph
							for (double theta = allBounds[e][0]; theta <= allBounds[e][1]; theta += addition)
							{
								BigDecimal bigTheta = new BigDecimal(theta).setScale(5, RoundingMode.HALF_EVEN);
								theta = bigTheta.doubleValue();
								
								String[] split = new String[allEquations[e].length];
								for (int var = 0; var < allEquations[e].length; var++) split[var] = allEquations[e][var];
								
								double r = parse.solveEquation(split, theta);
								
								if (!(Double.isNaN(r)) && !(Double.isInfinite(r)))
								{
									BigDecimal bigR = new BigDecimal(r).setScale(5, RoundingMode.HALF_EVEN);
									r = bigR.doubleValue();
								}
								
								double x = (r * Math.cos(theta) * 25.0) + 250.0;
								double y = (r * Math.sin(theta) * -25.0) + 250.0;
								
								if (!(Double.isNaN(x)) && !(Double.isInfinite(x)))
								{
									BigDecimal bigX = new BigDecimal(x).setScale(5, RoundingMode.HALF_EVEN);
									x = bigX.doubleValue();
								}
								
								if (!(Double.isNaN(y)) && !(Double.isInfinite(y)))
								{
									BigDecimal bigY = new BigDecimal(y).setScale(5, RoundingMode.HALF_EVEN);
									y = bigY.doubleValue();
								}
								
								double distance = Math.sqrt(Math.pow(x - xPrev, 2.0) + Math.pow(y - yPrev, 2.0));
								
								if (theta > allBounds[e][0] && distance < 500 && !(Double.isNaN(y)) && !(Double.isInfinite(y)) && !(Double.isNaN(yPrev)) && !(Double.isInfinite(yPrev)))
									g.drawLine((int)xPrev, (int)yPrev, (int)x, (int)y);
								
								xPrev = x;
								yPrev = y;
							}
							
							found = true;
						}
					}
				}
			}
		}
	}
	
	/**
	 * sets equations for graph
	 * @param eq1
	 * @param eq2
	 * @param eq3
	 */
	public void setEquations(String eq1, String eq2, String eq3, String[][] bounds)
	{
		//sets equations and splits equations parts
		allEquations = new String[3][];
		allEquations[0] = eq1.split(" ");
		allEquations[1] = eq2.split(" ");
		allEquations[2] = eq3.split(" ");
		
		//sets temporary bounds
		double[][] boundsTmp = {{0, 0}, {0, 0}, {0, 0}};
		allBounds = boundsTmp;
		
		//sets bounds
		if (bounds != null)
		{
			for (int b = 0; b < bounds.length; b++)
			{
				for (int c = 0; c < bounds[b].length; c++)
				{
					allBounds[b][c] = parse.solveEquation(bounds[b][c].split(" "), 0);
				}
			}
		}
	}
}
