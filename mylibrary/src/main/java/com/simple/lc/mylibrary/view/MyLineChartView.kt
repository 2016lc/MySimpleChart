package com.simple.lc.mylibrary.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import com.simple.lc.mylibrary.*
import java.util.ArrayList
import kotlin.math.ceil
import android.R.attr.y
import android.R.attr.x
import java.nio.file.Files.size
import com.simple.lc.mylibrary.ControlPoint
import android.graphics.PathMeasure


/**
 * Author:LC
 * Date:2018/12/26
 * Description:折线图
 */
class MyLineChartView(context: Context?, attrs: AttributeSet?) : MyCommonView(context, attrs) {
    /** 折线画笔 */
    private var mPaint_line: Paint? = null
    /** 每个折点的圆 */
    private var mPaint_Circle: Paint? = null
    /** 每个折点圆的填充 */
    private var mPaint_Fill: Paint? = null
    /** 线条宽度 */
    private var mLineWidth: Float = 1f
    /** 数据源 */
    private var mData: MutableList<MutableList<LineChartData>> = ArrayList()
    /** 折线条数 */
    private var mPathData: MutableList<Path> = ArrayList()
    /** 折线颜色 */
    private var mLineColor: Int? = null
    /** 是否是曲线 */
    private var iskinkedLine = ChartConstant.LINE_DEFAULT_ISkINkEDLINE
    /** 初始化PathMeasure 用于曲线动画 */
    private var pathMeasure = PathMeasure()
    private var mDst = Path()
    /** 绘制点的集合 */
    private var pointList: MutableList<PointF> = ArrayList()
    /** 是否显示折点的圆 */
    private var isShowCircle = ChartConstant.LINE_DEFAULT_ISSHOWCIRCLE

    init {
        initAttrs(attrs, context)//初始化属性
        initPaint()//初始化画笔
    }

    private fun initAttrs(attrs: AttributeSet?, context: Context?) {
        val typedArray = context!!.obtainStyledAttributes(
            attrs,
            R.styleable.MyLineChartView
        )
        mLineWidth = typedArray.getDimension(
            R.styleable.MyLineChartView_lineWidth,
            ChartConstant.LINE_DEFAULT_LINEWIDTH
        )
        mLineColor = typedArray.getColor(R.styleable.MyLineChartView_lineColor, Color.BLACK)

        iskinkedLine =
            typedArray.getBoolean(R.styleable.MyLineChartView_iskinkedLine, ChartConstant.LINE_DEFAULT_ISkINkEDLINE)

        isShowCircle =
            typedArray.getBoolean(R.styleable.MyLineChartView_isShowCircle, ChartConstant.LINE_DEFAULT_ISSHOWCIRCLE)

        typedArray.recycle()
    }

    private fun initPaint() {

        mPaint_line = Paint()
        mPaint_line!!.isDither = true
        mPaint_line!!.strokeWidth = mLineWidth
        mPaint_line!!.color = mLineColor!!
        mPaint_line!!.isAntiAlias = true
        mPaint_line!!.style = Paint.Style.STROKE

        mPaint_Circle = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint_Circle!!.color = mLineColor!!
        mPaint_Circle!!.isAntiAlias = true
        mPaint_Circle!!.isDither = true
        mPaint_Circle!!.strokeWidth = Util.dip2px(context, 2f).toFloat()
        mPaint_Circle!!.style = Paint.Style.STROKE

        mPaint_Fill = Paint()
        mPaint_Fill!!.isAntiAlias = true
        mPaint_Fill!!.isDither = true
        mPaint_Fill!!.style = Paint.Style.FILL
        mPaint_Fill!!.color = Color.WHITE

    }


    override fun drawContents(canvas: Canvas) {
        if (mData.size == 0) {
            return
        }


        for (j in 0 until mData.size) {

            val linePath = mPathData[j]

            mPaint_line!!.color = mData[j][0].color!!
            mPaint_Circle!!.color = mData[j][0].color!!

            pointList.clear()

            for (i in 0 until mData[j].size) {
                //画线
                val x = lineStartX + mDataMargin * (i + 1)
                val y =
                    mHeight.toFloat() - 2 * mMargin - textHeight - mData[j][i].value!! / (itemValue * mSegment).toFloat() * realHeight

                pointList.add(PointF(x, y))
            }

            if (iskinkedLine) {
                val controlPoints1 = ControlPoint.getControlPointList(pointList)
                for (i in controlPoints1.indices) {
                    if (i == 0) {
                        linePath.moveTo(pointList[i].x, pointList[i].y)
                    }
                    //画三价贝塞尔曲线
                    linePath.cubicTo(
                        controlPoints1[i].getConPoint1()!!.x, controlPoints1[i].getConPoint1()!!.y,
                        controlPoints1[i].getConPoint2()!!.x, controlPoints1[i].getConPoint2()!!.y,
                        pointList[i + 1].x, pointList[i + 1].y
                    )
                }
            } else {
                for (i in 0 until pointList.size) {
                    if (i == 0) {
                        linePath.moveTo(pointList[i].x, pointList[i].y)
                    } else {
                        linePath.lineTo(pointList[i].x, pointList[i].y)
                    }
                }
            }


            pathMeasure.setPath(linePath, false)

            val mLength = pathMeasure.length

            mDst.reset()
            // 硬件加速的BUG
            mDst.lineTo(0f, 0f)

            val stop = mLength * mPercent

            pathMeasure.getSegment(0f, stop, mDst, true)

            canvas.drawPath(mDst, mPaint_line!!)

            linePath.reset()

            for (i in 0 until mData[j].size) {
                //画占位圆
                val x = lineStartX + mDataMargin * (i + 1)
                val y =
                    mHeight.toFloat() - 2 * mMargin - textHeight - mData[j][i].value!! / (itemValue * mSegment).toFloat() * realHeight
                //x轴画字（只需执行一次）
                canvas.drawText(
                    mData[j][i].name!!,
                    x + mPaint_text!!.measureText(mData[j][i].name.toString()) / 2,
                    mHeight.toFloat() - mMargin,
                    mPaint_text!!
                )

                if (isShowCircle && mPercent == 1f) {
                    canvas.drawCircle(x, y, Util.dip2px(context, 4f).toFloat(), mPaint_Fill!!)
                    canvas.drawCircle(x, y, Util.dip2px(context, 4f).toFloat(), mPaint_Circle!!)
                }
            }

        }

    }

    /**
     * 设置数据
     * */
    fun setData(mDatas: MutableList<MutableList<LineChartData>>, maxValue: Float) {

        if (mData.size > 0) {
            mData.clear()
        }

        mData.addAll(mDatas)

        mMaxValue = ceil(maxValue.toDouble()).toInt()

        for (i in 0 until mData.size) {
            //声明几条画笔
            mPathData.add(Path())
            //取到最长一条数据的数据量
            if (maxNumSize < mData[i].size) {
                maxNumSize = mData[i].size + 1
            }
        }

        scrollTo(0, 0)
        // 停止滚动
        mFling?.stop()

        if (isAnim) {
            startAnim(0f, 1f, 3000)
        } else {
            mPercent = 1f
            invalidate()
        }

    }



}