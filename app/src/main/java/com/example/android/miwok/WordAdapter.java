package com.example.android.miwok;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by s238780 on 22/02/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private static final String LOG_TAG = ArrayAdapter.class.getSimpleName();
    private int mColorResourceId;

    /**
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context The current context. Used to inflate the layout file.
     * @param word    A List of Word objects to display in a list
     */
    public WordAdapter(Activity context, ArrayList<Word> word, int colorResourceId) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, word);
        mColorResourceId=colorResourceId;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID
        TextView miwok_translation = (TextView) listItemView.findViewById(R.id.lbl_miwok_translation);
        // Get the miwok translation from the current object pair (Miwok/English) and set this
        // text on the miwok TextView
        miwok_translation.setText(currentWord.getMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the ID
        TextView default_translation = (TextView) listItemView.findViewById(R.id.lbl_default_translation);
        // Get the English/Default translation from the current object pair (Miwok/English) and set
        // this text on the default TextView
        default_translation.setText(currentWord.getDefaultTranslation());

        // Find the TextView in the list_item.xml layout with the ID image
        ImageView image = (ImageView) listItemView.findViewById(R.id.img_image);
        if (currentWord.hasImage()) {
            image.setImageResource(currentWord.getImage());
            image.setVisibility(View.VISIBLE);
        } else {
            image.setVisibility(View.GONE);
        }

        LinearLayout layTranslations = (LinearLayout) listItemView.findViewById(R.id.lay_translations);
        layTranslations.setBackgroundColor(ContextCompat.getColor(getContext(),mColorResourceId));

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
}