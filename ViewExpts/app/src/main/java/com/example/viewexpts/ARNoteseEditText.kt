package layout

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.example.viewexpts.LongClickListener

class ARNoteseEditText(context : Context, attrs : AttributeSet? = null):
    androidx.appcompat.widget.AppCompatEditText(context, attrs)
{
    ///////////////////////////////////////
    //Fonts Manager(Text mode)
    override fun onTextChanged(
        text : CharSequence?,
        start : Int,
        lengthBefore : Int,
        lengthAfter : Int
    )
    {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
//        var before: String = (text as String).substring(0, lengthBefore)
//        var after: String = (text as String).substring(lengthBefore, lengthAfter)
//        for(i in stack){
//            before = before.substring(0, before.length-3)
//        }
//        var str = before + after
//        for(i in stack){
//
//        }
    }
    
    override fun onFocusChanged(focused : Boolean, direction : Int, previouslyFocusedRect : Rect?)
    {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if(focused){
        
        }
    }
    
    ////////////////////////////////////
    //Drag mode
    init{
        setOnLongClickListener(LongClickListener())
    }
    
    companion object{
        //var stack = mutableListOf<ARNotesTextMode>()
    }
}