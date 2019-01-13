package com.example.nir30.newsly2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<NewsArticle> newsArticles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.news_recycler);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, ShowArticleActivity.class);
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

        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));
        newsArticles.add(new NewsArticle("test"));

        NewsArticleAdapter newsArticleAdapter = new NewsArticleAdapter(newsArticles);
        recyclerView.setAdapter(newsArticleAdapter);
    }
}
