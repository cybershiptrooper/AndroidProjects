package com.example.roomworldsample

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomworldsample.*
import com.example.roomworldsample.adapters.OnItemClickListener
import com.example.roomworldsample.adapters.WordsAdapter
import com.example.roomworldsample.database.DescRepository
import com.example.roomworldsample.database.WordRepository
import com.example.roomworldsample.database.WordRoomDatabase
import com.example.roomworldsample.model.Description
import com.example.roomworldsample.model.Word
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainActivity : AppCompatActivity(), OnItemClickListener
{
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory(
            (application as WordsApplication).wordRepo,
            (application as WordsApplication).descRepo
        )
    }
    
    private val newWordActivityRequestCode = 1
    
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter =  WordsAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel = wordViewModel
        
        wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.submitList(it) }
        })
    
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewWordActivity.EXTRA_REPLY_WORD)?.let {
                val word =  Word(it)
                val wordStr = it
                data?.getStringExtra(NewWordActivity.EXTRA_REPLY_DESC)?.let {
                    val desc = Description(wordStr, it)
                    wordViewModel.addDesc(desc)
                }?: run{
                    wordViewModel.insertWord(word)
                }
            }
            
            
            
        } else {
            Toast.makeText(
                applicationContext, R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }
    override fun onItemClicked(text : String?)
    {
        val intent = Intent(this@MainActivity, WordDescriptionActivity::class.java)
        intent.putExtra("word", text)
        if(text != null)
        {
            var defs: ArrayList<String> = ArrayList()
            intent.putStringArrayListExtra("defs",defs)
        }
        startActivity(intent)
    }
    companion object{
        lateinit var viewModel : WordViewModel
        val applicationScope = CoroutineScope(SupervisorJob())
    
    }
}