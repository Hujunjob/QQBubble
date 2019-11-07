package com.hujun.bubble.utils

/**
 * Created by hujun on 2019-11-07.
 */

class MathUtils {
    companion object {
        fun calDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
            var sum = (x2 - x1) * (x2 - x1) + (y1 - y2) * (y1 - y2)
            return Math.sqrt(sum.toDouble()).toFloat()
        }
    }
}