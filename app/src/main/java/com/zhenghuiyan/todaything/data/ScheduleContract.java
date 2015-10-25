package com.zhenghuiyan.todaything.data;

import android.provider.BaseColumns;

/**
 * Created by zhenghuiyan on 2015/1/27.
 */
public final class ScheduleContract {

    public ScheduleContract() {}

    public static abstract class ScheduleContractEntry implements BaseColumns {
        public static final String TABLE_NAME = "Schedule";

        public static final String COLUMN_NAME_ID = "id";

        public static final String COLUMN_NAME_STIME = "s_time";

        public static final String COLUMN_NAME_CONTENT = "content";

        public static final String COLUMN_NAME_FROM_TIME = "from_time";

        public static final String COLUMN_NAME_TO_TIME = "to_time";

        public static final String COLUMN_NAME_COMPLETE_DATE = "complete_date";

        public static final String COLUMN_NAME_THING_ID = "thing_id";

        public static final String COLUMN_NAME_WEEK_NUM = "week_num";

        public static final String DEFAULT_DATE = "2000-01-01";


    }
}
