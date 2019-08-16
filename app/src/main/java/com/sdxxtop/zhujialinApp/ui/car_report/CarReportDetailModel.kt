package com.sdxxtop.zhujialinApp.ui.car_report

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.model.http.callback.IRequestCallback
import com.sdxxtop.model.http.net.Params
import com.sdxxtop.model.http.util.RxUtils
import com.sdxxtop.utils.UIUtils
import com.sdxxtop.zhujialinApp.base.BaseViewModel
import com.sdxxtop.zhujialinApp.http.net.RetrofitHelper
import com.sdxxtop.zhujialinApp.ui.car_report.data.CarReportDetailBean

/**
 * @author :  lwb
 * Date: 2019/8/16
 * Desc:
 */
class CarReportDetailModel : BaseViewModel() {

    val detailBean = ObservableField<CarReportDetailBean>()
    val detailBeanInfo = MutableLiveData<CarReportDetailBean>()

    fun loadData(eventId: Int) {
        val params = Params()
        params.put("ei", eventId)

        val politicsSearch = RetrofitHelper.getGuardianService().postCarReportDetail(params.data)
        val disposable = RxUtils.handleDataHttp(politicsSearch, object : IRequestCallback<CarReportDetailBean> {
            override fun onSuccess(t: CarReportDetailBean?) {
                detailBeanInfo.value = t
                detailBean.set(t)
            }

            override fun onFailure(code: Int, error: String?) {
                UIUtils.showToast(error)
            }
        })
        addDisposable(disposable)
    }
}