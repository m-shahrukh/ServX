package pl.servx.servx.Model;

import android.support.annotation.NonNull;

public class service_item implements Comparable<service_item> {
    public String car;
    public String oil;
    public String wash;
    public String location;
    public String date;
    public String time;
    public String status;
    public Integer reqid;


    public service_item() {
    }

    public service_item(String car, String oil, String wash, String location, String date, String time, String status) {
        this.car = car;
        this.oil = oil;
        this.wash = wash;
        this.location = location;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getCar() {return car;}

    public void setCar(String car) {this.car = car;}

    public String getOil() {
        return oil;
    }

    public void setOil(String oil) {
        this.oil = oil;
    }

    public String getWash() {
        return wash;
    }

    public void setWash(String wash) {
        this.wash = wash;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int compareTo(@NonNull service_item o) {
        return this.reqid.compareTo(o.reqid);
    }
}
