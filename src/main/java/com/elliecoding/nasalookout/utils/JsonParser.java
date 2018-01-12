package com.elliecoding.nasalookout.utils;

import com.elliecoding.nasalookout.entities.NasaData;
import com.google.gson.Gson;

/**
 * Helper class for all json access. Makes use of Google Gson project
 */
public class JsonParser {

    private JsonParser() {
        throw new AssertionError("Static util class, do not instantiate");
    }

    public static NasaData parseResponse(String response) {
        return new Gson().fromJson(response, NasaData.class);
    }
}
