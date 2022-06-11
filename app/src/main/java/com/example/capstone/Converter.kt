package com.example.capstone

import androidx.room.TypeConverter
import com.example.capstone.entities.ExercisesData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    @TypeConverter
    fun fromExerciseData(value: List<ExercisesData>): String {
        val gson = Gson()
        val type = object : TypeToken<List<ExercisesData>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toExerciseData(value: String) : List<ExercisesData> {
        val gson = Gson()
        val type = object : TypeToken<List<ExercisesData>>() {}.type
        return gson.fromJson(value, type)
    }
}