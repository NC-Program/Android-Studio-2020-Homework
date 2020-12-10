package com.example.imagegallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter implements View.OnClickListener {
    public final static String KEY = "IMAGE";

    private Context context;
    private ArrayList<Image> imageArrayList = new ArrayList<>();
    private View view;
    private LayoutInflater layoutInflater;
    private ArrayList<Boolean> checkedList = new ArrayList<>();
    private final int THUMNAILS = 250;
    private Integer countView = 0;
    private ArrayList<RecyclerImage> recyclerImageArrayList=new ArrayList<>();

    public GridAdapter(Context context, ArrayList<Image> imageArrayList) {
        this.context = context;
        this.imageArrayList = imageArrayList;
        checkedList.clear();
        for (int a = 0; a < this.imageArrayList.size(); a++) {
            checkedList.add(false);
        }
    }

    public void addRecyclerImage(Image image,int i) {
        this.recyclerImageArrayList.add(new RecyclerImage(image,i));
    }

    public void clearRecyclerImage(){
        this.recyclerImageArrayList.clear();
    }
    public void recoverRecyclerImage(){
        for(int a=recyclerImageArrayList.size()-1;a>=0;a--)
        {
            imageArrayList.add(recyclerImageArrayList.get(a).getIndex(),recyclerImageArrayList.get(a).getImageRecycler());
            checkedList.add(false);
        }
    }

    public void setCountView(Integer countView) {
        this.countView = countView;
    }

    public Integer getCountView() {
        return countView;
    }

    public ArrayList<Boolean> getCheckedList() {
        return checkedList;
    }

    public ArrayList<Image> getImageArrayList() {
        return imageArrayList;
    }

    @Override
    public int getCount() {
        return imageArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.single_item, null);
            ImageView imageView_thumbnail = view.findViewById(R.id.imageView_thumbnail);
            CheckBox checkBox_thumbnail = view.findViewById(R.id.checkBox_thumbnail);
            LoadImage(imageArrayList.get(i).getThumbnail(), imageView_thumbnail);
            imageView_thumbnail.setTag(countView.toString());
            checkBox_thumbnail.setTag(countView.toString());
            imageView_thumbnail.setOnClickListener(this);
            checkBox_thumbnail.setOnClickListener(this);
            countView++;
        }
        return view;
    }


    private void LoadImage(String s, final ImageView imageView) {
        ImageLoader imageLoader = MySingleton.getInstance(context.getApplicationContext()).getImageLoader();
        imageLoader.get(s, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap image = response.getBitmap();
                Bitmap thumbnail = ThumbnailUtils.extractThumbnail(image, THUMNAILS, THUMNAILS);
                imageView.setImageBitmap(thumbnail);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.imageView_thumbnail):
                Intent intent = new Intent(context.getApplicationContext(), MainActivity2.class);
                System.out.println("aaaaaaa"+((ImageView) view).getTag().toString());
                intent.putExtra(KEY, imageArrayList.get(Integer.valueOf(((ImageView) view).getTag().toString())).getFullImage());
                context.startActivity(intent);
                break;
            case(R.id.checkBox_thumbnail):
                checkedList.set(Integer.valueOf(((CheckBox)view).getTag().toString()),((CheckBox)view).isChecked());
                break;
        }
    }


}
