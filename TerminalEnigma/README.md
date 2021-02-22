# Terminal Enigma V0.2

**How to use:**

In terminal or in command line, navigate to folder with all three files inside. Type in enigma.py followed by the following arguments (in order, space separated):

{rotor1} {rotor2} {rotor3} {reflector} {rotor1-position} {rotor2-position} {rotor3-position} {plugboard} {plaintext-no-spaces}

**Plugboard:**

For the plugboard option, type the two letters to be swapped next to each other. If you want to swap B and C, you can write BC or CB (both do the same thing). Also, if you want to do multiple swaps, separate each pair of letters by a space.

**Files and Purposes:**

- enigma.py >>> Main program
- enigma-rotors.py >>> File with all rotor configurations
- enigma-reflectors.py >>> File with all reflector configurations
- add-rotor.py >>> Allows you to add rotor configurations
- add-reflector.py >>> Allows you to add reflector configurations
- text-generator.py >>> generates long string of text to be put into machine FOR TESTING PURPOSES
- check-if-equal.py >>> checks if two strings are equal (used in conjunction with 3rd party enigma simulator) FOR TESTING PURPOSES
