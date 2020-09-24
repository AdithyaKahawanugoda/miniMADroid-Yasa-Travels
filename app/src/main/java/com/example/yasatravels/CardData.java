package com.example.yasatravels;

import android.util.Log;

public class CardData {

    private String name,description,image;

    public CardData() {
    }

    public CardData(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        Log.i("settercheck",image);
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Log.i("settercheck",name);
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
