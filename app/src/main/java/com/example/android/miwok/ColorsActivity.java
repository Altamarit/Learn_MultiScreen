package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    //  Declare the media player
    private MediaPlayer mPlayerMiwok = null;
    //  Declare the List View
    private ListView listView = null;
    //  Declare an Audio Manager to implement audio focus
    protected AudioManager audioManager = null;

    //  Declare the OnCompletion Listener
    private MediaPlayer.OnCompletionListener mCompletionListener =
            new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    // Process once the sound is "completed"
                    completedPlayerMiwok();
                }
            };

    //  Declare the OnPrepared Listener
    private MediaPlayer.OnPreparedListener mPreparedListener =
            new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // Process once the sound is "prepared"
                    preparedPlayerMiwok();
                }
            };

    // Set Listener and capture the Click in the list view
    private ListView.OnItemClickListener mListViewClick =
            new ListView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the object that was clicked
                    Word itemClicked = (Word) parent.getItemAtPosition(position);
                    // pass the object to the function that has to operate with it.
                    try {
                        processItemClicked(itemClicked);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

    //  Set listener for audio Manager
    private AudioManager.OnAudioFocusChangeListener mAudioManagerChange =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    //
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                        // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                        // our app is allowed to continue playing sound but at a lower volume. We'll treat
                        // both cases the same way because our app is playing short sound files.

                        // Pause playback and reset player to the start of the file. That way, we can
                        // play the word from the beginning when we resume playback.
                        mPlayerMiwok.pause();
                        mPlayerMiwok.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                        mPlayerMiwok.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                        // Stop playback and clean up resources
                        completedPlayerMiwok();
                    }
                }
            };




    /*
        Actions on STOP of the Activity
     */
    @Override
    protected void onStop() {
        super.onStop();
        completedPlayerMiwok();
    }

    /*
        Actions on CREATION of the Activity
            1- Create the array of words
            2- Create the array adapter and pass it tot the List View
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //  Array with numbers
        ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("red", "weṭeṭṭi", R.drawable.color_red,
                R.raw.color_red));
        words.add(new Word("green", "chokokki", R.drawable.color_green,
                R.raw.color_green));
        words.add(new Word("brown", "ṭakaakki", R.drawable.color_brown,
                R.raw.color_brown));
        words.add(new Word("gray", "ṭopoppi", R.drawable.color_gray,
                R.raw.color_gray));
        words.add(new Word("black", "kululli", R.drawable.color_black,
                R.raw.color_black));
        words.add(new Word("white", "kelelli", R.drawable.color_white,
                R.raw.color_white));
        words.add(new Word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow,
                R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow,
                R.raw.color_mustard_yellow));

        //  Get audio services
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //  Create array adapter
        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_colors);

        //  Localize the ListView
        listView = (ListView) findViewById(R.id.list);

        //  Pass the array adapter into the ListView
        listView.setAdapter(itemsAdapter);

        // Set Listener to capture the Item Clicked
        listView.setOnItemClickListener(mListViewClick);

    }

    /**
     * Once the sound is "completed" (this is finished playing) then:
     *    1- release resources.
     */
    private void completedPlayerMiwok() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mPlayerMiwok != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mPlayerMiwok.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mPlayerMiwok = null;

            // Abandon audio focus when playback complete
            audioManager.abandonAudioFocus(mAudioManagerChange);
        }
    }

    /**
     * Once the sound is "prepared" (this is fetched and ready to be played) then:
     *    1- Play de Sound
     */
    private void preparedPlayerMiwok() {
        //  Play the Sound
        mPlayerMiwok.start();
    }

    /**
     * Actions on click in the List View
     *      1- Play de Sound in the Object -> details:
     *          1- Request sound focus
     *          1- Get the sound reference for the clicked object; build the URI
     *          2- Create the media player
     *          3- do and asyncronous preparation ---> Listener would trigger the PLAY
     *          4- set the listener for sound finished ---> Listener would release the resources
     */
    private void processItemClicked(Word itemClicked) throws IOException {
        // If the object has a sound to be plaid then play it
        if (itemClicked.hasMiwokSound()) {
            //  Request audio focus
            int result = audioManager.requestAudioFocus(mAudioManagerChange,
                    AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                // Prepare for playback
                mPlayerMiwok = new MediaPlayer();
                // Get the sound from the object anb build the URL
                Uri url = Uri.parse("android.resource://"
                        + getPackageName() + "/"
                        + itemClicked.getMiwokSound());
                mPlayerMiwok.setDataSource(this, url);

                // Set the listener so once it is prepared then can be played
                mPlayerMiwok.prepareAsync();
                mPlayerMiwok.setOnPreparedListener(mPreparedListener);

                // prepare the on completion listener so resources can be released
                //  once the sound finished playing
                mPlayerMiwok.setOnCompletionListener(mCompletionListener);
            }
        }
    }
}

