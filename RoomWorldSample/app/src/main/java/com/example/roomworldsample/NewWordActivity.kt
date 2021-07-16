package com.example.roomworldsample

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class NewWordActivity : AppCompatActivity() {
    
    private lateinit var editWordView: EditText
    private lateinit var editDefView: EditText
    
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        
        editWordView = findViewById(R.id.edit_word)
        editDefView = findViewById(R.id.edit_defs)
        
        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editWordView.text.toString()
                val def = editDefView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY_WORD, word)
                replyIntent.putExtra(EXTRA_REPLY_DESC, def)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
    
    companion object {
        const val EXTRA_REPLY_WORD = "com.example.android.wordlistsql.REPLY_WORD"
        const val EXTRA_REPLY_DESC = "com.example.android.wordlistsql.REPLY_DESC"
    }
}

