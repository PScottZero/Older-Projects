/**
 *
 * @author Paul Scott
 * @version 8 August 2018
 *
 * This class represents a single driver, which
 * includes data such as driver name, loginTimes,
 * and motion times.
 *
 */

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Driver(private val name: String, private var loginTimes: ArrayList<BusInfo>) {

    // returns driver name
    fun getName(): String { return name }

    // returns last name of driver (used for sorting)
    fun getLastName(): String { return name.split(" ")[1] }

    // returns login times of driver
    fun getLoginTimes(): ArrayList<BusInfo> { return loginTimes }

    // adds login time
    fun addLoginTime(busNumber: String, loginTime: String, motionTime: String) {
        val format = DateTimeFormatter.ofPattern("M/d/yyyy H:mm")
        loginTimes.add(BusInfo(busNumber, LocalDateTime.parse(loginTime, format), motionTime))
    }

    // adds multiple login times
    fun addLoginTimes(times: ArrayList<BusInfo>) {
        loginTimes.addAll(times)
        loginTimes.sortWith(Comparator.comparing(BusInfo::getLoginTime))
    }

    // returns size of login array list
    fun getLoginSize(): Int { return loginTimes.size }
}