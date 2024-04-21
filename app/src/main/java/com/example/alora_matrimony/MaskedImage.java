package com.example.alora_matrimony;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class MaskedImage extends AppCompatImageView {

    private Bitmap maskBitmap;

    public MaskedImage(Context context) {
        super(context);
        init();
    }

    public MaskedImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MaskedImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Load the image and set it as the mask bitmap
        Glide.with(getContext())
                .asBitmap()
                .load(R.drawable.deshboard_profile)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        setMaskBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                        // Do nothing
                    }
                });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (maskBitmap != null) {
            Paint paint = new Paint();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawBitmap(maskBitmap, 0, 0, paint);
        }
    }

    public void setMaskBitmap(Bitmap maskBitmap) {
        this.maskBitmap = maskBitmap;
        invalidate();
    }
}
