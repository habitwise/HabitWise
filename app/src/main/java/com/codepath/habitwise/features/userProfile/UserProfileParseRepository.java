package com.codepath.habitwise.features.userProfile;

import android.util.Log;

import com.codepath.habitwise.models.Friends;
import com.codepath.habitwise.objectKeys.ObjParseUser;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserProfileParseRepository implements IUserProfileRepository{

    private static UserProfileParseRepository userProfileParseRepository;
    public static final String TAG = "USER_PROFILE_PARSE_REPOSITORY";

    private UserProfileParseRepository(){}

    public static IUserProfileRepository getInstance() {
        UserProfileParseRepository repo = userProfileParseRepository;
        if (repo == null) {
            userProfileParseRepository = new UserProfileParseRepository();
        }
        return userProfileParseRepository;
    }

    public void fetchFriendsList(final IUserProfileEventListner eventListner) {
        final List<ParseUser> friendsLst = new ArrayList<>();
        ParseQuery<Friends> query = ParseQuery.getQuery(Friends.class);
        query.include(Friends.KEY_TO_USER);
        query.whereEqualTo(Friends.KEY_FROM_USER, ParseUser.getCurrentUser());
        query.whereContains(Friends.KEY_STATUS, "Accepted");
        query.findInBackground(new FindCallback<Friends>() {
            @Override
            public void done(List<Friends> objects, ParseException e) {
                if (e != null) {
                    Log.e(TAG,"Error while fetching to user: " + e.getMessage());
                    eventListner.fetchFriendsListFailed(e);
                    return;
                }
                for(Friends friend: objects) {
                   friendsLst.add(friend.getToUser());
                }

                ParseQuery<Friends> query = ParseQuery.getQuery(Friends.class);
                query.include(Friends.KEY_FROM_USER);
                query.whereEqualTo(Friends.KEY_TO_USER, ParseUser.getCurrentUser());
                query.whereContains(Friends.KEY_STATUS, "Accepted");
                query.findInBackground(new FindCallback<Friends>() {
                    @Override
                    public void done(List<Friends> objects, ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Error while fetching from User:" + e.getMessage());
                            eventListner.fetchFriendsListFailed(e);
                            return;
                        }
                        for(Friends friend: objects) {
                            friendsLst.add(friend.getFromUser());
                        }

                        sortFriendsList(friendsLst);
                        eventListner.fetchFriendsListSuccessful(friendsLst);
                    }
                });
            }
        });
    }

    // TODO: This should be in user class but since its not accessible, kept it here
    public void sortFriendsList(List<ParseUser> friendsList){
        Collections.sort(friendsList, new Comparator<ParseUser>() {
            @Override
            public int compare(ParseUser o1, ParseUser o2) {
                return ((o1.getString(ObjParseUser.KEY_FIRST_NAME) + o1.getString(ObjParseUser.KEY_LAST_NAME)).compareTo
                        (o2.getString(ObjParseUser.KEY_FIRST_NAME) + o2.getString(ObjParseUser.KEY_LAST_NAME)));
            }
        });
    }

    public void fetchFriendRequests(final IUserProfileEventListner eventListner) {
        final List<Friends> friendsRqs = new ArrayList<>();
        ParseQuery<Friends> query = ParseQuery.getQuery(Friends.class);
        query.include(Friends.KEY_FROM_USER);
        query.whereEqualTo(Friends.KEY_TO_USER, ParseUser.getCurrentUser());
        query.whereContains(Friends.KEY_STATUS, "Pending");
        query.findInBackground(new FindCallback<Friends>() {
            @Override
            public void done(List<Friends> objects, ParseException e) {
                if (e != null) {
                    Log.e(TAG,"Error while fetching to user: " + e.getMessage());
                    eventListner.fetchFriendRequestsFailed(e);
                    return;
                }
                friendsRqs.addAll(objects);
                eventListner.fetchFriendRequestsSuccessful(friendsRqs);
            }
        });
    }
}
