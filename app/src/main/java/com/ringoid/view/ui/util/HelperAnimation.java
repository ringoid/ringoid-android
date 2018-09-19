package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import com.github.jinatonic.confetti.ConfettiManager;
import com.github.jinatonic.confetti.ConfettiSource;
import com.github.jinatonic.confetti.ConfettoGenerator;
import com.github.jinatonic.confetti.confetto.BitmapConfetto;
import com.github.jinatonic.confetti.confetto.Confetto;
import com.ringoid.R;

import java.util.Random;

public class HelperAnimation implements IHelperAnimation {

    private ParticleGenerator generatorLikes, generatorMessages, generatorMatches;
    private ConfettiSource sourceLikes, sourceMessages, sourceMatches;

    @Override
    public void showPopupLikes(ViewGroup container) {
        if (generatorLikes == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(container.getResources(), R.drawable.ic_favorite_red_24dp);
            generatorLikes = new ParticleGenerator(bitmap);

            sourceLikes = new ConfettiSource(
                    container.getResources().getDisplayMetrics().widthPixels / 8 - getDeltaX(container.getContext()),
                    getY(container));
        }

        showAnimation(container, generatorLikes, sourceLikes);
    }

    private int getY(ViewGroup container) {
        return container.getHeight() - (int) container.getResources().getDimension(R.dimen.animation_y_delta);
    }

    @Override
    public void showPopupMessage(ViewGroup container) {
        if (generatorMessages == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(container.getResources(), R.drawable.ic_chat_bubble_green_24dp);
            generatorMessages = new ParticleGenerator(bitmap);

            sourceMessages = new ConfettiSource(
                    container.getResources().getDisplayMetrics().widthPixels / 8 * 5 - getDeltaX(container.getContext()),
                    getY(container));
        }

        showAnimation(container, generatorMessages, sourceMessages);
    }


    @Override
    public void showPopupMatches(ViewGroup container) {
        if (generatorMatches == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(container.getResources(), R.drawable.ic_match_red_24dp);
            generatorMatches = new ParticleGenerator(bitmap);

            sourceMatches = new ConfettiSource(
                    container.getResources().getDisplayMetrics().widthPixels / 8 * 3 - getDeltaX(container.getContext()),
                    getY(container));
        }

        showAnimation(container, generatorMatches, sourceMatches);
    }

    private int getDeltaX(Context context) {
        return (int) context.getResources().getDimension(R.dimen.dp12);
    }

    private void showAnimation(ViewGroup container, ParticleGenerator generatorLikes, ConfettiSource confettiSource) {
        new ConfettiManager(container.getContext(), generatorLikes, confettiSource, container)

                .setEmissionRate(10).setEmissionDuration(250) //todo replace with EMIT ONE PARTICLE method

                .setVelocityY(-100)
                .setTTL(3000)
                .enableFadeOut(new CustomInterpolator())
                .animate();
    }

    private class ParticleGenerator implements ConfettoGenerator {
        private Bitmap bitmap;

        public ParticleGenerator(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        public Confetto generateConfetto(Random random) {
            return new BitmapConfetto(bitmap);
        }
    }

    private class CustomInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float input) {
            return 1 - input;
        }
    }
}
