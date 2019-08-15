package com.sdxxtop.zhujialinApp.presenter.constract;

import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;
import com.sdxxtop.zhujialinApp.presenter.bean.MainIndexBean;

public interface HomeFragmentContract {
    interface IView extends BaseView {
        void showData(MainIndexBean data);

        void showInfo();
    }

    interface IPresenter extends BasePresenter<IView> {
        void loadData();

        /**
         * 加载签名的
         */
        void loadSignData();


        void loadInfo();
    }
}
