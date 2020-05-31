package com.example.dig_dog;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dig_dog.db.DbContract;
import com.example.dig_dog.db.Dbhelper;

public class LogonActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button logonbt;
    private Dbhelper dbhelper;
//    private TextView hinttv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);

        username=findViewById(R.id.logonusername);
        password=findViewById(R.id.logonpassword);
        logonbt=findViewById(R.id.logon);
//        hinttv=findViewById(R.id.hinttv);
        dbhelper=new Dbhelper(this);



        logonbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Adduser();
            }
        });

    }

    public boolean Checkusername()
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        String selection= DbContract.DIGDOGEntry.COLUMN_NAME_USERNAME+"='"+username.getText().toString()+"'";
        String[] projection={DbContract.DIGDOGEntry.COLUMN_NAME_USERNAME};
        Cursor cursor=db.query(DbContract.DIGDOGEntry.TABLE_NAME_PASSWORD, projection
                ,selection,null,null,null,null);
        if(cursor.getCount()==0)
        {
//            hinttv.setText("用户名可用");
            return true;
        }
        else
        {
//            hinttv.setText("用户名已被使用");
            return false;
        }

    }

    public void Adduser()
    {
        if(TextUtils.isEmpty(username.getText())||TextUtils.isEmpty(password.getText()))
        {
            Toast.makeText(LogonActivity.this,"用户名或密码为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Checkusername())
        {
            SQLiteDatabase db=dbhelper.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(DbContract.DIGDOGEntry.COLUMN_NAME_USERNAME,username.getText().toString());
            contentValues.put(DbContract.DIGDOGEntry.COLUMN_NAME_PASSWORD,password.getText().toString());
            db.insert(DbContract.DIGDOGEntry.TABLE_NAME_PASSWORD,null,contentValues);
            db.close();
            Toast.makeText(LogonActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(LogonActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(LogonActivity.this,"名字已被使用",Toast.LENGTH_SHORT).show();
        }
    }
}
