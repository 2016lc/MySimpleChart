package com.simple.lc.mysimplechart

import android.app.Activity
import android.os.Bundle
import com.simple.lc.mylibrary.BarAndLineChartData
import kotlinx.android.synthetic.main.activity_barchart.*

/**
 * Author:LC
 * Date:2018/12/25
 * Description:This is 柱状图示例
 */
class BarChartActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barchart)

        val mList: List<BarAndLineChartData> = listOf(
            BarAndLineChartData("哈哈", 12.35f),
            BarAndLineChartData("嘻嘻", 3.36f),
            BarAndLineChartData("呵呵", 190.96f),
            BarAndLineChartData("啧啧", 7f),
            BarAndLineChartData("弟弟", 100f),
            BarAndLineChartData("哥哥", 65f),
            BarAndLineChartData("哈哈", 12f),
            BarAndLineChartData("嘻嘻", 3f),
            BarAndLineChartData("呵呵", 190f),
            BarAndLineChartData("啧啧", 7f),
            BarAndLineChartData("弟弟", 100f),
            BarAndLineChartData("哥哥", 65f),
            BarAndLineChartData("哈哈", 12f),
            BarAndLineChartData("嘻嘻", 3f),
            BarAndLineChartData("呵呵", 190f),
            BarAndLineChartData("啧啧", 7f),
            BarAndLineChartData("弟弟", 100f),
            BarAndLineChartData("哥哥", 65f),
            BarAndLineChartData("哈哈", 12f),
            BarAndLineChartData("嘻嘻", 3f),
            BarAndLineChartData("呵呵", 190f),
            BarAndLineChartData("啧啧", 7f),
            BarAndLineChartData("弟弟", 100f),
            BarAndLineChartData("哥哥", 65f),
            BarAndLineChartData("哈哈", 12f),
            BarAndLineChartData("嘻嘻", 3f),
            BarAndLineChartData("呵呵", 190f),
            BarAndLineChartData("啧啧", 7f),
            BarAndLineChartData("弟弟", 100f),
            BarAndLineChartData("哥哥", 65f),
            BarAndLineChartData("哈哈", 12f),
            BarAndLineChartData("嘻嘻", 3f),
            BarAndLineChartData("呵呵", 190f),
            BarAndLineChartData("啧啧", 7f),
            BarAndLineChartData("弟弟", 100f),
            BarAndLineChartData("哥哥", 65f),
            BarAndLineChartData("哈哈", 12f),
            BarAndLineChartData("嘻嘻", 3f),
            BarAndLineChartData("呵呵", 190f),
            BarAndLineChartData("啧啧", 7f),
            BarAndLineChartData("弟弟", 100f),
            BarAndLineChartData("哥哥", 65f),
            BarAndLineChartData("哈哈", 12f),
            BarAndLineChartData("嘻嘻", 3f),
            BarAndLineChartData("呵呵", 190f),
            BarAndLineChartData("啧啧", 7f),
            BarAndLineChartData("弟弟", 100f),
            BarAndLineChartData("哥哥", 65f),
            BarAndLineChartData("哈哈", 12f),
            BarAndLineChartData("呵呵", 190f),
            BarAndLineChartData("啧啧", 7f),
            BarAndLineChartData("弟弟", 100f),
            BarAndLineChartData("哥哥", 65f),
            BarAndLineChartData("妹", 201f)
        )
        barchart.setData(mList)
    }
}