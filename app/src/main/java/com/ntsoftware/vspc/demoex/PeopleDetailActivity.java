package com.ntsoftware.vspc.demoex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ntsoftware.vspc.demoex.ui.detail.DetailFragment;

public class PeopleDetailActivity extends AppCompatActivity {

    private static final String ARG_IS_NEW_ITEM = "is_new";

    private static final String ARG_ID = "id";

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PeopleDetailActivity.class);
        intent.putExtra(ARG_IS_NEW_ITEM, true);
        return intent;
    }

    public static Intent newIntent(Context context, int _id) {
        Intent intent = new Intent(context, PeopleDetailActivity.class);
        intent.putExtra(ARG_IS_NEW_ITEM, false);
        intent.putExtra(ARG_ID, _id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_edit);


        Intent intent = getIntent();

        if (intent.getBooleanExtra(ARG_IS_NEW_ITEM, true)) {
            if (savedInstanceState == null) {
                Bundle bundle = new Bundle();
                bundle.putInt(DetailFragment.ARG_ID, getIntent().getIntExtra(ARG_ID, -1));
                bundle.putBoolean(DetailFragment.ARG_IS_NEW_ITEM, true);

                Fragment detail_fragment = new DetailFragment();

                detail_fragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().add(R.id.detail_container, detail_fragment).commit();
            }
        } else {
            if (savedInstanceState == null) {
                Bundle bundle = new Bundle();
                bundle.putInt(DetailFragment.ARG_ID, getIntent().getIntExtra(ARG_ID, -1));
                bundle.putBoolean(DetailFragment.ARG_IS_NEW_ITEM, false);

                Fragment detail_fragment = new DetailFragment();

                detail_fragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().add(R.id.detail_container, detail_fragment).commit();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

}