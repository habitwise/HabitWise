package com.codepath.habitwise.features.userProfile;

import com.codepath.habitwise.models.Friends;
import com.parse.ParseUser;

import java.util.List;

public interface IUserProfileEventListner {
    void fetchFriendsListSuccessful(List<ParseUser> newFriendsList);
    void fetchFriendsListFailed(Exception e);
    void updateRvFriendsList();

    void fetchFriendRequestsSuccessful(List<Friends> newFriendRequests);
    void fetchFriendRequestsFailed(Exception e);
    void updateRvFriendRequests();
}
