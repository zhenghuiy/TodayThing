package com.zhenghuiyan.todaything.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;

import com.gc.materialdesign.widgets.ProgressDialog;
import com.gc.materialdesign.widgets.SnackBar;
import com.zhenghuiyan.todaything.R;

/**
 * Created by zhenghuiyan on 2015/1/23.
 */
public class UIUtil {

    private UIUtil() {}

    /**
    * return size.x  size.y
    * */
    private static Point getScreenSize(Activity a) {
        if (a == null) {
            return null;
        }
        Display display = a.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size;
    }

    public static int getScreenWidth(Activity a) {
        int width = 0;
        Point p = getScreenSize(a);

        if (p != null) {
            width = p.x;
        }

        return width;
    }

    public static int getScreenHeight(Activity a) {
        int height = 0;
        Point p = getScreenSize(a);

        if (p != null) {
            height = p.y;
        }

        return height;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * show dialog
     *
     * */
    public static ProgressDialog showProgressDialog(Activity activity) {
        //activity = modifyDialogContext(activity);

        ProgressDialog dialog = new ProgressDialog(activity, activity.getResources().getString(R.string.loading));
        dialog.setCancelable(true);
        if (activity.isFinishing() == false) {
            dialog.show();
        }
        return dialog;
    }

    public static void showSnackBar(Activity activity, String message, SnackBar.OnHideListener onHideListener) {
        SnackBar snackBar = new SnackBar(activity, message);
        snackBar.show();
        if (onHideListener != null) {
            snackBar.setOnhideListener(onHideListener);
        }
    }
}
