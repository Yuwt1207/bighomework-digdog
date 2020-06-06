package com.example.dig_dog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.dig_dog.db.DbContract;
import com.example.dig_dog.db.Dbhelper;

public class VideoPlayActivity extends AppCompatActivity {

    private TextView descriptiontv;
    private TextView nicknametv;
    private VideoView videoView;
    private TextView likecounttv;
    private ImageView likeiv;
    private ImageView movelikeiv;
    private ImageView playbuttoniv;
    private ImageView starbuttoniv;
    private int isliked;
    private int isstared;
    private Dbhelper dbhelper;
    private  String description;
    private  String nickname;
    private  String videourl;
    private  int likecount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        dbhelper=new Dbhelper(this);

        Bundle bundle=getIntent().getExtras();
        description=bundle.getString("description");
        nickname=bundle.getString("nickname");
        likecount=bundle.getInt("likecount");
        videourl=bundle.getString("videourl");

        initUI();
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

        //喜欢键点击事件
        likeiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isliked==0)
                {
                    Toast.makeText(VideoPlayActivity.this,"已喜欢",Toast.LENGTH_SHORT).show();
                    Glide.with(VideoPlayActivity.this).load(R.drawable.red_heart).into(likeiv);
                    Glide.with(VideoPlayActivity.this).load(R.drawable.red_heart).into(movelikeiv);
                    isliked=1;
                    playlikeanimation();
                    ChangeLikeInDB(videourl,true);
                }
                else
                {
                    Toast.makeText(VideoPlayActivity.this,"已取消喜欢",Toast.LENGTH_SHORT).show();
                    Glide.with(VideoPlayActivity.this).load(R.drawable.white_heart).into(likeiv);
//                    Glide.with(VideoPlayActivity.this).load(R.drawable.white_heart).into(movelikeiv);
                    isliked=0;
                    ChangeLikeInDB(videourl,false);
                }

            }
        });

        //一键二连
        likeiv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(VideoPlayActivity.this,"已喜欢收藏",Toast.LENGTH_SHORT).show();
                Glide.with(VideoPlayActivity.this).load(R.drawable.red_heart).into(likeiv);
                Glide.with(VideoPlayActivity.this).load(R.drawable.red_heart).into(movelikeiv);
                isliked=1;
                playlikeanimation();
                Glide.with(VideoPlayActivity.this).load(R.drawable.red_star).into(starbuttoniv);
                isstared=1;
                ChangeLikeInDB(videourl,true);
                ChangeStarInDB(videourl,true);
                return true;//这里返回true不触发点击事件
            }
        });

        // 收藏单机时间
        starbuttoniv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isstared==0)
                {
                    Toast.makeText(VideoPlayActivity.this,"已收藏",Toast.LENGTH_SHORT).show();
                    Glide.with(VideoPlayActivity.this).load(R.drawable.red_star).into(starbuttoniv);
                    isstared=1;
                    ChangeStarInDB(videourl,true);
                }
                else
                {
                    Toast.makeText(VideoPlayActivity.this,"已取消收藏",Toast.LENGTH_SHORT).show();
                    Glide.with(VideoPlayActivity.this).load(R.drawable.white_star).into(starbuttoniv);
                    ChangeStarInDB(videourl,false);
                    isstared=0;
                }
            }
        });

        //实现双击事件监听
        videoView.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
            @Override
            public void onDoubleClick() {
                if(isliked==0)
                {
                    Toast.makeText(VideoPlayActivity.this,"已喜欢",Toast.LENGTH_SHORT).show();
                    Glide.with(VideoPlayActivity.this).load(R.drawable.red_heart).into(likeiv);
                    Glide.with(VideoPlayActivity.this).load(R.drawable.red_heart).into(movelikeiv);
                    isliked=1;
                    playlikeanimation();
                }
                else
                {
                    Toast.makeText(VideoPlayActivity.this,"已取消喜欢",Toast.LENGTH_SHORT).show();
                    Glide.with(VideoPlayActivity.this).load(R.drawable.white_heart).into(likeiv);
//                    Glide.with(VideoPlayActivity.this).load(R.drawable.white_heart).into(movelikeiv);
                    isliked=0;
                }
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

    public void playlikeanimation(){
        movelikeiv.setVisibility(View.VISIBLE);
        AnimationSet animationSet=new AnimationSet(true);
        AlphaAnimation alphaAnimation=new AlphaAnimation(1.0f,0f);
        alphaAnimation.setDuration(1200);
        //TranslateAnimation的8个参数为
        //       fromXType：移动前的x轴坐标的类型
        //       fromXValue：移动前的x轴的坐标
        //       toXType：移动后的x轴的坐标的类型
        //       toXValue：移动后的x轴的坐标
        //       fromYType：移动前的y轴的坐标的类型
        //       fromYValue：移动前的y轴的坐标
        //       toYType：移动后的y轴的坐标的类型
        //       toYValue：移动后的y轴的坐标
        //RELATIVE_TO_SELF表示以自己为参照，x正方向为→，y正方向为↓
        TranslateAnimation translateAnimation=new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1f);
        translateAnimation.setDuration(1000);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);
        movelikeiv.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                movelikeiv.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void initUI(){
        //隐藏ActionBar
        getSupportActionBar().hide();
        //设置页面全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        descriptiontv=findViewById(R.id.descriptiontextview);
        nicknametv=findViewById(R.id.nicknametextview);
        videoView=findViewById(R.id.videoView);
        likecounttv=findViewById(R.id.likecounttextview);
        likeiv=findViewById(R.id.likeimageview);
        movelikeiv=findViewById(R.id.movelikeimageview);
        playbuttoniv=findViewById(R.id.playbuttoniv);
        starbuttoniv=findViewById(R.id.starbuttoniv);


        String userName = ConfigHelper.getInstance().UserName;
        if(CheckLikedInDB(userName,videourl))
        {
            isliked = 1;
            Glide.with(this).load(R.drawable.red_heart).into(likeiv);
        }
        else
        {
            isliked = 0;
            Glide.with(this).load(R.drawable.white_heart).into(likeiv);
        }
        if(CheckStaredInDB(userName,videourl))
        {
            isstared= 1;
            Glide.with(this).load(R.drawable.red_star).into(starbuttoniv);
        }
        else
        {
            isstared = 0;
            Glide.with(this).load(R.drawable.white_star).into(starbuttoniv);
        }

        descriptiontv.setText(description);
        nicknametv.setText("@"+nickname);
        float likecountf =(float)likecount/1000;
        likecountf = (float)(Math.round(likecountf*10)/10);
        likecounttv.setText(""+(likecountf>1.0f?likecountf+"k":likecount));

        Glide.with(this).load(R.drawable.playbutton).into(playbuttoniv);

        videoView.setVideoURI(Uri.parse(videourl));
    }

    private boolean CheckLikedInDB(String userName,String url)
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        String[] projection={DbContract.DIGDOGEntry.COLUMN_NAME_USERNAME, DbContract.DIGDOGEntry.TABLE_NAME_LIKED};
        String selection= DbContract.DIGDOGEntry.COLUMN_NAME_USERNAME+"='"+userName+"' AND "+ DbContract.DIGDOGEntry.TABLE_NAME_LIKED +"='"+videourl+"'";
        Cursor cursor=db.query(DbContract.DIGDOGEntry.TABLE_NAME_LIKED,projection,selection
                ,null,null,null,null);
        return cursor.getCount()!=0;
    }

    private  boolean CheckStaredInDB(String userName,String url)
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        String[] projection={DbContract.DIGDOGEntry.COLUMN_NAME_USERNAME, DbContract.DIGDOGEntry.TABLE_NAME_STARED};
        String selection= DbContract.DIGDOGEntry.COLUMN_NAME_USERNAME+"='"+userName+"' AND "+ DbContract.DIGDOGEntry.TABLE_NAME_STARED +"='"+videourl+"'";
        Cursor cursor=db.query(DbContract.DIGDOGEntry.TABLE_NAME_STARED,projection,selection
                ,null,null,null,null);
        Log.d("wf",cursor.getCount()+"");
        return cursor.getCount()!=0;
    }


    private void ChangeLikeInDB(String url,boolean liked)
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        String userName = ConfigHelper.getInstance().UserName;
        if(liked)
        {

            ContentValues contentValues=new ContentValues();
            contentValues.put(DbContract.DIGDOGEntry.COLUMN_NAME_USERNAME,userName);
            contentValues.put(DbContract.DIGDOGEntry.COLUMN_NAME_LIKED,videourl);
            db.insert(DbContract.DIGDOGEntry.TABLE_NAME_LIKED,null,contentValues);
        }
        else
        {
            String selection = DbContract.DIGDOGEntry.COLUMN_NAME_USERNAME + " LIKE ?";
            String[] selectionArgs={userName};
            int deleteRows = db.delete(DbContract.DIGDOGEntry.COLUMN_NAME_LIKED,selection,selectionArgs);
        }
        db.close();;
    }

    private void ChangeStarInDB(String url,boolean stared)
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        String userName = ConfigHelper.getInstance().UserName;
        if(stared)
        {

            ContentValues contentValues=new ContentValues();
            contentValues.put(DbContract.DIGDOGEntry.COLUMN_NAME_USERNAME,ConfigHelper.getInstance().UserName);
            contentValues.put(DbContract.DIGDOGEntry.COLUMN_NAME_STARED,videourl);
            db.insert(DbContract.DIGDOGEntry.TABLE_NAME_STARED,null,contentValues);

        }
        else
        {
            String selection = DbContract.DIGDOGEntry.COLUMN_NAME_USERNAME + " LIKE ?";
            String[] selectionArgs={userName};
            int deleteRows = db.delete(DbContract.DIGDOGEntry.COLUMN_NAME_STARED,selection,selectionArgs);
        }
        db.close();
    }


}
