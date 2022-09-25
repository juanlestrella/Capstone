package com.example.capstone.database

import android.content.Context
import androidx.room.*
import com.example.capstone.entities.ExercisesData

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
    @Query("SELECT * FROM exercises WHERE name LIKE (:queryName || '%')")
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
     * Filter for exercises
     */
    @Query("SELECT * FROM exercises WHERE bodyPart LIKE :bodyPart and equipment LIKE :equipment and target LIKE :target")
    fun filterExercises(bodyPart: String, equipment: String, target: String) : List<ExercisesData>

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