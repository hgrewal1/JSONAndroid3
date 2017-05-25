package com.example.yad.jsonandroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Main3Activity extends AppCompatActivity {
    TextView out1, out2, out3, out4,out5,out6,out7,out8;
    ListView l;String openhrs ="";
    Button b;
    ArrayList<String> dataList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        out1 = (TextView) findViewById(R.id.textView9);
        out2 = (TextView) findViewById(R.id.textView10);
        out3 = (TextView) findViewById(R.id.textView11);
        out4 = (TextView) findViewById(R.id.textView12);
        out5 = (TextView) findViewById(R.id.textView13);
        out6 = (TextView) findViewById(R.id.textView14);
        out7 = (TextView) findViewById(R.id.textView15);
        out8 = (TextView) findViewById(R.id.textView16);
        b=(Button) findViewById(R.id.button3);
        l = (ListView) findViewById(R.id.textView1);
        new MyTask().execute();
    }
    public void home(View view){

        Intent MyIntent=new Intent(this,MainActivity.class);

        startActivity(MyIntent);
    }
    private class MyTask extends AsyncTask<Void, Void, Void> {
        String o1, o2, o3, o4,o5,o6,o7,o8;

        @Override
        protected Void doInBackground(Void... params) {
            URL url = null;


            try {
                url = new URL("http://144.217.163.57:8080/cegepgim/mobile/test/findStudent&z12345");

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
                //print result

                System.out.println(response.toString());
                JSONObject obj = new JSONObject(response.toString());
                o1 = obj.getString("active");
                if(o1.equals("true")) {
                    JSONArray ary= new JSONArray();
                    ary=obj.getJSONArray("results");
                    for (Integer i = 0; i<ary.length(); i++) {
                        JSONObject obj1=ary.getJSONObject(i);
                        o2=obj1.getString("course_title");
                        o4=obj1.getString("course_id");

                        dataList.add(o2);
                        dataList.add(o4);


                    }


                }
                else{




                    o2 = obj.getString("Message");
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

            out2.setText(dataList.get(0));
            out3.setText(dataList.get(2));
            out4.setText(dataList.get(4));
            out5.setText(dataList.get(6));
            out6.setText(dataList.get(8));

            super.onPostExecute(result);



        }


    }


}

