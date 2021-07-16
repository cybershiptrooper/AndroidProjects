package com.example.viewexpts

import android.os.Bundle
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import layout.ARNoteseEditText
import java.security.AccessController.getContext

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //drag and drop mode
        var notesView = findViewById<FrameLayout>(R.id.notesView)
        notesView.setOnDragListener(DragListener())
        findViewById<Button>(R.id.add).setOnClickListener{
                val editText = ARNoteseEditText(notesView.context)
                notesView.addView(editText, getParams(0, 0))
        }
        
        //Text mode
        findViewById<Button>(R.id.bold).setOnClickListener{
            bold = !bold
        }
        findViewById<Button>(R.id.italics).setOnClickListener{
            italics = !italics
        }
        findViewById<Button>(R.id.normal).setOnClickListener{
            bold = false
            italics = false
        }
    }
    
    
    companion object{
        var bold = false
        var italics = false
        lateinit  var activeTA: ARNoteseEditText
    
        fun getParams(x: Int, y: Int, initDim: Int?=null) : FrameLayout.LayoutParams
        {
            var defaultWidthOfView = ViewGroup.LayoutParams.WRAP_CONTENT
            var defaultHeightOfView = ViewGroup.LayoutParams.WRAP_CONTENT
            if(initDim!=null){
                defaultHeightOfView=initDim
                defaultWidthOfView=initDim
            }
            val params = FrameLayout.LayoutParams(defaultWidthOfView, defaultHeightOfView)
            params.leftMargin = x
            params.topMargin = y
            return params
        }
    }
}