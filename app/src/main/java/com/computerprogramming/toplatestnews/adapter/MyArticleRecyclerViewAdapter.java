package com.computerprogramming.toplatestnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.computerprogramming.toplatestnews.R;
import com.computerprogramming.toplatestnews.fragments.ArticleFragment.OnListFragmentInteractionListener;
import com.computerprogramming.toplatestnews.api.dummy.DummyContent.DummyItem;
import com.computerprogramming.toplatestnews.model.Article;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyArticleRecyclerViewAdapter extends RecyclerView.Adapter<MyArticleRecyclerViewAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Article> mListArticles;
    private final OnListFragmentInteractionListener mListener;

    public MyArticleRecyclerViewAdapter(Context context, List<Article> items, OnListFragmentInteractionListener listener) {
        this.mContext = context;
        mListArticles = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        holder.mItem = mListArticles.get(position);
        holder.mIdView.setText(mListArticles.get(position).getTitle());
        holder.mContentView.setText(mListArticles.get(position).getDescription());
        holder.mPublishAt.setText(mListArticles.get(position).getPublishedAt());
        Glide.with(mContext).load(mListArticles.get(position).getUrlToImage()).into(holder.mImageView);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.getAdapterPosition(),holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListArticles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Article mItem;
        public ImageView mImageView;
        public TextView mPublishAt;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.title);
            mContentView = (TextView) view.findViewById(R.id.content);
            mPublishAt = (TextView) view.findViewById(R.id.publish);
            mImageView = (ImageView) view.findViewById(R.id.imageurl);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
