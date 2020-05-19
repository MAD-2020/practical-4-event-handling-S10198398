package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */
    private TextView CountTextView;

    private Button Button1;
    private Button Button2;
    private Button Button3;
    private Integer score = 0;

    final String TAG = "WhackAMole 2.0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CountTextView = (TextView) findViewById(R.id.advancedScoreText);
        Button1 = (Button) findViewById(R.id.Button1);
        Button2 = (Button) findViewById(R.id.Button2);
        Button3 = (Button) findViewById(R.id.Button3);
        final List<Button> buttonList = new ArrayList<>();
        buttonList.add(Button1);
        buttonList.add(Button2);
        buttonList.add(Button3);
        setNewMole(buttonList);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<3; i++) {
                    if (buttonList.get(i).getId() == v.getId()) {
                        if(i == 0){
                            Log.v(TAG, "Button, Left clicked");
                            doCheck(Button1);
                        }
                        else if (i == 1){
                            Log.v(TAG, "Button, middle clicked");
                            doCheck(Button2);
                        }
                        else if (i == 2){
                            Log.v(TAG, "Button, right clicked");
                            doCheck(Button3);
                        }
                    }
                }

                CountTextView.setText(score.toString());
                setNewMole(buttonList);
            }
        };
        Button1.setOnClickListener(listener);
        Button2.setOnClickListener(listener);
        Button3.setOnClickListener(listener);
        Log.v(TAG, "Finished Pre-Initialisation!");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */
        if(checkButton.getText() == "*"){
            Log.v(TAG, "Hit, score added!");
            score += 1;
        }
        else {
            Log.v(TAG, "Missed, score deducted");
            score -= 1;
        }

        if (score >= 10){
            nextLevelQuery();
        }
        CountTextView.setText(String.valueOf(score));

    }

    private void nextLevelQuery(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Warning! Insane Whack-A-Mole incoming!");
        builder.setMessage("Would you like to advance to advanced mode?");

        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User accepts!");
                nextLevel(score);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User decline!");


            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        Log.v(TAG, "Advanced option given to user!");


    }

    private void nextLevel(int score){

        Intent levelUpTwo =  new Intent(MainActivity.this, Main2Activity.class);
        levelUpTwo.putExtra("score", score);
        startActivity(levelUpTwo);
    }

    private void setNewMole(List<Button> buttonList) {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        for(int i = 0; i < 3; i++){
            if(i == randomLocation){
                buttonList.get(i).setText("*");
            }
            else {
                buttonList.get(i).setText("O");
            }
        }
    }
}