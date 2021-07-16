package com.example.gsontestapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity()
{
    val write = writer()
    val read = reader()
    
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val twview = findViewById<TextView>(R.id.twrite)
        findViewById<Button>(R.id.writeToBtn).setOnClickListener{
            write.writeTo()
            twview.text = write.getPeople()
        }
        
        val trview = findViewById<TextView>(R.id.tread)
        
        findViewById<Button>(R.id.readFromBtn).setOnClickListener{
            read.readFrom()
            trview.text = read.getPeople()
        }
        
        
    
        val context: Context = this.applicationContext
        writer.filesDir = context.filesDir
        reader.filesDir = context.filesDir
    }
}