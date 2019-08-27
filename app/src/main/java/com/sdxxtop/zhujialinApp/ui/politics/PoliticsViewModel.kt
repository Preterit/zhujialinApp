package com.sdxxtop.zhujialinApp.ui.politics

import androidx.lifecycle.MutableLiveData
import com.sdxxtop.model.http.callback.IRequestCallback
import com.sdxxtop.model.http.net.ImageParams
import com.sdxxtop.model.http.net.Params
import com.sdxxtop.model.http.util.RxUtils
import com.sdxxtop.utils.UIUtils
import com.sdxxtop.zhujialinApp.base.BaseViewModel
import com.sdxxtop.zhujialinApp.data.PartBean
import com.sdxxtop.zhujialinApp.data.PartList
import com.sdxxtop.zhujialinApp.data.PushDataBean
import com.sdxxtop.zhujialinApp.http.net.RetrofitHelper
import java.io.File

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-22 16:37
 * Version: 1.0
 * Description:
 */
class PoliticsViewModel : BaseViewModel() {


    var searchContent = ""
    var isOpen = false

    var partBean: ArrayList<PartBean>? = null
    val pushSuccess = MutableLiveData<String>()


    fun load() {
        val params = Params()
        val eventShowPart = RetrofitHelper.getGuardianService().postEventShowPart(params.data)
        val disposable = RxUtils.handleDataHttp(eventShowPart, object : IRequestCallback<PartList> {
            override fun onSuccess(t: PartList?) {
                t?.data?.add(0, PartBean(0, "我不知道部门", false))
                partBean = t?.data
            }

            override fun onFailure(code: Int, error: String?) {
                UIUtils.showToast(error)
            }

        })
//        val disposable = RxUtils.handleHttp(eventShowPart, object : IRequestCallback<RequestBean<Any>> {
//            override fun onSuccess(t: RequestBean<Any>?) {
//            }
//
//            override fun onFailure(code: Int, error: String?) {
//            }
//
//        })

        addDisposable(disposable)
    }

    fun pushPolitics(partId: Int, titleValue: String, contentValue: String, imgList: List<File>) {
        val params = ImageParams()
        params.put("rd", partId)
        params.put("tl", titleValue)
        //是否匿名
        params.put("tp", if (isOpen) 0 else 1)
        params.put("ct", contentValue)
//        params.put("ct", contentValue)
        params.addImagePathList("img[]", imgList)

        val eventShowPart = RetrofitHelper.getGuardianService().postPoliticsConfirm(params.imgData)
        val disposable = RxUtils.handleDataHttp(eventShowPart, object : IRequestCallback<PushDataBean> {
            override fun onSuccess(t: PushDataBean?) {
                pushSuccess.value = t?.url
            }

            override fun onFailure(code: Int, error: String?) {
                UIUtils.showToast(error)
            }

        })
        addDisposable(disposable)
    }
}