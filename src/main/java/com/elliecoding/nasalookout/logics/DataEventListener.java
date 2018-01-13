package com.elliecoding.nasalookout.logics;

import com.elliecoding.nasalookout.entities.NasaData;

public interface DataEventListener {

    void onDataRetrieved(NasaData data);
}
