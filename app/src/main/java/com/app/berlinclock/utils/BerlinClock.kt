package com.app.berlinclock.utils

import com.app.berlinclock.model.BerlinClockData
import com.app.berlinclock.model.Hours
import com.app.berlinclock.model.Minutes
import com.app.berlinclock.utils.LampColor.*
/**
 * @Author: Manoj Kumar
 * @Date: 01/02/23
 */
class BerlinClock {

    fun getBerlinClock(time: String): BerlinClockData {
        val timeComponents = time.split(":")
        val hours = getHours(timeComponents[ZERO].toInt())
        val minutes = getMinutes(timeComponents[ONE].toInt())
        val seconds = getSeconds(timeComponents[TWO].toInt())

        return BerlinClockData(
                secondsOnLamp = seconds,
                minutesOnLamps = minutes,
                hoursOnLamps = hours
        )
    }

    private fun getHours(hours: Int): Hours = when {
        hours.lessThanFive() -> getValueForHoursLessThanFive(hours)
        hours.greaterThanOrEqualsFive() -> getValueForHoursGreaterThanFive(hours)
        else -> defaultHours
    }

    private fun getMinutes(minutes: Int): Minutes = when {
        minutes.lessThanFive() -> getValueForMinutesLessThanFive(minutes)
        minutes.greaterThanOrEqualsFive() -> getValueForMinutesGreaterThanFive(minutes)
        else -> defaultMinutes
    }

    private fun getSeconds(sec: Int): LampColor =
            if (sec.isEven()) YELLOW else OFF

    private fun getValueForHoursLessThanFive(hours: Int): Hours =
            Hours(bottomColors = getHoursLampColors(hours, Hours.default()))

    private fun getValueForHoursGreaterThanFive(hours: Int): Hours {
        val numberOfLightsOnTopToBeTurnedON = hours / 5
        val numberOfLightsOnBottomToBeTurnedON = hours % 5

        return Hours(
                getHoursLampColors(numberOfLightsOnTopToBeTurnedON, Hours.default()),
                getHoursLampColors(numberOfLightsOnBottomToBeTurnedON, Hours.default())
        )
    }

    private fun getHoursLampColors(
            hours: Int,
            lampColor: MutableList<LampColor>
    ): MutableList<LampColor> {
        (1..hours).forEach { i ->
            lampColor[i - 1] = RED
        }
        return lampColor
    }

    private val defaultHours get() = Hours()

    private fun getValueForMinutesLessThanFive(minutes: Int) =
            Minutes(
                    bottomColors = getMinutesOnBottomLampColors(
                            minutes,
                            Minutes.defaultBottom()
                    )
            )

    private fun getValueForMinutesGreaterThanFive(minutes: Int): Minutes {
        val numberOfLightsOnTopToBeTurnedON = minutes / FIVE
        val numberOfLightsOnBottomToBeTurnedON = minutes % FIVE

        val minutesOfLampsOnTop = getMinutesOfLampsOnTop(numberOfLightsOnTopToBeTurnedON)
        val minutesOnBottom =
                getMinutesOnBottomLampColors(
                        numberOfLightsOnBottomToBeTurnedON,
                        Minutes.defaultBottom()
                )
        return Minutes(topColors = minutesOfLampsOnTop, bottomColors = minutesOnBottom)
    }

    private fun getMinutesOfLampsOnTop(numberOfLightsOnTopToBeTurnedON: Int): List<LampColor> {
        val minutesOfLampsOnTop = Minutes.defaultTop()
        (ONE..numberOfLightsOnTopToBeTurnedON).forEach { i ->
            if (i.multipleOfThree()) {
                minutesOfLampsOnTop[i - ONE] = RED
            } else {
                minutesOfLampsOnTop[i - ONE] = YELLOW
            }
        }
        return minutesOfLampsOnTop
    }

    private fun getMinutesOnBottomLampColors(
            minutes: Int,
            lampColor: MutableList<LampColor>
    ): List<LampColor> {
        (ONE..minutes).forEach { i ->
            lampColor[i - ONE] = YELLOW
        }
        return lampColor
    }

    private val defaultMinutes get() = Minutes()

    private fun Int.greaterThanOrEqualsFive() = this >= FIVE
    private fun Int.lessThanFive() = this < FIVE
    private fun Int.isEven() = this % TWO == ZERO
    private fun Int.multipleOfThree() = this % THREE == ZERO

    companion object {
        const val ZERO = 0
        const val ONE = 1
        const val TWO = 2
        const val THREE = 3
        const val FIVE = 5
    }
}
