package com.simple.lc.mylibrary.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.widget.Scroller
import com.simple.lc.mylibrary.*
import java.util.ArrayList

/**
 * Author:LC
 * Date:2018/12/26
 * Description:折线图
 */
class MyLineChartView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var mPaint_xy: Paint? = null//x y轴
    private var mPaint_grid_line: Paint? = null//网格线
    private var mPaint_line: Paint? = null//画折线
    private var mPaint_Circle: Paint? = null//每个点的圆
    private var mPaint_Fill: Paint? = null//每个圆点的填充
    private var mSpacePaint: Paint? = null//左右的空间间隔
    private var mPaint_text: TextPaint? = null//字体画笔

    private var mTextSize: Int? = null//字体大小
    private var mStrokeWidth: Int? = null//xy、分割线线宽度
    private var mWidth: Int? = null//控件宽
    private var mHeight: Int? = null//控件高
    private var mMargin: Int? = null//间距
    private var mTextMargin: Int? = null//x轴字体间距
    private var mMaxValue: Int? = null//集合最大值，用于y轴取值分段
    private var mSegment: Int = ChartConstant.LINE_DEFAULT_SEGMENT//y轴分为几段
    private var mLineWidth: Float? = null//折线的宽度

    private var mData: MutableList<MutableList<LineChartData>> = ArrayList()//数据
    private var mPathData: MutableList<Path> = ArrayList()//画折线的path

    private var leftMoving: Float = 0f//滑动的总偏移量
    private var lastPointX: Float = 0f//手指按下时的标记坐标
    private var movingLeftThisTime = 0f//滑动时的偏移量
    private var lineStartX: Float = 0f//x轴的起始点
    private var spaceRect: RectF? = null//截取超过xy轴的部分空间
    private var mBg: Int? = null//背景颜色
    private var canScrollSpace: Float = 0f//能够滑动的距离
    private var mExtraSpace: Float = 0f//初始向左偏移量

    private var yUnit: String? = null//单位
    private var mDigit: Int? = null//小数点后面位数

    private var mLineColor: Int? = null//折线的颜色
    private var mAnimator: ValueAnimator? = null//属性动画
    private var mPercent: Float = 0f//动画进度
    private var isAnim: Boolean? = null//是否需要动画
    private var animTime: Int? = null//动画时间

    private var isShowGridLine: Boolean? = null//是否显示网格线
    private var textHeight: Float = 0f//字体高度
    /**
     * 滑动优化
     * */
    private var velocityTracker: VelocityTracker? = null
    private var scroller: Scroller? = null

    init {
        mMargin = Util.dip2px(context!!, 8f)
        mStrokeWidth = Util.dip2px(context, 0.7f)
        mTextSize = Util.dip2px(context, 10f)
        spaceRect = RectF()
        mAnimator = ValueAnimator()
        scroller = Scroller(context)
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
        mBg = typedArray.getColor(R.styleable.MyLineChartView_bg, Color.WHITE)
        yUnit = typedArray.getString(R.styleable.MyLineChartView_yUnit)

        mSegment = typedArray.getInt(
            R.styleable.MyLineChartView_mSegment,
            ChartConstant.LINE_DEFAULT_SEGMENT
        )
        mDigit = typedArray.getInt(
            R.styleable.MyLineChartView_mDigit,
            ChartConstant.LINE_DEFAULT_DIGIT
        )

        mLineColor = typedArray.getColor(R.styleable.MyLineChartView_lineColor, Color.BLACK)

        isAnim = typedArray.getBoolean(
            R.styleable.MyLineChartView_isAnim,
            ChartConstant.LINE_DEFAULT_ISANIM
        )

        animTime = typedArray.getInt(
            R.styleable.MyLineChartView_animTime,
            ChartConstant.LINE_DEFAULT_ANIMTIME
        )


        isShowGridLine = typedArray.getBoolean(
            R.styleable.MyLineChartView_isShowGridLine,
            ChartConstant.LINE_DEFAULT_ISGRIDLINE
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

        mPaint_grid_line = Paint()
        mPaint_grid_line!!.isDither = true
        mPaint_grid_line!!.strokeWidth = mStrokeWidth!!.toFloat()
        mPaint_grid_line!!.color = resources.getColor(R.color.colorAccent)
        mPaint_grid_line!!.style = Paint.Style.FILL

        mPaint_text = TextPaint()
        mPaint_text!!.color = resources.getColor(R.color.text_color)
        mPaint_text!!.isAntiAlias = true
        mPaint_text!!.textSize = mTextSize!!.toFloat()//字体大小
        mPaint_text!!.textAlign = Paint.Align.RIGHT

        mPaint_line = Paint()
        mPaint_line!!.isDither = true
        mPaint_line!!.strokeWidth = mLineWidth!!
        mPaint_line!!.color = mLineColor!!
        mPaint_line!!.style = Paint.Style.STROKE

        mPaint_Circle = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint_Circle!!.color = mLineColor!!
        mPaint_Circle!!.isAntiAlias = true
        mPaint_Circle!!.isDither = true
        mPaint_Circle!!.strokeWidth = Util.dip2px(context, 2f).toFloat()
        mPaint_Circle!!.style = Paint.Style.STROKE

        mPaint_Fill = Paint()
        mPaint_Fill!!.color = mBg!!
        mPaint_Fill!!.isAntiAlias = true
        mPaint_Fill!!.isDither = true
        mPaint_Fill!!.style = Paint.Style.FILL

        mSpacePaint = Paint()
        mSpacePaint!!.isDither = true
        mSpacePaint!!.color = mBg!!
        // mSpacePaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.save()
        canvas.drawColor(mBg!!)
        drawXyandTriangle(canvas)//画xy轴以及三角形
        drawTextAndLine(canvas)//画内容
        canvas.restore()
    }

    /**
     * 画x y轴字体 和 线
     * */
    private fun drawTextAndLine(canvas: Canvas?) {
        if (mData.size == 0) {
            return
        }

        mTextMargin = mWidth!! / 7//初始化间距
        textHeight = mPaint_text!!.descent() - mPaint_text!!.ascent()


        val lastValueWidth = mPaint_text!!.measureText(mMaxValue.toString())
        val lastNameWidth = mPaint_text!!.measureText(mData[0][(mData[0].size - 1)].name.toString())
        val max = Math.max(lastValueWidth, lastNameWidth)

        //向左偏移量
        mExtraSpace = max / 2

        //如果说按照设置的间距无法铺满x轴，那么就将数据平分（数据很少的情况）
        if (mWidth!! - mPaint_text!!.measureText(mMaxValue.toString()) - 3 * mMargin!! - Util.dip2px(
                context,
                5f
            ) > mTextMargin!! * mData[0].size
        ) {

            val margin = (mWidth!! - mPaint_text!!.measureText(mMaxValue.toString()) - 3 * mMargin!! - Util.dip2px(
                context,
                5f
            ) - mExtraSpace) / mData[0].size

            mTextMargin = margin.toInt()
        }

        //y轴
        if (mMaxValue!! < mSegment) {
            mMaxValue = mSegment
        }

        //y轴的每一个数字
        val itemValue = Math.ceil(mMaxValue!! / mSegment.toDouble()).toInt()
        val itemHeight = (mHeight!! - 3 * mMargin!! - Util.dip2px(
            context,
            15f
        ) - textHeight) / mSegment //（ 总高度 - 上面一个margin - 下面两个margin - 距离上面的高度 - x轴字体的高度 ）/分成的段数


        lineStartX = mPaint_text!!.measureText(mMaxValue.toString()) + mPaint_text!!.measureText(yUnit) + 2 * mMargin!!

        val lineStartY = mMargin!! + Util.dip2px(
            context,
            15f
        )

        val textX = mMargin!!.toFloat() + mPaint_text!!.measureText(mMaxValue.toString())  // y轴字的x坐标
        val textY = lineStartY + textHeight / 4 //y轴字的y坐标

        val realHeight =
            mHeight!! - 3 * mMargin!! - Util.dip2px(
                context,
                15f
            ) - textHeight


        for (j in 0 until mData.size) {

            val linePath = mPathData[j]

            mPaint_line!!.color = mData[j][0].color!!
            mPaint_Circle!!.color = mData[j][0].color!!
            for (i in 0 until mData[j].size) {
                //画线
                val x = lineStartX + mTextMargin!! * (i + 1) - leftMoving
                val y =
                    mHeight!!.toFloat() - 2 * mMargin!! - textHeight - mData[j][i].value!! / (itemValue * mSegment).toFloat() * realHeight

                if (i == 0) {
                    linePath.moveTo(
                        x,
                        y
                    )
                } else {
                    linePath.lineTo(
                        x, y
                    )
                }

            }
            canvas!!.drawPath(linePath, mPaint_line!!)
            linePath.reset()

            for (i in 0 until mData[j].size) {
                //画占位圆与网格线
                val x = lineStartX + mTextMargin!! * (i + 1) - leftMoving
                val y =
                    mHeight!!.toFloat() - 2 * mMargin!! - textHeight - mData[j][i].value!! / (itemValue * mSegment).toFloat() * realHeight

                //x轴画字（只需执行一次）
                canvas.drawText(
                    mData[j][i].name!!,
                    x + mPaint_text!!.measureText(mData[j][i].name.toString()) / 2,
                    mHeight!!.toFloat() - mMargin!!,
                    mPaint_text!!
                )
                //x轴网格线
                canvas.drawLine(
                    x,
                    mHeight!! - 2 * mMargin!! - textHeight,
                    x,
                    mMargin!! + Util.dip2px(context, 5f).toFloat(),
                    mPaint_grid_line!!
                )
                /*
                *     canvas.drawLine(
                    lineStartX + Util.dip2px(context, 8f),
                    lineStartY + itemHeight * i,
                    mWidth!!.toFloat() - mMargin!! - Util.dip2px(context, 5f),
                    lineStartY + itemHeight * i,
                    mPaint_grid_line!!
                )
*/
                //y轴网格线
             //   canvas.drawLine()

                canvas.drawCircle(x, y, Util.dip2px(context, 4f).toFloat(), mPaint_Fill!!)
                canvas.drawCircle(x, y, Util.dip2px(context, 4f).toFloat(), mPaint_Circle!!)
            }
        }

        SpaceRect(canvas)


        //保证向左滑动时有一屏的显示，不会全部滑出屏幕外
        canScrollSpace = mTextMargin!! *
                mData[0].size + mExtraSpace -
                (mWidth!! - 3 * mMargin!! - Util.dip2px(
                    context,
                    5f
                ) - mPaint_text!!.measureText(mMaxValue.toString()))


        for (i in 0 until mSegment) {
            //y轴数字
            canvas!!.drawText(
                ((mSegment - i) * itemValue).toString(),
                textX,
                textY + itemHeight * i,
                mPaint_text!!
            )
            //y轴单位
            canvas.drawText(
                yUnit,
                textX + mPaint_text!!.measureText(yUnit),
                textY + itemHeight * i,
                mPaint_text!!
            )
        }

    }

    private fun SpaceRect(canvas: Canvas?) {
        //用于遮挡左右的间距，不然得滑出屏幕过后才消失（待优化）[使用setXfermode]
        mSpacePaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
        spaceRect!!.left = 0f
        spaceRect!!.top = 0f
        spaceRect!!.right = lineStartX
        spaceRect!!.bottom = mHeight!!.toFloat()
        canvas!!.drawRect(spaceRect!!, mSpacePaint!!)
        spaceRect!!.left = mWidth!!.toFloat() - mMargin!!
        spaceRect!!.top = 0f
        spaceRect!!.right = mWidth!!.toFloat()
        spaceRect!!.bottom = mHeight!!.toFloat()
        canvas.drawRect(spaceRect!!, mSpacePaint!!)
        /*    bottomSpaceRect!!.left = 0f
            bottomSpaceRect!!.top = mHeight!!.toFloat() - 2 * mMargin!! - textHeight
            bottomSpaceRect!!.right = mWidth!!.toFloat()
            bottomSpaceRect!!.bottom = mHeight!!.toFloat()
            canvas.drawRect(bottomSpaceRect!!, mSpacePaint!!)*/
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
        val type = event.actionMasked

        when (type) {
            MotionEvent.ACTION_DOWN -> {
                // scroller!!.abortAnimation()//按下时终止滑动动画
                //   initVelocityTracker()//初始化VelocityTracker
                // velocityTracker!!.addMovement(event)
                lastPointX = event.rawX
            }

            MotionEvent.ACTION_MOVE -> {
                val x = event.rawX
                movingLeftThisTime = lastPointX - x

                leftMoving += movingLeftThisTime
                lastPointX = x
                //onScroll(movingLeftThisTime)
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
                leftMoving += lastMoving

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


    //释放velocityTracker
    private fun releaseVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker!!.recycle()
            velocityTracker = null
        }
    }

    //初始化velocityTracker
    private fun initVelocityTracker() {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        } else {
            velocityTracker!!.clear()
        }
    }


    private fun checkLeftMoving() {
        if (leftMoving < 0) {
            leftMoving = 0f
        }

        if (leftMoving > canScrollSpace) {
            leftMoving = canScrollSpace
        }
    }


    /**
     * 画x y轴以及x y轴上的三角形
     * */
    private fun drawXyandTriangle(canvas: Canvas?) {
        val stopY = mHeight!!.toFloat() - 2 * mMargin!! - textHeight
        val triangleX = mWidth!!.toFloat() - mMargin!!
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
            triangleX,
            stopY
        )
        path.lineTo(
            triangleX - Util.dip2px(context, 5f),
            stopY - Util.dip2px(
                context,
                3f
            )
        )

        path.lineTo(
            triangleX - Util.dip2px(context, 5f),
            stopY + Util.dip2px(
                context,
                3f
            )
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
    fun setData(mDatas: MutableList<MutableList<LineChartData>>, maxValue: Float) {

        if (mData.size > 0) {
            mData.clear()
        }

        mData.addAll(mDatas)

        mMaxValue = Math.ceil(maxValue.toDouble()).toInt()

        for (i in 0 until mData.size) {
            mPathData.add(Path())
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