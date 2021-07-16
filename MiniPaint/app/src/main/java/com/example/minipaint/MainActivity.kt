package com.example.minipaint

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import java.security.AccessController.getContext

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val canvasView = findViewById<CanvasView>(R.id.Canvas)
        
        ////////////////////////
        //RESPONSIVE
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val dp = getResources().getDisplayMetrics().density;
        width = displayMetrics.widthPixels/dp
        height = displayMetrics.heightPixels/dp
        
        var font = 30f.norm
        val textView = findViewById<EditText>(R.id.textView)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_FRACTION, font * displayMetrics.widthPixels)
        textView.setGravity(Gravity.TOP)
        ///////////////////////
        //GET BUTTONS
        var drawbtn = findViewById<Button>(R.id.drawBtn)
        var scrollbtn = findViewById<Button>(R.id.scrollBtn)
        var clearbtn = findViewById<Button>(R.id.clearBtn)
        var writeBtn = findViewById<Button>(R.id.writeBtn)
        var undoBtn = findViewById<Button>(R.id.undo)
        var redoBtn = findViewById<Button>(R.id.redo)
        
        ///////////////////////
        //SET BUTTON LISTENERS
        drawbtn.setOnClickListener {
            draw()
        }
        scrollbtn.setOnClickListener {
            scroll()
        }
        clearbtn.setOnClickListener {
            findViewById<CanvasView>(R.id.Canvas).clear()
        }
        writeBtn.setOnClickListener {
            NotesOperation = ARNotesOperation.ADD_TEXT
        }
        undoBtn.setOnClickListener {
            canvasView.undo()
        }
        redoBtn.setOnClickListener {
            canvasView.redo()
        }
        
        context = this.applicationContext
    }
    
    fun draw(){
        NotesOperation = ARNotesOperation.ADD_DRAW
    }
    
    fun scroll(){
        NotesOperation = ARNotesOperation.SCROLL
    }
    companion object{
        var NotesOperation = ARNotesOperation.SCROLL
        lateinit var context: Context
        
        var width: Float = 0f
        var height: Float = 0f
    }
}