package com.example.peter.myapplication;

/**
 * Created by peter on 2016/3/24.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

// 資料功能類別
public class UserDAO {
    // 表格名稱
    public static final String TABLE_NAME = "item";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String USER_NAME = "userName";
    public static final String PASS_WORD = "passWord";
    public static final String USER_POINT = "userPoint";


    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_NAME + " TEXT NOT NULL, " +
                    PASS_WORD + " TEXT NOT NULL, " +
                    USER_POINT + " INTEGER NOT NULL) " ;

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public UserDAO(Context context) {
        db = MyDBHelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    // 新增參數指定的物件
    public UserEntity insert(UserEntity userEntity) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(USER_NAME, userEntity.getUserName());
        cv.put(PASS_WORD, userEntity.getPassWord());
        cv.put(USER_POINT, userEntity.getUserPoint());


        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long id = db.insert(TABLE_NAME, null, cv);

        // 設定編號
        userEntity.setId(id);
        // 回傳結果
        return userEntity;
    }

    // 修改參數指定的物件
    public boolean update(UserEntity item) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(USER_NAME, item.getUserName());
        cv.put(PASS_WORD, item.getPassWord());
        cv.put(USER_POINT, item.getUserPoint());


        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + item.getId();

        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    // 刪除參數指定編號的資料
    public boolean delete(long id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where, null) > 0;
    }

    // 讀取所有記事資料
    public List<UserEntity> getAll() {
        List<UserEntity> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    // 取得指定編號的資料物件
    public UserEntity get(long id) {
        // 準備回傳結果用的物件
        UserEntity userEntity = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            userEntity = getRecord(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return userEntity;
    }

    // 取得指定編號的資料物件
    public UserEntity getByUserName(String userName) {
        // 準備回傳結果用的物件
        UserEntity userEntity = null;
        // 使用編號為查詢條件
        String where = USER_NAME + " = '" + userName + "'";
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            userEntity = getRecord(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return userEntity;
    }

    // 把Cursor目前的資料包裝為物件
    public UserEntity getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        UserEntity result = new UserEntity();

        result.setId(cursor.getLong(0));
        result.setUserName(cursor.getString(1));
        result.setPassWord(cursor.getString(2));
        result.setUserPoint(cursor.getInt(3));


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

    // 建立範例資料
    public void sample() {
        UserEntity userEntity1 = new UserEntity(0, "aaa", "111", 0);
        UserEntity userEntity2 = new UserEntity(0, "bbb", "222", 0);
        UserEntity userEntity3 = new UserEntity(0, "ccc", "333", 0);
        UserEntity userEntity4 = new UserEntity(0, "ddd", "444", 0);

        insert(userEntity1);
        insert(userEntity2);
        insert(userEntity3);
        insert(userEntity4);
    }

}