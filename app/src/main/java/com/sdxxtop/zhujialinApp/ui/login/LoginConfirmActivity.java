package com.sdxxtop.zhujialinApp.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.sdxxtop.model.db.UserData;
import com.sdxxtop.zhujialinApp.R;
import com.sdxxtop.zhujialinApp.base.GBaseActivity;
import com.sdxxtop.zhujialinApp.base.GBaseMvpActivity;
import com.sdxxtop.zhujialinApp.presenter.LoginConfirmPresenter;
import com.sdxxtop.zhujialinApp.presenter.constract.LoginConfirmContract;
import com.sdxxtop.zhujialinApp.ui.home.HomeActivity;
import com.sdxxtop.zhujialinApp.ui.home.HomeTabActivity;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class LoginConfirmActivity extends GBaseMvpActivity<LoginConfirmPresenter> implements ViewTreeObserver.OnGlobalLayoutListener, LoginConfirmContract.IView {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_jobs)
    TextView tvJobs;
    @BindView(R.id.btn_enter)
    Button btnEnter;
    @BindView(R.id.civ_img)
    CircleImageView civImg;
    @BindView(R.id.btn_no_people)
    Button btnNoPeople;

    @BindView(R.id.tv_line3)
    TextView tvLine3;

    @BindView(R.id.lv_line1)
    LinearLayout lvLine1;
    @BindView(R.id.lv_line2)
    LinearLayout lvLine2;
    @BindView(R.id.lv_line4)
    LinearLayout lvLine4;


    private boolean isAdmin;
    private String phone;
    private String name;
    private String partName;
    private int position;
    private String ruleName;
    private String img;
    private int expireTime;
    private String autoToken;
    private int userid;
    private int partId;
    private int mType;

    @Override
    protected int getLayout() {
        return R.layout.activity_login_confirm;
    }

    @Override
    protected void initVariables() {
        super.initVariables();
        if (getIntent() != null) {
            isAdmin = getIntent().getBooleanExtra("isAdmin", false);
            phone = getIntent().getStringExtra("phone");
            name = getIntent().getStringExtra("name");
            partName = getIntent().getStringExtra("partName");
            position = getIntent().getIntExtra("position", 1);
            ruleName = getIntent().getStringExtra("ruleName");
            img = getIntent().getStringExtra("img");

            expireTime = getIntent().getIntExtra("expireTime", 0);
            partId = getIntent().getIntExtra("partId", 0);
            userid = getIntent().getIntExtra("userid", 0);
            autoToken = getIntent().getStringExtra("autoToken");
            mType = getIntent().getIntExtra("type", 2);
        }
    }

    @Override
    protected boolean isInitStatusBar() {
        return false;
    }

    @Override
    protected void initView() {
        super.initView();
        statusBar(true);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvName.setText(name);
        if (!TextUtils.isEmpty(phone)) {
            tvPhone.setText(phone);
        } else {
            tvPhone.setText("13333333333");
        }
        tvCompany.setText(partName);

        tvJobs.setText(ruleName);
//        handleJob(position);

        if (!TextUtils.isEmpty(img)) {
            Glide.with(mContext).load(img).into(civImg);
        }

        tvLine3.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    private void handleJob(int position) {
        //1:网格员 2: 企业员工 3:街道管理员 4:区级管理员
        String positionName = "";
        switch (position) {
            case 1:
                positionName = "网格员";
                break;
            case 2:
                positionName = "企业员工";
                break;
            case 3:
                positionName = "街道管理员";
                break;
            default:
                positionName = "区级管理员";
                break;
        }
        tvJobs.setText(positionName);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.loadData(userid);

            }
        });

        btnNoPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData.getInstance().logout();
                finish();
            }
        });
    }

    private void startActivity(int type) {
        notifyLoginFinish();
        Intent intent = new Intent(this, HomeTabActivity.class);
        intent.putExtra("isAdmin", type == 1);
        startActivity(intent);
        finish();
    }

    private void notifyLoginFinish() {
        Intent intent = new Intent(LoginActivity.ACTION_LOGIN_CONFIRM_SUCCESS);
        sendBroadcast(intent);
    }

    @Override
    public void onGlobalLayout() {
        int measuredWidth = tvLine3.getMeasuredWidth();
        Logger.e(measuredWidth + "");
        setLvLayoutParams(lvLine1, measuredWidth);
        setLvLayoutParams(lvLine2, measuredWidth);
        setLvLayoutParams(lvLine4, measuredWidth);

    }

    private void setLvLayoutParams(LinearLayout lvLine, int measuredWidth) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) lvLine.getLayoutParams();
        layoutParams.width = measuredWidth;
        lvLine.setLayoutParams(layoutParams);
    }

    @Override
    protected void initInject() {
        getMyActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void confirmSuccess() {
        UserData.getInstance().saveInfo(autoToken, expireTime, partId, userid, phone, mType);
        startActivity(mType);
    }
}
