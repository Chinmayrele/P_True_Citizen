package com.codingproject.truecitizen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.codingproject.truecitizen.databinding.ActivityMainBinding;
import com.codingproject.truecitizen.models.Question;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 2;
    private final Question[] questionBank = new Question[]{
            // CREATE OR INSTANTIATE NEW QUESTION OBJECTS
            new Question(R.string.question_amendments, false),
            new Question(R.string.question_constitution, true),
            new Question(R.string.question_declaration, true),
            new Question(R.string.question_independence_rights, true),
            new Question(R.string.question_religion, true),
            new Question(R.string.question_government, false),
            new Question(R.string.question_government_feds, false),
            new Question(R.string.question_government_senators, true),
    };
    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
//                        Intent data = result.getData();
                        String data = result.getData().getStringExtra("msg_back");
                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
//                        doSomeOperations();
                    }
                }
            });
    private ActivityMainBinding binding;
    private int currentQueIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        Log.d("Cycle", "OnCreate: ");
        Toast.makeText(this, "onCreate() was Called", Toast.LENGTH_SHORT).show();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.queTextView.setText(questionBank[0].getAnswerResId());
        binding.queTextView.setTextColor(ContextCompat.getColor(this, R.color.black));

        binding.trueButton.setOnClickListener(view -> checkAnswer(true));
        binding.falseButton.setOnClickListener(view -> checkAnswer(false));

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentQueIndex = (currentQueIndex + 1) % questionBank.length;
                updateQue();
            }
        });
        binding.prevButton.setOnClickListener(view -> {
            if (currentQueIndex > 0) {
                currentQueIndex = (currentQueIndex - 1) % questionBank.length;
                updateQue();
            }
        });

        binding.navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                intent.putExtra("title", "Hello There");
                intent.putExtra("name", "Chinmay");
                intent.putExtra("age", 21);
//                startActivity(intent);  // SUCH START-ACTIVITY ONLY TELLS TO START ACTIVITY 7 DOES NOT WAIT FOR THE RESULT
                // FOR ACTIVITY TO KNOW THAT A RESULT CAN COME FROM B->A WE DO,
//                startActivityForResult(intent, REQUEST_CODE);  //THIS IS NOW DEPRECATED SO USE BELOW METHOD
                //DON'T USE startActivityForResult SINCE IT IS DEPRECATED 7 HENCE DON'T USE onActivityResult METHOD
                //INSTEAD USE someActivityResultLauncher & someActivityResultLauncher

                someActivityResultLauncher.launch(intent);
            }
        });
    }

//    // METHOD FOR LISTENING CONTINUOUSLY AND GETTING THE DATA COMING FROM NEXT PAGE
//    // HERE ACTIVITY GOES FROM A->B AND WE WILL LISTEN HERE DATA COMING FROM B->A
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                assert data != null;
//                String value = data.getStringExtra("msg_back");
//                Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    private void checkAnswer(boolean userAnsChoice) {
        boolean isAnswerCorrect = questionBank[currentQueIndex].isAnswerTrue();
        int messageID;

        if (isAnswerCorrect == userAnsChoice) {
            messageID = R.string.correct_answer;
        } else {
            messageID = R.string.wrong_answer;
        }

        Snackbar.make(binding.imageViewAndroid, messageID, Snackbar.LENGTH_SHORT).show();
    }

    private void updateQue() {
        binding.queTextView.setText(questionBank[currentQueIndex].getAnswerResId());
    }


//    @Override
//    protected void onStart() {
//        Log.d("Cycle", "OnStart: ");
//        Toast.makeText(this, "onStart() was Called", Toast.LENGTH_SHORT).show();
//        super.onStart();
//    }
//
//    @Override
//    protected void onPostResume() {
//        Log.d("Cycle", "OnPostResume: ");
//        Toast.makeText(this, "onPostResume() was Called", Toast.LENGTH_SHORT).show();
//        super.onPostResume();
//    }
//
//    @Override
//    protected void onPause() {
//        Log.d("Cycle", "OnPause: ");
//        Toast.makeText(this, "onPause() was Called", Toast.LENGTH_SHORT).show();
//        super.onPause();
//    }
//
//    @Override
//    protected void onRestart() {
//        Log.d("Cycle", "OnRestart: ");
//        Toast.makeText(this, "onRestart() was Called", Toast.LENGTH_SHORT).show();
//        super.onRestart();
//    }
//
//    @Override
//    protected void onStop() {
//        Log.d("Cycle", "OnStop: ");
//        Toast.makeText(this, "onStop() was Called", Toast.LENGTH_SHORT).show();
//        super.onStop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        Log.d("Cycle", "OnDestroy: ");
//        Toast.makeText(this, "onDestroy() was Called", Toast.LENGTH_SHORT).show();
//        super.onDestroy();
//    }
}