package com.rba.animewp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rba.animewp.mData.constant;

public class pb_internet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb_internet);
    }
    public void Reconnect(View view) {
        if (constant.isNetworkAvailable(pb_internet.this)) {
            Intent intent = new Intent(pb_internet.this, page_1.class);
            startActivity(intent);
            finish();
        } else {
            // La connexion Internet n'est pas disponible

        }

    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {


                        pb_internet.super.onBackPressed();
                        finish();
                    }
                }).create().show();
    }

}