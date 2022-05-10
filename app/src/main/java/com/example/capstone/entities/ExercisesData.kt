package com.example.capstone.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "exercises")
data class ExercisesData(

    @ColumnInfo(name = "bodyPart")
    var bodyPart: String,

    @ColumnInfo(name = "equipment")
    var equipment : String,

    @ColumnInfo(name = "gifUrl")
    var gifUrl : String,

    @PrimaryKey(autoGenerate = false)
    var id: String,

    @ColumnInfo(name = "name")
    var name : String,

    @ColumnInfo(name = "target")
    var target: String

)
