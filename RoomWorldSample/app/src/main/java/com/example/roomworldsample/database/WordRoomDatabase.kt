package com.example.roomworldsample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roomworldsample.database.dao.DescDao
import com.example.roomworldsample.database.dao.WordDao
import com.example.roomworldsample.model.Description
import com.example.roomworldsample.model.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Word::class, Description::class), version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase()
{
    abstract fun wordDao(): WordDao
    abstract fun descDao(): DescDao
    
    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        
        override fun onCreate(db: SupportSQLiteDatabase)
        {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.wordDao(), database.descDao())
                }
            }
        }
        
        suspend fun populateDatabase(wordDao: WordDao, descDao : DescDao) {
            // Delete all content here.
            wordDao.deleteAll()
            descDao.deleteAll()
        }
    }
    
    
    companion object{
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null
    
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WordRoomDatabase
        {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                    )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

