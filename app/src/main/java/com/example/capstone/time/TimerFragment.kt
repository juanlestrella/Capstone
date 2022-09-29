package com.example.capstone.time

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.R
import com.example.capstone.databinding.FragmentTimerBinding
import com.example.capstone.sendNotification
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class TimerFragment : Fragment() {

    private var _seconds = MutableLiveData<Long>()
    val seconds: LiveData<Long>
        get() = _seconds

    lateinit var timer: CountDownTimer

    private val viewModel: TimerViewModel by lazy {
        ViewModelProvider(this).get(TimerViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentTimerBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.cancelId.isEnabled = false

        val hoursArray: ArrayList<String> = (0..23).map { it.toString() } as ArrayList<String>
        binding.hoursId.adapter = adapterHelper(hoursArray)
        val minutesArray: ArrayList<String> = (0..59).map { it.toString() } as ArrayList<String>
        binding.minutesId.adapter = adapterHelper(minutesArray)
        val secondsArray: ArrayList<String> = (0..59).map { it.toString() } as ArrayList<String>
        binding.secondsId.adapter = adapterHelper(secondsArray)

        binding.startId.setOnClickListener {
            try {
                val seconds = TimeUnit.SECONDS.toMillis(Integer.parseInt(binding.secondsId.selectedItem.toString()).toLong())
                val minutes = TimeUnit.MINUTES.toMillis(Integer.parseInt(binding.minutesId.selectedItem.toString()).toLong())
                val hours = TimeUnit.HOURS.toMillis(Integer.parseInt(binding.hoursId.selectedItem.toString()).toLong())
                _seconds.value = hours + minutes + seconds
                binding.indicatorId.max = _seconds.value!!.toInt()

                binding.startId.isEnabled = false
                binding.cancelId.isEnabled = true
                binding.clearId.isEnabled = false
                binding.secondsId.isEnabled = false
                binding.minutesId.isEnabled = false
                binding.hoursId.isEnabled = false

                timer = object : CountDownTimer(_seconds.value!!, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val duration = millisUntilFinished.toDuration(DurationUnit.MILLISECONDS)
                        binding.timer.text = duration.toComponents { hours, minutes, seconds, _ ->
                            String.format("%02d:%02d:%02d", hours, minutes, seconds)
                        }
                        binding.indicatorId.incrementProgressBy(1000)
                    }

                    override fun onFinish() {
                        binding.timer.text = context!!.getString(R.string.done)
                        binding.motionLayout.progress = 0F
                        binding.startId.isEnabled = true
                        binding.cancelId.isEnabled = false
                        binding.clearId.isEnabled = true
                        binding.secondsId.isEnabled = true
                        binding.minutesId.isEnabled = true
                        binding.hoursId.isEnabled = true
                        binding.indicatorId.progress = 0
                        sendNotification(requireContext())
                    }
                }.start()

                //binding.motionLayout.setTransitionDuration(_seconds.value!!.toInt())
                binding.motionLayout.transitionToEnd()
            } catch (e: Exception) {
                Toast.makeText(context, "Please Enter Time", Toast.LENGTH_LONG).show()
            }
        }

        binding.cancelId.setOnClickListener {
            timer.cancel()
            binding.startId.isEnabled = true
            binding.cancelId.isEnabled = false
            binding.clearId.isEnabled = true
            binding.secondsId.isEnabled = true
            binding.minutesId.isEnabled = true
            binding.hoursId.isEnabled = true
            binding.indicatorId.progress = 0
            binding.motionLayout.progress = 0F
        }

        binding.clearId.setOnClickListener {
            binding.secondsId.setSelection(0)
            binding.minutesId.setSelection(0)
            binding.hoursId.setSelection(0)
        }

        return binding.root
    }

    private fun adapterHelper(arr: ArrayList<String>): ArrayAdapter<String> {
        return ArrayAdapter(
            context!!,
            android.R.layout.simple_spinner_item,
            arr
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }
}