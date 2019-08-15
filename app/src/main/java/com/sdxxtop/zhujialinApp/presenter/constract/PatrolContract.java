package com.sdxxtop.zhujialinApp.presenter.constract;

import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;
import com.sdxxtop.zhujialinApp.data.SignLogBean;

public interface PatrolContract {
    interface IView extends BaseView {
        void showData(SignLogBean signLogBean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
