package com.example.roomworldsample.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.roomworldsample.database.dao.DescDao
import com.example.roomworldsample.model.Description
import com.example.roomworldsample.model.Word
import kotlinx.coroutines.flow.Flow

class DescRepository(private val Dao: DescDao)
{
    @WorkerThread
    suspend fun insert(desc: Description)
    {
        Dao.insert(desc)
    }
    
    fun getDesc(word: String): LiveData<List<Description>>
    {
        return Dao.getDesc(word)
    }
}