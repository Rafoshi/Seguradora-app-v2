package com.example.seguradora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
                salvarArquivo("0800 727 7310");
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call("0800 294 2488");
                salvarArquivo("0800 294 2488");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call("0800 427 8810");
                salvarArquivo("0800 427 8810");
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
        }
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

    private void salvarArquivo(String conteudoArquivo){

        File folder = new File(Environment.getExternalStorageDirectory() + "Pasta_Note/");
        if(folder.exists()){
            folder.mkdir();
        }
        String nomearquivo = "numero.txt";
        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Pasta_Note/" + nomearquivo);
        try {
            FileOutputStream salvar = new FileOutputStream(arquivo);
            salvar.close();
            Toast.makeText(this, "Arquivo Salvo", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(this, "Arquivo não Encontrado", Toast.LENGTH_SHORT).show();
        } catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1000:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"Permitido",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this,"Não foi permitida",Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }
}