package com.zhenghuiyan.todaything;

import android.app.Application;
import com.zhenghuiyan.todaything.util.MiscUtil;

/**
 * Created by zhenghuiyan on 2015/1/28.
 */
public class TodayThingApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        MiscUtil.setAppContext(getApplicationContext());
    }
}
