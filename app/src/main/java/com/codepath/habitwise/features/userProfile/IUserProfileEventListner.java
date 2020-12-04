package com.codepath.habitwise.features.userProfile;

import com.parse.ParseUser;

import java.util.List;

public interface IUserProfileEventListner {
    void fetchFriendsListSuccessful(List<ParseUser> newFriendsList);
    void fetchFriendsListFailed(Exception e);
    void updateRvFriendsList();
}
