package com.example.minipaint

class ARNotesCanvasStroke (val color: String, val size: Int)
{
    private var coords : MutableList<FloatArray> = mutableListOf()
    
    //functions
    fun add(x: Float, y: Float){
        var coord = FloatArray(2)
        coord[0] = x
        coord[1] = y
        coords.add(coord)
    }
    
    fun getCoords() : MutableList<FloatArray>
    {
        return coords
    }
}

