package com.example.viewexpts

import android.view.DragEvent
import android.view.View
import android.view.View.OnDragListener
import android.widget.FrameLayout

class DragListener : OnDragListener
{
    private lateinit var params : FrameLayout.LayoutParams
    var x = 0f
    var y = 0f
    override fun onDrag(v : View?, event : DragEvent) : Boolean
    {
        val view : View = event.localState as View
        when(event.action)
        {
            DragEvent.ACTION_DRAG_STARTED-> params = view.getLayoutParams() as FrameLayout.LayoutParams
            DragEvent.ACTION_DRAG_ENTERED->
            {
                x = event.x
                y = event.y
            }
            
            DragEvent.ACTION_DRAG_LOCATION ->
            {
                x = event.x
                y = event.y
            }
            
            DragEvent.ACTION_DROP->
            {
                x = event.x
                y = event.y
                params.leftMargin = x.toInt()
                params.topMargin = y.toInt()
                view.setLayoutParams(params)
                view.setVisibility(View.VISIBLE)
            }
            else->
            {
            }
        }
        return true
    }
}