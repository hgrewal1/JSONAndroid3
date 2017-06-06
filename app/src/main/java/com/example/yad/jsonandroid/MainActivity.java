package com.example.yad.jsonandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void nextpage(View view){
        EditText in1=(EditText) findViewById(R.id.editText) ;
        EditText in2=(EditText) findViewById(R.id.editText2) ;
        Intent MyIntent=new Intent(this,Main2Activity.class);
        MyIntent.putExtra("LON",in2.getText().toString());
        MyIntent.putExtra("LAT",in1.getText().toString());
        
        SharedPreferences sp=getSharedPreferences("Login", 0);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putString("Unm",in2.getText().toString() );
        Ed.putString("Psw",in1.getText().toString());
        Ed.commit();
        startActivity(MyIntent);
    }
    public void next(View view){

        Intent MyIntent=new Intent(this,json.class);

        startActivity(MyIntent);
    }
    public void main3(View view){

        Intent MyIntent=new Intent(this,Main3Activity.class);

        startActivity(MyIntent);
    }




}
