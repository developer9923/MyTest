package com.app.berlinclock.model

import com.app.berlinclock.utils.LampColor

/**
 * @Author: Manoj Kumar
 * @Date: 01/02/23
 */
data class BerlinClockData(val secondsOnLamp: LampColor? = null,
                           val minutesOnLamps: Minutes? = null,
                           val hoursOnLamps: Hours? = null
) {
    companion object {
        fun initial() = BerlinClockData(LampColor.OFF, Minutes(), Hours())
    }
}
