package com.sdxxtop.zhujialinApp.data.course;

public class CourseHeaderBean extends BaseCourseDataBean {
    public String strHeader;
    public String color;

    @Override
    public int getItemType() {
        return TYPE_HEADER;
    }
}
