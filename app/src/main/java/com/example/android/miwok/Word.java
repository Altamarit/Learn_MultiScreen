package com.example.android.miwok;


/**
 * Created by s238780 on 22/02/2017.
 */

public class Word {

    // The two states for this class are the default translation and the Miwok one
    private String mDefaultTranslation;
    private String mMiwokTranslation;


    // contructor
    public Word(String defaultTranslation, String miwokTranslation) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
    }
    public String getDefaultTranslation(){
        return  mDefaultTranslation;
    }
    public String getMiwokTranslation(){
        return  mMiwokTranslation;
    }

}
