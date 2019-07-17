package com.simple.lc.mylibrary

/**
 * Author:LC
 * Date:2018/11/27
 * Description:柱状图
 */
class DoubleBarChartData {

    var name: String? = null
    var valueOne: Float? = null
    var valueTwo: Float? = null

    constructor(name: String?, valueOne: Float?,valueTwo: Float?) {
        this.name = name
        this.valueOne = valueOne
        this.valueTwo = valueTwo
    }
}