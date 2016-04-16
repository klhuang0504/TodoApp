package com.example.peter.myapplication.data;

/**
 * Created by peter on 2016/3/24.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

// 資料功能類別
public class TargetDAO {
    // 表格名稱
    public static final String TABLE_NAME = "target";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String TARGET_NAME = "targetName";
    public static final String POINT = "point";
    public static final String ATTRIBUTES = "attributes";

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TARGET_NAME + " TEXT NOT NULL, " +
                    POINT + " INTEGER NOT NULL, " +
                    ATTRIBUTES + " TEXT NOT NULL) ";

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public TargetDAO(Context context) {
        db = MyDBHelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    // 新增參數指定的物件
    public TargetEntity insert(TargetEntity targetEntity) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(TARGET_NAME, targetEntity.getTargetName());
        cv.put(POINT, targetEntity.getPoint());
        cv.put(ATTRIBUTES, targetEntity.getAttributes());

        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long id = db.insert(TABLE_NAME, null, cv);

        // 設定編號
        targetEntity.setId(id);
        // 回傳結果
        return targetEntity;
    }

    // 修改參數指定的物件
    public boolean update(TargetEntity targetEntity) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(TARGET_NAME, targetEntity.getTargetName());
        cv.put(POINT, targetEntity.getPoint());
        cv.put(ATTRIBUTES, targetEntity.getAttributes());


        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + targetEntity.getId();

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
    public ArrayList<TargetEntity> getAll() {
        ArrayList<TargetEntity> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    // 取得指定編號的資料物件
    public TargetEntity get(long id) {
        // 準備回傳結果用的物件
        TargetEntity targetEntity = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            targetEntity = getRecord(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return targetEntity;
    }

    // 取得指定編號的資料物件
    public TargetEntity getByTargetName(String targetName) {
        // 準備回傳結果用的物件
        TargetEntity targetEntity = null;
        // 使用編號為查詢條件
        String where = TARGET_NAME + " = '" + targetName + "'";
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            targetEntity = getRecord(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return targetEntity;
    }

    // 把Cursor目前的資料包裝為物件
    public TargetEntity getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        TargetEntity result = new TargetEntity();

        result.setId(cursor.getLong(0));
        result.setTargetName(cursor.getString(1));
        result.setPoint(cursor.getInt(2));
        result.setAttributes(cursor.getInt(3));

        // 回傳結果
        return result;
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

    public ArrayList<TargetEntity> getGoodTargetList() {
        ArrayList<TargetEntity> resultList = new ArrayList<TargetEntity>();
        String where = ATTRIBUTES + " = 0 ";
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

    public ArrayList<TargetEntity> getBadTargetList() {
        ArrayList<TargetEntity> resultList = new ArrayList<TargetEntity>();
        String where = ATTRIBUTES + " = 1 ";
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

    public ArrayList<TargetEntity> getRewardList() {
        ArrayList<TargetEntity> resultList = new ArrayList<TargetEntity>();
        String where = ATTRIBUTES + " = 2 ";
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

    // 建立範例資料
    public void sample() {
        TargetEntity targetEntity = new TargetEntity(0, "健身", 1, 0);
        TargetEntity targetEntity2 = new TargetEntity(0, "打電玩", 2, 1);
        TargetEntity targetEntity3 = new TargetEntity(0, "出國玩", 3, 2);
        TargetEntity targetEntity4 = new TargetEntity(0, "唸書", 4, 0);
        TargetEntity targetEntity5 = new TargetEntity(0, "吃垃圾食物", 1, 1);
        TargetEntity targetEntity6 = new TargetEntity(0, "買新手機", 2, 2);

        insert(targetEntity);
        insert(targetEntity2);
        insert(targetEntity3);
        insert(targetEntity4);
        insert(targetEntity5);
        insert(targetEntity6);
    }

}