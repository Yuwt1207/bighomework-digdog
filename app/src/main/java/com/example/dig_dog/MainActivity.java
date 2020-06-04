package com.example.dig_dog;


//ver1.0 2020-5-10 增加点击页面跳转播放视频和双击事件

import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import  com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    private List<Face> faceList = new ArrayList<>();
    private List<Video> videoList;
    final private String apiFileName="API.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        InitView();


    }

    private void InitView()
    {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView backBtn = findViewById(R.id.back);
        backBtn.setOnClickListener(
            (View view)->{ finish(); }
        );

        RecyclerView recyclerView = findViewById(R.id.face_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //将asserts下的API.txt解析为videoList
        parseGsonString();
        //为每个videoList里的video生成一个face,加进faceList
//        for(Video v:videoList)
//        {
//            Face face=new Face(v.nickname,v.avatar);
//            faceList.add(face);
//        }
        FaceAdapter adapter = new FaceAdapter(videoList,this);
        recyclerView.setAdapter(adapter);
    }


    private void parseGsonString() {
        String gsonString=new String("");
        Gson gson = new Gson();
        try {
            InputStream inputStream = getAssets().open(apiFileName);
            InputStreamReader inputStreamReader= new InputStreamReader(inputStream,"UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            gsonString=reader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        videoList=gson.fromJson(gsonString,new TypeToken<List<Video>>(){}.getType());
    }
}
