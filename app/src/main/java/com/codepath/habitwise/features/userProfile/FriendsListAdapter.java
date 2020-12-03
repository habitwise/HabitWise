package com.codepath.habitwise.features.userProfile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.habitwise.R;
import com.codepath.habitwise.objectKeys.ObjParseUser;
import com.parse.ParseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ViewHolder>{
    Context context;
    List<ParseUser> friends;

    public FriendsListAdapter(Context context, List<ParseUser> friends){
        this.context = context;
        this.friends = friends;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View friendView = LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false);
        return new ViewHolder(friendView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ParseUser friend = friends.get(position);
        holder.bind(friend);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvFriendName;
        CircleImageView ivUserProfileImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFriendName = itemView.findViewById(R.id.tvFriendName);
            ivUserProfileImage = itemView.findViewById(R.id.ivUserProfileImage);
        }

        public void bind(ParseUser friend) {
            tvFriendName.setText(friend.get(ObjParseUser.KEY_FIRST_NAME) + " " + friend.get(ObjParseUser.KEY_LAST_NAME));
        }
    }
}
