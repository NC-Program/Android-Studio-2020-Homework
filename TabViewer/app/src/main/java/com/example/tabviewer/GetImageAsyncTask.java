package com.example.tabviewer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.apache.http.params.HttpConnectionParams;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView imgView;

    public GetImageAsyncTask(ImageView imgView) {
        this.imgView = imgView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.disconnect();
            InputStream input=conn.getInputStream();
            Bitmap bitmap= BitmapFactory.decodeStream(input);
            return bitmap;
        } catch (IOException io) {
            io.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imgView.setImageBitmap(bitmap);
    }
}
