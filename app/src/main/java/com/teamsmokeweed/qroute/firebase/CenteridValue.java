package com.teamsmokeweed.qroute.firebase;

/**
 * Created by jongzazaal on 9/11/2559.
 */

public class CenteridValue {
    public String des, end_date, end_time, pic, placeName, placeType, start_date, start_time, titles, web;
    public String lat, lng;

    public CenteridValue(String des, String end_date, String end_time, String lat, String lng,
                         String pic, String placeName, String placeType, String start_date,
                         String start_time, String titles, String web) {
        this.des = des;
        this.end_date = end_date;
        this.end_time = end_time;
        this.lat = lat;
        this.lng = lng;
        this.pic = pic;
        this.placeName = placeName;
        this.placeType = placeType;
        this.start_date = start_date;
        this.start_time = start_time;
        this.titles = titles;
        this.web = web;
    }
    public CenteridValue(){}

    public String getDes() {
        return des;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getPic() {
        return pic;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlaceType() {
        return placeType;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getTitles() {
        return titles;
    }

    public String getWeb() {
        return web;
    }
}
