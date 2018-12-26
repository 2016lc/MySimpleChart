package com.simple.lc.mylibrary

/**
 * Author:LC
 * Date:2018/11/27
 * Description:This is 柱状图
 */
class DoubleBarChartData {

    var nameOne: String? = null
    var nameTwo: String? = null
    var valueOne: Float? = null
    var valueTwo: Float? = null

    constructor(nameOne: String?, nameTwo: String?, valueOne: Float?,valueTwo: Float?) {
        this.nameOne = nameOne
        this.nameTwo = nameTwo
        this.valueOne = valueOne
        this.valueTwo = valueTwo
    }
}