package com.simple.lc.mysimplechart

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.simple.lc.mylibrary.BarChartData
import com.simple.lc.mylibrary.DoubleBarChartData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_barchart.setOnClickListener {
            run {
                val intent = Intent(this, BarChartActivity::class.java)
                startActivity(intent)
            }
        }
        btn_doublebarchart.setOnClickListener {
            run {
                val intent = Intent(this, DoubleBarChartActivity::class.java)
                startActivity(intent)
            }
        }
        btn_horibarchart.setOnClickListener {
            run {
                val intent = Intent(this, HoriBarChartActivity::class.java)
                startActivity(intent)
            }
        }

    }
}
