package com.zhenghuiyan.todaything.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.zhenghuiyan.todaything.R;
import com.zhenghuiyan.todaything.util.MiscUtil;

/**
 * Created by zhenghuiyan on 2015/2/5.
 */
public class WeekButton extends CheckBox {

    public WeekButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    WeekButton.this.setTextColor(MiscUtil.getAppContext().getResources().getColor(android.R.color.white));
                } else {
                    WeekButton.this.setTextColor(MiscUtil.getAppContext().getResources().getColor(R.color.secondary_text));
                }
            }
        });
    }
}
