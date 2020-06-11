package com.avans.mediawikiactionapi_davy;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WikiApiManager {
    private static final String LOGTAG = RecyclerView.class.getName();

    private Context appContext;
    private RequestQueue queue;
    private WikiApiListener listener;
    private int amountToLoad;

    public WikiApiManager(Context appContext, WikiApiListener listener, int amountToLoad) {
        this.appContext = appContext;
        this.listener = listener;
        //requestQueue is used to stack your request and handles your cache.
        this.queue = Volley.newRequestQueue(this.appContext);
        this.amountToLoad = amountToLoad;
    }

    public void getWikiPhotos(SharedPreferences sharedPref) {
        // We pass the sharedPref to here so we can access it
        //Create the url based on the amountToLoad which is selected
        String url =
                "https://commons.wikimedia.org/w/api.php?action=query&format=json&list=allimages&aifrom=Nature&ailimit=" + sharedPref.getInt("amountToLoad", 0);
        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(LOGTAG, "Volley response: " + response.toString());
                        try {
                            //Creates a new JSONOBject from query. This is checked with POSTMAN
                            JSONObject queryObject = response.getJSONObject("query");
                            //Proceeds to get the array from JSON object allimages
                            JSONArray jArray = queryObject.getJSONArray("allimages");

                            //For each picture in the arraylist we create a photo and add it to the arraylist
                            //We do this by calling the method onPhotoAvailable in the Recyclerview class
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject photoJsonObject = jArray.getJSONObject(i);
                                WikiPictures photo = new WikiPictures(photoJsonObject);
                                Log.d(LOGTAG, "Adding photo: " + photo.getCaption());
                                listener.onPhotoAvailable(photo);
                            }

                        } catch (JSONException exception) {
                            Log.e(LOGTAG, "Error while parsing JSON data: " + exception.getLocalizedMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(LOGTAG, error.getLocalizedMessage());
                        listener.onPhotoError(new Error(error.getLocalizedMessage()));
                    }
                }
        );
        this.queue.add(request);
    }
}
