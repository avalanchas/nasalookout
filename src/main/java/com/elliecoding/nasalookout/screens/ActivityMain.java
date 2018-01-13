package com.elliecoding.nasalookout.screens;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
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

    public enum ViewState {
        YEAR,
        MONTH;
    }

    private ViewState mCurrentViewState = ViewState.YEAR;

    // TODO make all resources screen flip safe
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

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
            requestNasaData(date, true);
        }
    }

    private int calculateNumberOfBlocks() {
        // TODO check how many boxes we need to fill the screen according to screen size
        return 6;
    }


    private void requestNasaData(LocalDate date, boolean isCoverForMonth) {
        //        if (mStorageDataManager.hasInStorage(date)) {
        //            mStorageDataManager.requestFromStorage(date);
        //        } else {
        mInternetDataManager.requestFromInternet(date, isCoverForMonth);
    }

    @Override
    public void onDataRetrieved(NasaData data) {
        if (!mStorageDataManager.hasInStorage(data.getDate())) {
            // mStorageDataManager.writeToStorage(data);
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
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(R.string.app_name);
                }
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(R.string.menu);
                }
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
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
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (mCurrentViewState == ViewState.MONTH) {
            switchViewState(ViewState.YEAR, getString(R.string.app_name));
            switchViewContent(null);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onContainerClicked(NasaData data) {
        if (mCurrentViewState == ViewState.YEAR) {
            switchViewState(ViewState.MONTH, getMonthNameFromResources(data.getDate()));
            switchViewContent(data);
        } else {
            onNasaItemClicked(data);
        }
    }

    @Override
    public void onNasaItemClicked(NasaData data) {
        // We immediately go to the detail activity to show the image the user wants to see
        Intent startIntent = new Intent(this, ActivityDetail.class);
        startIntent.putExtra(KEY_EXTRA_DATA, data);
        startActivity(startIntent);
    }

    @Override
    public void onMoreItemsRequired(NasaData data) {
        // A month should already have all its items, so we only react if we currently show the initial overview
        if (mCurrentViewState == ViewState.YEAR) {
            for (LocalDate date : DateHelper.determinePastStartingDates(4, data.getDate())) {
                // requestNasaData(date, true);
            }
        }
    }

    private void switchViewContent(NasaData data) {
        FragmentOverview overview = (FragmentOverview) getSupportFragmentManager().findFragmentByTag
                (FRAGMENT_TAG_OVERVIEW);

        // We reuse the overview fragment since we essentially need to do the same thing. So remove all items
        overview.setToOverviewType(mCurrentViewState);

        if (data == null) {
            initialiseOverview();
        } else {
            // This way we benefit from the view recycling and the default animations. Now refill
            for (LocalDate date : DateHelper.determineDaysOfMonth(data.getDate())) {
                requestNasaData(date, false);
            }
        }
    }

    private void switchViewState(ViewState state, String title) {
        mCurrentViewState = state;
        if (getSupportActionBar() != null) {
            switch (state) {
                case MONTH:
                    getSupportActionBar().setTitle(title);
                    break;
                case YEAR:
                    getSupportActionBar().setTitle(title);
            }
        }
    }

    private String getMonthNameFromResources(LocalDate date) {
        return getResources().getStringArray(R.array.months)[date.getMonthOfYear() - 1] + " " +
                date.getYear();
    }
}