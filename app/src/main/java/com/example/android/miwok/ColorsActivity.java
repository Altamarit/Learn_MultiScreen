package com.example.android.miwok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //  Array with numbers
        ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("red", "weṭeṭṭi",R,));
        words.add(new Word("green", "chokokki"));
        words.add(new Word("brown", "ṭakaakki"));
        words.add(new Word("gray", "ṭopoppi"));
        words.add(new Word("black", "kululli"));
        words.add(new Word("white", "kelelli"));
        words.add(new Word("dusty yellow", "ṭopiisә"));
        words.add(new Word("mustard yellow", "chiwiiṭә"));


        //  Create array adapter
//        ArrayAdapter<Word> itemsAdapter = new ArrayAdapter<Word>(this, android.R.layout.simple_list_item_1, words);
        WordAdapter itemsAdapter = new WordAdapter(this, words);

        //  Localize the ListView
        ListView listView = (ListView) findViewById(R.id.list);
        //  Pass the array adapter into the ListView
        listView.setAdapter(itemsAdapter);

        // set the color background
//        listView.setBackgroundColor(getColor(R.color.category_colors));
    }
}
