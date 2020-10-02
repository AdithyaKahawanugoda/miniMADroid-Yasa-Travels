package com.example.yasatravels;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CurrancyConvertorActivity extends AppCompatActivity {

    Spinner sp1,sp2;
    EditText ed1;
    Button B1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currancy_convertor);


        ed1 = findViewById(R.id.amount);
        sp1 = findViewById(R.id.spinner1);
        sp2 = findViewById(R.id.spinner2);
        B1  = findViewById(R.id.convertBtn);

        String[] from = {"USD","GBP","AUD","LKR"};
        ArrayAdapter ad = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,from);
        sp1.setAdapter(ad);

        String[] to = {"LKR","USD","GBP"};
        ArrayAdapter ad1 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,to);
        sp2.setAdapter(ad1);

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double tot;
                Double amount = Double.parseDouble(ed1.getText().toString());


                if(sp1.getSelectedItem().toString() == "USD" && sp2.getSelectedItem().toString() == "LKR")
                {
                    tot = amount * 180.0;
                    Toast.makeText(getApplicationContext(),tot.toString(),Toast.LENGTH_LONG).show();
                }
                else if(sp1.getSelectedItem().toString() == "GBP" && sp2.getSelectedItem().toString() == "LKR")
                {
                    tot = amount * 238.3;
                    Toast.makeText(getApplicationContext(),tot.toString(),Toast.LENGTH_LONG).show();
                }
                else if(sp1.getSelectedItem().toString() == "AUD" && sp2.getSelectedItem().toString() == "LKR")
                {
                    tot = amount * 132.88;
                    Toast.makeText(getApplicationContext(),tot.toString(),Toast.LENGTH_LONG).show();
                }
                else if(sp1.getSelectedItem().toString() == "LKR" && sp2.getSelectedItem().toString() == "USD")
                {
                    tot = amount / 180;
                    Toast.makeText(getApplicationContext(),tot.toString(),Toast.LENGTH_LONG).show();
                }
                else if(sp1.getSelectedItem().toString() == "GBP" && sp2.getSelectedItem().toString() == "USD")
                {
                    tot = amount * 1.29;
                    Toast.makeText(getApplicationContext(),tot.toString(),Toast.LENGTH_LONG).show();
                }
                else if(sp1.getSelectedItem().toString() == "AUD" && sp2.getSelectedItem().toString() == "USD")
                {
                    tot = amount * 0.72;
                    Toast.makeText(getApplicationContext(),tot.toString(),Toast.LENGTH_LONG).show();
                }
                else if(sp1.getSelectedItem().toString() == "LKR" && sp2.getSelectedItem().toString() == "GBP")
                {
                    tot = amount / 238.3;
                    Toast.makeText(getApplicationContext(),tot.toString(),Toast.LENGTH_LONG).show();
                }
                else if(sp1.getSelectedItem().toString() == "AUD" && sp2.getSelectedItem().toString() == "GBP")
                {
                    tot = amount * 0.56;
                    Toast.makeText(getApplicationContext(),tot.toString(),Toast.LENGTH_LONG).show();
                }
                else if(sp1.getSelectedItem().toString() == "USD" && sp2.getSelectedItem().toString() == "GBP")
                {
                    tot = amount * 0.78;
                    Toast.makeText(getApplicationContext(),tot.toString(),Toast.LENGTH_LONG).show();
                }

            }
        });



    }
}