package com.zhenghuiyan.todaything.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zhenghuiyan.todaything.R;
import com.zhenghuiyan.todaything.adapter.AllListViewAdapter;
import com.zhenghuiyan.todaything.bean.Thing;
import com.zhenghuiyan.todaything.data.DataManager;

import java.util.ArrayList;

/**
 * Created by zhenghuiyan on 2015/2/14.
 */
public class AllFragment extends BaseFragment {
    private ListView mOnceListView;

    private ListView mReapteListView;

    private AllListViewAdapter mOnceAdatper;

    private AllListViewAdapter mRepeatAdapter;

    private ArrayList<Thing> mOnceThings;

    private ArrayList<Thing> mRepeatThings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all, container, false);
        initViews(rootView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mOnceThings = DataManager.getInstance().getOnceUncompletedThings();
                mRepeatThings = DataManager.getInstance().getAllRepeatThings();
            }
        }).start();

        return rootView;
    }

    private void initViews(View rootView) {
        mOnceListView = (ListView) rootView.findViewById(R.id.once_list);
        mReapteListView = (ListView) rootView.findViewById(R.id.repeat_list);
    }

/*    public void onEventMainThread(BaseEvent event) {
        if (event instanceof AllItemUpdatedEvent) {
            if (mOnceThings != null) {
                mOnceAdatper = new AllListViewAdapter(mOnceThings);
                mOnceListView.setAdapter(mOnceAdatper);
            }
            if (mRepeatThings != null) {
                mRepeatAdapter = new AllListViewAdapter(mRepeatThings);
                mReapteListView.setAdapter(mRepeatAdapter);
            }
        }

    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
