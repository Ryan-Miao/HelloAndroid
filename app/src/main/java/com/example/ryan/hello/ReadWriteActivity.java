package com.example.ryan.hello;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * 文件读写练习
 * Created by miao on 2/13/17.
 */

public class ReadWriteActivity extends AppCompatActivity {


    public static final String FILE_PATH = "data/data/com.example.ryan.hello/info.txt";
    public static final String FILE_NAME = "info.txt";
    public static final String NAME_SEPATATOR = ":";
    public static final String SHARED_PREFERENCES_NAME = "user_info";
    private EditText et_name;
    private EditText et_pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_write);

        et_name = (EditText) findViewById(R.id.username);
        et_pass = (EditText) findViewById(R.id.password);

        try {
            readAccount();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Button loginBtn = (Button) findViewById(R.id.login);
        assert loginBtn != null;
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_name.getText().toString();
                String password = et_pass.getText().toString();

                Log.i(ReadWriteActivity.class.getName(),
                        "登录成功\n用户名:" + username + "\n密码：" + password);
                Toast.makeText(ReadWriteActivity.this, "登录成功", Toast.LENGTH_LONG).show();

                CheckBox remember = (CheckBox) findViewById(R.id.remember);
                if (remember.isChecked()) {
                    Log.i(ReadWriteActivity.class.getName(), "记住了");
                    //sd card path
//                    File externalStorageDirectory = Environment.getExternalStorageDirectory();
                    File file = new File(getFilesDir(), FILE_NAME);

                    try {
                        FileOutputStream outputStream = new FileOutputStream(file);
                        outputStream.write((username + NAME_SEPATATOR + password).getBytes());

                        //通常写入SharedPreference 来保存密码
                        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putString("username", username);
                        edit.putString("password", password);
                        edit.commit();


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.i(ReadWriteActivity.class.getName(), "No");
                }
            }
        });

    }

    public void readAccount() throws IOException {

//        File file = new File(FILE_PATH);
        //getFilesDir 获取当前目录
        File file = new File(getFilesDir(), FILE_NAME);

        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String data = reader.readLine();
            Log.i(ReadWriteActivity.class.getName(), "获取到数据：" + data);
            String[] arr = data.split(NAME_SEPATATOR);
            String name = arr[0];
            String pass = arr[1];

            et_name.setText(name);
            et_pass.setText(pass);
        }

        //通过SharedPreferences来读文件
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        preferences.getString("username", "");
        preferences.getString("password", "");

    }


}
