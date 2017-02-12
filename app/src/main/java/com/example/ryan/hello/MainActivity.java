package com.example.ryan.hello;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn_call);

        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("按钮被点击了");
                EditText editText = (EditText) findViewById(R.id.et_phone);
                Editable text = editText.getText();
                System.out.println(text.toString());

                //创建意图对象
                Intent intent = new Intent();
                //封装动作
                intent.setAction(Intent.ACTION_CALL);
                //设置打给谁
                intent.setData(Uri.parse("tel:" + text.toString()));

                //把动作告诉系统
                startActivity(intent);


            }
        });

        Button button1 = (Button) findViewById(R.id.btn_1);
        assert button1 != null;
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("按钮1被点击");
            }
        });

        Button button2 = (Button) findViewById(R.id.btn_2);
        assert button2 != null;
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        System.out.println("第二个按钮被点击");
    }

    public void sendMessage(View view) {
        EditText phone = (EditText) findViewById(R.id.et_phone2);

        EditText content = (EditText) findViewById(R.id.et_content);

        Log.d("test1", MessageFormat.format("{0} 发送了的内容为： {1}", phone.getText().toString(), content.getText().toString()));

        SmsManager smsManager =SmsManager.getDefault();
        smsManager.sendTextMessage(phone.getText().toString(),null, content.getText().toString(), null, null);
    }
}
