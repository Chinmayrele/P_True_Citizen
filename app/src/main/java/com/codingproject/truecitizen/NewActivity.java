package com.codingproject.truecitizen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codingproject.truecitizen.databinding.ActivityNewBinding;

public class NewActivity extends AppCompatActivity {
    private ActivityNewBinding bindingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_new);
        bindingData = DataBindingUtil.setContentView(this, R.layout.activity_new);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            bindingData.setTextNew.setText(bundle.getString("title"));
            Log.d("data", bundle.getString("name"));
            Log.d("data", " "+bundle.getInt("age"));
        }

        bindingData.setTextNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.putExtra("msg_back", "I am Back Buddies");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

//        if(getIntent().getStringExtra("title") != null) {
//            bindingData.setTextNew.setText(getIntent().getStringExtra("title"));
//            Log.d("data", getIntent().getStringExtra("name"));
//        }
    }
}