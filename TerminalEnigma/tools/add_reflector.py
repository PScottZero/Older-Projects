alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

name = raw_input("Reflector Name: ")
print("Alphabet:    " + alpha)
swap = raw_input("Swap String: ")

f = open('enigma_reflectors.py', 'a')
f.write("\n" + name + " = {\n")
for i in range(len(swap)):
    f.write("\t'" + alpha[i] + "': '" + swap[i] + "',\n")
f.write("}\n")
f.close()
