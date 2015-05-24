package ru.firsto.yac;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by razor on 24.05.15.
 * Statistics fragment
 */
public class StatisticsFragment extends Fragment {

    private HashMap<String, String> stats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistics, container, false);

        stats = Statistics.get().statistics();

        ((TextView) v.findViewById(R.id.stat_gametime)).setText(stats.get(Statistics.STATS_GAMETIME));
        ((TextView) v.findViewById(R.id.stat_clicks)).setText(stats.get(Statistics.STATS_CLICKS));
        ((TextView) v.findViewById(R.id.stat_items)).setText(stats.get(Statistics.STATS_ITEMS));
        ((TextView) v.findViewById(R.id.stat_achievements)).setText(stats.get(Statistics.STATS_ACHIEVEMENTS));
        ((TextView) v.findViewById(R.id.stat_boxesGenerated)).setText(stats.get(Statistics.STATS_BOXES_GENERATED));
        ((TextView) v.findViewById(R.id.stat_boxesCaught)).setText(stats.get(Statistics.STATS_BOXES_CAUGHT));

        return v;
    }
}
