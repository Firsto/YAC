package ru.firsto.yac;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by razor on 11.05.15.
 * Achievement grid
 */
public class AchievementGridFragment extends Fragment {
    GridView mGridView;

    AchievementGridAdapter mAdapter;
    ArrayList<Achievement> mAchievements = new ArrayList<Achievement>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (mAchievements.size() == 0) {
            mAchievements.add(new Achievement("First", "First description"));
            mAchievements.add(new Achievement("Second", "Second description"));
            mAchievements.add(new Achievement("Third", "Third description"));
            mAchievements.add(new Achievement("Fourth", "Fourth description"));
            mAchievements.add(new Achievement("Fifth", "Fifth description"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_achievement, container, false);

        mGridView = (GridView)v.findViewById(R.id.achievement_grid);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Achievement item = mAchievements.get(position);
                Toast.makeText(getActivity(), "clicked " + item.getTitle(), Toast.LENGTH_SHORT).show();

                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.achievement_container, new GameFragment()).commit();

//                Uri photoPageUri = Uri.parse(item.getPhotoPageUrl());
////                Intent intent = new Intent(Intent.ACTION_VIEW, photoPageUri);
//                Intent intent = new Intent(getActivity(), PhotoPageActivity.class);
//                intent.setData(photoPageUri);

//                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupAdapter();
    }

    void setupAdapter() {
        if (getActivity() == null || mGridView == null) return;
        if (mAchievements != null) {
            if (mAdapter == null) {
                mAdapter = new AchievementGridAdapter(mAchievements);
            }
            mGridView.setAdapter(mAdapter);
        } else {
            mGridView.setAdapter(null);
        }
    }

    private class AchievementGridAdapter extends ArrayAdapter<Achievement> {
        public AchievementGridAdapter(ArrayList<Achievement> items) {
            super(getActivity(), 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.achievement_layout, parent, false);
            }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.achievement_image);
            imageView.setImageResource(android.R.drawable.ic_media_play);
            TextView textView = (TextView) convertView.findViewById(R.id.achievement_name);
            textView.setText(mAchievements.get(position).getTitle());

            return convertView;
        }
    }
}
