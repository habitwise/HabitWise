package com.codepath.habitwise.features.userProfile;

public interface IUserProfileRepository {
    void fetchFriendsList(IUserProfileEventListner eventListner);

    void fetchFriendRequests(IUserProfileEventListner eventListner);
}
