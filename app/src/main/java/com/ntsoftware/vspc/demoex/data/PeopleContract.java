package com.ntsoftware.vspc.demoex.data;

import android.provider.BaseColumns;

public final class PeopleContract {
    public final class PeopleEntry implements BaseColumns {
        public final static String TABLE_NAME = "peoples";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_FIRST_NAME = "f_name";
        public final static String COLUMN_LAST_NAME = "l_name";
        public final static String COLUMN_BIRTHDAY = "b_day";
        public final static String COLUMN_EMAIL = "email";
        public final static String COLUMN_DETAIL = "detail";
        public final static String COLUMN_IMAGE = "image";
    }
}
