package com.codepath.habitwise.features.analysis;

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
import com.codepath.habitwise.models.Analysis;
import com.codepath.habitwise.objectKeys.ObjParseUser;
import com.parse.GetFileCallback;
import com.parse.ParseException;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalAnalysisAdapter extends RecyclerView.Adapter<PersonalAnalysisAdapter.ViewHolder>{
    private Context context;
    private List<Analysis> analysisList;
    public static final String TAG = "AnalysisAdapterPersonal";
    public PersonalAnalysisAdapter(Context context , List<Analysis> analysisList) {
        this.context= context;
        this.analysisList= analysisList;
    }

    @NonNull
    @Override
    public PersonalAnalysisAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_personal_analysis, parent, false);
        return new PersonalAnalysisAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Analysis analysis = analysisList.get(position);
        holder.bind(analysis);
    }

    @Override
    public int getItemCount() {
        return analysisList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvSubtitle;
        private TextView tvStreakScore;
        private CircleImageView userImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitlePersonal);
            tvSubtitle = itemView.findViewById(R.id.tvSubTitlePersonal);
            tvStreakScore = itemView.findViewById(R.id.tvStreakScorePersonal);
            userImage = itemView.findViewById(R.id.ivUserPersonal);
        }

        public void bind(Analysis analysis) {
            tvTitle.setText(analysis.getHabit().getTitle());
            tvSubtitle.setText(analysis.getSubtitle());
            tvStreakScore.setText(analysis.getCurrentStreak() + "");

            analysis.getUser1().getParseFile(ObjParseUser.KEY_DISPLAY_PIC).getFileInBackground(new GetFileCallback() {
                @Override
                public void done(File file, ParseException e) {
                    if (e != null) {
                        Log.e(TAG, "Error while loading profile pic: " + e.getMessage());
                        return;
                    }
                    String filePath = file.getPath();
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    userImage.setImageBitmap(bitmap);
                }
            });
        }
    }
}
