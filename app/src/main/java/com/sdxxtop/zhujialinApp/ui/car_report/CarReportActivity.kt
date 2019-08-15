package com.sdxxtop.zhujialinApp.ui.car_report

import android.content.Intent
import android.text.InputFilter
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.sdxxtop.ui.dialog.IosAlertDialog
import com.sdxxtop.zhujialinApp.R
import com.sdxxtop.zhujialinApp.base.KBaseActivity
import com.sdxxtop.zhujialinApp.databinding.ActivityCarReportBinding
import com.sdxxtop.zhujialinApp.extens.toast
import com.sdxxtop.zhujialinApp.ui.guardianapp.AmapPoiActivity
import kotlinx.android.synthetic.main.activity_car_report.*

/**
 * @author :  lwb
 * Date: 2019/8/15
 * Desc:
 */
class CarReportActivity : KBaseActivity<ActivityCarReportBinding>() {

    var lonLng: String? = null

    override fun getLayoutId() = R.layout.activity_car_report

    override fun initView() {
        mBinding.vm = ViewModelProviders.of(this)[CarReportModel::class.java]
        mBinding.tatvPhone.editText.inputType = InputType.TYPE_CLASS_PHONE
        mBinding.tatvPhone.editText.filters = arrayOf(InputFilter.LengthFilter(11))
        mBinding.taevCarNum.editText.keyListener = DigitsKeyListener.getInstance(getString(R.string.text_digits))
        mBinding.netContent.setEditHint(" ")
    }

    override fun loadData(isRefresh: Boolean) {

    }

    override fun onClick(v: View?) {
        when (v) {
            tatv_happen -> {
                selectHappen()
            }
            btn_push -> {
                showReportConfirmDialog()
            }
        }
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
        lonLng?.ifEmpty { toast("请选择地址"); return }
        val content = mBinding.netContent.editValue
        content.ifEmpty { toast("填写描述"); return }

        toast("success")
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