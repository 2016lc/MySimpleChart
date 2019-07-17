package com.simple.lc.mylibrary.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import com.simple.lc.mylibrary.*
import java.util.*
import kotlin.math.ceil
import kotlin.math.max

/**
 * Author:LC
 * Date:2018/12/4
 * Description:柱状图
 */
class MyBarChartView(context: Context?, attrs: AttributeSet?) : MyCommonView(context, attrs) {

    /** 画柱子画笔 */
    private var mPaint_bar: Paint? = null
    /** 柱子的宽度 */
    private var mBarWidth: Float = ChartConstant.BAR_DEFAULT_BARWIDTH
    /** 柱子的颜色 */
    private var mBarColor: Int = 0
    /** 数据集合 */
    private var mData: MutableList<BarChartData> = ArrayList()
    /** 画柱子的矩形 */
    private var mRectF: RectF? = null
    /** 是否显示顶端数字 */
    private var isShowTopNum: Boolean = ChartConstant.BAR_DEFAULT_ISSHOWTOPNUM
    /** 是否是双列柱形图 */
    private var isDoubleBar: Boolean = ChartConstant.BAR_DEFAULT_ISDOUBLE
    /** 如果是双列，两个柱子之间的间距 */
    private var mBarMargin: Float = ChartConstant.BAR_DEFAULT_BARMARGIN


    init {
        val typedArray = context!!.obtainStyledAttributes(
            attrs,
            R.styleable.MyBarChartView
        )
        mBarWidth = typedArray.getDimension(R.styleable.MyBarChartView_barWidth, Util.dip2px(context, 8f).toFloat())
        mBarColor =
            typedArray.getColor(R.styleable.MyBarChartView_barColor, ContextCompat.getColor(context, R.color.bar_color))
        isShowTopNum =
            typedArray.getBoolean(R.styleable.MyBarChartView_barIsShowTopNum, ChartConstant.BAR_DEFAULT_ISSHOWTOPNUM)
        isDoubleBar = typedArray.getBoolean(R.styleable.MyBarChartView_barIsDouble, ChartConstant.BAR_DEFAULT_ISDOUBLE)

        mBarMargin = typedArray.getDimension(R.styleable.MyBarChartView_barMargin, Util.dip2px(context, 8f).toFloat())

        typedArray.recycle()

        prePare()
    }

    private fun prePare() {
        mPaint_bar = Paint()
        mPaint_bar!!.isDither = true
        mPaint_bar!!.strokeWidth = mBarWidth
        mPaint_bar!!.color = mBarColor
        mPaint_bar!!.style = Paint.Style.FILL

        mRectF = RectF()
    }


    override fun drawContents(canvas: Canvas) {

        if (mData.size == 0) {
            return
        }
        if (isDoubleBar) {
            for (i in 0 until mData.size) {

                //画第二根柱
                mRectF!!.right = lineStartX + mDataMargin * (i + 1)

                mRectF!!.left = mRectF!!.right - mBarWidth

                mRectF!!.bottom = mHeight.toFloat() - 2 * mMargin - textHeight

                mRectF!!.top =
                    mRectF!!.bottom - mData[i].twoValue / (itemValue * mSegment).toFloat() * realHeight * mPercent

                canvas.drawRect(mRectF!!, mPaint_bar!!)

                //画第一根柱
                mRectF!!.right = lineStartX + mDataMargin * (i + 1) - mBarMargin - mBarWidth

                mRectF!!.left = mRectF!!.right - mBarWidth

                mRectF!!.bottom = mHeight.toFloat() - 2 * mMargin - textHeight

                mRectF!!.top =
                    mRectF!!.bottom - mData[i].value!! / (itemValue * mSegment).toFloat() * realHeight * mPercent

                canvas.drawRect(mRectF!!, mPaint_bar!!)


                //画字
                canvas.drawText(
                    mData[i].name!!,
                    lineStartX + mDataMargin * (i + 1) - mBarMargin / 2 - mBarWidth  + mPaint_text!!.measureText(
                        mData[i].name!!
                    ) / 2,
                    mHeight.toFloat() - mMargin,
                    mPaint_text!!
                )

                //柱子顶端画字
                if (isShowTopNum) {

                    canvas.drawText(
                        Util.roundByScale(mData[i].twoValue.toDouble() * mPercent, mDigit),
                        lineStartX + mDataMargin * (i + 1) + mPaint_text!!.measureText(
                            Util.roundByScale(
                                mData[i].twoValue.toDouble() * mPercent,
                                mDigit
                            )
                        ) / 2 - mBarWidth / 2,
                        mHeight.toFloat() - 2 * mMargin - textHeight - mData[i].twoValue!! / (itemValue * mSegment).toFloat() * realHeight * mPercent - Util.dp2px(
                            context,
                            5f
                        ),
                        mPaint_text!!
                    )

                    canvas.drawText(
                        Util.roundByScale(mData[i].value!!.toDouble() * mPercent, mDigit),
                        lineStartX + mDataMargin * (i + 1) - mBarMargin - mBarWidth + mPaint_text!!.measureText(
                            Util.roundByScale(
                                mData[i].value!!.toDouble() * mPercent,
                                mDigit
                            )
                        ) / 2 - mBarWidth / 2,
                        mHeight.toFloat() - 2 * mMargin - textHeight - mData[i].value!! / (itemValue * mSegment).toFloat() * realHeight * mPercent - Util.dp2px(
                            context,
                            5f
                        ),
                        mPaint_text!!
                    )

                }
            }
        } else {
            for (i in 0 until mData.size) {
                //画柱
                mRectF!!.right = lineStartX + mDataMargin * (i + 1)

                mRectF!!.left = mRectF!!.right - mBarWidth

                mRectF!!.bottom = mHeight.toFloat() - 2 * mMargin - textHeight

                mRectF!!.top =
                    mRectF!!.bottom - mData[i].value!! / (itemValue * mSegment).toFloat() * realHeight * mPercent

                canvas.drawRect(mRectF!!, mPaint_bar!!)

                //画字
                canvas.drawText(
                    mData[i].name!!,
                    mRectF!!.right + mPaint_text!!.measureText(mData[i].name.toString()) / 2 - mBarWidth / 2,
                    mHeight.toFloat() - mMargin,
                    mPaint_text!!
                )

                //柱子顶端画字
                if (isShowTopNum) {
                    canvas.drawText(
                        Util.roundByScale(mData[i].value!!.toDouble() * mPercent, mDigit),
                        mRectF!!.right + mPaint_text!!.measureText(
                            Util.roundByScale(
                                mData[i].value!!.toDouble() * mPercent,
                                mDigit
                            )
                        ) / 2 - mBarWidth / 2,
                        mRectF!!.top - Util.dp2px(context, 5f),
                        mPaint_text!!
                    )
                }
            }
        }
    }


    override fun getBarData(): MutableList<BarChartData>? {
        return mData
    }

    override fun isDouble(): Boolean {
        return isDoubleBar
    }

    /**
     * 设置数据
     * */
    fun setData(mData: List<BarChartData>) {
        if (this.mData.size > 0) {
            this.mData.clear()
        }

        this.mData.addAll(mData)

        maxNumSize = this.mData.size

        mMaxValue = ceil(max(mData[0].value!!, mData[0].twoValue).toDouble()).toInt()
        for (i in 0 until mData.size) {
            if (ceil(max(mData[i].value!!, mData[i].twoValue).toDouble()).toInt() > mMaxValue) {
                mMaxValue = ceil(max(mData[i].value!!, mData[i].twoValue).toDouble()).toInt()
            }
        }

        scrollTo(0, 0)
        // 停止滚动
        mFling?.stop()

        if (isAnim) {
            startAnim(0f, 1f, animTime)
        } else {
            mPercent = 1F
            invalidate()
        }
    }


    /**
     * 设置是否是双列
     * */
    fun setIsDouble(isDouble: Boolean) {
        isDoubleBar = isDouble
        invalidate()
    }

    /**
     * 设置柱子的颜色
     * */
    fun setBarColor(color: Int) {
        mBarColor = color
        mPaint_bar!!.color = mBarColor
        invalidate()
    }

    override fun getColor(): Int {
        return mBarColor
    }

    /**
     * 是否显示顶端数字
     * */
    fun setIsShowTopNuM(isShowTopNum: Boolean) {
        this.isShowTopNum = isShowTopNum
        invalidate()
    }

    /**
     * 如果是双列，可以设置双列之间的间距
     * */
    fun setBarMargin(barMargin: Float) {
        if (!isDoubleBar) {
            Log.e("CHART", "单列柱状图无法设置barMargin")
            return
        }
        mBarMargin = barMargin
        invalidate()
    }


}

