package com.simple.lc.mysimplechart

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import com.simple.lc.mylibrary.BarChartData
import kotlinx.android.synthetic.main.activity_barchart.*
import java.util.*

/**
 * Author:LC
 * Date:2018/12/25
 * Description:This is 柱状图示例
 */
class BarChartActivity : Activity() {

    private var isDouble = false
    private var isShowTopNuM = false
    private var margin = 20f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barchart)


        btn_isdouble.setOnClickListener {
            barchart.setIsDouble(!isDouble)
            isDouble = !isDouble
            barchart.notifyDataSet()
        }

        btn_color.setOnClickListener {
            barchart.setBarColor(Color.parseColor("#${getRandColor()}"))
            barchart.notifyDataSet()
        }

        btn_isshowTopNum.setOnClickListener {
            barchart.setIsShowTopNuM(isShowTopNuM)
            isShowTopNuM = !isShowTopNuM
            barchart.notifyDataSet()
        }

        btn_barMargin.setOnClickListener {
            barchart.setBarMargin(margin)
            margin += 5
            barchart.notifyDataSet()
        }

        val mList: List<BarChartData> = listOf(
            BarChartData("hah", 15.36f, 9f),
            BarChartData("hdsfadah", 88.5f, 19f),
            BarChartData("hafaadfh", 18.36f, 99f),
            BarChartData("hadh", 12.234f, 239f),
            BarChartData("haadsfh", 13.36f, 149f),
            BarChartData("adf", 32f, 98f),
            BarChartData("hah", 88f, 88f),
            BarChartData("hah", 48f, 99f),
            BarChartData("fdafa", 59f, 99f),
            BarChartData("hah", 92.36f, 33f),
            BarChartData("dfsasf", 2.36f, 25f),
            BarChartData("hah", 2.36f, 85f),
            BarChartData("hadfsaah", 1.36f, 89f),
            BarChartData("hah", 3.36f, 59f),
            BarChartData("hah", 4.36f, 69f),
            BarChartData("afd", 5.36f, 39f),
            BarChartData("hah", 49.36f, 29f),
            BarChartData("afda", 58.36f, 19f),
            BarChartData("haah", 94.36f, 19f),
            BarChartData("嘻嘻嘻嘻", 22.36f, 99f)
        )

        barchart.setData(mList).build()
    }

    /** * 获取十六进制的颜色代码.例如 "#5A6677" * 分别取R、G、B的随机值，然后加起来即可 * * @return String  */
    fun getRandColor(): String {
        var R: String
        var G: String
        var B: String
        val random = Random()
        R = Integer.toHexString(random.nextInt(256)).toUpperCase()
        G = Integer.toHexString(random.nextInt(256)).toUpperCase()
        B = Integer.toHexString(random.nextInt(256)).toUpperCase()

        R = if (R.length == 1) "0$R" else R
        G = if (G.length == 1) "0$G" else G
        B = if (B.length == 1) "0$B" else B

        return R + G + B
    }
}