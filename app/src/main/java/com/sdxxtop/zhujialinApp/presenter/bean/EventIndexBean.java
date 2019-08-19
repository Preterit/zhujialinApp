package com.sdxxtop.zhujialinApp.presenter.bean;

import java.util.List;

public class EventIndexBean {

    /**
     * num : 3
     * event : [{"event_id":4,"title":"指派22","end_date":"2019-04-11","status":5,"place":"test地址2","add_time":"2019-04-10 12:13:32","important_type":1},{"event_id":3,"title":"指派11","end_date":"2019-04-11","status":2,"place":"test地址2","add_time":"2019-04-10 12:13:27","important_type":1},{"event_id":1,"title":"test","end_date":"0000-00-00","status":1,"place":"test地址","add_time":"2019-04-10 12:12:39","important_type":1}]
     */

    private int num;
    private List<EventBean> event;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<EventBean> getEvent() {
        return event;
    }

    public void setEvent(List<EventBean> event) {
        this.event = event;
    }

    public static class EventBean {
        /**
         * event_id : 4
         * title : 指派22
         * end_date : 2019-04-11
         * status : 5
         * place : test地址2
         * add_time : 2019-04-10 12:13:32
         * important_type : 1
         */

        private int id;
        private int event_id;
        private String title;
        private String end_date;
        private String end_time;
        private int status;
        private String place;
        private String add_time;
        private String important_type;
        private String address;
        private int is_car;

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getIs_car() {
            return is_car;
        }

        public void setIs_car(int is_car) {
            this.is_car = is_car;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getEvent_id() {
            return event_id;
        }

        public void setEvent_id(int event_id) {
            this.event_id = event_id;
        }

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

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getImportant_type() {
            return important_type;
        }

        public void setImportant_type(String important_type) {
            this.important_type = important_type;
        }
    }
}
