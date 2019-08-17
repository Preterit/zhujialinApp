package com.sdxxtop.zhujialinApp.ui.car_report

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.text.InputFilter
import android.text.InputType
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.luck.picture.lib.permissions.RxPermissions
import com.sdxxtop.ui.dialog.IosAlertDialog
import com.sdxxtop.utils.AMapFindLocation2
import com.sdxxtop.zhujialinApp.R
import com.sdxxtop.zhujialinApp.base.KBaseActivity
import com.sdxxtop.zhujialinApp.data.PartBean
import com.sdxxtop.zhujialinApp.databinding.ActivityCarReportBinding
import com.sdxxtop.zhujialinApp.extens.toast
import com.sdxxtop.zhujialinApp.ui.guardianapp.AmapPoiActivity
import com.sdxxtop.zhujialinApp.widget.SingleStyleView
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_car_report.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.textColor

/**
 * @author :  lwb
 * Date: 2019/8/15
 * Desc:
 */
class CarReportActivity : KBaseActivity<ActivityCarReportBinding>() {

    var lonLng: String? = null
    private var singleReportPathDataView = SingleStyleView(this)
    var mSelectPartId = -1

    override fun getLayoutId() = R.layout.activity_car_report

    @SuppressLint("CheckResult")
    override fun initView() {
        mBinding.vm = ViewModelProviders.of(this)[CarReportModel::class.java]
        mBinding.tatvPhone.editText.inputType = InputType.TYPE_CLASS_PHONE
        mBinding.tatvPhone.editText.filters = arrayOf(InputFilter.LengthFilter(11))
//        mBinding.taevCarNum.editText.keyListener = DigitsKeyListener.getInstance(getString(R.string.text_digits))
        mBinding.netContent.setEditHint(" ")

        val intent = Intent(this, MyCarReportActivity::class.java)
        mBinding.tvTitle.tvRight.onClick { startActivity(intent) }

        val mRxPermissions = RxPermissions(this)
        mRxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(Consumer<Boolean> { aBoolean ->
            if (aBoolean) {
                val instance = AMapFindLocation2.getInstance()
                instance.location()
                instance.setLocationCompanyListener {
                    instance.stopLocation()
//                    val latLng = LatLng(it.latitude, it.longitude)
                    lonLng = "" + it.latitude + ";" + it.longitude
                    mBinding.tatvHappen.textRightText.text = it.address
                }
            }
        })
    }

    override fun loadData(isRefresh: Boolean) {
        mBinding.vm?.loadArea()
    }

    override fun onClick(v: View?) {
        when (v) {
            tatv_happen -> {
                selectHappen()
            }
            btn_push -> {
                showReportConfirmDialog()
            }
            tatv_report_part -> {
                hideKeyboard(mBinding.tvTitle)
                singleReportPathDataView.show()
            }
        }
    }

    override fun initObserver() {
        mBinding.vm?.mPartList?.observe(this, Observer {
            setPickerData(it)
        })
        mBinding.vm?.addReprtSuccess?.observe(this, Observer {
            hideLoadingDialog()
            toast("上报成功")
            finish()
            startActivity<MyCarReportActivity>()
        })
    }

    private fun setPickerData(it: ArrayList<PartBean>) {
        if (it.isEmpty()) {
            return
        }
        val queryData = ArrayList<String>()
        for (s in it) {
            queryData.add(s.part_name)
        }
        singleReportPathDataView = SingleStyleView(this)
        singleReportPathDataView.setOnItemSelectLintener(SingleStyleView.OnItemSelectLintener { result ->
            mBinding.tatvReportPart.textRightText.text = result
            mBinding.tatvReportPart.textRightText.textColor = resources.getColor(R.color.black)
            for (partBean in it) {
                if (result == partBean.part_name) {
                    mSelectPartId = partBean.part_id
                }
            }
        })
        singleReportPathDataView.replaceData(queryData)
    }

    private fun showReportConfirmDialog() {
        IosAlertDialog(this).builder()
                .setNegativeButton("", View.OnClickListener { })
                .setPositiveButton("", View.OnClickListener { toReport() })
                .setHeightMsg("确定上报事件?")
                .show()
    }

    private fun toReport() {
        val imgList = mBinding.cvisvView.getImageOrVideoPushPath(1)
        val videoList = mBinding.cvisvView.getImageOrVideoPushPath(2)
        if (imgList.size == 0 && videoList.size == 0) {
            toast("请选择照片或者视频")
            return
        }
        val carNum = mBinding.taevCarNum.editText.text.toString().trim()
        carNum.ifEmpty { toast("请输入车牌号"); return }
        val name = mBinding.tatvName.editText.text.toString().trim()
        name.ifEmpty { toast("请输入姓名"); return }
        val phone = mBinding.tatvPhone.editText.text.toString().trim()
        phone.ifEmpty { toast("请输入电话"); return }
        val address = mBinding.tatvHappen.rightTVString
        if (TextUtils.isEmpty(lonLng) || TextUtils.isEmpty(address)) {
            toast("请选择地址")
            return
        }
        if (mSelectPartId == -1) {
            toast("请选择上报部门")
            return
        }
        val content = mBinding.netContent.editValue
        content.ifEmpty { toast("填写描述"); return }

        showLoadingDialog()
        mBinding.vm?.addReport(carNum, name, phone, address, lonLng!!, content, mSelectPartId, imgList)
    }

    private fun selectHappen() {
        //由于启动map界面比较慢,所以弄个进度条,在回到页面的情况下 hideLoadingDialog
        showLoadingDialog()
        val intent = Intent(this, AmapPoiActivity::class.java)
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        hideLoadingDialog()
        if (resultCode == RESULT_OK) {
            mBinding.cvisvView.callActivityResult(requestCode, resultCode, data)
        } else if (requestCode == 100 && resultCode == 10087 && data != null) {
            val address = data.getStringExtra("ad")
            val lt = data.getStringExtra("lt")
            lonLng = lt
            mBinding.tatvHappen.textRightText.text = address
        }
    }
}