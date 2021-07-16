package com.example.gsontestapp

import com.google.gson.Gson
import java.io.File

class writer
{
    var gson = Gson()
    var people = mutableListOf<Person>()
    lateinit var jsonio: People
    
    init
    {
        people.add(Person("John", "Doe"))
        people.add(Person("Hey", "Joe"))
        people[0].setAge(40)
        jsonio = People(people)
    }
    
    fun getPeople() : String?
    {
        return gson.toJson(jsonio)
    }
    
    fun writeTo(fileName: String = "iotest.json")
    {
        val file = File(filesDir, fileName)
        if(!file.exists())
            file.createNewFile()

        getPeople()?.let { file.writeText(it) }
    }
    
    companion object{
        lateinit var filesDir: File
    }
}