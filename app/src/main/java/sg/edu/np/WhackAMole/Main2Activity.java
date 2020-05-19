package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.nfc.Tag;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */
    final String TAG = "Whack-A-Mole 2.0!";
    private TextView advancedScoreText;
    private int advancedScore;

    private void readyTimer() {

        final CountDownTimer myCountDown;
        myCountDown = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                final Toast makeToast =  Toast.makeText(Main2Activity.this, "Get Ready in " + l/1000 + " seconds!", Toast.LENGTH_SHORT);
                makeToast.show();
                Handler time = new Handler();
                time.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        makeToast.cancel();
                    }
                }, 1000);
                Log.v(TAG, "Ready Countdown!" + l / 1000);


            }

            @Override
            public void onFinish() {
                Log.v(TAG, "Ready Countdown Complete!");
                Toast.makeText(Main2Activity.this,"GO!",Toast.LENGTH_SHORT).show();
                placeMoleTimer();


            }
        };
        myCountDown.start();

    }


        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */

    private void placeMoleTimer(){
        final CountDownTimer myCountDown;
        myCountDown = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long l) {
                setNewMole();
                Log.v(TAG,"New Mole Location!");
            }
            @Override
            public void onFinish() {
                placeMoleTimer();
            }

        };
        myCountDown.start();
    }
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */

    private static final int[] BUTTON_IDS = {
            R.id.Button0,
            R.id.Button1,
            R.id.Button2,
            R.id.Button3,
            R.id.Button4,
            R.id.Button5,
            R.id.Button6,
            R.id.Button7,
            R.id.Button8
            /* HINT:
                Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
                You may use if you wish to change or remove to suit your codes.*/
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */
        Intent levelTwo = getIntent();
        advancedScore = levelTwo.getIntExtra("score", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.v(TAG, "Current User Score: " + String.valueOf(advancedScore));
        advancedScoreText = (TextView) findViewById(R.id.advancedScoreText);
        advancedScoreText.setText(String.valueOf(advancedScore));
        for(final int id : BUTTON_IDS){
            final Button button = (Button) findViewById(id);
            button.setText("O");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doCheck(button);

                }
            });
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
        }


    }
    @Override
    protected void onStart(){
        super.onStart();
        readyTimer();


    }
    private void doCheck(Button checkButton)
    {
        if (checkButton.getText() == "*")
        {
            Log.v(TAG,"Hit, score added!");
            advancedScore+=1;
        }
        else{

            Log.v(TAG, "Missed, point deducted!");
            advancedScore-=1;
        }
        advancedScoreText.setText(String.valueOf(advancedScore));
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */
    }

    public void setNewMole()
    {
        for (int id :BUTTON_IDS){
            Button b=findViewById(id);
            b.setText("O");
        }
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        Button selectedButton = findViewById(BUTTON_IDS[randomLocation]);
        selectedButton.setText("*");
    }
}

