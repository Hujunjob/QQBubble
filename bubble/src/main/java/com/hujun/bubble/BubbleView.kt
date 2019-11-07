package com.hujun.bubble

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

/**
 * Created by junhu on 2019-11-07
 */
class BubbleView(context: Activity) : View(context) {
    private var mWidth = 0
    private var mHeight = 0

    private lateinit var mBubblePaint: Paint

    //必须传递Activity
    init {
        addViewToWindow(context)

        initPaint(context)
    }

    private fun initPaint(activity: Activity) {
        mBubblePaint = Paint()
        mBubblePaint.color = Color.RED
        mBubblePaint.style = Paint.Style.FILL

        activity.obtainStyledAttributes(activity.attr,)
    }

    private fun addViewToWindow(activity: Activity) {
        //获取DecorView，将该BubbleView放进去，因为这个view是可以全局拖拽的
        var decorView = activity.window.decorView as ViewGroup
        var param = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        decorView.addView(this, param)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //获取屏幕宽高
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawNumCircle(canvas)

    }

    fun drawNumCircle(canvas: Canvas?) {
        canvas?.drawCircle(x, y, 10f, mBubblePaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        return super.onTouchEvent(event)
    }

}