package com.gary.spiders.user;

import android.content.SharedPreferences;

import com.gary.spiders.enums.GameCategory;

import java.util.Map;

/**
 * Created by Gary on 17/01/2018.
 */

public class User {

    private String name;
    private String avatarResource;

    private String initialAssessmentCompleted;

    // There are 140 levels
    private int level;

    private GameCategory category;


    public User(SharedPreferences preferences){

        // default all values and then set any values which are available in Shared Preferences
        setDefaultValues();

       if(preferences != null && preferences.getAll().size() > 0) {
           Map<String, String> userDetails = (Map<String, String>) preferences.getAll();

           for (Map.Entry<String, String> userDetail : userDetails.entrySet()) {
               String value = userDetail.getValue();

               switch (userDetail.getKey()) {
                   case "name":
                       setName(value);
                       break;

                   case "avatarResource":
                       setAvatarResource(value);
                       break;

                   case "initialAssessmentCompleted":
                       setInitialAssessmentCompleted(value);
                       break;

                   case "level":
                       setLevel(Integer.parseInt(value));
                       break;

                   default:
                       throw new IllegalArgumentException("Invalid user detail key returned with value=" + userDetail.getKey());
               }
           }
       }
       else {
           // We don't have any stored info for the user yet, default all the values
           setDefaultValues();
       }
    }

    private void setDefaultValues() {
        setName("Username");
        setLevel(0);
        setInitialAssessmentCompleted("false");
        setAvatarResource("");
        setCategory(GameCategory.LINGUISTIC_LOW);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarResource() {
        return avatarResource;
    }

    public void setAvatarResource(String avatarResource) {
        this.avatarResource = avatarResource;
    }

    public GameCategory getCategory() {
        return category;
    }

    public void setCategory(GameCategory category) {
        this.category = category;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String isInitialAssessmentCompleted() {
        return initialAssessmentCompleted;
    }

    public void setInitialAssessmentCompleted(String initialAssessmentCompleted) {
        this.initialAssessmentCompleted = initialAssessmentCompleted;
    }
}
