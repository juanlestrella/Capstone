package com.example.capstone.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone.Constants
import com.example.capstone.database.ExercisesDatabase
import com.example.capstone.entities.ExercisesData
import com.example.capstone.network.ExercisesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class Repository(private val database: ExercisesDatabase) {

    val exercisesList: LiveData<List<ExercisesData>> =
        database.exercisesDao.getExerises()

    suspend fun refreshData(){
        withContext(Dispatchers.IO){
            val newListExercises = ExercisesApi.retrofitService.getExercises(Constants.RAPID_HOST, Constants.KEY)
            newListExercises.forEach{
                newExercise -> database.exercisesDao.insertExercises(newExercise)
            }
        }
    }

}