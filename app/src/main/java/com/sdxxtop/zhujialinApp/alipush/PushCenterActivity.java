package com.sdxxtop.zhujialinApp.alipush;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.sdxxtop.zhujialinApp.ui.car_report.CarReportDetailActivity;
import com.sdxxtop.zhujialinApp.ui.guardianapp.EventReportDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Administrator on 2018/6/15.
 * 处理多个
 */

public class PushCenterActivity extends AppCompatActivity {

    private String extraMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariables();
    }

    protected void initVariables() {
        if (getIntent() != null) {
            extraMap = getIntent().getStringExtra("extraMap");
            if (!TextUtils.isEmpty(extraMap)) {
                startToAc(extraMap);
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            extraMap = intent.getStringExtra("extraMap");
            if (!TextUtils.isEmpty(extraMap)) {
                startToAc(extraMap);
            } else {
                finish();
            }
        }
    }

    private void startToAc(String extraMap) {
        try {
            JSONObject jsonObject = new JSONObject(extraMap);
            if (!jsonObject.has("type")) {
                finish();
                return;
            }
            int type = jsonObject.optInt("type");
            Intent intent;
            switch (type) {
                case 1:  // 人员事件
                    if (!jsonObject.has("event_id")) {
                        finish();
                        return;
                    }
                    intent = new Intent(this, EventReportDetailActivity.class);
                    intent.putExtra("eventId", String.valueOf(jsonObject.optInt("event_id")));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case 2:  // 人员事件
                    if (!jsonObject.has("event_id")) {
                        finish();
                        return;
                    }
                    intent = new Intent(this, CarReportDetailActivity.class);
                    intent.putExtra("eventId", jsonObject.optInt("event_id"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
            }

            finish();
        } catch (JSONException e) {
            Log.e("MyMessageReceiver", e.getMessage());
            e.printStackTrace();
            finish();
        }
    }

    public static void startActivityToReceiver(final Context context, String extraMap) {
        Intent intent = new Intent();
        intent.setClassName("com.sdxxtop.zhujialinApp", "com.sdxxtop.zhujialinApp.alipush.PushCenterActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("extraMap", extraMap);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
