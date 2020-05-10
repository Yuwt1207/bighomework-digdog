package com.example.dig_dog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;

import java.util.List;



public class FaceAdapter extends RecyclerView.Adapter<FaceViewHolder>
{
    private List<Face> faceList;
    Context context;

    public  FaceAdapter(List<Face> faceList,Context context)
    {
        this.faceList=faceList;
        this.context=context;
    }

    @NonNull
    @Override
    public FaceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.face_item,viewGroup,false);
        return new FaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaceViewHolder viewHolder, int i) {
        final Face face = faceList.get(i);
        viewHolder.faceName.setText(face.getName());
        Glide.with(context).load(face.getURI()).into(viewHolder.facePic);
    }


    @Override
    public int getItemCount() {
        return faceList.size();
    }



}
