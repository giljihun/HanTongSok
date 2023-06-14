package com.example.hantongsok;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HaksaAdapter extends RecyclerView.Adapter<HaksaAdapter.ViewHolder> {

    private List<Post> posts;
    private Context context;

    public HaksaAdapter(List<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.haksa_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.postNumberTextView.setText(String.valueOf(post.getPostNumber()));
        holder.titleTextView.setText(post.getTitle());
        holder.authorTextView.setText(post.getAuthor());
        holder.dateTextView.setText(post.getDate());

        // Add separator line between list items
        if (position < getItemCount() - 1) {
            holder.separatorView.setVisibility(View.VISIBLE);
        } else {
            holder.separatorView.setVisibility(View.GONE);
        }

        // 타이틀 클릭 이벤트 처리
        holder.titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(post);
            }
        });
    }

    private void showDialog(Post post) {
        // 다이얼로그 생성
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_content);

        // 다이얼로그 내용 설정
        TextView titleTextView = dialog.findViewById(R.id.dialog_title);
        TextView slash1 = dialog.findViewById(R.id.slash1);
        TextView authorTextView = dialog.findViewById(R.id.dialog_author);
        TextView dateTextView = dialog.findViewById(R.id.dialog_date);
        TextView contentTextView = dialog.findViewById(R.id.post_content);
        TextView filenameTextView = dialog.findViewById(R.id.txt_attachment);
        ImageButton downloadButton = dialog.findViewById(R.id.attachment);

        filenameTextView.setText("");
        contentTextView.setMovementMethod(new ScrollingMovementMethod());
        slash1.setText(" | ");
        titleTextView.setText(post.getTitle());
        authorTextView.setText(post.getAuthor());
        dateTextView.setText(post.getDate());

        // "Dismiss" 버튼 클릭 이벤트 처리
        ImageButton dismissButton = dialog.findViewById(R.id.noButton);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // 다이얼로그 닫기
            }
        });

        // 다운로드 버튼 클릭 이벤트 처리
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String attachmentUrl = post.getAttachmentUrl();
                String fileName = post.getFilename();
                if (attachmentUrl != null && !attachmentUrl.isEmpty()) {
                    new DownloadFileTask((Activity) context, attachmentUrl, fileName).execute();
                }
            }
        });

        // post 클래스에 저장한 url을 가져와서 크롤링.
        String url = post.getUrl();
        new DownloadContentTask(contentTextView, post, filenameTextView).execute(url);

        // 다이얼로그 표시
        dialog.show();
    }

    private class DownloadContentTask extends AsyncTask<String, Void, String> {
        private TextView contentTextView;
        private TextView filenameTextView;
        private String currentTitle;
        private Post post;

        public DownloadContentTask(TextView contentTextView, Post post, TextView filenameTextView) {
            this.contentTextView = contentTextView;
            this.filenameTextView = filenameTextView;
            this.post = post;
        }

        @Override
        protected String doInBackground(String... urls) {
            String attachmentUrl = "";
            String fileName = "";
            String url = urls[0];
            StringBuilder contentBuilder = new StringBuilder();

            try {
                Document doc = Jsoup.connect(url).get();
                Elements contentElements = doc.select("p.0:not(table p)");
                Element titleElement = doc.selectFirst("h2.ui.bbs--view--tit");
                currentTitle = titleElement.text();

                for (Element element : contentElements) {
                    contentBuilder.append(element.text()).append("\n\n");
                }
                // 첨부 파일 다운로드 링크 설정

                Elements attachmentElements = doc.select("a.btn-file");

                if (!attachmentElements.isEmpty()) {
                    Element attachmentElement = attachmentElements.first();
                    String href = attachmentElement.attr("href");

                    // 다운로드 URL 생성
                    // 식별자와 버전 정보 추출
                    String fileId = "";
                    String fileSn = "";
                    Pattern pattern = Pattern.compile("'([^']+)'");
                    Matcher matcher = pattern.matcher(href);
                    if (matcher.find()) {
                        fileId = matcher.group(1);
                    }
                    if (matcher.find()) {
                        fileSn = matcher.group(1);
                    }

                    // 다운로드 URL 생성
                    attachmentUrl = "https://www.hanbat.ac.kr/cmm/fms/FileDown.do"
                            + "?atchFileId=" + fileId
                            + "&fileSn=" + fileSn;
                }
                Element fileDiv = doc.selectFirst("div.ui.bbs--view--file");
                if (fileDiv != null) {
                    Elements filenameElements = fileDiv.select("i");
                    if (!filenameElements.isEmpty()) {
                        Element filenameElement = filenameElements.first();
                        Element parentElement = filenameElement.parent();
                        String text = parentElement.ownText();
                        fileName = text;
                    }
                }

                Log.d("Crawling", "attachmentUrl: " + attachmentUrl);
                Log.d("Crawling", "fileName: " + fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }

            // 현재 게시물인지 확인하여 추가
            for (Post post : posts) {
                if (post.getTitle().equals(currentTitle)) {
                    post.setFilename(fileName);
                    post.setAttachmentUrl(attachmentUrl);
                    break;
                }
            }

            return contentBuilder.toString();
        }

        @Override
        protected void onPostExecute(String content) {
            contentTextView.setText(content);
            filenameTextView.setText(post.getFilename());
        }
    }

    private class DownloadFileTask extends AsyncTask<Void, Void, Void> {
        private String attachmentUrl;
        private Activity activity;
        private String filename;

        public DownloadFileTask(Activity activity, String attachmentUrl, String filename) {
            this.activity = activity;
            this.attachmentUrl = attachmentUrl;
            this.filename = filename;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(attachmentUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();

                // 파일을 저장할 경로 및 파일명 지정
                String downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                String modifiedFilename = filename.replaceAll("\\[.*\\]", "").trim();

                String filepath = downloadFolder + "/" + modifiedFilename;

                Log.d("FilePath", "File path: " + filepath); // Log 추가
                FileOutputStream output = new FileOutputStream(filepath);

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }

                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "다운로드가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView postNumberTextView;
        TextView titleTextView;
        TextView authorTextView;
        TextView dateTextView;
        View separatorView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postNumberTextView = itemView.findViewById(R.id.tvPostNumber);
            titleTextView = itemView.findViewById(R.id.tvPostTitle);
            authorTextView = itemView.findViewById(R.id.tvPostAuthor);
            dateTextView = itemView.findViewById(R.id.tvPostDate);
            separatorView = itemView.findViewById(R.id.separatorView);

            titleTextView.setSelected(true);
        }
    }
}
