package com.elliecoding.nasalookout.screens;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.elliecoding.nasalookout.R;
import com.elliecoding.nasalookout.entities.NasaData;
import com.elliecoding.nasalookout.logics.ContainerAdapter;
import com.elliecoding.nasalookout.logics.FragmentCallable;
import com.elliecoding.nasalookout.utils.DateHelper;
import com.elliecoding.nasalookout.utils.JsonParser;
import org.joda.time.LocalDate;

import java.util.List;

public class ActivityMain extends AppCompatActivity implements FragmentCallable {

    private static final String LOG_TAG = ActivityMain.class.getSimpleName();
    private static final String TAG_OVERVIEW = "overview";

    private ActionBarDrawerToggle mDrawerToggle;
    private boolean mRequestHd;

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

        startInitialRequest();
    }

    private void initialiseOverview() {
        RecyclerView blockGrid = findViewById(R.id.block_container);

        // We know the layout will not change, supposed to increase performance
        blockGrid.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        blockGrid.setLayoutManager(manager);
        blockGrid.setAdapter(new ContainerAdapter());

        // The adapter is currently empty, initialise the first couple of blocks
        retrieveDataContainers();
    }

    private void retrieveDataContainers() {
        for (LocalDate date : DateHelper.determineStartingDates(calculateNumberOfBlocks())) {

        }
    }

    private int calculateNumberOfBlocks() {
        // TODO check how many boxes we need to fill the screen according to screen size
        return 6;
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
                    .add(R.id.fragment_container, firstFragment, TAG_OVERVIEW).commit();
        }
    }

    private void startInitialRequest() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.nasa.gov/planetary/apod?api_key=ht3oMoWKS6pKVaHJGFQ7IjrzkFnleJ0wTP4Hr1TQ";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                startDetailedRequest(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w(LOG_TAG, "Request failed, " + error.getMessage());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void startDetailedRequest(String response) {
        NasaData data = JsonParser.parseResponse(response);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = data.getUrl();

        // Request a string response from the provided URL.
        ImageRequest stringRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                sendResponseToGallery(response);
            }
        }, 1024, 1024, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w(LOG_TAG, "Request failed, " + error.getMessage());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void sendResponseToGallery(Bitmap image) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_OVERVIEW);
        if (fragment != null && fragment instanceof FragmentOverview) {
            ((FragmentOverview) fragment).displayImage(image);
        }
    }

    private void initialiseDrawer() {
        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
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

        // TODO when in detail go to overview, when in overview close app. When drawer open close drawer
        super.onBackPressed();
    }

    @Override
    public void onFavouriteClicked(String id) {
        // TODO use method
    }
}