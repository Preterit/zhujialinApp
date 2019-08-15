package com.sdxxtop.zhujialinApp.presenter.constract;

import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;
import com.sdxxtop.zhujialinApp.data.ContactIndexBean;

import java.util.List;

public interface ContactContract {
    interface IView extends BaseView {
        void showList(List<ContactIndexBean.ContactBean> contactBean);

        void showSearchList(List<ContactIndexBean.ContactBean> contactBean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
