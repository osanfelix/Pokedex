package com.example.oscar.pokedex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    public static final int MAX_POKEMONS = 9;

    // Labels
    protected TextView textLife;
    protected TextView textAttack;
    protected TextView textDefense;
    protected TextView textSpeed;

    // Images
    protected ImageView imagePokemon;
    protected ImageView imageType;

    // Variables
    int pokemonIndex = 0;   // [0, (MAX_POKEMONS - 1)]

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get UI
        textLife    = (TextView) findViewById(R.id.textLife);
        textAttack  = (TextView) findViewById(R.id.textAttack);
        textDefense = (TextView) findViewById(R.id.textDefense);
        textSpeed   = (TextView) findViewById(R.id.textSpeed);
        imagePokemon    = (ImageView) findViewById(R.id.imagePokemon);
        imageType       = (ImageView) findViewById(R.id.imageType);
    }


    public void onClickLeft(View v)
    {
        pokemonIndex --;
    }

    public void onClickRight(View v)
    {
        pokemonIndex ++;
    }
}
