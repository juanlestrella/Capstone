package com.example.capstone.profiledetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.R
import com.example.capstone.databinding.FragmentProfileDetailsBindingImpl
import com.example.capstone.entities.ExercisesData
import com.example.capstone.entities.TemplatesData
import com.example.capstone.viewmodel.SharedViewModel
import com.google.android.material.snackbar.Snackbar

class ProfileDetailsFragment : Fragment() {

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

        /**
         * List of all checked boxes from the viewModel
         * then submit to ProfileDetailsAdapter
         */
        var finalList = viewModel.returnFinalExercisesList()
        (binding.recyclerProfileDetails.adapter as ProfileDetailsAdapter).submitList(finalList)

        /**
         * Swipe to Delete in recyclerProfileDetails
         * Using ItemTouchHelper
         */
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // get item position
                val position = viewHolder.adapterPosition
                // get the item being deleted
                val deletedExercise: ExercisesData = finalList.get(position)
                // delete from the viewmodel
                viewModel.deleteExercise(deletedExercise)
                // notify recycler view about the removed item
                (binding.recyclerProfileDetails.adapter as ProfileDetailsAdapter)
                    .notifyItemRemoved(viewHolder.adapterPosition)
                // user can undo the deleted exercise
//                Snackbar.make(binding.recyclerProfileDetails, "Deleted: " + deletedExercise.name, Snackbar.LENGTH_LONG)
//                    .setAction(
//                        "Undo",
//                        View.OnClickListener {
//                            // reinsert the deleted item
//                            viewModel.insertExercise(deletedExercise)
//
//                            (binding.recyclerProfileDetails.adapter as ProfileDetailsAdapter)
//                                .notifyItemInserted(position)
//                        }).show()
            }
        }).attachToRecyclerView(binding.recyclerProfileDetails)

        /**
         * When user clicks ADD EXERCISES, navigate to the ExercisesFragment
         */
        binding.addExercisesButtonId.setOnClickListener {
            navController.navigate(R.id.action_profileDetailFragment_to_exercisesFragment)
        }

        /**
         * After the list of exercises are added, click the DONE button
         * and navigate back to ProfileFragment with
         * Template's Title and list of Exercises
         */
        binding.doneButtonId.setOnClickListener {
            if (binding.templateNameEditviewId.text.isEmpty()) {
                Toast.makeText(context, "Please Enter Title", Toast.LENGTH_LONG).show()
            } else if (finalList.isEmpty()) {
                Toast.makeText(context, "Please Add Exercises", Toast.LENGTH_LONG).show()
            } else {
                val title = binding.templateNameEditviewId.text.toString()

                /**
                 * limit the title's characters to 3 to 8
                 */
                if (title.length < 3) {
                    Toast.makeText(
                        context,
                        "Title should be at least 3 characters",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (title.length > 12) {
                    Toast.makeText(
                        context,
                        "Title should be less than 12 characters",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    // insert new template
                    val newTemplatesData: TemplatesData = TemplatesData(title, finalList)
                    viewModel.insertNewTemplate(newTemplatesData)

                    // check if editing an old template or creating a new one
                    if (viewModel.isEditing) {
                        viewModel.isEditing = false
                        viewModel.isEditTemplateSaved = true
                    }

                    navController.navigate(R.id.action_profileDetailFragment_to_profileFragment)

                    viewModel.clearFinalExercisesList()
                }
            }
        }
        return binding.root
    }
}