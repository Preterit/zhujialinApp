package com.sdxxtop.zhujialinApp.http;

import com.sdxxtop.model.bean.InitBean;
import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.zhujialinApp.data.AutoLoginBean;
import com.sdxxtop.zhujialinApp.data.CarTypeBean;
import com.sdxxtop.zhujialinApp.data.ContactIndexBean;
import com.sdxxtop.zhujialinApp.data.EventSearchTitleBean;
import com.sdxxtop.zhujialinApp.data.ExamineFinishBean;
import com.sdxxtop.zhujialinApp.data.LearnNewsBean;
import com.sdxxtop.zhujialinApp.data.LoginBean;
import com.sdxxtop.zhujialinApp.data.MainMapBean;
import com.sdxxtop.zhujialinApp.data.PartBean;
import com.sdxxtop.zhujialinApp.data.PoliticsListBean;
import com.sdxxtop.zhujialinApp.data.PushDataBean;
import com.sdxxtop.zhujialinApp.data.RegisterBean;
import com.sdxxtop.zhujialinApp.data.ServerPeopleBean;
import com.sdxxtop.zhujialinApp.data.SignLogBean;
import com.sdxxtop.zhujialinApp.data.StudyCourseBean;
import com.sdxxtop.zhujialinApp.data.StudyQuestionBean;
import com.sdxxtop.zhujialinApp.data.UcenterIndexBean;
import com.sdxxtop.zhujialinApp.presenter.bean.EventIndexBean;
import com.sdxxtop.zhujialinApp.presenter.bean.EventReadBean;
import com.sdxxtop.zhujialinApp.presenter.bean.MainIndexBean;
import com.sdxxtop.zhujialinApp.ui.car_report.data.CarIndexBean;
import com.sdxxtop.zhujialinApp.ui.car_report.data.CarReportDetailBean;
import com.sdxxtop.zhujialinApp.ui.feedback.data.FeedbackData;
import com.sdxxtop.zhujialinApp.ui.feedback.data.ProposalBean;
import com.sdxxtop.zhujialinApp.ui.notice.data.NoticeData;
import com.sdxxtop.zhujialinApp.ui.policy.data.PolicyBean;
import com.sdxxtop.zhujialinApp.ui.policy.data.PolicyQueryBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-16 10:47
 * Version: 1.0
 * Description:
 */
public interface GuardianService {
    /**
     * debug的时候用http，正式打包的时候用https
     */
//    String BASE_URL = BuildConfig.DEBUG ?
//            "http://villageapi.sdzhujialin.com/village/"
//            : "https://villageapi.sdzhujialin.com/village/";

    String BASE_URL = "http://villageapi.sdzhujialin.com/village/";

    @FormUrlEncoded
    @POST("app/init")
    Observable<RequestBean<InitBean>> postAppInit(@Field("data") String data);

    @FormUrlEncoded
    @POST("login/sendCode")
    Observable<RequestBean> postLoginSendCode(@Field("data") String data);

    @FormUrlEncoded
    @POST("login/mobileLogin")
    Observable<RequestBean<LoginBean>> postLoginMobileLogin(@Field("data") String data);


    @FormUrlEncoded
    @POST("login/autoLogin")
    Observable<RequestBean<AutoLoginBean>> postLoginAutoLogin(@Field("data") String data);

    @FormUrlEncoded
    @POST("login/confirm")
    Observable<RequestBean> postLoginConfirm(@Field("data") String data);
//
//    ////////////// 首页 ////////////
    @FormUrlEncoded
    @POST("main/index")
    Observable<RequestBean<MainIndexBean>> postMainIndex(@Field("data") String data);

    @FormUrlEncoded
    @POST("main/sign")
    Observable<RequestBean> postMainSign(@Field("data") String data);

    @FormUrlEncoded
    @POST("login/register")
    Observable<RequestBean<RegisterBean>> postLoginRegister(@Field("data") String data);

    @FormUrlEncoded
    @POST("main/signlog")
    Observable<RequestBean<SignLogBean>> postMainSignLog(@Field("data") String data);
//
    @FormUrlEncoded
    @POST("main/map")
    Observable<RequestBean<MainMapBean>> postMainMap(@Field("data") String data);

    //////////////首页 ////////////


    @FormUrlEncoded
    @POST("event/index")
    Observable<RequestBean<EventIndexBean>> postEventIndex(@Field("data") String data);

    @Multipart
    @POST("event/add")
    Observable<RequestBean> postEventAdd(@PartMap Map<String, RequestBody> data);

    @FormUrlEncoded
    @POST("event/read")
    Observable<RequestBean<EventReadBean>> postEventRead(@Field("data") String data);

    @Multipart
    @POST("event/modify")
    Observable<RequestBean> postEventModify(@PartMap HashMap<String, RequestBody> data);

    @Multipart
    @POST("car_event/modify")
    Observable<RequestBean> postCarModify(@PartMap HashMap<String, RequestBody> data);

    //
    @FormUrlEncoded
    @POST("event/showPart")
    Observable<RequestBean<ArrayList<PartBean>>> postEventShowPart2(@Field("data") String data);

    //
    @FormUrlEncoded
    @POST("car_event/index")
    Observable<RequestBean<CarIndexBean>> postCarReportData(@Field("data") String data);

    //
    @FormUrlEncoded
    @POST("car_event/read")
    Observable<RequestBean<CarReportDetailBean>> postCarReportDetail(@Field("data") String data);

    @FormUrlEncoded
    @POST("event/search")
    Observable<RequestBean<EventSearchTitleBean>> postEventSearch(@Field("data") String data);

    //
    @FormUrlEncoded
    @POST("contact/index")
    Observable<RequestBean<ContactIndexBean>> postContactIndex(@Field("data") String data);
//
    @FormUrlEncoded
    @POST("contact/search")
    Observable<RequestBean<ContactIndexBean>> postContactSearch(@Field("data") String data);
//
//
    @FormUrlEncoded
    @POST("ucenter/index")
    Observable<RequestBean<UcenterIndexBean>> postUcenterIndex(@Field("data") String data);

    @Multipart
    @POST("ucenter/modImg")
    Observable<RequestBean> postUcenterModImg(@PartMap Map<String, RequestBody> data);

    /**
     * exam
     * course
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("study/{name}")
    Observable<RequestBean<StudyCourseBean>> postStudyCourse(@Path("name") String name, @Field("data") String data);

    /**
     * exam
     * course
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("study/question")
    Observable<RequestBean<StudyQuestionBean>> postStudyQuestion(@Field("data") String data);

    @FormUrlEncoded
    @POST("study/check")
    Observable<RequestBean> postStudyCheck(@Field("data") String data);

    @FormUrlEncoded
    @POST("study/finish")
    Observable<RequestBean<ExamineFinishBean>> postStudyFinish(@Field("data") String data);


    @Multipart
    @POST("face/reg")
    Observable<RequestBean> postFaceReg(@PartMap Map<String, RequestBody> data);

    @Multipart
    @POST("face/verify")
    Observable<RequestBean> postFaceVerify(@PartMap Map<String, RequestBody> data);


    @FormUrlEncoded
    @POST("article/article_info")
    Observable<RequestBean> getAllArticleInfo(@Field("data") String data);

    @FormUrlEncoded
    @POST("article/allarticle")
    Observable<RequestBean<List<LearnNewsBean>>> getAllArticle(@Field("data") String data);


    @FormUrlEncoded
    @POST("index/index")
    Observable<RequestBean<ServerPeopleBean>> postIndexTest(@Field("data") String data);

    @FormUrlEncoded
    @POST("event/showPart")
    Observable<RequestBean<ArrayList<PartBean>>> postEventShowPart(@Field("data") String data);

    @Multipart
    @POST("my_politics/politicsConfirm")
    Observable<RequestBean<PushDataBean>> postPoliticsConfirm(@PartMap Map<String, RequestBody> data);

    @Multipart
    @POST("car_event/add")
    Observable<RequestBean> postCarReport(@PartMap Map<String, RequestBody> data);

    @FormUrlEncoded
    @POST("my_politics/search")
    Observable<RequestBean<PoliticsListBean>> postPoliticsSearch(@Field("data") String data);

    @FormUrlEncoded
    @POST("policy/index")
    Observable<RequestBean<List<PolicyQueryBean>>> postPolicyIndex(@Field("data") String data);

    @FormUrlEncoded
    @POST("policy/search")
    Observable<RequestBean<PolicyBean>> postPolicySearch(@Field("data") String data);

    @Multipart
    @POST("my_proposal/proposalConfirm")
    Observable<RequestBean<ProposalBean>> postProposalPolics(@PartMap Map<String, RequestBody> data);

    @FormUrlEncoded
    @POST("my_proposal/search")
    Observable<RequestBean<FeedbackData>> postProposalSearch(@Field("data") String data);

    @FormUrlEncoded
    @POST("notice/index")
    Observable<RequestBean<NoticeData>> postNoticeIndex(@Field("data") String data);

    @FormUrlEncoded
    @POST("car_event/showType")
    Observable<RequestBean<ArrayList<CarTypeBean>>> postCarType(@Field("data") String data);

}
