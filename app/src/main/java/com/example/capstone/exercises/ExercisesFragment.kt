package com.example.capstone.exercises

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
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
import com.example.capstone.entities.ExercisesData
import com.example.capstone.viewmodel.SharedViewModel

const val KEY_CHECKED = "key_exercise_checked_boxes"

class ExercisesFragment : Fragment() {
    /** ViewModel shared by all fragments in the profile activity **/
    private val viewModel: SharedViewModel by activityViewModels()

    private val exercisesAdapter = ExercisesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        if(savedInstanceState != null){
            val state = savedInstanceState.getParcelableArrayList<Parcelable>(KEY_CHECKED)
            state?.forEach {
                exercisesAdapter.checkBoxList.add(it as ExercisesData)
            }
            Log.i("ExercisesFragment", exercisesAdapter.checkBoxList.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentExercisesBinding.inflate(inflater)

        val navController = findNavController()

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val arrLst = exercisesAdapter.returnCheckBoxList() as ArrayList<Parcelable>
        outState.putParcelableArrayList(KEY_CHECKED, arrLst)
    }
}