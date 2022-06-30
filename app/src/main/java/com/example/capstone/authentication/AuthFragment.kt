package com.example.capstone.authentication

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.capstone.databinding.AuthenticationBinding
import com.example.capstone.R
import com.firebase.ui.auth.AuthUI

class AuthFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    companion object {
        const val TAG = "AuthFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    private val viewModel by viewModels<AuthViewModel>()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<AuthenticationBinding>(
            inflater, R.layout.authentication, container, false)

        binding.loginButtonId.setOnClickListener{launchSignInFlow()}
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        // if user presses the back button, bring them back to home screen
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.popBackStack(R.id.authFragment, false)
        }

        // observe the auth state. If logged in, send user to RemindActivity. Else display error message
        viewModel.authState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                //AuthViewModel.AuthenticationState.AUTHENTICATED -> navController.navigate(R.id.action_authFragment_to_profileFragment)
                else -> Log.e(TAG, "Unauthenticated or Illegal Authentication user ($authenticationState)")
            }
        })

    }

    private fun launchSignInFlow() {
        // Give users the option to sign in / register with their email or Google account. If users
        // choose to register with their email, they will need to create a password as well.
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        // Create and launch sign-in intent. We listen to the response of this activity with the
        // SIGN_IN_RESULT_CODE code.
        // TODO: find another way to call startActivityForResult
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(),
            SIGN_IN_RESULT_CODE
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}