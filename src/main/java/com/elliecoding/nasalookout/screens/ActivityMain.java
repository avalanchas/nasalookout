package com.elliecoding.nasalookout.screens;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import com.elliecoding.nasalookout.R;
import com.elliecoding.nasalookout.entities.NasaData;
import com.elliecoding.nasalookout.logics.DataEventListener;
import com.elliecoding.nasalookout.logics.FragmentEventListener;
import com.elliecoding.nasalookout.logics.InternetDataManager;
import com.elliecoding.nasalookout.logics.StorageDataManager;
import com.elliecoding.nasalookout.utils.DateHelper;
import org.joda.time.LocalDate;

public class ActivityMain extends AppCompatActivity implements FragmentEventListener, DataEventListener {

    private static final String LOG_TAG = ActivityMain.class.getSimpleName();

    private static final String FRAGMENT_TAG_OVERVIEW = "overview";
    public static final String KEY_EXTRA_DATA = "extraData";

    // TODO make all resources screen flip safe
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean mRequestHd;

    private StorageDataManager mStorageDataManager = new StorageDataManager(this);
    private InternetDataManager mInternetDataManager = new InternetDataManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseDrawer();
        initialiseFragment(savedInstanceState);
        initialiseOverview();

        // TODO SwipeRefreshLayout to check for new items?
        // TODO settings

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    private void initialiseOverview() {
        for (LocalDate date : DateHelper.determinePastStartingDates(calculateNumberOfBlocks())) {
            requestNasaData(date);
        }
    }

    private int calculateNumberOfBlocks() {
        // TODO check how many boxes we need to fill the screen according to screen size
        return 6;
    }


    private void requestNasaData(LocalDate date) {
        if (mStorageDataManager.hasInStorage(date)) {
            mStorageDataManager.requestFromStorage(date);
        } else {
            mInternetDataManager.requestFromInternet(date);
        }
    }

    @Override
    public void onDataRetrieved(NasaData data) {
        if (!mStorageDataManager.hasInStorage(data.getDate())) {
            mStorageDataManager.writeToStorage(data);
        }
        sendResponseToGallery(data);
    }


    private void sendResponseToGallery(NasaData image) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_OVERVIEW);
        if (fragment != null && fragment instanceof FragmentOverview) {
            ((FragmentOverview) fragment).addDataToOverview(image);
        }
    }

    private void initialiseFragment(Bundle savedInstanceState) {
        // TODO implement logic to decide device rotation, screen size etc. here so we can decide which fragment to use
        if (findViewById(R.id.fragment_container_main) != null) {

            if (savedInstanceState != null) {
                return;
            }

            FragmentOverview overview = FragmentOverview.newInstance();
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_main, overview, FRAGMENT_TAG_OVERVIEW).commit();
        }
    }

    private void initialiseDrawer() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // TODO add search and favourite

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // TODO when in detail go to overview, when in overview close app. When drawer open close drawer
        super.onBackPressed();
    }

    @Override
    public void onContainerClicked(LocalDate startingDate) {
        FragmentOverview overview = (FragmentOverview) getSupportFragmentManager().findFragmentByTag
                (FRAGMENT_TAG_OVERVIEW);
        overview.removeAllItems();

        for (LocalDate date : DateHelper.determineDaysOfMonth(startingDate)) {
            requestNasaData(date);
        }
    }

    public void onNasaItemClicked(NasaData data) {

        // We immediately go to the detail activity to show the image the user wants to see
        Intent startIntent = new Intent(this, ActivityDetail.class);
        startIntent.putExtra(KEY_EXTRA_DATA, data);
        startActivity(startIntent);
    }
}