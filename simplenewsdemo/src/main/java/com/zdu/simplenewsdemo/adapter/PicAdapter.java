package com.zdu.simplenewsdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zdu.simplenewsdemo.R;
import com.zdu.simplenewsdemo.bean.ImageBean;

import java.util.ArrayList;

/**
 * Created by duzongning on 2016/9/6.
 */
public class PicAdapter extends RecyclerView.Adapter<PicAdapter.PicViewHolder> {

    private ArrayList<ImageBean> list;

    public PicAdapter(ArrayList<ImageBean> list) {
        this.list = list;
    }

    @Override
    public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new PicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PicViewHolder holder, int position) {
        ImageBean imageBean = list.get(position);
        holder.mTitle.setText(imageBean.getTitle());
        Glide.with(holder.mImageView.getContext()).load(imageBean.getThumburl()).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PicViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        ImageView mImageView;

        public PicViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mImageView = (ImageView) itemView.findViewById(R.id.ivImage);
        }
    }
}
