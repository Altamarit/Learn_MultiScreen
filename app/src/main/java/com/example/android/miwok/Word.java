package com.example.android.miwok;


/**
 * Created by s238780 on 22/02/2017.
 */

public class Word {

    // The two states for this class are the default translation and the Miwok one
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageId; // path to the image


    // constructor withOUT image
    public Word(String defaultTranslation, String miwokTranslation) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
    }
    // constructor with image
    public Word(String defaultTranslation, String miwokTranslation, int imageId) {
        mImageId = imageId;
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
    }
    public int getImage(){
        return  mImageId;
    }
    public String getDefaultTranslation(){
        return  mDefaultTranslation;
    }
    public String getMiwokTranslation(){
        return  mMiwokTranslation;
    }

}
