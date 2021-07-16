package com.example.gsontestapp

data class Person(override val firstName: String, override val lastName:String): datatemplate()
{
    private var age: Int? = null
    @JvmName("setAge1")
    fun setAge(a: Int){
        age = a
    }
}