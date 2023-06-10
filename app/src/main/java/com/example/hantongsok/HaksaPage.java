package com.example.hantongsok;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

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

    private RecyclerView recyclerView;
    private NoticeAdapter recyclerViewAdapter;
    private List<NoticeItem> articleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haksa);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        articleList = new ArrayList<>();
        recyclerViewAdapter = new NoticeAdapter(articleList);
        recyclerView.setAdapter(recyclerViewAdapter);

        new FetchDataTask().execute();
    }

    private class FetchDataTask extends AsyncTask<Void, Void, List<NoticeItem>> {

        @Override
        protected List<NoticeItem> doInBackground(Void... voids) {
            try {
                String url = "https://www.hanbat.ac.kr/prog/bbsArticle/BBSMSTR_000000000042/list.do";
                Document document = Jsoup.connect(url).get();
                Elements articleElements = document.select("ul.list_txt > li");

                List<NoticeItem> noticeItems = new ArrayList<>();
                for (Element articleElement : articleElements) {
                    String title = articleElement.select("a > span").text();
                    String link = articleElement.select("a").attr("href");
                    NoticeItem noticeItem = new NoticeItem(title, link);
                    noticeItems.add(noticeItem);
                }
                Log.d("Crawling", "Crawling completed. Total items: " + noticeItems.size());



                return noticeItems;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<NoticeItem> noticeItems) {
            if (noticeItems != null) {
                articleList.addAll(noticeItems);
                recyclerViewAdapter.notifyDataSetChanged();
            }
        }
    }
}
