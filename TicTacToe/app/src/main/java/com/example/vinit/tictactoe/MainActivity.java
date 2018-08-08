package com.example.vinit.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button[][] buttons=new Button[3][3];
    private boolean player1Turn=true;

    private String[][] field=new String[3][3];

    private int roundCount=0;
    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    private String[][] last=new String[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1=findViewById(R.id.tvp1);
        textViewPlayer2=findViewById(R.id.tvp2);

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
               String buttonId="button_"+i+j;
                int resID=getResources().getIdentifier(buttonId,"id",getPackageName());
                buttons[i][j]=findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset=findViewById(R.id.resetbut);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
                player1Points=0;
                player2Points=0;
                textViewPlayer1.setText("Player 1: "+player1Points);
                textViewPlayer2.setText("Player 2: "+player2Points);
            }
        });

        Button buttonPlay=findViewById(R.id.playbut);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
            }
        });

        Button undoButton=findViewById(R.id.undobut);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(roundCount>0) {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            buttons[i][j].setText(last[i][j]);
                            player1Turn = !player1Turn;
                        }
                    }
                }
                else{}
            }
        });

    }
    @Override
    public void onClick(View v){
        if(!((Button) v).getText().toString().equals(""))
        {return;}

        if(player1Turn){
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#ff9800"));
            player1Turn=false;
        }
        else{
            ((Button) v).setText("0");
            player1Turn=true;
            ((Button) v).setTextColor(Color.parseColor("#673ab7"));
        }
        roundCount++;
        if(checkForWin()){
            if(!player1Turn){
                maketoast(1);
            }
            else{
                maketoast(2);
            }
        }else if(roundCount==9){
            maketoast(-1);
        }
        else{}
    }


    private void maketoast(int x){
        String result="";
        if(x==-1){
            result="Draw";
        }
        else{
            if(x==1){
                player1Points+=1;
                result="Player 1 Wins";
            }
            else{
                player2Points+=1;
                result="Player 2 Wins";
            }
        }
        textViewPlayer1.setText("Player 1: "+player1Points);
        textViewPlayer2.setText("Player 2: "+player2Points);
        Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
        play();
    }

    private boolean checkForWin(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                last[i][j]=field[i][j];
                field[i][j]=buttons[i][j].getText().toString();
            }
        }

        for(int i=0;i<3;i++){
            if(field[i][0].equals(field[i][1])&&field[i][0].equals(field[i][2])&&!field[i][0].equals("")){
                return true;
            }
            if(field[0][i].equals(field[1][i])&&field[0][i].equals(field[2][i])&&!field[0][i].equals("")){
                return true;
            }
        }
        if(field[0][0].equals(field[1][1])&&field[0][0].equals(field[2][2])&&!field[0][0].equals("")){
            return true;
        }
        if(field[2][0].equals(field[1][1])&&field[2][0].equals(field[0][2])&&!field[2][0].equals("")){
            return true;
        }

         return false;
    }
    private void play(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
                field[i][j]="";
            }
        }
        player1Turn=true;
        roundCount=0;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount",roundCount);
        outState.putInt("player1Points",player1Points);
        outState.putInt("player2Points",player2Points);
        outState.putBoolean("player1Turn",player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
            roundCount=savedInstanceState.getInt("roundCount");
            player1Points=savedInstanceState.getInt("player1Points");
            player2Points=savedInstanceState.getInt("player2Points");
            player1Turn=savedInstanceState.getBoolean("player1Turn");



    }
}
