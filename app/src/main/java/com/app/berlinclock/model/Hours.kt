package com.app.berlinclock.model

import com.app.berlinclock.utils.LampColor

/**
 * @Author: Manoj Kumar
 * @Date: 01/02/23
 */
data class Hours(
        val topColors: List<LampColor> = default(),
        val bottomColors: List<LampColor> = default()
) {
    companion object {
        fun default() = MutableList(4) { LampColor.OFF }
    }
}