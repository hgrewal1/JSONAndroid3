package com.example.yad.jsonandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;




public class json extends AppCompatActivity {

    private String TAG = json.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;


    // URL to get contacts JSON


    ArrayList<HashMap<String, String>> arylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);


        arylist = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new MyTask().execute();


        /**
         * Listview item click listener
         * TrackListActivity will be lauched by passing album id
         * */
        lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, android.view.View view, int arg2,
                                    long arg3) {
                // on selecting a single album
                // TrackListActivity will be launched to show tracks inside the album
                Intent i = new Intent(getApplicationContext(), Searchtutorials.class);

                // send album id to tracklist activity to get list of songs under that album
                String tut_name = ((TextView) view.findViewById(R.id.name)).getText().toString();
                i.putExtra("tname", tut_name);

                startActivity(i);
            }
        });
    }




    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(json.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            URL url = null;


            try {
                url = new URL("http://10.0.2.2:8080/tutorials/cegepgim/tutorials/viewalltutorial");

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



                    JSONObject obj = new JSONObject(response.toString());
String status=obj.getString("Status");
                if (status.equals("ok")) {
                    JSONArray ary = new JSONArray();
                    ary = obj.getJSONArray("Tutorials");
                    for (Integer i = 0; i < ary.length(); i++) {
                        JSONObject obj1 = ary.getJSONObject(i);
                        String o2 = obj1.getString("TutorialName");
                        String o4 = obj1.getString("AddedDate");
                        String o5 = obj1.getString("TutorialId");


                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("TutorialName", o2);
                        contact.put("AddedDate", o4);
                        contact.put("TutorialId", o5);


                        // adding Array values to Array list
                        arylist.add(contact);
                    }
                }
                else {
                    Log.e(TAG, "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't get json from server. Check LogCat for possible errors!",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    json.this, arylist,
                    R.layout.list_item, new String[]{"TutorialName", "AddedDate","TutorialId"}, new int[]{R.id.name,
                    R.id.email,R.id.mobile});

            lv.setAdapter(adapter);
        }


    }
}