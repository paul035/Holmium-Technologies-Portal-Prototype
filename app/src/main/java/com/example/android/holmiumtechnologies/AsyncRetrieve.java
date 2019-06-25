package com.example.android.holmiumtechnologies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.android.holmiumtechnologies.MainActivity.CONNECTION_TIMEOUT;
import static com.example.android.holmiumtechnologies.MainActivity.READ_TIMEOUT;


public class AsyncRetrieve extends AsyncTask<String, String, String>{

    private Context context;
    private String plantNumber;

    public AsyncRetrieve(String plantNumber, Context context){
        this.plantNumber = plantNumber;
        this.context = context;
    }

    //ProgressDialog pdLoading = new ProgressDialog();
    HttpURLConnection conn;
    URL url = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //this method will be running on UI thread
       /* pdLoading.setMessage("\tPlant details are loading...");
        pdLoading.setCancelable(false);
        pdLoading.show();*/

    }

    @Override
    protected String doInBackground(String... params) {
        try {
            // Enter URL address where your php file resides
            url = new URL("http://10.0.2.2/webapp/data.plant_no.json.php");

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "exception";
        }
        try {
            // Setup HttpURLConnection class to send and receive data from php and mysql
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("POST");

            // setDoInput and setDoOutput method depict handling of both send and receive
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // Append parameters to URL
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("plant_no", plantNumber);
            String query = builder.build().getEncodedQuery();

            //Log.d("Plant Detail", query);
            // query contains the plantNumber

            // Open connection for sending data
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            conn.connect();

            Log.d("OS", String.valueOf(os));

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return "exception";
        }

        try {

            int response_code = conn.getResponseCode();

            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {

                // Read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                //  Log.d("Data", String.valueOf(result));
                // result contain the plant details each and every datas

                // Pass data to onPostExecute method

                return (result.toString());


            } else {

                return ("unsuccessful");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "exception";
        } finally {
            conn.disconnect();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        //this method will be running on UI thread

        //pdLoading.dismiss();

        if (result != null) {
                    String data1="";
                    String data2="";
                    String data3="";
                    String data4="";
                    String data5="";

            try {
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    //PlantDetail plantDetail = new PlantDetail();
                    data1 = json_data.getString("data1");
                    data2 = json_data.getString("data2");
                    data3 = json_data.getString("data3");
                    data4 = json_data.getString("data4");
                    data5 = json_data.getString("data5");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(context, GraphActivity.class);
            intent.putExtra("data1", data1);
            intent.putExtra("data2", data2);
            intent.putExtra("data3", data3);
            intent.putExtra("data4", data4);
            intent.putExtra("data5", data5);
            context.startActivity(intent);

           // Log.d("Data: ", result);

        }

    }

}
