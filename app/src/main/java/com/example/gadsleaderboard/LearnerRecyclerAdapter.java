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

public class LearnerRecyclerAdapter extends RecyclerView.Adapter<LearnerRecyclerAdapter.ViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private  List<LearnerModel> mLearnerModelList;
    private Context mContext;


    public LearnerRecyclerAdapter(@NonNull Context context, List<LearnerModel> LearnersList) {
        mLayoutInflater = LayoutInflater.from(context);
        mLearnerModelList = LearnersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.learner_item, parent, false);
        mContext=itemView.getContext();
        return new ViewHolder(itemView);
    }
    private void loadImage(ImageView iv, String  url) {
        Glide.with(iv.getContext()).load(url).thumbnail(0.5f).into(iv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LearnerModel learnerModel = mLearnerModelList.get(position);
        String details = learnerModel.getHours() + " learning hours, " + learnerModel.getCountry();
        String img_url = learnerModel.getBadgeUrl();
        holder.text_name.setText(learnerModel.getName());
        holder.text_details.setText(details);
        loadImage(holder.imgURL,img_url);
//        Glide.with(mContext)
//                .load(img_url)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.imgURL);
        holder.mCurrentPosition = position;
    }

    @Override
    public int getItemCount() {
        return mLearnerModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView text_name;
        public TextView text_details;
        public ImageView imgURL;
        public int mCurrentPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_name = itemView.findViewById(R.id.text_learner_name);
            text_details = itemView.findViewById(R.id.text_learner_hrs);
            imgURL = itemView.findViewById(R.id.imgview_icon);
        }
    }
}
