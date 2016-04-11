package com.example.peter.myapplication;

import java.util.Date;

/**
 * Created by peter on 2016/3/24.
 */
public class LogEntity implements java.io.Serializable {
    private long id;
//    private String targetName;
//    private int point;
    private int logId;
    //    private int attributes;
    private Date date;


    public LogEntity() {
    }

    public LogEntity(long id, String targetName, int logId, Date date) {
        this.id = id;
//        this.targetName = targetName;
        this.logId = logId;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public String getTargetName() {
//        return targetName;
//    }
//
//    public void setTargetName(String targetName) {
//        this.targetName = targetName;
//    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
