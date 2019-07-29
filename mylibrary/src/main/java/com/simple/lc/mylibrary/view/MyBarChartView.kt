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
import kotlin.math.sqrt

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
                    lineStartX + mDataMargin * (i + 1) - mBarMargin / 2 - mBarWidth + mPaint_text!!.measureText(
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

    /**
     * 画点击说明文
     * */
    override fun drawExplainWindow(canvas: Canvas) {

        mPaint_text!!.textAlign = Paint.Align.LEFT
        mPaint_text!!.color = mBarColor
        mPaint_text!!.textSize = explainWindowTextSize
        mPaint_grid_line!!.color = explainWindowBgColor

        if (isClick && isShowExplainWindow) {
            var msg = ""
            var msgTwo = ""
            var left = 0f
            var right = 0f
            var bottom = 0f

            msg =
                "${mData[selectedIndex - 1].name}：${Util.roundByScale(mData[selectedIndex - 1].value!!.toDouble(), mDigit)}"

            bottom = mMargin + textHeight + Util.dip2px(context, 10f)

            if (isDoubleBar) {
                msgTwo =
                    "${mData[selectedIndex - 1].name}：${Util.roundByScale(mData[selectedIndex - 1].twoValue.toDouble(), mDigit)}"

                bottom = mMargin + textHeight * 2 + Util.dip2px(context, 12f)
            }

            if (mDataMargin < (explainWindowWidth + Util.dip2px(context, 16f)) / 2) {
                when (selectedIndex) {
                    1 -> {

                        left = lineStartX + mDataMargin - explainWindowWidth / 2 - Util.dip2px(
                            context,
                            8f
                        ) + (explainWindowWidth + Util.dip2px(context, 16f)) / 2 - mDataMargin

                        right = lineStartX + mDataMargin + explainWindowWidth / 2 + Util.dip2px(
                            context,
                            8f
                        ) + (explainWindowWidth + Util.dip2px(context, 16f)) / 2 - mDataMargin

                    }
                    maxNumSize-1 -> {
                        left = lineStartX + mDataMargin * selectedIndex - explainWindowWidth / 2 - Util.dip2px(
                            context,
                            8f
                        ) + mDataMargin - (explainWindowWidth + Util.dip2px(context, 16f)) / 2

                        right = lineStartX + mDataMargin * selectedIndex + explainWindowWidth / 2 + Util.dip2px(
                            context,
                            8f
                        ) + mDataMargin - (explainWindowWidth + Util.dip2px(context, 16f)) / 2
                    }
                    else -> {
                        left =
                            lineStartX + mDataMargin * selectedIndex - explainWindowWidth / 2 - Util.dip2px(context, 8f)

                        right =
                            lineStartX + mDataMargin * selectedIndex + explainWindowWidth / 2 + Util.dip2px(context, 8f)
                    }
                }
            } else {
                left = lineStartX + mDataMargin * selectedIndex - explainWindowWidth / 2 - Util.dip2px(context, 8f)

                right = lineStartX + mDataMargin * selectedIndex + explainWindowWidth / 2 + Util.dip2px(context, 8f)
            }


            /*left = lineStartX + mDataMargin * selectedIndex - explainWindowWidth / 2 - Util.dip2px(
                context,
                8f
            )

            right = lineStartX + mDataMargin * selectedIndex + explainWindowWidth / 2 + Util.dip2px(
                context,
                8f
            )*/

            explainRect.set(
                left,
                mMargin.toFloat(),
                right,
                bottom
            )

            explainPath.reset()
            explainPath.addRoundRect(explainRect, 10f, 10f, Path.Direction.CCW)

            explainPath.moveTo(lineStartX + mDataMargin * selectedIndex, bottom)
            explainPath.rLineTo(-triangleLength / 2, 0f)
            explainPath.rLineTo(triangleLength / 2, (sqrt(3.0) * triangleLength / 2).toFloat())
            explainPath.rLineTo(triangleLength / 2, (-sqrt(3.0) * triangleLength / 2).toFloat())
            explainPath.close()
            // mPaint_grid_line!!.color = resources.getColor(R.color.explain_bg)
            //mPaint_grid_line!!.setShadowLayer(10f, 0f, 0f, 0x33000000)

            canvas.drawPath(explainPath, mPaint_grid_line!!)

            //float startX, float startY, float stopX, float stopY
            //mPaint_grid_line!!.color = resources.getColor(R.color.text_color)
            canvas.drawLine(
                lineStartX + mDataMargin * selectedIndex,
                mHeight - 2 * mMargin - textHeight,
                lineStartX + mDataMargin * selectedIndex,
                bottom,
                mPaint_grid_line!!
            )

            canvas.drawText(
                msg,
                left + Util.dip2px(
                    context,
                    8f
                ),
                mMargin + textHeight + Util.dip2px(context, 3f),
                mPaint_text!!
            )
            if (isDoubleBar) {
                canvas.drawText(
                    msgTwo,
                    left + Util.dip2px(
                        context,
                        8f
                    ),
                    mMargin + textHeight * 2 + Util.dip2px(context, 1f),
                    mPaint_text!!
                )
            }
        }
    }

    /**
     * 设置数据
     * */
    fun setData(mData: List<BarChartData>): MyBarChartView {
        if (this.mData.size > 0) {
            this.mData.clear()
        }
        this.mData.addAll(mData)
        maxNumSize = this.mData.size + 1
        mMaxValue = ceil(max(mData[0].value!!, mData[0].twoValue).toDouble()).toInt()
        for (i in 0 until mData.size) {
            if (ceil(max(mData[i].value!!, mData[i].twoValue).toDouble()).toInt() > mMaxValue) {
                mMaxValue = ceil(max(mData[i].value!!, mData[i].twoValue).toDouble()).toInt()
            }
        }
        return this
    }

    /**
     * 创建柱状图
     */
    fun build(): MyBarChartView {
        scrollTo(0, 0)
        // 停止滚动
        mFling?.stop()

        if (isAnim) {
            startAnim(0f, 1f, animTime)
        } else {
            mPercent = 1F
            invalidate()
        }
        return this
    }


    /**
     * 设置是否是双列
     * */
    fun setIsDouble(isDouble: Boolean) {
        isDoubleBar = isDouble
        //invalidate()
    }

    /**
     * 设置柱子的颜色
     * */
    fun setBarColor(color: Int) {
        mBarColor = color
        mPaint_bar!!.color = mBarColor
    }

    /**
     * 是否显示顶端数字
     * */
    fun setIsShowTopNuM(isShowTopNum: Boolean) {
        this.isShowTopNum = isShowTopNum
    }

    /**
     * 如果是双列，可以设置双列之间的间距
     * */
    fun setBarMargin(barMargin: Float) {
        if (!isDoubleBar) {
            Log.e("MySimpleChart", "单列柱状图无法设置barMargin")
            return
        }
        mBarMargin = barMargin
        invalidate()
    }


}

