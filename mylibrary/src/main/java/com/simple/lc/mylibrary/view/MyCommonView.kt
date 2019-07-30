package com.simple.lc.mylibrary.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.Scroller
import com.simple.lc.mylibrary.BarChartData
import com.simple.lc.mylibrary.ChartConstant
import com.simple.lc.mylibrary.R
import com.simple.lc.mylibrary.Util
import kotlin.math.*


/**
 * Author:LC
 * Date:2019/5/7
 * Description:This is MyCommonView
 */
abstract class MyCommonView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    /** x y轴 */
    private var mPaint_xy: Paint? = null
    /** 网格线 */
    open var mPaint_grid_line: Paint? = null
    /** 字体画笔 */
    open var mPaint_text: TextPaint? = null
    /** xy、网格线宽度 */
    private var mLineWidth: Float = 1f
    /** xy、网格线颜色 */
    private var mLineColor: Int = 0
    /** 字体大小 */
    private var mTextSize: Float = 12f
    /** 字体颜色 */
    private var mTextColor: Int = 0
    /** 控件宽/高 */
    open var mWidth: Int = 0
    open var mHeight: Int = 0
    /** 间距，用于绘制的图形距离边框有一定间距，绘制出来不会显得那么突兀 */
    open var mMargin: Int = 8
    /** 字体高度  */
    open var textHeight: Float = 0f
    /** x轴的起始点 */
    open var lineStartX: Float = 0f
    /** y轴的开始绘画点 */
    open var lineStartY: Float = 0f
    /** 集合最大值，用于y轴取值分段 */
    open var mMaxValue: Int = 0
    /** 单位 */
    private var yUnit: String = ""
    /** x轴数据间距 */
    open var mDataMargin: Float = 10f
    /** 有数据的画布宽 */
    private var mCanvasWidth: Float = 0f
    /** 数据个数(如果有多条数据，则取最大数据个数) */
    open var maxNumSize = 0
    /** 画三角形的path */
    private val path = Path()
    /** 解释文本框的path */
    val explainPath = Path()
    /** 解释文本框的RectF */
    val explainRect = RectF()
    /** 解释文本框的小三角 */
    var triangleLength = 10f
    /** 初始化y轴分为几段 */
    open var mSegment: Int = ChartConstant.COMMON_DEFAULT_SEGMENT
    /** 是否显示y轴单位 */
    private var isShowUnit = false
    /** 声明VelocityTracker Scroller用户滑动 */
    private var velocityTracker: VelocityTracker? = null
    private var mScroller: Scroller? = null
    /** fling最大速度 */
    private var maxVelocity: Int = 0
    /** fling最小速度 */
    private var minVelocity: Int = 0
    /** 滑动线程 */
    open var mFling: FlingRunnable? = null
    /** 按下时x轴的点 */
    private var lastX = 0f
    /** 数据保留的位数 */
    open var mDigit: Int = ChartConstant.COMMON_DEFAULT_DIGIT
    /** 声明属性动画 */
    open var mAnimator: ValueAnimator? = null
    /** 动画进度 */
    open var mPercent: Float = 0f
    /** 是否需要动画 */
    open var isAnim: Boolean = ChartConstant.COMMON_DEFAULT_ISANIM
    /** 动画时间 */
    open var animTime: Int = ChartConstant.COMMON_DEFAULT_ANIMTIME
    /** 是否显示网格线 */
    open var isShowGridLine: Boolean = ChartConstant.COMMON_DEFAULT_ISSHOWGRIDLINE
    /** y轴的每一个数字 */
    open var itemValue = 0
    /** 除去间距的高度 */
    open var realHeight = 0f
    /** y轴每一段的高度 */
    private var itemHeight = 0f
    /** 是否在滑动 */
    private var isScroll = false
    /** 若刚好一屏，x轴分为几段 */
    private var xSegment = ChartConstant.COMMON_DEFAULT_XSEGMENT
    /** 点击的位置 */
    var selectedIndex = 1
    /** 上次点击的位置 */
    private var lastSelectedIndex = -1
    /** 是否可以点击 */
    var isClick = true
    /** 是否显示解释文本框 */
    var isShowExplainWindow = false
    /** 解释文本框宽度 */
    var explainWindowWidth = 0f
    /** 解释文本框背景颜色 */
    var explainWindowBgColor = 0
    /** 解释文本框自己大小 */
    var explainWindowTextSize = 12f

    init {
        mLineWidth = Util.dip2px(context!!, 0.7f).toFloat()
        mMargin = Util.dip2px(context, 8f)
        mTextSize = Util.dip2px(context, 10f).toFloat()
        triangleLength = Util.dip2px(context, 7f).toFloat()
        // explainWindowWidth = Util.dp2px(context,15f).toFloat()

        mAnimator = ValueAnimator()

        mScroller = Scroller(context)
        mFling = FlingRunnable()
        minVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity
        maxVelocity = ViewConfiguration.get(context).scaledMaximumFlingVelocity

        initAttrs(attrs, context)//初始化属性
        //prePare()
        initPaint()//初始化画笔
    }

    private fun initAttrs(attrs: AttributeSet?, context: Context?) {
        val typedArray = context!!.obtainStyledAttributes(
            attrs,
            R.styleable.MyCommonView
        )

        isShowUnit = typedArray.getBoolean(
            R.styleable.MyCommonView_isShowUnit,
            ChartConstant.COMMON_DEFAULT_ISHOWUNIT
        )

        yUnit = if (typedArray.getString(R.styleable.MyCommonView_yUnit) == null) {
            ""
        } else {
            typedArray.getString(R.styleable.MyCommonView_yUnit)!!
        }

        mLineColor =
            typedArray.getColor(R.styleable.MyCommonView_xyLineColor, ContextCompat.getColor(context, R.color.xy_color))
        mTextColor =
            typedArray.getColor(R.styleable.MyCommonView_textColor, ContextCompat.getColor(context, R.color.text_color))

        mTextSize = typedArray.getDimension(R.styleable.MyCommonView_textSize, Util.dip2px(context, 10f).toFloat())

        isAnim = typedArray.getBoolean(
            R.styleable.MyCommonView_isAnim,
            ChartConstant.COMMON_DEFAULT_ISANIM
        )
        isShowGridLine = typedArray.getBoolean(
            R.styleable.MyCommonView_isShowGridLine,
            ChartConstant.COMMON_DEFAULT_ISSHOWGRIDLINE
        )
        animTime = typedArray.getInt(R.styleable.MyCommonView_animTime, ChartConstant.COMMON_DEFAULT_ANIMTIME)

        xSegment = typedArray.getInt(R.styleable.MyCommonView_xSegment, ChartConstant.COMMON_DEFAULT_XSEGMENT)

        explainWindowWidth =
            typedArray.getDimension(R.styleable.MyCommonView_explainWindowWidth, Util.dp2px(context, 120f).toFloat())

        explainWindowBgColor = typedArray.getColor(
            R.styleable.MyCommonView_explainWindowBgColor,
            ContextCompat.getColor(context, R.color.xy_color)
        )

        explainWindowTextSize =
            typedArray.getDimension(R.styleable.MyCommonView_explainWindowTextSize, Util.dip2px(context, 12f).toFloat())
        //DeclarativeAttribute(typedArray)

        mDigit = typedArray.getInt(R.styleable.MyCommonView_mDigit, ChartConstant.COMMON_DEFAULT_DIGIT)

        isClick = typedArray.getBoolean(R.styleable.MyCommonView_isClick,true)

        typedArray.recycle()
    }

    private fun initPaint() {
        mPaint_xy = Paint()
        mPaint_xy!!.isDither = true
        mPaint_xy!!.strokeWidth = mLineWidth
        mPaint_xy!!.color = mLineColor
        mPaint_xy!!.style = Paint.Style.FILL

        mPaint_grid_line = Paint()
        mPaint_grid_line!!.isDither = true
        mPaint_grid_line!!.strokeWidth = mLineWidth
        mPaint_grid_line!!.style = Paint.Style.FILL

        mPaint_text = TextPaint()
        // mPaint_text!!.color = resources.getColor(R.color.text_color)
        mPaint_text!!.isAntiAlias = true

        // mPaint_text!!.textAlign = Paint.Align.RIGHT

    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w - paddingLeft - paddingRight
        mHeight = h - paddingTop - paddingBottom
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mPaint_text!!.textAlign = Paint.Align.RIGHT
        mPaint_text!!.color = mTextColor
        mPaint_grid_line!!.color = mLineColor
        mPaint_text!!.textSize = mTextSize

        val realWidth =
            mWidth - mPaint_text!!.measureText(mMaxValue.toString()) - 3 * mMargin - Util.dip2px(
                context,
                5f
            ) - mPaint_text!!.measureText(yUnit)
        //x轴数据间距
        mDataMargin = realWidth / xSegment
        //x轴开始的坐标
        lineStartX = mPaint_text!!.measureText(mMaxValue.toString()) + mPaint_text!!.measureText(yUnit) + 2 * mMargin
        //y轴开始的坐标
        lineStartY = (mMargin + Util.dip2px(context, 15f)).toFloat()
        //需要绘制的所有长度
        mCanvasWidth = mDataMargin * maxNumSize + lineStartX + mMargin + Util.dip2px(context, 5f)

        if (mCanvasWidth < realWidth) {
            mCanvasWidth = realWidth
            mDataMargin = realWidth / maxNumSize
        }
        //字高度
        textHeight = mPaint_text!!.descent() - mPaint_text!!.ascent()

        canvas!!.save()

        drawXyandTriangle(canvas)//画xy轴以及三角形

        drawYText(canvas)//画y轴上面的数据


        //画布剪切
        canvas.clipRect(
            lineStartX + mLineWidth + scrollX,
            0f,
            mWidth - mMargin.toFloat() - Util.dip2px(context, 4.5f) + scrollX,
            mHeight.toFloat()
        )

        drawGridLine(canvas)//画y轴网格线

        drawContents(canvas)//画内容

        drawExplainWindow(canvas)//画点击说明文

        canvas.restore()

    }

    /**
     * 画点击说明文
     * */
    open fun drawExplainWindow(canvas: Canvas) {}

    /*open fun getBarData(): MutableList<BarChartData>? {
        return null
    }

    open fun isDouble(): Boolean {
        return false
    }

    open fun getColor(): Int {
        return 0
    }*/


    /**
     * 画y轴网格线
     * */
    private fun drawGridLine(canvas: Canvas) {
        if (isShowGridLine) {
            //y轴网格
            for (z in 0 until mSegment) {
                canvas.drawLine(
                    lineStartX ,
                    lineStartY + itemHeight * z,
                    lineStartX + mCanvasWidth,
                    lineStartY + itemHeight * z,
                    mPaint_grid_line!!
                )
            }
            //x轴网格线
            for (i in 0..maxNumSize) {
                canvas.drawLine(
                    lineStartX + mDataMargin * (i + 1),
                    mHeight - 2 * mMargin - textHeight,
                    lineStartX + mDataMargin * (i + 1),
                    lineStartY,
                    mPaint_grid_line!!
                )
            }
        }
    }


    /**
     * 画内容
     * */
    open fun drawContents(canvas: Canvas) {}

    /**
     * 准备工作
     * */
    //open fun prePare(){}

    /**
     *  声明额外属性
     * */
    //open fun DeclarativeAttribute(typedArray: TypedArray){}

    /**
     * 画y轴上面的数据
     */
    private fun drawYText(canvas: Canvas?) {
        //如果最大值小于默认值，则y轴最大值为默认值
        if (mMaxValue < mSegment) {
            mMaxValue = mSegment
        }

        //y轴的每一个数字
        itemValue = ceil(mMaxValue / mSegment.toDouble()).toInt()

        itemHeight = (mHeight - 3 * mMargin - Util.dip2px(
            context,
            15f
        ) - textHeight) / mSegment //（ 总高度 - 上面一个margin - 下面两个margin - 距离上面的高度 - x轴字体的高度 ）/分成的段数


        realHeight = mHeight - 3 * mMargin - Util.dip2px(
            context,
            15f
        ) - textHeight

        val itemStartY = mMargin + Util.dip2px(
            context,
            15f
        )

        val textX = mMargin.toFloat() + mPaint_text!!.measureText(mMaxValue.toString())  // y轴字的x坐标
        val textY = itemStartY + textHeight / 4 //y轴字的y坐标

        for (i in 0 until mSegment) {
            //y轴数字
            canvas!!.drawText(
                ((mSegment - i) * itemValue).toString(),
                textX + scrollX,
                textY + itemHeight * i,
                mPaint_text!!
            )
            //y轴单位
            if (isShowUnit) {
                canvas.drawText(
                    yUnit,
                    textX + mPaint_text!!.measureText(yUnit) + scrollX,
                    textY + itemHeight * i,
                    mPaint_text!!
                )
            }
        }

    }

    /**
     * 画x y轴以及x y轴上的三角形
     * */
    private fun drawXyandTriangle(canvas: Canvas?) {
        val stopY = mHeight.toFloat() - 2 * mMargin - textHeight
        val triangleX = mWidth.toFloat() - mMargin
        val triangleXx = triangleX - Util.dip2px(context, 5f) + scrollX
        val triangleYy = mMargin + Util.dip2px(context, 5f).toFloat()
        //x轴
        canvas!!.drawLine(
            lineStartX + scrollX,
            stopY,
            triangleX + scrollX,
            stopY,
            mPaint_xy!!
        )
        //y轴
        canvas.drawLine(
            lineStartX + scrollX,
            mMargin.toFloat(),
            lineStartX + scrollX,
            stopY,
            mPaint_xy!!
        )
        path.reset()
        //画x轴三角形
        path.close()
        path.moveTo(
            triangleX + scrollX,
            stopY
        )
        path.lineTo(
            triangleXx,
            stopY - Util.dip2px(
                context,
                3f
            )
        )

        path.lineTo(
            triangleXx,
            stopY + Util.dip2px(
                context,
                3f
            )
        )
        canvas.drawPath(path, mPaint_xy!!)

        //画y轴三角形
        path.moveTo(lineStartX + scrollX, mMargin.toFloat())
        path.lineTo(
            lineStartX + Util.dip2px(context, 3f) + scrollX,
            triangleYy
        )
        path.lineTo(
            lineStartX - Util.dip2px(context, 3f) + scrollX,
            triangleYy
        )
        canvas.drawPath(path, mPaint_xy!!)
    }

    /**
     * 手势控制
     * */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        // 当数据的长度不足以滑动时，不做滑动处理
        if (mCanvasWidth < mWidth) {
            return true
        }
        initVelocityTracker()//初始化VelocityTracker

        velocityTracker!!.addMovement(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                mFling!!.stop()
                //如果是坐标两边则不会左右滑动
                if (lastX <= lineStartX || lastX >= mWidth - mMargin - Util.dip2px(context, 5f)) {
                    return false
                }
            }
            MotionEvent.ACTION_MOVE -> {
                isScroll = true
                // 滑动的距离
                val scrollLengthX = event.x - lastX
                // getScrollX() 小于0，说明画布右移了
                // getScrollX() 大于0，说明画布左移了
                val endX = scrollX - scrollLengthX

                if (scrollLengthX > 0) {    // 画布往右移动 -->

                    // 注意：这里的等号不能去除，否则会有闪动
                    if (endX <= 0) {
                        scrollTo(0, 0)
                    } else {
                        scrollBy((-scrollLengthX).toInt(), 0)
                    }

                } else if (scrollLengthX < 0) {                    // 画布往左移动  <--
                    if (endX >= mCanvasWidth - mWidth) {     // 需要考虑是否右越界
                        scrollTo((mCanvasWidth - mWidth).toInt(), 0)
                    } else {
                        scrollBy((-scrollLengthX).toInt(), 0)
                    }

                }
                lastX = event.x
            }
            MotionEvent.ACTION_UP -> {
                // 计算当前速度， 1000表示每秒像素数等
                velocityTracker!!.computeCurrentVelocity(1000, maxVelocity.toFloat())
                // 获取横向速度
                val velocityX = velocityTracker!!.xVelocity.toInt()
                Log.i(
                    "velocityX",
                    "${velocityX}!!!!!!!!!!!!!!!!!!!$minVelocity~~~~~~~~~~~~~~~~~~~~~~~$maxVelocity"
                )
                // 速度要大于最小的速度值，才开始滑动
                if (abs(velocityX) > minVelocity) {
                    val initX = scrollX

                    val maxX = (mCanvasWidth - mWidth).toInt()
                    if (maxX > 0) {
                        mFling!!.start(initX, velocityX, initX, maxX)
                    }
                }
                releaseVelocityTracker()
                //点击显示详细说明
                if (!isScroll || abs(velocityX) < minVelocity) {

                    selectedIndex = ((lastX - lineStartX + scrollX) / mDataMargin).roundToInt()
                    if (selectedIndex == 0) {
                        selectedIndex = 1
                    }

                    if (selectedIndex == maxNumSize) {
                        selectedIndex -= 1
                    }


                    isShowExplainWindow = selectedIndex != lastSelectedIndex

                    lastSelectedIndex = if (isClick && isShowExplainWindow) {
                        selectedIndex
                    } else {
                        -1
                    }

                    invalidate()
                }
                isScroll = false
            }
        }
        return true
    }

    /**
     * 滚动线程
     */
    inner class FlingRunnable : Runnable {

        private var mInitX: Int = 0
        private var mMinX: Int = 0
        private var mMaxX: Int = 0
        private var mVelocityX: Int = 0

        internal fun start(
            initX: Int,
            velocityX: Int,
            minX: Int,
            maxX: Int
        ) {
            this.mInitX = initX
            this.mVelocityX = velocityX
            this.mMinX = minX
            this.mMaxX = maxX

            // 先停止上一次的滚动
            if (!mScroller!!.isFinished) {
                mScroller!!.abortAnimation()
            }

            // 开始 fling
            mScroller!!.fling(
                initX, 0, velocityX,
                0, 0, maxX, 0, 0
            )
            post(this)
        }

        override fun run() {

            // 如果已经结束，就不再进行
            if (!mScroller!!.computeScrollOffset()) {
                return
            }

            // 计算偏移量
            val currX = mScroller!!.currX
            var diffX = mInitX - currX

            /* Log.i(
                 TAG, "run: [currX: " + currX + "]\n"
                         + "[diffX: " + diffX + "]\n"
                         + "[initX: " + mInitX + "]\n"
                         + "[minX: " + mMinX + "]\n"
                         + "[maxX: " + mMaxX + "]\n"
                         + "[velocityX: " + mVelocityX + "]\n"
             )*/

            // 用于记录是否超出边界，如果已经超出边界，则不再进行回调，即使滚动还没有完成
            var isEnd = false

            if (diffX != 0) {

                // 超出右边界，进行修正
                if (scrollX + diffX >= mCanvasWidth - mWidth) {
                    diffX = (mCanvasWidth - mWidth - scrollX.toFloat()).toInt()
                    isEnd = true
                }

                // 超出左边界，进行修正
                if (scrollX <= 0) {
                    diffX = -scrollX
                    isEnd = true
                }

                if (!mScroller!!.isFinished) {
                    scrollBy(diffX, 0)
                }
                mInitX = currX
            }

            if (!isEnd) {
                post(this)
            }
        }

        /**
         * 进行停止
         */
        internal fun stop() {
            if (!mScroller!!.isFinished) {
                mScroller!!.abortAnimation()
            }
        }
    }


    /**
     * 释放velocityTracker
     */
    private fun releaseVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker!!.recycle()
            velocityTracker = null
        }
    }

    /**
     * 初始化velocityTracker
     */
    private fun initVelocityTracker() {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        }
    }


    /**
     * 动画
     * */
    open fun startAnim(start: Float, end: Float, animTime: Int) {
        mAnimator = ValueAnimator.ofFloat(start, end)
        mAnimator!!.duration = animTime.toLong()
        mAnimator!!.addUpdateListener {

            mPercent = it.animatedValue as Float

            postInvalidate()
        }
        mAnimator!!.start()
    }


    /**
     * 是否需要动画
     * */
    fun setIsAnim(isAnim: Boolean) {
        this.isAnim = isAnim
        //invalidate()
    }

    fun setYunit(yunit: String) {
        yUnit = yunit
    }


    /**
     * 刷新
     */
    fun notifyDataSet() {
        invalidate()
    }


    fun setexplainWindowWidth(eWidth: Float) {
        explainWindowWidth = eWidth
    }

    fun setmDigit(digit: Int) {
        mDigit = digit
    }

}