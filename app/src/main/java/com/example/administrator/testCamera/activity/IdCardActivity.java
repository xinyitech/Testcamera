package com.example.administrator.testCamera.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.testCamera.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/6/3 0003.
 * 身份认证
 */

public class IdCardActivity extends Activity implements View.OnClickListener {
    private Button btnSubmit;
    private ImageView arrowsBack;
    private RelativeLayout ivIdCardFront, ivIdCardBack;
    private int SHOOT_ID_CARD_FRONT_RESULT = 0;
    private int SHOOT_ID_CARD_SIDE_RESULT = 1;
    private RelativeLayout linearLayoutShowFront;
    private RelativeLayout linearLayoutShowBack;

    private ImageView showIvIdCardFront;
    private ImageView imageViewFront;

    ArrayList<byte[]> pics = new ArrayList<>();

    private ImageView showIvIdCardBack;
    private ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_card);
        this.init();
    }

    private void init() {
        this.initModule();
        this.addListener();
    }

    private void initModule() {
        this.btnSubmit = (Button) findViewById(R.id.submit);
        this.arrowsBack = (ImageView) findViewById(R.id.arrowsBack);
        this.ivIdCardFront = (RelativeLayout) findViewById(R.id.ivIdCardFront);
        this.ivIdCardBack = (RelativeLayout) findViewById(R.id.ivIdCardBack);
        this.linearLayoutShowFront = (RelativeLayout) findViewById(R.id.linearLayoutShowFront);
        this.showIvIdCardFront = (ImageView) findViewById(R.id.showIvIdCardFront);
        this.imageViewFront = (ImageView) findViewById(R.id.imageViewFront);
        this.linearLayoutShowBack = (RelativeLayout) findViewById(R.id.linearLayoutShowBack);
        this.showIvIdCardBack = (ImageView) findViewById(R.id.showIvIdCardBack);
        this.imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
    }

    private void addListener() {
        this.arrowsBack.setOnClickListener(this);
        this.btnSubmit.setOnClickListener(this);
        this.ivIdCardFront.setOnClickListener(this);
        this.ivIdCardBack.setOnClickListener(this);
        this.imageViewFront.setOnClickListener(this);
        this.imageViewBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.submit) {
            Intent intent = getIntent();
            intent.putExtra("isCommit", true);
            intent.putExtra("idCardPicOne", pics.get(0));
            intent.putExtra("idCardPicTwo", pics.get(1));
            setResult(RESULT_OK, intent);
            finish();
        } else if (id == R.id.arrowsBack) {
            this.finish();
        } else if (id == R.id.ivIdCardFront) {
            //身份证正面
            Intent intent = new Intent(this, IdCardCameraActivity.class);
            intent.putExtra("tips", "拍摄身份证正面");
            startActivityForResult(intent, SHOOT_ID_CARD_FRONT_RESULT);
        } else if (id == R.id.ivIdCardBack) {
            //身份证反面
            Intent intent = new Intent(this, IdCardCameraActivity.class);
            intent.putExtra("tips", "拍摄身份证反面");
            startActivityForResult(intent, SHOOT_ID_CARD_SIDE_RESULT);
        } else if (id == R.id.imageViewFront) {
            Intent intent = new Intent(this, IdCardCameraActivity.class);
            intent.putExtra("tips", "拍摄身份证正面");
            startActivityForResult(intent, SHOOT_ID_CARD_FRONT_RESULT);
        } else if (id == R.id.imageViewBack) {
            Intent intent = new Intent(this, IdCardCameraActivity.class);
            intent.putExtra("tips", "拍摄身份证反面");
            startActivityForResult(intent, SHOOT_ID_CARD_SIDE_RESULT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHOOT_ID_CARD_FRONT_RESULT) {
            switch (resultCode) {
                case RESULT_OK:
                    Bitmap bitmap = null;
                    byte[] pic = data.getByteArrayExtra("idCardPic");
                    if (!(pic == null || pic.length == 0)) {
                        pics.add(0, pic);

                        ivIdCardFront.setVisibility(View.GONE);
                        linearLayoutShowFront.setVisibility(View.VISIBLE);
                        showIvIdCardFront.setVisibility(View.VISIBLE);
                        bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
                        showIvIdCardFront.setImageBitmap(bitmap); //设置Bitmap
                        btnSubmit.setTextColor(getResources().getColor(R.color.white));
                        btnSubmit.setEnabled(true);
                    } else {
                        ivIdCardFront.setVisibility(View.VISIBLE);
                        linearLayoutShowFront.setVisibility(View.GONE);
                        btnSubmit.setTextColor(getResources().getColor(R.color.submit_color));
                        btnSubmit.setEnabled(false);
                    }
                    break;
                default:
            }

        } else if (requestCode == SHOOT_ID_CARD_SIDE_RESULT) {
            switch (resultCode) {
                case RESULT_OK:
                    Bitmap bitmap = null;
                    byte[] pic = data.getByteArrayExtra("idCardPic");
                    if (!(pic == null || pic.length == 0)) {
                        pics.add(1, pic);

                        ivIdCardBack.setVisibility(View.GONE);
                        linearLayoutShowBack.setVisibility(View.VISIBLE);
                        bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
                        showIvIdCardBack.setImageBitmap(bitmap); //设置Bitmap
                        btnSubmit.setTextColor(getResources().getColor(R.color.white));
                        btnSubmit.setEnabled(true);
                    } else {
                        ivIdCardBack.setVisibility(View.VISIBLE);
                        linearLayoutShowBack.setVisibility(View.GONE);
                        btnSubmit.setTextColor(getResources().getColor(R.color.submit_color));
                        btnSubmit.setEnabled(false);
                    }
                    break;
                default:
            }
        }
    }

}
