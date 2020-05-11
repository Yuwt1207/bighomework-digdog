package com.example.dig_dog;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

public class VideoPlayActivity extends AppCompatActivity {

    private TextView descriptiontv;
    private TextView nicknametv;
    private VideoView videoView;
    private TextView likecounttv;
    private ImageView likeiv;
    private ImageView playbuttoniv;

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
        likeiv=findViewById(R.id.likeimageview);
        playbuttoniv=findViewById(R.id.playbuttoniv);

        Bundle bundle=getIntent().getExtras();
        String description=bundle.getString("description");
        String nickname=bundle.getString("nickname");
        int likecount=bundle.getInt("likecount");
        final String videourl=bundle.getString("videourl");

        descriptiontv.setText(description);
        nicknametv.setText("@"+nickname);
        likecounttv.setText(""+likecount);
        Glide.with(this).load(R.drawable.white_heart).into(likeiv);
        Glide.with(this).load(R.drawable.playbutton).into(playbuttoniv);

        videoView.setVideoURI(Uri.parse(videourl));

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        videoView.setLayoutParams(params);

        videoView.start();

        //当视频播放结束，显示播放键
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Glide.with(VideoPlayActivity.this).load(R.drawable.playbutton).into(playbuttoniv);
                playbuttoniv.setVisibility(View.VISIBLE);
                playbuttoniv.setAlpha(1f);
            }
        });

        //播放键的点击事件
        playbuttoniv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.start();
                Glide.with(VideoPlayActivity.this).load(R.drawable.pausebutton).into(playbuttoniv);

                //淡出动画
                AlphaAnimation alphaAnimation=new AlphaAnimation(1.0f,0f);
                alphaAnimation.setDuration(1000);
                playbuttoniv.startAnimation(alphaAnimation);
                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //动画结束时将VISIBILITY设为GONE
                        playbuttoniv.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        //实现双击事件监听
        videoView.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
            @Override
            public void onDoubleClick() {
                Toast.makeText(VideoPlayActivity.this,"已喜欢",Toast.LENGTH_SHORT).show();
                Glide.with(VideoPlayActivity.this).load(R.drawable.red_heart).into(likeiv);

//                videoView.pause();
            }

            @Override
            public void onOnceClick() {
                if(videoView.isPlaying())
                {
                    videoView.pause();
                    Glide.with(VideoPlayActivity.this).load(R.drawable.playbutton).into(playbuttoniv);
                    playbuttoniv.setVisibility(View.VISIBLE);
                    playbuttoniv.setAlpha(1.0f);
                }

//                videoView.pause();
            }
        }));


    }



}
