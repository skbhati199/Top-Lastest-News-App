package com.computerprogramming.toplatestnews.actvities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.computerprogramming.toplatestnews.NewsApp;
import com.computerprogramming.toplatestnews.R;
import com.computerprogramming.toplatestnews.adapter.MyArticleRecyclerViewAdapter;
import com.computerprogramming.toplatestnews.api.WebAPI;
import com.computerprogramming.toplatestnews.fragments.ArticleFragment;
import com.computerprogramming.toplatestnews.model.Article;
import com.computerprogramming.toplatestnews.model.NewsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTopLastestNewsActivity extends AppCompatActivity {

    private static final String TAG = ListTopLastestNewsActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_top_lastest_news);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        getNewsArticles();


    }

    private void getNewsArticles() {
        WebAPI webAPIService = NewsApp.newInstance();
        Call<NewsModel> newsModelCall = webAPIService.getArticles();
        newsModelCall.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                if (response.body() != null) {
                    Log.i(TAG, "Response: " + response.message());
                    NewsModel model = response.body();
                    List<Article> mArticles = model.getArticles();
                    mAdapter = new MyArticleRecyclerViewAdapter(ListTopLastestNewsActivity.this,mArticles, new ArticleFragment.OnListFragmentInteractionListener() {
                        @Override
                        public void onListFragmentInteraction(int id,Article article) {
                            Intent intent = new Intent(ListTopLastestNewsActivity.this,LatestTopNewsActivity.class);
                            intent.putExtra("id",id);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(mAdapter);

                }
                else
                    Toast.makeText(ListTopLastestNewsActivity.this, "Error " +response.errorBody(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(ListTopLastestNewsActivity.this, "Error " +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
