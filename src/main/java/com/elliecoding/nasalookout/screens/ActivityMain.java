package com.elliecoding.nasalookout.screens;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import com.elliecoding.nasalookout.R;
import com.elliecoding.nasalookout.entities.NasaData;
import com.elliecoding.nasalookout.logics.*;
import com.elliecoding.nasalookout.utils.DateHelper;
import org.joda.time.LocalDate;

public class ActivityMain extends AppCompatActivity implements FragmentEventListener, DataEventListener {

    private static final String LOG_TAG = ActivityMain.class.getSimpleName();

    private static final String FRAGMENT_TAG_OVERVIEW = "overview";
    private static final String FRAGMENT_TAG_DETAIL = "detail";

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
        initialiseFragments(savedInstanceState);
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
       sendResponseToGallery(data);
    }


    private void sendResponseToGallery(NasaData image) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_OVERVIEW);
        if (fragment != null && fragment instanceof FragmentOverview) {
            ((FragmentOverview) fragment).addDataToOverview(image);
        }
    }

    private void initialiseFragments(Bundle savedInstanceState) {
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            FragmentOverview firstFragment = FragmentOverview.newInstance();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment, FRAGMENT_TAG_OVERVIEW).commit();
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
    public void onFavouriteClicked(String id) {
        // TODO use method
    }
}