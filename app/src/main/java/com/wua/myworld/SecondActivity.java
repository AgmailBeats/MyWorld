package com.wua.myworld;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SecondActivity extends AppCompatActivity {
    private static int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        new thread().start();
    }

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String[] obj_str = (String[]) msg.obj;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SecondActivity.this,
                        android.R.layout.simple_expandable_list_item_1,
                        obj_str);
                ListView listview = findViewById(R.id.mylistview);
                listview.setAdapter(adapter);
            }
        }

    };

    class thread extends Thread {
        @Override
        public void run() {
            String api = "https://www.tianqiapi.com/api?version=v6&appid=99291723&appsecret=vZe4Ehso";
            try {
                String[] str = GetData(api);
                Message msg = SecondActivity.this.handler.obtainMessage(SecondActivity.i = 1, str);
                SecondActivity.this.handler.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String[] GetData(String inputurl) throws Exception {
        String line = null;
        URL url = new URL(inputurl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setConnectTimeout(7000);
        urlConnection.setReadTimeout(10000);
        if (urlConnection.getResponseCode() != 200) {
            throw new RuntimeException("失败");
        }
        InputStream inputStream = urlConnection.getInputStream();
        InputStreamReader insputstreamreader = new InputStreamReader(urlConnection.getInputStream());
        BufferedReader buffer = new BufferedReader(insputstreamreader);
        StringBuffer json = new StringBuffer();
        while ((line = buffer.readLine()) != null) {
            json.append(line);
        }
        String sJsonData = json.toString();
        JSONObject jsonobject = new JSONObject(sJsonData);
        String[] str = {jsonobject.getString("city"), jsonobject.getString("tem")};
        urlConnection.disconnect();
        buffer.close();
        return str;
    }
}
