package com.sdxxtop.zhujialinApp.ui.login;

import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;
import com.sdxxtop.zhujialinApp.data.LoginBean;

public interface LoginContract {
    interface IView extends BaseView {
        void loginSuccess(LoginBean loginBean);
        void sendCodeSuccess();
        void sendCodeError();
    }

    interface IPresenter extends BasePresenter<IView> {
        void login(String mobile, String authCode, String partId);
    }
}
