package com.example.nir30.newsly2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class NewsArticleAdapter extends RecyclerView.Adapter<NewsArticleAdapter.NewsArticleViewHolder>{
    private List<NewsArticle> newsArticles;
    public NewsArticleAdapter( List<NewsArticle> newsArticles) {
        this.newsArticles = newsArticles;
    }

    public class NewsArticleViewHolder extends RecyclerView.ViewHolder{
        ImageView imgArticle;
        TextView titleArticle;
        TextView dateArticle;
        public NewsArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            imgArticle = itemView.findViewById(R.id.img_article);
            titleArticle = itemView.findViewById(R.id.title_article);
            dateArticle = itemView.findViewById(R.id.date_article);
        }
    }

    @NonNull
    @Override
    public NewsArticleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_layout,viewGroup,false);
        NewsArticleViewHolder holder = new NewsArticleViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsArticleViewHolder holder, int i) {
        NewsArticle article  = newsArticles.get(i);
        holder.titleArticle.setText(article.getTitle());
        holder.dateArticle.setText(article.getPublishedAt());
            Picasso.get()
                    .load(article.getImgUrl())
                    .resize(300, 200)
                    .into(holder.imgArticle);
        }

    @Override
    public int getItemCount() {
        return newsArticles.size();
    }
}
