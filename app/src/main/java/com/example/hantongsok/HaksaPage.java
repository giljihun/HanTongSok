package com.example.hantongsok;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HaksaPage extends AppCompatActivity {

    private List<Post> postList;
    private HaksaAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haksa);

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 게시글 목록을 담을 List<Post> 생성
        postList = new ArrayList<>();

        // 어댑터 생성 및 RecyclerView에 설정
        adapter = new HaksaAdapter(postList);
        recyclerView.setAdapter(adapter);

        progressBar = findViewById(R.id.progressBar);

        // 크롤링 수행
        new CrawlTask().execute("https://www.hanbat.ac.kr/bbs/BBSMSTR_000000000042/list.do");
        loadPosts();
    }

    // CrawlTask 클래스에 2023년 1월 1일 이후 게시글 크롤링 로직 추가
    private class CrawlTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {
            try {
                Document doc = Jsoup.connect(urls[0]).get();
                Elements pagination = doc.select("div.pagination ul li.page-item:not(.disabled) a.page-link");

                // Loop through each page link and crawl the posts
                for (Element pageLink : pagination) {
                    String pageUrl = urls[0] + pageLink.attr("href");
                    doc = Jsoup.connect(pageUrl).get();
                    Elements rows = doc.select("table.board_list tbody tr");

                    for (Element row : rows) {
                        // Skip rows with class="notice"
                        if (row.hasClass("notice")) {
                            continue;
                        }

                        Elements cells = row.select("td");

                        // Check if the row has the required number of cells
                        if (cells.size() >= 5) {
                            int postNumber = Integer.parseInt(cells.get(0).text());
                            String title = cells.get(1).text();
                            String author = cells.get(2).text();

                            // Check if the date cell is present
                            Element dateCell = cells.get(4);
                            String date = dateCell != null ? dateCell.text() : "";

                            // Check if the post date is after January 1, 2023
                            if (isPostDateAfter2023(date)) {
                                postList.add(new Post(postNumber, title, author, date));

                                Log.d("Crawling", "Post Number: " + postNumber);
                                Log.d("Crawling", "Title: " + title);
                                Log.d("Crawling", "Author: " + author);
                                Log.d("Crawling", "Date: " + date);
                            }
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // 크롤링 작업 시작 전에 ProgressBar 표시
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            // 어댑터에 변경된 데이터 설정
            adapter.notifyDataSetChanged();

            progressBar.setVisibility(View.GONE);
        }


    }


    // "yyyy-MM-dd" 형식의 날짜를 비교하여 2023년 1월 1일 이후인지 확인하는 메서드
    private boolean isPostDateAfter2023(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.set(2023, Calendar.JANUARY, 1);
            Date date2023 = calendar.getTime();
            return date != null && date.after(date2023);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    private void loadPosts() {
        adapter.setPosts(postList);
    }
}
