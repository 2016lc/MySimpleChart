package com.simple.lc.mylibrary

/**
 * Author:LC
 * Date:2018/11/27
 * Description:柱状图
 */
class BarChartData {

    var name: String? = null
    var value: Float? = null
    var twoValue: Float = 0f

    constructor(name: String?, value: Float?, twoValue: Float) {
        this.name = name
        this.twoValue = twoValue
        this.value = value
    }

    constructor(name: String?, value: Float?) {
        this.name = name
        this.value = value
    }


}