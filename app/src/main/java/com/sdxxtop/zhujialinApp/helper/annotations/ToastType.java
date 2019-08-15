package com.sdxxtop.zhujialinApp.helper.annotations;

import androidx.annotation.IntDef;

import static com.sdxxtop.zhujialinApp.helper.annotations.ToastType.ERROR;
import static com.sdxxtop.zhujialinApp.helper.annotations.ToastType.NORMAL;
import static com.sdxxtop.zhujialinApp.helper.annotations.ToastType.SUCCESS;
import static com.sdxxtop.zhujialinApp.helper.annotations.ToastType.WARNING;

@IntDef({ERROR,NORMAL,SUCCESS,WARNING})
public @interface ToastType {
    int ERROR=-2;
    int WARNING=-1;
    int NORMAL=0;
    int SUCCESS=1;
}