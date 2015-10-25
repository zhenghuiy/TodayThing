package com.zhenghuiyan.todaything.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zhenghuiyan.todaything.R;

/**
 * Created by zhenghuiyan on 2015/3/13.
 */
public abstract class BaseToolbarActivity extends ActionBarActivity {
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                goBackToMain();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected abstract void init();

    protected void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void goBackToMain() {
        finish();
    }
}
