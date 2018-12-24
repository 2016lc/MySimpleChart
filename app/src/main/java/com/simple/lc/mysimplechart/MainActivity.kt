package com.simple.lc.mysimplechart

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.simple.lc.mylibrary.BarChartData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mList: List<BarChartData> = listOf(
            BarChartData("哈哈", 12.35f),
            BarChartData("嘻嘻", 3.36f),
            BarChartData("呵呵", 190.96f),
            BarChartData("啧啧", 7f),
            BarChartData( "弟弟", 100f),
            BarChartData( "哥哥", 65f),
            BarChartData("哈哈", 12f),
            BarChartData("嘻嘻", 3f),
            BarChartData("呵呵", 190f),
            BarChartData("啧啧", 7f),
            BarChartData( "弟弟", 100f),
            BarChartData( "哥哥", 65f),
            BarChartData("哈哈", 12f),
            BarChartData("嘻嘻", 3f),
            BarChartData("呵呵", 190f),
            BarChartData("啧啧", 7f),
            BarChartData( "弟弟", 100f),
            BarChartData( "哥哥", 65f),
            BarChartData("哈哈", 12f),
            BarChartData("嘻嘻", 3f),
            BarChartData("呵呵", 190f),
            BarChartData("啧啧", 7f),
            BarChartData( "弟弟", 100f),
            BarChartData( "哥哥", 65f),
            BarChartData("哈哈", 12f),
            BarChartData("嘻嘻", 3f),
            BarChartData("呵呵", 190f),
            BarChartData("啧啧", 7f),
            BarChartData( "弟弟", 100f),
            BarChartData( "哥哥", 65f),
            BarChartData("哈哈", 12f),
            BarChartData("嘻嘻", 3f),
            BarChartData("呵呵", 190f),
            BarChartData("啧啧", 7f),
            BarChartData( "弟弟", 100f),
            BarChartData( "哥哥", 65f),
            BarChartData("哈哈", 12f),
            BarChartData("嘻嘻", 3f),
            BarChartData("呵呵", 190f),
            BarChartData("啧啧", 7f),
            BarChartData( "弟弟", 100f),
            BarChartData( "哥哥", 65f),
            BarChartData("哈哈", 12f),
            BarChartData("嘻嘻", 3f),
            BarChartData("呵呵", 190f),
            BarChartData("啧啧", 7f),
            BarChartData( "弟弟", 100f),
            BarChartData( "哥哥", 65f),
            BarChartData("哈哈", 12f),
            BarChartData("呵呵", 190f),
            BarChartData("啧啧", 7f),
            BarChartData( "弟弟", 100f),
            BarChartData( "哥哥", 65f),
            BarChartData( "妹", 201f)
        )
        barchart.setData(mList)
    }
}
