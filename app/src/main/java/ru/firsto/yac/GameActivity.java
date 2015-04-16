package ru.firsto.yac;

import android.support.v4.app.Fragment;

/**
 * Created by razor on 16.04.15.
 * Main Game Activity
 */
public class GameActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new GameFragment();
    }
}
