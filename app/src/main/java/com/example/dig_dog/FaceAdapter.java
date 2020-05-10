package com.example.dig_dog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;

import java.util.List;



public class FaceAdapter extends RecyclerView.Adapter<FaceViewHolder>
{
    private List<Video> VideoList;//为了方便跳转页面时传递参数，我这里改成了Videolist
    Context context;

    public  FaceAdapter(List<Video> VideoList,Context context)
    {
        this.VideoList=VideoList;
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
        final Video video = VideoList.get(i);
        viewHolder.faceName.setText(video.getNickname());
        Glide.with(context).load(video.getAvatar()).into(viewHolder.facePic);

        viewHolder.facePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,VideoPlayActivity.class);
                intent.putExtra("videourl",video.getFeedurl());
                intent.putExtra("likecount",video.getLikecount());
                intent.putExtra("description",video.getDescription());
                intent.putExtra("id",video.getId());
                intent.putExtra("nickname",video.getNickname());
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return VideoList.size();
    }



}
