package com.zhenghuiyan.todaything.bean;

import com.zhenghuiyan.todaything.constant.FragmentName;

/**
 * Created by zhenghuiyan on 2015/1/30.
 */
public class ThingMenuItem {
    private FragmentName fragmentName;

    private int drawableId;

    private String name;

    private boolean isSelected;

    public FragmentName getFragmentName() {
        return fragmentName;
    }

    public void setFragmentName(FragmentName fragmentName) {
        this.fragmentName = fragmentName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
