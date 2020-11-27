package com.codepath.habitwise.features.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.habitwise.R;
import com.codepath.habitwise.models.Habit;

import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.ViewHolder> {
    private Context context;
    private List<Habit> habits;

    public HabitAdapter(Context context , List<Habit> habits) {
        this.context= context;
        this.habits= habits;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_habit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Habit habit = habits.get(position);
        holder.bind(habit);


    }

    @Override
    public int getItemCount() {
        return habits.size();
    }
    public void clear() {
        habits.clear();
        notifyDataSetChanged();
    }


    public void addAll(List<Habit> list) {
        habits.addAll(list);
        notifyDataSetChanged();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
//        private TextView tvInfo;
        private Switch toggleSwitch;


        public ViewHolder( @NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
//            tvInfo = itemView.findViewById(R.id.tvInfo);
            toggleSwitch = itemView.findViewById(R.id.toggleSwitch);
        }

        public void bind(Habit habit) {
            tvTitle.setText(habit.getTitle());
//            tvInfo.setText(habit.getInfo());


        }
    }
}
