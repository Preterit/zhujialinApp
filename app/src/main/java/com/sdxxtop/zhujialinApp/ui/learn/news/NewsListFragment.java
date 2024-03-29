package com.sdxxtop.zhujialinApp.ui.learn.news;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sdxxtop.ui.widget.TitleView;
import com.sdxxtop.zhujialinApp.R;
import com.sdxxtop.zhujialinApp.base.GBaseMvpFragment;
import com.sdxxtop.zhujialinApp.data.LearnNewsBean;
import com.sdxxtop.zhujialinApp.helper.adapter.NewsListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewsListFragment extends GBaseMvpFragment<NewsListFragmentPresenter> implements NewsListFragmentContract.IView {
    private static final String TAG = "NewsListFragment";

    @BindView(R.id.tv_title)
    TitleView mTitleView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smart_refresh;

    private NewsListAdapter adapter;
    /******  1:新闻学习  2:罗庄发布  *******/
    private int type = 1;
    /*********  1:下拉刷新,2:上拉加载  *********/
    private int isLoadMore = 1;

    private boolean isFirstLoading;


    public static NewsListFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initInject() {
        getGFragmentComponent().inject(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            type = getArguments().getInt("type");
//            mPresenter.loadData(0,type);
        }

        statusBar(true);
        topViewPadding(mTitleView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsListAdapter(new ArrayList<>(), getContext(), type);
        recyclerView.setAdapter(adapter);
        //刷新
        smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isLoadMore = 1;
                mPresenter.loadData(0, type);
                refreshlayout.finishRefresh();
            }
        });
        //加载更多
        smart_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                isLoadMore = 2;
                int itemCount = 0;
                if (adapter != null) {
                    itemCount = adapter.getItemCount();
                }
                mPresenter.loadData(itemCount, type);
                refreshLayout.finishLoadMore();
            }
        });

        if (getUserVisibleHint()) {
            loadData();
            Logger.e("setUserVisibleHint1" , type);
        }
    }

    private void loadData() {
        if (isFirstLoading) {
            return;
        }
        isFirstLoading = true;
        showLoadingDialog();
        mPresenter.loadData(0, type);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mPresenter != null) {
            loadData();
            Logger.e("setUserVisibleHint2" , type);
        }
    }

    @Override
    public void showData(List<LearnNewsBean> data) {
        if (isLoadMore == 2) {
            adapter.addData(data);
        } else {
            adapter.replaceData(data);
        }

        closeLoadingDialog();
    }

    @Override
    public void showError(String error) {
        closeLoadingDialog();
    }
}
