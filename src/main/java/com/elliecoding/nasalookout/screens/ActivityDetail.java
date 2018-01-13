package com.elliecoding.nasalookout.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.elliecoding.nasalookout.R;
import com.elliecoding.nasalookout.entities.NasaData;

public class ActivityDetail extends AppCompatActivity {

    private static final String FRAGMENT_TAG_DETAIL = "detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        if (intent != null && intent.getSerializableExtra(ActivityMain.KEY_EXTRA_DATA) != null) {
            if (intent.getSerializableExtra(ActivityMain.KEY_EXTRA_DATA) instanceof NasaData) {
                showDetailView((NasaData) intent.getSerializableExtra(ActivityMain.KEY_EXTRA_DATA));
                return;
            }
        }

        showErrorView();
    }

    private void showErrorView() {

    }

    private void showDetailView(NasaData data) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_detail, FragmentDetail.newInstance
                (data), FRAGMENT_TAG_DETAIL).commit();
    }
}
