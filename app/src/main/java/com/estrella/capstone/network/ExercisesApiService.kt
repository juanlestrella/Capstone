package com.estrella.capstone.network

import com.estrella.capstone.Constants
import com.estrella.capstone.entities.ExercisesData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

interface ExercisesApiService {

    @GET("exercises")
    suspend fun getExercises(
        @Header("X-RapidAPI-Host") rapidHost: String,
        @Header("X-RapidAPI-Key") rapidKey: String
    ): List<ExercisesData>
}

object ExercisesApi {
    val retrofitService: ExercisesApiService by lazy {
        retrofit.create(ExercisesApiService::class.java)
    }
}