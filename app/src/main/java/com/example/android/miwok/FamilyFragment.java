package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {


    public FamilyFragment() {
        // Required empty public constructor
    }

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
            new ListView.OnItemClickListener() {
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

    // ON STOP event_____________________________________________________________________
    @Override
    public void onStop() {
        super.onStop();
        completedPlayerMiwok();
    }// END of ON STOP event_____________________________________________________________

    // ON CREATE event___________________________________________________________________
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate the fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        //  Array with numbers
        ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("father", "әpә", R.drawable.family_father,
                R.raw.family_father));
        words.add(new Word("mother", "әṭa", R.drawable.family_mother,
                R.raw.family_mother));
        words.add(new Word("son", "angsi", R.drawable.family_son,
                R.raw.family_son));
        words.add(new Word("daughter", "tune", R.drawable.family_daughter,
                R.raw.family_daughter));
        words.add(new Word("older brother", "taachi", R.drawable.family_older_brother,
                R.raw.family_older_brother));
        words.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother,
                R.raw.family_younger_brother));
        words.add(new Word("older sister", "teṭe", R.drawable.family_older_sister,
                R.raw.family_older_sister));
        words.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister,
                R.raw.family_younger_sister));
        words.add(new Word("grandmother", "ama", R.drawable.family_grandmother,
                R.raw.family_grandmother));
        words.add(new Word("grandfather", "paapa", R.drawable.family_grandfather,
                R.raw.family_grandfather));


        //  Get audio services
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //  Create array adapter
        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_family);

        //  Localize the ListView
        listView = (ListView) rootView.findViewById(R.id.list);

        //  Pass the array adapter into the ListView
        listView.setAdapter(itemsAdapter);

        // Set Listener to capture the Item Clicked
        listView.setOnItemClickListener(mListViewClick);

        // return the fragment
        return rootView;

    }// END of ON CREATE event___________________________________________________________


    /**
     * Once the sound is "completed" (this is finished playing) then:
     * 1- release resources.
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
     * 1- Play de Sound
     */
    private void preparedPlayerMiwok() {
        //  Play the Sound
        mPlayerMiwok.start();
    }

    /**
     * Actions on click in the List View
     * 1- Play de Sound in the Object -> details:
     * 1- Request sound focus
     * 1- Get the sound reference for the clicked object; build the URI
     * 2- Create the media player
     * 3- do and asyncronous preparation ---> Listener would trigger the PLAY
     * 4- set the listener for sound finished ---> Listener would release the resources
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
                        + getActivity().getPackageName() + "/"
                        + itemClicked.getMiwokSound());
                mPlayerMiwok.setDataSource(getActivity(), url);

                // Set the listener so once it is prepared then can be played
                mPlayerMiwok.prepareAsync();
                mPlayerMiwok.setOnPreparedListener(mPreparedListener);

                // prepare the on completion listener so resources can be released
                //  once the sound finished playing
                mPlayerMiwok.setOnCompletionListener(mCompletionListener);
            }
        }
    }



}// END of Fragment Class




