package com.example.capstone.exercises

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.capstone.database.getDataBase
import com.example.capstone.entities.ExercisesData
import com.example.capstone.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class ExercisesViewModel(application: Application) : ViewModel() {

    private val _status = MutableLiveData<String>()
    val status : LiveData<String>
        get() = _status

    private val database = getDataBase(application)
    private val repository = Repository(database)

    init {
        viewModelScope.launch {
            try{
                repository.refreshData()
                _status.postValue("Connected")
                Log.i("ExercisesViewModel", repository.exercisesList.value?.get(0).toString())
            }catch (e: Exception){
                _status.postValue("Failure: " + e.message)
            }
        }
    }

    val exercisesList = repository.exercisesList



    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ExercisesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ExercisesViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }
}