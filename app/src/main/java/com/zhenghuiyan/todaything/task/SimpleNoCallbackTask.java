package com.zhenghuiyan.todaything.task;

import android.os.AsyncTask;

/**
 * Created by zhenghuiyan on 2015/4/14.
 */
public abstract class SimpleNoCallbackTask extends AsyncTask<Void, Void, Void> {

    protected SimpleNoCallbackTask() {
    }

    @Override
    protected void onPreExecute() {
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
    protected void onPostExecute(Void aVoid) {}

    protected abstract void doInBack();
}