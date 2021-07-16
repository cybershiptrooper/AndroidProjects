package com.example.roomworldsample

import android.app.Application
import com.example.roomworldsample.database.DescRepository
import com.example.roomworldsample.database.WordRepository
import com.example.roomworldsample.database.WordRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication: Application()
{
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())
    
    
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
    val wordRepo by lazy { WordRepository(database.wordDao()) }
    val descRepo by lazy { DescRepository(database.descDao()) }
}