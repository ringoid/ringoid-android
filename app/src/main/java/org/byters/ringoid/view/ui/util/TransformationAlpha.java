package org.byters.ringoid.view.ui.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class TransformationAlpha extends BitmapTransformation {
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {

        int destMinEdge = Math.min(outWidth, outHeight);
        float radius = destMinEdge / 2f;

        int srcWidth = toTransform.getWidth();
        int srcHeight = toTransform.getHeight();

        float scaleX = destMinEdge / (float) srcWidth;
        float scaleY = destMinEdge / (float) srcHeight;
        float maxScale = Math.max(scaleX, scaleY);

        float scaledWidth = maxScale * srcWidth;
        float scaledHeight = maxScale * srcHeight;
        float left = (destMinEdge - scaledWidth) / 2f;
        float top = (destMinEdge - scaledHeight) / 2f;

        RectF destRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

        Bitmap newBitmap = pool.get(destMinEdge, destMinEdge, toTransform.getConfig());

        Paint alphaPaint = new Paint();
        alphaPaint.setAlpha(30);
        alphaPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        Canvas canvas = new Canvas(newBitmap);
        canvas.drawCircle(radius, radius, radius, new Paint());
        canvas.drawBitmap(toTransform, null, destRect, alphaPaint);
        canvas.setBitmap(null);

        return newBitmap;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        //  messageDigest.update("alpha transformation".getBytes());
    }
}
