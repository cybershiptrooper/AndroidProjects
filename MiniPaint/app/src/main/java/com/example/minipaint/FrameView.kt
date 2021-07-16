package com.example.minipaint

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.ContextMenu
import android.view.MotionEvent
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView

class FrameView(context : Context, attrs: AttributeSet): FrameLayout(context, attrs)
{
   
    
    override fun onInterceptTouchEvent(ev : MotionEvent?) : Boolean
    {
        when(MainActivity.NotesOperation)
        {
            ARNotesOperation.ADD_TEXT ->{
                val ta = findViewById<TextView>(R.id.textView)
//                ta.bringToFront()
                ta.requestFocus()
            }
            ARNotesOperation.ADD_DRAW -> {
//                findViewById<CanvasView>(R.id.Canvas).bringToFront()
            }
        }
        return super.onInterceptTouchEvent(ev)
    }
    
    override fun onTouchEvent(event : MotionEvent?) : Boolean
    {
        return super.onTouchEvent(event)
    }
    
    override fun setOnClickListener(l : OnClickListener?)
    {
        if(!CanvasView.returnFromDown && MainActivity.NotesOperation== ARNotesOperation.ADD_DRAW)
        {
            val ta = findViewById<TextView>(R.id.textView)
            ta.performClick()
            CanvasView.returnFromDown = true
        }
        super.setOnClickListener(l)
    }
}