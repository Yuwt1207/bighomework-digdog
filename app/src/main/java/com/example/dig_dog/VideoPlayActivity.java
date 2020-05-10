package com.example.dig_dog;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoPlayActivity extends AppCompatActivity {

    private TextView descriptiontv;
    private TextView nicknametv;
    private VideoView videoView;
    private TextView likecounttv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        //隐藏ActionBar
        getSupportActionBar().hide();
        //设置页面全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        descriptiontv=findViewById(R.id.descriptiontextview);
        nicknametv=findViewById(R.id.nicknametextview);
        videoView=findViewById(R.id.videoView);
        likecounttv=findViewById(R.id.likecounttextview);

        Bundle bundle=getIntent().getExtras();
        String description=bundle.getString("description");
        String nickname=bundle.getString("nickname");
        int likecount=bundle.getInt("likecount");
        String videourl=bundle.getString("videourl");

        descriptiontv.setText(description);
        nicknametv.setText("@"+nickname);
        likecounttv.setText(""+likecount);

        videoView.setVideoURI(Uri.parse(videourl));

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        videoView.setLayoutParams(params);

        videoView.start();

        //实现双击事件监听
        videoView.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
            @Override
            public void onDoubleClick() {
                Toast.makeText(VideoPlayActivity.this,"已喜欢",Toast.LENGTH_SHORT).show();
//                videoView.pause();
            }
        }));


    }



}
