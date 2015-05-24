package ru.firsto.yac;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
    Button mStopButton, mProgressButton, mShopButton, mStatsButton;
    GameTask gameTask;
    boolean isGameStarted = true;
    boolean isShow;
    int m = 0;

    FragmentTransaction ft;
    GameFragment mGameFragment;
    SurfaceFragment mSurfaceFragment;
    AchievementGridFragment mAchievementGridFragment;
    StatisticsFragment mStatisticsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResourcesCounter = (TextView) findViewById(R.id.counter_resourses);
        mIncomeCounter = (TextView) findViewById(R.id.counter_income);
        mStopButton = (Button) findViewById(R.id.stopgame);
        mProgressButton = (Button) findViewById(R.id.progress_button);
        mShopButton = (Button) findViewById(R.id.shop_button);
        mStatsButton = (Button) findViewById(R.id.stats_button);

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

        isShow = true;
        mShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = getSupportFragmentManager().beginTransaction();
                if (isShow) {
                    ft.replace(R.id.fragmentContainer, surfaceFragment());
                    isShow = !isShow;
                } else {
                    ft.replace(R.id.fragmentContainer, createFragment());
                    isShow = !isShow;
                }
                ft.commit();
            }
        });

        mProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft = getSupportFragmentManager().beginTransaction();
                if (isShow) {
                    ft.replace(R.id.fragmentContainer, progressFragment());
                    isShow = !isShow;
                } else {
                    ft.replace(R.id.fragmentContainer, createFragment());
                    isShow = !isShow;
                }
                ft.commit();
            }
        });

        mStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft = getSupportFragmentManager().beginTransaction();
                if (isShow) {
                    ft.replace(R.id.fragmentContainer, statisticsFragment());
                    isShow = !isShow;
                } else {
                    ft.replace(R.id.fragmentContainer, createFragment());
                    isShow = !isShow;
                }
                ft.commit();
            }
        });

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Toast.makeText(this,"onResume",Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        Toast.makeText(this,"onConfigurationChanged",Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"onDestroy",Toast.LENGTH_SHORT).show();
        gameTask.cancel(true);
    }

    @Override
    protected Fragment createFragment() {
        if (mGameFragment == null) {
            mGameFragment = new GameFragment();
        }
        return mGameFragment;
    }

    protected Fragment surfaceFragment() {
        if (mSurfaceFragment == null) {
            mSurfaceFragment = new SurfaceFragment();
        }
        return mSurfaceFragment;
    }

    protected Fragment progressFragment() {
        if (mAchievementGridFragment == null) {
            mAchievementGridFragment = new AchievementGridFragment();
        }
        return mAchievementGridFragment;
    }

    protected Fragment statisticsFragment() {
        if (mStatisticsFragment == null) {
            mStatisticsFragment = new StatisticsFragment();
        }
        return mStatisticsFragment;
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
