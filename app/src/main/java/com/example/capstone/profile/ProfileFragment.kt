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
import com.example.capstone.repository.Repository
import com.example.capstone.viewmodel.SharedViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

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

        val binding = FragmentProfileBinding.inflate(inflater)

        val profileAdapter = ProfileAdapter()

        val navController = findNavController()

        binding.lifecycleOwner = viewLifecycleOwner

        binding.recyclerProfile.adapter = profileAdapter

        binding.fab.setOnClickListener {
            //Toast.makeText(this.context, "FAB CLICKED", Toast.LENGTH_LONG).show()
            navController.navigate(R.id.action_profileFragment_to_profileDetailFragment)
        }

//        (binding.recyclerProfile.adapter as ProfileAdapter)
//            .notifyDataSetChanged()

        (binding.recyclerProfile.adapter as ProfileAdapter)
            .submitList(ArrayList(viewModel.templates))

        return binding.root
    }

}