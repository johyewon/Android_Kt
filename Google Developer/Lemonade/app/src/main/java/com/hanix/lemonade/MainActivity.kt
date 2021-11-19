package com.hanix.lemonade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    /**
     * DO NOT ALTER ANY VARIABLE OR VALUE NAMES OR THEIR INITIAL VALUES.
     *
     * Anything labeled var instead of val is expected to be changed in the functions but DO NOT
     * alter their initial values declared here, this could cause the app to not function properly.
     */
    private val LEMONADE_STATE = "LEMONADE_STATE"
    private val LEMON_SIZE = "LEMON_SIZE"
    private val SQUEEZE_COUNT = "SQUEEZE_COUNT"

    /* lemon state start */
    private val SELECT = "select"
    private val SQUEEZE = "squeeze"
    private val DRINK = "drink"
    private val RESTART = "restart"
    /* lemon state end */

    // 현재 레몬의 상태 - select, squeeze, drink, restart
    private var lemonadeState = "select"

    // 레몬을 짤 수 있는 회수
    private var lemonSize = -1

    // 현재 레몬을 짠 횟수
    private var squeezeCount = -1

    private var lemonTree = LemonTree()
    private var lemonImage: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // === DO NOT ALTER THE CODE IN THE FOLLOWING IF STATEMENT ===
        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(LEMONADE_STATE, "select")
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }
        // === END IF STATEMENT ===

        lemonImage = findViewById(R.id.image_lemon_state)
        setViewElements()
        lemonImage!!.setOnClickListener {
            clickLemonImage()
        }
        lemonImage!!.setOnLongClickListener {
            showSnackbar()
            false
        }
    }

    /**
     * === DO NOT ALTER THIS METHOD ===
     *
     * This method saves the state of the app if it is put in the background.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LEMONADE_STATE, lemonadeState)
        outState.putInt(LEMON_SIZE, lemonSize)
        outState.putInt(SQUEEZE_COUNT, squeezeCount)
        super.onSaveInstanceState(outState)
    }

    private fun clickLemonImage() {
        when (lemonadeState) {
            SELECT -> {
                lemonadeState = SQUEEZE

                lemonSize = lemonTree.pick()    // 레몬을 짤 수 있는 회수 저장
                squeezeCount = 0                // reset 해
            }
            SQUEEZE -> {
                if(lemonSize <= squeezeCount) {
                    lemonadeState = DRINK

                    lemonSize = -1
                    squeezeCount = -1
                } else if(lemonSize > squeezeCount) {
                    lemonSize--
                    squeezeCount++
                }
            }
            DRINK -> {
                lemonadeState = RESTART
            }
            RESTART -> {
                lemonadeState = SELECT
            }
        }

        setViewElements()
    }

    /**
     * Set up the view elements according to the state.
     */
    private fun setViewElements() {
        val textAction: TextView = findViewById(R.id.text_action)

        val imageResourceId: Int
        val textString: String

        when (lemonadeState) {
            SQUEEZE -> {
                imageResourceId = R.drawable.lemon_squeeze
                textString = getString(R.string.lemon_squeeze)
            }
            DRINK -> {
                imageResourceId = R.drawable.lemon_drink
                textString = getString(R.string.lemon_drink)
            }
            RESTART -> {
                imageResourceId = R.drawable.lemon_restart
                textString = getString(R.string.lemon_empty_glass)
            }
            else -> {
                imageResourceId = R.drawable.lemon_tree
                textString = getString(R.string.lemon_select)
            }
        }

        lemonImage?.setImageResource(imageResourceId)
        textAction.text = textString
    }

    /**
     * Long Click Event 스퀴즈 상태가 아닐 때에는 return
     */
    private fun showSnackbar(): Boolean {
        if (lemonadeState != SQUEEZE) {
            return false
        }
        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Snackbar.make(
            findViewById(R.id.constraint_Layout),
            squeezeText,
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }
}
