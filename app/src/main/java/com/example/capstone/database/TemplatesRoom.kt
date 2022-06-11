package com.example.capstone.database

import android.content.Context
import androidx.room.*
import com.example.capstone.Converter
import com.example.capstone.entities.TemplatesData

@Dao
interface TemplatesDao {

    @Query("SELECT * FROM templates")
    fun getTemplates(): List<TemplatesData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTemplates(templatesData: TemplatesData)
}

@Database(entities = [TemplatesData::class], version = 1)
@TypeConverters(Converter::class)
abstract class TemplatesDatabase : RoomDatabase(){
    abstract val templatesDao: TemplatesDao
}

private lateinit var INSTANCE: TemplatesDatabase
fun getTemplatesDatabase(context: Context): TemplatesDatabase {
    synchronized(TemplatesDatabase::class.java) {
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
                context,
                TemplatesDatabase::class.java,
                "templates_database"
            ).allowMainThreadQueries().build()
        }
        return INSTANCE
    }
}