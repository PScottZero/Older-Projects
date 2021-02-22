/**
 *
 * @author Paul Scott
 * @version 8 August 2018
 *
 * This file represents specific bus data key
 * to calculating active time and motion time
 * of a driver. These data include bus number,
 * login or logoff time, and relevant motion
 * start or stop time
 *
 */

import java.time.LocalDateTime

class BusInfo(private val busNumber: String, private val loginTime: LocalDateTime, private val motionTime: String) {

    // returns login time
    fun getLoginTime(): LocalDateTime { return loginTime }

    // returns login date as a string
    fun getLoginDateAsString(): String { return "" + loginTime.monthValue + "/" + loginTime.dayOfMonth + "/" + loginTime.year }

    // returns login time as a string
    fun getLoginTimeAsString(): String { return "" + loginTime.hour + ":" + String.format("%02d", loginTime.minute) }

    // returns motion time
    fun getMotionTime(): String { return motionTime }

    // returns bus number
    fun getBusNumber(): String { return busNumber }
}