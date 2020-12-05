package com.ntsoftware.vspc.demoex.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PeopleDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = PeopleDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "pp.db";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_PEOPLE_TABLE =
            "CREATE TABLE " + PeopleContract.PeopleEntry.TABLE_NAME + " ("
                    + PeopleContract.PeopleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + PeopleContract.PeopleEntry.COLUMN_FIRST_NAME + " TEXT, "
                    + PeopleContract.PeopleEntry.COLUMN_LAST_NAME + " TEXT, "
                    + PeopleContract.PeopleEntry.COLUMN_BIRTHDAY + " TEXT, "
                    + PeopleContract.PeopleEntry.COLUMN_EMAIL + " TEXT, "
                    + PeopleContract.PeopleEntry.COLUMN_DETAIL + " TEXT, "
                    + PeopleContract.PeopleEntry.COLUMN_IMAGE + " BLOB );";

    String insert_q = "INSERT INTO " + PeopleContract.PeopleEntry.TABLE_NAME
            + " (" + PeopleContract.PeopleEntry.COLUMN_FIRST_NAME + ", " + PeopleContract.PeopleEntry.COLUMN_LAST_NAME + ", "
            + PeopleContract.PeopleEntry.COLUMN_BIRTHDAY + ", " + PeopleContract.PeopleEntry.COLUMN_EMAIL + ", " + PeopleContract.PeopleEntry.COLUMN_DETAIL + ") VALUES "
            + "('Трифонов','Никита','15.09.1999','myemalexample@gmail.com', 'Ну типа разрабочик этой навереное бесполезной проги.'),"
            + "('Человек 2','Человек 2','10.04.1989','Ntууууу@nt.com', 'Какой то чел.'),"
            + "('Человек 3','Человек 3','10.04.1989','Ntууууу@nt.com', 'Какой то чел.'),"
            + "('Человек 4','Человек 4','10.04.1989','Ntууууу@nt.com', 'Какой то чел.'),"
            + "('Человек 5','Человек 5','10.04.1989','Ntууууу@nt.com', 'Какой то чел.'),"
            + "('Человек 6','Человек 6','10.04.1989','Ntууууу@nt.com', 'Какой то чел.'),"
            + "('Человек 7','Человек 7','10.04.1989','Ntууууу@nt.com', 'Какой то чел.'),"
            + "('Человек 8','Человек 8','10.04.1989','Ntууууу@nt.com', 'Какой то чел.')";


    public PeopleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(LOG_TAG, "Create database");
        sqLiteDatabase.execSQL(SQL_CREATE_PEOPLE_TABLE);
        sqLiteDatabase.execSQL(insert_q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.w(LOG_TAG, "Обновляемся с версии " + i + " на версию " + i1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PeopleContract.PeopleEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
