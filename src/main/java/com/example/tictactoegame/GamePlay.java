package com.example.tictactoegame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GamePlay extends AppCompatActivity implements View.OnClickListener {
    private TextView P1_score, P2_score, gameStatus;
    private Button[] btns = new Button[9];
    private Button resetGame;

    public int P1_score_count, P2_score_count, round;

    boolean activePlayer;

    /* p1 = 0
       p2 = 1
       empty = 2
     */
    Random ran = new Random();


    int[] ximage = {R.mipmap.x_foreground, 00000000};
    int[] oimage = {R.mipmap.alien1_foreground,R.mipmap.alien2_foreground};

    int ranNum = ran.nextInt(2);


    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions =
            {
                    {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // winning rows
                    {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // winning columns
                    {0, 4, 8}, {2, 4, 6} // winning cross
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        P1_score = findViewById(R.id.playerOneScore);
        P2_score = findViewById(R.id.playerTwoScore);

        gameStatus = findViewById(R.id.gameStatus);

        resetGame = findViewById(R.id.resetGame);



        for(int i = 0; i < btns.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID,"id",getPackageName());
            btns[i] = (Button) findViewById(resourceID);
            btns[i].setOnClickListener(this);

        }

        round = 0;
        P1_score_count = 0;
        P2_score_count = 0;
        activePlayer = true;


    }


    @Override
    public void onClick(View v) {

        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId()); // btn_1
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1, buttonID.length())); // 1

        if (activePlayer) {
            //((Button) v).setText("X");
            //((Button) v).setTextColor(Color.parseColor("#ff0000"));
            v.setBackgroundResource(ximage[0]);
            gameState[gameStatePointer] = 0;
        } else {
            v.setBackgroundResource(oimage[0]);

            int temp = oimage[0];

            oimage[0] = oimage[1];

            oimage[1] = temp;

            //((Button) v).setText("O");
            //((Button) v).setTextColor(Color.parseColor("#FF1DFD9D"));
            gameState[gameStatePointer] = 1;
        }

        round++;

        if(checkWinner()){
            if(activePlayer){
                P1_score_count++;
                updatePlayerScore();
                newMatch();
            }
            else{
                P2_score_count++;
                updatePlayerScore();
                newMatch();

            }
        }
        else if(round == 9){
            try {
                TimeUnit.SECONDS.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            newMatch();

        }
        else{
            activePlayer = !activePlayer;
        }



        if(round > 0 || (P1_score_count > 0|| P2_score_count > 0) ){
            gameStatus.setText("");
        }
        else {
            gameStatus.setText("BEST OF 5");
        }



        if(P1_score_count == 3 || P2_score_count ==3){
            gameStatus.setText(winner());
            resetBoard();

        }
        else {

            resetGame.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    resetBoard();
                }
            });
        }

    }


    public boolean checkWinner(){
        boolean winnerResults = false;

        for(int [] winningPosition : winningPositions){
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2){
                winnerResults = true;
            }
        }
        return winnerResults;
    }

    public void updatePlayerScore(){
        P1_score.setText(Integer.toString(P1_score_count));
        P2_score.setText(Integer.toString(P2_score_count));

    }
    public void resetBoard() {
        round = 0;
        activePlayer = true;
        P1_score_count = 0;
        P2_score_count = 0;
        updatePlayerScore();
        for (int i = 0; i < btns.length; i++) {
            gameState[i] = 2;
            btns[i].setText("");
            btns[i].setBackgroundResource(ximage[1]);


        }
    }

    public void newMatch(){
        round = 0;
        activePlayer = true;

        for(int i = 0; i < btns.length; i++){
            gameState[i] = 2;
            btns[i].setText("");
            btns[i].setBackgroundResource(ximage[1]);
        }
    }

    public String winner(){

        if(P1_score_count == 3) {
            return ("PLAYER 1 WINS");
        }

        else{
            return("PLAYER 2 WINS");
        }
    }

}
