package com.codepath.habitwise.features.addUpdateHabit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.codepath.habitwise.R;
import com.codepath.habitwise.objectKeys.ObjParseUser;
import com.parse.ParseUser;

import java.util.ArrayList;

public class FriendsSpinnerAdapter extends ArrayAdapter<ParseUser> {

    public FriendsSpinnerAdapter(Context context,
                            ArrayList<ParseUser> friendsList)
    {
        super(context, 0, friendsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView,
                          ViewGroup parent)
    {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.text_view);
        ParseUser currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem == ParseUser.getCurrentUser()) {
            textViewName.setText("Just myself");
        }
        else if (currentItem != null) {
            textViewName.setText(currentItem.get(ObjParseUser.KEY_FIRST_NAME) + " " + currentItem.get(ObjParseUser.KEY_LAST_NAME).toString());
        }
        return convertView;
    }
}

