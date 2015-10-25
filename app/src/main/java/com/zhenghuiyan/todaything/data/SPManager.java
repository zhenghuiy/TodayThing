package com.zhenghuiyan.todaything.data;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.zhenghuiyan.todaything.util.MiscUtil;

/**
 * Created by zhenghuiyan on 2015/2/2.
 */
public class SPManager {
    public SharedPreferences mSharedPreferences;

    public static final String LAST_THING_ID_KEY = "last_thing_id";

    public static final String LAST_SYNC_TIME = "last_sync_time";

    private static class SingletonHolder{
        private static final SPManager instance = new SPManager();
    }

    public static SPManager getInstance() {
        return SingletonHolder.instance;
    }

    private SPManager() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(MiscUtil.getAppContext());
    }

    public int getNextThingId() {
        int i = mSharedPreferences.getInt(LAST_THING_ID_KEY, 0);
        return ++i;
    }

    public void setLastThingId(int i) {
        mSharedPreferences.edit().putInt(LAST_THING_ID_KEY, i).commit();
    }

    public long getLastSyncTime() {
        return mSharedPreferences.getLong(LAST_SYNC_TIME, 0);
    }

    public void setLastSyncTime(long time) {
        mSharedPreferences.edit().putLong(LAST_SYNC_TIME, time).commit();
    }
}
