package ru.firsto.yac;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by razor on 22.05.15.
 * Achievement description
 */
public class AchievementFragment extends Fragment {

    String title, description;
    Button mButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString("title");
            description = bundle.getString("description");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.achievement_full_layout, container, false);

        ((TextView) v.findViewById(R.id.achievement_name)).setText(title);
        ((TextView) v.findViewById(R.id.achievement_description)).setText(description);
        mButton = (Button) v.findViewById(R.id.achievement_close);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });

        return v;
    }

    private void close() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

}
