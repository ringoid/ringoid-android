/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterAdapterLikesImages;

import javax.inject.Inject;

public class ViewHolderItemLikesImages extends ViewHolderItemImagesLikeable
        implements View.OnClickListener {

    @Inject
    IPresenterAdapterLikesImages presenterAdapterLikesImages;

    ViewHolderItemLikesImages(ViewGroup parent) {
        super(parent, R.layout.view_image_likes);
        ApplicationRingoid.getComponent().inject(this);

    }

    @Override
    void setData(int position) {

    }

    public void setData(int adapterPosition, int position) {
        this.adapterPosition = adapterPosition;

        String url = presenterAdapterLikesImages.getUrl(adapterPosition, position);

        super.setData(url);

        setLiked(presenterAdapterLikesImages.isLiked(adapterPosition, position));
    }

    @Override
    public void onClick(View v) {
        if (v == itemView) {
            if (!isLikeable()) return;

            presenterAdapterLikesImages.onClickLike(adapterPosition, getAdapterPosition());
            super.onClickView(presenterAdapterLikesImages.isLiked(adapterPosition, getAdapterPosition()));
        }
        if (v.getId() == R.id.ivLike) {
            if (!isLikeable()) return;

            boolean isLiked = presenterAdapterLikesImages.onClickIconLike(adapterPosition, getAdapterPosition());
            super.onClickIconLike(isLiked);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v == itemView) {
            presenterAdapterLikesImages.onLongClick(adapterPosition, getAdapterPosition());
            return true;
        }
        return false;
    }

    @Override
    protected int getLikeEmptyRes() {
        return R.drawable.ic_favorite_border_red_24dp;
    }
}
