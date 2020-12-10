package com.example.phototomap;

import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

public class GetAddressAsyncTask extends AsyncTask <String,Void,String>{
    @Override
    protected String doInBackground(String... strings) {
//        try {
//            URL url=new URL(strings[0]);
//            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
//            conn.setDoInput(true);
//            conn.disconnect();
//            InputStream inputStream=conn.getInputStream();
////            String address= URLDecoder.decode(inputStream);
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(
//                            inputStream));
//
//            StringBuilder response = new StringBuilder();
//            String inputLine;
//
//            while ((inputLine = in.readLine()) != null)
//                response.append(inputLine);
//
//            in.close();
//            System.out.println(response.toString());
//            return response.toString();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        RequestQueue mQueue;
//        String url="https://maps.googleapis.com/maps/api/geocode/json?latlng=36.2048,138.2529&sensor=true&key=AIzaSyBnYlCIq0OhiwHArdaQGQ5vhdSSV-rSwRY";
//        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println("FAIL");
//            }
//        });
//        mQueue.add(request);

         return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    public GetAddressAsyncTask() {
    }
}
