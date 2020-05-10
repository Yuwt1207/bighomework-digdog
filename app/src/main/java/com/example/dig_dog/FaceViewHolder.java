package com.example.dig_dog;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FaceViewHolder extends  RecyclerView.ViewHolder
{

    TextView faceName;
    ImageView facePic;

    public FaceViewHolder (View view) {
        super(view);
        faceName = view.findViewById(R.id.face_name);
        facePic = view.findViewById(R.id.face_pic);
    }


}
