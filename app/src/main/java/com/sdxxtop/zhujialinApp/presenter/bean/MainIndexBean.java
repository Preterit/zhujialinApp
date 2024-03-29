package com.sdxxtop.zhujialinApp.presenter.bean;

import java.util.ArrayList;
import java.util.List;

public class MainIndexBean {

    /**
     * name : 周周
     * position : 1
     * part_name : 岸堤镇
     * is_face : 2
     * pending_event : [{"title":"指派11","end_date":"2019-04-11","status":2}]
     * add_event : [{"title":"指派22","end_date":"2019-04-11","status":5},{"title":"指派11","end_date":"2019-04-11","status":2},{"title":"test1","end_date":"",
     * "status":1},{"title":"test","end_date":"0000-00-00","status":1}]
     */

    private String name;
    private String img;
    private int position;
    private String part_name;
    private int is_face;
    private List<PendingEventBean> pending_event;
    private List<AddEventBean> add_event;
    private List<AddCarEvent> add_car_event;

    public List<AddCarEvent> getAdd_car_event() {
        return add_car_event;
    }

    public void setAdd_car_event(List<AddCarEvent> add_car_event) {
        this.add_car_event = add_car_event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    public int getIs_face() {
        return is_face;
    }

    public void setIs_face(int is_face) {
        this.is_face = is_face;
    }

    public List<PendingEventBean> getPending_event() {
        return pending_event;
    }

    public void setPending_event(List<PendingEventBean> pending_event) {
        this.pending_event = pending_event;
    }

    public List<AddEventBean> getAdd_event() {
        return add_event;
    }

    public void setAdd_event(List<AddEventBean> add_event) {
        this.add_event = add_event;
    }

    public List<EventBean> getEventBean() {
        List<EventBean> eventBeans = new ArrayList<>();
        EventBean eventBean = new EventBean();
        eventBean.type = EventBean.TYPE_PENDING;
        eventBean.mPendingEventBean = pending_event;
        eventBeans.add(eventBean);
        eventBean = new EventBean();
        eventBean.type = EventBean.TYPE_ADD;
        eventBean.mAddEventBean = add_event;
        eventBeans.add(eventBean);
        eventBean = new EventBean();
        eventBean.type = EventBean.TYPE_CAR_REPORT;
        eventBean.mAddCarEvent = add_car_event;
        eventBeans.add(eventBean);

        return eventBeans;
    }


    public static class EventBean {
        public static final int TYPE_PENDING = 10;
        public static final int TYPE_ADD = 11;
        public static final int TYPE_CAR_REPORT = 12;

        public List<PendingEventBean> mPendingEventBean;
        public List<AddEventBean> mAddEventBean;
        public List<AddCarEvent> mAddCarEvent;

        public int type;
    }

    public static class PendingEventBean {
        /**
         * title : 指派11
         * end_date : 2019-04-11
         * status : 2
         */

        private String title;
        private String end_date;
        private int status;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class AddEventBean {
        /**
         * title : 指派22
         * end_date : 2019-04-11
         * status : 5
         */

        private String title;
        private String end_date;
        private int status;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class AddCarEvent{
        private String car_name;
        private String end_time;
        private int status;

        public String getCar_name() {
            return car_name;
        }

        public void setCar_name(String car_name) {
            this.car_name = car_name;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
