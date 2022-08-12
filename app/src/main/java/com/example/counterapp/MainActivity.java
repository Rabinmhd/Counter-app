package com.example.counterapp;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private ImageButton btPlus;
    private TextView CountsTx;
    private SwitchCompat darkSwitch;
    int count = 0;
    public boolean testTrue=false;
    boolean getBoolFromSave=false;

    String saveString;

    SharedPreferences saveText;
    SharedPreferences boolSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveText = getSharedPreferences("savedTextKey", MODE_PRIVATE);
        boolSave = getSharedPreferences("nightSave1", MODE_PRIVATE);
        darkSwitch = findViewById(R.id.DarkModSwitch);
        btPlus = findViewById(R.id.btPlus);
        CountsTx = findViewById(R.id.count);


        MobileAds.initialize(this);
        AdView adView= (AdView)findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        getBoolFromSave=getIntent().getBooleanExtra("boolFromSaveAct",false);
        if (getBoolFromSave){
            count=getIntent().getIntExtra("countFromSaveAct",0);
            Log.d("message","count received");
        }else{
            count = saveText.getInt("saveCount", 0);
        }
        CountsTx.setText(count + "");


        boolean myBool = boolSave.getBoolean("nightSave", false);

        if (myBool) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            darkSwitch.setChecked(true);

        }



        btPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = count + 1;
                CountsTx.setText(count + "");

                SaveData();

            }

        });
        darkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    darkSwitch.setChecked(true);
                    SharedPreferences.Editor prefEditor = boolSave.edit();
                    prefEditor.putBoolean("nightSave", true);
                    prefEditor.apply();


                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    darkSwitch.setChecked(false);
                    SharedPreferences.Editor prefEditor = boolSave.edit();
                    prefEditor.putBoolean("nightSave", false);
                    prefEditor.apply();

                }

            }
        });

    }

    public void SaveData() {
        SharedPreferences.Editor prefEditor = saveText.edit();
        prefEditor.putInt("saveCount", count);
        prefEditor.apply();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.customCount:
                setValueMenu();

                break;

            case R.id.resetm:

                reset();
                break;
            case R.id.saveC:
                save();

                break;
            case R.id.loadC:
                Load();

                break;


        }

        return super.onOptionsItemSelected(item);
    }

    private void setValueMenu() {


        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("Enter custom number");
        EditText edtTxt = new EditText(this);
        edtTxt.setInputType(InputType.TYPE_CLASS_NUMBER);

        dlg.setView(edtTxt);

        dlg.setPositiveButton("update", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Editable inpCount = edtTxt.getText();
                CountsTx.setText(inpCount + "");
                count = Integer.parseInt(CountsTx.getText().toString());
                SaveData();
            }
        });
        dlg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dlg.create().show();


    }


    private void reset() {
        if (count == 0) {
            Toast.makeText(this, "Value is zero", Toast.LENGTH_SHORT).show();
        } else {
            CountsTx.setText("0");
            count = 0;
            SaveData();
        }
    }

    void save() {
        testTrue=true;
        Intent transfer = new Intent(this, SaveActivity.class);
        AlertDialog.Builder saveAlert = new AlertDialog.Builder(this);
        saveAlert.setTitle("Enter a name for the count");
        EditText edtTxSave = new EditText(this);
        saveAlert.setView(edtTxSave);


        saveAlert.setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (edtTxSave.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "No names", Toast.LENGTH_SHORT).show();

                } else {
                    transfer.putExtra("countToStore", count);
                    saveString = edtTxSave.getText().toString();
                    transfer.putExtra("nameToStore", saveString);
                    transfer.putExtra("test", true);
                    startActivity(transfer);

                }


            }
        });
        saveAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        saveAlert.create().show();


    }

    void Load() {

        Intent intent=new Intent(this,SaveActivity.class);
        intent.putExtra("test", false);


        startActivity(intent);

    }

}