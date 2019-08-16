package com.sdxxtop.zhujialinApp.ui.car_report

import androidx.lifecycle.MutableLiveData
import com.sdxxtop.model.http.callback.IRequestCallback
import com.sdxxtop.model.http.net.Params
import com.sdxxtop.model.http.util.RxUtils
import com.sdxxtop.utils.UIUtils
import com.sdxxtop.zhujialinApp.base.BaseViewModel
import com.sdxxtop.zhujialinApp.http.net.RetrofitHelper
import com.sdxxtop.zhujialinApp.ui.car_report.data.CarIndexBean

/**
 * @author :  lwb
 * Date: 2019/8/16
 * Desc:
 */
class MyCarReportModel : BaseViewModel() {

    val carIndexBeanData = MutableLiveData<CarIndexBean>()

    fun loadData(type: Int, itemCount: Int) {
        val params = Params()
        params.put("et", type)
        params.put("sp", itemCount)

        val politicsSearch = RetrofitHelper.getGuardianService().postCarReportData(params.data)
        val disposable = RxUtils.handleDataHttp(politicsSearch, object : IRequestCallback<CarIndexBean> {
            override fun onSuccess(t: CarIndexBean?) {
                t?.pageCount = itemCount
                carIndexBeanData.value = t
            }

            override fun onFailure(code: Int, error: String?) {
                UIUtils.showToast(error)
            }
        })
        addDisposable(disposable)
    }
}