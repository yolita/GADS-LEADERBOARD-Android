package com.example.gadsleaderboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gadsleaderboard.DataModel.LearnerModel;
import com.example.gadsleaderboard.Services.GadsApi;
import com.example.gadsleaderboard.Services.ServiceBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkillsFragment extends Fragment {
    private static final String TAG = "Skill Fragment";
    List<LearnerModel> learnerItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v(TAG, "OnCreateView Method");

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_skills, container, false);

        //Initialize RecyclerView
        final RecyclerView recyclerLearner = view.findViewById(R.id.skillsListItems);
        final LinearLayoutManager learnersLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerLearner.setLayoutManager(learnersLayoutManager);


        //Call data from api
        GadsApi learnerHrsService = ServiceBuilder.buildService(GadsApi.class);
        Call<List<LearnerModel>> request = learnerHrsService.getSkillIQ();

        request.enqueue(new Callback<List<LearnerModel>>() {
            @Override
            public void onResponse(Call<List<LearnerModel>> call, Response<List<LearnerModel>> response) {
//                learnerItem = (List<LearnerModel>) response.body();
                recyclerLearner.setAdapter(new SkillsRecyclerAdapter(view.getContext(),response.body()));

            }

            @Override
            public void onFailure(Call<List<LearnerModel>> call, Throwable t) {
                Toast.makeText(view.getContext(), "Failed to retrieve item.", Toast.LENGTH_SHORT).show();;
            }
        });

        return view;
    }
}
