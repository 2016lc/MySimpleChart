package com.simple.lc.mylibrary.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.simple.lc.mylibrary.BarChartConstant
import com.simple.lc.mylibrary.BarChartData
import com.simple.lc.mylibrary.R
import com.simple.lc.mylibrary.Util
import java.util.*

/**
 * Author:LC
 * Date:2018/12/4
 * Description:This is 横向柱状图
 */
class MyHorizontalBarChartView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var mPaint_xy: Paint? = null//x y轴
    private var mPaint_line: Paint? = null//网格线
    private var mPaint_bar: Paint? = null//画柱子
    private var mSpacePaint: Paint? = null//左右的空间间隔
    private var mPaint_text: TextPaint? = null//字体画笔
    private var mTextSize: Int? = null//字体大小
    private var mStrokeWidth: Int? = null//线宽度
    private var mWidth: Int? = null//控件宽
    private var mHeight: Int? = null//控件高
    private var mMargin: Int? = null//间距
    private var mTextMargin: Int? = null//x轴字体间距
    private var mMaxValue: Int? = null//集合最大值，用于y轴取值分段
    private var mSegment: Int = BarChartConstant.DEFAULT_SEGMENT//y轴分为几段
    private var mBarWidth: Float? = null//柱子的宽度
    private var mData: MutableList<BarChartData> = ArrayList()
    private var mRectF: RectF? = null
    private var topMoving: Float = 0f
    private var lastPointX: Float = 0f
    private var movingLeftThisTime = 0f
    private var lineStartX: Float = 0f
    private var leftSpaceRect: RectF? = null
    private var rightSpaceRect: RectF? = null
    private var mBg: Int? = null//背景颜色
    private var canScrollSpace: Float = 0f
    private var mExtraSpace: Float = 0f
    private var yUnit: String? = null
    private var mDigit: Int? = null
    private var mBarColor: Int? = null
    private var mAnimator: ValueAnimator? = null//属性动画
    private var mPercent: Float = 0f//动画进度
    private var isAnim: Boolean? = null//是否需要动画
    private var animTime: Int? = null
    private var isShowTopNum: Boolean? = null//是否显示柱子顶端数字
    private var isShowGridLine: Boolean? = null//是否显示网格线
    private var textHeight: Float = 0f
    private var mTextMaxWidth: Float = 0f

    init {
        mMargin = Util.dip2px(context!!, 8f)
        mStrokeWidth = Util.dip2px(context, 0.7f)
        mTextSize = Util.dip2px(context, 10f)
        mRectF = RectF()
        leftSpaceRect = RectF()
        rightSpaceRect = RectF()
        mAnimator = ValueAnimator()
        initAttrs(attrs, context)//初始化属性
        initPaint()//初始化画笔
    }

    private fun initAttrs(attrs: AttributeSet?, context: Context?) {
        val typedArray = context!!.obtainStyledAttributes(
            attrs,
            R.styleable.MyBarChartView
        )
        mBarWidth = typedArray.getDimension(
            R.styleable.MyBarChartView_barWidth,
            BarChartConstant.DEFAULT_BARWIDTH
        )
        mBg = typedArray.getColor(R.styleable.MyBarChartView_bg, Color.WHITE)
        yUnit = typedArray.getString(R.styleable.MyBarChartView_yUnit)
        mSegment = typedArray.getInt(
            R.styleable.MyBarChartView_mSegment,
            BarChartConstant.DEFAULT_SEGMENT
        )
        mDigit = typedArray.getInt(
            R.styleable.MyBarChartView_mDigit,
            BarChartConstant.DEFAULT_DIGIT
        )
        mBarColor = typedArray.getColor(R.styleable.MyBarChartView_barColor, Color.BLACK)
        isAnim = typedArray.getBoolean(
            R.styleable.MyBarChartView_isAnim,
            BarChartConstant.DEFAULT_ISANIM
        )
        animTime = typedArray.getInt(
            R.styleable.MyBarChartView_animTime,
            BarChartConstant.DEFAULT_ANIMTIME
        )
        isShowGridLine = typedArray.getBoolean(
            R.styleable.MyBarChartView_isShowGridLine,
            BarChartConstant.DEFAULT_ISSHOWGRIDLINE
        )
        isShowTopNum =
                typedArray.getBoolean(
                    R.styleable.MyBarChartView_isShowTopNum,
                    BarChartConstant.DEFAULT_ISSHOWTOPNUM
                )

        if (yUnit == null) {
            yUnit = ""
        }
        typedArray.recycle()
    }

    private fun initPaint() {
        mPaint_xy = Paint()
        mPaint_xy!!.isDither = true
        mPaint_xy!!.strokeWidth = mStrokeWidth!!.toFloat()
        mPaint_xy!!.color = resources.getColor(R.color.xy_color)
        mPaint_xy!!.style = Paint.Style.FILL

        mPaint_line = Paint()
        mPaint_line!!.isDither = true
        mPaint_line!!.strokeWidth = mStrokeWidth!!.toFloat()
        mPaint_line!!.color = resources.getColor(R.color.line_color)
        mPaint_line!!.style = Paint.Style.FILL

        mPaint_text = TextPaint()
        mPaint_text!!.color = resources.getColor(R.color.text_color)
        mPaint_text!!.isAntiAlias = true
        mPaint_text!!.textSize = mTextSize!!.toFloat()//字体大小
        mPaint_text!!.textAlign = Paint.Align.RIGHT

        mPaint_bar = Paint()
        mPaint_bar!!.isDither = true
        mPaint_bar!!.strokeWidth = mBarWidth!!
        mPaint_bar!!.color = mBarColor!!
        mPaint_bar!!.style = Paint.Style.FILL

        mSpacePaint = Paint()
        mSpacePaint!!.isDither = true
        mSpacePaint!!.color = mBg!!
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawColor(mBg!!)
        drawTextAndBar(canvas)
        drawXy(canvas)
    }

    /**
     * 画x y轴字体 和 柱子
     * */
    private fun drawTextAndBar(canvas: Canvas?) {
        if (mData.size == 0) {
            return
        }
        mTextMargin = Util.dip2px(context, 40f) + mBarWidth!!.toInt()
        textHeight = mPaint_text!!.descent() - mPaint_text!!.ascent()

        val lastValueWidth = mPaint_text!!.measureText(mData[mData.size - 1].value.toString())
        val lastNameWidth = mPaint_text!!.measureText(mData[mData.size - 1].name.toString())
        val max = Math.max(lastValueWidth, lastNameWidth)

        //向上偏移量
        mExtraSpace = if (mBarWidth!! >= max) {
            0f
        } else {
            (max - mBarWidth!!) / 2
        }

        //如果说按照设置的间距无法铺满y轴，那么就将数据平分（数据很少的情况）
        if (mHeight!! - textHeight - 3 * mMargin!! - Util.dip2px(
                context,
                5f
            ) > mTextMargin!! * mData.size
        ) {

            val margin = (mWidth!! - textHeight - 3 * mMargin!! - Util.dip2px(
                context,
                5f
            ) - mExtraSpace) / mData.size

            mTextMargin = margin.toInt()
        }

        //x轴
        if (mMaxValue!! < mSegment) {
            mMaxValue = mSegment
        }

        //x轴的每一个数字
        val itemValue = Math.ceil(mMaxValue!! / mSegment.toDouble()).toInt()
        val itemWidth = (mWidth!! - 3 * mMargin!! - Util.dip2px(
            context,
            25f
        ) - mTextMaxWidth) / mSegment


        lineStartX = mTextMaxWidth + 2 * mMargin!!

        val realWidth = mWidth!! - 3 * mMargin!! - mTextMaxWidth!! - Util.dip2px(
            context,
            25f
        )

        val bottom = mHeight!!.toFloat() - 2 * mMargin!! - textHeight


        if (isShowGridLine!!) {
            for (i in 0 until mSegment) {
                //网格线头
                canvas!!.drawLine(
                    lineStartX + itemWidth * (i + 1),
                    mHeight!! - 2 * mMargin!! - textHeight,
                    lineStartX + itemWidth * (i + 1),
                    mHeight!! - 2 * mMargin!! - textHeight - Util.dip2px(context, 8f),
                    mPaint_xy!!
                )

                //网格线
                canvas.drawLine(
                    lineStartX + itemWidth * (i + 1),
                    mHeight!! - 2 * mMargin!! - textHeight - Util.dip2px(context, 8f),
                    lineStartX + itemWidth * (i + 1),
                    mMargin!! + Util.dip2px(context, 5f).toFloat(),
                    mPaint_line!!
                )
            }
        }

        for (i in 0 until mData.size) {
            //画柱
            mRectF!!.bottom = mTextMargin!! * i - topMoving + mMargin!! + Util.dip2px(
                context,
                15f
            ) + mBarWidth!!
            mRectF!!.top = mRectF!!.bottom - mBarWidth!!
            mRectF!!.left = lineStartX
            mRectF!!.right = lineStartX + mData[i].value!! / (itemValue * mSegment).toFloat() * realWidth * mPercent

            canvas!!.drawRect(mRectF!!, mPaint_bar!!)

            //画字
            canvas.drawText(
                mData[i].name!!,
                mMargin!! + mTextMaxWidth,
                mRectF!!.bottom - (mBarWidth!! - textHeight * 2 / 3) / 2,
                mPaint_text!!
            )

            //柱子顶端画字
            if (isShowTopNum!!) {
                canvas.drawText(
                    Util.roundByScale(mData[i].value!!.toDouble() * mPercent, mDigit!!),
                    mRectF!!.right - Util.dip2px(
                        context,
                        5f
                    ) + mPaint_text!!.measureText(mData[i].value.toString()),
                    mRectF!!.bottom - (mBarWidth!! - textHeight * 2 / 3) / 2,
                    mPaint_text!!
                )
            }
        }


        //用于遮挡左右的间距，不然得滑出屏幕过后才消失（待优化）
        leftSpaceRect!!.left = 0f
        leftSpaceRect!!.top = 0f
        leftSpaceRect!!.right = mWidth!!.toFloat()
        leftSpaceRect!!.bottom = mMargin!!.toFloat()
        canvas!!.drawRect(leftSpaceRect!!, mSpacePaint!!)
        rightSpaceRect!!.left = 0f
        rightSpaceRect!!.top = mHeight!!.toFloat() - 2 * mMargin!! - textHeight
        rightSpaceRect!!.right = mWidth!!.toFloat()
        rightSpaceRect!!.bottom = mHeight!!.toFloat()
        canvas.drawRect(rightSpaceRect!!, mSpacePaint!!)


        //保证向上滑动时有一屏的显示，不会全部滑出屏幕外
        canScrollSpace = mTextMargin!! *
                mData.size + mExtraSpace -
                (mHeight!! - 3 * mMargin!! - Util.dip2px(
                    context,
                    5f
                ) - textHeight)


        for (i in 0 until mSegment) {
            //x轴数字
            canvas.drawText(
                ((i + 1) * itemValue).toString(),
                2 * mMargin!! + mTextMaxWidth + (i + 1) * itemWidth + mPaint_text!!.measureText(((i + 1) * itemValue).toString()) / 2 -  mPaint_text!!.measureText(yUnit)/2,
                mHeight!! - mMargin!!.toFloat(),
                mPaint_text!!
            )
            //x轴单位
            canvas.drawText(
                yUnit,
                2 * mMargin!! + mTextMaxWidth + (i + 1) * itemWidth + mPaint_text!!.measureText(((i + 1) * itemValue).toString()) / 2 +  mPaint_text!!.measureText(yUnit)/2,
                mHeight!! - mMargin!!.toFloat(),
                mPaint_text!!
            )
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w - paddingLeft - paddingRight
        mHeight = h - paddingTop - paddingBottom
    }


    /**
     * 手势控制
     * */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val type = event.action

        when (type) {
            MotionEvent.ACTION_DOWN -> lastPointX = event.rawY

            MotionEvent.ACTION_MOVE -> {
                val y = event.rawY
                movingLeftThisTime = lastPointX - y

                topMoving += movingLeftThisTime
                lastPointX = y

                invalidate()
            }

            MotionEvent.ACTION_UP ->
                //smooth scroll
                Thread(SmoothScrollThread(movingLeftThisTime)).start()

            else -> return super.onTouchEvent(event)
        }

        return true
    }


    private inner class SmoothScrollThread(internal var lastMoving: Float) : Runnable {
        internal var scrolling = true

        init {
            scrolling = true
        }

        override fun run() {
            while (scrolling) {
                val start = System.currentTimeMillis()
                lastMoving = (0.9f * lastMoving).toInt().toFloat()
                topMoving += lastMoving

                checkLeftMoving()
                postInvalidate()

                if (Math.abs(lastMoving) < 5) {
                    scrolling = false
                }

                val end = System.currentTimeMillis()
                if (end - start < 20) {
                    try {
                        Thread.sleep(20 - (end - start))
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }

    private fun checkLeftMoving() {
        if (topMoving < 0) {
            topMoving = 0f
        }

        if (topMoving > canScrollSpace) {
            topMoving = canScrollSpace
        }
    }


    /**
     * 画x y轴
     * */
    private fun drawXy(canvas: Canvas?) {
        val stopY = mHeight!!.toFloat() - 2 * mMargin!! - textHeight
        //x轴
        canvas!!.drawLine(
            lineStartX,
            stopY,
            mWidth!!.toFloat() - mMargin!!,
            stopY,
            mPaint_xy!!
        )
        //y轴
        canvas.drawLine(
            lineStartX,
            mMargin!!.toFloat(),
            lineStartX,
            stopY,
            mPaint_xy!!
        )

        //画x轴三角形
        val path = Path()
        path.close()
        path.moveTo(
            mWidth!!.toFloat() - mMargin!!,
            stopY
        )
        path.lineTo(
            mWidth!!.toFloat() - mMargin!! - Util.dip2px(context, 5f),
            mHeight!!.toFloat() - 2 * mMargin!! - Util.dip2px(
                context,
                3f
            ) - textHeight
        )
        path.lineTo(
            mWidth!!.toFloat() - mMargin!! - Util.dip2px(context, 5f),
            mHeight!!.toFloat() - 2 * mMargin!! + Util.dip2px(
                context,
                3f
            ) - textHeight
        )
        canvas.drawPath(path, mPaint_xy!!)
        //画y轴三角形
        path.moveTo(lineStartX, mMargin!!.toFloat())
        path.lineTo(
            lineStartX + Util.dip2px(context, 3f),
            mMargin!! + Util.dip2px(context, 5f).toFloat()
        )
        path.lineTo(
            lineStartX - Util.dip2px(context, 3f),
            mMargin!! + Util.dip2px(context, 5f).toFloat()
        )
        canvas.drawPath(path, mPaint_xy!!)
    }


    /**
     * 设置数据
     * */
    fun setData(mDatas: List<BarChartData>) {
        if (mData.size > 0) {
            mData.clear()
        }
        mData.addAll(mDatas)
        mMaxValue = Math.ceil(mData[0].value!!.toDouble()).toInt()
        mTextMaxWidth = mPaint_text!!.measureText(mDatas[0].name)
        for (i in 0 until mData.size) {
            if (Math.ceil(mData[i].value!!.toDouble()).toInt() > mMaxValue!!) {
                mMaxValue = Math.ceil(mData[i].value!!.toDouble()).toInt()
            }
            if (mPaint_text!!.measureText(mDatas[i].name) > mTextMaxWidth) {
                mTextMaxWidth = mPaint_text!!.measureText(mDatas[i].name)
            }
        }

        if (isAnim!!) {
            startAnim(0f, 1f, animTime!!)
        } else {
            mPercent = 1F
            invalidate()
        }

    }


    /**
     * 动画
     * */
    private fun startAnim(start: Float, end: Float, animTime: Int) {
        mAnimator = ValueAnimator.ofFloat(start, end)
        mAnimator!!.duration = animTime.toLong()
        mAnimator!!.addUpdateListener {

            mPercent = it.animatedValue as Float

            postInvalidate()
        }
        mAnimator!!.start()
    }


}

