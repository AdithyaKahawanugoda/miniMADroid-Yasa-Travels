//package com.example.yasatravels;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//
//public class ResultsLocationsActivity extends AppCompatActivity {
//
//    public static final String EXTRA_LRESULTID = "locationID";
//
//    private RecyclerView locationlist;
//    private DatabaseReference dbRef;
//    private Query query;
//    private TextView distName;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_results_locations);
//
//        Intent intent = getIntent();
//        String input = intent.getStringExtra(SearchActivity.EXTRA_TEXTDISTRICT);
//        Log.i("ResultsLocation", input);
//
//        distName = (TextView) findViewById(R.id.inputdist);
//        distName.setText(input);
//
//        dbRef = FirebaseDatabase.getInstance().getReference().child("Location");
//        query = dbRef.orderByChild("district").equalTo(input);
//
//        locationlist = (RecyclerView) findViewById(R.id.resultLlist);
//        locationlist.setHasFixedSize(true);
//        locationlist.setLayoutManager(new LinearLayoutManager(this));
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseRecyclerOptions<CardData> options = new FirebaseRecyclerOptions.Builder<CardData>().setQuery(query, CardData.class).build();
//        FirebaseRecyclerAdapter<CardData, CardViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CardData, CardViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, final int i, @NonNull CardData cardData) {
//                cardViewHolder.setDetails(getApplicationContext(), cardData.getName(), cardData.getDescription(), cardData.getImage());
//
//
//                cardViewHolder.myView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String dataId = getRef(i).getKey();
//                        // Log.i("detailsHanderCheck", "Touched" + dataId);
//                        Intent intent = new Intent(ResultsLocationsActivity.this, LocationDetailsActivity.class);
//                        intent.putExtra(EXTRA_LRESULTID , dataId);
//                        startActivity(intent);
//                    }
//                });
//            }
//
//            @NonNull
//            @Override
//            public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_details_card, parent, false);
//                Log.i("viewData", view.toString());
//                return new CardViewHolder(view);
//            }
//        };
//
//        firebaseRecyclerAdapter.startListening();
//        locationlist.setAdapter(firebaseRecyclerAdapter);
//    }
//}

//package com.example.yasatravels;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//
//public class ResultsLocationsActivity extends AppCompatActivity {
//
//    public static final String EXTRA_LRESULTID = "locationID";
//
//    private RecyclerView locationlist;
//    private DatabaseReference dbRef;
//    private Query query;
//    private TextView distName;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_results_locations);
//
//        Intent intent = getIntent();
//        String input = intent.getStringExtra(SearchActivity.EXTRA_TEXTDISTRICT);
//        Log.i("ResultsLocation", input);
//
//        distName = (TextView) findViewById(R.id.inputdist);
//        distName.setText(input);
//
//        dbRef = FirebaseDatabase.getInstance().getReference().child("Location");
//        query = dbRef.orderByChild("district").equalTo(input);
//
//        locationlist = (RecyclerView) findViewById(R.id.resultLlist);
//        locationlist.setHasFixedSize(true);
//        locationlist.setLayoutManager(new LinearLayoutManager(this));
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseRecyclerOptions<CardData> options = new FirebaseRecyclerOptions.Builder<CardData>().setQuery(query, CardData.class).build();
//        FirebaseRecyclerAdapter<CardData, CardViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CardData, CardViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, final int i, @NonNull CardData cardData) {
//                cardViewHolder.setDetails(getApplicationContext(), cardData.getName(), cardData.getDescription(), cardData.getImage());
//
//
//                cardViewHolder.myView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String dataId = getRef(i).getKey();
//                        // Log.i("detailsHanderCheck", "Touched" + dataId);
//                        Intent intent = new Intent(ResultsLocationsActivity.this, LocationDetailsActivity.class);
//                        intent.putExtra(EXTRA_LRESULTID , dataId);
//                        startActivity(intent);
//                    }
//                });
//            }
//
//            @NonNull
//            @Override
//            public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_details_card, parent, false);
//                Log.i("viewData", view.toString());
//                return new CardViewHolder(view);
//            }
//        };
//
//        firebaseRecyclerAdapter.startListening();
//        locationlist.setAdapter(firebaseRecyclerAdapter);
//    }
//}

package com.example.yasatravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ResultsLocationsActivity  extends AppCompatActivity {

    public static final String EXTRA_LRESULTID = "locationID";

    private RecyclerView locationlist;
    private DatabaseReference dbRef;
    private Query query;
    private TextView distName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_locations);

        Intent intent = getIntent();
        String input = intent.getStringExtra(SearchActivity.EXTRA_TEXTDISTRICT);
        Log.i("ResultsLocations", input);

        distName = (TextView) findViewById(R.id.inputdist);
        distName.setText(input);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Location");
        query = dbRef.orderByChild("district").equalTo(input);

        locationlist = (RecyclerView) findViewById(R.id.resultLlist);
        locationlist.setHasFixedSize(true);
        locationlist.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<CardData> options = new FirebaseRecyclerOptions.Builder<CardData>().setQuery(query, CardData.class).build();
        FirebaseRecyclerAdapter<CardData, CardViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CardData, CardViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, final int i, @NonNull CardData cardData) {
                cardViewHolder.setDetails(getApplicationContext(), cardData.getName(), cardData.getDescription(), cardData.getImage());

                cardViewHolder.myView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dataId = getRef(i).getKey();
                        // Log.i("detailsHanderCheck", "Touched" + dataId);
                        Intent intent = new Intent(ResultsLocationsActivity.this, LocationDetailsActivity.class);
                        intent.putExtra(EXTRA_LRESULTID, dataId);
                        startActivity(intent);
                    }
                });
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
        locationlist.setAdapter(firebaseRecyclerAdapter);
    }
}