package com.example.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

public class MainPage extends AppCompatActivity {

    private CardView cardviewEven;
    private CardView cardViewCustom;
    private CardView cardViewCombination;
    private CardView cardViewHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        cardviewEven=findViewById(R.id.evenBreakDown);
        cardviewEven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, EvenBreakDown.class);
                startActivity(intent);
            }
        });

        cardViewCustom=findViewById(R.id.ratioBreakDown);
        cardViewCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, RatioBreakDown.class);
                startActivity(intent);
            }
        });

        cardViewCombination=findViewById(R.id.combinationBreakDown);
        cardViewCombination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, CombinationBreakDown.class);
                startActivity(intent);
            }
        });




    };



}