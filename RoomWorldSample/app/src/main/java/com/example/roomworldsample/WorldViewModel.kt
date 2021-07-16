package com.example.roomworldsample

import androidx.lifecycle.*
import com.example.roomworldsample.database.DescRepository
import com.example.roomworldsample.model.Word
import com.example.roomworldsample.database.WordRepository
import com.example.roomworldsample.model.Description
import kotlinx.coroutines.launch

class WordViewModel(private val wordRepo: WordRepository, private val descRepo: DescRepository) : ViewModel() {
    
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Word>> = wordRepo.allWords.asLiveData()
    
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertWord(word: Word) = viewModelScope.launch {
        wordRepo.insert(word)
    }
    
    fun addDesc(desc: Description) = viewModelScope.launch {
        wordRepo.insert(Word(desc.word))
        descRepo.insert(desc)
    }
    
    fun getDesc(word: String):  LiveData<List<Description>>
    {
        return descRepo.getDesc(word)
    }
    
}

class WordViewModelFactory(
    private val wordRepo: WordRepository,
    private val descRepo: DescRepository
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(wordRepo, descRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}