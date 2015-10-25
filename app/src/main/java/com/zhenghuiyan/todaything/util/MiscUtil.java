package com.zhenghuiyan.todaything.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zhenghuiyan.todaything.R;

/**
 * Created by zhenghuiyan on 2015/1/28.
 */
public class MiscUtil {
    public static Context appContext = null;

    private MiscUtil() {}

    public static void setAppContext(Context c) {
        appContext = c;
    }

    public static Context getAppContext() {
        return appContext;
    }

    /**
     *
     * */

     public static void goToActivity(Activity a, Fragment f, Class<?> b, Bundle bundle, boolean isForResult) {
        Intent intent = new Intent(a, b);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (isForResult && (f != null)) {
            f.startActivityForResult(intent, 0);
        } else if (isForResult && (f == null)) {
            a.startActivityForResult(intent, 0);
        } else {
            a.startActivity(intent);
        }
        a.overridePendingTransition(R.anim.zero_scale_one, R.anim.keep_status);
    }
}
