package com.example.yasatravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManageRidesActivity extends AppCompatActivity {

    public static final String EXTRA_TEXTID = "rideID";

    private RecyclerView ridesList;
    private DatabaseReference dbRef;
    private Button addRides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_rides);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Ride");

        ridesList = (RecyclerView) findViewById(R.id.fullRidesList);
        ridesList.setHasFixedSize(true);
        ridesList.setLayoutManager(new LinearLayoutManager(this));

        addRides = (Button) findViewById(R.id.btnAddRides);
        addRides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageRidesActivity.this, AddRideActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<CardData> options = new FirebaseRecyclerOptions.Builder<CardData>().setQuery(dbRef, CardData.class).build();
        FirebaseRecyclerAdapter<CardData, CardViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CardData, CardViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, final int i, @NonNull CardData cardData) {
                cardViewHolder.setDetails(getApplicationContext(), cardData.getName(), cardData.getDescription(), cardData.getImage());
                // Log.i("cardDatafull", cardData.toString());

                cardViewHolder.myView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dataId = getRef(i).getKey();
                        // Log.i("detailsHanderCheck", "Touched" + dataId);
                        Intent intent = new Intent(ManageRidesActivity.this, EditRideActivity.class);
                        intent.putExtra(EXTRA_TEXTID, dataId);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_details_card, parent, false);
                //  Log.i("viewData", view.toString());
                return new CardViewHolder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        ridesList.setAdapter(firebaseRecyclerAdapter);
    }
}