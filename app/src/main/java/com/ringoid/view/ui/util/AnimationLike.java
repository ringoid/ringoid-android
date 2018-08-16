package com.ringoid.view.ui.util;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;

public class AnimationLike {

    private View view;
    private AnimationSet animation;

    public AnimationLike(View view) {
        this.view = view;

        view.setVisibility(View.VISIBLE);
        Animation animationAlphaIn = new AlphaAnimation(0.5f, 0.9f);
        animationAlphaIn.setDuration(250);

        Animation animationResize = new ScaleAnimation(0.5f, 1f, 0.5f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animationResize.setDuration(250);
        animationResize.setInterpolator(new OvershootInterpolator());

        Animation animationAlphaOut = new AlphaAnimation(0.9f, 0f);
        animationAlphaOut.setDuration(50);
        animationAlphaOut.setStartOffset(500);
        animationAlphaOut.setInterpolator(new DecelerateInterpolator());

        animation = new AnimationSet(false);
        animation.addAnimation(animationAlphaIn);
        animation.addAnimation(animationResize);
        animation.addAnimation(animationAlphaOut);
        animation.setFillAfter(true);
        animation.setAnimationListener(new AnimationListener());

    }

    public void cancel() {
        animation.cancel();
        view.clearAnimation();
        view.setVisibility(View.INVISIBLE);

    }

    public void show() {
        animation.reset();
        view.startAnimation(animation);
    }

    private class AnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            cancel();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
