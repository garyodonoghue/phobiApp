package com.gary.spiders.game;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.gary.spiders.R;

import java.util.ArrayList;
import java.util.List;


public class JigsawPuzzleGame extends Game {

    private int numSelectedTiles = 0;
    private ImageButton imageButton1;
    private Drawable buttonImage1;
    private Drawable buttonImage2;

    Bitmap spiderImageBitmap = null;
    int imageResourceId;

    GameGenerator.Category category;
    boolean initialAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jigsaw_puzzle_game);

        GameResourceLoader resourceLoader = new GameResourceLoader(this);
        imageResourceId = resourceLoader.getResource(category);
    }

    public void tileClicked(View v){
        if(numSelectedTiles == 0){
            imageButton1 = (ImageButton) v;
            buttonImage1 = imageButton1.getDrawable();
            numSelectedTiles++;
        }
        else if(numSelectedTiles == 1){
            ImageButton imageButton2 = (ImageButton) v;
            buttonImage2 = imageButton2.getDrawable();

            imageButton1.setImageDrawable(buttonImage2);
            imageButton2.setImageDrawable(buttonImage1);

            //check if each of the tiles contains the correct image - if so, complete the game
            //if(findViewById(R.id.imageButton1).getDrawable() == R.id.image1)


            buttonImage1 = null;
            imageButton1 = null;
            imageButton2 = null;
            buttonImage2 = null;

            numSelectedTiles = 0;
        }
    }


    private void splitImage(ImageView image, int chunkNumbers) {

        //For the number of rows and columns of the grid to be displayed
        int rows,cols;

        //For height and width of the small image chunks
        int chunkHeight,chunkWidth;

        //To store all the small image chunks in bitmap format in this list
        List<Bitmap> chunkedImages = new ArrayList<>(chunkNumbers);

        //Getting the scaled bitmap of the source image
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        rows = cols = (int) Math.sqrt(chunkNumbers);
        chunkHeight = bitmap.getHeight()/rows;
        chunkWidth = bitmap.getWidth()/cols;

        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;
        for(int x=0; x<rows; x++){
            int xCoord = 0;
            for(int y=0; y<cols; y++){
                chunkedImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }
    }
}
