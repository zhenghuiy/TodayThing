package com.zhenghuiyan.todaything.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhenghuiyan.todaything.R;
import com.zhenghuiyan.todaything.bean.ThingMenuItem;
import com.zhenghuiyan.todaything.util.MiscUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenghuiyan on 2015/1/28.
 */
public class MenuListViewAdapter extends BaseAdapter{
    private List<ThingMenuItem> mItems= null;

    public MenuListViewAdapter(List<ThingMenuItem> items) {
        this.mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {

        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(MiscUtil.getAppContext()).inflate(R.layout.listview_menu_item, null);
            holder = new ViewHolder();
            holder.mIvImg = (ImageView) convertView.findViewById(R.id.img);
            holder.mTvName = (TextView) convertView.findViewById(R.id.name);
            holder.mIvSeleted = (ImageView) convertView.findViewById(R.id.isSeleted);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ThingMenuItem item = mItems.get(position);
        holder.mIvImg.setImageDrawable(MiscUtil.getAppContext().getResources().getDrawable(item.getDrawableId()));
        holder.mTvName.setText(item.getName());
        if (item.isSelected()) {
            holder.mIvSeleted.setVisibility(View.VISIBLE);
        } else {
            holder.mIvSeleted.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    private static class ViewHolder{
        private ImageView mIvImg;

        private TextView mTvName;

        private ImageView mIvSeleted;
    }
}
