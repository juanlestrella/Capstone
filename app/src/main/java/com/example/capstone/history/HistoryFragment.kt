package com.example.capstone.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.databinding.FragmentHistoryBinding
import com.example.capstone.viewmodel.SharedViewModel

class HistoryFragment : Fragment(){


//    private val viewModel: SharedViewModel by lazy {
//        ViewModelProvider(
//            this, SharedViewModel.Factory(this.requireActivity().application)
//        ).get(SharedViewModel::class.java)
//    }
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHistoryBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        return binding.root
    }
}