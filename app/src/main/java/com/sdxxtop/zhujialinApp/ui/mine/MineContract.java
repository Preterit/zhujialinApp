package com.sdxxtop.zhujialinApp.ui.mine;

import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;
import com.sdxxtop.zhujialinApp.data.UcenterIndexBean;

/**
 * 用来copy使用的
 */
public interface MineContract {
    interface IView extends BaseView {
        void showList(UcenterIndexBean ucenterIndexBean);

        void changeIconSuccess();
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
