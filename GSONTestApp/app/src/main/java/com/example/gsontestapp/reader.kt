package com.example.gsontestapp

import com.google.gson.Gson
import java.io.File

class reader(val fileName: String = "iotest.json")
{
    var gson = Gson()
    lateinit var people: People
    lateinit var inputString: String
//    init
//    {
//        readFrom()
//    }
    fun readFrom(){
        val file = File(filesDir, fileName)
        if(file.exists())
        {
            inputString = file
                .inputStream()
                .bufferedReader()
                .use { it.readText() }
            people = gson.fromJson(inputString, People::class.java)
        }
        else
        {
            people = People(mutableListOf<Person>())
            inputString = ""
        }
    }
    
    fun getPeople() : String
    {
        return inputString
    }
    
    companion object{
        lateinit var filesDir: File
    }
}