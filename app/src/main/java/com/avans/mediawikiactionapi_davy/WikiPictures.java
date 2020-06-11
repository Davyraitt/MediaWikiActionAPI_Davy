package com.avans.mediawikiactionapi_davy;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class WikiPictures implements Serializable, Comparable<WikiPictures>{

    private static final String LOGTAG = WikiPictures.class.getName();

    private String name; // The filename of the image on WikiMedia
    private String timestamp; // A string representation of the timestamp of the image
    private String url; // The URL for the image on WikiMedia, use this to load the image
    private String descriptionurl; // The URL for the description page on WikiMedia
    private String title; // The user who uploaded the image to WikiMedia


    public WikiPictures(JSONObject json) {
        try {
            //gets the attributes based on the JSON file we passed trough
            name = json.getString("name");
            timestamp = json.getString("timestamp");
            url = json.getString("url");
            descriptionurl = json.getString("descriptionurl");
            title = json.getString("title");
        } catch (JSONException exception) {
            Log.e(LOGTAG, "Error parsing JSON to construct WikiImage");
        }
    }

    public String toString() {
        return name + "[" + url + "]" + "@" + timestamp;
    }

    public String getName() {
        return name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUrl() {
        return url;
    }

    public String getDescriptionurl() {
        return descriptionurl;
    }

    public String getTitle() {
        return title;
    }

    public String getCaption() {
        //This caption is like a toString method, we can request it and it returns a String
        StringBuilder builder = new StringBuilder(50);
        builder.append(" Name: ");
        builder.append(getName());
        builder.append(" Title: ");
        builder.append(getTitle());
        builder.append(" Time ");
        builder.append(getTimestamp());
        builder.append(" Description URL: ");
        builder.append(getDescriptionurl());
        return builder.toString();
    }

    @Override
    public int compareTo(WikiPictures o) {
        //compareTo to sort by date...
        return o.getTimestamp().compareTo(getTimestamp());
    }
}
