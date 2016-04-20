package com.example.peter.myapplication.data;

/**
 * Created by peter on 2016/3/24.
 */
public class UserEntity implements java.io.Serializable {
    private long id;
    private String userName;
    private String userEmail;
    private String userFbId;
    private String passWord;
    private int userPoint;


    public UserEntity() {
    }

    public UserEntity(long id, String userName, String userEmail, String userFbId, String passWord, int userPoint) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userFbId = userFbId;
        this.passWord = passWord;
        this.userPoint = userPoint;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public int getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(int userPoint) {
        this.userPoint = userPoint;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public String getUserFbId() {
        return userFbId;
    }

    public void setUserFbId(String userFbId) {
        this.userFbId = userFbId;
    }
}
