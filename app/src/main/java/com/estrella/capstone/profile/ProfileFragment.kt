package com.estrella.capstone.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.estrella.capstone.R
import com.estrella.capstone.databinding.FragmentProfileBinding
import com.estrella.capstone.viewmodel.SharedViewModel

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
            /** Removes previous list of exercises */
            viewModel.clearFinalExercisesList()
            /** Navigate to Profile Detail Fragment */
            navController.navigate(R.id.action_profileFragment_to_profileDetailFragment)
        }
        /**
         * If the template is saved in the ProfileFragment (by clicking the Done Button)
         * then delete the previous edited fragment.
         * Reset the isEditTemplateSaved to false so that the app knows that
         * there is no template getting edit.
         *
         */
        if (viewModel.isEditTemplateSaved){
            viewModel.deleteTemplate(viewModel.currentEditingTemplate)
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