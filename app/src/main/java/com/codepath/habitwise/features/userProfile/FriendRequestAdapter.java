package com.codepath.habitwise.features.userProfile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codepath.habitwise.R;
import com.codepath.habitwise.models.Friends;
import com.codepath.habitwise.objectKeys.ObjParseUser;
import com.parse.GetFileCallback;
import com.parse.ParseException;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.ViewHolder> {
    Context context;
    List<Friends> friends;
    private static final String TAG = "FRIEND_REQUEST_ADAPTER";

    public FriendRequestAdapter(Context context, List<Friends> friends){
        this.context = context;
        this.friends = friends;
    }

    @NonNull
    @Override
    public FriendRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View friendView = LayoutInflater.from(context).inflate(R.layout.item_request, parent, false);
        return new ViewHolder(friendView);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestAdapter.ViewHolder holder, int position) {
        Friends friend = friends.get(position);
        holder.bind(friend);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvFriendName;
        CircleImageView ivUserProfileImage;
        Button btnConfirm;
        Button btnDecline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFriendName = itemView.findViewById(R.id.tvFriendName);
            ivUserProfileImage = itemView.findViewById(R.id.ivUserProfileImage);
            btnConfirm = itemView.findViewById(R.id.btnConfirm);
            btnDecline = itemView.findViewById(R.id.btnDecline);
        }

        public void bind(final Friends friend) {
            tvFriendName.setText(friend.getFromUser().get(ObjParseUser.KEY_FIRST_NAME) + " " + friend.getFromUser().get(ObjParseUser.KEY_LAST_NAME));
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    friend.setStatus("Accepted");
                    try {
                        friend.save();
                    } catch (ParseException e) {
                        Log.e(TAG, "Error while updating", e);
                    }
                }
            });
            btnDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    friend.setStatus("Declined");
                    try {
                        friend.save();
                    } catch (ParseException e) {
                        Log.e(TAG, "Error while updating", e);
                    }
                }
            });

            friend.getFromUser().getParseFile(ObjParseUser.KEY_DISPLAY_PIC).getFileInBackground(new GetFileCallback() {
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
