package com.sdxxtop.zhujialinApp.ui.car_report

import android.content.Intent
import android.graphics.Color
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sdxxtop.zhujialinApp.R
import com.sdxxtop.zhujialinApp.ui.car_report.data.Event
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.textColor


/**
 * @author :  lwb
 * Date: 2019/8/16
 * Desc:
 */
class MyCarEventAdapter(layoutResId: Int = R.layout.item_mycar_event) : BaseQuickAdapter<Event, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder?, item: Event) {
        val tvStatus = helper?.getView<TextView>(R.id.tv_status)
        val tvAddress = helper?.getView<TextView>(R.id.tv_address)
        val tvTime = helper?.getView<TextView>(R.id.tv_time)
        val tvCarNum = helper?.getView<TextView>(R.id.tv_car_num)

        tvStatus?.text = getStatusStr(item.status)
        tvStatus?.textColor = Color.parseColor("#73E373")
        tvCarNum?.text = item.car_name
        tvAddress?.text = "上报地点: " + item.address
        tvTime?.text = "上报时间: " + item.report_time

        helper?.itemView?.onClick {
            val intent = Intent(mContext, CarReportDetailActivity::class.java)
            intent.putExtra("eventId", item.id)
            mContext.startActivity(intent)
        }
    }

    //事件状态:1=待派发, 2=待解决,3=待验收, 4=验收通过,5=验收不通
    fun getStatusStr(status: Int): String {
        var str = ""
        when (status) {
            1 -> {
                str = "待派发"
            }
            2 -> {
                str = "待解决"
            }
            3 -> {
                str = "已完成"
            }
        }
        return str
    }
}
