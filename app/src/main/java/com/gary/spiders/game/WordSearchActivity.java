package com.gary.spiders.game;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.gary.spiders.R;
import com.gary.spiders.util.AlertUtility;
import com.gary.spiders.util.Grid;
import com.gary.spiders.util.WordSearch;
import com.gary.spiders.util.WordSearchConfig;
import com.gary.spiders.util.WordSearchGridAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WordSearchActivity extends Game {

    int numWordsFound = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordsearch);

        GridView gridView = (GridView) findViewById(R.id.wordsearch_grid);
        final ListView listView = (ListView) findViewById(R.id.wordsearch_words);

        String[] args = new String[3];
        args[0] = WordSearchConfig.EASY_DIFFICULTY;
        args[1] = "10";
        args[2] = "10";

        GameResourceLoader resourceLoader = new GameResourceLoader(this);
        String[] wordsArray = getResources().getStringArray(resourceLoader.getResourceArray(super.category));

        Random rand = new Random();


        // choose 5 words from the list at random
        final List<String> words = new ArrayList<>();
        while(words.size() < 5){
            int randomIndex = rand.nextInt(8);
            if(!words.contains(wordsArray[randomIndex])){
                words.add(wordsArray[randomIndex]);
            }
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, words);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppCompatTextView clickedTextView = (AppCompatTextView) view;
                clickedTextView.setPaintFlags(clickedTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        });
        Grid grid = WordSearch.generateWordSearch(args, words);
        WordSearchGridAdapter booksAdapter = new WordSearchGridAdapter(this, grid.getGridArray());
        gridView.setAdapter(booksAdapter);

        final List<TextView> selectedTiles = new ArrayList<>();

        gridView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent me) {
                final GridView view = (GridView) v;
                float currentXPosition = me.getX();
                float currentYPosition = me.getY();
                int position = view.pointToPosition((int) currentXPosition, (int) currentYPosition);

                // Change the color of the key pressed
                TextView tv = (TextView) view.getChildAt(position);

                if(tv != null){
                    tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    selectedTiles.add(tv);
                }

                if(me.getAction() == MotionEvent.ACTION_UP){
                    StringBuilder sb = new StringBuilder();
                    for(TextView textV : selectedTiles){
                        sb.append(textV.getText());
                    }
                    for(String word : words){
                        if(containsAllChars(word, sb.toString())){
                            selectedTiles.clear();
                            updateNumWordsFound();
                            return true;
                        }
                    }

                    // didnt pick a word correctly
                    for(TextView selectedTextView : selectedTiles){
                        selectedTextView.setPaintFlags( selectedTextView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                    }

                    selectedTiles.clear();
                }
                return true;
            }
        });
    }

    private void updateNumWordsFound() {
        numWordsFound++;
        if(numWordsFound == 5){
            AlertDialog alertDialog = AlertUtility.createGameCompletedAlert(WordSearchActivity.this, category);
            alertDialog.show();
        }
    }

    public static boolean containsAllChars
            (String expected, String attempt) {

        char[] first = expected.toCharArray();
        char[] second = attempt.toCharArray();
        Arrays.sort(first);
        Arrays.sort(second);
        return Arrays.equals(first, second);
    }

}
