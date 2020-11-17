package com.example.tictactoegame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.tictactoegame.R.layout;

public class WinningSreen extends AppCompatActivity implements View.OnClickListener {
    Button playAgain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_winning_sreen);

        Bundle bundle = getIntent().getExtras();

        String winnerMsg = bundle.getString("winner");
        String finalscore = bundle.getString("fscore");

        TextView fScore = findViewById(R.id.endingScore);

        TextView winMsg = findViewById(R.id.winningplayer);

        winMsg.setText(winnerMsg);

        fScore.setText(finalscore);



        /*
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });
    }
    public void openNewActivity(){
        Intent i = new Intent(WinningSreen.this, GamePlay.class);
        startActivity(i);
    }

         */
    }

    @Override
    public void onClick(View v) {

        playAgain.setText("Worked");
    }
}
