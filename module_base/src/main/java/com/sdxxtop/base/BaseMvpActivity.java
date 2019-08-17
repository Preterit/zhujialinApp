package com.sdxxtop.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.sdxxtop.app.App;
import com.sdxxtop.di.component.ActivityComponent;
import com.sdxxtop.di.component.DaggerActivityComponent;
import com.sdxxtop.di.module.ActivityModule;
import com.sdxxtop.utils.ViewUtil;

import javax.inject.Inject;

import androidx.annotation.Nullable;

public abstract class BaseMvpActivity<T extends RxPresenter> extends BaseActivity implements BaseView {
    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent
                .builder()
                .appComponent(App.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    protected abstract void initInject();

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    /**
     * 控件往下移状态栏高度
     *
     * @param view
     */
    public void setTopViewPadding(View view) {
        view.setPadding(0, ViewUtil.getStatusHeight(this), 0, 0);
    }
}
