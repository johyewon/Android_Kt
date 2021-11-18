package com.hanix.lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import java.util.*

class MainActivity : AppCompatActivity() {

    private val clearButton: Button by lazy {
        findViewById(R.id.clearButton)
    }

    private val addButton: Button by lazy {
        findViewById(R.id.addButton)
    }

    private val runButton: Button by lazy {
        findViewById(R.id.runButton)
    }

    private val numberPicker: NumberPicker by lazy {
        findViewById(R.id.numberPicker)
    }

    private val numberTextViewList: List<TextView> by lazy {
        listOf<TextView>(
            findViewById(R.id.textView1),
            findViewById(R.id.textView2),
            findViewById(R.id.textView3),
            findViewById(R.id.textView4),
            findViewById(R.id.textView5),
            findViewById(R.id.textView6)
        )
    }

    private var didRun = false

    // 중복 제거를 위해 사용했음
    private val pickNumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
    }

    private fun initRunButton() {
        // 자동 생성 시작
        runButton.setOnClickListener {
            val list = getRandomNumber()

            list.forEachIndexed { index, number ->
                val textView = numberTextViewList[index]

                textView.text = number.toString()
                textView.isVisible = true

                setNumberBackground(number, textView)
            }

            didRun = true
        }

        // 번호 추가하기
        addButton.setOnClickListener {
            if (didRun) {        // 자동 실행이 되어 있는 경우
                Toast.makeText(this, "초기화 후에 시도해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickNumberSet.size >= 6) {       // 저장되어 있는 번호가 다섯 개 이상일 때
                Toast.makeText(this, "번호는 최대 다섯 개입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickNumberSet.contains(numberPicker.value)) {        // 이미 번호가 포함되어 있을 때
                Toast.makeText(this, "이미 선택한 번호입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val textView = numberTextViewList[pickNumberSet.size]
            textView.isVisible = true
            textView.text = numberPicker.value.toString()

            setNumberBackground(numberPicker.value, textView)

            pickNumberSet.add(numberPicker.value)

        }

        // 초기화
        clearButton.setOnClickListener {
            pickNumberSet.clear()

            numberTextViewList.forEach {
                it.isVisible = false
            }

            didRun = false
        }
    }

    private fun getRandomNumber(): List<Int> {
        val randomList = mutableListOf<Int>()
            .apply {
                for (i in 1..45) {
                    if (pickNumberSet.contains(i)) {     // 이미 선택된 번호일 때
                        continue
                    }

                    this.add(i)
                }
            }

        randomList.shuffle()        // 랜덤하게 섞어줌

        val newList = pickNumberSet.toList() + randomList.subList(
            0,
            6 - pickNumberSet.size
        )        // 이미 선택된 번호 + (새로 추가되는 번호  - 이미 있는 자리수)

        return newList.sorted()
    }

    private fun setNumberBackground(number: Int, textView: TextView) {
        when (number) {
            in 1..10 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_yellow)

            in 11..20 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_blue)

            in 21..30 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_red)

            in 31..40 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_gray)

            else -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_green)
        }
    }
}