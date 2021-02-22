# The Hypothetical Computer


INSTRUCTION SET FOR HC2021 PROCESSOR
(C) 1974 PSC SYSTEMS INC.


**ALU INSTRUCTIONS**

10000RRR ADD RRR *;adds accumulator and RRR*                [Carry ON if >255]

10010RRR SUB RRR *;subtracts RRR from accumulator* 		  [Carry ON if <0]

10100RRR CMP RRR *;compare accumulator with RRR* 			  [Greater ON if acc > RRR]

10110RRR EQL RRR *;checks if accumulator and RRR are equal* [Equal ON if acc = RRR]

11000RRR ZER RRR *;checks if RRR is zero* 			      [Zero ON if RRR = 0]

11010RRR INC RRR *;increments RRR by one* 				  [Carry ON if >255]

11100RRR DEC RRR *;decrements RRR by one* 				  [Carry ON if <0]



**GENERAL INSTRUCTIONS**

00010RRR XXXXXXXX LD(RRR) XXXXXXXX *;loads data at address X into RRR*

00100RRR XXXXXXXX ST(RRR) XXXXXXXX *;stores data in RRR into address X*

00110JJJ XXXXXXXX JM(JJJ) XXXXXXXX *;jump to address X if condition met*

01000000 XXXXXXXX JMP XXXXXXXX     *;jump to address X*

0RRA1RRB MOV RRA,RRB               *;moves RRA into RRB*

11110RRR LDA (RRR)                 *;loads data from address specified by RRR into accumulator*

11111RRR STA (RRR)				   *;stores accumulator into address specified by RRR*



**REGISTER CODES**

A = 000

B = 001

C = 010

X = 011

Y = 100

Z = 101



**JUMP CONDITIONS**

Carry   ON = 000 OFF = 001

Greater ON = 010 OFF = 011

Equal   ON = 100 OFF = 101

Zero    ON = 110 OFF = 111
