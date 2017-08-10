package com.computerprogramming.toplatestnews.actvities;

import android.app.Application;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.computerprogramming.toplatestnews.NewsApp;
import com.computerprogramming.toplatestnews.R;
import com.computerprogramming.toplatestnews.api.WebAPI;
import com.computerprogramming.toplatestnews.model.Article;
import com.computerprogramming.toplatestnews.model.NewsModel;

import java.security.acl.LastOwnerException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LatestTopNewsActivity extends AppCompatActivity {

    private static final String TAG = LatestTopNewsActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private NewsApp mNewsApp;
    private int id = 0;
    private TextView description;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_top_news);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        description = (TextView) findViewById(R.id.tvDescription);
        imageView = (ImageView) findViewById(R.id.imageNews);
        getSupportActionBar().setSubtitle("SubTitle");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (getIntent().getExtras() != null) {
            id = Integer.valueOf(getIntent().getIntExtra("id", 0));
        }
        imageView.setEnabled(false);
        getNewsArticles(id);




    }

    private void rxJava(ImageView view) {

        final Observable<Integer> serverDownloadObservable = Observable.create(emitter -> {
            SystemClock.sleep(1000);
            emitter.onNext(5);
            emitter.onComplete();
        });
        serverDownloadObservable.
                observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                subscribe(integer -> {
                   updateTheUserInterface(integer);
                    view.setEnabled(true);
                });
    }

    private void updateTheUserInterface(Integer integer) {
        Log.d(TAG, "Integer: " + integer);
    }

    private void getNewsArticles(final int id) {
        WebAPI webAPIService = NewsApp.newInstance();
        Call<NewsModel> newsModelCall = webAPIService.getArticles();
        newsModelCall.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                if (response.body() != null) {
                    Log.i(TAG, "Response: " + response.message());
                    NewsModel model = response.body();
                    List<Article> mArticles = model.getArticles();
                    Article article = mArticles.get(id);
                    Log.i(TAG, "Title: " +article.getTitle());
                    mToolbar.setTitle(article.getTitle());
                    Log.i(TAG, "Description: " +article.getDescription());
                    description.setText(article.getDescription());
                    Log.i(TAG, "url: " +article.getUrl());
                    Log.i(TAG, "imageUrl: " +article.getUrlToImage());
                    rxJava(imageView);
                    Glide.with(LatestTopNewsActivity.this).load(article.getUrlToImage()).into(imageView);
                    Log.i(TAG, "publishedAt: " +article.getPublishedAt());

                }
                else
                    Toast.makeText(LatestTopNewsActivity.this, "Error " +response.errorBody(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(LatestTopNewsActivity.this, "Error " +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
