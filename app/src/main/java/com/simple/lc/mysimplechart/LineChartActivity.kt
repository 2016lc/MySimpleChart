package com.simple.lc.mysimplechart

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import com.simple.lc.mylibrary.LineChartData
import kotlinx.android.synthetic.main.activity_linechart.*

/**
 * Author:LC
 * Date:2018/12/25
 * Description:This is 柱状图示例
 */
class LineChartActivity : Activity() {

    private var linewidth = 10f
    private var circlewidth = 8f
    private var iskinkedLine = false
    private var isShowCircle = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linechart)

        val colorOne = Color.parseColor("#54FF9F")
        val colorTwo = Color.parseColor("#FF6347")
        val colorThree = Color.parseColor("#98FB98")
        val colorFour = "#8B6914"
        val colorFive = "#7FFFD4"

        val mList: MutableList<LineChartData> = mutableListOf(
            LineChartData(3.36f, "11111111"),
            LineChartData(3.36f, "11111111"),
            LineChartData(3.36f, "11111111"),
            LineChartData(3.36f, "11111111"),
            LineChartData(100f, "11111111"),
            LineChartData(65f, "11111111"),
            LineChartData(12f, "11111111"),
            LineChartData(3f, "11111111"),
            LineChartData(190f, "11111111"),
            LineChartData(7f, "11111111"),
            /*LineChartData("弟弟", 100f, "11111111"(colorOne)),
            LineChartData("哥哥", 65f, "11111111"(colorOne)),
            LineChartData("哈哈", 12f, "11111111"(colorOne)),
            LineChartData("嘻嘻", 3f, "11111111"(colorOne)),
            LineChartData("呵呵", 190f, "11111111"(colorOne)),
            LineChartData("啧啧", 7f, "11111111"(colorOne)),
            LineChartData("弟弟", 100f, "11111111"(colorOne)),
            LineChartData("哥哥", 65f, "11111111"(colorOne)),
            LineChartData("哈哈", 12f, "11111111"(colorOne)),
            LineChartData("嘻嘻", 3f, "11111111"(colorOne)),
            LineChartData("呵呵", 190f, "11111111"(colorOne)),
            LineChartData("啧啧", 7f, "11111111"(colorOne)),
            LineChartData("弟弟", 100f, "11111111"(colorOne)),
            LineChartData("哥哥", 65f, "11111111"(colorOne)),
            LineChartData("哈哈", 12f, "11111111"(colorOne)),
            LineChartData("嘻嘻", 3f, "11111111"(colorOne)),
            LineChartData("呵呵", 190f, "11111111"(colorOne)),
            LineChartData("啧啧", 7f, "11111111"(colorOne)),
            LineChartData("弟弟", 100f, "11111111"(colorOne)),
            LineChartData("哥哥", 65f, "11111111"(colorOne)),
            LineChartData("哈哈", 12f, "11111111"(colorOne)),
            LineChartData("嘻嘻", 3f, "11111111"(colorOne)),
            LineChartData("呵呵", 190f, "11111111"(colorOne)),
            LineChartData("啧啧", 7f, "11111111"(colorOne)),
            LineChartData("弟弟", 100f, "11111111"(colorOne)),
            LineChartData("哥哥", 65f, "11111111"(colorOne)),
            LineChartData("哈哈", 12f, "11111111"(colorOne)),
            LineChartData("嘻嘻", 3f, "11111111"(colorOne)),
            LineChartData("呵呵", 190f, "11111111"(colorOne)),
            LineChartData("啧啧", 7f, "11111111"(colorOne)),
            LineChartData("弟弟", 100f, "11111111"(colorOne)),
            LineChartData("哥哥", 65f, "11111111"(colorOne)),
            LineChartData("哈哈", 12f, "11111111"(colorOne)),
            LineChartData("嘻嘻", 3f, "11111111"(colorOne)),
            LineChartData("呵呵", 190f, "11111111"(colorOne)),
            LineChartData("啧啧", 7f, "11111111"(colorOne)),
            LineChartData("弟弟", 100f, "11111111"(colorOne)),
            LineChartData("哥哥", 65f, "11111111"(colorOne)),
            LineChartData("哈哈", 12f, "11111111"(colorOne)),
            LineChartData("呵呵", 190f, "11111111"(colorOne)),
            LineChartData("啧啧", 7f, "11111111"(colorOne)),
            LineChartData("弟弟", 100f, "11111111"(colorOne)),
            LineChartData("哥哥", 65f, "11111111"(colorOne)),*/
            LineChartData(201f, "11111111")
        )


        val mListTwo: MutableList<LineChartData> = mutableListOf(
            LineChartData(19.35f, "11111111"),
            LineChartData(13.36f, "11111111"),
            LineChartData(1.96f, "11111111"),
            LineChartData(0f, "11111111"),
            LineChartData(10f, "11111111"),
            LineChartData(165f, "11111111"),
            LineChartData(30f, "11111111"),
            LineChartData(19f, "11111111"),
            LineChartData(70f, "11111111"),
            /* LineChartData("弟弟", 10f, "11111111"(colorTwo)),
             LineChartData("哥哥", 165f, "11111111"(colorTwo)),
             LineChartData("哈哈", 120f, "11111111"(colorTwo)),
             LineChartData("嘻嘻", 30f, "11111111"(colorTwo)),
             LineChartData("呵呵", 19f, "11111111"(colorTwo)),
             LineChartData("啧啧", 70f, "11111111"(colorTwo)),
             LineChartData("弟弟", 10f, "11111111"(colorTwo)),
             LineChartData("哥哥", 6f, "11111111"(colorTwo)),
             LineChartData("哈哈", 120f, "11111111"(colorTwo)),
             LineChartData("嘻嘻", 13f, "11111111"(colorTwo)),
             LineChartData("呵呵", 19f, "11111111"(colorTwo)),
             LineChartData("啧啧", 70f, "11111111"(colorTwo)),
             LineChartData("弟弟", 108f, "11111111"(colorTwo)),
             LineChartData("哥哥", 6f, "11111111"(colorTwo)),
             LineChartData("哈哈", 126f, "11111111"(colorTwo)),
             LineChartData("嘻嘻", 36f, "11111111"(colorTwo)),
             LineChartData("呵呵", 197f, "11111111"(colorTwo)),
             LineChartData("啧啧", 78f, "11111111"(colorTwo)),
             LineChartData("弟弟", 1f, "11111111"(colorTwo)),
             LineChartData("哥哥", 65f, "11111111"(colorTwo)),
             LineChartData("哈哈", 12f, "11111111"(colorTwo)),
             LineChartData("嘻嘻", 30f, "11111111"(colorTwo)),
             LineChartData("呵呵", 190f, "11111111"(colorTwo)),
             LineChartData("啧啧", 7f, "11111111"(colorTwo)),
             LineChartData("弟弟", 100f, "11111111"(colorTwo)),
             LineChartData("哥哥", 65f, "11111111"(colorTwo)),
             LineChartData("哈哈", 12f, "11111111"(colorTwo)),
             LineChartData("嘻嘻", 3f, "11111111"(colorTwo)),
             LineChartData("呵呵", 190f, "11111111"(colorTwo)),
             LineChartData("啧啧", 75f, "11111111"(colorTwo)),
             LineChartData("弟弟", 100f, "11111111"(colorTwo)),
             LineChartData("哥哥", 65f, "11111111"(colorTwo)),
             LineChartData("哈哈", 18f, "11111111"(colorTwo)),
             LineChartData("嘻嘻", 3f, "11111111"(colorTwo)),
             LineChartData("呵呵", 190f, "11111111"(colorTwo)),
             LineChartData("啧啧", 70f, "11111111"(colorTwo)),
             LineChartData("弟弟", 10f, "11111111"(colorTwo)),
             LineChartData("哥哥", 68f, "11111111"(colorTwo)),
             LineChartData("哈哈", 17f, "11111111"(colorTwo)),
             LineChartData("呵呵", 197f, "11111111"(colorTwo)),
             LineChartData("啧啧", 8f, "11111111"(colorTwo)),
             LineChartData("弟弟", 108f, "11111111"(colorTwo)),
             LineChartData("哥哥", 65f, "11111111"(colorTwo)),*/
            LineChartData(205f, "11111111")
        )


        val mListThree: MutableList<LineChartData> = mutableListOf(
            LineChartData(190.35f, "11111111"),
            LineChartData(130.36f, "11111111"),
            LineChartData(10.96f, "11111111"),
            LineChartData(0f, "11111111"),
            /* LineChartData("弟弟", 100f, "11111111"(colorThree)),
             LineChartData("哥哥", 105f, "11111111"(colorThree)),
             LineChartData("哈哈", 12f, "11111111"(colorThree)),
             LineChartData("嘻嘻", 130f, "11111111"(colorThree)),
             LineChartData("呵呵", 119f, "11111111"(colorThree)),
             LineChartData("啧啧", 170f, "11111111"(colorThree)),
              LineChartData("弟弟", 110f, "11111111"(colorThree)),
              LineChartData("哥哥", 165f, "11111111"(colorThree)),
              LineChartData("哈哈", 110f, "11111111"(colorThree)),
              LineChartData("嘻嘻", 130f, "11111111"(colorThree)),
              LineChartData("呵呵", 191f, "11111111"(colorThree)),
              LineChartData("啧啧", 170f, "11111111"(colorThree)),
              LineChartData("弟弟", 10f, "11111111"(colorThree)),
              LineChartData("哥哥", 16f, "11111111"(colorThree)),
              LineChartData("哈哈", 20f, "11111111"(colorThree)),
              LineChartData("嘻嘻", 3f, "11111111"(colorThree)),
              LineChartData("呵呵", 9f, "11111111"(colorThree)),
              LineChartData("啧啧", 0f, "11111111"(colorThree)),
              LineChartData("弟弟", 8f, "11111111"(colorThree)),
              LineChartData("哥哥", 60f, "11111111"(colorThree)),
              LineChartData("哈哈", 16f, "11111111"(colorThree)),
              LineChartData("嘻嘻", 36f, "11111111"(colorThree)),
              LineChartData("呵呵", 17f, "11111111"(colorThree)),
              LineChartData("啧啧", 7f, "11111111"(colorThree)),
              LineChartData("弟弟", 19f, "11111111"(colorThree)),
              LineChartData("哥哥", 6f, "11111111"(colorThree)),
              LineChartData("哈哈", 120f, "11111111"(colorThree)),
              LineChartData("嘻嘻", 39f, "11111111"(colorThree)),
              LineChartData("呵呵", 190f, "11111111"(colorThree)),
              LineChartData("啧啧", 70f, "11111111"(colorThree)),
              LineChartData("弟弟", 10f, "11111111"(colorThree)),
              LineChartData("哥哥", 65f, "11111111"(colorThree)),
              LineChartData("哈哈", 18f, "11111111"(colorThree)),
              LineChartData("嘻嘻", 30f, "11111111"(colorThree)),
              LineChartData("呵呵", 90f, "11111111"(colorThree)),
              LineChartData("啧啧", 7f, "11111111"(colorThree)),
              LineChartData("弟弟", 10f, "11111111"(colorThree)),
              LineChartData("哥哥", 6f, "11111111"(colorThree)),
              LineChartData("哈哈", 1f, "11111111"(colorThree)),
              LineChartData("嘻嘻", 3f, "11111111"(colorThree)),
              LineChartData("呵呵", 190f, "11111111"(colorThree)),
              LineChartData("啧啧", 79f, "11111111"(colorThree)),
              LineChartData("弟弟", 18f, "11111111"(colorThree)),
              LineChartData("哥哥", 69f, "11111111"(colorThree)),
              LineChartData("哈哈", 15f, "11111111"(colorThree)),
              LineChartData("呵呵", 198f, "11111111"(colorThree)),
              LineChartData("啧啧", 6f, "11111111"(colorThree)),
              LineChartData("弟弟", 18f, "11111111"(colorThree)),
              LineChartData("哥哥", 6f, "11111111"(colorThree)),
             LineChartData("哈哈", 190.35f, "11111111"(colorThree)),
             LineChartData("嘻嘻", 130.36f, "11111111"(colorThree)),
             LineChartData("呵呵", 10.96f, "11111111"(colorThree)),
             LineChartData("啧啧", 0f, "11111111"(colorThree)),
             LineChartData("弟弟", 100f, "11111111"(colorThree)),
             LineChartData("哥哥", 105f, "11111111"(colorThree)),
             LineChartData("哈哈", 12f, "11111111"(colorThree)),
             LineChartData("嘻嘻", 130f, "11111111"(colorThree)),
             LineChartData("呵呵", 119f, "11111111"(colorThree)),
             LineChartData("啧啧", 170f, "11111111"(colorThree)),
             LineChartData("弟弟", 110f, "11111111"(colorThree)),
             LineChartData("哥哥", 165f, "11111111"(colorThree)),
             LineChartData("哈哈", 110f, "11111111"(colorThree)),
             LineChartData("嘻嘻", 130f, "11111111"(colorThree)),
             LineChartData("呵呵", 191f, "11111111"(colorThree)),
             LineChartData("啧啧", 170f, "11111111"(colorThree)),
             LineChartData("弟弟", 10f, "11111111"(colorThree)),
             LineChartData("哥哥", 16f, "11111111"(colorThree)),
             LineChartData("哈哈", 20f, "11111111"(colorThree)),
             LineChartData("嘻嘻", 3f, "11111111"(colorThree)),
             LineChartData("呵呵", 9f, "11111111"(colorThree)),
             LineChartData("啧啧", 0f, "11111111"(colorThree)),
             LineChartData("弟弟", 8f, "11111111"(colorThree)),
             LineChartData("哥哥", 60f, "11111111"(colorThree)),
             LineChartData("哈哈", 16f, "11111111"(colorThree)),
             LineChartData("嘻嘻", 36f, "11111111"(colorThree)),
             LineChartData("呵呵", 17f, "11111111"(colorThree)),
             LineChartData("啧啧", 7f, "11111111"(colorThree)),
             LineChartData("弟弟", 19f, "11111111"(colorThree)),
             LineChartData("哥哥", 6f, "11111111"(colorThree)),
             LineChartData("哈哈", 120f, "11111111"(colorThree)),
             LineChartData("嘻嘻", 39f, "11111111"(colorThree)),
             LineChartData("呵呵", 190f, "11111111"(colorThree)),
             LineChartData("啧啧", 70f, "11111111"(colorThree)),
             LineChartData("弟弟", 10f, "11111111"(colorThree)),
             LineChartData("哥哥", 65f, "11111111"(colorThree)),
             LineChartData("哈哈", 18f, "11111111"(colorThree)),
             LineChartData("嘻嘻", 30f, "11111111"(colorThree)),
             LineChartData("呵呵", 90f, "11111111"(colorThree)),
             LineChartData("啧啧", 7f, "11111111"(colorThree)),
             LineChartData("弟弟", 10f, "11111111"(colorThree)),
             LineChartData("哥哥", 6f, "11111111"(colorThree)),
             LineChartData("哈哈", 1f, "11111111"(colorThree)),
             LineChartData("嘻嘻", 3f, "11111111"(colorThree)),
             LineChartData("呵呵", 190f, "11111111"(colorThree)),
             LineChartData("啧啧", 79f, "11111111"(colorThree)),
             LineChartData("弟弟", 18f, "11111111"(colorThree)),
             LineChartData("哥哥", 69f, "11111111"(colorThree)),
             LineChartData("哈哈", 15f, "11111111"(colorThree)),
             LineChartData("呵呵", 198f, "11111111"(colorThree)),
             LineChartData("啧啧", 6f, "11111111"(colorThree)),
             LineChartData("弟弟", 18f, "11111111"(colorThree)),
             LineChartData("哥哥", 6f, "11111111"(colorThree)),*/
            LineChartData(20f, "11111111")
        )


        val dataList: MutableList<MutableList<LineChartData>> = mutableListOf(mList, mListTwo, mListThree)

        val colorList: List<Int> = listOf(colorOne, colorTwo, colorThree)

        val nameList: List<String> = listOf("的啦啦啦啦啦啦啦", "哈哈哈", "嘻嘻嘻嘻")

        linechart.setYunit("名")
        linechart.setColor(colorList as MutableList<Int>).setData(dataList, 201f)
            .setName(nameList as MutableList<String>).build()
        // linechart.setData(dataList, 201f)


        btn_linewidth.setOnClickListener {
            linechart.setLineWidth(linewidth)
            linewidth += 5f
            linechart.notifyDataSet()
            //linechart.setIsAnim(true)
        }

        btn_circlewidth.setOnClickListener {
            linechart.setCircleWidth(circlewidth)
            circlewidth += 5f
            linechart.notifyDataSet()
        }

        btn_iskinkedLine.setOnClickListener {
            linechart.setIskinkedLine(iskinkedLine)
            iskinkedLine = !iskinkedLine
            linechart.notifyDataSet()
        }

        btn_isShowCircle.setOnClickListener {
            linechart.setIsShowCircle(isShowCircle)
            isShowCircle = !isShowCircle
            linechart.notifyDataSet()
        }


    }
}