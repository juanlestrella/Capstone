package com.example.capstone.entities

import androidx.room.*

@Entity(tableName = "templates")
data class TemplatesData (

    @ColumnInfo(name = "title")
    var title: String,

//    @Relation(parentColumn = "idTemplatesData",
//        entityColumn = "idExercisesData",
//        entity = ExercisesData::class)
    @ColumnInfo(name = "exercises")
    var exercises: List<ExercisesData>
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idTemplatesData")
    var idTemplatesData: Long = 0
}

