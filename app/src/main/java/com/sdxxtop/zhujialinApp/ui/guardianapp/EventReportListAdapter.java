package com.sdxxtop.zhujialinApp.ui.guardianapp;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.zhujialinApp.R;
import com.sdxxtop.zhujialinApp.presenter.bean.EventIndexBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventReportListAdapter extends BaseQuickAdapter<EventIndexBean.EventBean, BaseViewHolder> {
    public EventReportListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventIndexBean.EventBean item) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvTime = helper.getView(R.id.tv_time);
        TextView tvAddress = helper.getView(R.id.tv_address);
        TextView tvStatus = helper.getView(R.id.tv_status);

        tvTitle.setText(item.getTitle());
        tvTime.setText("上报时间：" + handleTime(item.getAdd_time()));
        tvAddress.setText("事件地点：" + item.getPlace());
        String strStatus = "";
        switch (item.getStatus()) { //状态(1:带派发 2:待解决 3:待验收 4:验收通过 5:验收不通过)
            case 2:
                strStatus = "已派发";
                break;
            case 3:
                strStatus = "已反馈";
                break;
            case 4:
                strStatus = "已完成";
                break;
            case 5:
//                strStatus = "验收不通过";
                strStatus = "已完成";
                break;
            default:
                strStatus = "已提交";
                break;
        }
        tvStatus.setText(strStatus);

        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventReportDetailActivity.startDetailActivity(v.getContext(), String.valueOf(item.getId()));
            }
        });
    }

    private String handleTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        try {
            Date date = sdf.parse(time);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }
}
