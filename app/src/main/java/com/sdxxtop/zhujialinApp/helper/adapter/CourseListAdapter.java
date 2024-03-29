package com.sdxxtop.zhujialinApp.helper.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.model.http.callback.IRequestCallback;
import com.sdxxtop.model.http.net.Params;
import com.sdxxtop.model.http.util.RxUtils;
import com.sdxxtop.utils.UIUtils;
import com.sdxxtop.zhujialinApp.R;
import com.sdxxtop.zhujialinApp.data.StudyQuestionBean;
import com.sdxxtop.zhujialinApp.data.course.BaseCourseDataBean;
import com.sdxxtop.zhujialinApp.data.course.CourseCellBean;
import com.sdxxtop.zhujialinApp.data.course.CourseHeaderBean;
import com.sdxxtop.zhujialinApp.data.course.ExamCellBean;
import com.sdxxtop.zhujialinApp.http.net.RetrofitHelper;
import com.sdxxtop.zhujialinApp.ui.examine.ExamineActivity;
import com.sdxxtop.zhujialinApp.ui.learn.news.NewsDetailsActivity;
import com.sdxxtop.zhujialinApp.ui.video.VideoPlayActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class CourseListAdapter extends BaseMultiItemQuickAdapter<BaseCourseDataBean, BaseViewHolder> {

    private boolean mIsCourse;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public CourseListAdapter() {
        super(new ArrayList<>());

        addItemType(BaseCourseDataBean.TYPE_HEADER, R.layout.item_course_list_header_recycler);
        addItemType(BaseCourseDataBean.TYPE_CELL, R.layout.item_course_list_cell_recycler);
        addItemType(BaseCourseDataBean.TYPE_LINE, R.layout.item_course_list_line_recycler);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseCourseDataBean item) {
        switch (item.getItemType()) {
            case BaseCourseDataBean.TYPE_HEADER:
                if (item instanceof CourseHeaderBean) {
                    TextView tvHeader = helper.getView(R.id.tv_header);
                    tvHeader.setText(((CourseHeaderBean) item).strHeader);
                    tvHeader.setBackgroundColor(Color.parseColor(((CourseHeaderBean) item).color));
                }
                break;
            case BaseCourseDataBean.TYPE_CELL:
                TextView tvTitle = helper.getView(R.id.tv_title);
                TextView tvRightDec = helper.getView(R.id.tv_right_dec);
                TextView tvBtn = helper.getView(R.id.tv_btn);
                TextView tvDec = helper.getView(R.id.tv_dec);
                TextView tvLastTime = helper.getView(R.id.tv_last_time);

                if (item instanceof CourseCellBean) {

                    CourseCellBean bean = (CourseCellBean) item;
                    //1.文章 2.视频

                    tvTitle.setText(bean.getTitle());
                    //共26课时，预计用时13小时
                    tvDec.setText("共" + bean.getClass_time() + "课时，预计用时" + bean.getStudy_time() + "小时");
//                    tvLastTime.setText("共" + bean.getClass_time() + "课时，预计用时" + bean.getStudy_time() + "小时");
                    tvLastTime.setText("课程考试日期：" + handleTime(bean.getExam_time()));

                    tvBtn.setText("去学习");
                    int type = bean.getType();
                    if (type == 1) {
                        tvRightDec.setText("文章");
                    } else {
                        tvRightDec.setText("视频");
                    }

                    tvBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (type == 1) {
                                Intent intent = new Intent(mContext, NewsDetailsActivity.class);
                                intent.putExtra("article_path", ((CourseCellBean) item).getUrl());
                                mContext.startActivity(intent);
                            } else {
                                Intent intent = new Intent(mContext, VideoPlayActivity.class);
                                intent.putExtra("video_path", ((CourseCellBean) item).getUrl());
                                intent.putExtra("title", ((CourseCellBean) item).getTitle());
                                mContext.startActivity(intent);
                            }
                        }
                    });
                }


                if (item instanceof ExamCellBean) {
                    ExamCellBean bean = (ExamCellBean) item;
                    tvTitle.setText(bean.getTitle());

                    tvDec.setText("考试形式：选择题    人脸识别防作弊");

                    String endTime = bean.getEnd_time();
                    tvLastTime.setText("课程最晚考试日期: " + handleHistoryTime(endTime));


                    int type = bean.getType();
                    if (type == 1) {
                        tvRightDec.setText("文章");
                    } else {
                        tvRightDec.setText("PPT");
                    }

                    //1.参加了，2.没参加
                    int is_exam = bean.getIs_exam();
                    if (is_exam == 1) {
                        tvBtn.setText("考试结束");
                        tvBtn.setBackgroundResource(R.drawable.btn_gray_solid_bg);
                        tvBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    } else {
                        tvBtn.setText("现在考试");
                        tvBtn.setBackgroundResource(R.drawable.btn_green_solid_bg);
                        tvBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                skipExam(((ExamCellBean) item).getTitle(),((ExamCellBean) item).getExam_id(), ((ExamCellBean) item).getExam_time());

                            }
                        });
                    }


                }
                break;

            default:

                break;
        }
    }

    private void skipExam(String title,int exam_id, String exam_time) {
        Params params = new Params();
        params.put("ei", exam_id);
        params.put("nm", 1);
        params.put("ai", 0);
        Observable<RequestBean<StudyQuestionBean>> observable = RetrofitHelper.getGuardianService().postStudyQuestion(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<StudyQuestionBean>() {
            @Override
            public void onSuccess(StudyQuestionBean studyQuestionBean) {
                //成功了就跳转
                Intent intent = new Intent(mContext, ExamineActivity.class);
                intent.putExtra("examId", exam_id);
                intent.putExtra("examTime", exam_time);
                intent.putExtra("title", title);
                mContext.startActivity(intent);
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });

    }

    private String handleTime(String examTime) {
        if (TextUtils.isEmpty(examTime)) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM.dd日");
        try {
            Date date = sdf.parse(examTime);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    private String handleHistoryTime(String examTime) {
        if (TextUtils.isEmpty(examTime)) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");
        try {
            Date date = sdf.parse(examTime);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public void setCourse(boolean isCourse) {
        mIsCourse = isCourse;
    }
}
