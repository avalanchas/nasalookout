package com.elliecoding.nasalookout.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Simple DTO for a nasa astronomy picture of the day. Holds all relevant information that was passed by the Nasa API.
 * Note that any data field may be empty or contain corrupt information
 *
 * @see <a href=https://api.nasa.gov/index.html>APOD API</a>
 */
public class NasaData {

    private final String copyright;
    private final String date;
    private final String explanation;
    private final String url;
    private final String hdUrl;
    private final MediaType mediaType;
    private final String serviceVersion;
    private final String title;


    public enum MediaType {
        @SerializedName("image")
        IMAGE,

        @SerializedName("")
        VIDEO;
    }

    public NasaData(String copyright, String date, String explanation, String url, String hdUrl, MediaType
            mediaType, String serviceVersion, String title) {
        this.copyright = copyright;
        this.date = date;
        this.explanation = explanation;
        this.url = url;
        this.hdUrl = hdUrl;
        this.mediaType = mediaType;
        this.serviceVersion = serviceVersion;
        this.title = title;
    }


    public String getUrl() {
        return url;
    }
}
