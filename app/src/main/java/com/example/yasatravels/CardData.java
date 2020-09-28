package com.example.yasatravels;

import android.util.Log;

public class CardData {

    private String name,description,image,email;

    public CardData() {
    }

    public CardData(String name, String description, String image,String email) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.email = email;

    }

    public String getImage() {
        return image;
    }

    public String getEmail(){return email;}

    public void setEmail(String email){
        this.email = email;
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
