package com.sdxxtop.zhujialinApp.ui.guardianapp;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.zhujialinApp.R;
import com.sdxxtop.zhujialinApp.base.GBaseMvpActivity;
import com.sdxxtop.zhujialinApp.helper.adapter.TaskAgentsAdapter;
import com.sdxxtop.zhujialinApp.presenter.TaskAgentsPresenter;
import com.sdxxtop.zhujialinApp.presenter.bean.EventIndexBean;
import com.sdxxtop.zhujialinApp.presenter.constract.TaskAgentsContract;

import java.util.List;

import butterknife.BindView;

/**
 * 待办任务
 */
public class TaskAgentsActivity extends GBaseMvpActivity<TaskAgentsPresenter> implements TaskAgentsContract.IView {
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.srl_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    private TaskAgentsAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_task_agents;
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TaskAgentsAdapter(R.layout.item_task_agents_recycler);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        super.initData();
//        mPresenter.loadData(0);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (mPresenter != null) {
                    mPresenter.loadData(mAdapter.getData().size(), 1);
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (mPresenter != null) {
                    mPresenter.loadData(0, 1);
                }
            }
        });

        mSmartRefreshLayout.autoRefresh();
    }

    @Override
    protected void initInject() {
        getMyActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showData(int page, EventIndexBean eventIndexBean) {
        List<EventIndexBean.EventBean> event = eventIndexBean.getEvent();
        if (event == null) {
            return;
        }

        if (page == 0 && event.size() != 0) {
            mAdapter.replaceData(event);
        }

        if (page != 0) {
            mAdapter.addData(event);
        }

        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.finishRefresh();
            mSmartRefreshLayout.finishLoadMore();
        }
    }
}
