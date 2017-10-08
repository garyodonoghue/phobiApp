package com.gary.spiders.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.gary.spiders.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class JigsawPuzzleGame extends Game {

    private int numSelectedTiles = 0;
    private ImageButton imageButton1;
    private Drawable buttonImage1;
    private Drawable buttonImage2;
    int imageResourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jigsaw_puzzle_game);

        GameResourceLoader resourceLoader = new GameResourceLoader(this);
        imageResourceId = resourceLoader.getResource(category);

        ImageButton topLeft = (ImageButton) findViewById(R.id.top_left);
        ImageButton topCenter = (ImageButton) findViewById(R.id.top_center);
        ImageButton topRight = (ImageButton) findViewById(R.id.top_right);

        ImageButton centerLeft = (ImageButton) findViewById(R.id.center_left);
        ImageButton centerCenter = (ImageButton) findViewById(R.id.center_center);
        ImageButton centerRight = (ImageButton) findViewById(R.id.center_right);

        ImageButton bottomLeft = (ImageButton) findViewById(R.id.bottom_left);
        ImageButton bottomCenter = (ImageButton) findViewById(R.id.bottom_center);
        ImageButton bottomRight = (ImageButton) findViewById(R.id.bottom_right);

        // Divide up image into 9 pieces and assign to imageButton segments
        ImageView imageView = new ImageView(this);
        Bitmap image = BitmapFactory.decodeResource(getResources(),imageResourceId);
        Bitmap resizedImage = Bitmap.createScaledBitmap(image, 300, 300, false);
        imageView.setImageBitmap(resizedImage);

        List<Bitmap> splitImages = this.splitImage(imageView, 9);

        topLeft.setImageBitmap(splitImages.get(0));
        topCenter.setImageBitmap(splitImages.get(1));
        topRight.setImageBitmap(splitImages.get(2));

        centerLeft.setImageBitmap(splitImages.get(3));
        centerCenter.setImageBitmap(splitImages.get(4));
        centerRight.setImageBitmap(splitImages.get(5));

        bottomLeft.setImageBitmap(splitImages.get(6));
        bottomCenter.setImageBitmap(splitImages.get(7));
        bottomRight.setImageBitmap(splitImages.get(8));


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


    private List<Bitmap> splitImage(ImageView image, int chunkNumbers) {

        //For the number of rows and columns of the grid to be displayed
        int rows,cols;

        //For height and width of the small image chunks
        int chunkHeight,chunkWidth;

        //To store all the small image chunks in bitmap format in this list
        List<Bitmap> chunkedImages = new ArrayList<>(chunkNumbers);

        //Getting the scaled bitmap of the source image
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap picture = drawable.getBitmap();

        Bitmap[] imgs = new Bitmap[9];
        imgs[0] = Bitmap.createBitmap(picture, 0,                      0, picture.getWidth()/3, picture.getHeight()/3);
        imgs[1] = Bitmap.createBitmap(picture, picture.getWidth()/3,   0, picture.getWidth()/3, picture.getHeight()/3);
        imgs[2] = Bitmap.createBitmap(picture, 2*picture.getWidth()/3, 0, picture.getWidth()/3, picture.getHeight()/3);

        imgs[3] = Bitmap.createBitmap(picture, 0,                      picture.getHeight()/3, picture.getWidth()/3, picture.getHeight()/3);
        imgs[4] = Bitmap.createBitmap(picture, picture.getWidth()/3,   picture.getHeight()/3, picture.getWidth()/3, picture.getHeight()/3);
        imgs[5] = Bitmap.createBitmap(picture, 2*picture.getWidth()/3, picture.getHeight()/3, picture.getWidth()/3, picture.getHeight()/3);

        imgs[6] = Bitmap.createBitmap(picture, 0,                      2*picture.getHeight()/3, picture.getWidth()/3, picture.getHeight()/3);
        imgs[7] = Bitmap.createBitmap(picture, picture.getWidth()/3,   2*picture.getHeight()/3, picture.getWidth()/3, picture.getHeight()/3);
        imgs[8] = Bitmap.createBitmap(picture, 2*picture.getWidth()/3, 2*picture.getHeight()/3, picture.getWidth()/3, picture.getHeight()/3);

        return Arrays.asList(imgs);
    }
}
