package com.example.attributetothefrontliners;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int THUMNAILS=250;
    public final static String KEY="IMAGE";
    private final static String URL[]={"https://images.newindianexpress.com/uploads/user/imagelibrary/2020/4/23/w1200X800/Sony_adapting_popular.jpg",
            "https://vignette.wikia.nocookie.net/naruto/images/0/09/Naruto_newshot.png/revision/latest/scale-to-width-down/340?cb=20170621101134",
            "https://cdnb.artstation.com/p/assets/images/images/013/364/201/large/ryan-qadri-img-20181011-182405.jpg?1539254081",
            "https://vignette.wikia.nocookie.net/naruto/images/4/4d/Kushina_2.png/revision/latest/scale-to-width-down/340?cb=20150719165408"};
    private ImageView[] imageViews;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    imageViews=new ImageView[]{findViewById(R.id.imageView1),
            findViewById(R.id.imageView2),
            findViewById(R.id.imageView3),
            findViewById(R.id.imageView4)};
        for(count=0;count<imageViews.length;count++)
        {
            LoadImage(URL[count],count);
            imageViews[count].setOnClickListener(this);
            imageViews[count].setTag(count);
        }
    }

    private void LoadImage(String s, final int count) {
        ImageLoader imageLoader=MySingleton.getInstance(getApplicationContext()).getImageLoader();
        imageLoader.get(s, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap image=response.getBitmap();
                Bitmap thumbnail= ThumbnailUtils.extractThumbnail(image,THUMNAILS,THUMNAILS);
                imageViews[count].setImageBitmap(thumbnail);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error",error.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        int pos=Integer.parseInt(view.getTag().toString());
        Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
        intent.putExtra(KEY,URL[pos]);
        startActivity(intent);
    }
}