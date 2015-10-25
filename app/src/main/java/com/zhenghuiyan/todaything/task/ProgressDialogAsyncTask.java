package com.zhenghuiyan.todaything.task;

import android.app.Activity;
import android.os.AsyncTask;

import com.gc.materialdesign.widgets.ProgressDialog;
import com.zhenghuiyan.todaything.util.UIUtil;

public abstract class ProgressDialogAsyncTask extends AsyncTask<Void, Void, Void> {
  ProgressDialog mDialog;
  protected Activity mActivity;
  boolean mShowDialog = true;

  protected ProgressDialogAsyncTask(Activity activity) {
    mActivity = activity;
  }

  protected ProgressDialogAsyncTask(Activity activity, boolean showDialog) {
    mActivity = activity;
    mShowDialog = showDialog;
  }


  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    if (mShowDialog) {
      mDialog = UIUtil.showProgressDialog(mActivity);
    }
  }

  @Override
  protected Void doInBackground(Void... params) {
    try {
      doInBack();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  protected void onPostExecute(Void aVoid) {
    super.onPostExecute(aVoid);
    if (mShowDialog && mDialog.isShowing()) {
       mDialog.dismiss();
    }
    onPost();
  }

  protected abstract void doInBack() throws Exception;

  protected abstract void onPost();
}
