package com.zhenghuiyan.todaything.constant;

import com.zhenghuiyan.todaything.R;
import com.zhenghuiyan.todaything.bean.ThingMenuItem;
import com.zhenghuiyan.todaything.util.MiscUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huihui on 2015/8/23.
 */
public final class MenuItems {
    public static List<ThingMenuItem> sMenuItems = null;
    private MenuItems() {}

    public static List<ThingMenuItem> getMenuItems() {
        if (sMenuItems == null) {
            sMenuItems = new ArrayList<ThingMenuItem>();
            ThingMenuItem item = new ThingMenuItem();
            //day
            item.setFragmentName(FragmentName.Today);
            item.setDrawableId(R.drawable.ic_event_grey600_18dp);
            item.setSelected(true);
            item.setName(MiscUtil.getAppContext().getResources().getString(R.string.menu_day));
            sMenuItems.add(item);

            //all
            item = new ThingMenuItem();
            item.setFragmentName(FragmentName.All);
            item.setDrawableId(R.drawable.ic_view_list_grey600_18dp);
            item.setName(MiscUtil.getAppContext().getResources().getString(R.string.menu_all));
            sMenuItems.add(item);
        }

        return sMenuItems;
    }
}
