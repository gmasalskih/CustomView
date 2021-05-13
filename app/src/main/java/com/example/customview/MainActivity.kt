package com.example.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cvg = findViewById<CustomViewGroup>(R.id.cvg)
        val random = Random(10)

        repeat(500) {
            val cv = CustomView(this)
            cv.layoutParams =
                ViewGroup.LayoutParams(random.nextInt(100,1000), random.nextInt(100,1000))
            cv.setPadding(2,2,2,2)
            cvg.addView(cv)
        }
    }
}