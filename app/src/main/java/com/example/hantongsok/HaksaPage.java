package com.example.hantongsok;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HaksaPage extends AppCompatActivity {

    private List<Post> postList;
    private HaksaAdapter adapter;
    private RecyclerView recyclerView;

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

        // 크롤링 수행
        new CrawlTask().execute("https://www.hanbat.ac.kr/bbs/BBSMSTR_000000000042/list.do");
    }

    private class CrawlTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... urls) {
            try {
                Document doc = Jsoup.connect(urls[0]).get();
                Element table = doc.selectFirst("table.board_list.table.table-default");
                Elements rows = table.select("tbody tr");

                int rowIndex = 2; // 3번째 로우 선택

                if (rowIndex < rows.size()) {
                    Element row = rows.get(rowIndex);
                    Elements cells = row.select("td");
                    int postNumber = Integer.parseInt(cells.get(0).text());
                    String title = cells.get(1).text();
                    String author = cells.get(2).text();
                    String date = cells.get(4).text();

                    postList.add(new Post(postNumber, title, author, date));

                    Log.d("Crawling", "Post Number: " + postNumber);
                    Log.d("Crawling", "Title: " + title);
                    Log.d("Crawling", "Author: " + author);
                    Log.d("Crawling", "Date: " + date);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // 크롤링 완료 후 어댑터에 데이터 변경 알림
            adapter.notifyDataSetChanged();
        }
    }
}




