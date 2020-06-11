package com.avans.mediawikiactionapi_davy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    //A Context is a handle to the system; it provides services like resolving resources,
    // obtaining access to databases and preferences, and so on. An Android app has activities.
    // Context is like a handle to the environment your application is currently running in.
    private Context appContext;
    //List of pictures
    private List<WikiPictures> photoList;
    //Clicklistener, when we click something an action can be called
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(int clickedPosition);
    }

    public PhotoAdapter(Context context, List<WikiPictures> photos, OnItemClickListener listener) {
        appContext = context;
        photoList = photos;
        clickListener = listener;
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView photoImageView;
        public final TextView captionTextView;

        public PhotoViewHolder(View itemView, PhotoAdapter adapter) {
            super(itemView);
            //defining our picture and the text below it
            photoImageView = itemView.findViewById(R.id.activity_list_picture);
            captionTextView = itemView.findViewById(R.id.activity_list_picture_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //when its clicked this happens
            //gets the clickedposition
            int clickedPosition = getAdapterPosition();
            //prints what has been clicked
            System.out.println("Item " + clickedPosition + " was clicked");
            //calls the onItemClick method, passing trough the clickedposition
            clickListener.onItemClick(clickedPosition);
        }
    }


    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        System.out.println("onCreateViewHolder() called");
        //The LayoutInflater class is used to instantiate the contents of layout XML files into their corresponding View objects.
        //In other words, it takes an XML file as input and builds the View objects from it.
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_list_picture, viewGroup, false);

        PhotoViewHolder viewHolder = new PhotoViewHolder(itemView, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder photoViewHolder, int i) {
        System.out.println("onBindViewHolder() called for item " + i);
        //gets the picture
        WikiPictures photo = photoList.get(i);
        //sets the correct caption
        photoViewHolder.captionTextView.setText(photo.getCaption());
        //loads and draws the picture
        Glide.with(appContext).load(photo.getUrl()).into(photoViewHolder.photoImageView);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

}