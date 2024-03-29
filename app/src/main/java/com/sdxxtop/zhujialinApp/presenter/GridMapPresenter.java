package com.sdxxtop.zhujialinApp.presenter;


import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import androidx.annotation.ColorRes;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.sdxxtop.base.RxPresenter;
import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.model.http.callback.IRequestCallback;
import com.sdxxtop.model.http.net.Params;
import com.sdxxtop.model.http.util.RxUtils;
import com.sdxxtop.utils.UIUtils;
import com.sdxxtop.zhujialinApp.base.GRxPresenter;
import com.sdxxtop.zhujialinApp.data.MainMapBean;
import com.sdxxtop.zhujialinApp.presenter.constract.GridMapContract;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GridMapPresenter extends GRxPresenter<GridMapContract.IView> implements GridMapContract.IPresenter, DistrictSearch.OnDistrictSearchListener {
    @Inject
    public GridMapPresenter() {
    }


    public void postMap() {
        Params params = new Params();
        Observable<RequestBean<MainMapBean>> observable = getService().postMainMap(params.getData());
        RxUtils.handleDataHttp(observable, new IRequestCallback<MainMapBean>() {
            @Override
            public void onSuccess(MainMapBean mainMapBean) {
                List<MainMapBean.UserBean> user = mainMapBean.getUser();
                if (user != null) {
                    AsyncTask.THREAD_POOL_EXECUTOR.execute(
                            new MapRunnable(GridMapPresenter.this, user));
                }
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });
    }

    public void search(Context context, List<GridPeople> gridPeopleList) {

        for (GridPeople gridPeople : gridPeopleList) {
            search(context, gridPeople.address);
        }
    }


    private void search(Context context, String keywords) {
        DistrictSearch search = new DistrictSearch(context);
        DistrictSearchQuery query = new DistrictSearchQuery();
        query.setKeywords(keywords);//传入关键字
        query.setShowBoundary(true);//是否返回边界值
        search.setQuery(query);
        search.setOnDistrictSearchListener(this);
        search.searchDistrictAsyn();

    }

    @Override
    public void onDistrictSearched(DistrictResult districtResult) {
        ArrayList<DistrictItem> district = districtResult.getDistrict();
        for (DistrictItem districtItem : district) {
            String adcode = districtItem.getAdcode();
            LatLonPoint center = districtItem.getCenter();
            String citycode = districtItem.getCitycode();
            String level = districtItem.getLevel();
            String name = districtItem.getName();
            List<DistrictItem> subDistrict = districtItem.getSubDistrict();
            new Thread() {
                @Override
                public void run() {
                    parseData(districtItem);

                }
            }.start();

        }
    }


    private void parseData(int index, MainMapBean.UserBean middle, String lanLan) {
        if (TextUtils.isEmpty(lanLan)) {
            return;
        }
        List<LatLng> list = new ArrayList<>();
        String[] lat = lanLan.split(";");
        boolean isFirst = true;
        LatLng firstLatLng = null;
        for (String latstr : lat) {
            String[] lats = latstr.split(",");
            if (isFirst) {
                isFirst = false;
                firstLatLng = new LatLng(Double
                        .parseDouble(lats[1]), Double
                        .parseDouble(lats[0]));
            }
            list.add(new LatLng(Double
                    .parseDouble(lats[1]), Double
                    .parseDouble(lats[0])));

        }
        if (firstLatLng != null) {
            list.add(firstLatLng);
        }
        mView.showPolygon(index, middle, list);
    }

    private void parseData(DistrictItem districtItem) {
        String[] polyStr = districtItem.districtBoundary();
        if (polyStr == null || polyStr.length == 0) {
            return;
        }

        List<LatLng> list;

        for (String str : polyStr) {
            list = new ArrayList<>();
            String[] lat = str.split(";");
            boolean isFirst = true;
            LatLng firstLatLng = null;
            for (String latstr : lat) {
                String[] lats = latstr.split(",");
                if (isFirst) {
                    isFirst = false;
                    firstLatLng = new LatLng(Double
                            .parseDouble(lats[1]), Double
                            .parseDouble(lats[0]));
                }
                list.add(new LatLng(Double
                        .parseDouble(lats[1]), Double
                        .parseDouble(lats[0])));

            }
            if (firstLatLng != null) {
                list.add(firstLatLng);
            }
//            mView.showPolygon(list);
        }
    }


    static class GridPeople {
        public String name;
        public String title;
        public String address;
        public String msg;
        public @ColorRes
        int color;
    }


    public static class MapRunnable implements Runnable {
        List<MainMapBean.UserBean> user;
        WeakReference<GridMapPresenter> presenterDef;

        public MapRunnable(GridMapPresenter presenter, List<MainMapBean.UserBean> user) {
            this.user = user;
            presenterDef = new WeakReference<>(presenter);
        }

        @Override
        public void run() {
            for (MainMapBean.UserBean userBean : user) {
                String lonLon = userBean.getLonLon();
                int i = user.indexOf(userBean);
                if (!TextUtils.isEmpty(lonLon) && presenterDef.get() != null) {
                    presenterDef.get().parseData(i, userBean, lonLon);
                }
            }
        }
    }

}
