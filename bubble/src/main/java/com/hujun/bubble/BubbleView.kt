package com.hujun.bubble

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.hujun.bubble.utils.MathUtils

/**
 * Created by junhu on 2019-11-07
 */
class BubbleView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var mWidth = 0
    private var mHeight = 0

    //拖拽球相关
    private lateinit var mBubblePaint: Paint
    private var mBubbleSize = 30f
    private lateinit var mBubbleCenter: PointF

    //不可拖拽球相关
    private lateinit var mFixedBubblePaint: Paint
    private var mFixedBubbleSize = 20f
    private lateinit var mFixedCenter: PointF

    private val TOUCH_SAFE_DISTANCE = 10

    //消息数字相关
    private val mText = "12"
    private lateinit var mTextPaint: Paint

    //1、静止状态：1个小球和消息数静止
    //2、拖拽状态：1个小球和消息数被拖动，原位置处有一个小球（大小会变化），中间连接着贝塞尔曲线组成的桥
    //3、断开状态：1个小球和消息数被拖动
    //4、爆炸状态：1个小球和消息数爆炸消失
    private enum class BubbleState {
        STABLE,
        DRAG,
        DISCONNCT,
        DISMISS
    }

    private var bubbleState = BubbleState.STABLE

    //必须传递Activity
    init {
//        addViewToWindow(context)

        initPaint(context, attrs)
    }

    private fun initPaint(context: Context, attrs: AttributeSet) {
        mBubblePaint = Paint()
        mBubblePaint.color = Color.RED
        mBubblePaint.style = Paint.Style.FILL

        val bubbleColor = Color.RED
        mBubblePaint.color = bubbleColor

        mTextPaint = Paint()
        mTextPaint.color = Color.WHITE
        mTextPaint.textSize = 30f
        mTextPaint.strokeWidth = 10f
        mTextPaint.textAlign = Paint.Align.CENTER

        mFixedBubblePaint = Paint()
        mFixedBubblePaint.color = Color.RED
        mFixedBubblePaint.style = Paint.Style.FILL
    }

//    private fun addViewToWindow(context: Context?) {
//        //获取DecorView，将该BubbleView放进去，因为这个view是可以全局拖拽的
//        var decorView = context.window.decorView as ViewGroup
//        var param = ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.MATCH_PARENT
//        )
//        decorView.addView(this, param)
//    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //获取屏幕宽高
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)

        mBubbleCenter = PointF()
        mBubbleCenter.x = mWidth * 0.5f
        mBubbleCenter.y = mHeight * 0.5f

        mFixedCenter = PointF()
        mFixedCenter.x = mWidth * 0.5f
        mFixedCenter.y = mHeight * 0.5f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //分为几种状态
        //1、静止状态：1个小球和消息数静止
        //2、拖拽状态：1个小球和消息数被拖动，原位置处有一个小球（大小会变化），中间连接着贝塞尔曲线组成的桥
        //3、断开状态：1个小球和消息数被拖动
        //4、爆炸状态：1个小球和消息数爆炸消失

        //除了爆炸状态，都需要绘制消息球
        if (bubbleState != BubbleState.DISMISS)
            drawNumCircle(canvas)

        if (bubbleState == BubbleState.DRAG) {
            drawSmallBubble(canvas)
            drawConnect(canvas)
        }

    }

    //绘制连接两个球的桥接
    private fun drawConnect(canvas: Canvas?) {
        //该桥是由两根二阶贝塞尔曲线组成的闭合曲线
        //二阶贝塞尔曲线的控制点为 ：两个小球球心连线的中点
        //数据点为：两小球连线方向，经过球心并垂直于该连线的直线，与球面的交点。在大球和小球分别有2个交点

        //计算控制点
        val controlX = (mBubbleCenter.x - mFixedCenter.x) * 0.5f
        val controlY = (mBubbleCenter.y - mFixedCenter.y) * 0.5f

        //计算数据点


    }

    //绘制中间固定小球
    private fun drawSmallBubble(canvas: Canvas?) {
        canvas?.drawCircle(mFixedCenter.x, mFixedCenter.y, mFixedBubbleSize, mFixedBubblePaint)

    }

    //绘制消息球
    private fun drawNumCircle(canvas: Canvas?) {
        canvas?.drawCircle(mBubbleCenter.x, mBubbleCenter.y, mBubbleSize, mBubblePaint)

        canvas?.drawText(mText, mBubbleCenter.x, mBubbleCenter.y, mTextPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (isTouchBubble(event)) {
                    bubbleState = BubbleState.DRAG
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (bubbleState == BubbleState.DRAG) {
                    mBubbleCenter.x = event.x
                    mBubbleCenter.y = event.y
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                if (isTouchBubble(event)) {
                    bubbleState = BubbleState.STABLE
                }
            }
        }

        return true
    }


    private fun isTouchBubble(event: MotionEvent): Boolean {
        var x = event.x
        var y = event.y

        //计算触摸点和Bubble中心距离
        var dis = MathUtils.calDistance(x, y, mBubbleCenter.x, mBubbleCenter.y)
        //设定一个安全距离，即不一定需要触摸到圆，在一定距离即相当于点击到圆
        if (dis < TOUCH_SAFE_DISTANCE + mBubbleSize) {
            return true
        }
        return false
    }
}