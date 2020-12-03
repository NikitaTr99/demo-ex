package com.ntsoftware.vspc.demoex.ui.home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ntsoftware.vspc.demoex.R;
import com.ntsoftware.vspc.demoex.data.PeopleContract.PeopleEntry;
import com.ntsoftware.vspc.demoex.data.PeopleDbHelper;

import java.util.List;

public class MainFragment extends Fragment {

    View root;

    PeopleDbHelper peopleDbHelper;

    RecyclerView recyclerView;

    String insert_q = "INSERT INTO " + PeopleEntry.TABLE_NAME
            + " (" + PeopleEntry.COLUMN_FIRST_NAME + ", " + PeopleEntry.COLUMN_LAST_NAME + ", "
            + PeopleEntry.COLUMN_BIRTHDAY + ", " + PeopleEntry.COLUMN_EMAIL + ", " + PeopleEntry.COLUMN_DETAIL + ") VALUES "
            + "('Человек 2','Человек 2','10.04.1989','Ntууууу@nt.com', 'Какой то чел.'),"
            + "('Человек 3','Человек 3','10.04.1989','Ntууууу@nt.com', 'Какой то чел.'),"
            + "('Человек 4','Человек 4','10.04.1989','Ntууууу@nt.com', 'Какой то чел.'),"
            + "('Человек 5','Человек 5','10.04.1989','Ntууууу@nt.com', 'Какой то чел.'),"
            + "('Человек 6','Человек 6','10.04.1989','Ntууууу@nt.com', 'Какой то чел.'),"
            + "('Человек 7','Человек 7','10.04.1989','Ntууууу@nt.com', 'Какой то чел.'),"
            + "('Человек 8','Человек 8','10.04.1989','Ntууууу@nt.com', 'Какой то чел.')";

    @Override
    public void onStart() {
        super.onStart();
        peopleDbHelper = new PeopleDbHelper(root.getContext());
//        peopleDbHelper.getWritableDatabase().execSQL(insert_q);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(new PeopleAdapter(getPeoples()));

//        peopleDbHelper.getWritableDatabase().execSQL(insert_q);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = root.findViewById(R.id.rw_content);
        return root;
    }

    private List<PeopleItem> getPeoples() {
        SQLiteDatabase db = peopleDbHelper.getReadableDatabase();

        String[] projection = {
                PeopleEntry._ID,
                PeopleEntry.COLUMN_FIRST_NAME,
                PeopleEntry.COLUMN_LAST_NAME,
                PeopleEntry.COLUMN_BIRTHDAY,
                PeopleEntry.COLUMN_EMAIL,
                PeopleEntry.COLUMN_DETAIL,
                PeopleEntry.COLUMN_IMAGE
        };

        Cursor cursor = db.query(
                PeopleEntry.TABLE_NAME,             // таблица
                projection,                         // столбцы
                null,                      // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                      // Don't group the rows
                null,                       // Don't filter by row groups
                null);                     // порядок сортировки


        List<PeopleItem> list = new PeopleItem.PeopleItemAdapter(cursor).adaptCursorToList();

        return list;
    }
}
