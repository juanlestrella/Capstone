package com.example.capstone.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.capstone.entities.ExercisesData
import retrofit2.http.QueryName

@Dao
interface ExercisesDao {

    /**
     * Gets a list of LiveData to keep the RecyclerView Updated
     */
    @Query("SELECT * FROM exercises")
    fun getExercises(): List<ExercisesData>

    /**
     * Finds a specific exercise from the exercises table
     */
    @Query("SELECT * FROM exercises WHERE name = :queryName ")
    fun findExerciseWithName(queryName: String): List<ExercisesData>

    /**
     * Gets all the body parts
     */
    @Query("SELECT DISTINCT bodyPart FROM exercises")
    fun getAllBodyParts(): List<String>

    /**
     * Gets all the equipments
     */
    @Query("SELECT DISTINCT equipment FROM exercises")
    fun getAllEquipments(): List<String>

    /**
     * Gets all the targets
     */
    @Query("SELECT DISTINCT target FROM exercises")
    fun getAllTargets(): List<String>

    /**
     * Filter to show only selected body part
     */
    @Query("SELECT * FROM exercises WHERE bodyPart = :bodyPart")
    fun filterBodyPart(bodyPart: String): List<ExercisesData>

    /**
     * Filter to show only selected equipment
     */
    @Query("SELECT * FROM exercises WHERE equipment = :equipment")
    fun filterEquipment(equipment: String): List<ExercisesData>

    /**
     * Filter to show only selected target
     */
    @Query("SELECT * FROM exercises WHERE target = :target")
    fun filterTarget(target: String): List<ExercisesData>

    /**
     * Insert an exercise in the table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExercises(exercisesData: ExercisesData)
}

@Database(entities = [ExercisesData::class], version = 1)
abstract class ExercisesDatabase : RoomDatabase() {
    abstract val exercisesDao: ExercisesDao
}

private lateinit var INSTANCE: ExercisesDatabase
fun getExercisesDataBase(context: Context): ExercisesDatabase {
    synchronized(ExercisesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context,
                ExercisesDatabase::class.java,
                "exerises_database"
            ).allowMainThreadQueries().build()
        }
        return INSTANCE
    }
}