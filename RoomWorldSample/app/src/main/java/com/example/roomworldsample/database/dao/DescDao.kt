package com.example.roomworldsample.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomworldsample.model.Description
import com.example.roomworldsample.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface DescDao
{
    @Query("select * from word_desc where word=:word")
    fun getDesc(word : String): LiveData<List<Description>>
    
    @Query("select Definition from word_desc where word=:word")
    fun getDefinitions(word: String): LiveData<List<String>>
    
    @Update
    suspend fun updateDesc(desc: Description)
    
    @Delete
    suspend fun deleteDesc(desc: Description)
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(desc: Description)
    
    @Query("DELETE FROM Word_desc")
    suspend fun deleteAll()
}