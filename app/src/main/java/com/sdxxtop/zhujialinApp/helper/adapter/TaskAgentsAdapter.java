package com.sdxxtop.zhujialinApp.helper.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.utils.DateUtil;
import com.sdxxtop.zhujialinApp.R;
import com.sdxxtop.zhujialinApp.presenter.bean.EventIndexBean;
import com.sdxxtop.zhujialinApp.ui.car_report.CarReportDetailActivity;
import com.sdxxtop.zhujialinApp.ui.guardianapp.EventReportDetailActivity;

public class TaskAgentsAdapter extends BaseQuickAdapter<EventIndexBean.EventBean, BaseViewHolder> {
    public TaskAgentsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventIndexBean.EventBean item) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvAddress = helper.getView(R.id.tv_address);
        TextView tvImportant = helper.getView(R.id.tv_important);
        TextView tvDate = helper.getView(R.id.tv_date);
        TextView tvPhase = helper.getView(R.id.tv_phase);

        int is_car = item.getIs_car();

        tvTitle.setText(item.getTitle());
        tvAddress.setText("地址：" + (is_car == 1 ? item.getAddress() : item.getPlace()));
        String importantType = item.getImportant_type();
        StringBuilder sb = new StringBuilder().append("重要性：");
        switch (importantType) { //1:低 2:中 3:高
            case "1":
                sb.append("低");
                break;
            case "2":
                sb.append("中");
                break;
            default:
                sb.append("高");
                break;
        }

        tvImportant.setText(sb);

        String endDate = item.getEnd_time();
        tvDate.setText("截止日期：" + DateUtil.stampToDate(endDate));

        String strStatus = "";
        if (is_car == 1) {
            switch (item.getStatus()) { //1=待派发, 2=待解决,3=待验收, 4=验收通过,5=验收不通
                case 1:
                    strStatus = "待派发";
                    break;
                case 2:
                    strStatus = "待解决";
                    break;
                case 3:
                    strStatus = "已完成";
                    break;
            }
        } else {
            switch (item.getStatus()) { //状态(1:待派发 2:待解决 3:待验收 4:验收通过 5:验收未通过)
                case 2:
                    strStatus = "待解决";
                    break;
                case 3:
                    strStatus = "待验收";
                    break;
                case 4:
                    strStatus = "验收通过";
                    break;
                case 5:
                    strStatus = "验收未通过";
                    break;
                default:
                    strStatus = "待派发";
                    break;
            }
        }
        tvPhase.setText(strStatus);

        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getIs_car() == 1) {
                    Intent intent = new Intent(mContext, CarReportDetailActivity.class);
                    intent.putExtra("eventId", item.getId());
                    mContext.startActivity(intent);
                } else {
                    EventReportDetailActivity.startDetailActivity(v.getContext(), String.valueOf(item.getId()));
                }
            }
        });
    }
}
