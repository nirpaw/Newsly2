package com.example.nir30.newsly2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.news_recycler);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        List<NewsArticle> newsArticles = new ArrayList<>();
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
