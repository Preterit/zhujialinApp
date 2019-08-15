package com.sdxxtop.zhujialinApp.ui.server_people

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sdxxtop.model.bean.RequestBean
import com.sdxxtop.model.http.callback.IRequestCallback
import com.sdxxtop.model.http.net.Params
import com.sdxxtop.model.http.util.RxUtils
import com.sdxxtop.utils.UIUtils
import com.sdxxtop.zhujialinApp.base.BaseViewModel
import com.sdxxtop.zhujialinApp.data.RegisterBean
import com.sdxxtop.zhujialinApp.data.ServerPeopleBean
import com.sdxxtop.zhujialinApp.http.net.RetrofitHelper

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-22 11:31
 * Version: 1.0
 * Description:
 */
class ServerPeopleViewModel : BaseViewModel() {
    val serverPeopleData = MutableLiveData<ServerPeopleBean>()

    fun load() {
        val params = Params()

        val observable = RetrofitHelper.getGuardianService().postIndexTest(params.data)
        val disposable = RxUtils.handleDataHttp(observable, object : IRequestCallback<ServerPeopleBean> {
            override fun onSuccess(t: ServerPeopleBean?) {
                serverPeopleData.value = t
            }

            override fun onFailure(code: Int, error: String) {
                UIUtils.showToast(error)
            }
        })

        addDisposable(disposable)
    }


}