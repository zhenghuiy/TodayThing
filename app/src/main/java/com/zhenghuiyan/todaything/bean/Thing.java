package com.zhenghuiyan.todaything.bean;
import com.zhenghuiyan.todaything.data.ScheduleContract;
import com.zhenghuiyan.todaything.util.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhenghuiyan on 2015/1/27.
 */
public class Thing implements Comparable<Thing>, Serializable{
    private int id = 0;

    private String sTime;

    private String content;

    private HourAndMinute fromTime;

    private HourAndMinute toTime;

    private int thing_id;

    private String complete_date = ScheduleContract.ScheduleContractEntry.DEFAULT_DATE;

    private int weekNum = 0;
    //binary num of sunday to saturday
    private final int SUN_NUM = 0b00000001;
    private final int MON_NUM = 0b00000010;
    private final int TUE_NUM = 0b00000100;
    private final int WED_NUM = 0b00001000;
    private final int THU_NUM = 0b00010000;
    private final int FRI_NUM = 0b00100000;
    private final int SAT_NUM = 0b01000000;

    public Thing() {}

    public Thing(int id, String sTime, String content, HourAndMinute fromTime, HourAndMinute toTime, int thing_id, String complete_date, int weekNumber) {
        this.id = id;
        this.sTime = sTime;
        this.content = content;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.thing_id = thing_id;
        this.complete_date = complete_date;
        this.weekNum = weekNumber;
    }

    public Thing(int id, String sTime, String content, String fromTime, String toTime, int thing_id, String complete_date, int weekNumber) {
        this(id, sTime, content, new HourAndMinute(fromTime), new HourAndMinute(toTime), thing_id, complete_date, weekNumber);
    }

    public static boolean isTodayRepeatByWeekNum(int weekNum) {
        Thing t = new Thing();
        t.setWeekNum(weekNum);
        int dayOfWeek = DateUtil.getDayOfWeek(new Date());

        return t.isDayOfWeekRepeat(dayOfWeek);
    }

    public void setSun(boolean isThisDayReapt) {
        if (isThisDayReapt) {
            weekNum |= SUN_NUM;
        } else {
            weekNum &= (~SUN_NUM);
        }
    }

    public boolean isSunRepeat() {
        if (((weekNum >> 0) & 1) > 0) {
            return true;
        }

        return false;
    }

    public void setMon(boolean isThisDayReapt) {
        if (isThisDayReapt) {
            weekNum |= MON_NUM;
        } else {
            weekNum &= (~MON_NUM);
        }
    }

    public boolean isMonRepeat() {
        if (((weekNum >> 1) & 1) > 0) {
            return true;
        }

        return false;
    }

    public void setTue(boolean isThisDayReapt) {
        if (isThisDayReapt) {
            weekNum |= TUE_NUM;
        } else {
            weekNum &= (~TUE_NUM);
        }
    }

    public boolean isTueRepeat() {
        if (((weekNum >> 2) & 1) > 0) {
            return true;
        }

        return false;
    }

    public void setWed(boolean isThisDayReapt) {
        if (isThisDayReapt) {
            weekNum |= WED_NUM;
        } else {
            weekNum &= (~WED_NUM);
        }
    }

    public boolean isWedRepeat() {
        if (((weekNum >> 3) & 1) > 0) {
            return true;
        }

        return false;
    }

    public void setThu(boolean isThisDayReapt) {
        if (isThisDayReapt) {
            weekNum |= THU_NUM;
        } else {
            weekNum &= (~THU_NUM);
        }
    }

    public boolean isThuRepeat() {
        if (((weekNum >> 4) & 1) > 0) {
            return true;
        }

        return false;
    }

    public void setFri(boolean isThisDayReapt) {
        if (isThisDayReapt) {
            weekNum |= FRI_NUM;
        } else {
            weekNum &= (~FRI_NUM);
        }
    }

    public boolean isFriRepeat() {
        if (((weekNum >> 5) & 1) > 0) {
            return true;
        }

        return false;
    }

    public void setSat(boolean isThisDayReapt) {
        if (isThisDayReapt) {
            weekNum |= SAT_NUM;
        } else {
            weekNum &= (~SAT_NUM);
        }
    }

    public boolean isSatRepeat() {
        if (((weekNum >> 6) & 1) > 0) {
            return true;
        }

        return false;
    }

    public boolean isDayOfWeekRepeat(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1:
                return isSunRepeat();
            case 2:
                return isMonRepeat();
            case 3:
                return isTueRepeat();
            case 4:
                return isWedRepeat();
            case 5:
                return isThuRepeat();
            case 6:
                return isFriRepeat();
            case 7:
                return isSatRepeat();
            default:
                return false;
        }
    }

    public int getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

    public boolean isWeekNumSet() {
        return (weekNum > 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HourAndMinute getFromTime() {
        return fromTime;
    }

    public void setFromTime(HourAndMinute fromTime) {
        this.fromTime = fromTime;
    }

    public HourAndMinute getToTime() {
        return toTime;
    }

    public void setToTime(HourAndMinute toTime) {
        this.toTime = toTime;
    }

    public int getThing_id() {
        return thing_id;
    }

    public void setThing_id(int thing_id) {
        this.thing_id = thing_id;
    }

    public String getComplete_date() {
        return complete_date;
    }

    public void setComplete_date(String complete_date) {
        this.complete_date = complete_date;
    }

    @Override
    public int compareTo(Thing another) {
        if (fromTime.compareTo(another.fromTime) != 0) {
            return fromTime.compareTo(another.fromTime);
        } else if(toTime.compareTo(another.toTime) != 0) {
            return toTime.compareTo(another.toTime);
        } else {
            return (thing_id - another.thing_id);
        }

    }

    @Override
    public String toString() {
        return content;
    }

    public String toWeekString() {
        StringBuffer sb = new StringBuffer();

        sb.append(isSunRepeat() ? "sun," : "");
        sb.append(isMonRepeat() ? "mon," : "");
        sb.append(isTueRepeat() ? "tue," : "");
        sb.append(isWedRepeat() ? "wed," : "");
        sb.append(isThuRepeat() ? "thu," : "");
        sb.append(isFriRepeat() ? "fri," : "");
        sb.append(isSatRepeat() ? "sat" : "");

        return sb.toString();
    }
}
