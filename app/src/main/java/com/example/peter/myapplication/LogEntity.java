package com.example.peter.myapplication;

import java.util.Date;

/**
 * Created by peter on 2016/3/24.
 */
public class LogEntity implements java.io.Serializable {
    private long id;
    private String targetName;
    private int point;
    private long entityId;
    //    private int attributes;
    private Date date;




    public LogEntity() {
    }

    public LogEntity(long id, int entityId, Date date) {
        this.id = id;
//        this.targetName = targetName;
        this.entityId = entityId;
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

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
