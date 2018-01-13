package com.elliecoding.nasalookout.logics;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.elliecoding.nasalookout.entities.NasaData;
import com.elliecoding.nasalookout.utils.AppConstants;
import com.elliecoding.nasalookout.utils.JsonHelper;
import org.joda.time.LocalDate;

public class InternetDataManager {

    private static final String LOG_TAG = InternetDataManager.class.getSimpleName();

    private static final int NONE = 0;

    private final Context mContext;
    private final DataEventListener mListener;

    public InternetDataManager(Context context) {
        mContext = context;
        if (mContext instanceof DataEventListener) {
            mListener = (DataEventListener) context;
        } else {
            throw new IllegalStateException("A context that wishes to receive Internet updates must implement " +
                    DataEventListener.class.getName());
        }
    }

    public void requestFromInternet(final LocalDate date) {
        RequestQueue queue = Volley.newRequestQueue(mContext);

        // We can use "toString" here since NASA requests YYYY-MM-DD, which the date returns
        String url = "https://api.nasa.gov/planetary/apod?api_key=" + AppConstants.API_KEY + "&date=" + date.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                NasaData data = JsonHelper.parseJsonToData(response);
                startImageRequest(data);
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

    private void startImageRequest(final NasaData data) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        ImageRequest stringRequest = new ImageRequest(data.getUrl(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                data.setImage(response);
                notifyListener(data);
            }
        }, NONE, NONE, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w(LOG_TAG, "Request failed, " + error.getMessage());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void notifyListener(NasaData data) {
        mListener.onDataRetrieved(data);
    }
}
