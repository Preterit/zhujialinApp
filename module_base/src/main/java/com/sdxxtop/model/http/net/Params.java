package com.sdxxtop.model.http.net;

import android.content.Context;
import android.text.TextUtils;

import com.sdxxtop.app.AppSession;
import com.sdxxtop.app.Constants;
import com.sdxxtop.model.http.util.DeviceUtil;
import com.sdxxtop.model.http.util.NetUtil;
import com.sdxxtop.utils.SpUtil;
import com.sdxxtop.utils.StringUtil;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/5/7.
 */

public class Params {
    protected Context context;
    protected HashMap<String, String> map;

    public Params() {
        map = new HashMap<>();
        context = AppSession.getInstance().getContext();
        defaultValue();
    }

    private void defaultValue() {
        put("ui", SpUtil.getInt(Constants.USER_ID, 0));
        put("pi", SpUtil.getInt(Constants.PART_ID, 0));
        put("tp", SpUtil.getInt(Constants.TYPE, 2));
    }

    public String getUserId() {
        String ui = map.get("ui");
        if (TextUtils.isEmpty(ui)) {
            ui = String.valueOf(SpUtil.getInt(Constants.USER_ID, 0));
        }
        return ui;
    }

    public String getPartId() {
        String pi = map.get("ci");
        if (TextUtils.isEmpty(pi)) {
            pi = String.valueOf(SpUtil.getInt(Constants.PART_ID, 0));
        }
        return pi;
    }

    public void removeKey(String key) {
        if (map.containsKey(key)) {
            map.remove(key);
        }
    }

    public void put(String key, String value) {
        map.put(key, StringUtil.stringNotNull(value));
    }

    public void put(String key, long value) {
        map.put(key, value + "");
    }

    public void put(String key, int value) {
        map.put(key, value + "");
    }

    public String getData() {
        return NetUtil.getBase64Data(map);
    }

    public void putDeviceNo() {
        map.put("dn", DeviceUtil.getDeviceNo(context));
    }
}
