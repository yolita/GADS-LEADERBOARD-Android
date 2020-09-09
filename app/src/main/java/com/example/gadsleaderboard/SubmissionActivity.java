package com.example.gadsleaderboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gadsleaderboard.DataModel.FormModel;
import com.example.gadsleaderboard.DataModel.LearnerModel;
import com.example.gadsleaderboard.Services.GadsApi;
import com.example.gadsleaderboard.Services.ServiceBuilder;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmissionActivity extends AppCompatActivity {

    private static final String TAG = "Submission";
    private static boolean flag =false;
    private String mFirstname;
    private String mLastname;
    private String mEmail;
    private String mGitlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);
        Button submit = findViewById(R.id.buttonsubmit);


        //Set up appbar
        Toolbar subToolbar = (Toolbar) findViewById(R.id.submission_toolbar);
        setSupportActionBar(subToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Get form values
        final TextView firstnameView = findViewById(R.id.text_firstname);
        final TextView lastnameView = findViewById(R.id.text_lastname);
        final TextView emailView = findViewById(R.id.text_email);
        final TextView gitlinkView = findViewById(R.id.text_gitlink);


        //Get UI view elements
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                mFirstname = firstnameView.getText().toString();
                mLastname = lastnameView.getText().toString();
                mEmail = emailView.getText().toString();
                mGitlink = gitlinkView.getText().toString();


                if (mFirstname == null) {
                    firstnameView.setError("Enter Firstname");
                }
                else if (mLastname == null) {
                    lastnameView.setError("Enter Lastname");
                }
                else if (mGitlink == null) {
                    gitlinkView.setError("Enter valid link");
                }
                else if (mEmail == null || !isValidEmail(mEmail)) {
                    emailView.setError("Enter valid email");
                }
                else{
                    submissionConfirm();
                }
            }

        });
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public void submissionSuccessful(){
        LayoutInflater inflater = this.getLayoutInflater();

        AlertDialog.Builder successfulAlertDialog =new AlertDialog.Builder(this);
        successfulAlertDialog.setView(inflater.inflate(R.layout.success_alert,null));

        AlertDialog success= successfulAlertDialog.create();
        success.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        success.show();
    }

    public void submissionUnsuccessful(){
        LayoutInflater inflater = this.getLayoutInflater();

        AlertDialog.Builder unsuccessfulAlertDialog =new AlertDialog.Builder(this);
        unsuccessfulAlertDialog.setView(inflater.inflate(R.layout.unsuccessful_alert,null));

        AlertDialog unsuccessful= unsuccessfulAlertDialog.create();
        unsuccessful.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        unsuccessful.show();
    }

    public void submissionConfirm(){
        LayoutInflater inflater = SubmissionActivity.this.getLayoutInflater();

        SpannableStringBuilder txt_style = new SpannableStringBuilder("Are you sure ?");
        txt_style.setSpan(new RelativeSizeSpan(1.7f), 13,14 , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        View inflated = inflater.inflate(R.layout.failure_alert, null);
        //Get layout views
        TextView txt_msg = inflated.findViewById(R.id.dialog_warning);

        txt_msg.setText(txt_style);


        //Dialog builder
        AlertDialog.Builder confirmAlertDialog =new AlertDialog.Builder(SubmissionActivity.this);
        confirmAlertDialog.setView(inflated);
        final AlertDialog confirm= confirmAlertDialog.create();
        confirm.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        ImageButton btn_close =  inflated.findViewById(R.id.closeBtn);
        Button btn_confirm =  inflated.findViewById(R.id.confirmBtn);

        //ImageButton btn_close =(ImageButton) success.findViewById(R.id.closeBtn);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.i("BTN_MSG","Button clicked");
                confirm.cancel();

            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = true;
                Log.d(TAG, "btn_confirm");
                confirm.cancel();
                Save();
            }
        });

        confirm.show();
    }

    public void Save(){
        GadsApi learnerHrsService = ServiceBuilder.buildService(GadsApi.class);
        Call<Void> response = learnerHrsService.save(mFirstname, mLastname, mEmail, mGitlink);
        response.enqueue(new Callback<Void>() {


            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Post successful");
                    submissionSuccessful();
                }
                if(!response.isSuccessful()){
                    Log.i(TAG, "Post Not Successful");
                    submissionUnsuccessful();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i(TAG, "Post Failed");
                submissionConfirm();
            }

        });
    }
}