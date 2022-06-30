package com.example.capstone.time

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.R
import com.example.capstone.databinding.FragmentTimerBinding
import com.example.capstone.sendNotification

class TimerFragment : Fragment() {

    private var _seconds = MutableLiveData<Long>()
    val seconds: LiveData<Long>
        get() = _seconds

//    private var _timeRemain = MutableLiveData<Long>()
//    val timeRemain : LiveData<Long>
//        get() = _timeRemain

    lateinit var timer: CountDownTimer

//    private var isPause : Boolean = false

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

        binding.startId.setOnClickListener {
            try {
                _seconds.value =
                    Integer.parseInt(binding.secondId.text.toString()).toLong() * 1000 + 1000

                binding.startId.isEnabled = false
                binding.cancelId.isEnabled = true

                timer = object : CountDownTimer(_seconds.value!!, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        binding.timer.text = (millisUntilFinished / 1000).toString()
                    }

                    override fun onFinish() {
                        binding.timer.text = context!!.getString(R.string.done)
                        binding.motionLayout.progress = 0F
                        binding.startId.isEnabled = true
                        binding.cancelId.isEnabled = false
                        sendNotification(requireContext())
                    }
                }.start()

                binding.motionLayout.setTransitionDuration(_seconds.value!!.toInt())
                binding.motionLayout.transitionToEnd()
            } catch (e: Exception) {
                Toast.makeText(context, "Please Enter Timer in Seconds", Toast.LENGTH_LONG).show()
            }
        }

        binding.cancelId.setOnClickListener {
            timer.cancel()
            binding.startId.isEnabled = true
            binding.cancelId.isEnabled = false
            binding.motionLayout.progress = 0F
        }

        binding.clearId.setOnClickListener {
            binding.secondId.text.clear()
        }

        return binding.root
    }
}