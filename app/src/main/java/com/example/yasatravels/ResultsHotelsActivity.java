package com.example.yasatravels;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ResultsHotelsActivity extends AppCompatActivity {

    private RecyclerView hotelList;
    private DatabaseReference dbRef;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_hotels);

        Intent intent = getIntent();
        String input = intent.getStringExtra(SearchActivity.EXTRA_TEXTDISTRICT);
        Log.i("ResultsHotel", input);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Hotel");
        query = dbRef.orderByChild("district").equalTo(input);

        hotelList = (RecyclerView) findViewById(R.id.resultHlist);
        hotelList.setHasFixedSize(true);
        hotelList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<CardData> options = new FirebaseRecyclerOptions.Builder<CardData>().setQuery(query, CardData.class).build();
        FirebaseRecyclerAdapter<CardData, CardViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CardData, CardViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, final int i, @NonNull CardData cardData) {
                cardViewHolder.setDetails(getApplicationContext(), cardData.getName(), cardData.getDescription(), cardData.getImage());
            }

            @NonNull
            @Override
            public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_details_card, parent, false);
                Log.i("viewData", view.toString());
                return new CardViewHolder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        hotelList.setAdapter(firebaseRecyclerAdapter);
    }


//    public class CardViewHolder extends RecyclerView.ViewHolder{
//
//        View myView;
//
//        public CardViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            myView = itemView;
//        }
//        public void setName(String name){
//            TextView card_head = (TextView) myView.findViewById(R.id.cardHead);
//            card_head.setText(name);
//        }
//
//        public void setBody(String description){
//            TextView card_body = (TextView) myView.findViewById(R.id.cardDesc);
//            card_body.setText(description);
//        }
//
//        public void setImage(Application ctx, String image){
//            ImageView card_image = (ImageView) myView.findViewById(R.id.mhcardimg);
//            Picasso.get().load(image).into(card_image);
//            Glide.with(ctx).load(image).into(card_image);
//            //Picasso.with(ctx).load(image).into(card_image);
//
//            Log.i("loadimage",image);
//            // Log.i("imgviewId",card_image.toString());
//        }
//    }


}