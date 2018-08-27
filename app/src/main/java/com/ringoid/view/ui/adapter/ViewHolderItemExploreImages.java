/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterAdapterExploreImages;

import javax.inject.Inject;

public class ViewHolderItemExploreImages extends ViewHolderItemImagesLikeable
        implements View.OnClickListener {

    @Inject
    IPresenterAdapterExploreImages presenterAdapterExploreImages;

    ViewHolderItemExploreImages(ViewGroup parent) {
        super(parent, R.layout.view_image_explore);
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    void setData(int position) {

    }

    public void setData(int adapterPosition, int position) {
        this.adapterPosition = adapterPosition;

        String url = presenterAdapterExploreImages.getUrl(adapterPosition, position);

        super.setData(url);

        setLiked(presenterAdapterExploreImages.isLiked(adapterPosition, position));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == itemView) {
            if (!isLikeable()) return;

            presenterAdapterExploreImages.onClickLike(adapterPosition, getAdapterPosition());
            super.onClickView(presenterAdapterExploreImages.isLiked(adapterPosition, getAdapterPosition()));
        }
        if (v.getId() == R.id.ivLike) {
            if (!isLikeable()) return;

            boolean isLiked = presenterAdapterExploreImages.onClickIconLike(adapterPosition, getAdapterPosition());
            super.onClickIconLike(isLiked);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v == itemView) {
            presenterAdapterExploreImages.onLongClick(adapterPosition, getAdapterPosition());
            return true;
        }
        return false;
    }
}
