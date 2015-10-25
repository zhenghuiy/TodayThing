package com.zhenghuiyan.todaything.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhenghuiyan.todaything.R;
import com.zhenghuiyan.todaything.bean.Thing;
import com.zhenghuiyan.todaything.util.MiscUtil;

import java.util.ArrayList;

/**
 * Created by zhenghuiyan on 2015/1/28.
 */
public class ThingListViewAdapter extends BaseAdapter{
    private ArrayList<Thing> mThings= null;

    public ThingListViewAdapter(ArrayList<Thing> things) {
        this.mThings = things;
    }

    @Override
    public int getCount() {
        return mThings.size();
    }

    @Override
    public Object getItem(int position) {

        return mThings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(MiscUtil.getAppContext()).inflate(R.layout.listview_thing_item, null);
            holder = new ViewHolder();
            holder.mTvContent = (TextView) convertView.findViewById(R.id.content);
            holder.mTvTime = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Thing t = mThings.get(position);
        String content = t.getContent();
        String time = t.getFromTime().toString(true) + " to " + t.getToTime().toString(true);
        holder.mTvContent.setText(content);
        holder.mTvTime.setText(time);

        return convertView;
    }

    private static class ViewHolder{
        private TextView mTvContent;

        private TextView mTvTime;
    }
}
