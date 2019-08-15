package com.sdxxtop.zhujialinApp.ui.login

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sdxxtop.app.Constants
import com.sdxxtop.utils.SpUtil
import com.sdxxtop.zhujialinApp.R
import com.sdxxtop.zhujialinApp.base.KBaseActivity
import com.sdxxtop.zhujialinApp.databinding.ActivityRegisterBinding
import com.sdxxtop.zhujialinApp.ui.home.HomeTabActivity
import org.jetbrains.anko.startActivity

class RegisterActivity : KBaseActivity<ActivityRegisterBinding>() {
    override fun initView() {
        val phone = intent.getStringExtra("phone")
        mBinding.vm = ViewModelProviders.of(this)[RegisterViewModel::class.java]
        mBinding.vm?.phone = phone;
        mBinding.vm?.username = ""

        mBinding.vm?.registerLiveData?.observe(this, Observer {
            hideLoadingDialog()
            //保存phone
            SpUtil.putString(Constants.MOBILE, phone)
            startActivity<HomeTabActivity>()

            notifyLoginFinish()
            finish()
        })

        mBinding.vm?.registerErrorLiveData?.observe(this, Observer {
            hideLoadingDialog()
        })

    }

    override fun onResume() {
        super.onResume()
        mBinding.etPhone.setSelection(mBinding.etPhone.text.length);
    }

    override fun getLayoutId() = R.layout.activity_register

    override fun loadData(isRefresh: Boolean) {

    }

    override fun onClick(v: View?) {
        when (v) {
            mBinding.btnLogin -> {
                showLoadingDialog()
                mBinding.vm?.load()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.vm?.remove()
    }

    private fun notifyLoginFinish() {
        val intent = Intent(LoginActivity.ACTION_LOGIN_CONFIRM_SUCCESS)
        sendBroadcast(intent)
    }

}
