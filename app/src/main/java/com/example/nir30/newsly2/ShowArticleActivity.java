package com.example.nir30.newsly2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ShowArticleActivity extends AppCompatActivity {

    AppBarLayout appBarLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_article);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout =  findViewById(R.id.toolbar_layout);
        appBarLayout = findViewById(R.id.app_bar);
        toolbarLayout.setScrollBarSize(222);
        Picasso.get().load(getIntent().getStringExtra("imgurl_extra")).resize(300,200).into(new Target() {
            ProgressBar loader  = findViewById(R.id.loader_show);
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Drawable drawable =new BitmapDrawable(bitmap);
                appBarLayout.setBackground(drawable);
                loader.setVisibility(View.GONE);

            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                loader.setVisibility(View.GONE);

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }

        });



        toolbar.setTitleTextColor(Color.GREEN);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_language_white_24dp);
        toolbarLayout.setTitle(getIntent().getStringExtra("title_extra"));
        TextView contectTV = findViewById(R.id.tv_content);
        contectTV.setText(getIntent().getStringExtra("content_extra"));
        TextView authorTV = findViewById(R.id.author_tv);
        authorTV.setText(getIntent().getStringExtra("author_extra"));
        TextView dateTV = findViewById(R.id.date_tv);
        dateTV.setText(getIntent().getStringExtra("publishedAt_extra"));


        Button readMoreButton = (Button) findViewById(R.id.readmore_btn);
        readMoreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToWebsite();
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWebsite();
                Snackbar.make(view, "Wait...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    private void goToWebsite(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getStringExtra("url_extra")));
        startActivity(intent);
    }
}
