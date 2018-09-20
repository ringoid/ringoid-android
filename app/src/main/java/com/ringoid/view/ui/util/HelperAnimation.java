package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import com.github.jinatonic.confetti.ConfettiManager;
import com.github.jinatonic.confetti.ConfettiSource;
import com.github.jinatonic.confetti.ConfettoGenerator;
import com.github.jinatonic.confetti.confetto.BitmapConfetto;
import com.github.jinatonic.confetti.confetto.Confetto;
import com.ringoid.ApplicationRingoid;
import com.ringoid.R;

import java.util.Random;

import javax.inject.Inject;

public class HelperAnimation implements IHelperAnimation {

    @Inject
    Random random;
    private ParticleGenerator generatorLikes, generatorMessages, generatorMatches;
    private ConfettiSource source;

    public HelperAnimation() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void showPopupLikes(ViewGroup container) {
        if (generatorLikes == null) {
            Bitmap bitmap = getBitmap(container.getContext(), R.drawable.ic_favorite_red_24dp);
            generatorLikes = new ParticleGenerator(bitmap);
        }

        showAnimation(container, generatorLikes);
    }

    private Bitmap getBitmap(Context context, int drawableRes) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableRes);
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && drawable instanceof VectorDrawable) {
            return getBitmap((VectorDrawable) drawable);
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }

    private Bitmap getBitmap(VectorDrawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private int getY(ViewGroup container) {
        return container.getHeight() - (int) container.getResources().getDimension(R.dimen.animation_y_delta);
    }

    @Override
    public void showPopupMessage(ViewGroup container) {
        if (generatorMessages == null) {
            Bitmap bitmap = getBitmap(container.getContext(), R.drawable.ic_chat_bubble_green_24dp);
            generatorMessages = new ParticleGenerator(bitmap);
        }

        showAnimation(container, generatorMessages);
    }


    @Override
    public void showPopupMatches(ViewGroup container) {
        if (generatorMatches == null) {
            Bitmap bitmap = getBitmap(container.getContext(), R.drawable.ic_match_red_24dp);
            generatorMatches = new ParticleGenerator(bitmap);
        }

        showAnimation(container, generatorMatches);
    }

    private int getDeltaX(Context context) {
        return (int) context.getResources().getDimension(R.dimen.dp12);
    }

    private void showAnimation(ViewGroup container, ParticleGenerator generatorLikes) {

        if (source == null)
            source = new ConfettiSource(
                    0,
                    getY(container));

        new ConfettiManager(container.getContext(), generatorLikes, source, container)

                .setNumInitialCount(1)

                .setVelocityX(100, 50)
                .setAccelerationX(-50, 25)
                .setTargetVelocityX(0, 12.5f)

                .setVelocityY(-220, 80)
                .setTTL(3000)
                .enableFadeOut(new CustomInterpolator(random.nextFloat() / 10f))
                .animate();
    }

    private class ParticleGenerator implements ConfettoGenerator {
        private Bitmap bitmap;

        ParticleGenerator(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        public Confetto generateConfetto(Random random) {
            return new BitmapConfetto(bitmap);
        }
    }

    private class CustomInterpolator implements Interpolator {
        private float randomStart;

        CustomInterpolator(float random) {
            this.randomStart = random;
        }

        @Override
        public float getInterpolation(float input) {
            return Math.max(0, 1 - randomStart - input);
        }
    }
}
