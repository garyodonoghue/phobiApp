package com.gary.spiders.game;


import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.gary.spiders.R;
import com.gary.spiders.util.AlertUtility;

/**
 * Created by Gary on 18/09/2017.
 */

public class FocusImageGame extends BaseGame {

    private float focusFactor = 0.1f;
    ImageView imageView = null;
    Bitmap spiderImageBitmap = null;
    private static volatile Matrix sScaleMatrix;
    private static volatile int sDefaultDensity = -1;
    int imageResourceId;
    int progress;
    int bonusPoints = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_focus);

        GameResourceLoader resourceLoader = new GameResourceLoader(this);
        imageResourceId = resourceLoader.getResource(category);

        imageView = (ImageView) findViewById(R.id.focus_image);
        imageView.setImageResource(imageResourceId);
        spiderImageBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar_focus);
        progressBar.setMax(19);

        updateImageFocus(focusFactor);

        final Button button = (Button) findViewById(R.id.button_focus);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                progressBar.incrementProgressBy(1);
                progress = progress + 1;
                incrementFocusFactor();
                focusFactor = getFocusFactor();

                updateImageFocus(focusFactor);

                if(progress > progressBar.getMax()){
                    AlertDialog alertDialog = AlertUtility.createGameCompletedAlert(FocusImageGame.this);
                    alertDialog.show();
                }
            }
        });
    }

    private void updateImageFocus(float focusFactor) {
        imageView.setImageDrawable(builtInPixelization(focusFactor, spiderImageBitmap));
    }

    /**
     * This method of image pixelization utilizes the bitmap scaling operations built
     * into the framework. By downscaling the bitmap and upscaling it back to its
     * original size (while setting the filter flag to false), the same effect can be
     * achieved with much better performance.
     */
    public BitmapDrawable builtInPixelization(float pixelizationFactor, Bitmap bitmap) {
        float width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float downScaleFactorWidth = (float)(pixelizationFactor * width);
        downScaleFactorWidth = downScaleFactorWidth > 0 ? downScaleFactorWidth : 1;
        int downScaleFactorHeight = (int)(pixelizationFactor * height);
        downScaleFactorHeight = downScaleFactorHeight > 0 ? downScaleFactorHeight : 1;
        float downScaledWidth =  width / downScaleFactorWidth;
        float downScaledHeight = height / downScaleFactorHeight;
        Bitmap pixelatedBitmap = createScaledBitmap(bitmap, downScaledWidth,
                downScaledHeight, false);

        Bitmap upscaled = createScaledBitmap(pixelatedBitmap, width, height, false);
        return new BitmapDrawable(getResources(), upscaled);

    }


    /**
     * Creates a new bitmap, scaled from an existing bitmap, when possible. If the
     * specified width and height are the same as the current width and height of
     * the source bitmap, the source bitmap is returned and no new bitmap is
     * created.
     *
     * @param src       The source bitmap.
     * @param dstWidth  The new bitmap's desired width.
     * @param dstHeight The new bitmap's desired height.
     * @param filter    true if the source should be filtered.
     * @return The new scaled bitmap or the source bitmap if no scaling is required.
     * @throws IllegalArgumentException if width is <= 0, or height is <= 0
     */
    public static Bitmap createScaledBitmap(Bitmap src, float dstWidth, float dstHeight,
                                            boolean filter) {
        Matrix m;
        synchronized (Bitmap.class) {
            // small pool of just 1 matrix
            m = sScaleMatrix;
            sScaleMatrix = null;
        }

        if (m == null) {
            m = new Matrix();
        }

        final int width = src.getWidth();
        final int height = src.getHeight();
        final float sx = dstWidth  / (float)width;
        final float sy = dstHeight / (float)height;
        m.setScale(sx, sy);
        Bitmap b = Bitmap.createBitmap(src, 0, 0, width, height, m, filter);

        synchronized (Bitmap.class) {
            // do we need to check for null? why not just assign everytime?
            if (sScaleMatrix == null) {
                sScaleMatrix = m;
            }
        }

        return b;
    }

    private float getFocusFactor(){
        return this.focusFactor;
    }

    float decrement = 0.02f;

    int round = 0;
    private void incrementFocusFactor(){
        if(round <= 5) {
            // round 1 to 5 should focus the image a lot,
            // then 6 to 10 should be minor increments, where the image is sharper
            // and the user is awarded more points
            this.decrement = 0.012f;
            this.focusFactor = this.focusFactor - this.decrement;
        }
        else {
            this.decrement = 0.00185f;
            this.focusFactor = this.focusFactor - this.decrement;
        }

        if(round > 14){
            this.bonusPoints++;
            super.bonusPoints = this.bonusPoints;
        }
        round++;
    }


    public void giveUp(View v){
        super.giveUp(v);
    }
}
