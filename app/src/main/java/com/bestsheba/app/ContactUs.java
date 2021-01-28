package com.bestsheba.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);


        findViewById(R.id.btn_back).setOnClickListener(v->{
            Intent c = new Intent(this, MainActivity.class);
            startActivity(c);
            finish();
        });
    }
}