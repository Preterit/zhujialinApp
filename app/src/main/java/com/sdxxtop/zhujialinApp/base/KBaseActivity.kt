package com.sdxxtop.zhujialinApp.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.noober.background.BackgroundLibrary
import com.sdxxtop.utils.DialogUtil
import com.sdxxtop.zhujialinApp.extens.statusBar
import me.yokeyword.fragmentation.SupportActivity

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-15 21:11
 * Version: 1.0
 * Description:
 */
abstract class KBaseActivity<VB : ViewDataBinding> : SupportActivity(), Presenter {
    protected val mBinding: VB by lazy { DataBindingUtil.setContentView<VB>(this, getLayoutId()) }
    protected lateinit var mContext: Context

    protected var autoRefresh = true
    protected val mDialogUtil: DialogUtil by lazy {
        DialogUtil()
    };

    override fun onCreate(savedInstanceState: Bundle?) {
        BackgroundLibrary.inject(this)
        super.onCreate(savedInstanceState)
        mBinding.setVariable(BR.presenter, this)
        mBinding.executePendingBindings()
        mBinding.lifecycleOwner = this
        mContext = this

        //默认在6.0的时候设置为灰色
        this.statusBar(true)

        initView()
        initObserver()
        loadData(autoRefresh)
    }

    open fun initObserver() {

    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    override fun onClick(v: View?) {
    }

    fun showLoadingDialog() {
        mDialogUtil.showLoadingDialog(this)
    }

    fun hideLoadingDialog() {
        mDialogUtil.closeLoadingDialog()
    }

    fun <T : ViewModel> bindViewModel(clazz: Class<T>): T {
        return ViewModelProviders.of(this)[clazz]
    }

    fun hideKeyboard(view: View) {
        val imm = view.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

}