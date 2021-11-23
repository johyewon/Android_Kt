package com.hanix.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.room.Room
import com.hanix.calculator.model.History

class MainActivity : AppCompatActivity() {

    private val expressionTextView: TextView by lazy {
        findViewById(R.id.expressionTextView)
    }

    private val resultTextView: TextView by lazy {
        findViewById(R.id.resultTextView)
    }

    private val historyLayout: View by lazy {
        findViewById(R.id.historyLayout)
    }

    private val historyLinearLayout: LinearLayout by lazy {
        findViewById(R.id.historyLinearLayout)
    }

    private lateinit var db: AppDatabase

    private var isOperator = false
    private var hasOperator = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "historyDB"
        ).build()
    }

    /**
     * click listener
     */
    fun buttonClicked(v: View) {
        when (v.id) {
            R.id.button0 -> numberButtonClicked("0")
            R.id.button1 -> numberButtonClicked("1")
            R.id.button2 -> numberButtonClicked("2")
            R.id.button3 -> numberButtonClicked("3")
            R.id.button4 -> numberButtonClicked("4")
            R.id.button5 -> numberButtonClicked("5")
            R.id.button6 -> numberButtonClicked("6")
            R.id.button7 -> numberButtonClicked("7")
            R.id.button8 -> numberButtonClicked("8")
            R.id.button9 -> numberButtonClicked("9")

            R.id.buttonPlus -> operatorButtonClicked("+")
            R.id.buttonMinus -> operatorButtonClicked("-")
            R.id.buttonMulti -> operatorButtonClicked("*")
            R.id.buttonDivider -> operatorButtonClicked("/")
            R.id.buttonModulo -> operatorButtonClicked("%")
        }
    }

    /**
     * 숫자 선택 함수
     */
    private fun numberButtonClicked(number: String) {

        if (isOperator) {
            expressionTextView.append(" ")
            isOperator = false
        }

        val expressionText = expressionTextView.text.split(" ")

        if (expressionText.isNotEmpty() && expressionText.last().length >= 15) {
            Toast.makeText(this, "15자리까지만 사용할 수 있습니다", Toast.LENGTH_SHORT).show()
            return
        } else if (expressionText.last().isEmpty() && number == "0") {
            Toast.makeText(this, "0은 제일 앞에 올 수 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        expressionTextView.append(number)
        resultTextView.text = calculateExpression()


    }

    /**
     * 연산자 선택 함수
     */
    @SuppressLint("SetTextI18n")
    private fun operatorButtonClicked(operator: String) {
        if (expressionTextView.text.isEmpty()) {     // 입력된 숫자가 없을 때
            return
        }

        when {
            isOperator -> {
                val text = expressionTextView.text.toString()
                expressionTextView.text = text.dropLast(1) + operator // 맨 끝에서부터 한 자리만 지워줌
            }
            hasOperator -> {
                Toast.makeText(this, "연산자는 한 번만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                expressionTextView.append(" $operator")
            }
        }

        val ssb = SpannableStringBuilder(expressionTextView.text)       // 특정 부분만 글자색 표시
        ssb.setSpan(
            ForegroundColorSpan(
                getColor(R.color.green)
            ),
            expressionTextView.text.length - 1,       // 연산자가 마지막에 들어올 수밖에 없기 때문
            expressionTextView.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        expressionTextView.text = ssb

        isOperator = true
        hasOperator = true

    }


    /**
     * 현재 내용 삭제
     */
    fun clearButtonClicked(v: View) {
        expressionTextView.text = ""
        resultTextView.text = ""
        isOperator = false
        hasOperator = false
    }

    /**
     * 결과값 보여주기
     */
    fun resultButtonClicked(v: View) {
        val expressionTexts = expressionTextView.text.split(" ")

        if (expressionTextView.text.isEmpty() || expressionTexts.size == 1) {
            return
        }

        if (expressionTexts.size != 3 && hasOperator) {
            Toast.makeText(this, "아직 완성되지 않은 수식입니다", Toast.LENGTH_SHORT).show()
            return
        }

        if (!expressionTexts[0].isNumber() && !expressionTexts[2].isNumber()) {       // 숫자 포맷으로 와야 하는 것들이 숫자가 아닐 경우
            Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        // DB 저장을 위해 남김
        val expressionText = expressionTextView.text.toString()
        val resultText = calculateExpression()

        Thread {
            db.historyDao()
                .insertHistory(History(null, expressionText, resultText))        // db 에 저장
        }.start()

        resultTextView.text = ""
        expressionTextView.text = resultText

        isOperator = false
        hasOperator = false
    }

    /**
     * 기록 노출
     */
    @SuppressLint("SetTextI18n")
    fun historyButtonClicked(v: View) {
        historyLayout.isVisible = true
        historyLinearLayout.removeAllViews()

        Thread {
            db.historyDao().getAll().reversed().forEach {
                runOnUiThread {
                    val historyView = LayoutInflater.from(this).inflate(
                        R.layout.item_history_row,
                        null,
                        false
                    )     // addView 를 통해 삽입하기 때문에 null 로 선언
                    historyView.findViewById<TextView>(R.id.expressionTextView).text = it.expression
                    historyView.findViewById<TextView>(R.id.resultTextView).text = "= ${it.result}"

                    historyLinearLayout.addView(historyView)
                }
            }
        }.start()
    }

    /**
     * 기록 노출 해제
     */
    fun closeHistoryButtonClicked(v: View) {
        historyLayout.isVisible = false
    }

    /**
     * 계산 기록 삭제
     */
    fun historyClearButtonClicked(v: View) {
        historyLinearLayout.removeAllViews()

        Thread {
            db.historyDao().deleteAll()
        }.start()
    }

    /**
     * 계산해서 미리보기 띄워주기
     */
    private fun calculateExpression(): String {
        val expressionTexts = expressionTextView.text.split(" ")

        if (!hasOperator || expressionTexts.size != 3) {
            return ""
        } else if (!expressionTexts[0].isNumber() && !expressionTexts[2].isNumber()) {       // 숫자 포맷으로 와야 하는 것들이 숫자가 아닐 경우
            return ""
        }

        val exp1 = expressionTexts[0].toBigInteger()
        val exp2 = expressionTexts[2].toBigInteger()

        return when (expressionTexts[1]) {
            "+" -> (exp1 + exp2).toString()
            "-" -> (exp1 - exp2).toString()
            "*" -> (exp1 * exp2).toString()
            "/" -> (exp1 / exp2).toString()
            "%" -> (exp1 % exp2).toString()
            else -> ""
        }
    }

}

/**
 * 확장 함수 구현
 */
fun String.isNumber(): Boolean {
    return try {
        this.toBigInteger()
        true
    } catch (e: NumberFormatException) {
        false
    }
}