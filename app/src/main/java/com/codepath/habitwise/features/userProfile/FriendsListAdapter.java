package com.codepath.habitwise.features.userProfile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.habitwise.R;
import com.codepath.habitwise.objectKeys.ObjParseUser;
import com.parse.GetFileCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ViewHolder>{
    Context context;
    List<ParseUser> friends;
    private static final String TAG = "FRIENDS_LIST_ADAPTER";

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
            ivUserProfileImage = itemView.findViewById(R.id.ivUserProfileImage1);
        }

        public void bind(ParseUser friend) {
            tvFriendName.setText(friend.get(ObjParseUser.KEY_FIRST_NAME) + " " + friend.get(ObjParseUser.KEY_LAST_NAME));
            friend.getParseFile(ObjParseUser.KEY_DISPLAY_PIC).getFileInBackground(new GetFileCallback() {
                @Override
                public void done(File file, ParseException e) {
                    if (e != null) {
                        Log.e(TAG, "Error while loading profile pic: " + e.getMessage());
                        return;
                    }
                    String filePath = file.getPath();
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    ivUserProfileImage.setImageBitmap(bitmap);
                }
            });
        }
    }
}
