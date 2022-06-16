package com.example.capstone.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone.R
import com.example.capstone.databinding.FragmentExercisesBinding
import com.example.capstone.viewmodel.SharedViewModel

class ExercisesFragment : Fragment() {
    /** ViewModel shared by all fragments in the profile activity **/
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

        val binding = FragmentExercisesBinding.inflate(inflater)

        val navController = findNavController()

        val exercisesAdapter = ExercisesAdapter()

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        binding.recyclerExercises.adapter = exercisesAdapter

        /** Creates a divider per View Holder **/
        binding.recyclerExercises.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )

        /** Observe LiveData of exercisesList
         * and submit it to the ExercisesAdapter **/
        viewModel.exercisesList.observe(viewLifecycleOwner) { listData ->
            (binding.recyclerExercises.adapter as ExercisesAdapter).submitList(listData)
        }

        /**
         * Add all the checked exercises in the SharedViewModel
         * Navigate to ProfileDetailFragment
         */
        binding.AddFAB.setOnClickListener {
            viewModel.setCheckedExercisesList(exercisesAdapter.returnCheckBoxList())
            // Navigate to ProfileDetailFragment
            navController.navigate(R.id.action_exercisesFragment_to_profileDetailFragment)
        }

        /**
         * uncheck all the checked boxes
         */
        binding.ClearFAB.setOnClickListener {
            viewModel.checkBoxAllFalse()
            // instantly change the checkboxes view
            exercisesAdapter.notifyDataSetChanged()
        }

        return binding.root
    }
}