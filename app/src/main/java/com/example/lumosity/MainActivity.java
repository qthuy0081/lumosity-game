package com.example.lumosity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int numberWrong = 0;
    int numberCorrect = 0;
    int Score = 0;
    int winStreak;
    AlertDialog.Builder builder;
    CountDownTimer timer;
    ArrayList<String> listColorText = new ArrayList<String>(Arrays.asList("Blue","Red","Black","Yellow"));
    String colorName;
    Random random;
    TextView timerText , randomText,randomColorText, scoreTextView, correctNumberView, wrongNumberView;
    Button wrongButton,correctButton;
    String storeColorName = "";
    String storeActualColor = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerText = findViewById(R.id.timer);
        wrongButton = findViewById(R.id.wrongButton);
        correctButton = findViewById(R.id.correctButton);
        randomText = findViewById(R.id.randomText);
        randomColorText = findViewById(R.id.randomColor);
        scoreTextView = findViewById(R.id.score);
        correctNumberView = findViewById(R.id.correctNumber);
        wrongNumberView = findViewById(R.id.wrongNumber);
        startGame();
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Your scores: " + Score).setTitle("Result").setCancelable(false).setPositiveButton("Try again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                timer.start();
                startGame();
            }
        });
            timer = new CountDownTimer(150_000,1) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText(Long.toString(millisUntilFinished/60_000)+":"+Long.toString(millisUntilFinished%60_000/1_000));
            }
            @Override
            public void onFinish() {
                AlertDialog alert = builder.create();
                alert.show();
            }
        };
            correctButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calculateScores1();

                }
            });
            wrongButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    calculateScores2();
                }
            });
       timer.start();
    }


    protected String generateRandomColor () {
        random = new Random();
        int index = random.nextInt(listColorText.size());
        String colorName = listColorText.get(index);
        return colorName;
    }

    protected  void setColor (TextView textView, String color) {
        switch (color) {
            case "Blue" :
                textView.setTextColor(Color.parseColor("#00BFFF"));
                break;
            case "Black":
                textView.setTextColor(Color.parseColor("#000000"));
                break;
            case "Yellow":
                textView.setTextColor(Color.parseColor("#FFFF00"));
                break;
            default:
                textView.setTextColor(Color.parseColor("#DC143C"));
        }
    }
    protected void startGame () {
        Score = 0;
        numberWrong = 0;
        numberCorrect = 0;
        winStreak = 0;
        scoreTextView.setText(Integer.toString(Score));
        correctNumberView.setText(Integer.toString(numberCorrect));
        wrongNumberView.setText(Integer.toString(numberWrong));
        changeText();
    }
    protected  void  changeText() {
        storeActualColor = generateRandomColor();
        storeColorName = generateRandomColor();
        randomColorText.setText(generateRandomColor());
        randomText.setText(storeColorName);
        setColor(randomColorText,storeActualColor);
        setColor(randomText,generateRandomColor());
    }
    protected  void calculateScores1() {
        if(storeColorName == storeActualColor) {
            Score += 100;
            numberCorrect++;
            scoreTextView.setText(Integer.toString(Score));
            correctNumberView.setText(Integer.toString(numberCorrect));

        }
        changeText();
    }
    protected  void calculateScores2() {
        if(storeColorName != storeActualColor) {
            Score += 100;
            numberWrong++;
            scoreTextView.setText(Integer.toString(Score));
            wrongNumberView.setText(Integer.toString(numberWrong));
        }
        changeText();
    }

}
