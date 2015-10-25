package com.zhenghuiyan.todaything.data;

import com.zhenghuiyan.todaything.bean.Thing;
import com.zhenghuiyan.todaything.bean.ThingMenuItem;
import com.zhenghuiyan.todaything.constant.MenuItems;
import com.zhenghuiyan.todaything.util.DateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by zhenghuiyan on 2015/1/28.
 */
public class DataManager {
    public static final String TODAY_STR = DateUtil.dateToString(new Date());

    private ArrayList<Thing> mThings = null;

    private DBManager mDBManager = null;

    private int mCurrentMenuPosition = 0;



    private static class SingletonHolder{
        private static final DataManager instance = new DataManager();
    }

    public static DataManager getInstance() {
        return SingletonHolder.instance;
    }

    private DataManager() {
        mDBManager = DBManager.getInstance();
    }

    public int getmCurrentMenuPosition() {
        return mCurrentMenuPosition;
    }

    public void setmCurrentMenuPosition(int mCurrentMenuPosition) {
        this.mCurrentMenuPosition = mCurrentMenuPosition;
    }

    public void updateThings() {
        if (mThings == null) {
            mThings = new ArrayList<Thing>();
        }
        mThings = mDBManager.getTodayUnCompletedThings();
        Collections.sort(mThings);
    }

    public List<Thing> getAllThings() {
        return mDBManager.getAllThings();
    }

    public void updateThings(Thing t, int position) {
        if (mThings == null) {
            updateThings();
        }

        if (position != -1) {
            mThings.remove(position);
        }

        mThings.add(t);
        Collections.sort(mThings);
    }

    public void deleteAndUpdateThings(int position) {
        if (mThings == null) {
            updateThings();
        }
        if (position != -1) {
            mThings.remove(position);
        }
    }

    public ArrayList<Thing> getThings() {
        if (mThings == null) {
            updateThings();
        }

        return mThings;
    }

    public ArrayList<Thing> getOnceUncompletedThings() {

        return mDBManager.getOnceUncompletedThings();
    }

    public ArrayList<Thing> getAllRepeatThings(){

        return mDBManager.getAllRepeatThings();
    }

    public void completeThing(Thing thing) {
        mDBManager.completeOneThing(thing);
    }

    public void deleteThing(Thing t) {
        mDBManager.deleteOneThing(t);
    }

    public List<ThingMenuItem> getMenuData() {
        return MenuItems.getMenuItems();
    }

}
