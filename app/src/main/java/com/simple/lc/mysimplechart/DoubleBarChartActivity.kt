package com.simple.lc.mysimplechart

import android.app.Activity
import android.os.Bundle
import com.simple.lc.mylibrary.DoubleBarChartData
import kotlinx.android.synthetic.main.activity_doublebarchart.*

/**
 * Author:LC
 * Date:2018/12/25
 * Description:This is 双柱状图示例
 */
class DoubleBarChartActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doublebarchart)
        val mList: List<DoubleBarChartData> = listOf(
            DoubleBarChartData("hah", "hh", 15.36f, 99f),
            DoubleBarChartData("hah", "hh", 88.5f, 19f),
            DoubleBarChartData("hah", "hh", 18.36f, 99f),
            DoubleBarChartData("hah", "hh", 12.234f, 239f),
            DoubleBarChartData("hah", "hh", 13.36f, 149f),
            DoubleBarChartData("hah", "hh", 32f, 98f),
            DoubleBarChartData("hah", "hh", 88f, 88f),
            DoubleBarChartData("hah", "hh", 48f, 99f),
            DoubleBarChartData("hah", "hh", 59f, 99f),
            DoubleBarChartData("hah", "hh", 92.36f, 33f),
            DoubleBarChartData("hah", "hh", 2.36f, 25f),
            DoubleBarChartData("hah", "hh", 2.36f, 85f),
            DoubleBarChartData("hah", "hh", 1.36f, 89f),
            DoubleBarChartData("hah", "hh", 1.36f, 79f),
            DoubleBarChartData("hah", "hh", 3.36f, 59f),
            DoubleBarChartData("hah", "hh", 4.36f, 69f),
            DoubleBarChartData("hah", "hh", 5.36f, 39f),
            DoubleBarChartData("hah", "hh", 49.36f, 29f),
            DoubleBarChartData("hah", "hh", 58.36f, 19f),
            DoubleBarChartData("hah", "hh", 94.36f, 19f),
            DoubleBarChartData("hah", "hh", 22.36f, 99f)
        )
        barchart.setData(mList)
    }
}