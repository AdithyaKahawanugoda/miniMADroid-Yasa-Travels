package com.example.yasatravels;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class CardViewHolder extends RecyclerView.ViewHolder {

    View myView;

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);

        myView = itemView;
    }

    public void setDetails(Context ctx, String name, String description, String image) {
        TextView card_head = (TextView) myView.findViewById(R.id.cardHead);
        TextView card_body = (TextView) myView.findViewById(R.id.cardDesc);
        ImageView card_image = (ImageView) myView.findViewById(R.id.mhcardimg);
        card_head.setText(name);
        card_body.setText(description);
        Picasso.get().load(image).into(card_image);

        Animation animation = AnimationUtils.loadAnimation(ctx, android.R.anim.slide_in_left);
        myView.setAnimation(animation);

    }

    //for user management
    public void setUserData(Context ctx, String name, String email, String image){
        TextView cname = (TextView) myView.findViewById(R.id.mgUcName);
        TextView cemail = (TextView) myView.findViewById(R.id.mgUcEmail);
        ImageView cimage = (ImageView) myView.findViewById(R.id.mgUcImg);
        cname.setText(name);
        cemail.setText(email);
        Picasso.get().load(image).into(cimage);

        Animation animation = AnimationUtils.loadAnimation(ctx, android.R.anim.slide_in_left);
        myView.setAnimation(animation);
    }
}