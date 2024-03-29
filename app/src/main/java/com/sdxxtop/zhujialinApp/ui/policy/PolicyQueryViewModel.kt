package com.sdxxtop.zhujialinApp.ui.policy

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.model.http.callback.IRequestCallback
import com.sdxxtop.model.http.net.Params
import com.sdxxtop.model.http.util.RxUtils
import com.sdxxtop.utils.UIUtils
import com.sdxxtop.zhujialinApp.base.BaseViewModel
import com.sdxxtop.zhujialinApp.extens.set
import com.sdxxtop.zhujialinApp.http.net.RetrofitHelper
import com.sdxxtop.zhujialinApp.ui.policy.data.PolicyBean
import com.sdxxtop.zhujialinApp.ui.policy.data.PolicyQueryBean

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-27 14:12
 * Version: 1.0
 * Description:
 */
class PolicyQueryViewModel : BaseViewModel() {
    var searchContent = ""
    val mPolicyQueryData = MutableLiveData<List<PolicyQueryBean>>()

    fun load() {
        val params = Params()
        val politicsSearch = RetrofitHelper.getGuardianService().postPolicyIndex(params.data)
        val disposable = RxUtils.handleDataHttp(politicsSearch, object : IRequestCallback<List<PolicyQueryBean>> {
            override fun onFailure(code: Int, error: String?) {
                UIUtils.showToast(error)
            }

            override fun onSuccess(t: List<PolicyQueryBean>?) {
                mPolicyQueryData.set(t)
            }
        })

        addDisposable(disposable)
    }

    fun push(title: String?, mineId: Int, findId: Int) {
        val params = Params()

        params.put("ih", 1)

        if (!TextUtils.isEmpty(title)) {
            params.put("tl", title)
            //1：不是，2：是
            params.put("ih", 2)
        }

        if (mineId != 0) {
            params.put("is", mineId)
            params.put("ih", 2)
        }

        if (findId != 0) {
            params.put("id", findId)
            params.put("ih", 2)
        }

        params.put("st", 0)
        params.put("lt", 0)

        val eventShowPart = RetrofitHelper.getGuardianService().postPolicySearch(params.data)
        val disposable = RxUtils.handleDataHttp(eventShowPart, object : IRequestCallback<PolicyBean> {
            override fun onSuccess(t: PolicyBean?) {

            }

            override fun onFailure(code: Int, error: String?) {
                UIUtils.showToast(error)
            }

        })

        addDisposable(disposable)
    }
}