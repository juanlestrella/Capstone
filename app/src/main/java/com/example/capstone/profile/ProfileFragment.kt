package com.example.capstone.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.capstone.R
import com.example.capstone.databinding.FragmentProfileBinding
import com.example.capstone.exercises.ExercisesAdapter
import com.example.capstone.repository.Repository
import com.example.capstone.viewmodel.SharedViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

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

        val binding = FragmentProfileBinding.inflate(inflater)

        val profileAdapter = ProfileAdapter()

        val navController = findNavController()

        binding.lifecycleOwner = viewLifecycleOwner

        binding.recyclerProfile.adapter = profileAdapter

        binding.fab.setOnClickListener {
            /** Navigate to Profile Detail Fragment */
            navController.navigate(R.id.action_profileFragment_to_profileDetailFragment)
        }

        /**
         * Observe templates' LiveData and submit it to the recycler view's adapter
         */
        viewModel.templates.observe(viewLifecycleOwner) { listTemplate ->
            (binding.recyclerProfile.adapter as ProfileAdapter).submitList(listTemplate)
        }

        /**
         * TODO:
         * 2) Fix RecyclerView so the bottom has padding/margin -> Issue: bottom is cutted off
         * 3) Add Design to the UI
         * 4) Review Rubrics Guideline with the current app
         */

        return binding.root
    }

}