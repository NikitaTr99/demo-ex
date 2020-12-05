package com.ntsoftware.vspc.demoex.data;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PeopleItem {
    int _id;
    String first_name;
    String last_name;
    String birthday;
    String email;
    String detail;
    byte[] image;

    public PeopleItem() {
    }

    public PeopleItem(int _id, String first_name, String last_name, String birthday, String email, String detail, byte[] image) {
        this._id = _id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birthday = birthday;
        this.email = email;
        this.detail = detail;
        this.image = image;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "PeopleItem{" +
                "_id=" + _id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", detail='" + detail + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }

    public static class Builder {
        private PeopleItem peopleItem;

        public Builder() {
            peopleItem = new PeopleItem();
        }

        public Builder addId(int i) {
            peopleItem._id = i;
            return this;
        }

        public Builder addFirstName(String s) {
            peopleItem.first_name = s;
            return this;
        }

        public Builder addLastName(String s) {
            peopleItem.last_name = s;
            return this;
        }

        public Builder addBirthday(String s) {
            peopleItem.birthday = s;
            return this;
        }

        public Builder addEmail(String s) {
            peopleItem.email = s;
            return this;
        }

        public Builder addImage(byte[] b) {
            peopleItem.image = b;
            return this;
        }

        public Builder addDetail(String s) {
            peopleItem.detail = s;
            return this;
        }

        public PeopleItem build() {
            return peopleItem;
        }
    }

    public static class PeopleItemAdapter {

        Cursor cursor;
        List<PeopleItem> peopleItems;

        int id_index;
        int first_name_index;
        int last_name_index;
        int birthday_index;
        int email_index;
        int image_index;
        int detail_index;

        public PeopleItemAdapter(Cursor cursor) {
            this.cursor = cursor;
            peopleItems = new ArrayList<>();
            id_index = cursor.getColumnIndex(PeopleContract.PeopleEntry._ID);
            first_name_index = cursor.getColumnIndex(PeopleContract.PeopleEntry.COLUMN_FIRST_NAME);
            last_name_index = cursor.getColumnIndex(PeopleContract.PeopleEntry.COLUMN_LAST_NAME);
            birthday_index = cursor.getColumnIndex(PeopleContract.PeopleEntry.COLUMN_BIRTHDAY);
            email_index = cursor.getColumnIndex(PeopleContract.PeopleEntry.COLUMN_EMAIL);
            image_index = cursor.getColumnIndex(PeopleContract.PeopleEntry.COLUMN_IMAGE);
            detail_index = cursor.getColumnIndex(PeopleContract.PeopleEntry.COLUMN_DETAIL);
        }

        public List<PeopleItem> adaptCursorToList() {
            try {
                while (cursor.moveToNext()) {
                    PeopleItem p = new Builder()
                            .addFirstName(cursor.getString(first_name_index))
                            .addLastName(cursor.getString(last_name_index))
                            .addBirthday(cursor.getString(birthday_index))
                            .addEmail(cursor.getString(email_index))
                            .addImage(cursor.getBlob(image_index))
                            .addDetail(cursor.getString(detail_index))
                            .addId(cursor.getInt(id_index))
                            .build();

                    peopleItems.add(p);
                }
            } finally {
                cursor.close();
            }
            return peopleItems;
        }
    }
}
