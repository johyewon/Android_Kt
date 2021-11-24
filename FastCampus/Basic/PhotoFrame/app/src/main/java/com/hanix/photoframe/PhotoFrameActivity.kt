package com.hanix.photoframe

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.timer

class PhotoFrameActivity : AppCompatActivity() {

    private val photoList = mutableListOf<Uri>()

    private val backgroundPhotoImage: ImageView by lazy {
        findViewById(R.id.backgroundPhotoImage)
    }
    private val photoImageView: ImageView by lazy {
        findViewById(R.id.photoImageView)
    }

    private var currentPosition = 0

    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_frame)

        getPhotoUriFromIntent()

        startTimer()
    }

    // background 상태
    override fun onStop() {
        super.onStop()

        Log.d("PhotoFrame", "onStop@@ timer cancel")

        timer?.cancel()
    }

    // 다시 시작했을 때
    override fun onStart() {
        super.onStart()
        Log.d("PhotoFrame", "onStart@@ timer start")
        startTimer()
    }

    // activity 가 완전히 가려졌을 때
    override fun onDestroy() {
        super.onDestroy()

        Log.d("PhotoFrame", "onDestroy@@ timer cancel")
        timer?.cancel()
    }


    /**
     * intent 에서 데이터 가져오기
     */
    private fun getPhotoUriFromIntent() {
        val size = intent.getIntExtra("photoListSize", 0)
        for (i in 0..size) {
            intent.getStringExtra("photo$i")?.let {
                //   null 이 아닐 때만 실행
                photoList.add(Uri.parse(it))
            }
        }
    }

    private fun startTimer() {
        timer = timer(period = 5 * 1000) {
            runOnUiThread {

                Log.d("PhotoFrame", "5초가 지나감")

                val current = currentPosition
                val next = if (photoList.size <= currentPosition + 1) 0 else currentPosition + 1

                backgroundPhotoImage.setImageURI(photoList[current])

                photoImageView.alpha = 0f
                photoImageView.setImageURI(photoList[next])
                photoImageView.animate().alpha(1.0f).setDuration(1000).start()

                currentPosition = next
            }
        }
    }

}