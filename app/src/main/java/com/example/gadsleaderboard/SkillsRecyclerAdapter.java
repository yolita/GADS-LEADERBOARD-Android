package com.example.gadsleaderboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gadsleaderboard.DataModel.LearnerModel;

import java.util.List;

public class SkillsRecyclerAdapter extends RecyclerView.Adapter<SkillsRecyclerAdapter.SkillsViewHolder> {

    private Context mContext;
    private List<LearnerModel> mSkillIqList;
    private LayoutInflater mLayoutInflater;

    public SkillsRecyclerAdapter(@NonNull Context context, List<LearnerModel> SkillIqList) {
        mLayoutInflater = LayoutInflater.from(context);
        mSkillIqList = SkillIqList;
        mContext = context;
    }

    @NonNull
    @Override
    public SkillsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.skill_item, parent, false);
        return new SkillsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillsViewHolder holder, int position) {
        LearnerModel skillIQ = mSkillIqList.get(position);
        String details = skillIQ.getHours() + " learning hours, " + skillIQ.getCountry();
        String img_url =skillIQ.getBadgeUrl();
        holder.text_skills_name.setText(skillIQ.getName());
        holder.text_skills_details.setText(details);
        Glide.with(mContext)

                .load(img_url)
                .into(holder.imgView);
        holder.mCurrentPosition = position;
    }


    @Override
    public int getItemCount() {
        return mSkillIqList.size();
    }

    public class SkillsViewHolder extends RecyclerView.ViewHolder{
        public TextView text_skills_name;
        public TextView text_skills_details;
        public ImageView imgView;
        public int mCurrentPosition;

        public SkillsViewHolder(@NonNull View itemView) {
            super(itemView);
            text_skills_name = itemView.findViewById(R.id.text_skilled_learner_name);
            text_skills_details = itemView.findViewById(R.id.text_skilled_learner_hrs);
            imgView =itemView.findViewById(R.id.imgview_icon);
        }
    }
}
