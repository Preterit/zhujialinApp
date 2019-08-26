package com.sdxxtop.zhujialinApp.ui.car_report

import androidx.lifecycle.MutableLiveData
import com.sdxxtop.model.bean.RequestBean
import com.sdxxtop.model.http.callback.IRequestCallback
import com.sdxxtop.model.http.net.ImageParams
import com.sdxxtop.model.http.net.Params
import com.sdxxtop.model.http.util.RxUtils
import com.sdxxtop.utils.UIUtils
import com.sdxxtop.zhujialinApp.base.BaseViewModel
import com.sdxxtop.zhujialinApp.data.CarTypeBean
import com.sdxxtop.zhujialinApp.data.PartBean
import com.sdxxtop.zhujialinApp.extens.set
import com.sdxxtop.zhujialinApp.http.net.RetrofitHelper
import java.io.File

/**
 * @author :  lwb
 * Date: 2019/8/15
 * Desc:
 */
class CarReportModel : BaseViewModel() {

    val mPartList = MutableLiveData<ArrayList<PartBean>>()
    val mCarTypeList = MutableLiveData<ArrayList<CarTypeBean>>()
    val addReprtSuccess = MutableLiveData<Boolean>()

    fun loadArea() {
        val params = Params()
        val politicsSearch = RetrofitHelper.getGuardianService().postEventShowPart2(params.data)
        val disposable = RxUtils.handleDataHttp(politicsSearch, object : IRequestCallback<ArrayList<PartBean>> {
            override fun onFailure(code: Int, error: String?) {
                UIUtils.showToast(error)
            }

            override fun onSuccess(t: ArrayList<PartBean>?) {
                mPartList.set(t)
            }
        })
        addDisposable(disposable)
    }

    fun addReport(carNum: String, name: String, phone: String, address: String, loglng: String, describes: String, reportId: Int, imagePushPath: List<File>, mSelectCarType: Int) {
        val imageParams = ImageParams()
        imageParams.put("cne", carNum)
        imageParams.put("cdr", name)
        imageParams.put("dpe", phone)
        imageParams.put("as", address)
        imageParams.put("lg", loglng)
        imageParams.put("ds", describes)
        imageParams.put("rd", reportId)
        imageParams.put("ci", mSelectCarType)
        imageParams.addImagePathList("img[]", imagePushPath)

        val observable = RetrofitHelper.getGuardianService().postCarReport(imageParams.imgData)
        val disposable = RxUtils.handleHttp(observable, object : IRequestCallback<RequestBean<*>> {
            override fun onSuccess(requestBean: RequestBean<*>) {
                addReprtSuccess.set(true)
            }

            override fun onFailure(code: Int, error: String) {
                UIUtils.showToast(error)
            }
        })
        addDisposable(disposable)
    }

    fun loadCarType() {
        val params = Params()
        val politicsSearch = RetrofitHelper.getGuardianService().postCarType(params.data)
        val disposable = RxUtils.handleDataHttp(politicsSearch, object : IRequestCallback<ArrayList<CarTypeBean>> {
            override fun onSuccess(t: ArrayList<CarTypeBean>?) {
                mCarTypeList.set(t)
            }

            override fun onFailure(code: Int, error: String?) {
                UIUtils.showToast(error)
            }
        })
        addDisposable(disposable)
    }
}