package com.sdxxtop.zhujialinApp.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdxxtop.utils.UIUtils;
import com.sdxxtop.zhujialinApp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author :  lwb
 * Date: 2019/4/4
 * Desc:
 */
public class CustomProgressBar extends RelativeLayout {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("MM.dd HH:mm:ss");

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;

    private TextView tvLine1;
    private TextView tvLine2;
    private TextView tvLine3;

    private TextView tvText1;
    private TextView tvText2;
    private TextView tvText3;
    private TextView tvText4;

    private LinearLayout llContainorTemp;

    private TextView tvDate1;
    private TextView tvDate2;
    private TextView tvDate3;
    private TextView tvDate4;

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_progressbar, this, true);
        llContainorTemp = (LinearLayout) view.findViewById(R.id.ll_containor_temp);
        img1 = (ImageView) view.findViewById(R.id.img_1);
        img2 = (ImageView) view.findViewById(R.id.img_2);
        img3 = (ImageView) view.findViewById(R.id.img_3);
        img4 = (ImageView) view.findViewById(R.id.img_4);

        tvLine1 = (TextView) view.findViewById(R.id.tv_line_1);
        tvLine2 = (TextView) view.findViewById(R.id.tv_line_2);
        tvLine3 = (TextView) view.findViewById(R.id.tv_line_3);

        tvText1 = (TextView) view.findViewById(R.id.tv_text_1);
        tvText2 = (TextView) view.findViewById(R.id.tv_text_2);
        tvText3 = (TextView) view.findViewById(R.id.tv_text_3);
        tvText4 = (TextView) view.findViewById(R.id.tv_text_4);

        tvDate1 = (TextView) view.findViewById(R.id.tv_date_1);
        tvDate2 = (TextView) view.findViewById(R.id.tv_date_2);
        tvDate3 = (TextView) view.findViewById(R.id.tv_date_3);
        tvDate4 = (TextView) view.findViewById(R.id.tv_date_4);


        WindowManager windowManager = (WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int width = display.getWidth() / 8 - UIUtils.dip2px(8);
        LayoutParams lp = new LayoutParams(llContainorTemp.getLayoutParams());
        lp.setMargins(width, 0, width, 0);
        llContainorTemp.setLayoutParams(lp);
        setStatus(0);
    }

    /**
     * 设置状态
     */
    public void setStatus(int status, List<String> date) {
        setStatus(status - 1);
        setDateStatus(status - 1, date);
        this.invalidate();
    }

    private void setDateStatus(int curStatus, List<String> date) {
        switch (curStatus) {
            case 4:
            case 3:
                tvDate4.setText(parseShowDate(date.get(3)));
            case 2:
                tvDate3.setText(parseShowDate(date.get(2)));
            case 1:
                tvDate2.setText(parseShowDate(date.get(1)));
            case 0:
                tvDate1.setText(parseShowDate(date.get(0)));
                break;
        }
    }

    private void setStatus(int status) {
        tvText4.setText("已完成");
        switch (status) {
            case 0:   // 已受理
                img1.setImageDrawable(getResources().getDrawable(R.drawable.details));
                img2.setImageDrawable(getResources().getDrawable(R.drawable.progress_status_normal));
                img3.setImageDrawable(getResources().getDrawable(R.drawable.progress_status_normal));
                img4.setImageDrawable(getResources().getDrawable(R.drawable.progress_status_normal));
                tvLine1.setBackgroundColor(Color.parseColor("#DEEAE3"));
                tvLine2.setBackgroundColor(Color.parseColor("#DEEAE3"));
                tvLine3.setBackgroundColor(Color.parseColor("#DEEAE3"));
                break;
            case 1:  // 已派发
                img1.setImageDrawable(getResources().getDrawable(R.drawable.details));
                img2.setImageDrawable(getResources().getDrawable(R.drawable.details));
                img3.setImageDrawable(getResources().getDrawable(R.drawable.progress_status_normal));
                img4.setImageDrawable(getResources().getDrawable(R.drawable.progress_status_normal));
                tvLine1.setBackgroundColor(Color.parseColor("#32B16C"));
                tvLine2.setBackgroundColor(Color.parseColor("#DEEAE3"));
                tvLine3.setBackgroundColor(Color.parseColor("#DEEAE3"));
                break;
            case 2:  // 已反馈
                img1.setImageDrawable(getResources().getDrawable(R.drawable.details));
                img2.setImageDrawable(getResources().getDrawable(R.drawable.details));
                img3.setImageDrawable(getResources().getDrawable(R.drawable.details));
                img4.setImageDrawable(getResources().getDrawable(R.drawable.progress_status_normal));
                tvLine1.setBackgroundColor(Color.parseColor("#32B16C"));
                tvLine2.setBackgroundColor(Color.parseColor("#32B16C"));
                tvLine3.setBackgroundColor(Color.parseColor("#DEEAE3"));
                break;

            case 3:  // 已完成
                img1.setImageDrawable(getResources().getDrawable(R.drawable.details));
                img2.setImageDrawable(getResources().getDrawable(R.drawable.details));
                img3.setImageDrawable(getResources().getDrawable(R.drawable.details));
                img4.setImageDrawable(getResources().getDrawable(R.drawable.details));

                tvLine1.setBackgroundColor(Color.parseColor("#32B16C"));
                tvLine2.setBackgroundColor(Color.parseColor("#32B16C"));
                tvLine3.setBackgroundColor(Color.parseColor("#32B16C"));
                break;

            case 4:  // 受理失败
                img1.setImageDrawable(getResources().getDrawable(R.drawable.details));
                img2.setImageDrawable(getResources().getDrawable(R.drawable.details));
                img3.setImageDrawable(getResources().getDrawable(R.drawable.details));
                img4.setImageDrawable(getResources().getDrawable(R.drawable.event_progress_show_error));

                tvLine1.setBackgroundColor(Color.parseColor("#32B16C"));
                tvLine2.setBackgroundColor(Color.parseColor("#32B16C"));
                tvLine3.setBackgroundColor(Color.parseColor("#32B16C"));

                tvText4.setText("未通过");
                break;

        }
    }

    private String parseShowDate(String strDate) {
        if (TextUtils.isEmpty(strDate)) {
            return "";
        }

        try {
            Date date = sdf.parse(strDate);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
