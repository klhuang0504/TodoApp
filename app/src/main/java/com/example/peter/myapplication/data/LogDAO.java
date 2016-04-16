package com.example.peter.myapplication.data;

/**
 * Created by peter on 2016/3/24.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// 資料功能類別
public class LogDAO {
    // 表格名稱
    public static final String TABLE_NAME = "log";

    public static final String TARGET_TABLE_NAME = "target";


    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String ENTITY_ID = "entityId";
    public static final String DATE = "date";

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ENTITY_ID + " LONG NOT NULL, " +
                    DATE + " DATETIME NOT NULL) ";

    // 資料庫物件
    private SQLiteDatabase db;
    SimpleDateFormat dateFormat;


    // 建構子，一般的應用都不需要修改
    public LogDAO(Context context) {
        db = MyDBHelper.getDatabase(context);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    // 新增參數指定的物件
    public LogEntity insert(LogEntity logEntity) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(ENTITY_ID, logEntity.getEntityId());
        cv.put(DATE, dateFormat.format(logEntity.getDate()));
        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long id = db.insert(TABLE_NAME, null, cv);

        // 設定編號
        logEntity.setId(id);
        // 回傳結果
        return logEntity;
    }

    // 修改參數指定的物件
    public boolean update(LogEntity logEntity) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(ENTITY_ID, logEntity.getEntityId());
        cv.put(DATE, dateFormat.format(logEntity.getDate()));


        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + logEntity.getId();

        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    // 刪除參數指定編號的資料
    public boolean delete(long id) {
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where, null) > 0;
    }

    // 讀取所有記事資料
    public ArrayList<LogEntity> getAll() {
        ArrayList<LogEntity> result = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, DATE + " DESC", null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    // 取得指定編號的資料物件
    public LogEntity get(long id) {
        // 準備回傳結果用的物件
        LogEntity logEntity = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            logEntity = getRecord(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return logEntity;
    }

    // 取得指定編號的資料物件
    public LogEntity getByEntityId(int entityId) {
        // 準備回傳結果用的物件
        LogEntity logEntity = null;
        // 使用編號為查詢條件
        String where = ENTITY_ID + " = " + entityId;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            logEntity = getRecord(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return logEntity;
    }

    // 把Cursor目前的資料包裝為物件
    public LogEntity getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        LogEntity resultLogEntity = new LogEntity();

        resultLogEntity.setId(cursor.getLong(0));
        resultLogEntity.setEntityId(cursor.getLong(1));

        try {
            resultLogEntity.setDate(dateFormat.parse(cursor.getString(2)));
        } catch (ParseException e) {
            resultLogEntity.setDate(new Date());
            Log.e("LogDao", "getRecord, dateFromt parse exception.");
        }
        // 回傳結果
        return resultLogEntity;
    }

    // 取得資料數量
    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }

    public ArrayList<LogEntity> getLogListToday(){
        ArrayList<LogEntity> resultList = new ArrayList<LogEntity>();

        String where = "date(" + DATE + ") = date('now') ";
        return resultList;
    }

    public ArrayList<LogEntity> getLogListSelectDay(Date date){
        ArrayList<LogEntity> resultList = new ArrayList<LogEntity>();

        String where = "date(" + DATE + ") = date('now') ";

        return resultList;
    }

    public ArrayList<LogEntity> getTargetLogListByAttributes(int attributes) {
        ArrayList<LogEntity> resultList = new ArrayList<LogEntity>();
        String where = ENTITY_ID + " in (SELECT _id FROM " + TARGET_TABLE_NAME + " WHERE attributes = " + attributes;
        // 執行查詢
        Cursor cursor = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        while (cursor.moveToNext()) {
            resultList.add(getRecord(cursor));
        }

        // 關閉Cursor物件
        cursor.close();

        return resultList;
    }
//
//    public ArrayList<LogEntity> getBadTargetList() {
//        ArrayList<LogEntity> resultList = new ArrayList<LogEntity>();
//        String where = ATTRIBUTES + " = 1 ";
//        // 執行查詢
//        Cursor cursor = db.query(
//                TABLE_NAME, null, where, null, null, null, null, null);
//
//        // 如果有查詢結果
//        while (cursor.moveToNext()) {
//            resultList.add(getRecord(cursor));
//        }
//
//        // 關閉Cursor物件
//        cursor.close();
//
//        return resultList;
//    }
//
//    public ArrayList<LogEntity> getRewardList() {
//        ArrayList<LogEntity> resultList = new ArrayList<LogEntity>();
//        String where = ATTRIBUTES + " = 2 ";
//        // 執行查詢
//        Cursor cursor = db.query(
//                TABLE_NAME, null, where, null, null, null, null, null);
//
//        // 如果有查詢結果
//        while (cursor.moveToNext()) {
//            resultList.add(getRecord(cursor));
//        }
//
//        // 關閉Cursor物件
//        cursor.close();
//
//        return resultList;
//    }

    // 建立範例資料
    public void sample() {
        LogEntity logEntity = new LogEntity(0, 1, new Date());
        insert(logEntity);
    }

}