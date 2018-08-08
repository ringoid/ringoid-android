package org.byters.ringoid.view.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterAdapterLikesImages;
import org.byters.ringoid.view.ui.dialog.DialogMenuImageOther;

import javax.inject.Inject;

public class ViewHolderItemLikesImages extends ViewHolderItemImagesLikeable
        implements View.OnClickListener {

    @Inject
    IPresenterAdapterLikesImages presenterAdapterLikesImages;

    private DialogMenuImageOther dialogMenuRank;

    private int adapterPosition;

    ViewHolderItemLikesImages(ViewGroup parent) {
        super(parent, R.layout.view_image_likes);
        ApplicationRingoid.getComponent().inject(this);

        itemView.findViewById(R.id.tvMenu).setOnClickListener(this);
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
    void setLiked(boolean isLikes) {
        ivLike.setImageResource(isLikes
                ? R.drawable.ic_favorite_red_24dp
                : R.drawable.ic_favorite_border_red_24dp);
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
            presenterAdapterLikesImages.onClickLike(adapterPosition, getAdapterPosition());
            super.onClickView(presenterAdapterLikesImages.isLiked(adapterPosition, getAdapterPosition()));
        }
    }

}
