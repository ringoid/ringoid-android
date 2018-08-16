package com.ringoid.view.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterAdapterExploreImages;
import com.ringoid.view.ui.dialog.DialogMenuImageOther;

import javax.inject.Inject;

public class ViewHolderItemExploreImages extends ViewHolderItemImagesLikeable
        implements View.OnClickListener {

    @Inject
    IPresenterAdapterExploreImages presenterAdapterExploreImages;

    private DialogMenuImageOther dialogMenuRank;

    private int adapterPosition;

    ViewHolderItemExploreImages(ViewGroup parent) {
        super(parent, R.layout.view_image_explore);
        ApplicationRingoid.getComponent().inject(this);

        itemView.findViewById(R.id.tvMenu).setOnClickListener(this);
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
        if (v.getId() == R.id.tvMenu) {
            if (dialogMenuRank != null)
                dialogMenuRank.cancel();
            dialogMenuRank = new DialogMenuImageOther(itemView);
            dialogMenuRank.show();
        }
        if (v == itemView) {
            presenterAdapterExploreImages.onClickLike(adapterPosition, getAdapterPosition());
            super.onClickView(presenterAdapterExploreImages.isLiked(adapterPosition, getAdapterPosition()));
        }
    }
}
