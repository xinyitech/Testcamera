package com.example.administrator.testCamera.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.testCamera.R;


public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                XinyiCameraSelector.create(MainActivity.this).intentFaceView(10);
                XinyiCameraSelector.create(MainActivity.this).intentIdCardView(20, true);
            }
        });

        imageView = findViewById(R.id.iv_test);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            byte[] pic = data.getByteArrayExtra("facePic");
            if (pic != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
                imageView.setImageBitmap(bitmap);
            }
        } else if (requestCode == 20) {
            Boolean isCommit = data.getBooleanExtra("isCommit", false);
            if (isCommit) {
                //提交按钮返回
                byte[] picOne = data.getByteArrayExtra("idCardPicOne");//身份证正面
                byte[] picTwo = data.getByteArrayExtra("idCardPicTwo");//身份证反面

            } else {
                byte[] pic = data.getByteArrayExtra("idCardPic");
                if (pic != null) {
                    Bitmap bitmapOne = BitmapFactory.decodeByteArray(pic, 0, pic.length);
                    imageView.setImageBitmap(bitmapOne);
                }
            }

        }
    }
}
