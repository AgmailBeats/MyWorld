package com.wua.myworld;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginbutton = findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(this);
        Button registerbutton = findViewById(R.id.registerbutton);
        registerbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MyDBOpenHelper myDBHelper = new MyDBOpenHelper(MainActivity.this);
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        EditText user_Text = findViewById(R.id.usertext);
        EditText pwd_Text = findViewById(R.id.pwdtext);
        if (v.getId() == R.id.loginbutton) {
            String user = user_Text.getText().toString();
            String pwd = pwd_Text.getText().toString();
            Cursor cursor = db.rawQuery("select name,pwd from user where name = ? and pwd = ?", new String[]{user, pwd});
            if (cursor.moveToFirst()) {
                cursor.close();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "登录失败!", Toast.LENGTH_SHORT).show();
            }
        }
        if (v.getId() == R.id.registerbutton) {
            String user = user_Text.getText().toString();
            String pwd = pwd_Text.getText().toString();
            ContentValues values = new ContentValues();
            values.put("name", user);
            values.put("pwd", pwd);
            db.insert("user", null, values);
        }
    }
}
