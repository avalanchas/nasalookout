package com.elliecoding.nasalookout.entities;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import org.joda.time.LocalDate;

import java.io.Serializable;

/**
 * Simple DTO for a nasa astronomy picture of the day. Holds all relevant information that was passed by the Nasa API.
 * Note that any data field may be empty or contain corrupt information
 *
 * @see <a href=https://api.nasa.gov/index.html>APOD API</a>
 */
public class NasaData implements Comparable<NasaData>, Serializable {

    private static final long serialVersionUID = 3915531850180120791L;

    private final String copyright;
    private final LocalDate date;
    private final String explanation;
    private final String url;
    private final String hdUrl;
    private final String title;

    @SerializedName("service_version")
    private final String serviceVersion;

    @SerializedName("media_type")
    private final MediaType mediaType;

    private SerializedBitmap image;

    public enum MediaType {
        @SerializedName("image")
        IMAGE,

        @SerializedName("video")
        VIDEO;
    }

    public NasaData(String copyright, LocalDate date, String explanation, String url, String hdUrl, MediaType
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

    public String getCopyright() {
        return copyright;
    }

    public String getExplanation() {
        return explanation;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public String getTitle() {
        return title;
    }

    public void setImage(Bitmap image) {
        this.image = new SerializedBitmap(image);
    }

    public Bitmap getImage() {
        return image.getBitmap();
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public int compareTo(@NonNull NasaData other) {
        return date.compareTo(other.getDate());
    }
}
