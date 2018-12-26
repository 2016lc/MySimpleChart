package com.simple.lc.mysimplechart

import android.app.Activity
import android.os.Bundle
import com.simple.lc.mylibrary.BarChartData
import kotlinx.android.synthetic.main.activity_horibarchart.*

/**
 * Author:LC
 * Date:2018/12/25
 * Description:This is 横向柱状图示例
 */
class HoriBarChartActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horibarchart)
        val mList: List<BarChartData> = listOf(
            BarChartData("哈哈", 12.35f),
            BarChartData("嘻嘻", 3.36f),
            BarChartData("呵呵", 190.96f),
            BarChartData("啧啧", 7f),
            BarChartData("弟弟", 100f),
            BarChartData("哥哥", 65f),
            BarChartData("哈哈", 12f),
            BarChartData("嘻嘻", 3f),
            BarChartData("呵呵", 190f),
            BarChartData("啧啧", 7f),
            BarChartData("弟弟", 100f),
            BarChartData("哥哥", 65f),
            BarChartData("哈哈", 12f),
            BarChartData("嘻嘻", 3f),
            BarChartData("呵呵", 190f),
            BarChartData("啧啧", 7f),
            BarChartData("弟弟", 100f),
            BarChartData("哥哥", 65f),
            BarChartData("哈哈", 12f),
            BarChartData("嘻嘻", 3f),
            BarChartData("呵呵", 190f),
            BarChartData("啧啧", 7f),
            BarChartData("弟弟", 100f),
            BarChartData("哥哥", 65f),
            BarChartData("哈哈", 12f),
            BarChartData("嘻嘻", 3f),
            BarChartData("呵呵", 190f),
            BarChartData("啧啧", 7f),
            BarChartData("弟弟", 100f),
            BarChartData("哥哥", 65f),
            BarChartData("哈哈", 12f),
            BarChartData("嘻嘻", 3f),
            BarChartData("呵呵", 190f),
            BarChartData("啧啧", 7f),
            BarChartData("弟弟", 100f),
            BarChartData("哥哥", 65f),
            BarChartData("哈哈", 12f),
            BarChartData("嘻嘻", 3f),
            BarChartData("呵呵", 190f),
            BarChartData("啧啧是", 7f),
            BarChartData("弟弟弟弟", 123f),
            BarChartData("哥哥弟弟", 65f),
            BarChartData("哈哈", 12f),
            BarChartData("嘻嘻弟弟弟弟", 3f),
            BarChartData("呵呵弟弟", 190f),
            BarChartData("啧啧", 7f),
            BarChartData("弟弟弟弟", 164f),
            BarChartData("哥哥", 65f),
            BarChartData("哈哈", 12f),
            BarChartData("呵呵", 190f),
            BarChartData("啧啧", 7f),
            BarChartData("弟弟", 100f),
            BarChartData("哥哥", 65f),
            BarChartData("妹", 205f)
        )
        barchart.setData(mList)
    }
}