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
        repeat(50) {
            val cv = CustomViewGroup(this)
            val random = Random(0).nextInt(100,1000)
            cv.layoutParams =
                ViewGroup.LayoutParams(random, random)
            cv.setPadding(2,2,2,2)
            cvg.addView(cv)
        }
    }
}