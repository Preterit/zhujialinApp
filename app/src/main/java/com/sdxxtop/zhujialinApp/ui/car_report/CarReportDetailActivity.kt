package com.sdxxtop.zhujialinApp.ui.car_report

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.luck.picture.lib.permissions.RxPermissions
import com.sdxxtop.app.Constants
import com.sdxxtop.utils.AMapFindLocation2
import com.sdxxtop.utils.DateUtil
import com.sdxxtop.utils.SpUtil
import com.sdxxtop.zhujialinApp.R
import com.sdxxtop.zhujialinApp.base.KBaseActivity
import com.sdxxtop.zhujialinApp.databinding.ActivityCarReportDetailBinding
import com.sdxxtop.zhujialinApp.ui.car_report.data.StatusBean
import com.sdxxtop.zhujialinApp.ui.guardianapp.EventReportDetailSecondActivity
import com.sdxxtop.zhujialinApp.widget.img_video_picker.MediaBean
import com.sdxxtop.zhujialinApp.widget.img_video_picker.PatrolDetailImgAdapter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_car_report_detail.*
import org.jetbrains.anko.startActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author :  lwb
 * Date: 2019/8/16
 * Desc:
 */
class CarReportDetailActivity : KBaseActivity<ActivityCarReportDetailBinding>() {

    private val imgAdapter = PatrolDetailImgAdapter(R.layout.gv_filter_image, null)
    private val imgjiejueAdapter = PatrolDetailImgAdapter(R.layout.gv_filter_image, null)
    private val statusAdapter = CarDetailStatusAdapter()
    private var mMapView: MapView? = null
    private var aMap: AMap? = null
    var eventId = 0
    val statusList = ArrayList<StatusBean>()

    override fun getLayoutId() = R.layout.activity_car_report_detail

    @SuppressLint("CheckResult")
    override fun initView() {
        mBinding.vm = ViewModelProviders.of(this)[CarReportDetailModel::class.java]
        eventId = intent.getIntExtra("eventId", 0)
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        mBinding.recyclerView.adapter = imgAdapter

        mBinding.recyclerViewStatus.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mBinding.recyclerViewStatus.adapter = statusAdapter

        mBinding.recyclerViewJiejue.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        mBinding.recyclerViewJiejue.adapter = imgjiejueAdapter


        mMapView = mBinding.mapView
        aMap = mMapView?.map

        val mRxPermissions = RxPermissions(this)
        mRxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(Consumer<Boolean> { aBoolean ->
            if (aBoolean!!) {
                initMap()
            }
        })
    }

    private fun initMap() {
        val myLocationStyle = MyLocationStyle()//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        myLocationStyle.showMyLocation(true);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
        aMap?.myLocationStyle = myLocationStyle//设置定位蓝点的Style
        aMap?.uiSettings?.isMyLocationButtonEnabled = false //设置默认定位按钮是否显示，非必需设置。
        aMap?.moveCamera(CameraUpdateFactory.zoomTo(10f))

        val instance = AMapFindLocation2.getInstance()
        instance.location()
        instance.setLocationCompanyListener {
            instance.stopLocation()
            val latLng = LatLng(it.latitude, it.longitude)
            aMap?.moveCamera(CameraUpdateFactory.changeLatLng(latLng))
        }
    }

    override fun loadData(isRefresh: Boolean) {

    }

    /**
     * 刷新数据
     */
    override fun initObserver() {
        mBinding.vm?.detailBeanInfo?.observe(this, androidx.lifecycle.Observer {
            bandImgAndVideo(it.image, "", mBinding.recyclerView, imgAdapter)
            if (it.status == 1) mBinding.llParfaLayout.visibility = View.GONE else mBinding.llParfaLayout.visibility = View.VISIBLE
            when (it.important) {//重要性:1=低,2=中,3=高
                1 -> mBinding.tvImportanceType.text = "低"
                2 -> mBinding.tvImportanceType.text = "中"
                3 -> mBinding.tvImportanceType.text = "高"
            }
            mBinding.tvJiejueTime.text = "解决反馈时间: " + handleTime(it.finish_time)
//            mBinding.tvEndtime.text = handleTime(it.end_date)
            mBinding.tvEndtime.text = handleTime(DateUtil.stampToDate(it.end_time))

            statusList.clear()
            when (it.status) {//事件状态:1=待派发, 2=待解决,3=完成
                1 -> statusList.add(StatusBean("待派发", ""))  // 上报时间
                2 -> {
                    statusList.add(StatusBean("已派发", "" + it.send_time))     // 派发时间
                    statusList.add(StatusBean("待解决", ""))     // 待解决时间
                }
                3 -> {
                    statusList.add(StatusBean("已派发", "" + it.send_time))
                    statusList.add(StatusBean("已解决", "" + it.finish_time))
                    statusList.add(StatusBean("已完成", "" + it.finish_time))
                }
            }
            statusAdapter.replaceData(statusList)
            if (it.send_id == SpUtil.getInt(Constants.USER_ID, 0) && it.status == 2) {
                mBinding.llBtnLayout.visibility = View.VISIBLE
            } else {
                mBinding.llBtnLayout.visibility = View.GONE
            }

            if (it.status == 3) {
                mBinding.llJiejueLayout.visibility = View.VISIBLE
            } else {
                mBinding.llJiejueLayout.visibility = View.GONE
            }
            bandImgAndVideo(it.finish_img, "", mBinding.recyclerViewJiejue, imgjiejueAdapter)

            if (!TextUtils.isEmpty(it.loglng)) {
                val spUtil = it?.loglng?.split(",")
                val latLng = LatLng(java.lang.Double.parseDouble(spUtil?.get(1)), java.lang.Double.parseDouble(spUtil?.get(0)))
                val options = MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.grid_map_icon))
                        .position(latLng)
                aMap?.addMarker(options)
                aMap?.moveCamera(CameraUpdateFactory.changeLatLng(latLng))
            }
        })
    }

    private fun bandImgAndVideo(img: String, vedio: String, recyclerView: RecyclerView, adapter: PatrolDetailImgAdapter) {
        val list = ArrayList<MediaBean>()
        if (!TextUtils.isEmpty(vedio)) {
            list.add(MediaBean(vedio, 2))
        }

        if (!TextUtils.isEmpty(img)) {
            val split = img.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in split.indices) {
                list.add(MediaBean(split[i], 1))
            }
        }
        if (list.size > 0) {
            recyclerView.visibility = View.VISIBLE
            adapter.replaceData(list)
        } else {
            recyclerView.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_modify -> {  // 完成事件
                val status = 3
                startActivity<EventReportDetailSecondActivity>(
                        "eventId" to "" + eventId,
                        "eventType" to 3
                )
            }
        }
    }

    private fun handleTime(time: String): String {
        if (TextUtils.isEmpty(time)) {
            return ""
        }

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val sdf2 = SimpleDateFormat("yyyy.MM.dd")
        try {
            val date = sdf.parse(time)
            return sdf2.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return ""
    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView?.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mBinding.vm?.loadData(eventId)
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView?.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView?.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMapView?.onCreate(savedInstanceState)
    }
}