package com.example.covid19_safetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class StateCasesActivity extends AppCompatActivity {

    ImageView btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_cases);

        btnReturn=findViewById(R.id.imgStateCasesReturn);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StateCasesActivity.this.finish();
            }
        });
    }
}
