package com.simple.lc.mysimplechart

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import com.simple.lc.mylibrary.BarChartData
import com.simple.lc.mylibrary.LineChartData
import kotlinx.android.synthetic.main.activity_linechart.*

/**
 * Author:LC
 * Date:2018/12/25
 * Description:This is 柱状图示例
 */
class LineChartActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linechart)

        val colorOne = "#54FF9F"
        val colorTwo = "#FF6347"
        val colorThree = "#98FB98"
        val colorFour = "#8B6914"
        val colorFive = "#7FFFD4"

        val mList: MutableList<LineChartData> = mutableListOf(
            LineChartData("哈哈", 3.36f, Color.parseColor(colorOne)),
            LineChartData("嘻嘻", 3.36f, Color.parseColor(colorOne)),
            LineChartData("呵呵", 3.36f, Color.parseColor(colorOne)),
            LineChartData("啧啧", 3.36f, Color.parseColor(colorOne)),
            LineChartData("弟弟", 100f, Color.parseColor(colorOne)),
            LineChartData("哥哥", 65f, Color.parseColor(colorOne)),
            LineChartData("哈哈", 12f, Color.parseColor(colorOne)),
            LineChartData("嘻嘻", 3f, Color.parseColor(colorOne)),
            LineChartData("呵呵", 190f, Color.parseColor(colorOne)),
            LineChartData("啧啧", 7f, Color.parseColor(colorOne)),
            /*LineChartData("弟弟", 100f, Color.parseColor(colorOne)),
            LineChartData("哥哥", 65f, Color.parseColor(colorOne)),
            LineChartData("哈哈", 12f, Color.parseColor(colorOne)),
            LineChartData("嘻嘻", 3f, Color.parseColor(colorOne)),
            LineChartData("呵呵", 190f, Color.parseColor(colorOne)),
            LineChartData("啧啧", 7f, Color.parseColor(colorOne)),
            LineChartData("弟弟", 100f, Color.parseColor(colorOne)),
            LineChartData("哥哥", 65f, Color.parseColor(colorOne)),
            LineChartData("哈哈", 12f, Color.parseColor(colorOne)),
            LineChartData("嘻嘻", 3f, Color.parseColor(colorOne)),
            LineChartData("呵呵", 190f, Color.parseColor(colorOne)),
            LineChartData("啧啧", 7f, Color.parseColor(colorOne)),
            LineChartData("弟弟", 100f, Color.parseColor(colorOne)),
            LineChartData("哥哥", 65f, Color.parseColor(colorOne)),
            LineChartData("哈哈", 12f, Color.parseColor(colorOne)),
            LineChartData("嘻嘻", 3f, Color.parseColor(colorOne)),
            LineChartData("呵呵", 190f, Color.parseColor(colorOne)),
            LineChartData("啧啧", 7f, Color.parseColor(colorOne)),
            LineChartData("弟弟", 100f, Color.parseColor(colorOne)),
            LineChartData("哥哥", 65f, Color.parseColor(colorOne)),
            LineChartData("哈哈", 12f, Color.parseColor(colorOne)),
            LineChartData("嘻嘻", 3f, Color.parseColor(colorOne)),
            LineChartData("呵呵", 190f, Color.parseColor(colorOne)),
            LineChartData("啧啧", 7f, Color.parseColor(colorOne)),
            LineChartData("弟弟", 100f, Color.parseColor(colorOne)),
            LineChartData("哥哥", 65f, Color.parseColor(colorOne)),
            LineChartData("哈哈", 12f, Color.parseColor(colorOne)),
            LineChartData("嘻嘻", 3f, Color.parseColor(colorOne)),
            LineChartData("呵呵", 190f, Color.parseColor(colorOne)),
            LineChartData("啧啧", 7f, Color.parseColor(colorOne)),
            LineChartData("弟弟", 100f, Color.parseColor(colorOne)),
            LineChartData("哥哥", 65f, Color.parseColor(colorOne)),
            LineChartData("哈哈", 12f, Color.parseColor(colorOne)),
            LineChartData("嘻嘻", 3f, Color.parseColor(colorOne)),
            LineChartData("呵呵", 190f, Color.parseColor(colorOne)),
            LineChartData("啧啧", 7f, Color.parseColor(colorOne)),
            LineChartData("弟弟", 100f, Color.parseColor(colorOne)),
            LineChartData("哥哥", 65f, Color.parseColor(colorOne)),
            LineChartData("哈哈", 12f, Color.parseColor(colorOne)),
            LineChartData("呵呵", 190f, Color.parseColor(colorOne)),
            LineChartData("啧啧", 7f, Color.parseColor(colorOne)),
            LineChartData("弟弟", 100f, Color.parseColor(colorOne)),
            LineChartData("哥哥", 65f, Color.parseColor(colorOne)),*/
            LineChartData("妹", 201f, Color.parseColor(colorOne))
        )


        val mListTwo: MutableList<LineChartData> = mutableListOf(
            LineChartData("哈哈", 19.35f, Color.parseColor(colorTwo)),
            LineChartData("嘻嘻", 13.36f, Color.parseColor(colorTwo)),
            LineChartData("呵呵", 1.96f, Color.parseColor(colorTwo)),
            LineChartData("啧啧", 0f, Color.parseColor(colorTwo)),
            LineChartData("弟弟", 10f, Color.parseColor(colorTwo)),
            LineChartData("哥哥", 165f, Color.parseColor(colorTwo)),
            LineChartData("哈哈", 120f, Color.parseColor(colorTwo)),
            LineChartData("嘻嘻", 30f, Color.parseColor(colorTwo)),
            LineChartData("呵呵", 19f, Color.parseColor(colorTwo)),
            LineChartData("啧啧", 70f, Color.parseColor(colorTwo)),
            /* LineChartData("弟弟", 10f, Color.parseColor(colorTwo)),
             LineChartData("哥哥", 165f, Color.parseColor(colorTwo)),
             LineChartData("哈哈", 120f, Color.parseColor(colorTwo)),
             LineChartData("嘻嘻", 30f, Color.parseColor(colorTwo)),
             LineChartData("呵呵", 19f, Color.parseColor(colorTwo)),
             LineChartData("啧啧", 70f, Color.parseColor(colorTwo)),
             LineChartData("弟弟", 10f, Color.parseColor(colorTwo)),
             LineChartData("哥哥", 6f, Color.parseColor(colorTwo)),
             LineChartData("哈哈", 120f, Color.parseColor(colorTwo)),
             LineChartData("嘻嘻", 13f, Color.parseColor(colorTwo)),
             LineChartData("呵呵", 19f, Color.parseColor(colorTwo)),
             LineChartData("啧啧", 70f, Color.parseColor(colorTwo)),
             LineChartData("弟弟", 108f, Color.parseColor(colorTwo)),
             LineChartData("哥哥", 6f, Color.parseColor(colorTwo)),
             LineChartData("哈哈", 126f, Color.parseColor(colorTwo)),
             LineChartData("嘻嘻", 36f, Color.parseColor(colorTwo)),
             LineChartData("呵呵", 197f, Color.parseColor(colorTwo)),
             LineChartData("啧啧", 78f, Color.parseColor(colorTwo)),
             LineChartData("弟弟", 1f, Color.parseColor(colorTwo)),
             LineChartData("哥哥", 65f, Color.parseColor(colorTwo)),
             LineChartData("哈哈", 12f, Color.parseColor(colorTwo)),
             LineChartData("嘻嘻", 30f, Color.parseColor(colorTwo)),
             LineChartData("呵呵", 190f, Color.parseColor(colorTwo)),
             LineChartData("啧啧", 7f, Color.parseColor(colorTwo)),
             LineChartData("弟弟", 100f, Color.parseColor(colorTwo)),
             LineChartData("哥哥", 65f, Color.parseColor(colorTwo)),
             LineChartData("哈哈", 12f, Color.parseColor(colorTwo)),
             LineChartData("嘻嘻", 3f, Color.parseColor(colorTwo)),
             LineChartData("呵呵", 190f, Color.parseColor(colorTwo)),
             LineChartData("啧啧", 75f, Color.parseColor(colorTwo)),
             LineChartData("弟弟", 100f, Color.parseColor(colorTwo)),
             LineChartData("哥哥", 65f, Color.parseColor(colorTwo)),
             LineChartData("哈哈", 18f, Color.parseColor(colorTwo)),
             LineChartData("嘻嘻", 3f, Color.parseColor(colorTwo)),
             LineChartData("呵呵", 190f, Color.parseColor(colorTwo)),
             LineChartData("啧啧", 70f, Color.parseColor(colorTwo)),
             LineChartData("弟弟", 10f, Color.parseColor(colorTwo)),
             LineChartData("哥哥", 68f, Color.parseColor(colorTwo)),
             LineChartData("哈哈", 17f, Color.parseColor(colorTwo)),
             LineChartData("呵呵", 197f, Color.parseColor(colorTwo)),
             LineChartData("啧啧", 8f, Color.parseColor(colorTwo)),
             LineChartData("弟弟", 108f, Color.parseColor(colorTwo)),
             LineChartData("哥哥", 65f, Color.parseColor(colorTwo)),*/
            LineChartData("妹", 205f, Color.parseColor(colorTwo))
        )



        val mListThree: MutableList<LineChartData> = mutableListOf(
            LineChartData("哈哈", 190.35f, Color.parseColor(colorThree)),
            LineChartData("嘻嘻", 130.36f, Color.parseColor(colorThree)),
            LineChartData("呵呵", 10.96f, Color.parseColor(colorThree)),
            LineChartData("啧啧", 0f, Color.parseColor(colorThree)),
            LineChartData("弟弟", 100f, Color.parseColor(colorThree)),
            LineChartData("哥哥", 105f, Color.parseColor(colorThree)),
            LineChartData("哈哈", 12f, Color.parseColor(colorThree)),
            LineChartData("嘻嘻", 130f, Color.parseColor(colorThree)),
            LineChartData("呵呵", 119f, Color.parseColor(colorThree)),
            LineChartData("啧啧", 170f, Color.parseColor(colorThree)),
            /* LineChartData("弟弟", 110f, Color.parseColor(colorThree)),
             LineChartData("哥哥", 165f, Color.parseColor(colorThree)),
             LineChartData("哈哈", 110f, Color.parseColor(colorThree)),
             LineChartData("嘻嘻", 130f, Color.parseColor(colorThree)),
             LineChartData("呵呵", 191f, Color.parseColor(colorThree)),
             LineChartData("啧啧", 170f, Color.parseColor(colorThree)),
             LineChartData("弟弟", 10f, Color.parseColor(colorThree)),
             LineChartData("哥哥", 16f, Color.parseColor(colorThree)),
             LineChartData("哈哈", 20f, Color.parseColor(colorThree)),
             LineChartData("嘻嘻", 3f, Color.parseColor(colorThree)),
             LineChartData("呵呵", 9f, Color.parseColor(colorThree)),
             LineChartData("啧啧", 0f, Color.parseColor(colorThree)),
             LineChartData("弟弟", 8f, Color.parseColor(colorThree)),
             LineChartData("哥哥", 60f, Color.parseColor(colorThree)),
             LineChartData("哈哈", 16f, Color.parseColor(colorThree)),
             LineChartData("嘻嘻", 36f, Color.parseColor(colorThree)),
             LineChartData("呵呵", 17f, Color.parseColor(colorThree)),
             LineChartData("啧啧", 7f, Color.parseColor(colorThree)),
             LineChartData("弟弟", 19f, Color.parseColor(colorThree)),
             LineChartData("哥哥", 6f, Color.parseColor(colorThree)),
             LineChartData("哈哈", 120f, Color.parseColor(colorThree)),
             LineChartData("嘻嘻", 39f, Color.parseColor(colorThree)),
             LineChartData("呵呵", 190f, Color.parseColor(colorThree)),
             LineChartData("啧啧", 70f, Color.parseColor(colorThree)),
             LineChartData("弟弟", 10f, Color.parseColor(colorThree)),
             LineChartData("哥哥", 65f, Color.parseColor(colorThree)),
             LineChartData("哈哈", 18f, Color.parseColor(colorThree)),
             LineChartData("嘻嘻", 30f, Color.parseColor(colorThree)),
             LineChartData("呵呵", 90f, Color.parseColor(colorThree)),
             LineChartData("啧啧", 7f, Color.parseColor(colorThree)),
             LineChartData("弟弟", 10f, Color.parseColor(colorThree)),
             LineChartData("哥哥", 6f, Color.parseColor(colorThree)),
             LineChartData("哈哈", 1f, Color.parseColor(colorThree)),
             LineChartData("嘻嘻", 3f, Color.parseColor(colorThree)),
             LineChartData("呵呵", 190f, Color.parseColor(colorThree)),
             LineChartData("啧啧", 79f, Color.parseColor(colorThree)),
             LineChartData("弟弟", 18f, Color.parseColor(colorThree)),
             LineChartData("哥哥", 69f, Color.parseColor(colorThree)),
             LineChartData("哈哈", 15f, Color.parseColor(colorThree)),
             LineChartData("呵呵", 198f, Color.parseColor(colorThree)),
             LineChartData("啧啧", 6f, Color.parseColor(colorThree)),
             LineChartData("弟弟", 18f, Color.parseColor(colorThree)),
             LineChartData("哥哥", 6f, Color.parseColor(colorThree)),*/
            LineChartData("妹", 20f, Color.parseColor(colorThree))
        )


        val dataList:MutableList<MutableList<LineChartData>> = mutableListOf(mList,mListTwo,mListThree)

        linechart.setData(dataList, 201f)


        btn_isAnim.setOnClickListener {
            linechart.setIsAnim(true)
        }
    }
}