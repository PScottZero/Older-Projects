import sys
from enigma_rotors import *
from enigma_reflectors import *

class Enigma:

    def __init__(self):
        self.reflector = None
        self.config = None
        self.plugboard = {}

    def get_rotor(self, i):
        return {
            '1': rotor_i,
            '2': rotor_ii,
            '3': rotor_iii,
            '4': rotor_iv,
            '5': rotor_v,
        }[i]

    def get_reflector(self, i):
        i = i.upper()
        return {
            'A': reflector_a,
            'B': reflector_b,
            'C': reflector_c,
        }[i]

    def config_machine(self, r1, r2, r3, ref, p1, p2, p3):
        if p1.isalpha(): p1 = ord(p1.upper()) - 64
        if p2.isalpha(): p2 = ord(p2.upper()) - 64
        if p3.isalpha(): p3 = ord(p3.upper()) - 64
        rotor1, rotor2, rotor3 = self.get_rotor(r1), self.get_rotor(r2), self.get_rotor(r3)
        self.config = [[rotor1, int(p1) - 1], [rotor2, int(p2) - 1], [rotor3, int(p3) - 1]]
        self.reflector = self.get_reflector(ref)

    def config_plugboard(self, plug_list):
        old, new = "", ""
        for plug in plug_list:
            old += plug[0]
            old += plug[1]
            new += plug[1]
            new += plug[0]
        self.plugboard = str.maketrans(old, new)

    def shift(self):
        shifted = False
        self.config[2][1] = (self.config[2][1] + 1) % 26
        if (self.config[2][1] == self.config[2][0]['Turnover']) or (self.config[1][1] == self.config[1][0]['Turnover'] - 1):
            self.config[1][1] = (self.config[1][1] + 1) % 26
            shifted = True
        if self.config[1][1] == self.config[1][0]['Turnover'] and shifted:
            self.config[0][1] = (self.config[0][1] + 1) % 26

    def encrypt(self, plaintext):
        ciphertext = ""
        plaintext = plaintext.translate(self.plugboard)
        for letter in plaintext:
            letter = letter.upper()
            self.shift()
            for i in reversed(range(len(self.config))):
                rotor = self.config[i][0]
                shift = self.config[i][1]
                if i < 2: shift -= self.config[i+1][1]
                temp = ord(letter) - 64 + shift
                if temp > 26: temp %= 26
                elif temp < 1: temp += 26
                key = chr(temp + 64)
                letter = rotor[key]
            temp = ord(letter) - 64 - self.config[0][1]
            if temp > 26: temp %= 26
            elif temp < 1: temp += 26
            letter = chr(temp + 64)
            letter = self.reflector[letter]
            for i in range(len(self.config)):
                rotor = {v: k for k, v in self.config[i][0].items()}
                shift = self.config[i][1]
                if i > 0: shift -= self.config[i-1][1]
                temp = ord(letter) - 64 + shift
                if temp > 26: temp %= 26
                elif temp < 1: temp += 26
                key = chr(temp + 64)
                letter = rotor[key]
            temp = ord(letter) - 64 - self.config[2][1]
            if temp > 26: temp %= 26
            elif temp < 1: temp += 26
            letter = chr(temp + 64)
            ciphertext += letter
        ciphertext = ciphertext.translate(self.plugboard)
        return ciphertext


e = Enigma()
e.config_machine(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4], sys.argv[5], sys.argv[6], sys.argv[7])
plug_list = []
if len(sys.argv) > 8:
    for i in range(8, len(sys.argv) - 1):
        plug_list.append(sys.argv[i])
e.config_plugboard(plug_list)
print(e.encrypt(sys.argv[-1]))
