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
import ru.firsto.yac.Util.Saver;

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
    int m = 0;

    FragmentTransaction ft;
    ShopFragment mShopFragment;
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

//        boolean newgame = getIntent().getBooleanExtra("NEW GAME", false);
//        Toast.makeText(this, "new game " + newgame, Toast.LENGTH_SHORT).show();

//        mGame = Game.get();
        mGame = Game.get().newGame(getIntent().getBooleanExtra("NEW GAME", false));

        gameTask = new GameTask();
        gameTask.execute();

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGameStarted) {
                    isGameStarted = false;
                    gameTask.cancel(true);
                    mStopButton.setText("START");
                } else {
                    isGameStarted = true;
                    gameTask = new GameTask();
                    gameTask.execute();
                    mStopButton.setText("STOP");
                }
            }
        });

        mShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = getSupportFragmentManager().beginTransaction();
                if (mShopFragment == null || !mShopFragment.isVisible()) {
                    ft.replace(R.id.fragmentContainer, shopFragment());
                } else {
                    ft.replace(R.id.fragmentContainer, createFragment());
                }

                ft.commit();
            }
        });

        mProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft = getSupportFragmentManager().beginTransaction();
                if (mAchievementGridFragment == null || !mAchievementGridFragment.isVisible()) {
                    ft.replace(R.id.fragmentContainer, progressFragment());
                } else {
                    ft.replace(R.id.fragmentContainer, createFragment());
                }
                ft.commit();
            }
        });

        mStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft = getSupportFragmentManager().beginTransaction();
                if (mStatisticsFragment == null || !mStatisticsFragment.isVisible()) {
                    ft.replace(R.id.fragmentContainer, statisticsFragment());
                } else {
                    ft.replace(R.id.fragmentContainer, createFragment());
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
//        Toast.makeText(this,"onDestroy",Toast.LENGTH_SHORT).show();
        saveGame();
        gameTask.cancel(true);
    }

    @Override
    protected Fragment createFragment() {
        return surfaceFragment();
    }

    protected Fragment shopFragment() {
        if (mShopFragment == null) {
            mShopFragment = new ShopFragment();
        }
        return mShopFragment;
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

    protected void saveGame() {
        Saver.save(getApplicationContext(), Game.get().getResources(), Game.get().getStartgame(), ItemPool.get().getLevels(), Statistics.get().statistics());
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
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
                if (f != null && f instanceof ShopFragment) {
                    ((ShopFragment) f).updateList();
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
