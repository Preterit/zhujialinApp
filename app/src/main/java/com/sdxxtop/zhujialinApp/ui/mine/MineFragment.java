package com.sdxxtop.zhujialinApp.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sdxxtop.app.Constants;
import com.sdxxtop.model.db.UserData;
import com.sdxxtop.ui.dialog.IosAlertDialog;
import com.sdxxtop.ui.widget.TitleView;
import com.sdxxtop.utils.SpUtil;
import com.sdxxtop.utils.UIUtils;
import com.sdxxtop.zhujialinApp.R;
import com.sdxxtop.zhujialinApp.base.GBaseMvpFragment;
import com.sdxxtop.zhujialinApp.data.UcenterIndexBean;
import com.sdxxtop.zhujialinApp.ui.feedback.FeedbackActivity;
import com.sdxxtop.zhujialinApp.ui.feedback.FeedbackSearchActivity;
import com.sdxxtop.zhujialinApp.ui.login.LoginActivity;
import com.sdxxtop.zhujialinApp.ui.politics.PoliticsSearchActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MineFragment extends GBaseMvpFragment<MinePresenter> implements MineContract.IView {
    @BindView(R.id.tv_title)
    TitleView mTitleView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.civ_header)
    CircleImageView civHeader;

    private boolean isAdmin;

    private int IMAGE_STORE = 100;
    private String mPartName;
    private String partUnit; //什么单位

    public static MineFragment newInstance(boolean isAdmin) {

        Bundle args = new Bundle();
        args.putBoolean("isAdmin", isAdmin);
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        super.initView();
        statusBar(false);
        if (getArguments() != null) {
            isAdmin = getArguments().getBoolean("isAdmin");
        }

        topViewPadding(mTitleView);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mTitleView.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadData();
    }

    private void logoutDialog() {
        new IosAlertDialog(getActivity()).builder()
                .setHeightMsg("确定退出吗?")
                .setPositiveButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logout();
                    }
                })
                .setNegativeButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            statusBar(false);
            mPresenter.loadData();
        }
    }

    public void logout() {
        UserData.getInstance().logout();

        Intent intent = new Intent(getContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }

    @Override
    protected void initInject() {
        getGFragmentComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showList(UcenterIndexBean indexBean) {
        String img = indexBean.getImg();
        if (!TextUtils.isEmpty(img)) {
            SpUtil.putString(Constants.USER_IMG, img);
            Glide.with(this).load(img).into(civHeader);
        }
        tvName.setText(indexBean.getName());
        tvPlace.setText(new StringBuilder().append(indexBean.getPart_name()).append(" ").append(indexBean.getStringPosition()));
        mPartName = indexBean.getPart_name();
        int type = indexBean.getType();
        switch (type) { //1:区级 2: 乡镇 3:企业
            case 1:
                partUnit = "区级单位";
                break;
            case 2:
                partUnit = "乡镇单位";
                break;
            default:
                partUnit = "企业单位";
                break;

        }
    }

    @OnClick({R.id.civ_header, R.id.tatv_message, R.id.tatv_report,
            R.id.event_report, R.id.grid_member_report, R.id.company_report
    ,R.id.ll_politics, R.id.ll_jianyi, R.id.ll_find})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civ_header:
                PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
                        .cropCompressQuality(90)
                        .minimumCompressSize(200)
                        .withAspectRatio(1, 1)
                        .enableCrop(true)
                        .showCropFrame(false)
                        .showCropGrid(false)
                        .circleDimmedLayer(true)
                        .compress(true).selectionMode(PictureConfig.SINGLE).maxSelectNum(1).forResult(IMAGE_STORE);

                break;
            case R.id.ll_politics: // 我的问政
                Intent politicsSearchIntent = new Intent(getActivity(), PoliticsSearchActivity.class);
                politicsSearchIntent.putExtra("content", "");
                politicsSearchIntent.putExtra("title", "我的网络问政");
                startActivity(politicsSearchIntent);
//                new QuitGroupPopView(getActivity(), mRootView, mPartName, partUnit);
                break;
            case R.id.ll_jianyi: // 我的意见反馈

                Intent feedbackIntent = new Intent(getActivity(), FeedbackSearchActivity.class);
                feedbackIntent.putExtra("content", "");
                feedbackIntent.putExtra("title", "意见建议及投诉");
                feedbackIntent.putExtra("isSearch", false);
                startActivity(feedbackIntent);

                break;
            case R.id.ll_find: // 服务进度

                showFindDialog();

                break;
            default:
                break;
//            case R.id.tatv_report:
//                startActivity(new Intent(getActivity(), EventReportListActivity.class));
//                break;
//            case R.id.event_report:
//                startActivity(new Intent(getActivity(), GrantEventReportActivity.class));
//                break;
//            case R.id.grid_member_report: // 网格员报告
//                Intent gridMemberIntent = new Intent(getActivity(), GrantCompanyReportActivity.class);
//                gridMemberIntent.putExtra("reportType",1);
//                startActivity(gridMemberIntent);
//                break;
//            case R.id.company_report:     // 企业报告
//                Intent CompanyIntent = new Intent(getActivity(), GrantCompanyReportActivity.class);
//                CompanyIntent.putExtra("reportType",2);
//                startActivity(CompanyIntent);
//                break;
        }
    }

    private void showFindDialog() {
        new IosAlertDialog(getActivity())
                .builder()
                .setTitle("提示")
                .setMsg("暂时没有进行中的服务")
                .setPositiveButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_STORE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList != null && selectList.size() > 0) {
                    LocalMedia media = selectList.get(0);
                    String path = media.getCompressPath();
                    if (TextUtils.isEmpty(path)) {
                        path = media.getCutPath();
                    }
                    if (!TextUtils.isEmpty(path)) {
                        mPresenter.changeIcon(path);
                    } else {
                        UIUtils.showToast("上传头像失败");
                    }
                }
            }
        }
    }

    @Override
    public void changeIconSuccess() {
        mPresenter.loadData();
    }
}
