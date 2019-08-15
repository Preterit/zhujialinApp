package com.sdxxtop.zhujialinApp.ui.policy

import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import com.sdxxtop.zhujialinApp.R
import com.sdxxtop.zhujialinApp.base.KBaseActivity
import com.sdxxtop.zhujialinApp.databinding.ActivityPolicyQueryBinding
import com.sdxxtop.zhujialinApp.extens.toast
import com.sdxxtop.zhujialinApp.helper.adapter.HomePagerAdapter
import com.sdxxtop.zhujialinApp.ui.policy.data.PolicyQueryBean
import com.sdxxtop.zhujialinApp.ui.politics.PoliticsSearchActivity
import kotlinx.android.synthetic.main.fragment_policy_query_tab.*
import org.jetbrains.anko.startActivity

class PolicyQueryActivity : KBaseActivity<ActivityPolicyQueryBinding>() {

    val arrayList = listOf("企业政策", "个人政策")
    var fragmentList: List<PolicyQueryTabFragment>? = null;

    override fun initView() {
        mBinding.vm = bindViewModel(PolicyQueryViewModel::class.java)

        mBinding.etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    skip()
                    return true
                }
                return false
            }
        })
    }

    private fun skip() {
        val empty = mBinding.vm?.searchContent?.isEmpty()
        if (!empty!!) {
//            startActivity<PoliticsSearchActivity>("content" to mBinding.vm?.searchContent
//                    , "title" to "网络问政查询结果"
//                    , "isSearch" to true)
            startActivity<PolicyQueryListActivity>(
                    "title" to mBinding.vm?.searchContent,
                    "mineId" to 0, "findId" to 0, "isSearch" to true)
        }
    }

    override fun initObserver() {
        mBinding.vm?.mPolicyQueryData?.observe(this, Observer {
            setViewPager(it)
        })
    }

    private fun setViewPager(beanList: List<PolicyQueryBean>?) {

        if (beanList == null || beanList.size <= 1) {
            toast("获取数据格式有误")
            return
        }

        fragmentList = listOf(PolicyQueryTabFragment.newInstance(beanList[0], 0),
                PolicyQueryTabFragment.newInstance(beanList[1], 1))

        val homePagerAdapter = HomePagerAdapter(supportFragmentManager, arrayList, fragmentList)

        mBinding.vp.adapter = homePagerAdapter
        mBinding.tlTab.setupWithViewPager(mBinding.vp)


    }

    override fun onClick(v: View?) {
        fragmentList?.forEach {
            if (it.userVisibleHint) {
                var mineId = 0
                var findId = 0
                when (it.index) {
                    0 -> {
                        if (!"全部".equals(it.tv_mine.text)) {
                            mineId = it.twoPoint
                        } else {
                            mineId = 0
                        }

                        if (!"全部".equals(it.tv_find.text)) {
                            findId = it.threePoint
                        } else {
                            findId = 0
                        }
                    }
                    1 -> {
                        if (!"全部".equals(it.tv_mine.text)) {
                            mineId = it.twoPoint
                        } else {
                            mineId = 0
                        }

                        if (!"全部".equals(it.tv_find.text)) {
                            findId = it.threePoint
                        } else {
                            findId = 0
                        }
                    }
                }

                if ((mineId != 0 && findId == 0) || (mineId == 0 && findId != 0)) {
                    toast("我是和我找必须同时选择")
                    return
                }

                startActivity<PolicyQueryListActivity>(
                        "title" to mBinding.vm?.searchContent,
                        "mineId" to mineId, "findId" to findId
                        , "isSearch" to !TextUtils.isEmpty(mBinding.vm?.searchContent))
//                mBinding.vm?.push(mBinding.vm?.searchContent, mineId, findId)
            }
        }
    }

    override fun getLayoutId() = R.layout.activity_policy_query

    override fun loadData(isRefresh: Boolean) {
        mBinding.vm?.load()
    }

}
