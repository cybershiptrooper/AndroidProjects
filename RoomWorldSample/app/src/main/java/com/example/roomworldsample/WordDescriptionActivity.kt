package com.example.roomworldsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.roomworldsample.adapters.DescListAdapter

class WordDescriptionActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        var word = intent.getStringExtra("word")
        setContentView(R.layout.activity_word_description)
        
        val textView = findViewById<TextView>(R.id.word_disp)
        val recyclerView = findViewById<RecyclerView>(R.id.def_disp)
        if(word != null)
        {
            textView.text = word
            val adapter =  DescListAdapter()
            MainActivity.viewModel.getDesc(word).observe(this,
                 Observer { words -> // Update the cached copy of the words in the adapter.
                    words?.let { adapter.submitList(it) }
                })
            recyclerView.setAdapter(adapter)
        }
        else{
            returnToMain()
        }
    }
    
    fun returnToMain(){
        //setResult(Activity.RESULT_CANCELED, Intent())
        startActivity(Intent())
    }
    
}