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

import com.example.hantongsok.Gongzi.GongziPage;
import com.example.hantongsok.Haksa.HaksaPage;
import com.example.hantongsok.Hangsa.HangsaPage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
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

            //설정 버튼
//            {
//                ImageView imgSetting = findViewById(R.id.img_setting);
//                imgSetting.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // 눌러짐 효과
//                        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_scale_animation);
//                        imgSetting.startAnimation(anim);
//                        int filterColor = Color.argb(200, 50, 50, 50); // 적당한 짙은 회색 색상 (알파, 빨강, 초록, 파랑)
//                        imgSetting.setColorFilter(filterColor, PorterDuff.Mode.MULTIPLY);
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                // 필터 제거
//                                imgSetting.clearColorFilter();
//                            }
//                        }, 500);
//
//                        Intent intent = new Intent(MainPage.this, SettingPage.class);
//                        startActivity(intent);
//                    }
//                });
//            }
        }

        // 탑, 언더 헤드라인 크롤링
        {
            TextView top_headline = findViewById(R.id.headline);
            TextView under_headline = findViewById(R.id.under_headline);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Under
                    try {
                        // 웹 페이지의 URL
                        String url = "https://www.hanbat.ac.kr/bbs/BBSMSTR_000000000042/list.do";

                        // Jsoup을 사용하여 웹 페이지의 내용 가져오기
                        Document doc = Jsoup.connect(url).get();

                        // 웹 페이지에서 <tr class="notice"> 요소 가져오기
                        Element trElement = doc.selectFirst("tr.notice");

                        // <tr class="notice"> 내부의 <td> 요소 가져오기
                        Element tdElement = trElement.selectFirst("td.subject");

                        // <td> 내부의 <a> 요소 가져오기
                        Element aElement = tdElement.selectFirst("a");

                        // <a> 요소의 텍스트(제목) 가져오기
                        final String title = aElement.text();

                        // UI 업데이트를 위해 메인 스레드에서 실행
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 짧은글 방지 코드
                                under_headline.setText(title + "   " + title);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Top
                    try {
                        // 웹 페이지의 URL
                        String url = "https://www.hanbat.ac.kr/bbs/BBSMSTR_000000000050/list.do";

                        // Jsoup을 사용하여 웹 페이지의 내용 가져오기
                        Document doc = Jsoup.connect(url).get();

                        // 웹 페이지에서 <tr class="notice"> 요소 가져오기
                        Element trElement = doc.selectFirst("tr.notice");

                        // <tr class="notice"> 내부의 <td> 요소 가져오기
                        Element tdElement = trElement.selectFirst("td.subject");

                        // <td> 내부의 <a> 요소 가져오기
                        Element aElement = tdElement.selectFirst("a");

                        // <a> 요소의 텍스트(제목) 가져오기
                        final String title = aElement.text();

                        // UI 업데이트를 위해 메인 스레드에서 실행
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                top_headline.setText("<HEADLINE NEWS> : " + title);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
        }
    }
}