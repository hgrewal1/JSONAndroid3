package com.example.yad.jsonandroid;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class Main4Activity extends AppCompatActivity {
    TextView out1, out2, out3, out4,out5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        out1 = (TextView) findViewById(R.id.textView1);
        out2 = (TextView) findViewById(R.id.textView2);
        out3 = (TextView) findViewById(R.id.textView3);
        out4 = (TextView) findViewById(R.id.textView4);
        out5 = (TextView) findViewById(R.id.textView5);

        new MyTask().execute();
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        String o1, o2, o3, o4,o5,o6,o7,o8;

        @Override
        protected Void doInBackground(Void... params) {
            URL url = null;
            SharedPreferences sp1=getSharedPreferences("Login",0);

            String r=sp1.getString("Unm", null);



            try {
                url = new URL("http://144.217.163.57:8080/cegepgim/mobile/tutorials/viewtutorial&"+r);

                HttpURLConnection client = null;
                client = (HttpURLConnection) url.openConnection();
                client.setRequestMethod("GET");
                int responseCode = client.getResponseCode();
                System.out.println("\n Sending 'GET' request to url:" + url);
                System.out.println("\n Response code:" + responseCode);
                InputStreamReader myInput = new InputStreamReader(client.getInputStream());
                BufferedReader in = new BufferedReader(myInput);
                String inputline;
                StringBuffer response = new StringBuffer();

                while ((inputline = in.readLine()) != null) {
                    response.append(inputline);
                }
                in.close();
                //print rsult
                System.out.println(response.toString());
                JSONObject obj = new JSONObject(response.toString());
                o1 = obj.getString("Status");
                if(o1.equals("ok")){
                    o2 = obj.getString("TutorialName");
                    o3 = obj.getString("AddedDate");
                    o4 = obj.getString("TutorialId");
                    o5 = obj.getString("UserId");}



                else{

                    o2 = obj.getString("TutorialName");


                    o3 = obj.getString("Message");
                    o4 = obj.getString("TimeStamp");



                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            out1.setText(o1);
            out2.setText(o2);
            out3.setText(o3);
            out4.setText(o4);
            out5.setText(o5);


            super.onPostExecute(result);



        }


    }


}

