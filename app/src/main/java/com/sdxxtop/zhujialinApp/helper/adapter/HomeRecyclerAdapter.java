package com.sdxxtop.zhujialinApp.helper.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.zhujialinApp.R;
import com.sdxxtop.zhujialinApp.presenter.bean.MainIndexBean;
import com.sdxxtop.zhujialinApp.ui.car_report.CarReportActivity;
import com.sdxxtop.zhujialinApp.ui.guardianapp.EventReportActivity;
import com.sdxxtop.zhujialinApp.ui.guardianapp.TaskAgentsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

public class HomeRecyclerAdapter extends BaseQuickAdapter<MainIndexBean.EventBean, BaseViewHolder> {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public HomeRecyclerAdapter(int layoutResId, @Nullable List<MainIndexBean.EventBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainIndexBean.EventBean item) {
        ImageView itemImageView = helper.getView(R.id.iv_right);
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvTask1 = helper.getView(R.id.tv_new_task1);
        TextView tvTask2 = helper.getView(R.id.tv_new_task2);
        TextView tvScore1 = helper.getView(R.id.tv_score1);
        TextView tvScore2 = helper.getView(R.id.tv_score2);
        View view = helper.getView(R.id.ll_bg);
        List<EventData> eventDataList = new ArrayList<>();

        switch (item.type) {
            case MainIndexBean.EventBean.TYPE_PENDING:
                tvTitle.setText("待办任务");
                if (item.mPendingEventBean != null) {
                    for (MainIndexBean.PendingEventBean pendingEventBean : item.mPendingEventBean) {
                        EventData eventData = new EventData();
                        eventData.end_date = pendingEventBean.getEnd_date();
                        eventData.title = pendingEventBean.getTitle();
                        eventData.status = pendingEventBean.getStatus();
                        eventDataList.add(eventData);
                    }
                }
                handleTaskStatus(MainIndexBean.EventBean.TYPE_PENDING, tvTask1, tvTask2, tvScore1, tvScore2, eventDataList);
                itemImageView.setImageResource(R.drawable.icon_1_list);
                view.setBackgroundColor(mContext.getResources().getColor(R.color.color_7ECEF4));

                break;
            case MainIndexBean.EventBean.TYPE_ADD:
                tvTitle.setText("事件上报");

                if (item.mAddEventBean != null) {
                    for (MainIndexBean.AddEventBean pendingEventBean : item.mAddEventBean) {
                        EventData eventData = new EventData();
                        eventData.end_date = pendingEventBean.getEnd_date();
                        eventData.title = pendingEventBean.getTitle();
                        eventData.status = pendingEventBean.getStatus();
                        eventDataList.add(eventData);
                    }
                }
                handleTaskStatus(MainIndexBean.EventBean.TYPE_ADD, tvTask1, tvTask2, tvScore1, tvScore2, eventDataList);


                itemImageView.setImageResource(R.drawable.icon_2_list);
                view.setBackgroundColor(mContext.getResources().getColor(R.color.color_8C97CB));
                break;
            case MainIndexBean.EventBean.TYPE_CAR_REPORT:
                tvTitle.setText("车辆上报");
                if (item.mAddCarEvent != null) {
                    for (MainIndexBean.AddCarEvent carEvent : item.mAddCarEvent) {
                        EventData eventData = new EventData();
                        eventData.end_date = carEvent.getEnd_time();
                        eventData.title = carEvent.getCar_name();
                        eventData.status = carEvent.getStatus();
                        eventDataList.add(eventData);
                    }
                }
                handleTaskStatus(MainIndexBean.EventBean.TYPE_CAR_REPORT, tvTask1, tvTask2, tvScore1, tvScore2, eventDataList);


                itemImageView.setImageResource(R.drawable.icon_3_list);
                view.setBackgroundColor(mContext.getResources().getColor(R.color.color_89C997));
                break;
        }


        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UIUtils.showToast("" + item);
                Intent intent = null;
                switch (item.type) {
                    case MainIndexBean.EventBean.TYPE_PENDING: // 待办任务
                        intent = new Intent(mContext, TaskAgentsActivity.class);
                        break;
                    case MainIndexBean.EventBean.TYPE_ADD:  //事件上报
                        intent = new Intent(mContext, EventReportActivity.class);
                        break;
                    case MainIndexBean.EventBean.TYPE_CAR_REPORT:  //车辆上报
                        intent = new Intent(mContext, CarReportActivity.class);
                        break;
//                    case R.drawable.list_3:  // 环保检测
//                        intent = new Intent(mContext, EnvironmentalTestActivity.class);
//                        break;
//                    case R.drawable.list_4:  // 智能传感器
//                        intent = new Intent(mContext, IntelligentSensorActivity.class);
//                        break;
//                    case R.drawable.list_5:  // 统计分析
//                        intent = new Intent(mContext, StatisticsAnalyzeActivity.class);
//                        break;
//                    case R.drawable.list_6:  // 检举举报
//                        intent = new Intent(mContext, ImpeachReportActivity.class);
//                        break;
                }

                if (intent != null) {
                    mContext.startActivity(intent);
                }
            }
        });
    }

    private void handleTaskStatus(int type, TextView tvTask1, TextView tvTask2,
                                  TextView tvScore1, TextView tvScore2, List<EventData> eventDataList) {
        switch (eventDataList.size()) {
            case 0:
                tvTask1.setVisibility(View.INVISIBLE);
                tvTask2.setVisibility(View.INVISIBLE);
                tvScore1.setVisibility(View.INVISIBLE);
                tvScore2.setVisibility(View.INVISIBLE);
                break;
            case 1:
                tvTask1.setVisibility(View.VISIBLE);
                tvTask2.setVisibility(View.INVISIBLE);
                tvScore1.setVisibility(View.VISIBLE);
                tvScore2.setVisibility(View.INVISIBLE);
                break;
            case 2:
                tvTask1.setVisibility(View.VISIBLE);
                tvTask2.setVisibility(View.VISIBLE);
                tvScore1.setVisibility(View.VISIBLE);
                tvScore2.setVisibility(View.VISIBLE);
                break;
        }

        if (eventDataList.size() == 1) {
            String appendValue = "新任务：";

            if (type == MainIndexBean.EventBean.TYPE_ADD) {// 事件上报
                appendValue = getAddValue(eventDataList.get(0).status);
            } else if (type == MainIndexBean.EventBean.TYPE_PENDING) { // 代办
                appendValue = getAppendValue(eventDataList.get(0).status);
            } else if (type == MainIndexBean.EventBean.TYPE_CAR_REPORT) {// 车辆上报
                appendValue = getAddCarValue(eventDataList.get(0).status);
            }

            if (!TextUtils.isEmpty(appendValue)) {
                tvTask1.setText(appendValue + eventDataList.get(0).title);
                tvScore1.setVisibility(View.VISIBLE);
                handleScore(tvScore1, eventDataList.get(0).end_date);
            } else {
                tvTask1.setVisibility(View.GONE);
                tvScore1.setVisibility(View.INVISIBLE);
            }
        } else if (eventDataList.size() >= 2) {

            String appendValue = "新任务：";
            String appendValue2 = "新任务：";

            if (type == MainIndexBean.EventBean.TYPE_ADD) {
                appendValue = getAddValue(eventDataList.get(0).status);
                appendValue2 = getAddValue(eventDataList.get(1).status);
            } else if (type == MainIndexBean.EventBean.TYPE_PENDING) {
                appendValue = getAppendValue(eventDataList.get(0).status);
                appendValue2 = getAppendValue(eventDataList.get(1).status);
            } else if (type == MainIndexBean.EventBean.TYPE_CAR_REPORT) {// 车辆上报
                appendValue = getAddCarValue(eventDataList.get(0).status);
                appendValue2 = getAddCarValue(eventDataList.get(1).status);
            }

            if (!TextUtils.isEmpty(appendValue)) {
                tvTask1.setText(appendValue + eventDataList.get(0).title);
                tvScore1.setVisibility(View.VISIBLE);
                handleScore(tvScore1, eventDataList.get(0).end_date);
            } else {
                tvTask1.setVisibility(View.GONE);
                tvScore1.setVisibility(View.INVISIBLE);
            }

            if (!TextUtils.isEmpty(appendValue2)) {
                tvTask2.setText(appendValue2 + eventDataList.get(1).title);
                tvScore2.setVisibility(View.VISIBLE);
                handleScore(tvScore2, eventDataList.get(1).end_date);
            } else {
                tvTask2.setVisibility(View.GONE);
                tvScore2.setVisibility(View.INVISIBLE);
            }
        }

    }

    private String getAppendValue(int status) {
        String value = "";
        switch (status) {
            case 1:
                value = "新任务：";
                break;
            case 2:
                value = "新任务：";
                break;
            case 3:
                value = "新任务：";
                break;
//            //受理事变
            default:
                value = "新任务：";
                break;
        }
        return value;
    }

    private String getAddValue(int status) {
        String value = "";
        switch (status) {
            case 1:
                value = "已提交：";
                break;
            case 2:
                value = "已派发：";
                break;
            case 3:
                value = "已反馈：";
                break;
            //受理事变
            default:
                value = "已完成：";
                break;
        }
        return value;
    }

    //事件状态: 1:"待派发"  2: "待解决"  3 "已完成"
    private String getAddCarValue(int status) {
        String value = "";
        switch (status) {
            case 1:
                value = "待派发：";
                break;
            case 2:
                value = "待解决：";
                break;
            case 3:
                value = "已完成：";
                break;
        }
        return value;
    }

    public static class EventData {
        public String title;
        public String end_date;
        public int status;
    }


    public void handleScore(TextView tv, String strDate) {
        if (TextUtils.isEmpty(strDate)) {
            tv.setVisibility(View.INVISIBLE);
            return;
        }
        try {
            Date date = simpleDateFormat.parse(strDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int day = calendar.get(Calendar.DATE);
            int month = calendar.get(Calendar.MONTH) + 1;
//            tv.setVisibility(View.VISIBLE);
            tv.setText(new StringBuilder().append("截止：").append(month).append(".").append(day));
        } catch (ParseException e) {
            e.printStackTrace();
            tv.setVisibility(View.INVISIBLE);
        }
    }
}
