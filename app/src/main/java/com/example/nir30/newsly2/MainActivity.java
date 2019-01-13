package com.example.nir30.newsly2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<NewsArticle> newsArticles = new ArrayList<>();

    static final String NEWS_SOURCE ="mtv-news";
    static final String API_KEY ="13475800ebb34d37bd8f3590529cddee";
    static final String KEY_AUTHOR = "author";
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_URL = "url";
    static final String KEY_URLTOIMAGE = "urlToImage";
    static final String KEY_PUBLISHEDAT = "publishedAt";
    static final String KEY_CONTENT = "content";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_article,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.news_recycler);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, ShowArticleActivity.class);
                        intent.putExtra("title_extra", newsArticles.get(position).getTitle());
                        intent.putExtra("content_extra", newsArticles.get(position).getContent());
                        intent.putExtra("imgurl_extra", newsArticles.get(position).getImgUrl());
                        intent.putExtra("url_extra", newsArticles.get(position).getUrl());
                        intent.putExtra("author_extra", newsArticles.get(position).getAutor());
                        intent.putExtra("publishedAt_extra", newsArticles.get(position).getPublishedAt());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, newsArticles.get(position).getTitle() +": " +newsArticles.get(position).getUrl() );
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                    }
                })
        );
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        if(Function.isNetworkAvailable(getApplicationContext()))
        {
            DownloadNews newsTask = new DownloadNews();
            newsTask.execute();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    }


    class DownloadNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(String... args) {
            String xml = "";
            String urlParameters = "";
            xml = Function.excuteGet("https://newsapi.org/v2/everything?sources="+NEWS_SOURCE+"&apiKey="+API_KEY, urlParameters);
            return  xml;
        }
        @Override
        protected void onPostExecute(String xml) {

            if(xml.length()>10){

                try {
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        String autor =  jsonObject.optString(KEY_AUTHOR).toString();
                        String title =  jsonObject.optString(KEY_TITLE).toString();
                        String description =  jsonObject.optString(KEY_DESCRIPTION).toString();
                        String url =  jsonObject.optString(KEY_URL).toString();
                        String imgurl =  jsonObject.optString(KEY_URLTOIMAGE).toString();
                        String date =  jsonObject.optString(KEY_PUBLISHEDAT).toString();
                        String content =  jsonObject.optString(KEY_CONTENT).toString();
                        newsArticles.add(new NewsArticle(autor, title, description, url,imgurl, date, content  ));
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }
                NewsArticleAdapter newsArticleAdapter = new NewsArticleAdapter(newsArticles);
                recyclerView.setAdapter(newsArticleAdapter);

//                ListNewsAdapter adapter = new ListNewsAdapter(MainActivity.this, dataList);
//                listNews.setAdapter(adapter);
//
//                listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    public void onItemClick(AdapterView<?> parent, View view,
//                                            int position, long id) {
//                        Intent i = new Intent(MainActivity.this, DetailsActivity.class);
//                        i.putExtra("url", dataList.get(+position).get(KEY_URL));
//                        startActivity(i);
//                    }
//                });

            }else{
                Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }



    }

}

