package ru.firsto.yac;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import ru.firsto.yac.Util.Notation;

/**
 * Created by razor on 16.04.15.
 * Main Game Activity
 */
public class GameActivity extends SingleFragmentActivity {

    Game mGame;

    TextView mResourcesCounter, mIncomeCounter;
    Button mStopButton;
    GameTask gameTask;
    boolean isGameStarted = true;
    int m = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResourcesCounter = (TextView) findViewById(R.id.counter_resourses);
        mIncomeCounter = (TextView) findViewById(R.id.counter_income);
        mStopButton = (Button) findViewById(R.id.stopgame);

        mGame = Game.get();

        gameTask = new GameTask();
        gameTask.execute();

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGameStarted) {
                    isGameStarted = !isGameStarted;
                    gameTask.cancel(true);
                    mStopButton.setText("START");
                } else {
                    isGameStarted = !isGameStarted;
                    gameTask = new GameTask();
                    gameTask.execute();
                    mStopButton.setText("STOP");
                }
            }
        });
    }

    @Override
    protected Fragment createFragment() {
        return new GameFragment();
    }

    class GameTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                while (isGameStarted) {
                    TimeUnit.MILLISECONDS.sleep(100);
                    mGame.addResources(Math.round(mGame.getIncome() / 10));
                    publishProgress();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
//            mResourcesCounter.setText(String.valueOf(values[0]) + " b");
            mResourcesCounter.setText(Notation.get(mGame.getResources()));
            mIncomeCounter.setText(Notation.get(Math.round(mGame.getIncome())) + " bps");
            m++;
            if (m == 10) {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (f != null && f instanceof GameFragment) {
                    ((GameFragment) f).updateList();
                }
                m = 0;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Game Stopped - " + isGameStarted, Toast.LENGTH_SHORT).show();
        }
    }
}
