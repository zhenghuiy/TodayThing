package com.zhenghuiyan.todaything.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhenghuiyan on 2015/1/27.
 */
public class ThingDBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;

    public static final String DB_NAME = "schedule.db";

    public ThingDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ScheduleTable.createTable(db);
//        RepeatRecordTable.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
