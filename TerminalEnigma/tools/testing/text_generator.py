from random import randint

longtext = ""
for i in range(1000):
    longtext += chr(randint(65, 90))

print(longtext)
