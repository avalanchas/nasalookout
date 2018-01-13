package com.elliecoding.nasalookout.logics;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.elliecoding.nasalookout.entities.NasaData;
import com.elliecoding.nasalookout.utils.JsonHelper;
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

    /**
     * Determines whether the Nasa Data information for the specified date exists as a file in the Android internal
     * storage directory. This does <b>not</b> guarantee that the file is intact, complete, accessible, writable or
     * other. It also doe
     *
     * @param date The date for which to check the storage
     * @return true if the file exists, false otherwise
     */
    public boolean hasInStorage(LocalDate date) {
        return new File(mContext.getFilesDir(), date.getYear() + "/" + date.getMonthOfYear() + "/" + date.getDayOfMonth
                () + ".json").exists();
    }

    public void requestFromStorage(LocalDate date) {
        new FileReaderTask().execute(date);
    }

    public void writeToStorage(NasaData data) {
        new FileWriterTask().execute(data);
    }

    private class FileReaderTask extends AsyncTask<LocalDate, Integer, NasaData> {

        @Override
        protected NasaData doInBackground(LocalDate... dates) {
            String filename = getFilenameByDate(dates[0]);

            try (Reader reader = new FileReader(new File(mContext.getFilesDir(), filename))) {
                return JsonHelper.streamDataFromJson(reader, NasaData.class);
            } catch (FileNotFoundException e) {
                Log.d(LOG_TAG, "File for date " + dates[0] + " was not found", e);
                return null;
            } catch (IOException e) {
                Log.e(LOG_TAG, "IO Exception during file read", e);
                return null;
            }
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

            if (!new File(mContext.getFilesDir(), filename).getParentFile().mkdirs()) {
                Log.d(LOG_TAG, "File already existed or error");
            }

            try (Writer writer = new FileWriter(new File(mContext.getFilesDir(), filename))) {
                JsonHelper.streamDataToJson(data[0], writer);
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
