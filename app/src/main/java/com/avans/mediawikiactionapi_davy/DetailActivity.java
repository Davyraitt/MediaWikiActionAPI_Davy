package com.avans.mediawikiactionapi_davy;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private static final String LOGTAG = DetailActivity.class.getName();
    public static final String EXTRA_PHOTO = "DJRAITT_PHOTO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting the view
        setContentView(R.layout.activity_detail);

        //defining the photo
        WikiPictures photo = (WikiPictures) getIntent().getSerializableExtra(EXTRA_PHOTO);
        Log.d(LOGTAG, "Show photo " + photo.getCaption());
        ImageView detailPhotoView = findViewById(R.id.detailPhotoImageView);

        //Defining the textviews
        TextView idName = findViewById(R.id.idName);
        TextView idTime = findViewById(R.id.idTime);
        TextView idUser = findViewById(R.id.idUser);

        //setting the textviews in the detailscreen
        idName.setText(getString(R.string.idName) + "   " +  photo.getName());
        idTime.setText(getString(R.string.idTime) + "   " + photo.getTimestamp());
        idUser.setText(getString(R.string.idUser) +"   " +  photo.getName());

        //loading the picture with glide, used picasso first but replaced it
        Glide.with(this).load(photo.getUrl()).into(detailPhotoView);
        //Picasso.get().load(photo.getUrl()).into(detailPhotoView);
    }

}
