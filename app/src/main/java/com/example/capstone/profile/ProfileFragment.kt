package com.example.capstone.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.capstone.R
import com.example.capstone.databinding.FragmentProfileBinding
import com.example.capstone.repository.Repository
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(
            this, ProfileViewModel.Factory(this.requireActivity().application)
        ).get(ProfileViewModel::class.java)
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

        val binding = FragmentProfileBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        return binding.root
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.main_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val navController = findNavController()
//
//        return when(item.itemId){
//            R.id.logout_item -> {
//                FirebaseAuth.getInstance().signOut()
//                navController.popBackStack() // TODO: navigate back to authFragment
//                //Toast.makeText(activity, "clicked", Toast.LENGTH_SHORT).show()
//                return true
//            }
//            R.id.profileFragment -> {
//                Log.i("Profile", "Clicked on Profile")
//                Toast.makeText(requireContext(), "click on profile", Toast.LENGTH_LONG).show()
//                return true
//            }
//
//            R.id.exercises_item -> {
//                Toast.makeText(requireContext(), "click on exercises", Toast.LENGTH_LONG).show()
//                //navController.navigate(R.id.action_profileFragment_to_exercisesFragment)
//                return true
//            }
//
//            R.id.history_item -> {
//                Toast.makeText(requireContext(), "click on history", Toast.LENGTH_LONG).show()
//                return true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//
//        //return super.onOptionsItemSelected(item)
//    }
}