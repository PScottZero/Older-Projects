/**
 *
 * @author Paul Scott
 * @version 15 August 2018
 *
 * This file takes the asset history csv file and
 * pulls out information relevant for calculating
 * driving time for all drivers in a given week
 *
 */

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import javax.swing.JFileChooser

class ReportParse(fileDir: String) {

    companion object {
        val buses = arrayListOf(ArrayList<String>())
        val fileLines = ArrayList<String>()
        val drivers = ArrayList<Driver>()
        val dates = ArrayList<String>()
        val csvLines = ArrayList<String>()
    }

    init {

        Report.reportArea.text = "Generating Report...\n\n"

        drivers.clear()
        fileLines.clear()
        buses.clear()
        dates.clear()
        csvLines.clear()

        // reads all lines from asset history csv
        File(fileDir).forEachLine { fileLines.add(it) }
        fileLines.removeAt(0)

        // finds first day of week
        var minDate = LocalDateTime.MAX
        for (line in fileLines) {
            val time = convertToLocalTime(line.split(',')[1])

            if (time < minDate) {
                minDate = time
            }
        }
        val firstDay = "" + minDate.monthValue + "." + minDate.dayOfMonth + "." + minDate.year

        // separates lines by bus number
        Report.reportArea.append("Separating buses by number...\n")
        while (fileLines.size > 0) {
            val busNumber = fileLines[1].split(',')[0].trim()
            val history = ArrayList<String>()

            // finds file lines with matching bus number
            var i = 0
            while (i < fileLines.size) {
                val currentNumber = fileLines[i].split(',')[0].trim()
                if (currentNumber == busNumber) {
                    history.add(0, fileLines.removeAt(i))
                    i--
                }
                i++
            }
            buses.add(history)
        }

        // pulls important bus information
        Report.reportArea.append("\nFinding drivers and driving info...\n")
        for (bus in buses) {

            var i = 0
            while (i < bus.size) {

                val lineSplit = bus[i].split(",")

                // if driver has logged into bus
                if (bus[i].contains("has entered a startup code")) {

                    // finds name of driver
                    val nameSplit = lineSplit[6].split(" +".toRegex())
                    var name = nameSplit[0] + " " + nameSplit[1]
                    name = name.replace("\"", "")

                    // only add driver if they are not the previous driver
                    if (drivers.size == 0 || drivers.last().getName() != name) {
                        val busNumber = lineSplit[0]

                        if (name != "Easton Mechanic"
                                && name != "Easton Washbay"
                                && name != "Third Party") {
                            Report.reportArea.append("   Found $name on bus $busNumber\n      Finding motion times...\n")
                        }

                        val loginTimes = ArrayList<BusInfo>()
                        drivers.add(Driver(name, loginTimes))

                        var j = i + 1
                        var motionTime = lineSplit[1]
                        while (j < bus.size) {

                            if (bus[j].contains("Motion Start")) {
                                motionTime = bus[j].split(",")[1]
                                break
                            }; j++
                        }

                        drivers.last().addLoginTime(lineSplit[0], lineSplit[1], motionTime)

                    } else {
                        val prevDate = drivers.last().getLoginTimes().last().getLoginDateAsString()
                        if (prevDate != lineSplit[1].split(" ")[0]) {
                            var j = i + 1
                            var motionTime = lineSplit[1]
                            while (j < bus.size) {

                                if (bus[j].contains("Motion Start")) {
                                    motionTime = bus[j].split(",")[1]
                                    break
                                }; j++
                            }

                            drivers.last().addLoginTime(lineSplit[0], lineSplit[1], motionTime)
                        }
                    }
                }

                // if driver has logged out of bus
                else if (bus[i].contains("Startup code cleared")) {

                    // remove latest logout time if one already exists else add new logout time
                    if (drivers.last().getLoginSize() % 2 == 0) {
                        drivers.last().getLoginTimes().removeAt(drivers.last().getLoginSize() - 1)
                    }

                    var j = i - 1
                    var motionTime = lineSplit[1]
                    while (j > 0) {

                        if (bus[j].contains("Motion Stop")) {
                            motionTime = bus[j].split(",")[1]
                            break
                        }; j--
                    }

                    drivers.last().addLoginTime(lineSplit[0], lineSplit[1], motionTime)
                }
                i++
            }

            // add time if there is no logout time for the last driver
            if (drivers.last().getLoginTimes().size % 2 != 0) {
                val time = drivers.last().getLoginTimes().last().getLoginTime()
                val newTime = "" + time.monthValue + "/" + time.dayOfMonth + "/" + time.year
                drivers.last().addLoginTime(drivers.last().getLoginTimes().last().getBusNumber(), "$newTime 23:59", "$newTime 23:59")
            }
        }

        // combines data if the driver drove multiple buses
        mergeDrivers()

        // sorts drivers by last name
        drivers.sortWith(Comparator.comparing(Driver::getLastName))

        // prints data
        Report.reportArea.append("\nCalculating work hours...\n")
        csvLines.add("Driver Name, Date, Login, Logout, Motion Start, Motion End, Log Total, Motion Total, Difference\n")
        for (driver in drivers) {

            if (driver.getName() != "Easton Mechanic" && driver.getName() != "Easton Washbay" && driver.getName() != "Third Party") {

                Report.reportArea.append("   Calculating login and motion hours for " + driver.getName() + "\n")
                var i = 1

                // separates days where drivers drove overnight
                while (i < driver.getLoginSize()) {

                    val time1 = driver.getLoginTimes()[i-1]
                    val time2 = driver.getLoginTimes()[i]

                    if (time1.getLoginDateAsString() != time2.getLoginDateAsString()) {
                        val date1 = time1.getLoginDateAsString()
                        val date2 = time2.getLoginDateAsString()
                        val midnight = convertToLocalTime("$date1 23:59")
                        val nextDay = convertToLocalTime("$date2 0:00")
                        driver.getLoginTimes().add(i, BusInfo(time1.getBusNumber(), midnight, "$date1 23:59"))
                        driver.getLoginTimes().add(i+1, BusInfo(time2.getBusNumber(), nextDay, "$date2 0:00"))
                        i+=2
                    }
                    i+=2
                }

                // removes excess times
                i = 2
                while (i < driver.getLoginSize()) {
                    val time1 = driver.getLoginTimes()[i-1]
                    val time2 = driver.getLoginTimes()[i]

                    if (time1.getLoginDateAsString() == time2.getLoginDateAsString()) {
                        driver.getLoginTimes().removeAt(i-1)
                        driver.getLoginTimes().removeAt(i-1)
                    } else i+= 2
                }

                // finds hours of work per day
                var total = 0.0
                var motionTotal = 0.0
                for (j in 1 until driver.getLoginSize() step 2) {
                    val driverName = driver.getName()
                    val time1 = driver.getLoginTimes()[j-1]
                    val time2 = driver.getLoginTimes()[j]
                    val date = time1.getLoginDateAsString()
                    val timeString1 = time1.getLoginTimeAsString()
                    val timeString2 = time2.getLoginTimeAsString()
                    val hourDiff = hourDifference(time1.getLoginTime(), time2.getLoginTime())
                    total+= hourDiff

                    val hourDiffString = String.format("%.1f", hourDiff)

                    val motionTime1 = time1.getMotionTime()
                    val motionTime2 = time2.getMotionTime()
                    val motionString1 = motionTime1.split(" ")[1]
                    val motionString2 = motionTime2.split(" ")[1]
                    val motionDiff = hourDifference(convertToLocalTime(motionTime1), convertToLocalTime(motionTime2))

                    motionTotal+= motionDiff
                    val motionDiffString = String.format("%.1f", motionDiff)

                    val difference =String.format("%.1f", hourDiff - motionDiff)

                    csvLines.add("$driverName, $date, $timeString1, $timeString2, $motionString1, $motionString2, $hourDiffString, $motionDiffString, $difference\n")
                }

                // prints total login hours
                val totalString = String.format("%.1f", total)
                val totalMotionString = String.format("%.1f", motionTotal)
                val totalDiffString = String.format("%.1f", total - motionTotal)

                csvLines.add(",,,,,, $totalString, $totalMotionString, $totalDiffString\n\n")
            }
        }

        // prompts user for save location
        Report.reportArea.append("\nPrompting user for save location...\n")
        val chooser = JFileChooser(System.getProperty(("user.home")))
        chooser.selectedFile = File("Report - Week of $firstDay.csv")
        val option = chooser.showSaveDialog(null)

        if (option == JFileChooser.APPROVE_OPTION) {
            val directory = chooser.selectedFile.toString()

            // report confirmation
            Report.reportArea.append("\nSaving report... ($directory)\n")

            val write = BufferedWriter(FileWriter(directory))
            for (line in csvLines) write.append(line)
            write.close()

            // report confirmation
            Report.reportArea.append("\nReport generated successfully!\n")
        } else {
            // report cancellation
            Report.reportArea.append("\nReport generation cancelled\n")
        }
    }

    // merges duplicate drivers
    private fun mergeDrivers() {

        var d1 = 0

        while (d1 < drivers.size) {

            var d2 = d1 + 1
            while (d2 < drivers.size) {

                if (drivers[d1].getName() == drivers[d2].getName()) {
                    drivers[d1].addLoginTimes(drivers[d2].getLoginTimes())
                    drivers.removeAt(d2)
                    d2--
                }
                d2++
            }
            d1++
        }
    }

    // finds number of hours between two times
    private fun hourDifference(time1: LocalDateTime, time2: LocalDateTime): Double {
        val minutes = ChronoUnit.MINUTES.between(time1, time2)
        return minutes.toDouble() / 60.0
    }

    // converts date to local time
    private fun convertToLocalTime(time: String): LocalDateTime {
        val format = DateTimeFormatter.ofPattern("M/d/yyyy H:mm")
        return LocalDateTime.parse(time, format)
    }
}