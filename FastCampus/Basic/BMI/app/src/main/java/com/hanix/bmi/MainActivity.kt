package com.hanix.bmi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editHeight: EditText = findViewById(R.id.editHeight)
        val editWeight = findViewById<EditText>(R.id.editWeight)
        val submitButton = findViewById<Button>(R.id.submitButton)


        submitButton.setOnClickListener {

            if(editHeight.text.isEmpty() || editWeight.text.isEmpty()) {
                Toast.makeText(this, "신장과 체중을 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val height: Int = editHeight.text.toString().toInt()
            val weight: Int = editWeight.text.toString().toInt()

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("height", height)
            intent.putExtra("weight", weight)
            startActivity(intent)
        }

    }
}