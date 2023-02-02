package com.app.berlinclock.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.berlinclock.model.BerlinClockData
import com.app.berlinclock.utils.BerlinClock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @Author: Manoj Kumar
 * @Date: 01/02/23
 */
class BerlinClockViewModel(private val berlinClock: BerlinClock) : ViewModel() {

    private lateinit var timer: CountDownTimer
    private var _berlinClockTime = MutableStateFlow(BerlinClockData())
    val berlinClockTime: StateFlow<BerlinClockData> = _berlinClockTime.asStateFlow()
    private var _berlinCurrentTime = MutableLiveData<String>()
    val berlinCurrentTime: LiveData<String> = _berlinCurrentTime

    fun updateBerlinClockInitialState() {
        _berlinClockTime.value = BerlinClockData.initial()
    }

    fun updateBerlinClock(time: String) {
        val result = berlinClock.getBerlinClock(time)
        _berlinClockTime.value = result
    }

    fun startBerlinClock() {
        timer = object : CountDownTimer(COUNT_DOWN_MILLIS,
                                        COUNT_DOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                val currentTime: String = SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(Date())
                _berlinCurrentTime.value = currentTime
                updateBerlinClock(currentTime)

            }
            override fun onFinish() {
                start()
            }
        }
        timer.start()
    }

    companion object {
        const val COUNT_DOWN_MILLIS = 600000L
        const val COUNT_DOWN_INTERVAL = 1000L
        const val TIME_FORMAT = "HH:mm:ss"
    }
}