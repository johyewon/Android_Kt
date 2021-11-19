package com.hanix.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.hanix.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButton()
    }

    private fun initButton() {
        binding.calculateButton.setOnClickListener { calculateTip() }
    }

    private fun calculateTip() {
        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()     // double 형으로 변환하려면 toString 으로 먼저 변환해야 함

        if (cost != null) {
            val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {      // 팁 퍼센트
                R.id.optionFifteenPercent -> 0.15
                R.id.optionEighteenPercent -> 0.18
                else -> 0.2
            }

            var tip = tipPercentage * cost
            if (binding.roundUpSwitch.isChecked) { // 반올림 여부
                tip = ceil(tip)
            }

            displayTip(tip)

        } else {
            Toast.makeText(this, "Enter cost of Service.", Toast.LENGTH_SHORT).show()
            binding.costOfService.requestFocus()

            displayTip(tip = 0.0)
        }
    }

    private fun displayTip(tip: Double) {
        val formattedTip =
            NumberFormat.getCurrencyInstance().format(tip)       // number Format 맞추기

        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)       // set text
    }
}