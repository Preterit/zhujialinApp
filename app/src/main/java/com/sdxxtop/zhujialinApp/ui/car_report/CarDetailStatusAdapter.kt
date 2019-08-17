package com.sdxxtop.zhujialinApp.ui.car_report

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sdxxtop.zhujialinApp.R
import com.sdxxtop.zhujialinApp.ui.car_report.data.StatusBean

/**
 * @author :  lwb
 * Date: 2019/8/17
 * Desc:
 */
class CarDetailStatusAdapter(layoutResId: Int = R.layout.item_carstatus_view) : BaseQuickAdapter<StatusBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder?, item: StatusBean?) {
        val tvLine = helper?.getView<TextView>(R.id.tv_line)
        val tvStatus = helper?.getView<TextView>(R.id.tv_status)
        val tvTime = helper?.getView<TextView>(R.id.tv_time)
        tvLine?.visibility = if (helper?.adapterPosition == 0) View.VISIBLE else View.GONE
        tvStatus?.text = item?.title
        tvTime?.text = item?.time
    }
}