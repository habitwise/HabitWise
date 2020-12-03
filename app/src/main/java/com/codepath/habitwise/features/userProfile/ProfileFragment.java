package com.codepath.habitwise.features.userProfile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.habitwise.R;
import com.codepath.habitwise.models.Friends;
import com.codepath.habitwise.objectKeys.ObjFriends;
import com.codepath.habitwise.objectKeys.ObjParseUser;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    public static final String TAG = "USER_PROFILE_FRAGMENT";
    private RecyclerView rvFriends;
    protected FriendsListAdapter friendsListAdapter;
    protected List<ParseUser> friends;
    private TextView tvName;
    private SwipeRefreshLayout swipeContainer;
    private CircleImageView displayPic;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        rvFriends = view.findViewById(R.id.rvFriends);
        tvName = view.findViewById(R.id.tvName);
        displayPic = view.findViewById(R.id.ivUserProfileImage);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                updateView();
                swipeContainer.setRefreshing(false);
            }
        });

        friends = new ArrayList<>();
        friendsListAdapter = new FriendsListAdapter(getContext(), friends);
        rvFriends.setAdapter(friendsListAdapter);
        rvFriends.setLayoutManager(new LinearLayoutManager(getContext()));
        updateView();
    }

    private void updateView() {
        tvName.setText(ParseUser.getCurrentUser().get(ObjParseUser.KEY_FIRST_NAME) + " " + ParseUser.getCurrentUser().get(ObjParseUser.KEY_LAST_NAME));
        updateFriendsList();
    }

    private void updateFriendsList(){
        ParseQuery<Friends> query = ParseQuery.getQuery("Friends");

        query.include(ObjParseUser.KEY_TABLE);
        query.whereEqualTo(ObjFriends.KEY_FROM_USER, ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Friends>() {
            @Override
            public void done(List<Friends> objects, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error occured while fetching all posts");
                } else {
                    Log.i(TAG, "Fetching all posts successful");
                    for (Friends friend : objects) {
                        Log.i(TAG, friend.getToUser().getUsername());
                        friends.add(friend.getToUser());
                    }
                    friendsListAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}