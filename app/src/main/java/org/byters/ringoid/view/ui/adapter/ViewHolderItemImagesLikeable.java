package org.byters.ringoid.view.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import org.byters.ringoid.R;
import org.byters.ringoid.view.ui.util.AnimationLike;
import org.byters.ringoid.view.ui.util.GlideApp;

abstract class ViewHolderItemImagesLikeable extends ViewHolderBase
        implements View.OnClickListener {

    private AnimationLike animationLike;
    private View ivLikeAnimated;
    private ImageView ivItem, ivLike;

    ViewHolderItemImagesLikeable(ViewGroup container, int layoutRes) {
        super(container, layoutRes);
        ivLikeAnimated = itemView.findViewById(R.id.ivLikeAnimated);
        ivItem = itemView.findViewById(R.id.ivContent);
        ivLike = itemView.findViewById(R.id.ivLike);

        itemView.setOnClickListener(this);
    }


    void showLikeAnimationSmall(boolean liked) {
        setLiked(liked);

        Animation animationScale = new ScaleAnimation(0.7f, 1f, 0.7f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animationScale.setDuration(250);
        animationScale.setInterpolator(new OvershootInterpolator());
        ivLike.startAnimation(animationScale);
    }


    void showLikeAnimation() {
        if (animationLike != null)
            animationLike.cancel();
        animationLike = new AnimationLike(ivLikeAnimated);
        animationLike.show();
    }

    public void setData(String url) {

        ivItem.setImageDrawable(null);

        if (!TextUtils.isEmpty(url))
            GlideApp.with(itemView.getContext())
                    .load(url)
                    // .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .override(ivItem.getWidth(), ivItem.getHeight())
                    .centerCrop()
                    .into(ivItem);

        if (animationLike != null) animationLike.cancel();
    }


    void setLiked(boolean isLikes) {
        ivLike.setImageResource(isLikes
                ? R.drawable.ic_favorite_red_24dp
                : R.drawable.ic_favorite_border_white_24dp);
    }

    public void onClickView(boolean liked) {
        showLikeAnimationSmall(liked);
        showLikeAnimation();
    }
}
