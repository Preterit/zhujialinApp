package com.sdxxtop.zhujialinApp.di;

import android.app.Activity;

import com.sdxxtop.di.component.AppComponent;
import com.sdxxtop.di.module.FragmentModule;
import com.sdxxtop.di.qualifier.FragmentScope;
import com.sdxxtop.zhujialinApp.ui.home.HomeFragment;
import com.sdxxtop.zhujialinApp.ui.learn.course.CourseListFragment;
import com.sdxxtop.zhujialinApp.ui.learn.news.NewsListFragment;
import com.sdxxtop.zhujialinApp.ui.mine.MineFragment;

import dagger.Component;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-16 14:29
 * Version: 1.0
 * Description:
 */
@FragmentScope
@Component(modules = {FragmentModule.class}, dependencies = AppComponent.class)
public interface GFragmentComponent {
    Activity getActivity();

    void inject(NewsListFragment newsListFragment);

    void inject(CourseListFragment fragment);

    void inject(MineFragment fragment);

    void inject(HomeFragment fragment);
}
