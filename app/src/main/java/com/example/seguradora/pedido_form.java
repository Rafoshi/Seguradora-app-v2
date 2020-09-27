package com.example.seguradora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class pedido_form extends AppCompatActivity {
    EditText name, phone, vehicle, piece;
    Button btnEnviar;

    private static final String KEY_NAME = "name_key";
    private static final String KEY_PHONE = "phone_key";
    private static final String KEY_VEHICLE = "vehicle_key";
    private static final String KEY_PIECE = "piece_key";

    public final static String EXTRA_MESSAGE_NAME = "com.example.seguradora.name";
    public final static String EXTRA_MESSAGE_PHONE = "com.example.seguradora.phone";
    public final static String EXTRA_MESSAGE_VEHICLE = "com.example.seguradora.vehicle";
    public final static String EXTRA_MESSAGE_PIECE = "com.example.seguradora.piece";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_form);

        name = (EditText) findViewById(R.id.clientName);
        phone = (EditText) findViewById(R.id.clientPhone);
        vehicle = (EditText) findViewById(R.id.clientVe);
        piece = (EditText) findViewById(R.id.clientPiece);

        btnEnviar = (Button) findViewById(R.id.btnEnviar);

        if (savedInstanceState != null) {
            String savedName = savedInstanceState.getString(KEY_NAME);
            String savedPhone = savedInstanceState.getString(KEY_PHONE);
            String savedVehicle = savedInstanceState.getString(KEY_VEHICLE);
            String savedPiece = savedInstanceState.getString(KEY_PIECE);
        }

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameCli = name.getText().toString();
                String phoneCli = phone.getText().toString();
                String vehicleCli = vehicle.getText().toString();
                String pieceCli = piece.getText().toString();

                if (validatePhone(phoneCli) && isNull(nameCli) && isNull(vehicleCli) && isNull(pieceCli)) {
                    Intent intent = new Intent(pedido_form.this, pedido_final.class);

                    intent.putExtra(EXTRA_MESSAGE_NAME, nameCli);
                    intent.putExtra(EXTRA_MESSAGE_PHONE, phoneCli);
                    intent.putExtra(EXTRA_MESSAGE_VEHICLE, vehicleCli);
                    intent.putExtra(EXTRA_MESSAGE_PIECE, pieceCli);

                    startActivity(intent);
                }
            }
        });
    }

    private boolean validatePhone(String phoneNumber) {
        int phoneLength = phoneNumber.length();
        if (phoneLength != 9) {
            Toast.makeText(getApplicationContext(), "Insira um número válido (Sem DDD)", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isNull(String input) {
        if (input.length() == 0) {
            Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState( Bundle savedInstanceState) {

        savedInstanceState.putString(KEY_NAME, name.getText().toString());
        savedInstanceState.putString(KEY_PHONE, phone.getText().toString());
        savedInstanceState.putString(KEY_PIECE, vehicle.getText().toString());
        savedInstanceState.putString(KEY_VEHICLE, piece.getText().toString());

        super.onSaveInstanceState(savedInstanceState);
    }
}