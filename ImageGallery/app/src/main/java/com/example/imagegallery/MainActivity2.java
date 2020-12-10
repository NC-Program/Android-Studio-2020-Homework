package com.example.imagegallery;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

public class MainActivity2 extends AppCompatActivity {
    private ImageView imageView_fullImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle bundle=getIntent().getExtras();
        String url=bundle.getString(GridAdapter.KEY);


        imageView_fullImage=findViewById(R.id.imageView_fullImage);
        LoadImage(url);

    }

    private void LoadImage(String s) {
        ImageLoader imageLoader=MySingleton.getInstance(getApplicationContext()).getImageLoader();
        imageLoader.get(s, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap image=response.getBitmap();
//                Bitmap thumbnail= ThumbnailUtils.extractThumbnail(image,250,250);
                imageView_fullImage.setImageBitmap(image);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error",error.getMessage());
            }
        });
    }
}