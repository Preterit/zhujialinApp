package com.sdxxtop.zhujialinApp.ui.splash;


import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;
import com.sdxxtop.zhujialinApp.data.AutoLoginBean;

public interface SplashContract {
    interface IView extends BaseView {
        void autoSuccess(AutoLoginBean autoLoginBean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
