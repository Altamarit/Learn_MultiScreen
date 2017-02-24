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
    private boolean mHasImageProvided = FALSE;
    private int mMiwokSound;
    private boolean mHasSoundProvided = FALSE;

    // constructor withOUT image
    public Word(String defaultTranslation, String miwokTranslation, int miwokSound) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mHasImageProvided = FALSE;
        mMiwokSound = miwokSound;
        mHasSoundProvided = TRUE;
    }

    // constructor with image AND sound
    public Word(String defaultTranslation, String miwokTranslation, int imageId, int miwokSound) {
        mImageId = imageId;
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mHasImageProvided = TRUE;
        mMiwokSound = miwokSound;
        mHasSoundProvided = TRUE;
    }


    public int getImage() {
        return mImageId;
    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getMiwokSound() {
        return mMiwokSound;
    }

    public boolean hasImage() {
        return mHasImageProvided;
    }

    public boolean hasMiwokSound() {
        return mHasSoundProvided;
    }

}
