package com.ntsoftware.vspc.demoex.ui.detail;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.ntsoftware.vspc.demoex.R;
import com.ntsoftware.vspc.demoex.data.PeopleContract;
import com.ntsoftware.vspc.demoex.data.PeopleDbHelper;
import com.ntsoftware.vspc.demoex.data.PeopleItem;

import java.util.List;

public class DetailFragment extends Fragment {

    public static final String ARG_ID = "id";
    public static final String ARG_IS_NEW_ITEM = "is_new";

    View root;

    PeopleDbHelper peopleDbHelper;

    TextInputEditText first_name;
    TextView last_name;
    TextView birthday;
    TextView email;
    TextView detail;


    @Override
    public void onStart() {
        super.onStart();

        if (getArguments().getBoolean(ARG_IS_NEW_ITEM, true)) {

        } else {
            peopleDbHelper = new PeopleDbHelper(root.getContext());

            SQLiteDatabase db = peopleDbHelper.getReadableDatabase();

            String[] projection = {
                    PeopleContract.PeopleEntry._ID,
                    PeopleContract.PeopleEntry.COLUMN_FIRST_NAME,
                    PeopleContract.PeopleEntry.COLUMN_LAST_NAME,
                    PeopleContract.PeopleEntry.COLUMN_BIRTHDAY,
                    PeopleContract.PeopleEntry.COLUMN_EMAIL,
                    PeopleContract.PeopleEntry.COLUMN_DETAIL,
                    PeopleContract.PeopleEntry.COLUMN_IMAGE
            };

            Cursor cursor = db.query(
                    PeopleContract.PeopleEntry.TABLE_NAME,             // таблица
                    projection,                         // столбцы
                    PeopleContract.PeopleEntry._ID + " = " + getArguments().getInt(ARG_ID),                      // столбцы для условия WHERE
                    null,                  // значения для условия WHERE
                    null,                      // Don't group the rows
                    null,                       // Don't filter by row groups
                    null);

            List<PeopleItem> peopleItems = new PeopleItem.PeopleItemAdapter(cursor).adaptCursorToList();
            PeopleItem p = peopleItems.get(0);

            first_name.setText(p.getFirst_name());
            last_name.setText(p.getLast_name());
            birthday.setText(p.getBirthday());
            email.setText(p.getEmail());
            detail.setText(p.getDetail());

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detail, container, false);
        AppBarLayout appBarLayout = root.findViewById(R.id.app_bar);
        first_name = root.findViewById(R.id.tv_detail_f_name);
        last_name = root.findViewById(R.id.tv_detail_l_name);
        birthday = root.findViewById(R.id.tv_detail_birthday);
        email = root.findViewById(R.id.tv_detail_email);
        detail = root.findViewById(R.id.tv_detail_detail);

        Button delete = root.findViewById(R.id.b_delete);
        Button share = root.findViewById(R.id.b_share);

        delete.setOnClickListener(test_onclick);
        share.setOnClickListener(test_onclick);

        return root;
    }

    View.OnClickListener test_onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Snackbar.make(view, "TODO", Snackbar.LENGTH_SHORT)
                    .setAction("OK", null)
                    .setAnchorView(R.id.constraintLayout)
                    .show();

        }
    };

}
