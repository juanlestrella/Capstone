package com.example.capstone.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.databinding.FragmentExercisesBinding
import com.example.capstone.repository.Repository

class ExercisesFragment : Fragment() {

    private val viewModel: ExercisesViewModel by lazy {
        ViewModelProvider(
            this, ExercisesViewModel.Factory(this.requireActivity().application)
        ).get(ExercisesViewModel::class.java)
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

        val binding = FragmentExercisesBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        binding.recyclerExercises.adapter = ExercisesAdapter()

        binding.recyclerExercises.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))

        viewModel.exercisesList.observe(viewLifecycleOwner){
            listData -> (binding.recyclerExercises.adapter as ExercisesAdapter).submitList(listData)
        }

        return binding.root
    }
}