package com.hanix.direroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val diceImage: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    private val rollButton: Button by lazy {
        findViewById(R.id.button)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rollButton.setOnClickListener {
          roll()
        }

        roll()
    }

    private fun roll() {
        Toast.makeText(this, "Dice Rolled!", Toast.LENGTH_SHORT).show()

        val diceRoll = rollDice()
        imageSetting(diceRoll)

        luckyDice(3, diceRoll)
    }

    private fun rollDice(): Int {
        val dice = Dice(6)

        return dice.roll()
    }

    private fun imageSetting(diceNumber: Int) {
        val diceDrawable = when(diceNumber)  {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        diceImage.setImageResource(diceDrawable)

    }

    private fun luckyDice(luckyNumber: Int, resultNumber: Int) {
        val msg = when {
            resultNumber == luckyNumber -> {
                "You won!"
            }
            resultNumber < luckyNumber -> {
                val d = luckyNumber - resultNumber
                "So Sorry! You rolled a ${d}. Try Again!"
            }
            else -> {
                val d = resultNumber - luckyNumber
                "Don't cry! You rolled a ${d}. Try Again!"
            }
        }

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}