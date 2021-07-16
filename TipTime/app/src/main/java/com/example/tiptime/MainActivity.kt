package com.example.tiptime

//import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial
import android.widget.Switch as Switch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding //for view bind

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)//disable night mode
        binding = ActivityMainBinding.inflate(layoutInflater) //for view bind
        setContentView(binding.root) //modified for view bind



        binding.calculateButton.setOnClickListener{
            calculateTip()
        }

        binding.costOfServiceEditText.showKeyboard()
    }

    private fun View.showKeyboard(){
        this.requestFocus()
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun makeAlert(message: String = "message"){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("alert")
        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()

    }

    private fun calculateTip(){
//        makeAlert()
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null) {
            binding.tipAmt.text = ""
//            makeAlert("${stringInTextField}")
            return
        }

        val tipPercentage = when(binding.radioGaga.checkedRadioButtonId) {
            R.id.radio_amaze -> 0.20
            R.id.radio_good -> 0.18
            else -> 0.15
        }
//        makeAlert("${tipPercentage}")
        var tip = tipPercentage * cost
        if (binding.roundUpSwitch.isChecked) {
//            makeAlert("round up")
            tip = kotlin.math.ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipAmt.text = getString(R.string.tip_amount, formattedTip)
    }
}