package com.example.yad.jsonandroid;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
public class search extends AppCompatActivity implements OnItemSelectedListener {
    String selected;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);


        spinner = (Spinner) findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("tutorial");
        categories.add("category");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
Intent intent;
        selected=(String)spinner.getSelectedItem();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
public  void onclick(View view){
    EditText edittext=(EditText) findViewById(R.id.editText3) ;


       switch(selected){

        case "tutorial":
            Intent intent = new Intent(search.this, Searchtutorials.class);
            intent.putExtra("search",edittext.getText().toString());

            startActivity(intent);
            break;
           case "category":
              intent = new Intent(search.this, searchcategory.class);
               intent.putExtra("search",edittext.getText().toString());

               startActivity(intent);
               break;
       }

}}
