package com.sdxxtop.zhujialinApp.presenter.constract;


import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;
import com.sdxxtop.zhujialinApp.data.EventSearchTitleBean;
import com.sdxxtop.zhujialinApp.data.ShowPartBean;

import java.util.List;

public interface EventReportContract {
    interface IView extends BaseView {
        void pushSuccess(String eventId);

        void showPart(List<ShowPartBean.PartBean> par);

        void showSearchData(EventSearchTitleBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
