package com.sdxxtop.zhujialinApp.ui.learn.course;


import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.model.http.callback.IRequestCallback;
import com.sdxxtop.model.http.net.Params;
import com.sdxxtop.model.http.util.RxUtils;
import com.sdxxtop.utils.UIUtils;
import com.sdxxtop.zhujialinApp.base.GRxPresenter;
import com.sdxxtop.zhujialinApp.data.StudyCourseBean;
import com.sdxxtop.zhujialinApp.data.course.CourseCellBean;
import com.sdxxtop.zhujialinApp.data.course.ExamCellBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class CourseListPresenter extends GRxPresenter<CourseListContract.IView> implements CourseListContract.IPresenter {
    @Inject
    public CourseListPresenter() {
    }

    public void loadData(String name) {
        Params params = new Params();
        Observable<RequestBean<StudyCourseBean>> observable = getService().postStudyCourse(name, params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<StudyCourseBean>() {
            @Override
            public void onSuccess(StudyCourseBean studyCourseBean) {

                if ("exam".equals(name)) {
                    List<ExamCellBean> exam = studyCourseBean.getExam();
                    if (exam != null) {
                        mView.showExam(exam);
                    }
                } else {
                    List<CourseCellBean> course = studyCourseBean.getCourse();
                    if (course != null) {
                        mView.showList(course);
                    }
                }

            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }
}
