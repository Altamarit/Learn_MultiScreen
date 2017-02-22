package com.example.android.miwok;


import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by s238780 on 22/02/2017.
 */

public class Word {

    // The two states for this class are the default translation and the Miwok one
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageId; // path to the image
    private boolean mHasImageProvided =FALSE ;

    // constructor withOUT image
    public Word(String defaultTranslation, String miwokTranslation) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mHasImageProvided = FALSE;
    }
    // constructor with image
    public Word(String defaultTranslation, String miwokTranslation, int imageId) {
        mImageId = imageId;
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mHasImageProvided = TRUE;
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

    public boolean hasImage(){
        return mHasImageProvided;
    }

}
