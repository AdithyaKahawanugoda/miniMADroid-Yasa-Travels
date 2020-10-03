package com.example.yasatravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManageUsersActivity extends AppCompatActivity {

    private RecyclerView userlList;
    private DatabaseReference dbRef;
    private Button deletebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);


        dbRef = FirebaseDatabase.getInstance().getReference().child("User");

        userlList = (RecyclerView) findViewById(R.id.mguserlist);
        userlList.setHasFixedSize(true);
        userlList.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<CardData> options = new FirebaseRecyclerOptions.Builder<CardData>().setQuery(dbRef, CardData.class).build();
        FirebaseRecyclerAdapter<CardData, CardViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CardData, CardViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, final int i, @NonNull CardData cardData) {
                cardViewHolder.setUserData(getApplicationContext(), cardData.getName(), cardData.getEmail(), cardData.getImage());
                deletebtn = (Button) cardViewHolder.myView.findViewById(R.id.mgUcdelete);
                deletebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dataId = getRef(i).getKey();
                        Log.i("mgusers", "Touched ID:" + dataId);
                        // user delete code
                        AlertDialog.Builder builder = new AlertDialog.Builder(ManageUsersActivity.this);
                        builder.setTitle("Are you sure?");
                        builder.setMessage("Deleting this entry cannot rollback after confirmation");
                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbRef.removeValue();
                                dialog.dismiss();
                            }

                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }

            @NonNull
            @Override
            public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_mgusers_card, parent, false);
                return new CardViewHolder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        userlList.setAdapter(firebaseRecyclerAdapter);
    }
}