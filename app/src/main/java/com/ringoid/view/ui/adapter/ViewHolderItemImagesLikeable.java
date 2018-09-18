/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterItemImageLikeable;
import com.ringoid.view.ui.dialog.DialogHiddenMode;
import com.ringoid.view.ui.dialog.callback.IDialogHiddenModeListener;
import com.ringoid.view.ui.util.AnimationLike;
import com.ringoid.view.ui.util.GlideApp;

import javax.inject.Inject;

public abstract class ViewHolderItemImagesLikeable extends ViewHolderBase
        implements View.OnClickListener, View.OnLongClickListener {

    @Inject
    IPresenterItemImageLikeable presenterItemImageLikeable;

    ImageView ivItem, ivLike;

    int adapterPosition;

    private ListenerDialogHiddenMode listenerDialogHiddenMode;
    private AnimationLike animationLike;
    private View ivLikeAnimated;

    private DialogHiddenMode dialogHiddenMode;

    ViewHolderItemImagesLikeable(ViewGroup container, int layoutRes) {
        super(container, layoutRes);
        ApplicationRingoid.getComponent().inject(this);

        ivLikeAnimated = itemView.findViewById(R.id.ivLikeAnimated);
        ivItem = itemView.findViewById(R.id.ivContent);
        ivLike = itemView.findViewById(R.id.ivLike);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

        itemView.findViewById(R.id.ivLike).setOnClickListener(this);
        listenerDialogHiddenMode = new ListenerDialogHiddenMode();

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
                : getLikeEmptyRes());
    }

    protected abstract int getLikeEmptyRes();

    void onClickView(boolean liked) {
        showLikeAnimationSmall(liked);
        showLikeAnimation();
    }

    void onClickIconLike(boolean isLiked) {
        if (isLiked)
            onClickView(true);
        else setLiked(false);
    }

    boolean isLikeable() {
        if (presenterItemImageLikeable.isUserNew()) {
            presenterItemImageLikeable.showDialogNoPhoto();
            return false;
        }

        if (!presenterItemImageLikeable.isHiddenMode()) return true;
        if (!presenterItemImageLikeable.isDialogHiddenShow()) return false;

        if (dialogHiddenMode != null) dialogHiddenMode.cancel();

        dialogHiddenMode = new DialogHiddenMode(itemView.getContext(), listenerDialogHiddenMode);
        dialogHiddenMode.show();


        return false;
    }

    private class ListenerDialogHiddenMode implements IDialogHiddenModeListener {

        @Override
        public void onSelectOK(boolean b) {
            presenterItemImageLikeable.onClickOK(b);
        }

        @Override
        public void onSelectSettings(boolean b) {
            presenterItemImageLikeable.onClickSettings(b);
        }
    }
}
