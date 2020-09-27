package com.example.seguradora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class pedido_final extends AppCompatActivity {
    TextView txtName,txtCel,txtPiece,txtvehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_final);

        Bundle extras = getIntent().getExtras();
        txtName = (TextView) findViewById(R.id.txtNome);
        txtCel = (TextView) findViewById(R.id.txtCel);
        txtPiece = (TextView) findViewById(R.id.txtPeca);
        txtvehicle = (TextView) findViewById(R.id.txtVeiculo);

        if (extras != null) {
            String name = extras.getString(pedido_form.EXTRA_MESSAGE_NAME);
            String cell = extras.getString(pedido_form.EXTRA_MESSAGE_PHONE);
            String piece = extras.getString(pedido_form.EXTRA_MESSAGE_PIECE);
            String vehicle = extras.getString(pedido_form.EXTRA_MESSAGE_VEHICLE);

            txtName.setText(name);
            txtCel.setText(cell);
            txtPiece.setText(piece);
            txtvehicle.setText(vehicle);

        }
    }
}