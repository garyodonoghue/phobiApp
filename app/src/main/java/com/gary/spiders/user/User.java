package com.gary.spiders.user;

import android.content.SharedPreferences;

import com.gary.spiders.enums.GameCategory;

import java.util.Map;

/**
 * Created by Gary on 17/01/2018.
 */

public class User {

    private String name;
    private int avatarResourceId;

    private boolean initialAssessmentCompleted;

    // There are 100 levels, 10pts per level
    private int points;
    private int level;

    private GameCategory category;


    public User(SharedPreferences preferences){
       if(preferences != null) {
           Map<String, String> userDetails = (Map<String, String>) preferences.getAll();


           for (Map.Entry<String, String> userDetail : userDetails.entrySet()) {
               String value = userDetail.getValue();

               switch (userDetail.getKey()) {
                   case "name":
                       setName(value);
                       break;

                   case "avatarResourceId":
                       setAvatarResourceId(Integer.parseInt(value));
                       break;

                   case "initialAssessmentCompleted":
                       setInitialAssessmentCompleted(Boolean.parseBoolean(value));
                       break;

                   case "points":
                       setPoints(Integer.parseInt(value));
                       break;

                   default:
                       throw new IllegalArgumentException("Invalid user detail key returned with value=" + userDetail.getKey());
               }
           }
       }
       else {
           // We don't have any stored info for the user yet, default all the values
           setName("Username");
           setPoints(0);
           setInitialAssessmentCompleted(false);
           setAvatarResourceId(-1);
           setCategory(GameCategory.LINGUISTIC_LOW);
       }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvatarResourceId() {
        return avatarResourceId;
    }

    public void setAvatarResourceId(int avatarResourceId) {
        this.avatarResourceId = avatarResourceId;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isInitialAssessmentCompleted() {
        return initialAssessmentCompleted;
    }

    public void setInitialAssessmentCompleted(boolean initialAssessmentCompleted) {
        this.initialAssessmentCompleted = initialAssessmentCompleted;
    }
}
