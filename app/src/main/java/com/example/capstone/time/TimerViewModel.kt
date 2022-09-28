package com.example.capstone.time

import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {

    private val _time = MutableLiveData<Int>()
    val time: LiveData<Int>
        get() = _time

    val hoursArray: ArrayList<String> = (0..61).map { it.toString() } as ArrayList<String>
    val minutesArray: ArrayList<String> = arrayListOf()
    val secondsArray: ArrayList<String> = arrayListOf()

    fun setTime(hours: Long = 0, minutes: Long = 0, seconds: Long = 0): String {
        val hoursToSeconds = hours * 3600
        val minutesToSeconds = minutes * 60
        var totalSeconds = hoursToSeconds + minutesToSeconds + seconds
        return DateUtils.formatElapsedTime(totalSeconds)
    }


}