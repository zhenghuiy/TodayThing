package com.zhenghuiyan.todaything.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.internal.widget.TintEditText;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.Dialog;
import com.zhenghuiyan.todaything.R;
import com.zhenghuiyan.todaything.bean.HourAndMinute;
import com.zhenghuiyan.todaything.bean.Thing;
import com.zhenghuiyan.todaything.data.DBManager;
import com.zhenghuiyan.todaything.data.DataManager;
import com.zhenghuiyan.todaything.data.SPManager;
import com.zhenghuiyan.todaything.fragment.TodayFragment;
import com.zhenghuiyan.todaything.task.SimpleNoCallbackTask;
import com.zhenghuiyan.todaything.util.DateUtil;
import com.zhenghuiyan.todaything.widget.WeekButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by zhenghuiyan on 2015/1/23.
 */
public class EditActivity extends BaseToolbarActivity implements View.OnClickListener{
    private TintEditText mEdtContent;

    private ButtonFlat mBtnFromTime;

    private ButtonFlat mBtnToTime;

    private ButtonFlat mBtnDate;

    private ButtonFlat mBtnOk;

    private Thing mThing;

    private boolean isEditMode = false;
    /** position of item which should be removed  */
    private int position = -1;

    private static Dialog mDeleteDialog;

    private WeekButton wbSun;
    private WeekButton wbMon;
    private WeekButton wbTue;
    private WeekButton wbWed;
    private WeekButton wbThu;
    private WeekButton wbFri;
    private WeekButton wbSat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        setContentView(R.layout.activity_edit);
    }

    @Override
    protected void initViews() {
        super.initViews();
        mEdtContent = (TintEditText) findViewById(R.id.content);
        mBtnFromTime = (ButtonFlat)findViewById(R.id.from_time);
        mBtnToTime = (ButtonFlat)findViewById(R.id.to_time);
        mBtnOk = (ButtonFlat)findViewById(R.id.ok);
        mBtnDate = (ButtonFlat) findViewById(R.id.date);
        mBtnDate.setText(DateUtil.dateToString(new Date()));

        wbSun = (WeekButton) findViewById(R.id.week_sun);
        wbMon = (WeekButton) findViewById(R.id.week_mon);
        wbTue = (WeekButton) findViewById(R.id.week_tue);
        wbWed = (WeekButton) findViewById(R.id.week_wed);
        wbThu = (WeekButton) findViewById(R.id.week_thu);
        wbFri = (WeekButton) findViewById(R.id.week_fri);
        wbSat = (WeekButton) findViewById(R.id.week_sat);

        mBtnDate.setOnClickListener(this);
        mBtnFromTime.setOnClickListener(this);
        mBtnToTime.setOnClickListener(this);
        mBtnOk.setOnClickListener(this);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String action = bundle.getString("action");

        if ((!TextUtils.isEmpty(action)) && (action.equals(TodayFragment.Action.EDIT.name()))) {
            isEditMode = true;
            mThing = (Thing) bundle.getSerializable("thing");
            position = bundle.getInt("position");
            mEdtContent.setText(mThing.getContent());
            mBtnFromTime.setText(mThing.getFromTime().toString(true));
            mBtnToTime.setText(mThing.getToTime().toString(true));
            mBtnDate.setText(mThing.getsTime());

            if (mThing.isWeekNumSet()) {
                if (mThing.isSunRepeat()) {wbSun.setChecked(true);}
                if (mThing.isMonRepeat()) {wbMon.setChecked(true);}
                if (mThing.isTueRepeat()) {wbTue.setChecked(true);}
                if (mThing.isWedRepeat()) {wbWed.setChecked(true);}
                if (mThing.isThuRepeat()) {wbThu.setChecked(true);}
                if (mThing.isFriRepeat()) {wbFri.setChecked(true);}
                if (mThing.isSatRepeat()) {wbSat.setChecked(true);}

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        MenuItem delete = menu.findItem(R.id.action_delete);
        if (isEditMode) {
            delete.setVisible(true);
        } else {
            delete.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                goBackToMain();
                break;
            case R.id.action_delete:
                    showDelete();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.from_time:
                showTimePicker(mBtnFromTime, mBtnFromTime.getText());
                break;
            case R.id.to_time:
                showTimePicker(mBtnToTime, mBtnToTime.getText());
                break;
            case R.id.date:
                showDatePicker(mBtnDate, mBtnDate.getText());
                break;
            case R.id.ok:
                if (saveContent()) {
                    goBackToMain();
                }
                break;
        }
    }

    private boolean saveContent() {
        if (TextUtils.isEmpty(mEdtContent.getText())) {
            return false;
        } else {
            DataManager dm = DataManager.getInstance();
            if(!isEditMode) {
                // add
                mThing = new Thing();

                if (wbSun.isChecked()) { mThing.setSun(true);}
                if (wbMon.isChecked()) { mThing.setMon(true);}
                if (wbTue.isChecked()) { mThing.setTue(true);}
                if (wbWed.isChecked()) { mThing.setWed(true);}
                if (wbThu.isChecked()) { mThing.setThu(true);}
                if (wbFri.isChecked()) { mThing.setFri(true);}
                if (wbSat.isChecked()) { mThing.setSat(true);}

                mThing.setContent(mEdtContent.getText().toString());
                mThing.setsTime(mBtnDate.getText());
                mThing.setFromTime(new HourAndMinute(mBtnFromTime.getText()));
                mThing.setToTime(new HourAndMinute(mBtnToTime.getText()));
                mThing.setThing_id(SPManager.getInstance().getNextThingId());

                if (mThing.getsTime().equals(DataManager.TODAY_STR) || mThing.isDayOfWeekRepeat(DateUtil.getDayOfWeek(DateUtil.parseDate(DataManager.TODAY_STR)))) {

                    dm.updateThings(mThing, position);
                }

                //save thing to database on background thread
                new SimpleNoCallbackTask() {
                    @Override
                    protected void doInBack(){
                        //local
                        DBManager dbManager = DBManager.getInstance();
                        dbManager.addOneThing(mThing);
                        SPManager spManager = SPManager.getInstance();
                        spManager.setLastThingId(spManager.getNextThingId());
                    }
                }.execute();
            } else {
                // edit
                if (wbSun.isChecked()) { mThing.setSun(true);}
                if (wbMon.isChecked()) { mThing.setMon(true);}
                if (wbTue.isChecked()) { mThing.setTue(true);}
                if (wbWed.isChecked()) { mThing.setWed(true);}
                if (wbThu.isChecked()) { mThing.setThu(true);}
                if (wbFri.isChecked()) { mThing.setFri(true);}
                if (wbSat.isChecked()) { mThing.setSat(true);}

                mThing.setContent(mEdtContent.getText().toString());
                mThing.setFromTime(new HourAndMinute(mBtnFromTime.getText()));
                mThing.setToTime(new HourAndMinute(mBtnToTime.getText()));
                mThing.setsTime(mBtnDate.getText());

                dm.updateThings(mThing, position);
                //save thing to database on background thread
                new SimpleNoCallbackTask() {
                    @Override
                    protected void doInBack(){
                        //local
                        DBManager dbManager = DBManager.getInstance();
                        dbManager.updateOneThing(mThing);
                    }
                }.execute();
            }

            return true;
        }
    }

    private void showDelete() {
        if (mDeleteDialog == null) {
            mDeleteDialog = new Dialog(EditActivity.this, getResources().getString(R.string.delete), getResources().getString(R.string.edit_delete_tip));
            mDeleteDialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //delete thing
                    DataManager data = DataManager.getInstance();
                    data.deleteThing(mThing);
                    data.deleteAndUpdateThings(position);
                    mDeleteDialog.dismiss();
                    goBackToMain();
                }
            });
            mDeleteDialog.addCancelButton(getResources().getString(R.string.edit_delete_cancel), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDeleteDialog.dismiss();
                }
            });
        }
        mDeleteDialog.show();
    }

    private void showTimePicker(final ButtonFlat text, String str) {
        int hourOfDay = 00;
        int minute = 00;

        if(!TextUtils.isEmpty(str)) {
            HourAndMinute ham = new HourAndMinute(str);
            hourOfDay = ham.getHour();
            minute = ham.getMinute();
        }

        TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                HourAndMinute ham = new HourAndMinute();
                ham.setHour(hourOfDay);
                ham.setMinute(minute);
                text.setText(ham.toString(true));
            }
        };
        TimePickerDialog mTimePickerDialog = TimePickerDialog.newInstance(callback, hourOfDay, minute, true, R.style.DateTimePicker);
        mTimePickerDialog.setCancelable(true);
        mTimePickerDialog.show(getSupportFragmentManager(),"TimePickerDialog");
    }

    private void showDatePicker(final ButtonFlat text, String str) {
        int year = 0;
        int monthOfYear = 0;
        int dayOfMonth = 0;

        if(!TextUtils.isEmpty(str)) {
            Calendar c = Calendar.getInstance();
            c.setTime(DateUtil.parseDate(str));
            year = c.get(Calendar.YEAR);
            monthOfYear = c.get(Calendar.MONTH);
            dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Date d = c.getTime();
                text.setText(DateUtil.dateToString(d));
            }
        };

        DatePickerDialog mDatePickerDialog = DatePickerDialog.newInstance(callback, year, monthOfYear, dayOfMonth, R.style.DateTimePicker);
        mDatePickerDialog.setCancelable(true);
        mDatePickerDialog.show(getSupportFragmentManager(),"DatePickerDialog");
    }

}
