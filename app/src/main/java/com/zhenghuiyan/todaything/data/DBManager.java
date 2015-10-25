package com.zhenghuiyan.todaything.data;

import android.database.sqlite.SQLiteDatabase;

import com.zhenghuiyan.todaything.bean.Thing;
import com.zhenghuiyan.todaything.util.MiscUtil;

import java.util.ArrayList;

/**
 * Created by zhenghuiyan on 2015/1/28.
 */
public class DBManager {
    private ThingDBHelper mHelper;

    private static class SingletonHolder{
        private static final DBManager instance = new DBManager();
    }

    public static DBManager getInstance() {
        return SingletonHolder.instance;
    }

    private DBManager() {
        mHelper = new ThingDBHelper(MiscUtil.getAppContext());
    }

    public void addOneThing(Thing t) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ScheduleTable.addItem(db, t);
//        if (t.isWeekNumSet()) {
//            RepeatRecordTable.addItem(db, t);
//        }

    }

    public void updateOneThing(Thing t) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ScheduleTable.updateItem(db, t);
    }

    public void completeOneThing(Thing t) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int id = t.getId();
        ScheduleTable.completeItemById(db, id);
    }

    public void deleteOneThing(Thing t) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int id = t.getId();
        ScheduleTable.deleteItemById(db, id);
    }

    public ArrayList<Thing> getAllThings() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        return ScheduleTable.getAllItem(db);
    }

    public ArrayList<Thing> getTodayUnCompletedThings() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        return ScheduleTable.getTodayUnCompletedItem(db);
    }

    public ArrayList<Thing> getOnceUncompletedThings() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        return ScheduleTable.getOnceUncompletedItem(db);
    }

    public ArrayList<Thing> getAllRepeatThings() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        return ScheduleTable.getAllRepeatItem(db);
    }

//    public ArrayList<Thing> getTodayThings() {
//        SQLiteDatabase db = mHelper.getReadableDatabase();
//        Date d = new Date();
//
//        return ScheduleTable.getAllItemByDate(db, DateUtil.dateToString(d));
//    }


}
