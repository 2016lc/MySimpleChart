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
            DoubleBarChartData("hah",  15.36f, 99f),
            DoubleBarChartData("hah", 88.5f, 19f),
            DoubleBarChartData("hah",  18.36f, 99f),
            DoubleBarChartData("hah",  12.234f, 239f),
            DoubleBarChartData("hah", 13.36f, 149f),
            DoubleBarChartData("hah", 32f, 98f),
            DoubleBarChartData("hah",  88f, 88f),
            DoubleBarChartData("hah",  48f, 99f),
            DoubleBarChartData("hah", 59f, 99f),
            DoubleBarChartData("hah", 92.36f, 33f),
            DoubleBarChartData("hah",  2.36f, 25f),
            DoubleBarChartData("hah", 2.36f, 85f),
            DoubleBarChartData("hah",  1.36f, 89f),
            DoubleBarChartData("hah",  3.36f, 59f),
            DoubleBarChartData("hah", 4.36f, 69f),
            DoubleBarChartData("hah", 5.36f, 39f),
            DoubleBarChartData("hah", 49.36f, 29f),
            DoubleBarChartData("hah",  58.36f, 19f),
            DoubleBarChartData("hah",  94.36f, 19f),
            DoubleBarChartData("hah",  22.36f, 99f)
        )
        //barchart.setData(mList)
    }
}