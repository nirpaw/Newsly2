package com.example.nir30.newsly2;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    RecyclerView newsRecyclerView;
    RecyclerView sportRecyclerView;
    List<NewsArticle> newsArticles = new ArrayList<>();
    List<NewsArticle> sportNewsArticles = new ArrayList<>();
    Bitmap pic;
    final int SETTINGS_REQUEST = 1;
    boolean notificationCBIsEnable;
    int intervalNotificationInSec = -1;
    AlarmManager alarmManager;

    static final String NEWS_SOURCE = "mtv-news";
    static final String SPORT_NEWS_SOURCE = "bbc-sport";
    static final String API_KEY = "13475800ebb34d37bd8f3590529cddee";
    static final String KEY_AUTHOR = "author";
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_URL = "url";
    static final String KEY_URLTOIMAGE = "urlToImage";
    static final String KEY_PUBLISHEDAT = "publishedAt";
    static final String KEY_CONTENT = "content";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_article, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivityForResult(new Intent(this, SettingsActivity.class), SETTINGS_REQUEST);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SETTINGS_REQUEST) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            notificationCBIsEnable = sharedPreferences.getBoolean("pref_checkbox", false);
            if (notificationCBIsEnable) {


                // delete
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
                String intervalStr = sp.getString("list_interval", "-1");
                intervalNotificationInSec = Integer.parseInt(intervalStr);

                String articleKindstr = sp.getString("list_kind", "0");
                int articleKind = Integer.parseInt(articleKindstr) ;


                //

                Intent intent = new Intent(MainActivity.this , NotificationSendReceiver.class);
                intent.putExtra("intervalInSec",intervalNotificationInSec );
                intent.putExtra("kindArticle", articleKind );

                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,
                        0 , intent, PendingIntent.FLAG_CANCEL_CURRENT);

                alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + intervalNotificationInSec*1000, pendingIntent );

                Toast.makeText(this, intervalNotificationInSec + "", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final int SETTINGS_REQUEST = 1;

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        sportRecyclerView = findViewById(R.id.news_sport_recycler);
        sportRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, sportRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, ShowArticleActivity.class);
                        intent.putExtra("title_extra", sportNewsArticles.get(position).getTitle());
                        intent.putExtra("content_extra", sportNewsArticles.get(position).getContent());
                        intent.putExtra("imgurl_extra", sportNewsArticles.get(position).getImgUrl());
                        intent.putExtra("url_extra", sportNewsArticles.get(position).getUrl());
                        intent.putExtra("author_extra", sportNewsArticles.get(position).getAutor());
                        intent.putExtra("publishedAt_extra", sportNewsArticles.get(position).getPublishedAt());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, newsArticles.get(position).getTitle() + ": " + newsArticles.get(position).getUrl());
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                    }
                })
        );
        newsRecyclerView = findViewById(R.id.news_recycler);
        newsRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, newsRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, ShowArticleActivity.class);
                        intent.putExtra("title_extra", newsArticles.get(position).getTitle());
                        intent.putExtra("content_extra", newsArticles.get(position).getContent());
                        intent.putExtra("imgurl_extra", newsArticles.get(position).getImgUrl());
                        intent.putExtra("url_extra", newsArticles.get(position).getUrl());
                        intent.putExtra("author_extra", newsArticles.get(position).getAutor());
                        intent.putExtra("publishedAt_extra", newsArticles.get(position).getPublishedAt());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, newsArticles.get(position).getTitle() + ": " + newsArticles.get(position).getUrl());
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                    }
                })
        );
        newsRecyclerView.setHasFixedSize(true);
        newsRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        sportRecyclerView.setHasFixedSize(true);
        sportRecyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));

        if (Function.isNetworkAvailable(getApplicationContext())) {
            DownloadNews newsTask = new DownloadNews();
            newsTask.execute();
            DownloadSportNews sportNewsTask = new DownloadSportNews();
            sportNewsTask.execute();

        } else {
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
            xml = Function.excuteGet("https://newsapi.org/v2/everything?sources=" + NEWS_SOURCE + "&apiKey=" + API_KEY, urlParameters);
            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            if (xml.length() > 10) {
                try {
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        String autor = jsonObject.optString(KEY_AUTHOR).toString();
                        String title = jsonObject.optString(KEY_TITLE).toString();
                        String description = jsonObject.optString(KEY_DESCRIPTION).toString();
                        String url = jsonObject.optString(KEY_URL).toString();
                        String imgurl = jsonObject.optString(KEY_URLTOIMAGE).toString();
                        String date = jsonObject.optString(KEY_PUBLISHEDAT).toString();
                        String content = jsonObject.optString(KEY_CONTENT).toString();
                        newsArticles.add(new NewsArticle(autor, title, description, url, imgurl, date, content));
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }
                NewsArticleAdapter newsArticleAdapter = new NewsArticleAdapter(newsArticles);
                newsRecyclerView.setAdapter(newsArticleAdapter);


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

            } else {
                Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class DownloadSportNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            String xml = "";
            String urlParameters = "";
            xml = Function.excuteGet("https://newsapi.org/v2/everything?sources=" + SPORT_NEWS_SOURCE + "&apiKey=" + API_KEY, urlParameters);
            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            if (xml.length() > 10) {

                try {
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        String autor = jsonObject.optString(KEY_AUTHOR).toString();
                        String title = jsonObject.optString(KEY_TITLE).toString();
                        String description = jsonObject.optString(KEY_DESCRIPTION).toString();
                        String url = jsonObject.optString(KEY_URL).toString();
                        String imgurl = jsonObject.optString(KEY_URLTOIMAGE).toString();
                        String date = jsonObject.optString(KEY_PUBLISHEDAT).toString();
                        String content = jsonObject.optString(KEY_CONTENT).toString();
                        sportNewsArticles.add(new NewsArticle(autor, title, description, url, imgurl, date, content));
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }
                SportNewsArticleAdapter sportNewsArticleAdapter = new SportNewsArticleAdapter(sportNewsArticles);
                sportRecyclerView.setAdapter(sportNewsArticleAdapter);

            } else {
                Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }


    }


    public  void showNotification(Context context, Intent intent) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String articleKindstr = sp.getString("list_kind", "0");
        int articleKind = Integer.parseInt(articleKindstr) ;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        //
        NewsArticle notifItem;
        if(articleKind == 0 ) { // news
             notifItem = newsArticles.get(0);
        }
        else {
            notifItem = sportNewsArticles.get(0);
        }
        //
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notifItem.getTitle())
                .setContentText(notifItem.getContent());

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }

    public NewsArticle getMostRecentNewsByType(int type){

        NewsArticle latest ;
        if(type == 0 ){
            latest = newsArticles.get(0);
        }
        else {
            latest = sportNewsArticles.get(0);
        }
        return latest;
    }

}