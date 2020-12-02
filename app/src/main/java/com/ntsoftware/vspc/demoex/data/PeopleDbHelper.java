package com.ntsoftware.vspc.demoex.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PeopleDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = PeopleDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "pp.db";

    private static final int DATABASE_VERSION = 3;

    private static final String SQL_CREATE_PEOPLE_TABLE =
            "CREATE TABLE " + PeopleContract.PeopleEntry.TABLE_NAME + " ("
                    + PeopleContract.PeopleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + PeopleContract.PeopleEntry.COLUMN_FIRST_NAME + " TEXT, "
                    + PeopleContract.PeopleEntry.COLUMN_LAST_NAME + " TEXT, "
                    + PeopleContract.PeopleEntry.COLUMN_BIRTHDAY + " TEXT, "
                    + PeopleContract.PeopleEntry.COLUMN_EMAIL + " TEXT, "
                    + PeopleContract.PeopleEntry.COLUMN_DETAIL + " TEXT, "
                    + PeopleContract.PeopleEntry.COLUMN_IMAGE + " BLOB );";

    public PeopleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(LOG_TAG, "Create database");
        sqLiteDatabase.execSQL(SQL_CREATE_PEOPLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.w(LOG_TAG, "Обновляемся с версии " + i + " на версию " + i1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PeopleContract.PeopleEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
