package com.zdu.simplenewsdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zdu.simplenewsdemo.R;
import com.zdu.simplenewsdemo.bean.NewsBean;

import java.util.ArrayList;

/**
 * Created by duzongning on 2016/8/15.
 */
public class NewsListAdapter extends RecyclerView.Adapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private ArrayList<NewsBean.NewsEntity> list;

    public boolean isShowFooter() {
        return mShowFooter;
    }

    public void setShowFooter(boolean showFooter) {
        mShowFooter = showFooter;
    }

    private boolean mShowFooter;

    public NewsListAdapter(ArrayList<NewsBean.NewsEntity> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (!mShowFooter)
            return TYPE_ITEM;
        if (position + 1 == getItemCount())
            return TYPE_FOOTER;
        else
            return TYPE_ITEM;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            NewsBean.NewsEntity newsEntity = list.get(position);
            if (newsEntity == null) {
                return;
            }
            Glide.with(itemViewHolder.mImageView.getContext()).load(newsEntity.getImgsrc()).crossFade().into(itemViewHolder.mImageView);
            itemViewHolder.mTitle.setText(newsEntity.getTitle());
            itemViewHolder.mContent.setText(newsEntity.getDigest());
        }
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter ? 1 : 0;
        if (list == null) {
            return begin;
        }
        return list.size() + begin;
    }

    /**
     * 列表项
     */
    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageView;
        TextView mTitle;
        TextView mContent;

        ItemViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.ivNews);
            mTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mContent = (TextView) itemView.findViewById(R.id.tvDesc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    /**
     * 底部加载项
     */
    private class FooterViewHolder extends RecyclerView.ViewHolder {

        FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    private ItemClickListener mItemClickListener;

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
