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

        val navController = findNavController()

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.fab.setOnClickListener {
            //Toast.makeText(this.context, "FAB CLICKED", Toast.LENGTH_LONG).show()
            navController.navigate(R.id.action_profileFragment_to_profileDetailFragment)
        }

        return binding.root
    }
    /*
    TODO:
    1) create a FAB to add new template
    2) template: name, list of added exercises from recyclers view, start button, completed button
        (add later -> number of repetition and weights, delete or add more exercises)
    3) Users should be able to click on the FAB and will redirect to "Exercise fragment".
    4) In Exercise Fragment, users should be able to scroll down and click the "plus button" to add
        an exercise to the template.
    5) *** Is it possible to only show the "plus button" when coming from the Profile Fragment? ***
     */
}