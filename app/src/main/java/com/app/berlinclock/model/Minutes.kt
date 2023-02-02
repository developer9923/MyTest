package com.app.berlinclock.model

import com.app.berlinclock.utils.LampColor

/**
 * @Author: Manoj Kumar
 * @Date: 01/02/23
 */
data class Minutes(
        val topColors: List<LampColor> = defaultTop(),
        val bottomColors: List<LampColor> = defaultBottom()
) {
    companion object {
        fun defaultBottom() = MutableList(4) { LampColor.OFF }
        fun defaultTop() = MutableList(11) { LampColor.OFF }
    }
}