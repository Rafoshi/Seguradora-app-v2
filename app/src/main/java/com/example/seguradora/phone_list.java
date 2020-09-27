package com.example.seguradora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class phone_list extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    Button btn1,btn2,btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_list);

        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call("0800 727 7310");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call("0800 294 2488");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call("0800 427 8810");
            }
        });
    }

    private void call(String numberForCall) {
        if (ContextCompat.checkSelfPermission(phone_list.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(phone_list.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }else {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+numberForCall)));
        }
    }
}