# Pi-Cipher

  During my sophomore year of high school, I decided to have fun and create a cipher.
I've always been interested in the idea of ciphers, that a message could be hidden in plain sight,
so to speak. I also have a large interest in mathematics. I decided to use one of my favorite numbers,
(of which I devoted time to memorizing 150 of its digits) as the basis of the cipher. The process
of encrypting plaintext is relatively simple.<br>

  The cipher revolves around the first five digits of pi, 3 1 4 1 5. When using any of the numbers
that are not "1", the value of the letter in the original string is multiplied by that number.
If the product of this is larger than 26, then 26 must repeatedly be subtracted from the product until
it yields a value between 1 and 26. When using a "1", the value of the letter in the original string
is simply incremented by 1. If the letter happens to be "Z", then the corresponding letter is wrapped
around to "A". Starting from the first letter, you would use the number 3, then 1, then 4, then 1,
then 5, then back to 3, and so on.<br>

  Let's say that you have the string "HELLOWORLD". The "H" in the string has a value of 8. Since
we are at the start of the string, we will start by using the number 3. 8*3 = 24, and the value of
24 corresponds to the letter "X". The next letter in the string, "E", will then use the number 1.
When using the number 1, the value of the letter is incremented by one. Incrementing the value
of "E" leads to the letter "F". The next letter in the string, "L" is then multiplied
by 4. "L" has a value of 12. 12 * 4 = 48, and since 48 does not correspond to any letter, we subtract
26 from it until it corresponds to a letter. Since 48 - 26 * 1 = 22, "L" corresponds to "V". Moving on to
the next letter "L", we add 1 to its value and get "M". Notice that the cipher assigned the two L's
in the original string two different values, even though they are the same letter.
Then, we take the next letter, "O", and multiply its value by 5. 15 * 5 = 75, and
since 75 is greater than 26, we need to subtract 26 from it twice in order to make it correspond to a
letter value. 75 - 26 * 2 = 75 - 52 = 23, so "O" corresponds to the letter "W". When we get to the
letter "W" in our original string, the cycle repeats, and we start back at the number 3.
The final result of the cipher yields: HELLOWORLD = XFVMWQPTMT.<br>

  Decrypting the ciphertext is a bit challenging. When dealing with the numbers 3, 4, and 5, you must first
see if the letter value is divisible by the number. If it is not, then you add 26 to the letter value and
see if the new value is divisible by the current pi number. Once the number 130 is reached or passed
(130 is 26 * 5), then the process stops. If the original letter value or the modified
value is divisible by the current pi number, then we can say that this is the plaintext letter. However,
there are some occasions where this process may yield multiple letters (usually two if this occurs). This
happens infrequently enough that one can usually tell which one is correct. However, because of this, when
decrypting any ciphertext, if more than one solution exists, these solutions are surrounded by square brackets,
which indicates that one of the letters inside must be correct. When dealing with the number 1, simply decrement
the ciphertext letter's value by one. If the ciphertext letter is "A", then it wraps around to "Z". This will never
result in multiple solutions, and will always yield the proper plaintext.<br>

  In our previous example, the ciphertext was XFVMWQPTMT. First, the value of "X" is divided by 3. Since
X's value of 24 is divisible by 3, the quotient solution 24 / 3 = 8 is assumed to be the corresponding
plaintext, which is the letter "H". We then add 26 to the value of X, which yields 24 + 26 = 50. Since 50 is not
divisible by 3, we can ignore it. Next, 26 is added to 50, which yields 26 + 50 = 76. 76 is also not divisible
by 3, so we can ignore it. 26 is then added to 76 to yield 26 + 76 = 102. 102 / 3 is equal to 34. However,
34 does not correspond to any letter value, so it is also ignored. Since adding any more to the modified letter
value will never yield a number of 26 or less when divided by 3, we can stop. A good rule of thumb is that if
the modified letter value exceeds 130, then the process should terminate since 130 is the largest possible
product of a letter value and a number in the pi sequence ("Z" * 5 = 130). Therefore, anything about 130 is
guaranteed to have a quotient larger than 26. Next, the value of "F" is decremented by 1, yielding the letter
"E". Next, we divide the value of the letter "V" by 4. This process is repeated until we get the result:
XFVMWQPTMT = HE[LY]LOWO[ER]LD. Notice that the letter "V" and the first "T" in the ciphertext both yielded
two solutions. However, with the context of the other letters, we can assume which letters are correct.<br>

  Take some time to play with the cipher on the home page. It may not be perfect (especially when decrypting),
but it has a special place in my heart &#9825;.
