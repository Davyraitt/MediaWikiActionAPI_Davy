package com.avans.mediawikiactionapi_davy;

public interface WikiApiListener {
    //interface WikiApiListener
    public void onPhotoAvailable(WikiPictures photo);
    public void onPhotoError(Error error);
}
