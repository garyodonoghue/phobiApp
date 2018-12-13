package com.gary.spiders.game;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.gary.spiders.enums.GameCategory;
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
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnTouch;

public class WordSearchGame extends BaseGame {

    @BindView(R.id.wordsearch_grid) GridView gridView;
    @BindView(R.id.wordsearch_words) ListView listView;
    @BindView(R.id.wordsearchTimer) TextView textView;

    private int numWordsFound = 0;
    private List<String> obfuscatedWords = new ArrayList<>();
    private List<String> allWords;
    private int numWords;
    private Set<TextView> selectedTiles = new HashSet<>();
    private ColorStateList[] oldColors = {null};
    private List<String> remainingWords = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordsearch);
        ButterKnife.bind(this);
        CountDownTimer timer = super.setupGameTimer(textView, this, 90000);

        super.presentGameInfoPopup(this, "Find all the remainingWords in the wordsearch within the allowed time to progress to the next level. " +
                "Tapping a word in the list will reveal it for 3 seconds.", timer);

        String[] args = new String[3];
        args[0] = WordSearchConfig.NORMAL_DIFFICULTY;
        args[1] = "10";
        args[2] = "10";

        GameResourceLoader resourceLoader = new GameResourceLoader(this);

        if(super.category == null){
            super.category = GameCategory.LINGUISTIC_HIGH;
        }

        setNumWordsToBeFound();

        String[] wordsArray = getResources().getStringArray(resourceLoader.getResourceArray(super.category));
        Random rand = new Random();

        // choose 'numWords' remainingWords from the list at random
        while(remainingWords.size() < numWords){
            int randomIndex = rand.nextInt(numWords);
            String word = wordsArray[randomIndex];
            if(!remainingWords.contains(word)){
                remainingWords.add(word);
                obfuscateWord(word);
            }
        }

        allWords = new ArrayList<>();
        allWords.addAll(remainingWords);

        arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, obfuscatedWords);

        listView.setAdapter(arrayAdapter);
        Grid grid = WordSearch.generateWordSearch(args, remainingWords);
        WordSearchGridAdapter booksAdapter = new WordSearchGridAdapter(this, grid.getGridArray());
        gridView.setAdapter(booksAdapter);
    }

    @OnTouch(R.id.wordsearch_grid) boolean onTouch(View v, MotionEvent me) {
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
            for(String word : remainingWords){
                if(containsAllChars(word, sb.toString())){
                    // this will avoid being able to progress by selecting the same word 'numWords' times
                    remainingWords.remove(word);
                    selectedTiles.clear();
                    updateNumWordsFound();
                    obfuscatedWords.set(allWords.indexOf(word), word);
                    arrayAdapter.notifyDataSetChanged();
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

    @OnItemClick(R.id.wordsearch_words) void click(AdapterView<?> parent, final View view, final int position, long id){
            final AppCompatTextView clickedTextView = (AppCompatTextView) view;

            // check if its hidden, if so, reveal the word for 3 seconds and then hide it again
            if(clickedTextView.getText().toString().contains("*")) {
                revealWord((AppCompatTextView) view, position);

                final Timer t = new java.util.Timer();
                t.schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideWord((AppCompatTextView) view, position);
                                        t.cancel();
                                    }
                                });
                            }
                        },
                        2000
                );
            }
        }


    private void setNumWordsToBeFound() {
        if(super.category == GameCategory.LINGUISTIC_LOW){
            numWords = 3;
        }
        else if((super.category == GameCategory.LINGUISTIC_MED)){
            numWords = 4;
        }
        else if(super.category == GameCategory.LINGUISTIC_HIGH){
            numWords = 5;
        }
    }

    private void hideWord(AppCompatTextView view, int position) {
        String obfuscatedText = obfuscatedWords.get(position);
        view.setText(obfuscatedText);
    }

    private void revealWord(AppCompatTextView view, int position) {
        String clearText = allWords.get(position);
        view.setText(clearText);
    }

    private void obfuscateWord(String word) {
        int numLetters = word.length();

        String toAdd = "*";
        StringBuilder s = new StringBuilder();
        for(int count = 0; count < numLetters; count++) {
            s.append(toAdd);
        }
        String obfuscatedWord = s.toString();
        obfuscatedWords.add(obfuscatedWord);
    }

    private void updateNumWordsFound() {
        numWordsFound++;
        if(numWordsFound == numWords){
            super.stopTimer();
            AlertDialog alertDialog = AlertUtility.createGameCompletedAlert(WordSearchGame.this);
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
