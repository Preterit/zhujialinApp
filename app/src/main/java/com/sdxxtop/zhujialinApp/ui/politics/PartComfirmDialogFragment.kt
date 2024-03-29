package com.sdxxtop.zhujialinApp.ui.politics

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_part_comfirm_dialog.*
import android.webkit.WebSettings
import android.R


const val CONFIRM_TITLE = "confirm_title"


/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    PartComfirmDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 *
 * You activity (or fragment) needs to implement [PartComfirmDialogFragment.Listener].
 */
class PartComfirmDialogFragment : BottomSheetDialogFragment() {
    private var mListener: Listener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.sdxxtop.zhujialinApp.R.layout.fragment_part_comfirm_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        list.layoutManager = LinearLayoutManager(context)
//        list.adapter = ItemAdapter(arguments?.getInt(ARG_ITEM_COUNT)!!)

        val tvTitle = view.findViewById<TextView>(com.sdxxtop.zhujialinApp.R.id.tv_title)
        tvTitle.text = arguments?.getString(CONFIRM_TITLE)

        val btnConfirm = view.findViewById<Button>(com.sdxxtop.zhujialinApp.R.id.btn_confirm)

        btnConfirm.setOnClickListener {
            mListener?.let {
                it.onConfirmClicked()
                dismiss()
            }
        }
        val tvThink = view.findViewById<TextView>(com.sdxxtop.zhujialinApp.R.id.tv_think)
        tvThink.setOnClickListener {
            dismiss()
        }

        webview.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (!webview.canScrollVertically(-1)) {      //canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
                    webview.requestDisallowInterceptTouchEvent(false);
                } else {
                    webview.requestDisallowInterceptTouchEvent(true);
                }
                return false
            }

        })

        val settings = webview.getSettings()
//      自适应屏幕
        settings.setUseWideViewPort(true)
        settings.setLoadWithOverviewMode(true)
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)

        //扩大比例的缩放
        settings.setJavaScriptEnabled(true)
        if (webview.url == null) {

            webview.loadUrl("file:///android_asset/agreement.html")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val parent = parentFragment
        if (parent != null) {
            mListener = parent as Listener
        } else {
            mListener = context as Listener
        }
    }

    override fun onDetach() {
        mListener = null
        webview?.clearView()
        webview?.destroy()
        super.onDetach()
    }

    interface Listener {
        fun onConfirmClicked()
    }

//    private inner class ViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup)
//        : RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_part_comfirm_dialog_item, parent, false)) {
//
//        internal val text: TextView = itemView.text
//
//        init {
//            text.setOnClickListener {
//                mListener?.let {
//                    it.onItemClicked(adapterPosition)
//                    dismiss()
//                }
//            }
//        }
//    }
//
//    private inner class ItemAdapter internal constructor(private val mItemCount: Int) : RecyclerView.Adapter<ViewHolder>() {
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            return ViewHolder(LayoutInflater.from(parent.context), parent)
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            holder.text.text = position.toString()
//        }
//
//        override fun getItemCount(): Int {
//            return mItemCount
//        }
//    }

    companion object {

        // TODO: Customize parameters
        fun newInstance(itemCount: String): PartComfirmDialogFragment =
                PartComfirmDialogFragment().apply {
                    arguments = Bundle().apply {
                        putString(CONFIRM_TITLE, itemCount)
                    }
                }

    }
}
