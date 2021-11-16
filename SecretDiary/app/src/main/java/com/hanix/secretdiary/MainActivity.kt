package com.hanix.secretdiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val firstNumberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.firstNumberPicker)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }
    private val secondNumberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.secondNumberPicker)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }
    private val thirdNumberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.thirdNumberPicker)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val openButton: AppCompatButton by lazy {
        findViewById(R.id.openButton)
    }
    private val changePasswordButton: AppCompatButton by lazy {
        findViewById(R.id.changePasswordButton)
    }

    private var changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstNumberPicker
        secondNumberPicker
        thirdNumberPicker

        openButton.setOnClickListener {
            if (changePasswordMode) {        // 비밀번호 변경 중일 때
                Toast.makeText(this, "비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordPreference = getSharedPreferences(
                "password",
                Context.MODE_PRIVATE
            )      // password -> 파일 이름,    Mode -> 다른 앱과 공유하지 않고 앱 안에서만 사용하기 때문에 private 으로 선언

            val passwordFromUser =
                "${firstNumberPicker.value}${secondNumberPicker.value}${thirdNumberPicker.value}"

            if (passwordPreference.getString("password", "000").equals(passwordFromUser)) {
                // 패스워드 성공
                startActivity(Intent(this, DiaryActivity::class.java))
            } else {
                // 패스워드 실패
                showErrorAlertDialog()
            }
        }

        changePasswordButton.setOnClickListener {
            val passwordPreference = getSharedPreferences(
                "password",
                Context.MODE_PRIVATE
            )      // password -> 파일 이름,    Mode -> 다른 앱과 공유하지 않고 앱 안에서만 사용하기 때문에 private 으로 선언
            val passwordFromUser =
                "${firstNumberPicker.value}${secondNumberPicker.value}${thirdNumberPicker.value}"

            if (changePasswordMode) {
                // password 를 변경하는 동안 UI 변경을 하지 않게 하기 위해 commit true 로 함
                passwordPreference.edit(true) {
                    putString("password", passwordFromUser)
                }

                changePasswordMode = false
                changePasswordButton.setBackgroundColor(Color.BLACK)
            } else {
                // 비밀번호 변경 활성화 :: 비밀번호가 맞는지 확인
                if (passwordPreference.getString("password", "000").equals(passwordFromUser)) {
                    changePasswordMode = true
                    Toast.makeText(this, "변경할 패스워드를 입력해 주세요", Toast.LENGTH_SHORT).show()

                    changePasswordButton.setBackgroundColor(Color.GREEN)
                } else {
                    // 패스워드 실패
                    showErrorAlertDialog()
                }
            }
        }

    }

    private fun showErrorAlertDialog() {
        AlertDialog.Builder(this)
            .setMessage("비밀번호가 잘못되었습니다.")
            .setPositiveButton("확인") { _, _ -> }
            .create().show()
    }

}