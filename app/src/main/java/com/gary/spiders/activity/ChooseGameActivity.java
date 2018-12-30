package com.gary.spiders.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.gary.spiders.R;
import com.gary.spiders.enums.GameType;
import com.gary.spiders.game.BaseGame;
import com.gary.spiders.game.GameFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseGameActivity extends AppCompatActivity {

    @BindView(R.id.wordsearchGameSelected) ToggleButton wordsearchGameSelected;
    @BindView(R.id.jigsawGameSelected) ToggleButton jigsawGameSelected;
    @BindView(R.id.imagePickerGameSelected) ToggleButton imagePickerGameSelected;
    @BindView(R.id.zoomGameSelected) ToggleButton zoomGameSelected;
    @BindView(R.id.focusGameSelected) ToggleButton focusGameSelected;
    @BindView(R.id.playVideoGameSelected) ToggleButton playVideoGameSelected;
    @BindView(R.id.popupImagesGameSelected) ToggleButton popupImagesGameSelected;
    @BindView(R.id.sharpenTextGameSelected) ToggleButton sharpenTextGameSelected;
    @BindView(R.id.unscrambleWordGameSelected) ToggleButton unscrambleWordGameSelected;

    private String selectedGameMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);
        ButterKnife.bind(this);

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
        sharpenTextGameSelected.setOnCheckedChangeListener(onToggleListener);
        unscrambleWordGameSelected.setOnCheckedChangeListener(onToggleListener);
    }

    public void generateGame(View v){
        BaseGame gameType = GameFactory.generateSpecificGame(GameType.valueOf(selectedGameMode));

        Intent intent1 = new Intent(ChooseGameActivity.this, gameType.getClass());
        intent1.putExtra("initialAssessment", "false");

        ChooseGameActivity.this.startActivity(intent1);
    }

    private void clearAllToggles(){
        wordsearchGameSelected.setChecked(false);
        jigsawGameSelected.setChecked(false);
        imagePickerGameSelected.setChecked(false);
        zoomGameSelected.setChecked(false);
        focusGameSelected.setChecked(false);
        playVideoGameSelected.setChecked(false);
        popupImagesGameSelected.setChecked(false);
        sharpenTextGameSelected.setChecked(false);
        unscrambleWordGameSelected.setChecked(false);
    }
}
