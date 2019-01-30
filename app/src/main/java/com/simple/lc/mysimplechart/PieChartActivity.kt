package com.simple.lc.mysimplechart

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.simple.lc.mylibrary.PieChartData
import com.simple.lc.mylibrary.PieChartType
import kotlinx.android.synthetic.main.activity_mypiechart.*

/**
 * Author:LC
 * Date:2018/11/27
 * Description:This is MyPieChart
 */
class PieChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.
            activity_mypiechart)
        val mList: List<PieChartData> = listOf(
            PieChartData(Color.parseColor("#0000FF"), "哈哈", 1f,"人"),
            PieChartData(Color.parseColor("#8A2BE2"), "嘻嘻", 2f,"人"),
            PieChartData(Color.parseColor("#A52A2A"), "呵呵", 3f,"人"),
            PieChartData(Color.parseColor("#DEB887"), "啧啧", 4f,"人"),
            PieChartData(Color.parseColor("#5F9EA0"), "弟弟", 5f,"人"),
            PieChartData(Color.parseColor("#7FFF00"), "哥哥", 6f,"人"),
            PieChartData(Color.parseColor("#D2691E"), "妹妹", 7f,"人")
        )
        piechart.setData(mList)
        //piechart.setType(PieChartType.PERCENT)
       // piechart.setType(PieChartType.NUM)
        piechart.setType(PieChartType.CONTENT_NUM)
    }
}