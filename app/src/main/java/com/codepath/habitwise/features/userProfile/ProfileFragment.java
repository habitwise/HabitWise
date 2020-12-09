package com.codepath.habitwise.features.userProfile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.habitwise.R;
import com.codepath.habitwise.models.Friends;
import com.codepath.habitwise.objectKeys.ObjParseUser;
import com.parse.GetFileCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements IUserProfileEventListner{

    public static final String TAG = "USER_PROFILE_FRAGMENT";
    private RecyclerView rvFriends;
    protected FriendsListAdapter friendsListAdapter;
    protected FriendRequestAdapter friendRequestAdapter;
    protected List<ParseUser> friends;
    protected List<Friends> friendRequests;
    private TextView tvName;
    private SwipeRefreshLayout swipeContainer;
    private CircleImageView displayPic;
    private RecyclerView rvRequests;
    private TextView tvRequests;


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
        tvRequests = view.findViewById(R.id.tvRequests);
        rvRequests = view.findViewById(R.id.rvRequests);

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
        friendRequests = new ArrayList<>();
        friendsListAdapter = new FriendsListAdapter(getContext(), friends);
        friendRequestAdapter = new FriendRequestAdapter(getContext(), friendRequests);
        rvFriends.setAdapter(friendsListAdapter);
        rvRequests.setAdapter(friendRequestAdapter);
        rvFriends.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRequests.setLayoutManager(new LinearLayoutManager(getContext()));
        updateView();


    }

    private void updateView() {
        tvName.setText(ParseUser.getCurrentUser().get(ObjParseUser.KEY_FIRST_NAME) + " " + ParseUser.getCurrentUser().get(ObjParseUser.KEY_LAST_NAME));
        ParseUser.getCurrentUser().getParseFile(ObjParseUser.KEY_DISPLAY_PIC).getFileInBackground(new GetFileCallback() {
            @Override
            public void done(File file, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while loading profile pic: " + e.getMessage());
                    return;
                }
                String filePath = file.getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                displayPic.setImageBitmap(bitmap);
            }
        });
        IUserProfileRepository repository = UserProfileParseRepository.getInstance();
        repository.fetchFriendRequests(this);
        repository.fetchFriendsList(this);
    }


    @Override
    public void fetchFriendsListSuccessful(List<ParseUser> newFriendsList) {
        friends.clear();
        friends.addAll(newFriendsList);
        updateRvFriendsList();
    }

    @Override
    public void fetchFriendsListFailed(Exception e) {
        if (e != null) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    @Override
    public void updateRvFriendsList() {
        friendsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void fetchFriendRequestsSuccessful(List<Friends> newFriendRequests) {
        friendRequests.clear();
        friendRequests.addAll(newFriendRequests);
        updateRvFriendRequests();
    }

    @Override
    public void fetchFriendRequestsFailed(Exception e) {
        if (e != null) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    @Override
    public void updateRvFriendRequests() {
        friendRequestAdapter.notifyDataSetChanged();
    }
}