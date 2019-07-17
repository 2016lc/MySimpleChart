package com.simple.lc.mylibrary

import android.graphics.PointF

/**
 * Author:LC
 * Date:2019/7/15
 * Description:This is ControlPoint
 */
class ControlPoint {

    private var conPoint1: PointF? = null
    private var conPoint2: PointF? = null


    constructor(conPoint1: PointF?, conPoint2: PointF?) {
        this.conPoint1 = conPoint1
        this.conPoint2 = conPoint2
    }

    fun getConPoint1(): PointF? {
        return conPoint1
    }

    fun setConPoint1(conPoint1: PointF) {
        this.conPoint1 = conPoint1
    }

    fun getConPoint2(): PointF? {
        return conPoint2
    }

    fun setConPoint2(conPoint2: PointF) {
        this.conPoint2 = conPoint2
    }

    companion object {
        private var smoothness = 0.1

        fun getControlPointList(pointFs: List<PointF>): List<ControlPoint> {

            val controlPoints: MutableList<ControlPoint> = ArrayList()

            var p1: PointF
            var p2: PointF
            var conP1x: Float
            var conP1y: Float
            var conP2x: Float
            var conP2y: Float
            for (i in 0 until pointFs.size - 1) {

                when (i) {
                    0 -> {
                        //第一断1曲线 控制点
                        conP1x = (pointFs[i].x + (pointFs[i + 1].x - pointFs[i].x) * smoothness).toFloat()
                        conP1y = (pointFs[i].y + (pointFs[i + 1].y - pointFs[i].y) * smoothness).toFloat()

                        conP2x = (pointFs[i + 1].x - (pointFs[i + 2].x - pointFs[i].x) * smoothness).toFloat()
                        conP2y = (pointFs[i + 1].y - (pointFs[i + 2].y - pointFs[i].y) * smoothness).toFloat()

                    }
                    pointFs.size - 2 -> {
                        //最后一段曲线 控制点
                        conP1x = (pointFs[i].x + (pointFs[i + 1].x - pointFs[i - 1].x) * smoothness).toFloat()
                        conP1y = (pointFs[i].y + (pointFs[i + 1].y - pointFs[i - 1].y) * smoothness).toFloat()

                        conP2x = (pointFs[i + 1].x - (pointFs[i + 1].x - pointFs[i].x) * smoothness).toFloat()
                        conP2y = (pointFs[i + 1].y - (pointFs[i + 1].y - pointFs[i].y) * smoothness).toFloat()
                    }
                    else -> {
                        conP1x = (pointFs[i].x + (pointFs[i + 1].x - pointFs[i - 1].x) * smoothness).toFloat()
                        conP1y = (pointFs[i].y + (pointFs[i + 1].y - pointFs[i - 1].y) * smoothness).toFloat()

                        conP2x = (pointFs[i + 1].x - (pointFs[i + 2].x - pointFs[i].x) * smoothness).toFloat()
                        conP2y = (pointFs[i + 1].y - (pointFs[i + 2].y - pointFs[i].y) * smoothness).toFloat()
                    }
                }

               /* conP1x = pointFs[i].x + (pointFs[i + 1].x - pointFs[i].x) / 4
                conP1y = pointFs[i].y + (pointFs[i + 1].y - pointFs[i].y) / 4

                conP2x = pointFs[i + 1].x - (pointFs[i + 2].x - pointFs[i].x) / 4
                conP2y = pointFs[i + 1].y - (pointFs[i + 2].y - pointFs[i].y) / 4*/

                p1 = PointF(conP1x, conP1y)
                p2 = PointF(conP2x, conP2y)

                val controlPoint = ControlPoint(p1, p2)
                controlPoints.add(controlPoint)
            }

            return controlPoints
        }
    }


}