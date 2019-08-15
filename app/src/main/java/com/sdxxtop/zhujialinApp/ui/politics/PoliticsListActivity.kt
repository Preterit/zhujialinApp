package com.sdxxtop.zhujialinApp.ui.politics

import com.sdxxtop.zhujialinApp.R
import com.sdxxtop.zhujialinApp.base.KBaseActivity
import com.sdxxtop.zhujialinApp.databinding.ActivityPoliticsListBinding
import kotlinx.android.synthetic.main.activity_politics_list.*

class PoliticsListActivity : KBaseActivity<ActivityPoliticsListBinding>() {

    var politicsId = 0


    override fun getLayoutId() = R.layout.activity_politics_list


    override fun initView() {
        politicsId = intent.getIntExtra("politicsId", 0)
    }


    override fun loadData(isRefresh: Boolean) {
    }
}
