package org.byters.ringoid.view.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.target.Target;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterAdapterLikesImages;
import org.byters.ringoid.view.ui.dialog.DialogMenuRank;
import org.byters.ringoid.view.ui.util.AnimationLike;
import org.byters.ringoid.view.ui.util.GlideApp;

import javax.inject.Inject;

public class ViewHolderItemLikesImages  extends ViewHolderBase
        implements View.OnClickListener {

    @Inject
    IPresenterAdapterLikesImages presenterAdapterLikesImages;

    private View ivLikeAnimated;
    private ImageView ivItem, ivLike;
    private DialogMenuRank dialogMenuRank;
    private AnimationLike animationLike;

    private int adapterPosition;

    public ViewHolderItemLikesImages(ViewGroup parent) {
        super(parent, R.layout.view_image_likes);
        ApplicationRingoid.getComponent().inject(this);

        ivLikeAnimated = itemView.findViewById(R.id.ivLikeAnimated);
        ivItem = itemView.findViewById(R.id.ivContent);
        ivLike = itemView.findViewById(R.id.ivLike);
        itemView.findViewById(R.id.tvMenu).setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

    @Override
    void setData(int position) {

    }

    public void setData(int adapterPosition, int position) {
        this.adapterPosition = adapterPosition;

        String url = presenterAdapterLikesImages.getUrl(adapterPosition, position);

        ivItem.setImageDrawable(null);

        if (!TextUtils.isEmpty(url))
            GlideApp.with(itemView.getContext())
                    .load(url)
                    //.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .override(ivItem.getWidth(),ivItem.getHeight())
                    .centerCrop()
                    .into(ivItem);

        setLiked(position);

        if (animationLike != null) animationLike.cancel();
    }

    private void setLiked(int position) {
        ivLike.setImageResource(presenterAdapterLikesImages.isLiked(adapterPosition, position)
                ? R.drawable.ic_favorite_red_24dp
                : R.drawable.ic_favorite_border_white_24dp);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvMenu) {
            if (dialogMenuRank != null)
                dialogMenuRank.cancel();
            dialogMenuRank = new DialogMenuRank(itemView.getContext());
            dialogMenuRank.show();
        }
        if (v == itemView) {
            showLikeAnimation();
            presenterAdapterLikesImages.onClickLike(adapterPosition, getAdapterPosition());
            setLiked(getAdapterPosition());
        }
    }

    private void showLikeAnimation() {
        if (animationLike != null)
            animationLike.cancel();
        animationLike = new AnimationLike(ivLikeAnimated);
        animationLike.show();
    }
}
