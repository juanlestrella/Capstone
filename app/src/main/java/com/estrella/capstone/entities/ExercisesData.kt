package com.estrella.capstone.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "exercises")
data class ExercisesData(

    @ColumnInfo(name = "bodyPart")
    var bodyPart: String,

    @ColumnInfo(name = "equipment")
    var equipment: String,

    @ColumnInfo(name = "gifUrl")
    var gifUrl: String,

    @PrimaryKey(autoGenerate = false)
    var id: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "target")
    var target: String,

    @ColumnInfo(name = "isChecked")
    var isChecked: Boolean = false

) : Parcelable
