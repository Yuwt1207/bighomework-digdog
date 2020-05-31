package com.example.dig_dog;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dig_dog.db.DbContract;
import com.example.dig_dog.db.Dbhelper;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameet;
    private EditText passwordet;
    private Button loginbt;
    private Button logonbt;
    private Dbhelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbhelper=new Dbhelper(this);

        usernameet=findViewById(R.id.username);
        passwordet=findViewById(R.id.password);
        loginbt=findViewById(R.id.login);
        logonbt=findViewById(R.id.logonbutton);



        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(usernameet.getText())||TextUtils.isEmpty(passwordet.getText()))
                {
                    Toast.makeText(LoginActivity.this,"用户名或密码为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean temp= Checkusername();
                if(!temp)//找到对应用户信息
                {
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"用户名或密码不正确",Toast.LENGTH_SHORT).show();
                }

            }
        });

        logonbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,LogonActivity.class);
                startActivity(intent);

            }
        });
    }

    public boolean Checkusername(){
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        String[] projection={DbContract.DIGDOGEntry.COLUMN_NAME_USERNAME, DbContract.DIGDOGEntry.COLUMN_NAME_PASSWORD};
        String selection= DbContract.DIGDOGEntry.COLUMN_NAME_USERNAME+"='"+usernameet.getText().toString()+"' AND "
                + DbContract.DIGDOGEntry.COLUMN_NAME_PASSWORD+"='"+passwordet.getText().toString()+"'";
        Cursor cursor=db.query(DbContract.DIGDOGEntry.TABLE_NAME_PASSWORD,projection,selection
                ,null,null,null,null);
        if(cursor.getCount()==0)//没有找到对应信息
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
