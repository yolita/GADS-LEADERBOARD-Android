package com.example.gadsleaderboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private LearnerFragment mLearnerFragment;
    private SkillsFragment mSkillsFragment;

    private static final int LEARNER_FRAGMENT=0;
    private static final int SKILLS_FRAGMENT=1;

    private ViewPager mViewPager;
    private MainPagerAdapter mSectionsPagerAdapter;
    private TabLayout mTabLayout;
    private Button mSubmit_btn;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "OnCreate Method");
        setContentView(R.layout.activity_main);
        mViewPager=(ViewPager) findViewById(R.id.view_pager);
        setupViewPager();

        mContext=this;
        mSubmit_btn = findViewById(R.id.btn_submit);
        mSubmit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SubmissionActivity.class);
                startActivity(intent);
            }
        });
    }



    private void setupViewPager(){
        mSectionsPagerAdapter = new MainPagerAdapter(this,getSupportFragmentManager());
        mLearnerFragment = new LearnerFragment();
        mSkillsFragment = new SkillsFragment();
        mSectionsPagerAdapter.addFragment(mLearnerFragment);
        mSectionsPagerAdapter.addFragment(mSkillsFragment);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.getTabAt(LEARNER_FRAGMENT).setText("Learning Leaders");
        mTabLayout.getTabAt(SKILLS_FRAGMENT).setText("Skill IQ Leaders");

    }
}