package com.gary.spiders.game;

import android.content.res.ColorStateList;
import android.graphics.Color;
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
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class WordSearchActivity extends BaseGame {

    int numWordsFound = 0;
    List<String> obfuscatedWords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordsearch);

        final TextView textView = (TextView) findViewById(R.id.wordsearchTimer);
        super.setupGameTimer(textView, this);

        GridView gridView = (GridView) findViewById(R.id.wordsearch_grid);
        final ListView listView = (ListView) findViewById(R.id.wordsearch_words);

        String[] args = new String[3];
        args[0] = WordSearchConfig.NORMAL_DIFFICULTY;
        args[1] = "10";
        args[2] = "10";

        GameResourceLoader resourceLoader = new GameResourceLoader(this);
        String[] wordsArray = getResources().getStringArray(resourceLoader.getResourceArray(super.category));

        Random rand = new Random();


        // choose 5 words from the list at random
        final List<String> words = new ArrayList<>();
        while(words.size() < 5){
            int randomIndex = rand.nextInt(8);
            String word = wordsArray[randomIndex];
            if(!words.contains(word)){
                words.add(word);
                obfuscateWord(word);
            }
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, obfuscatedWords);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppCompatTextView clickedTextView = (AppCompatTextView) view;

                // if text view is already striked through, then remove it, else set it
                if(clickedTextView.getPaintFlags() == Paint.STRIKE_THRU_TEXT_FLAG){
                    clickedTextView.setPaintFlags( clickedTextView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }
                else {
                    clickedTextView.setPaintFlags(clickedTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        });

        Grid grid = WordSearch.generateWordSearch(args, words);
        WordSearchGridAdapter booksAdapter = new WordSearchGridAdapter(this, grid.getGridArray());
        gridView.setAdapter(booksAdapter);

        final Set<TextView> selectedTiles = new HashSet<>();
        final Set<TextView> correctTiles = new HashSet<>();

        TextView lastSelectedTextView = null;
        final ColorStateList[] oldColors = {null};

        gridView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent me) {
                final GridView view = (GridView) v;
                float currentXPosition = me.getX();
                float currentYPosition = me.getY();
                int position = view.pointToPosition((int) currentXPosition, (int) currentYPosition);

                // Change the color of the key pressed
                TextView tv = (TextView) view.getChildAt(position);


                if(tv != null){
                    oldColors[0] = oldColors[0] == null ? tv.getTextColors() : oldColors[0];
                    tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    tv.setTextColor(Color.BLUE);
                    selectedTiles.add(tv);
                }

                if(me.getAction() == MotionEvent.ACTION_UP){
                    StringBuilder sb = new StringBuilder();
                    for(TextView textV : selectedTiles){
                        sb.append(textV.getText());
                    }
                    for(String word : words){
                        if(containsAllChars(word, sb.toString())){
                            // this will avoid being able to progress by selecting the same word 5 times
                            words.remove(word);
                            correctTiles.addAll(selectedTiles);
                            selectedTiles.clear();
                            updateNumWordsFound();
                            return true;
                        }
                    }

                    // didnt pick a word correctly
                    for(TextView selectedTextView : selectedTiles){
                        selectedTextView.setPaintFlags( selectedTextView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                        selectedTextView.setTextColor(oldColors[0]);
                    }

                    selectedTiles.clear();
                }
                return true;
            }
        });
    }

    private void obfuscateWord(String word) {
        String firstLetter = String.valueOf(word.charAt(0));
        String lastLetter = String.valueOf(word.charAt(word.length()-1));
        int numbMiddleLetters = word.length() - 2;

        String toAdd = "*";
        StringBuilder s = new StringBuilder();
        for(int count = 0; count < numbMiddleLetters; count++) {
            s.append(toAdd);
        }
        String replacedMiddle = s.toString();
        String obfuscatedWord = firstLetter + replacedMiddle + lastLetter;
        obfuscatedWords.add(obfuscatedWord);
    }

    private void updateNumWordsFound() {
        numWordsFound++;
        if(numWordsFound == 5){
            AlertDialog alertDialog = AlertUtility.createGameCompletedAlert(WordSearchActivity.this);
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

    public void giveUp(View v){
        super.giveUp(v);
    }
}
