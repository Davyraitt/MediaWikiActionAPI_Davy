package com.avans.mediawikiactionapi_davy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collections;

public class RecyclerView extends AppCompatActivity implements PhotoAdapter.OnItemClickListener, WikiApiListener {
    private static final String LOGTAG = RecyclerView.class.getName();
    private androidx.recyclerview.widget.RecyclerView RecyclerViewPictures;
    private ArrayList<WikiPictures> pictures;
    private PhotoAdapter photoAdapter;
    private WikiApiManager wikiApiManager;


    //declaring the stuff needed to reload etc...
    private EditText amountToLoad;
    private Button saveAmountToLoad;
    private Button reload;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__list_view);

        //Setting up sharedpreferences so we can save persistent
        sharedPref = getSharedPreferences("amountToLoad", Context.MODE_PRIVATE);
        editor = sharedPref.edit();


        //Pictures is the arraylist that contains our pictures
        pictures = new ArrayList<WikiPictures>();

        //Linking the recyclerview
        RecyclerViewPictures = findViewById(R.id.RecyclerViewPictures);

        //Creating the photoadapter
        photoAdapter = new PhotoAdapter(this, pictures, this);

        //Setting the layoutmanager and photoadapter
        RecyclerViewPictures.setAdapter(photoAdapter);
        RecyclerViewPictures.setLayoutManager(new LinearLayoutManager(this));


        //Creating our wikiapimanager
        wikiApiManager = new WikiApiManager(this, this, 1);
        wikiApiManager.getWikiPhotos(sharedPref);

        //handles the saving part
        amountToLoad = (EditText) findViewById(R.id.editText6);
        saveAmountToLoad = (Button) findViewById(R.id.button3);
        saveAmountToLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveAmountToLoad();
            }
        });


        //handles the reloading part
        reload = (Button) findViewById(R.id.button2);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleReload();
            }
        });

    }

    private void handleReload() {
        //this method handles when the reload button is clicked
        System.out.println("reloading with.. : " + sharedPref.getInt("amountToLoad", 0));
        pictures.clear();
        wikiApiManager.getWikiPhotos(sharedPref);
    }

    private void handleSaveAmountToLoad() {
        //this method handles when the save button is clicked
        editor.putInt("amountToLoad", Integer.parseInt(amountToLoad.getText().toString()));
        editor.apply();


        System.out.println("current amount is.. " + sharedPref.getInt("amountToLoad", 0));
    }


    @Override
    public void onItemClick(int clickedPosition) {
        //We clicked the item, so now we start a new activity
        System.out.println("onItemClick() called for position " + clickedPosition);
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra(DetailActivity.EXTRA_PHOTO, pictures.get(clickedPosition));
        startActivity(detailIntent);
    }

    @Override
    public void onPhotoAvailable(WikiPictures photo) {
        System.out.println("onPhotoAvailable() called");
        //adding and sorting the pictures so we show the most recent first!
        pictures.add(photo);
        Collections.sort(pictures);
        photoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPhotoError(Error error) {
        Log.e(LOGTAG, "Error: " + error.toString());
    }


}
