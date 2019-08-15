package com.sdxxtop.zhujialinApp.ui.policy

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sdxxtop.model.http.callback.IRequestCallback
import com.sdxxtop.model.http.net.Params
import com.sdxxtop.model.http.util.RxUtils
import com.sdxxtop.utils.UIUtils
import com.sdxxtop.zhujialinApp.base.BaseViewModel
import com.sdxxtop.zhujialinApp.extens.set
import com.sdxxtop.zhujialinApp.http.net.RetrofitHelper
import com.sdxxtop.zhujialinApp.ui.policy.data.Policy
import com.sdxxtop.zhujialinApp.ui.policy.data.PolicyBean
import com.sdxxtop.zhujialinApp.ui.policy.data.PolicyQueryBean

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-27 16:44
 * Version: 1.0
 * Description:
 */

class PolicyQueryListViewModel : BaseViewModel() {

    var mPolicy = MutableLiveData<List<Policy>>()
    var titleValue = ""
    var policy_num = 0;

    var isPullLoad = false
    var isSearch = false

    fun load() {
        val params = Params()
        val politicsSearch = RetrofitHelper.getGuardianService().postPolicyIndex(params.data)
        val disposable = RxUtils.handleDataHttp(politicsSearch, object : IRequestCallback<List<PolicyQueryBean>> {
            override fun onFailure(code: Int, error: String?) {
                UIUtils.showToast(error)
            }

            override fun onSuccess(t: List<PolicyQueryBean>?) {
//                mPolicyQueryData.set(t)
            }
        })

        addDisposable(disposable)
    }

    fun push(title: String?, mineId: Int, findId: Int, startPage: Int) {
        val params = Params()

        params.put("ih", 1)

        if (!TextUtils.isEmpty(title)) {
            params.put("tl", title)
            //1：不是，2：是
        }

        if (mineId != 0) {
            params.put("is", mineId)
        }

        if (findId != 0) {
            params.put("id", findId)
        }

        if (isSearch) {
            params.put("ih", 2)
        } else {
            params.put("ih", 1)
        }
        params.put("st", startPage)
        params.put("lt", 10)

        val eventShowPart = RetrofitHelper.getGuardianService().postPolicySearch(params.data)
        val disposable = RxUtils.handleDataHttp(eventShowPart, object : IRequestCallback<PolicyBean> {
            override fun onSuccess(t: PolicyBean?) {
                titleValue = t?.title ?: ""
                policy_num = t?.policy_num ?: 0
                mPolicy.set(t?.policy)
            }

            override fun onFailure(code: Int, error: String?) {
                UIUtils.showToast(error)
            }

        })

        addDisposable(disposable)
    }
}