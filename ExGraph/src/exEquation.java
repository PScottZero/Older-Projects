import java.util.Stack;

/**
 * 
 * @author Paul Scott
 * @version April 12th, 2017
 * 
 * This file interprets entered equations
 *
 */
public class exEquation 
{
	public exEquation() {}
	
	/**
	 * solves equation for given x value using reverse polish notation
	 * @param vars
	 * @param x
	 * @return y value
	 */
	public double solveEquation(String[] vars, double x)
	{
		Stack<Double> stack = new Stack<Double>();

		if (vars[0].equals(""))
			return 0;
		
		for (int j = 0; j < vars.length; j++)
		{
			//replaces x with x value
			if (vars[j].equals("x") || vars[j].equals("theta") || vars[j].equals("t"))
			{
				vars[j] = x + "";
			}
			
			//replaces e with euler's number
			if (vars[j].equals("e"))
			{
				vars[j] = Math.E + "";
			}
			
			//replaces pi with decimal approximation of pi
			if (vars[j].equals("pi"))
			{
				vars[j] = Math.PI + "";
			}
			
			if (vars[j].equals("(x)") || vars[j].equals("(theta)"))
			{
				String[] varsMod = new String[vars.length - 1];
				for (int v = 0; v < varsMod.length; v++)
				{
					varsMod[v] = vars[v];
				}
				vars = varsMod;
			}
		}
		
		for (int i = 0; i < vars.length; i++)
		{
			////////////////////////////////////////////////// Basic Operations ////////////////////////////////////////////////////////////////
			
			//addition
			if (vars[i].equals("+"))
			{
				double b = stack.pop();
				double a = stack.pop();
				stack.add(a + b);
			}
			
			//subtraction
			else if (vars[i].equals("-"))
			{
				double b = stack.pop();
				double a = stack.pop();
				stack.add(a - b);
			}
			
			//multiplication
			else if (vars[i].equals("*"))
			{
				double b = stack.pop();
				double a = stack.pop();
				stack.add(a * b);
			}
			
			//division
			else if (vars[i].equals("/"))
			{
				double b = stack.pop();
				double a = stack.pop();
				stack.add(a / b);
			}
			
			//power
			else if (vars[i].equals("^"))
			{
				double b = stack.pop();
				double a = stack.pop();
				stack.add(Math.pow(a,b));
			}
			
			//absolute value
			else if (vars[i].equals("|"))
			{
				double a = stack.pop();
				stack.add(Math.abs(a));
			}
			
			////////////////////////////////////////////////// Trigonometry Radians ////////////////////////////////////////////////////////////////
			
			//sine
			else if (vars[i].equals("sin"))
			{
				double a = stack.pop();
				stack.add(Math.sin(a));
			}
			
			//arc sine
			else if (vars[i].equals("arcsin"))
			{
				double a = stack.pop();
				stack.add(Math.asin(a));
			}
			
			//cosine
			else if (vars[i].equals("cos"))
			{
				double a = stack.pop();
				stack.add(Math.cos(a));
			}
			
			//arc cosine
			else if (vars[i].equals("arccos"))
			{
				double a = stack.pop();
				stack.add(Math.acos(a));
			}
			
			//tangent
			else if (vars[i].equals("tan"))
			{
				double a = stack.pop();
				stack.add(Math.tan(a));
			}
			
			//arc tangent
			else if (vars[i].equals("arctan"))
			{
				double a = stack.pop();
				stack.add(Math.atan(a));
			}
			
			//secant
			else if (vars[i].equals("sec"))
			{
				double a = stack.pop();
				stack.add(1.0 / Math.cos(a));
			}
			
			//arc secant
			else if (vars[i].equals("arcsec"))
			{
				double a = stack.pop();
				stack.add(Math.acos(1.0 / a));
			}
			
			//cosecant
			else if (vars[i].equals("csc"))
			{
				double a = stack.pop();
				stack.add(1.0 / Math.sin(a));
			}
			
			//arc cosecant
			else if (vars[i].equals("arccsc"))
			{
				double a = stack.pop();
				stack.add(Math.asin(1.0 / a));
			}
			
			//cotangent
			else if (vars[i].equals("cot"))
			{
				double a = stack.pop();
				stack.add(1.0 / Math.tan(a));
			}
			
			//arc cotangent
			else if (vars[i].equals("arccot"))
			{
				double a = stack.pop();
				stack.add(Math.atan(1.0 / a));
			}
			
			////////////////////////////////////////////////// Trigonometry Degrees ////////////////////////////////////////////////////////////////
			
			//sine degrees
			else if (vars[i].equals("sind"))
			{
				double a = stack.pop();
				stack.add(Math.sin(Math.toRadians(a)));
			}
			
			//arc sine degrees
			else if (vars[i].equals("arcsind"))
			{
				double a = stack.pop();
				stack.add(Math.toDegrees(Math.asin(a)));
			}
			
			//cosine degrees
			else if (vars[i].equals("cosd"))
			{
				double a = stack.pop();
				stack.add(Math.cos(Math.toRadians(a)));
			}
			
			//arc sine degrees
			else if (vars[i].equals("arccosd"))
			{
				double a = stack.pop();
				stack.add(Math.toDegrees(Math.acos(a)));
			}
			
			//tangent degrees
			else if (vars[i].equals("tand"))
			{
				double a = stack.pop();
				stack.add(Math.tan(Math.toRadians(a)));
			}
			
			//arc tangent degrees
			else if (vars[i].equals("arctand"))
			{
				double a = stack.pop();
				stack.add(Math.toDegrees(Math.atan(a)));
			}
			
			//secant degrees
			else if (vars[i].equals("secd"))
			{
				double a = stack.pop();
				stack.add(1.0 / Math.cos(Math.toRadians(a)));
			}
			
			//arc secant degrees
			else if (vars[i].equals("arcsecd"))
			{
				double a = stack.pop();
				stack.add(Math.toDegrees(Math.acos(1.0 / a)));
			}
			
			//cosecant degrees
			else if (vars[i].equals("cscd"))
			{
				double a = stack.pop();
				stack.add(1.0 / Math.sin(Math.toRadians(a)));
			}
			
			//arc cosecant degrees
			else if (vars[i].equals("arccscd"))
			{
				double a = stack.pop();
				stack.add(Math.toDegrees(Math.asin(1.0 / a)));
			}
			
			//cotangent degrees
			else if (vars[i].equals("cotd"))
			{
				double a = stack.pop();
				stack.add(1.0 / Math.tan(Math.toRadians(a)));
			}

			//arc cotangent degrees
			else if (vars[i].equals("arccotd"))
			{
				double a = stack.pop();
				stack.add(Math.toDegrees(Math.atan(1.0 / a)));
			}
			
			////////////////////////////////////////////////// Logarithms ////////////////////////////////////////////////////////////////
			
			//log base 10
			else if (vars[i].equals("log"))
			{
				double a = stack.pop();
				stack.add(Math.log10(a));
			}
			
			//log base n
			else if (vars[i].equals("logb"))
			{
				double b = stack.pop();
				double a = stack.pop();
				stack.add(Math.log(a) / Math.log(b));
			}
			
			//natural log
			else if (vars[i].equals("ln"))
			{
				double a = stack.pop();
				stack.add(Math.log(a));
			}
			
			//adds numbers to stack
			else
			{
				stack.add(Double.parseDouble(vars[i]));
			}
		}
		//returns y or r value
		return stack.get(0);
	}
}
