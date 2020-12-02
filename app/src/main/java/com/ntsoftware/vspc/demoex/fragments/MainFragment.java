package com.ntsoftware.vspc.demoex.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ntsoftware.vspc.demoex.R;
import com.ntsoftware.vspc.demoex.data.PeopleContract;
import com.ntsoftware.vspc.demoex.data.PeopleDbHelper;

public class MainFragment extends Fragment {

    View root;

    PeopleDbHelper peopleDbHelper;

    @Override
    public void onStart() {
        super.onStart();
        peopleDbHelper = new PeopleDbHelper(root.getContext());
        displayDatabaseInfo();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);
        return root;
    }

    private void displayDatabaseInfo() {
        // Создадим и откроем для чтения базу данных
        SQLiteDatabase db = peopleDbHelper.getReadableDatabase();

        String[] projection = {
                PeopleContract.PeopleEntry._ID,
                PeopleContract.PeopleEntry.COLUMN_FIRST_NAME,
                PeopleContract.PeopleEntry.COLUMN_LAST_NAME};
        // Зададим условие для выборки - список столбцов

        // Делаем запрос
        Cursor cursor = db.query(
                PeopleContract.PeopleEntry.TABLE_NAME,   // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки

        TextView displayTextView = (TextView) root.findViewById(R.id.rw_content);

        try {
            displayTextView.setText("Таблица содержит " + cursor.getCount() + " людей.\n\n");
            displayTextView.append(PeopleContract.PeopleEntry._ID + " - " +
                    PeopleContract.PeopleEntry.COLUMN_FIRST_NAME + " - " +
                    PeopleContract.PeopleEntry.COLUMN_LAST_NAME + " - ");

            // Узнаем индекс каждого столбца
            int idColumnIndex = cursor.getColumnIndex(PeopleContract.PeopleEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PeopleContract.PeopleEntry.COLUMN_FIRST_NAME);
            int cityColumnIndex = cursor.getColumnIndex(PeopleContract.PeopleEntry.COLUMN_LAST_NAME);

            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                int currentID = cursor.getInt(idColumnIndex);
                String currentFName = cursor.getString(nameColumnIndex);
                String currentLName = cursor.getString(cityColumnIndex);
                // Выводим значения каждого столбца
                displayTextView.append(("\n" + currentID + " - " +
                        currentFName + " - " +
                        currentLName + " - "));
            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
    }
}
