package com.ntsoftware.vspc.demoex.ui.detail;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ntsoftware.vspc.demoex.R;
import com.ntsoftware.vspc.demoex.data.PeopleContract;
import com.ntsoftware.vspc.demoex.data.PeopleDbHelper;
import com.ntsoftware.vspc.demoex.data.PeopleItem;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class DetailFragment extends Fragment {

    public static final String ARG_ID = "id";
    public static final String ARG_IS_NEW_ITEM = "is_new";

    private boolean image_changed = false;

    View root;

    PeopleDbHelper peopleDbHelper;

    PeopleItem peopleItem;

    TextInputEditText first_name;
    TextInputEditText last_name;
    TextInputEditText birthday;
    TextInputEditText email;
    TextInputEditText detail;

    TextInputLayout tv_detail_email_layout;

    Button delete;
    Button share;
    Button save;

    CardView image_frame;
    ImageView imageView;

    @Override
    public void onStart() {

        super.onStart();

        if (getArguments().getBoolean(ARG_IS_NEW_ITEM, true)) {

            toggleActionButton();

        } else {

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
                    PeopleContract.PeopleEntry.TABLE_NAME,                                           // таблица
                    projection,                                                                      // столбцы
                    PeopleContract.PeopleEntry._ID + " = " + getArguments().getInt(ARG_ID), // столбцы для условия WHERE
                    null,                                                                // значения для условия WHERE
                    null,                                                                    // Don't group the rows
                    null,                                                                     // Don't filter by row groups
                    null);

            List<PeopleItem> peopleItems = new PeopleItem.PeopleItemAdapter(cursor).adaptCursorToList();
            PeopleItem p = peopleItems.get(0);

            peopleItem = p;

            first_name.setText(p.getFirst_name());
            last_name.setText(p.getLast_name());
            birthday.setText(p.getBirthday());
            email.setText(p.getEmail());
            detail.setText(p.getDetail());
            if (p.getImage() != null && !image_changed)
                setImageByBytes(p.getImage());
        }
        setChangeListeners();

    }

    @Override
    public void onStop() {
        super.onStop();
        removeChangeListeners();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detail, container, false);
        first_name = root.findViewById(R.id.tv_detail_f_name);
        last_name = root.findViewById(R.id.tv_detail_l_name);
        birthday = root.findViewById(R.id.tv_detail_birthday);
        email = root.findViewById(R.id.tv_detail_email);
        detail = root.findViewById(R.id.tv_detail_detail);
        tv_detail_email_layout = root.findViewById(R.id.tv_detail_email_layout);

        image_frame = root.findViewById(R.id.image_frame);
        imageView = root.findViewById(R.id.detail_image);

        delete = root.findViewById(R.id.b_delete);
        share = root.findViewById(R.id.b_share);
        save = root.findViewById(R.id.b_save);

        peopleDbHelper = new PeopleDbHelper(root.getContext());

        delete.setOnClickListener(delete_onclick);
        share.setOnClickListener(share_onclick);
        save.setOnClickListener(save_onclick);
        tv_detail_email_layout.setEndIconOnClickListener(send_email_onclick);
        image_frame.setOnClickListener(image_onclick);


        return root;
    }

    View.OnClickListener image_onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 10);
        }
    };

    View.OnClickListener send_email_onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent emailSelectorIntent = new Intent(Intent.ACTION_SENDTO);
            emailSelectorIntent.setData(Uri.parse("mailto:"));

            final Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{peopleItem.getEmail()});
            emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            emailIntent.setSelector(emailSelectorIntent);

            if (emailIntent.resolveActivity(getActivity().getPackageManager()) != null)
                startActivity(emailIntent);

        }
    };

    View.OnClickListener share_onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, peopleItem.toText());
            intent.setType("text/plain");
            startActivity(intent);
        }
    };

    View.OnClickListener delete_onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SQLiteDatabase db = peopleDbHelper.getWritableDatabase();
            db.delete(PeopleContract.PeopleEntry.TABLE_NAME,
                    PeopleContract.PeopleEntry._ID + "= ?",
                    new String[]{String.valueOf(getArguments().getInt(ARG_ID))});
            db.close();

            Toast.makeText(view.getContext(), R.string.toast_deleted, Toast.LENGTH_SHORT)
                    .show();

            Objects.requireNonNull(getActivity()).onBackPressed();
        }
    };

    View.OnClickListener save_onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ContentValues contentValues = new ContentValues();
            SQLiteDatabase db = peopleDbHelper.getWritableDatabase();

            if (imageView.getDrawable() != null) {
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytes = stream.toByteArray();
                contentValues.put(PeopleContract.PeopleEntry.COLUMN_IMAGE, bytes);
            }

            contentValues.put(PeopleContract.PeopleEntry.COLUMN_FIRST_NAME, getTextFromView(first_name));
            contentValues.put(PeopleContract.PeopleEntry.COLUMN_LAST_NAME, getTextFromView(last_name));
            contentValues.put(PeopleContract.PeopleEntry.COLUMN_BIRTHDAY, getTextFromView(birthday));
            contentValues.put(PeopleContract.PeopleEntry.COLUMN_EMAIL, getTextFromView(email));
            contentValues.put(PeopleContract.PeopleEntry.COLUMN_DETAIL, getTextFromView(detail));

            if (getArguments().getInt(ARG_ID) == -1) {
                db.insertWithOnConflict(PeopleContract.PeopleEntry.TABLE_NAME, "", contentValues, SQLiteDatabase.CONFLICT_IGNORE);
            } else {
                db.update(
                        PeopleContract.PeopleEntry.TABLE_NAME,
                        contentValues,
                        PeopleContract.PeopleEntry._ID + "= ?",
                        new String[]{String.valueOf(getArguments().getInt(ARG_ID))});
            }

            Toast.makeText(view.getContext(), R.string.toast_saved, Toast.LENGTH_SHORT)
                    .show();

            db.close();

            Objects.requireNonNull(getActivity()).onBackPressed();
        }
    };


    TextWatcher data_changed_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            toggleActionButton(true);
        }
    };

    private void setChangeListeners() {
        first_name.addTextChangedListener(data_changed_listener);
        last_name.addTextChangedListener(data_changed_listener);
        birthday.addTextChangedListener(data_changed_listener);
        email.addTextChangedListener(data_changed_listener);
        detail.addTextChangedListener(data_changed_listener);
    }

    private void removeChangeListeners() {
        first_name.removeTextChangedListener(data_changed_listener);
        last_name.removeTextChangedListener(data_changed_listener);
        birthday.removeTextChangedListener(data_changed_listener);
        email.removeTextChangedListener(data_changed_listener);
        detail.removeTextChangedListener(data_changed_listener);
    }

    private void toggleActionButton() {
        toggleActionButton(false);
    }

    private void toggleActionButton(boolean stay_hide) {
        if (save.getVisibility() != View.VISIBLE) {
            delete.setVisibility(View.GONE);
            share.setVisibility(View.GONE);
            save.setVisibility(View.VISIBLE);
        } else if (stay_hide) {

        } else {
            delete.setVisibility(View.VISIBLE);
            share.setVisibility(View.VISIBLE);
            save.setVisibility(View.GONE);
        }
    }

    private String getTextFromView(TextInputEditText textInputEditText) {
        return textInputEditText.getText().toString();
    }

    public void setImageByBytes(byte[] b) {
        Bitmap image = BitmapFactory.decodeByteArray(b, 0, b.length);
        imageView.setImageBitmap(image);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10) {
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                    byte[] b = toByteArray(inputStream);
                    image_changed = true;
                    setImageByBytes(b);
                    toggleActionButton();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;

        // read bytes from the input stream and store them in buffer
        while ((len = in.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            os.write(buffer, 0, len);
        }

        return os.toByteArray();
    }

}
