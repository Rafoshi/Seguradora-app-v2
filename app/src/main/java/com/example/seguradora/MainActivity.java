package com.example.seguradora;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    Button call,localize,priceList,order;

    private Sensor mySensor;
    private SensorManager SM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        call = (Button) findViewById(R.id.btnLigar);
        localize = (Button) findViewById(R.id.btnLocaliza);
        priceList = (Button) findViewById(R.id.btnPreco);
        order = (Button) findViewById(R.id.btnPedido);

        localize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,nearest.class);
                startActivity(intent);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, phone_list.class);
                startActivity(intent);
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, pedido_form.class);
                startActivity(intent);
            }
        });

        priceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webSiteIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.euautopecas.pt/catalogo-pecas"));
                startActivity(webSiteIntent);
            }
        });

        SM = (SensorManager) getSystemService(SENSOR_SERVICE);
        mySensor = SM.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        SM.registerListener((SensorEventListener) this,mySensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] >= 5 || event.values[1] >= 5 || event.values[0] <= -5 || event.values[1] <= -5) {

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Não use o aplicativo se estiver dirigindo !!");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Nothing happens if clicks
                        }
                    });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    //Nothing here
    }
}