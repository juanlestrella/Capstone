package com.example.capstone.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.databinding.FragmentHistoryBinding
import com.example.capstone.databinding.FragmentProfileBinding
import com.example.capstone.exercises.ExercisesViewModel
import com.example.capstone.profile.ProfileViewModel
import com.example.capstone.repository.Repository

class HistoryFragment : Fragment(){


    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(
            this, HistoryViewModel.Factory(this.requireActivity().application)
        ).get(HistoryViewModel::class.java)
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

        val binding = FragmentHistoryBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        return binding.root
    }
}