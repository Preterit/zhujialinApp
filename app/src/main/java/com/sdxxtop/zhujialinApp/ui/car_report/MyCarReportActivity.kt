package com.sdxxtop.zhujialinApp.ui.car_report

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sdxxtop.zhujialinApp.R
import com.sdxxtop.zhujialinApp.base.KBaseActivity
import com.sdxxtop.zhujialinApp.databinding.ActivityMycarReportBinding
import com.sdxxtop.zhujialinApp.ui.car_report.data.CarIndexBean


/**
 * @author :  lwb
 * Date: 2019/8/16
 * Desc:
 */
class MyCarReportActivity : KBaseActivity<ActivityMycarReportBinding>() {

    var adapter = MyCarEventAdapter()

    override fun getLayoutId() = R.layout.activity_mycar_report

    override fun initView() {
        mBinding.vm = ViewModelProviders.of(this)[MyCarReportModel::class.java]
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.recyclerView.adapter = adapter

        mBinding.smartRefresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout?) {
                loadData(adapter.itemCount)
            }

            override fun onRefresh(refreshLayout: RefreshLayout?) {
                loadData(0)
            }
        })
    }

    override fun initObserver() {
        mBinding.vm?.carIndexBeanData?.observe(this, Observer<CarIndexBean> {
            mBinding.smartRefresh.finishRefresh()
            mBinding.smartRefresh.finishLoadMore()
            if (it.pageCount == 0) {
                adapter.replaceData(it.event)
            } else {
                adapter.addData(it.event)
            }
        })
    }

    override fun loadData(isRefresh: Boolean) {

    }

    fun loadData(pageSize: Int) {
        mBinding.vm?.loadData(2, pageSize)
    }

    override fun onResume() {
        super.onResume()
        loadData(0)
    }
}