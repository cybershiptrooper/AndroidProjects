package com.example.helloworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class DisplayMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)

        // Get the Intent that started this activity and extract the string
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        val hello_message = "Hello ${message}!"
        // Capture the layout's TextView and set the string as its text
        val textView = findViewById<TextView>(R.id.messageDisp).apply {
            text = hello_message
        }
    }

    fun GoBack(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}