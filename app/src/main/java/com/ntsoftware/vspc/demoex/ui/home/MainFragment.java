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
import com.ntsoftware.vspc.demoex.data.PeopleItem;

import java.util.List;

public class MainFragment extends Fragment {

    View root;

    PeopleDbHelper peopleDbHelper;

    RecyclerView recyclerView;


    @Override
    public void onStart() {
        super.onStart();
        peopleDbHelper = new PeopleDbHelper(root.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(new PeopleAdapter(getPeoples()));
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
