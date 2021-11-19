package com.hanix.tipcalculator

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

    /**
     * 키보드 이벤트
     */
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)     // 0 이 키보드 내림
            return true
        }
        return false
    }

    private fun initButton() {
        binding.calculateButton.setOnClickListener { calculateTip() }
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }

    private fun calculateTip() {
        val stringInTextField = binding.costOfServiceEditText.text.toString()
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