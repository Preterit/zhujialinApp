package com.sdxxtop.zhujialinApp.presenter;

import com.sdxxtop.zhujialinApp.presenter.constract.CopyContract;
import com.sdxxtop.base.RxPresenter;

import javax.inject.Inject;

/**
 * 用来copy使用的
 */
public class CopyPresenter extends RxPresenter<CopyContract.IView> implements CopyContract.IPresenter {
    @Inject
    public CopyPresenter() {
    }

    public void loadData() {
        mView.showError("aa");
    }
}
