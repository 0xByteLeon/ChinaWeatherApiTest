package com.example.moonlightsw.chinaweatherapitest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                InputStreamReader inputStreamReader = null;
                BufferedReader reader = null;
                try {
                    //  各种api测试项目，打印返回结果
                    URL url = new URL("http://www.weather.com.cn/data/cityinfo/101010200.html");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(false);

                    inputStreamReader = new InputStreamReader(connection.getInputStream());
                    reader = new BufferedReader(inputStreamReader);

                    StringBuilder result = new StringBuilder();
                    String line = null;

                    if ((line = reader.readLine()) != null) {
                        result = result.append(line);
                    }

                    Log.e("APIReturn",result.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (reader != null) {
                            reader.close();
                        }

                        if (inputStreamReader != null) {
                            inputStreamReader.close();
                        }

                        if (connection != null) {
                            connection.disconnect();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
