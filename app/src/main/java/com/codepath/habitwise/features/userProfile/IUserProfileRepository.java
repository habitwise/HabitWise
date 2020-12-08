package com.codepath.habitwise.features.userProfile;

import com.parse.ParseUser;

import java.util.List;

public interface IUserProfileRepository {
    void fetchFriendsList(IUserProfileEventListner eventListner);
}
