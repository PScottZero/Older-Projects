/**
 *
 * @author Paul Scott
 *
 * @version 6th April 2018
 *
 * This program performs the lucas-lehmer primality test in order to find
 * large mersenne prime numbers (prime numbers that take the form 2^p-1,
 * where p is a prime number)
 *
 */

import java.math.BigInteger

// lucas-lehmer primality test class
class LucasLehmerTest {

    // companion object with main method
    companion object {
        @JvmStatic fun main(args : Array<String>) {
            var power = BigInteger("3")
            if (args.size == 1) {
                power = args[0].toBigInteger()
                if (power.remainder(BigInteger("2")) == BigInteger.ZERO) {
                    power++
                }
            }

            val ll = LucasLehmerTest()
            ll.runTest(power)
        }
    }

    // big integer constants
    private val zero = BigInteger.ZERO
    private val one = BigInteger.ONE
    private val two = BigInteger("2")

    // finds if number is prime using lucas-lehmer primality test
    private fun isPrime(number : BigInteger, power : BigInteger) : Boolean {
        var currLucas = BigInteger("4")
        var count = one

        // builds lucas-lehmer sequence
        while (count < power - one) {
            currLucas = (currLucas.pow(2) - two).remainder(number)
            count++
        }

        return currLucas == zero
    }

    // checks if power is likely to be prime using fermat primality test
    private fun isProbablePrime(power : BigInteger) : Boolean {
        return getMersenne(power - one).remainder(power) == zero
    }

    // returns possible mersenne prime (in form 2^power-1)
    private fun getMersenne(power : BigInteger) : BigInteger {
        var count = one
        var total = two

        // finds 2^power
        while (count < power) {
            total = total.multiply(two)
            count++
        }

        return total - one
    }

    private fun getTime(time1 : Long, time2 : Long) : String {
        var str = ""
        var elapsed = (time2 - time1) / 1000.0
        str += (elapsed.toInt() / 3600).toString() + "h "
        elapsed %= 3600
        str += (elapsed.toInt() / 60).toString() + "m "
        str += String.format("%.3fs", elapsed % 60)
        return str
    }

    // performs lucas-lehmer test on odd powers
    fun runTest(power : BigInteger) {
        val start = System.currentTimeMillis()
        var time1 = start
        var currPow = power

        // test runs until command window is closed
        while (true) {

            // checks to see if odd power is likely to be prime
            if (isProbablePrime(currPow)) {
                val number = getMersenne(currPow)

                // checks if mersenne number is prime
                if (isPrime(number, currPow)) {
                    val time2 = System.currentTimeMillis()
                    println(String.format("2^%d-1:\n%d\n", currPow, number))
                    println("Time Since Last Prime Found: " + getTime(time1, time2))
                    println("Total Time Elapsed: " + getTime(start, time2) + "\n")
                    time1 = time2
                }
            }
            currPow += two
        }
    }
}