package com.example.capstone.entities

import androidx.room.*

@Entity(tableName = "templates")
/**
 * Constructor only need title and list of ExerciseData
 */
data class TemplatesData(

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "exercises")
    var exercises: List<ExercisesData>
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idTemplatesData")
    var idTemplatesData: Long = 0
}

