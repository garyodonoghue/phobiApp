package com.gary.spiders.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gary.spiders.R;
import com.gary.spiders.util.AlertUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class JigsawPuzzleGame extends BaseGame {

    private int numSelectedTiles = 0;
    private ImageButton imageButton1;
    private Drawable buttonImage1;
    private Drawable buttonImage2;
    int imageResourceId;
    List<Bitmap> correctImageOrder;

    int bonusPoints = 10;

    ImageButton topLeft;
    ImageButton topCenter;
    ImageButton topRight;

    ImageButton centerLeft;
    ImageButton centerCenter;
    ImageButton centerRight;

    ImageButton bottomLeft;
    ImageButton bottomCenter;
    ImageButton bottomRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jigsaw_puzzle_game);

        final TextView textView = (TextView) findViewById(R.id.jigsawCountdownTimer);
        super.setupGameTimer(textView, this);

        GameResourceLoader resourceLoader = new GameResourceLoader(this);
        imageResourceId = resourceLoader.getResource(category);

        topLeft = (ImageButton) findViewById(R.id.top_left);
        topCenter = (ImageButton) findViewById(R.id.top_center);
        topRight = (ImageButton) findViewById(R.id.top_right);

        centerLeft = (ImageButton) findViewById(R.id.center_left);
        centerCenter = (ImageButton) findViewById(R.id.center_center);
        centerRight = (ImageButton) findViewById(R.id.center_right);

        bottomLeft = (ImageButton) findViewById(R.id.bottom_left);
        bottomCenter = (ImageButton) findViewById(R.id.bottom_center);
        bottomRight = (ImageButton) findViewById(R.id.bottom_right);

        // Divide up image into 9 pieces and assign to imageButton segments
        ImageView imageView = new ImageView(this);
        Bitmap image = BitmapFactory.decodeResource(getResources(), imageResourceId);
        Bitmap resizedImage = Bitmap.createScaledBitmap(image, 300, 300, false);
        imageView.setImageBitmap(resizedImage);

        List<Bitmap> splitImages = this.splitImage(imageView, 9);
        correctImageOrder = splitImages;

        topLeft.setImageBitmap(splitImages.get(1));
        topCenter.setImageBitmap(splitImages.get(3));
        topRight.setImageBitmap(splitImages.get(5));

        centerLeft.setImageBitmap(splitImages.get(7));
        centerCenter.setImageBitmap(splitImages.get(0));
        centerRight.setImageBitmap(splitImages.get(4));

        bottomLeft.setImageBitmap(splitImages.get(2));
        bottomCenter.setImageBitmap(splitImages.get(6));
        bottomRight.setImageBitmap(splitImages.get(8));
    }

    public void tileClicked(View v) {

        if (numSelectedTiles == 0) {
            imageButton1 = (ImageButton) v;
            buttonImage1 = imageButton1.getDrawable();
            imageButton1.setColorFilter(Color.argb(100, 255, 255, 255), PorterDuff.Mode.LIGHTEN); // White Tint

            numSelectedTiles++;
        } else if (numSelectedTiles == 1) {
            ImageButton imageButton2 = (ImageButton) v;
            buttonImage2 = imageButton2.getDrawable();
            imageButton2.setColorFilter(Color.argb(100, 255, 255, 255), PorterDuff.Mode.LIGHTEN); // White Tint

            imageButton1.setImageDrawable(buttonImage2);
            imageButton2.setImageDrawable(buttonImage1);

            this.bonusPoints = bonusPoints - 1;
            if(this.bonusPoints < 0){
                this.bonusPoints = 0;
            }
            super.setBonusPoints(this.bonusPoints);

            //check if each of the tiles contains the correct image - if so, complete the game
            boolean solved = checkIfSolved();
            if(solved) {
                AlertDialog alertDialog = AlertUtility.createGameCompletedAlert(JigsawPuzzleGame.this);
                super.stopTimer();
                alertDialog.show();
            }
            
            imageButton1.clearColorFilter();
            imageButton2.clearColorFilter();

            buttonImage1 = null;
            imageButton1 = null;
            buttonImage2 = null;

            numSelectedTiles = 0;
        }
    }

    private boolean checkIfSolved() {
        Bitmap drawable1 = ((BitmapDrawable) topLeft.getDrawable()).getBitmap();
        Bitmap drawable2 = ((BitmapDrawable) topCenter.getDrawable()).getBitmap();
        Bitmap drawable3 = ((BitmapDrawable) topRight.getDrawable()).getBitmap();

        Bitmap drawable4 = ((BitmapDrawable) centerLeft.getDrawable()).getBitmap();
        Bitmap drawable5 = ((BitmapDrawable) centerCenter.getDrawable()).getBitmap();
        Bitmap drawable6 = ((BitmapDrawable) centerRight.getDrawable()).getBitmap();

        Bitmap drawable7 = ((BitmapDrawable) bottomLeft.getDrawable()).getBitmap();
        Bitmap drawable8 = ((BitmapDrawable) bottomCenter.getDrawable()).getBitmap();
        Bitmap drawable9 = ((BitmapDrawable) bottomRight.getDrawable()).getBitmap();

        boolean solved = false;

        if (drawable1.sameAs(correctImageOrder.get(0))) {
            if (drawable2.sameAs(correctImageOrder.get(1))) {
                if (drawable3.sameAs(correctImageOrder.get(2))) {
                    if (drawable4.sameAs(correctImageOrder.get(3))) {
                        if (drawable5.sameAs(correctImageOrder.get(4))) {
                            if (drawable6.sameAs(correctImageOrder.get(5))) {
                                if (drawable7.sameAs(correctImageOrder.get(6))) {
                                    if (drawable8.sameAs(correctImageOrder.get(7))) {
                                        if (drawable9.sameAs(correctImageOrder.get(8))) {
                                            solved = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return solved;
    }


    private List<Bitmap> splitImage(ImageView image, int chunkNumbers) {

        //For the number of rows and columns of the grid to be displayed
        int rows, cols;

        //For height and width of the small image chunks
        int chunkHeight, chunkWidth;

        //To store all the small image chunks in bitmap format in this list
        List<Bitmap> chunkedImages = new ArrayList<>(chunkNumbers);

        //Getting the scaled bitmap of the source image
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap picture = drawable.getBitmap();

        Bitmap[] images = new Bitmap[9];

        Bitmap bitmap1 = Bitmap.createBitmap(picture, 0, 0, picture.getWidth() / 3, picture.getHeight() / 3);
        images[0] = addWhiteBorder(bitmap1, 2);
        
        Bitmap bitmap2 = Bitmap.createBitmap(picture, picture.getWidth() / 3, 0, picture.getWidth() / 3, picture.getHeight() / 3);
        images[1] = addWhiteBorder(bitmap2, 2);

        Bitmap bitmap3 = Bitmap.createBitmap(picture, 2 * picture.getWidth() / 3, 0, picture.getWidth() / 3, picture.getHeight() / 3);
        images[2] = addWhiteBorder(bitmap3, 2);

        Bitmap bitmap4  = Bitmap.createBitmap(picture, 0, picture.getHeight() / 3, picture.getWidth() / 3, picture.getHeight() / 3);
        images[3] = addWhiteBorder(bitmap4, 2);

        Bitmap bitmap5  = Bitmap.createBitmap(picture, picture.getWidth() / 3, picture.getHeight() / 3, picture.getWidth() / 3, picture.getHeight() / 3);
        images[4] = addWhiteBorder(bitmap5, 2);

        Bitmap bitmap6  = Bitmap.createBitmap(picture, 2 * picture.getWidth() / 3, picture.getHeight() / 3, picture.getWidth() / 3, picture.getHeight() / 3);
        images[5] = addWhiteBorder(bitmap6, 2);

        Bitmap bitmap7  = Bitmap.createBitmap(picture, 0, 2 * picture.getHeight() / 3, picture.getWidth() / 3, picture.getHeight() / 3);
        images[6] = addWhiteBorder(bitmap7, 2);

        Bitmap bitmap8  = Bitmap.createBitmap(picture, picture.getWidth() / 3, 2 * picture.getHeight() / 3, picture.getWidth() / 3, picture.getHeight() / 3);
        images[7] = addWhiteBorder(bitmap8, 2);

        Bitmap bitmap9  = Bitmap.createBitmap(picture, 2 * picture.getWidth() / 3, 2 * picture.getHeight() / 3, picture.getWidth() / 3, picture.getHeight() / 3);
        images[8] = addWhiteBorder(bitmap9, 2);

        return Arrays.asList(images);
    }

    private Bitmap addWhiteBorder(Bitmap bmp, int borderSize) {
      Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2, bmp.getHeight() + borderSize * 2, bmp.getConfig());
          Canvas canvas = new Canvas(bmpWithBorder);
          canvas.drawColor(Color.BLACK);
          canvas.drawBitmap(bmp, borderSize, borderSize, null);
          return bmpWithBorder;
    }

    public void giveUp(View v){
        super.giveUp(v);
    }
}