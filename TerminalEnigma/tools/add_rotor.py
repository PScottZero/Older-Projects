alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

name = raw_input("Rotor Name: ")
print("Alphabet:    " + alpha)
swap = raw_input("Swap String: ")
turn = raw_input("Turnover: ")

if turn.isalpha(): turn = str(ord(turn) - 64)

f = open('enigma_rotors.py', 'a')
f.write("\n" + name + " = {\n")
for i in range(len(swap)):
    f.write("\t'" + alpha[i] + "': '" + swap[i] + "',\n")
f.write("\t'Turnover': " + turn + ",\n")
f.write("}\n")
f.close()
