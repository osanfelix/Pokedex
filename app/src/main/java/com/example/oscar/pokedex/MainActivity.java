package com.example.oscar.pokedex;

import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
    MediaPlayer mPlayer = null;
    boolean downloading = false;

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

        downloadPokemonDrawable();
    }


    public void onClickLeft(View v)
    {
        if(!downloading)
        {
            pokemonIndex = pokemonIndex != 0 ? (pokemonIndex - 1) : MAX_POKEMONS - 1;
            downloadPokemonDrawable();
        }
    }

    public void onClickRight(View v)
    {
        if(!downloading) {
            pokemonIndex = (pokemonIndex + 1) % MAX_POKEMONS;
            downloadPokemonDrawable();
        }
    }

    public void onClickPokemon(View v)
    {
        if(!downloading)
            mPlayer.start();

    }

    Drawable downloadFromNet(String url)
    {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "server");
            Log.d("POKEMS", "Download image: " + d.toString());

            return d;
        } catch (Exception e) { return null; }
    }

    // Downloads current Pokemon (pokemonIndex)
    public void downloadPokemonDrawable()
    {
        final String imageUrl = "http://internetserver.no-ip.org/P4/p" + (pokemonIndex + 1) + ".png";
        final String soundUrl = "http://internetserver.no-ip.org/P4/p" + (pokemonIndex + 1) + ".wav";

        downloading = true;
        Log.d("POKEMS", "downloading");
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //  Download image
                final Drawable pokeDraw = downloadFromNet(imageUrl);

                // Download sound
                try
                {
                    mPlayer = new MediaPlayer();
                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mPlayer.setDataSource(soundUrl);
                    mPlayer.prepare(); // might take long! (for buffering, etc)
                    downloading = false;
                }catch (IOException e)
                {
                    e.printStackTrace();
                }

                imagePokemon.post(new Runnable() {
                    public void run() {
                        imagePokemon.setImageDrawable(pokeDraw);
                    }
                });
            }
        }).start();
    }
}