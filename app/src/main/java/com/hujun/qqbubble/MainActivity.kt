package com.hujun.qqbubble

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hujun.bubble.BubbleView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bubbleView = BubbleView(this)
        bubbleView.x = 200f
        bubbleView.y = 200f
    }
}
