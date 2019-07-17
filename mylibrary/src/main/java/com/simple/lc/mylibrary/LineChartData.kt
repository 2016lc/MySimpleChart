package com.simple.lc.mylibrary

/**
 * Author:LC
 * Date:2018/11/27
 * Description:折线图
 */
class LineChartData {

    var name: String? = null
    var value: Float? = null
    var color: Int? = null

    constructor(name: String?, value: Float?, color: Int?) {
        this.name = name
        this.value = value
        this.color = color
    }
}