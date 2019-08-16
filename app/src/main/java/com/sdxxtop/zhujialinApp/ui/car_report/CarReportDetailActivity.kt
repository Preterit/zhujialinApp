package com.sdxxtop.zhujialinApp.ui.car_report

import android.Manifest
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
import com.sdxxtop.utils.AMapFindLocation2
import com.sdxxtop.zhujialinApp.R
import com.sdxxtop.zhujialinApp.base.KBaseActivity
import com.sdxxtop.zhujialinApp.databinding.ActivityCarReportDetailBinding
import com.sdxxtop.zhujialinApp.widget.img_video_picker.MediaBean
import com.sdxxtop.zhujialinApp.widget.img_video_picker.PatrolDetailImgAdapter
import io.reactivex.functions.Consumer
import java.util.*

/**
 * @author :  lwb
 * Date: 2019/8/16
 * Desc:
 */
class CarReportDetailActivity : KBaseActivity<ActivityCarReportDetailBinding>() {

    private val imgAdapter = PatrolDetailImgAdapter(R.layout.gv_filter_image, null)
    private var mMapView: MapView? = null
    private var aMap: AMap? = null

    override fun getLayoutId() = R.layout.activity_car_report_detail

    override fun initView() {
        mBinding.vm = ViewModelProviders.of(this)[CarReportDetailModel::class.java]
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        mBinding.recyclerView.adapter = imgAdapter
        mMapView = mBinding.mapView
        aMap = mMapView?.map

        val mRxPermissions = RxPermissions(this)
        mRxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(Consumer<Boolean> { aBoolean ->
            if (aBoolean!!) {
                initMap()
            }
        })

        val data = "http://xuxingtest.oss-cn-hangzhou.aliyuncs.com/envir/20190816153532396485.png," +
                "http://xuxingtest.oss-cn-hangzhou.aliyuncs.com/envir/20190816153532860972.png," +
                "http://xuxingtest.oss-cn-hangzhou.aliyuncs.com/envir/20190816153533476920.png," +
                "http://xuxingtest.oss-cn-hangzhou.aliyuncs.com/envir/20190816153533807812.png," +
                "http://xuxingtest.oss-cn-hangzhou.aliyuncs.com/envir/20190816153533165792.png," +
                "http://xuxingtest.oss-cn-hangzhou.aliyuncs.com/envir/20190816153533533340.png," +
                "http://xuxingtest.oss-cn-hangzhou.aliyuncs.com/envir/20190816153533534053.png," +
                "http://xuxingtest.oss-cn-hangzhou.aliyuncs.com/envir/20190816153533256477.png," +
                "http://xuxingtest.oss-cn-hangzhou.aliyuncs.com/envir/20190816153533498534.png"
//        bandImgAndVideo(data, "", mBinding.recyclerView, imgAdapter)
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
            val options = MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.grid_map_icon))
                    .position(latLng)
            aMap?.addMarker(options)
        }
    }

    override fun loadData(isRefresh: Boolean) {
        val eventId = intent.getIntExtra("eventId", 0)
        mBinding.vm?.loadData(eventId)
    }

    override fun initObserver() {
        mBinding.vm?.detailBeanInfo?.observe(this, androidx.lifecycle.Observer {
            val imgStr = it.image
            bandImgAndVideo(imgStr, "", mBinding.recyclerView, imgAdapter)
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

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView?.onDestroy()
    }

    override fun onResume() {
        super.onResume()
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