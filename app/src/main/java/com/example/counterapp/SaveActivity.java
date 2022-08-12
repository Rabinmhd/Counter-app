package com.example.counterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SaveActivity extends AppCompatActivity implements ContactsViewAdapter.OnNoteListener {

    protected int savedCount;
    protected String recIntend;
    RecyclerView recView;



    ArrayList<SaveClass> saveArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        MobileAds.initialize(this);
        AdView adView= (AdView)findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        recView = findViewById(R.id.myLisRecycler);

        savedCount = getIntent().getIntExtra("countToStore", 0);
        recIntend = getIntent().getStringExtra("nameToStore");

        boolean testTrue= getIntent().getBooleanExtra("test",false);
        saveArray = loadData();
        if (saveArray == null)
            saveArray = new ArrayList<>();
        if (testTrue){
           saveArray.add(0,new SaveClass(savedCount, recIntend));
        }




        ContactsViewAdapter adapter = new ContactsViewAdapter(saveArray,this);
        recView.setAdapter(adapter);
        recView.setLayoutManager(new LinearLayoutManager(this));




    }



    private void saveData() {
        SharedPreferences sharedPrefs = getSharedPreferences("savedArray", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(saveArray);
        editor.putString("jsonSave", json);
        editor.apply();
    }

    private  ArrayList<SaveClass> loadData() {
        SharedPreferences sharedPref = getSharedPreferences("savedArray", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("jsonSave", null);
        Type type = new TypeToken<ArrayList<SaveClass>>() {
        }.getType();
       return gson.fromJson(json, type);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData();
    }

    @Override
    public void onNoteClick(SaveClass saveClass) {


        saveData();

        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("boolFromSaveAct", true);
        intent.putExtra("countFromSaveAct",saveClass.countC);

        startActivity(intent);

    }

    @Override
    public void deleteSavedCount(SaveClass saveClass) {
        saveArray.remove(saveClass);
        saveData();
    }
}