package com.simple.lc.mylibrary.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.simple.lc.mylibrary.*
import java.util.ArrayList
import kotlin.math.ceil
import com.simple.lc.mylibrary.ControlPoint
import android.graphics.PathMeasure
import android.util.Log
import kotlin.math.sqrt


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
    /** 是否是曲线 */
    private var iskinkedLine = ChartConstant.LINE_DEFAULT_ISkINkEDLINE
    /** 初始化PathMeasure 用于曲线动画 */
    private var pathMeasure = PathMeasure()
    private var mDst = Path()
    /** 绘制点的集合 */
    private var pointList: MutableList<PointF> = ArrayList()
    /** 是否显示折点的圆 */
    private var isShowCircle = ChartConstant.LINE_DEFAULT_ISSHOWCIRCLE
    /** 显示文字集合 */
    private var mMsgList: MutableList<String> = ArrayList()
    /** 线的颜色集合 */
    private var mColorList: MutableList<Int> = ArrayList()
    /** 每条线的名称集合 */
    private var mNameList: MutableList<String> = ArrayList()
    /** 标记圆的宽度 */
    private var circleWidth = 0f


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

        circleWidth =
            typedArray.getDimension(R.styleable.MyLineChartView_circleWidth, Util.dip2px(context, 4f).toFloat())

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
        mPaint_line!!.isAntiAlias = true
        mPaint_line!!.style = Paint.Style.STROKE

        mPaint_Circle = Paint(Paint.ANTI_ALIAS_FLAG)
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

            mPaint_line!!.color = mColorList[j]
            mPaint_Circle!!.color = mColorList[j]

            pointList.clear()

            for (i in 0 until mData[j].size) {
                //画线
                val x = lineStartX + mDataMargin * (i + 1)
                val y =
                    mHeight.toFloat() - 2 * mMargin - textHeight - mData[j][i].value!! / (itemValue * mSegment).toFloat() * realHeight

                canvas.drawText(
                    mData[j][i].xValue!!,
                    x + mPaint_text!!.measureText(mData[j][i].xValue) / 2,
                    mHeight.toFloat() - mMargin,
                    mPaint_text!!
                )

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

            if (isShowCircle && mPercent == 1f) {
                for (point in pointList) {
                    canvas.drawCircle(point.x, point.y, circleWidth, mPaint_Fill!!)
                    canvas.drawCircle(point.x, point.y, circleWidth, mPaint_Circle!!)
                }
            }
        }

    }

    override fun drawExplainWindow(canvas: Canvas) {

        mPaint_text!!.textAlign = Paint.Align.LEFT
        mPaint_text!!.textSize = explainWindowTextSize
        mPaint_grid_line!!.color = explainWindowBgColor

        if (isClick && isShowExplainWindow) {

            mMsgList.clear()
            var left = 0f
            var right = 0f

            for (i in 0 until mData.size) {
                if (selectedIndex <= mData[i].size) {
                    mMsgList.add("${mNameList[i]}：${Util.roundByScale(mData[i][selectedIndex - 1].value!!.toDouble(), mDigit)}")
                }
            }

            val bottom = mMargin + textHeight * mMsgList.size + Util.dip2px(context, 10f)

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
                    maxNumSize - 1 -> {
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

            explainRect.set(
                left,
                mMargin.toFloat(),
                right,
                bottom
            )

            canvas.drawLine(
                lineStartX + mDataMargin * selectedIndex,
                mHeight - 2 * mMargin - textHeight,
                lineStartX + mDataMargin * selectedIndex,
                bottom,
                mPaint_grid_line!!
            )

            for (j in 0 until mData.size) {
                mPaint_Circle!!.color = mColorList[j]
                if (selectedIndex <= mData[j].size) {
                    //画占位圆
                    val x = lineStartX + mDataMargin * selectedIndex
                    val y =
                        mHeight.toFloat() - 2 * mMargin - textHeight - mData[j][selectedIndex - 1].value!! / (itemValue * mSegment).toFloat() * realHeight
                    //x轴画字（只需执行一次）
                    canvas.drawCircle(x, y, circleWidth, mPaint_Fill!!)
                    canvas.drawCircle(x, y, circleWidth, mPaint_Circle!!)
                }
            }

            explainPath.reset()
            explainPath.addRoundRect(explainRect, 10f, 10f, Path.Direction.CCW)

            explainPath.moveTo(lineStartX + mDataMargin * selectedIndex, bottom)
            explainPath.rLineTo(-triangleLength / 2, 0f)
            explainPath.rLineTo(triangleLength / 2, (sqrt(3.0) * triangleLength / 2).toFloat())
            explainPath.rLineTo(triangleLength / 2, (-sqrt(3.0) * triangleLength / 2).toFloat())
            explainPath.close()

            //mPaint_grid_line!!.setShadowLayer(10f, 0f, 0f, 0x33000000)

            canvas.drawPath(explainPath, mPaint_grid_line!!)

            for (i in 0 until mMsgList.size) {
                mPaint_text!!.color = mColorList[i]
                canvas.drawText(
                    mMsgList[i],
                    left + Util.dip2px(context, 8f),
                    mMargin + textHeight * (i + 1) + Util.dip2px(context, 3f),
                    mPaint_text!!
                )
            }

        }
    }

    /**
     * 设置数据
     * */
    fun setData(mDatas: MutableList<MutableList<LineChartData>>, maxValue: Float): MyLineChartView {

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


        return this
    }

    /**
     * 每条线的颜色
     * */

    fun setColor(colorList: MutableList<Int>): MyLineChartView {
        mColorList.clear()
        mColorList.addAll(colorList)
        return this
    }

    /**
     * 线名称的集合
     * */
    fun setName(nameList: MutableList<String>): MyLineChartView {
        mNameList.clear()
        mNameList.addAll(nameList)
        return this
    }

    /**
     * 创建折线图
     * */
    fun build() {
        scrollTo(0, 0)
        // 停止滚动
        mFling?.stop()

        if (isAnim) {
            startAnim(0f, 1f, animTime)
        } else {
            mPercent = 1f
            invalidate()
        }
    }

    fun setLineWidth(lineWidth:Float):MyLineChartView{
        mLineWidth=lineWidth
        mPaint_line!!.strokeWidth = mLineWidth
        return this
    }

    fun setCircleWidth(circleWidth:Float):MyLineChartView{
        this.circleWidth = circleWidth
        return this
    }

    fun setIskinkedLine(iskinkedLine:Boolean):MyLineChartView{
        this.iskinkedLine = iskinkedLine
        return this
    }

    fun setIsShowCircle(isShowCircle:Boolean):MyLineChartView{
        this.isShowCircle = isShowCircle
        return this
    }


}