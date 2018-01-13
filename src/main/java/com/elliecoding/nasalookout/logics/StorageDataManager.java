package com.elliecoding.nasalookout.logics;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.elliecoding.nasalookout.entities.NasaData;
import com.google.gson.Gson;
import org.joda.time.LocalDate;

import java.io.*;

public class StorageDataManager {

    private static final String LOG_TAG = StorageDataManager.class.getSimpleName();

    private Context mContext;
    private DataEventListener mListener;

    public StorageDataManager(Context context) {
        mContext = context;
        if (mContext instanceof DataEventListener) {
            mListener = (DataEventListener) context;
        } else {
            throw new IllegalStateException("A context that wishes to receive Internet updates must implement " +
                    DataEventListener.class.getName());

        }
    }

    public boolean hasInStorage(Context context, LocalDate date) {
        return new File(context.getFilesDir(), date.getYear() + "/" + date.getMonthOfYear() + "/" + date.getDayOfMonth
                ()).exists();
    }

    public void requestFromStorage(LocalDate date) {
        new FileReaderTask().execute(date);
    }

    public void writeToStorage(Context context, NasaData data) {
        new FileWriterTask().execute(data);
    }

    private class FileReaderTask extends AsyncTask<LocalDate, Integer, NasaData> {

        @Override
        protected NasaData doInBackground(LocalDate... dates) {
            String filename = getFilenameByDate(dates[0]);
            try (Reader reader = new FileReader(new File(mContext.getFilesDir(), filename))) {
                return new Gson().fromJson(reader, NasaData.class);
            } catch (FileNotFoundException e) {
                Log.d(LOG_TAG, "File for date " + dates[0] + " was not found", e);
            } catch (IOException e) {
                Log.e(LOG_TAG, "IO Exception during file read", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(NasaData data) {
            notifyListener(data);
        }

    }

    private class FileWriterTask extends AsyncTask<NasaData, Integer, Void> {

        @Override
        protected Void doInBackground(NasaData... data) {
            LocalDate date = data[0].getDate();
            String filename = getFilenameByDate(date);

            try (Writer writer = new FileWriter(new File(mContext.getFilesDir(), filename))) {
                new Gson().toJson(data, writer);
            } catch (IOException e) {
                Log.e(LOG_TAG, "IO Exception during file write", e);
            }
            return null;
        }
    }

    private void notifyListener(NasaData data) {
        mListener.onDataRetrieved(data);
    }


    private static String getFilenameByDate(LocalDate date) {
        return date.getYear() + "/" + date.getMonthOfYear() + "/" + date.getDayOfMonth() + ".json";
    }

}
