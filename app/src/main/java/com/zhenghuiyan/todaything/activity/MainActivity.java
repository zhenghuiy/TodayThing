package com.zhenghuiyan.todaything.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhenghuiyan.todaything.constant.FragmentName;
import com.zhenghuiyan.todaything.R;
import com.zhenghuiyan.todaything.adapter.MenuListViewAdapter;
import com.zhenghuiyan.todaything.bean.ThingMenuItem;
import com.zhenghuiyan.todaything.data.DataManager;
import com.zhenghuiyan.todaything.fragment.AllFragment;
import com.zhenghuiyan.todaything.fragment.TodayFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    private Toolbar mToolbar;

    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    private Fragment mTodayFragment;

    private Fragment mAllFragment;

    private Fragment mSettingFragment;

    private List<ThingMenuItem> mMenus;

    private MenuListViewAdapter mMenuAdapter;

    private DataManager mDataManager;

    private ListView mMenuListView;

    private FragmentName mCurrentFragmentName;

    private FragmentName mLastFragmentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataManager = DataManager.getInstance();
        initViews();
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                showCurrentFragment();
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //init Menu
        mMenuListView = (ListView) findViewById(R.id.menu_listview);
        mMenus = mDataManager.getMenuData();
        mMenuAdapter = new MenuListViewAdapter(mMenus);
        mMenuListView.setAdapter(mMenuAdapter);
        mMenuListView.setOnItemClickListener(this);

        mTodayFragment = new TodayFragment();
        //set default fragment
        getFragmentManager().beginTransaction().add(R.id.main_content, mTodayFragment).commit();
        mCurrentFragmentName = FragmentName.Today;
        mLastFragmentName = FragmentName.Today;

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mDataManager.getmCurrentMenuPosition() != position) {
            mMenus.get(mDataManager.getmCurrentMenuPosition()).setSelected(false);
            mDataManager.setmCurrentMenuPosition(position);
            ThingMenuItem item = mMenus.get(position);
            mCurrentFragmentName = item.getFragmentName();
            item.setSelected(true);
            mDrawerLayout.closeDrawers();
            mMenuAdapter.notifyDataSetChanged();
        }

    }

    private void showCurrentFragment() {
        //判断是否没有改变当前显示的fragment，以防破坏当前显示的Fragment的状态
        if (mCurrentFragmentName == mLastFragmentName) {
            return;
        }
        mLastFragmentName = mCurrentFragmentName;
        switch(mCurrentFragmentName) {
            case Today:
                if (mTodayFragment == null) {
                    mTodayFragment = new TodayFragment();
                }
                showContent(mTodayFragment);
                break;
            case All:
                if (mAllFragment == null) {
                    mAllFragment = new AllFragment();
                }
                showContent(mAllFragment);
                break;
        }
    }

    private void showContent(Fragment fragment) {
        if (fragment == null) {
            return;
        }

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.main_content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
