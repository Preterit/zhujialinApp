package com.sdxxtop.zhujialinApp.presenter;

import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.model.http.callback.IRequestCallback;
import com.sdxxtop.model.http.net.ImageParams;
import com.sdxxtop.model.http.util.RxUtils;
import com.sdxxtop.utils.UIUtils;
import com.sdxxtop.zhujialinApp.base.GRxPresenter;
import com.sdxxtop.zhujialinApp.presenter.constract.ERDSecondContract;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ERDSecondPresenter extends GRxPresenter<ERDSecondContract.IView> implements ERDSecondContract.IPresenter {
    @Inject
    public ERDSecondPresenter() {
    }

    public void modify(String eventId, int status, String extra, List<File> imagePushPath, int eventType) {
        ImageParams params = new ImageParams();
        params.put("ei", eventId);
        params.put("st", status);
        params.put("et", extra);

        params.addImagePathList("img[]", imagePushPath);
        Observable<RequestBean> observable = null;
        if (eventType == 3) {
            observable = getService().postCarModify(params.getImgData());
        } else {
            observable = getService().postEventModify(params.getImgData());
        }

        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean requestBean) {
                mView.modifyRefresh();
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
                mView.showError(error);
            }
        });
        addSubscribe(disposable);
    }
}
