package com.example.seguradora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class pedido_final extends AppCompatActivity {
    TextView txtName,txtCel,txtPiece,txtvehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_final);

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);

        txtName.setText(preferences.getString("nameCli","Não informado"));
        txtCel.setText(preferences.getString("phoneCli","Não informado"));
        txtPiece.setText(preferences.getString("pieceCli","Não informado"));
        txtvehicle.setText(preferences.getString("vehicleCli","Não informado"));
    }
}