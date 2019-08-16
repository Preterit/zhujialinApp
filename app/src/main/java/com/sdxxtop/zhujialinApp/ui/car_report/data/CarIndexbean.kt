package com.sdxxtop.zhujialinApp.ui.car_report.data

/**
 * @author :  lwb
 * Date: 2019/8/16
 * Desc:
 */

data class CarIndexBean(
        val event: List<Event>,
        val num: Int,
        var pageCount:Int
)

data class Event(
    val address: String,
    val car_name: String,
    val id: Int,
    val report_time: String,
    val status: Int
)