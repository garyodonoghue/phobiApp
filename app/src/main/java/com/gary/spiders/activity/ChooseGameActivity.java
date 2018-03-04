package com.gary.spiders.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.gary.spiders.R;
import com.gary.spiders.enums.GameMode;
import com.gary.spiders.game.GameFactory;

public class ChooseGameActivity extends AppCompatActivity {

    private ToggleButton wordsearchGameSelected;
    private ToggleButton jigsawGameSelected;
    private ToggleButton imagePickerGameSelected;
    private ToggleButton zoomGameSelected;
    private ToggleButton focusGameSelected;
    private ToggleButton playVideoGameSelected;
    private ToggleButton popupImagesGameSelected;

    private EditText levelValue;

    private String selectedGameMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);

        wordsearchGameSelected = (ToggleButton) findViewById(R.id.wordsearchGameSelected);
        jigsawGameSelected = (ToggleButton) findViewById(R.id.jigsawGameSelected);
        imagePickerGameSelected = (ToggleButton) findViewById(R.id.imagePickerGameSelected);
        zoomGameSelected = (ToggleButton) findViewById(R.id.zoomGameSelected);
        focusGameSelected = (ToggleButton) findViewById(R.id.focusGameSelected);
        playVideoGameSelected = (ToggleButton) findViewById(R.id.playVideoGameSelected);
        popupImagesGameSelected = (ToggleButton) findViewById(R.id.popupImagesGameSelected);

        levelValue = (EditText) findViewById(R.id.levelValue);

        CompoundButton.OnCheckedChangeListener onToggleListener = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clearAllToggles();
                buttonView.setChecked(isChecked);
                selectedGameMode = (String) buttonView.getTag();
            }
        };

        wordsearchGameSelected.setOnCheckedChangeListener(onToggleListener);
        jigsawGameSelected.setOnCheckedChangeListener(onToggleListener);
        imagePickerGameSelected.setOnCheckedChangeListener(onToggleListener);
        zoomGameSelected.setOnCheckedChangeListener(onToggleListener);
        focusGameSelected.setOnCheckedChangeListener(onToggleListener);
        playVideoGameSelected.setOnCheckedChangeListener(onToggleListener);
        popupImagesGameSelected.setOnCheckedChangeListener(onToggleListener);
    }

    public void generateGame(View v){
        String level = levelValue.getText().toString();
        GameFactory.generateSpecificGame(Integer.parseInt(level), GameMode.valueOf(selectedGameMode));
    }

    private void clearAllToggles(){
        wordsearchGameSelected.setChecked(false);
        jigsawGameSelected.setChecked(false);
        imagePickerGameSelected.setChecked(false);
        zoomGameSelected.setChecked(false);
        focusGameSelected.setChecked(false);
        playVideoGameSelected.setChecked(false);
        popupImagesGameSelected.setChecked(false);
    }
}
