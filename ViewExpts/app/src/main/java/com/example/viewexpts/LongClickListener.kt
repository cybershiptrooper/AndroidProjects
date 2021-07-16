package com.example.viewexpts

import android.content.ClipData
import android.view.View
import android.view.View.OnLongClickListener

class LongClickListener : OnLongClickListener
{
    override fun onLongClick(v : View) : Boolean
    {
        val dragdata = ClipData.newPlainText("", "")
        val shdwbldr = View.DragShadowBuilder(v)
        v.startDrag(dragdata, shdwbldr, v, 0)
        v.setVisibility(View.INVISIBLE)
        return true
    }
}