package com.simple.lc.mysimplechart

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.simple.lc.mylibrary.PieChartData
import com.simple.lc.mylibrary.PieChartType
import kotlinx.android.synthetic.main.activity_mypiechart.*
import java.util.*

/**
 * Author:LC
 * Date:2018/11/27
 * Description:This is MyPieChart
 */
class PieChartActivity : AppCompatActivity() {
    private var datasize = 15f
    private var type = "pointingInstructions"
    private var digit = 0
    private var isHollow = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_mypiechart
        )
        val mList: List<PieChartData> = listOf(
            PieChartData(Color.parseColor("#0000FF"), "哈哈", 1f, "人"),
            PieChartData(Color.parseColor("#8A2BE2"), "嘻嘻", 2f, "人"),
            PieChartData(Color.parseColor("#A52A2A"), "呵呵", 3f, "人"),
            PieChartData(Color.parseColor("#DEB887"), "啧啧", 4f, "人"),
            PieChartData(Color.parseColor("#5F9EA0"), "弟弟", 5f, "人"),
            PieChartData(Color.parseColor("#7FFF00"), "哥哥", 6f, "人"),
            PieChartData(Color.parseColor("#D2691E"), "妹妹", 7f, "人")
        )
        piechart.setData(mList).build()
        //piechart.setType(PieChartType.PERCENT)
        // piechart.setType(PieChartType.NUM)
        // piechart.setType(PieChartType.CONTENT_NUM)

        btn_datasize.setOnClickListener {
            piechart.setDataSize(datasize)
            datasize += 5f
            piechart.notifyDataSet()
        }

        btn_datacolor.setOnClickListener {
            piechart.setDataColor(Color.parseColor("#${getRandColor()}"))
            piechart.notifyDataSet()
        }

        btn_unitsize.setOnClickListener {
            piechart.setUnitSize(datasize)
            datasize += 5f
            piechart.notifyDataSet()
        }

        btn_unitcolor.setOnClickListener {
            piechart.setUnitColor(Color.parseColor("#${getRandColor()}"))
            piechart.notifyDataSet()
        }

        btn_layouttype.setOnClickListener {
            piechart.setLayoutType(type)
            type = if(type=="defaults"){
                "pointingInstructions"
            }else{
                "defaults"
            }
            piechart.notifyDataSet()
        }

        btn_animtime.setOnClickListener {
            piechart.setAnimTime(5000)
            piechart.notifyDataSet()
        }

        btn_pointingcolor.setOnClickListener {
            piechart.setPointingColor(Color.parseColor("#${getRandColor()}"))
            piechart.notifyDataSet()
        }

        btn_digit.setOnClickListener {
            piechart.setDigit(digit)
            digit+=1
            piechart.notifyDataSet()
        }

        btn_ishollow.setOnClickListener {
            piechart.setIsHollow(isHollow)
            isHollow = !isHollow
            piechart.notifyDataSet()
        }


    }

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