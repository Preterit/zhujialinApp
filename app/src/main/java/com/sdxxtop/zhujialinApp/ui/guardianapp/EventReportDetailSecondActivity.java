package com.sdxxtop.zhujialinApp.ui.guardianapp;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdxxtop.ui.widget.TitleView;
import com.sdxxtop.zhujialinApp.R;
import com.sdxxtop.zhujialinApp.base.GBaseMvpActivity;
import com.sdxxtop.zhujialinApp.presenter.ERDSecondPresenter;
import com.sdxxtop.zhujialinApp.presenter.constract.ERDSecondContract;
import com.sdxxtop.zhujialinApp.widget.NumberEditTextView;
import com.sdxxtop.zhujialinApp.widget.SelectHoriPhotoView;
import com.sdxxtop.zhujialinApp.widget.SingleDataView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;

public class EventReportDetailSecondActivity extends GBaseMvpActivity<ERDSecondPresenter> implements ERDSecondContract.IView {

    //彻底检查完成
    public static final int TYPE_REPORT = 1;
    //反馈完成
    public static final int TYPE_FINISH = 2;
    //车辆上报完成
    public static final int TYPE_CAR_FINISH = 3;

    @BindView(R.id.tv_title)
    TitleView tvTitle;
    @BindView(R.id.iv_time_more)
    ImageView ivTimeMore;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.et_num_content)
    NumberEditTextView etNumContent;
    @BindView(R.id.shpv_view)
    SelectHoriPhotoView mShpvView;
    @BindView(R.id.btn_push)
    Button btnPush;

    @BindView(R.id.tv_content_title)
    TextView tvContentTitle;
    @BindView(R.id.rl_layout)
    RelativeLayout rlLayout;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.tv_photo_title)
    TextView tvPhotoTitle;

    private SingleDataView mSingleDataView;
    private String mEventId;
    private ArrayList<String> mList;
    private int mEventType;

    @Override
    protected int getLayout() {
        return R.layout.activity_event_report_detail_second;
    }

    @Override
    protected void initInject() {
        getMyActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {
        hideLoadingDialog();
    }

    @Override
    protected void initVariables() {
        super.initVariables();
        Intent intent = getIntent();
        if (intent != null) {
            mEventId = intent.getStringExtra("eventId");
            mEventType = intent.getIntExtra("eventType", TYPE_REPORT);
        }

        setTopViewPadding(tvTitle);
        if (mEventType == TYPE_FINISH || mEventType == TYPE_CAR_FINISH) {
            tvRemark.setText("解决问题的简要描述");
            rlLayout.setVisibility(View.GONE);
            tvTitle.setTitleValue("解决反馈");
            tvPhotoTitle.setVisibility(View.VISIBLE);
        } else {
            tvPhotoTitle.setVisibility(View.GONE);
        }


    }

    @OnClick({R.id.iv_time_more, R.id.tv_select, R.id.btn_push})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_time_more:
            case R.id.tv_select:
                showSelect();
                break;
            case R.id.btn_push:
                push();
                break;
        }
    }

    private void push() {
        boolean isFinish = (mEventType == TYPE_FINISH || mEventType == TYPE_CAR_FINISH);
        if (mList == null && !isFinish) { //是完成的状态的情况不进入这个选择
            showToast("请选择验收结果");
            return;
        }

        String editValue = etNumContent.getEditValue();
        if (TextUtils.isEmpty(editValue)) {
            editValue = "";
            showToast("请填写编辑内容");
            return;
        }

        List<File> imagePushPath = mShpvView.getImagePushPath();

        showLoadingDialog();
        if (isFinish) {
            mPresenter.modify(mEventId, 3, editValue, imagePushPath, mEventType);
        } else {  //走彻底完成的逻辑

            String selectType = tvSelect.getText().toString().trim();
            int i = mList.indexOf(selectType) + 4;

            mPresenter.modify(mEventId, i, editValue, imagePushPath, mEventType);
        }
    }

    private void showSelect() {
        if (mSingleDataView == null) {
            mList = new ArrayList<>();
            mList.add("验证通过");
            mList.add("验证不通过");

            mSingleDataView = new SingleDataView(this, mList);
        }

        mSingleDataView.setOnItemPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                tvSelect.setText(item);
            }
        });

        mSingleDataView.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mShpvView != null) {
            mShpvView.callActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void modifyRefresh() {
        showLoadingDialog();
        showToast("提交成功");
        setResult(200);
        finish();
    }
}
