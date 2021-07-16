package com.example.minipaint

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import kotlinx.coroutines.*
import java.lang.Integer.toHexString
import kotlin.math.roundToInt

class CanvasView(context : Context,
                 attrs: AttributeSet
): View(context, attrs)
{
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap
    
    init{
        this.setBackgroundColor(Color.TRANSPARENT)
    }
    ///////////////////////////////////////////////////////////////////////////////////
    //Linking Canvas Strokes: CRUD Support
    
    fun pushToStroke(x: Float, y: Float){
        canvasStrokes.last().add(x.dp/ MainActivity.width, y.dp/ MainActivity.width)
        undoStack = mutableListOf()
    }
    
    fun makeNewStroke(){
        canvasStrokes.add(
            ARNotesCanvasStroke(("#" + toHexString(drawColor)).toUpperCase(),
                                STROKE_WIDTH.roundToInt())
        )
    }
    
    fun resetStrokes(){
        canvasStrokes = mutableListOf()
    }
    
    fun drawStrokes(canvas : Canvas){
        for(stroke in canvasStrokes){
            val color = java.lang.Long.parseLong(stroke.color.substring(1), 16).toInt()
            val size = stroke.size
            val paintElem = getPaint(color, size.toFloat())
            var prevX = stroke.getCoords()[0][0].px* MainActivity.width
            var prevY = stroke.getCoords()[0][1].px* MainActivity.width
            
            var drawPath = Path()
            drawPath.reset()
            drawPath.moveTo(prevX, prevY)
            for(coord in stroke.getCoords()){
                var currX = coord[0].px* MainActivity.width
                var currY = coord[1].px* MainActivity.width
                drawPath.quadTo(
                    prevX,
                    prevY,
                    ( currX + prevX) / 2,
                    ( currY + prevY) / 2)
                
                prevX = currX
                prevY = currY
            }
            canvas.drawPath(drawPath, paintElem)
        }
    }
    
    fun clear(){
        resetStrokes()
        invalidate()
    }
    
    fun undo(){
        if(canvasStrokes.isEmpty()) return
        undoStack.add(canvasStrokes.last())
        canvasStrokes.remove(canvasStrokes.last())
        invalidate()
    }
    
    fun redo(){
        if(undoStack.isEmpty()) return
        canvasStrokes.add(undoStack.last())
        undoStack.remove(undoStack.last())
        invalidate()
    }
    
    ///////////////////////////////////////////////////////////////////////////////////
    //Override Methods
    
    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        //extraCanvas.drawColor(backgroundColor)
        //redraw = true
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawStrokes(canvas)
        canvas.drawPath(path, paint)
    }
    override fun onMeasure(widthMeasureSpec : Int, heightMeasureSpec : Int)
    {
        ////////////////////////////////////////
        //////CHANGE DEFAULT SIZE IF NEEDED/////
        ////////////////////////////////////////
        val desiredWidth = 500
        val desiredHeight = 500
        ////////////////////////////////////////
        
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        
        val width = when(widthMode)
        {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> Math.min(desiredWidth, widthSize)
            else -> desiredWidth
        }
        
        val height = when(heightMode)
        {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> Math.min(desiredHeight, heightSize)
            else -> desiredHeight
        }
        
        setMeasuredDimension(width, height)
    }
    
    ////////////////////////////////////////////////////////////////////////////////
    //Define Stroke, paint and path
    
    var STROKE_WIDTH = 12f
    var drawColor =  ResourcesCompat.getColor(resources, R.color.colorPaint, null)
    private val paint = getPaint(drawColor, STROKE_WIDTH)
    
    fun getPaint(drawcol : Int, width : Float) : Paint
    {
        return Paint().apply {
            color = drawcol
            isAntiAlias = true
            isDither = true
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = width
        }
    }
    
    //Current Stroke
    private var path = Path()
    
    ///////////////////////////////////////////////////////////
    //Manage touch
    
    /////////////////////////////////////
    ////////ARNotes Incompatible/////////
    /////////////////////////////////////
    fun checkDrawMode(): Boolean
    {
        return MainActivity.NotesOperation == ARNotesOperation.ADD_DRAW
    }
    
    fun checkControlMode(): Boolean
    {
        return MainActivity.NotesOperation != ARNotesOperation.SCROLL
    }
    /////////////////////////////////////
    /////////////////////////////////////
    
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f
    
    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y
        if(checkControlMode())
        {
            getParent().requestDisallowInterceptTouchEvent(true)
        }
        else
        {
            getParent().requestDisallowInterceptTouchEvent(false)
        }
        if(checkDrawMode())
        {
            when(event.action)
            {
                MotionEvent.ACTION_DOWN -> touchStart()
                MotionEvent.ACTION_MOVE -> touchMove()
                MotionEvent.ACTION_UP   -> touchUp()
            }
            if(!returnFromDown)context.toast(touchMode.toString())
            return returnFromDown
        }
        return false
        
    }
    
    private var currentX = 0f
    private var currentY = 0f
    private var touchMode: Int? = null
    
    private fun touchStartCRUD(){
        //<--CRUD
        makeNewStroke()
        pushToStroke(currentX, currentY)
        //--!>
    }
    
    private fun longlongTouch(){
        path.reset()
//        context.toast("Loong loooooong touch")
        returnFromDown = false
//        (this.getParent() as ViewGroup as FrameView).performClick()
//        returnFromDown = true
    }
    
//    @OptIn(DelicateCoroutinesApi::class)
    private fun touchStart(){
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
        touchMode = MotionEvent.ACTION_DOWN
        this.postDelayed({
            if(touchMode == MotionEvent.ACTION_DOWN)
            {
                longlongTouch()
            }
        }, 1000)
    }
    
    
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop
    private fun touchMove() {
        val dx = Math.abs(motionTouchEventX - currentX)
        val dy = Math.abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            if(touchMode == MotionEvent.ACTION_DOWN){
                returnFromDown = true
                touchStartCRUD()
                touchMode = MotionEvent.ACTION_MOVE
            }
            path.quadTo(currentX, currentY, (motionTouchEventX + currentX) / 2, (motionTouchEventY + currentY) / 2)
            currentX = motionTouchEventX
            currentY = motionTouchEventY
            
            //<--CRUD
            pushToStroke(currentX, currentY)
            //--!>
            
            extraCanvas.drawPath(path, paint)
        }
        invalidate()
    }
    
    private fun touchUp() {
        if(touchMode == MotionEvent.ACTION_DOWN){
            if(returnFromDown) touchStartCRUD()
            touchMode = MotionEvent.ACTION_UP
        }
        path.reset()
        invalidate()
    }
    
    companion object{
        var canvasStrokes = mutableListOf<ARNotesCanvasStroke>()
        var undoStack = mutableListOf<ARNotesCanvasStroke>()
        var returnFromDown = true
    }

}