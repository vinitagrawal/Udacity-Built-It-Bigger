package com.example.jokedisplay;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    public static final String INTENT_JOKE = "JOKE";

    public static Intent startIntent(Context context, String joke) {
        Intent intent = new Intent(context, JokeDisplayActivity.class);
        return intent.putExtra(INTENT_JOKE, joke);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        String joke = getIntent().getStringExtra(INTENT_JOKE);
        if(joke==null)
            joke = "There are no jokes";

        TextView textViewJoke = (TextView) findViewById(R.id.textViewJoke);
        textViewJoke.setText(joke);

    }
}
