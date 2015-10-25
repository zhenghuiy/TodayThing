package com.zhenghuiyan.todaything.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zhenghuiyan.todaything.bean.Thing;
import com.zhenghuiyan.todaything.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zhenghuiyan on 2015/1/27.
 */
public class ScheduleTable {
    public static final String CLASS_NAME = "ScheduleTable";

    public static final String CREATE_TABLE_SQL = "CREATE TABLE " +
            ScheduleContract.ScheduleContractEntry.TABLE_NAME +
            "(" +
            ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID +
            " Integer not null primary key autoincrement," +
            ScheduleContract.ScheduleContractEntry.COLUMN_NAME_STIME +
            " TEXT NOT NULL," + ScheduleContract.ScheduleContractEntry.COLUMN_NAME_CONTENT +
            " TEXT NOT NULL," + ScheduleContract.ScheduleContractEntry.COLUMN_NAME_FROM_TIME +
            " TEXT NOT NULL," +
            ScheduleContract.ScheduleContractEntry.COLUMN_NAME_TO_TIME +
            " TEXT NOT NULL," + ScheduleContract.ScheduleContractEntry.COLUMN_NAME_COMPLETE_DATE +
            " TEXT default '" + ScheduleContract.ScheduleContractEntry.DEFAULT_DATE + "'," + ScheduleContract.ScheduleContractEntry.COLUMN_NAME_THING_ID +
            " INTEGER NOT NULL," + ScheduleContract.ScheduleContractEntry.COLUMN_NAME_WEEK_NUM + "" +
            " INTEGER DEFAULT 0)";

    public ScheduleTable() {

    }

    public static void createTable(SQLiteDatabase db) {
        if (db != null) {
            db.execSQL(CREATE_TABLE_SQL);
        } else {
            Log.v(CLASS_NAME, "create table failed!");
        }
    }

    public static void addItem(SQLiteDatabase db, Thing t) {
        ContentValues values = new ContentValues();
        values.put(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_CONTENT, t.getContent());
        values.put(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_STIME, t.getsTime());
        values.put(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_FROM_TIME, t.getFromTime().toString(false));
        values.put(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_TO_TIME, t.getToTime().toString(false));
        values.put(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_THING_ID, t.getThing_id());
        values.put(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_WEEK_NUM, t.getWeekNum());

        db.insert(ScheduleContract.ScheduleContractEntry.TABLE_NAME, null, values);
    }

    public static void completeItemById(SQLiteDatabase db, int id) {
        ContentValues values = new ContentValues();
        values.put(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_COMPLETE_DATE, DateUtil.dateToString(new Date()));
        String whereClause =  ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID + "=?";
        String[] whereArgs = {String.valueOf(id)};
        db.update(ScheduleContract.ScheduleContractEntry.TABLE_NAME, values, whereClause, whereArgs);
    }

    public static void deleteItemById(SQLiteDatabase db, int id) {
        String whereClause =  ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID + "=?";
        String[] whereArgs = {String.valueOf(id)};
        db.delete(ScheduleContract.ScheduleContractEntry.TABLE_NAME, whereClause, whereArgs);
    }

    public static void updateItem(SQLiteDatabase db, Thing t) {
        ContentValues values = new ContentValues();
        values.put(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_CONTENT, t.getContent());
        values.put(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_STIME, t.getsTime());
        values.put(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_FROM_TIME, t.getFromTime().toString(false));
        values.put(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_TO_TIME, t.getToTime().toString(false));
        values.put(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_COMPLETE_DATE, t.getComplete_date());
        values.put(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_WEEK_NUM, t.getWeekNum());
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(t.getId())};

        db.update(ScheduleContract.ScheduleContractEntry.TABLE_NAME, values, whereClause, whereArgs);
    }

    public static ArrayList<Thing> getAllItem(SQLiteDatabase db) {
        ArrayList<Thing> things = new ArrayList<Thing>();

        String[] columns = {ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_CONTENT, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_STIME, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_FROM_TIME, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_TO_TIME, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_COMPLETE_DATE, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_THING_ID, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_WEEK_NUM};
        String selection = null;

        Cursor cursor = db.query(ScheduleContract.ScheduleContractEntry.TABLE_NAME, columns, selection, null, null, null, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID));
                String sTime = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_STIME));
                String content = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_CONTENT));
                String fromTime = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_FROM_TIME));
                String toTime = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_TO_TIME));
                String complete_date = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_COMPLETE_DATE));
                int thing_id = cursor.getInt(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_THING_ID));
                int weekNum = cursor.getInt(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_WEEK_NUM));

                things.add(new Thing(id, sTime, content, fromTime, toTime, thing_id, complete_date, weekNum));
            } while(cursor.moveToNext());
        }

        cursor.close();

        return things;
    }
    /**
     * todo: 将日期的存储格式改为int,方便比较日期的前后，目前没做日期的筛选，只是简单的全部取出
     * */
    public static ArrayList<Thing> getOnceUncompletedItem(SQLiteDatabase db) {
        ArrayList<Thing> things = new ArrayList<Thing>();

        String[] columns = {ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_CONTENT, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_STIME, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_FROM_TIME, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_TO_TIME, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_COMPLETE_DATE, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_THING_ID, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_WEEK_NUM};
        String selection = ScheduleContract.ScheduleContractEntry.COLUMN_NAME_WEEK_NUM + "=? AND " + ScheduleContract.ScheduleContractEntry.COLUMN_NAME_COMPLETE_DATE + "=?";
        String[] selectionArgs = {"0", ScheduleContract.ScheduleContractEntry.DEFAULT_DATE};

        Cursor cursor = db.query(ScheduleContract.ScheduleContractEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID));
                String sTime = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_STIME));
                String content = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_CONTENT));
                String fromTime = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_FROM_TIME));
                String toTime = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_TO_TIME));
                String complete_date = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_COMPLETE_DATE));
                int thing_id = cursor.getInt(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_THING_ID));
                int weekNum = cursor.getInt(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_WEEK_NUM));

                things.add(new Thing(id, sTime, content, fromTime, toTime, thing_id, complete_date, weekNum));
            } while(cursor.moveToNext());
        }

        cursor.close();

        return things;
    }

    public static ArrayList<Thing> getAllRepeatItem(SQLiteDatabase db) {
        ArrayList<Thing> things = new ArrayList<Thing>();

        String[] columns = {ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_CONTENT, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_STIME, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_FROM_TIME, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_TO_TIME, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_COMPLETE_DATE, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_THING_ID, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_WEEK_NUM};
        String selection = ScheduleContract.ScheduleContractEntry.COLUMN_NAME_WEEK_NUM + ">?";
        String[] selectionArgs = {"0"};

        Cursor cursor = db.query(ScheduleContract.ScheduleContractEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID));
                String sTime = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_STIME));
                String content = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_CONTENT));
                String fromTime = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_FROM_TIME));
                String toTime = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_TO_TIME));
                String complete_date = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_COMPLETE_DATE));
                int thing_id = cursor.getInt(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_THING_ID));
                int weekNum = cursor.getInt(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_WEEK_NUM));

                things.add(new Thing(id, sTime, content, fromTime, toTime, thing_id, complete_date, weekNum));
            } while(cursor.moveToNext());
        }

        cursor.close();

        return things;
    }

    public static ArrayList<Thing> getTodayUnCompletedItem(SQLiteDatabase db) {
        ArrayList<Thing> things = new ArrayList<Thing>();
        final String[] columns = {ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_CONTENT, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_STIME, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_FROM_TIME, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_TO_TIME, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_COMPLETE_DATE, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_THING_ID, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_WEEK_NUM};
        // select today and uncompleted thing
        String selectionToday = ScheduleContract.ScheduleContractEntry.COLUMN_NAME_STIME + "=?" + " AND " + ScheduleContract.ScheduleContractEntry.COLUMN_NAME_COMPLETE_DATE + "<>?";
        String[] selectionTodayArgs = {DataManager.TODAY_STR, DataManager.TODAY_STR};

        Cursor cursorToday = db.query(ScheduleContract.ScheduleContractEntry.TABLE_NAME, columns, selectionToday, selectionTodayArgs, null, null, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID);
        if (cursorToday.getCount() != 0) {
            cursorToday.moveToFirst();
            do {
                int id = cursorToday.getInt(cursorToday.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID));
                String sTime = cursorToday.getString(cursorToday.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_STIME));
                String content = cursorToday.getString(cursorToday.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_CONTENT));
                String fromTime = cursorToday.getString(cursorToday.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_FROM_TIME));
                String toTime = cursorToday.getString(cursorToday.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_TO_TIME));
                String complete_date = cursorToday.getString(cursorToday.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_COMPLETE_DATE));
                int thing_id = cursorToday.getInt(cursorToday.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_THING_ID));
                int weekNum = cursorToday.getInt(cursorToday.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_WEEK_NUM));

                things.add(new Thing(id, sTime, content, fromTime, toTime, thing_id, complete_date, weekNum));
            } while(cursorToday.moveToNext());
        }
        cursorToday.close();

        //select repeat thing
        String selectionRepeat = ScheduleContract.ScheduleContractEntry.COLUMN_NAME_WEEK_NUM+ ">?" + " AND " + ScheduleContract.ScheduleContractEntry.COLUMN_NAME_COMPLETE_DATE + "<>?" + " AND " + ScheduleContract.ScheduleContractEntry.COLUMN_NAME_STIME + "<>?";
        String[] selectionRepeatArgs = {String.valueOf(0), DataManager.TODAY_STR, DataManager.TODAY_STR};
        int dayOfWeek = DateUtil.getDayOfWeek(DateUtil.parseDate(DataManager.TODAY_STR));

        Cursor cursor = db.query(ScheduleContract.ScheduleContractEntry.TABLE_NAME, columns, selectionRepeat, selectionRepeatArgs, null, null, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID));
                String sTime = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_STIME));
                String content = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_CONTENT));
                String fromTime = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_FROM_TIME));
                String toTime = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_TO_TIME));
                String complete_date = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_COMPLETE_DATE));
                int thing_id = cursor.getInt(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_THING_ID));
                int weekNum = cursor.getInt(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_WEEK_NUM));

                Thing t = new Thing(id, sTime, content, fromTime, toTime, thing_id, complete_date, weekNum);
                if (t.isDayOfWeekRepeat(dayOfWeek)) {
                    things.add(t);
                }
            } while(cursor.moveToNext());
        }
        cursor.close();

        return things;
    }

//    public static ArrayList<Thing> getAllItemByDate(SQLiteDatabase db, String date) {
//        ArrayList<Thing> things = new ArrayList<Thing>();
//
//        String[] columns = {ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_CONTENT, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_STIME, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_FROM_TIME, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_TO_TIME, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_COMPLETE, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_THING_ID};
//        String selection = ScheduleContract.ScheduleContractEntry.COLUMN_NAME_STIME + "=?";
//        String[] selectionArgs = {date};
//
//        Cursor cursor = db.query(ScheduleContract.ScheduleContractEntry.TABLE_NAME, columns, selection, selectionArgs,null, null, ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID);
//        cursor.moveToFirst();
//        do {
//            int id = cursor.getInt(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_ID));
//            String sTime = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_STIME));
//            String content = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_CONTENT));
//            String fromTime = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_FROM_TIME));
//            String toTime = cursor.getString(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_TO_TIME));
//            int complete = cursor.getInt(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_COMPLETE));
//            int thing_id = cursor.getInt(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_THING_ID));
//            int weekNum = cursor.getInt(cursor.getColumnIndex(ScheduleContract.ScheduleContractEntry.COLUMN_NAME_WEEK_NUM));
//
//            things.add(new Thing(id, sTime, content, fromTime, toTime, thing_id, complete, weekNum));
//        } while(cursor.moveToNext());
//        cursor.close();
//
//        return things;
//    }


}
