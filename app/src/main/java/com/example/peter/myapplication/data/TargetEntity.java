package com.example.peter.myapplication.data;

/**
 * Created by peter on 2016/3/24.
 */
public class TargetEntity implements java.io.Serializable {
    private long id;
    private String targetName;
    private int point;
    private int attributes;
    private String photoFileName;


    public TargetEntity() {
    }

    public TargetEntity(long id, String targetName, int point, int attributes) {
        this.id = id;
        this.targetName = targetName;
        this.point = point;
        this.attributes = attributes;
    }

    public TargetEntity(long id, String targetName, int point, int attributes, String photoFileName) {
        this.id = id;
        this.targetName = targetName;
        this.point = point;
        this.attributes = attributes;
        this.photoFileName = photoFileName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getAttributes() {
        return attributes;
    }

    public void setAttributes(int attributes) {
        this.attributes = attributes;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }
}
