package com.sdxxtop.zhujialinApp.presenter.constract;


import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;
import com.sdxxtop.zhujialinApp.presenter.bean.EventIndexBean;

public interface TaskAgentsContract {
    interface IView extends BaseView {
        void showData(int page, EventIndexBean eventIndexBean);
    }

    interface IPresenter extends BasePresenter<IView> {

        void loadData(int page, int type);
    }
}
