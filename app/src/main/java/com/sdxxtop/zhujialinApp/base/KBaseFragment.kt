package com.sdxxtop.zhujialinApp.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.sdxxtop.app.App
import com.sdxxtop.utils.SystemUtil
import com.sdxxtop.zhujialinApp.BR
import com.sdxxtop.zhujialinApp.extens.dispatchFailure
import com.sdxxtop.zhujialinApp.extens.toast
import com.sdxxtop.zhujialinApp.helper.annotations.ToastType
import me.yokeyword.fragmentation.SupportFragment


/**
 * 页面描述：fragment 基类
 *
 * Created by ditclear on 2017/9/27.
 */

abstract class KBaseFragment<VB : ViewDataBinding> : SupportFragment(), Presenter {

    protected val mBinding by lazy { DataBindingUtil.inflate<VB>(layoutInflater, getLayoutId(), null, false) }

    protected lateinit var mContext: Context

    protected var lazyLoad = false

    protected var visible = false

    /**
     * 标志位，标志已经初始化完成
     */
    protected var isPrepared: Boolean = false
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    protected var hasLoadOnce: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArgs(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = activity ?: throw Exception("activity 为null")
        retainInstance = true
        initView()
        if (lazyLoad) {
            //延迟加载，需重写lazyLoad方法
            lazyLoad()
        } else {
            // 加载数据
            loadData(true);
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding.setVariable(BR.presenter, this)
        mBinding.executePendingBindings()
        mBinding.setLifecycleOwner(this)
        return mBinding.root
    }

    /**x
     * 是否可见，延迟加载
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            visible = true
            onVisible()
        } else {
            visible = false
            onInvisible()
        }
    }

    protected fun onInvisible() {

    }

    protected open fun onVisible() {
        lazyLoad()
    }


    open fun lazyLoad() {}

    open fun initArgs(savedInstanceState: Bundle?) {

    }

    abstract fun initView()
    abstract override fun loadData(isRefresh: Boolean)

    abstract fun getLayoutId(): Int

    fun toast(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }

    fun toastSuccess(msg: String?) {
        msg?.let { activity?.toast(it, ToastType.SUCCESS) }
    }

    fun toastFailure(error: Throwable) {
        activity?.dispatchFailure(error)
    }

    override fun onClick(v: View?) {

    }

    protected fun <T> autoWired(key: String, default: T? = null): T? =
            arguments?.let { findWired(it, key, default) }

    private fun <T> findWired(bundle: Bundle, key: String, default: T? = null): T? {
        return if (bundle.get(key) != null) {
            try {
                bundle.get(key) as T
            } catch (e: ClassCastException) {
                e.printStackTrace()
                null
            }
        } else default

    }

    fun topViewPadding(view: View) {
        if (isVersionMoreKitkat()) {
            view.setPadding(0, SystemUtil.getStatusHeight(App.getContext()), 0, 0)
        }
    }

    fun isVersionMoreKitkat(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            true
        } else {
            false
        }
    }

}