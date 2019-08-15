package com.sdxxtop.zhujialinApp.ui.home


import android.view.Menu
import com.sdxxtop.zhujialinApp.R
import com.sdxxtop.zhujialinApp.base.KBaseActivity
import com.sdxxtop.zhujialinApp.databinding.ActivityHomeBinding
import com.sdxxtop.zhujialinApp.extens.toast
import android.view.MenuItem


class HomeActivity : KBaseActivity<ActivityHomeBinding>() {

    override fun getLayoutId() = com.sdxxtop.zhujialinApp.R.layout.activity_home

    override fun loadData(isRefresh: Boolean) {

    }

    override fun initView() {
        var toolbar = mBinding.toolbar
        toolbar.title = ""

        setSupportActionBar(toolbar)

//        toolbar.setOnClickListener { toast("asfasfd") }

        toolbar.setNavigationOnClickListener { view ->
            toast("fanhui")
        }

        toolbar.setOnMenuItemClickListener { menuItem ->
            toast("afdaf")
            return@setOnMenuItemClickListener false
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.right_icon, menu)
        return true
    }


}
