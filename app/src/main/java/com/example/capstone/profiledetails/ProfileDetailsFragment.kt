package com.example.capstone.profiledetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.capstone.R
import com.example.capstone.databinding.FragmentProfileDetailsBindingImpl
import com.example.capstone.viewmodel.SharedViewModel

class ProfileDetailsFragment : Fragment() {

//    private val viewModelFactory by lazy {
//        ViewModelProvider(
//            this, SharedViewModel.Factory(this.requireActivity().application)
//        ).get(SharedViewModel::class.java)
//    }

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentProfileDetailsBindingImpl.inflate(inflater)

        val navController = findNavController()

        val profileDetailsAdapter = ProfileDetailsAdapter()

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        binding.recyclerProfileDetails.adapter = profileDetailsAdapter

        // Empty = null, otherwise print all checked exercises list
        // Toast.makeText(context, viewModel.checkedExercisesList.value.toString(), Toast.LENGTH_LONG).show()

        /**
         * List of all checked boxes from the viewModel
         * then submit to ProfileDetailsAdapter
         */
        val finalList = viewModel.returnFinalExercisesList()
        (binding.recyclerProfileDetails.adapter as ProfileDetailsAdapter).submitList(finalList)

        /**
         * When users click add exercises, navigate to the ExercisesFragment
         * Note: Is it possible to make the boxes visible only when the user
         * comes from the ProfileDetailsFragments?
         */
        binding.addExercisesButtonId.setOnClickListener {
            navController.navigate(R.id.action_profileDetailFragment_to_exercisesFragment)
        }

        /**
         * After the list of exercises are added, the DONE button is pushed
         * and navigate back to ProfileFragment with Template Name and Exercises
         * passed.
         */
        binding.doneButtonId.setOnClickListener {
            //viewModel.clearFinalExercisesList()
            navController.navigate(R.id.action_profileDetailFragment_to_profileFragment)
        }

        return binding.root
    }
}