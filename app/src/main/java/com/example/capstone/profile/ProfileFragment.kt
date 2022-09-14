package com.example.capstone.profile

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.R
import com.example.capstone.databinding.FragmentProfileBinding
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

        val profileAdapter = ProfileAdapter(viewModel)

        val navController = findNavController()

        binding.lifecycleOwner = viewLifecycleOwner

        binding.recyclerProfile.adapter = profileAdapter

        /**
         * adds divider per viewholder
         */
        binding.recyclerProfile.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )

        binding.fab.setOnClickListener {
            viewModel.clearFinalExercisesList()
            /** Navigate to Profile Detail Fragment */
            navController.navigate(R.id.action_profileFragment_to_profileDetailFragment)
        }

        if (viewModel.isEditTemplateSaved){
            viewModel.deleteTemplate(viewModel.current_editing_template)
            viewModel.isEditTemplateSaved = false
        }

        /**
         * Observe templates' LiveData and submit it to the recycler view's adapter
         */
        viewModel.templates.observe(viewLifecycleOwner) { listTemplate ->
            (binding.recyclerProfile.adapter as ProfileAdapter).submitList(listTemplate)
        }

        return binding.root
    }

}