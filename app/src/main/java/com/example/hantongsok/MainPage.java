package com.example.hantongsok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 날짜 표시 기능
        {
            TextView textDate = findViewById(R.id.text_date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());
            String currentDate = dateFormat.format(new Date());
            textDate.setText(currentDate);
        }
        // marquee
        {
            TextView headline = (TextView) findViewById(R.id.headline);
            headline.setSelected(true);

            TextView under_headline = (TextView) findViewById(R.id.under_headline);
            under_headline.setSelected(true);
        }
        // 이미지버튼으로 액티비티 이동
        {
            // 학사공지 버튼
            {
                ImageView imgHaksa = findViewById(R.id.img_haksa);
                imgHaksa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 눌러짐 효과
                        {
                            Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_scale_animation);
                            imgHaksa.startAnimation(anim);

                            int filterColor = Color.argb(200, 50, 50, 50); // 적당한 짙은 회색 색상 (알파, 빨강, 초록, 파랑)
                            imgHaksa.setColorFilter(filterColor, PorterDuff.Mode.MULTIPLY);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // 필터 제거
                                    imgHaksa.clearColorFilter();
                                }
                            }, 500);
                        }
                        Intent intent = new Intent(MainPage.this, HaksaPage.class);
                        startActivity(intent);
                    }
                });
            }

            // 공지사항 버튼
            {
                ImageView imgGongzi = findViewById(R.id.img_gongzi);
                imgGongzi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 눌러짐 효과
                        {
                            Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_scale_animation);
                            imgGongzi.startAnimation(anim);
                            int filterColor = Color.argb(200, 50, 50, 50); // 적당한 짙은 회색 색상 (알파, 빨강, 초록, 파랑)
                            imgGongzi.setColorFilter(filterColor, PorterDuff.Mode.MULTIPLY);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // 필터 제거
                                    imgGongzi.clearColorFilter();
                                }
                            }, 500);
                        }

                        Intent intent = new Intent(MainPage.this, GongziPage.class);
                        startActivity(intent);
                    }
                });
            }

            // 주요행사 버튼
            {
                ImageView imgHangsa = findViewById(R.id.img_hangsa);
                imgHangsa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 눌러짐 효과
                        {
                            Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_scale_animation);
                            imgHangsa.startAnimation(anim);
                            int filterColor = Color.argb(200, 50, 50, 50); // 적당한 짙은 회색 색상 (알파, 빨강, 초록, 파랑)
                            imgHangsa.setColorFilter(filterColor, PorterDuff.Mode.MULTIPLY);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // 필터 제거
                                    imgHangsa.clearColorFilter();
                                }
                            }, 500);
                        }

                        Intent intent = new Intent(MainPage.this, HangsaPage.class);
                        startActivity(intent);
                    }
                });
            }

            // 설정 버튼
            {
                ImageView imgSetting = findViewById(R.id.img_setting);
                imgSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 눌러짐 효과
                        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_scale_animation);
                        imgSetting.startAnimation(anim);
                        int filterColor = Color.argb(200, 50, 50, 50); // 적당한 짙은 회색 색상 (알파, 빨강, 초록, 파랑)
                        imgSetting.setColorFilter(filterColor, PorterDuff.Mode.MULTIPLY);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // 필터 제거
                                imgSetting.clearColorFilter();
                            }
                        }, 500);

                        Intent intent = new Intent(MainPage.this, SettingPage.class);
                        startActivity(intent);
                    }
                });
            }
        }

    }
}