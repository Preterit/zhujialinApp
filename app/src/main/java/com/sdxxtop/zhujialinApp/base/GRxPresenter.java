package com.sdxxtop.zhujialinApp.base;

import com.sdxxtop.base.BaseView;
import com.sdxxtop.base.RxPresenter;
import com.sdxxtop.zhujialinApp.http.GuardianService;
import com.sdxxtop.zhujialinApp.http.net.RetrofitHelper;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-16 10:48
 * Version: 1.0
 * Description:
 */
public abstract class GRxPresenter<T extends BaseView> extends RxPresenter<T> {

    public GuardianService getService() {
        return RetrofitHelper.getGuardianService();
    }
}
