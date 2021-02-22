# ExGraph
I should note that this isn't meant as a replacement for a graphing calculator or anything, but I created it simply
to test myself with Java. I am a huge fan of mathematics, and I wanted to try to challenge myself with a program that follows one of my favorite interests.

ExGraph has the capability to graph functions, polar equations, and parametric equations, as long as the equation is in Reverse Polish Notation (see below). In order to use each one, you only need to change the variable (x = function, theta = polar, t = parametric). For Functions and Polar, simply type in the equations and hit enter to graph. For functions and polar equations, if you want to graph a single constant, specify which type you want to graph by putting either '(x)' or '(theta)' after the constant. For parametric, separate your x and y equations with '&'. Also, the last two text fields in each equation row are used to designate the bounds of the graph. Note that the three equation fields can be filled with any of these equations, meaning you can graph a funciton, polar equation, and parametric equation at the same time.

The main reason I used Reverse Polish notation is that it allows for decoding equations to be handled much more efficiently. Reverse Polish notation (RPN) is simply putting the operator after the values you wish to operate on. The equation is always read from left to right, because it already handles order of operations. Here are some examples:

2 + 2 = 2 2 +

2 * 3 + 5 = 2 3 * 5 +

2 * (3 + 5) = 3 5 + 2 *

sin(3t) = 3 t * sin

ln(t) = t ln

2^2^3 = 2 2 3 ^ ^

etc.
