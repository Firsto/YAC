package ru.firsto.yac;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by razor on 28.04.15.
 * Surface
 **/
public class SurfaceFragment extends Fragment {

    private SurfaceView mSurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        mSurfaceView.setVisibility(View.VISIBLE);
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mSurfaceView.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_surface, container, false);

//        mSurfaceView = (SurfaceView) view.findViewById(R.id.game_surface);
        mSurfaceView = (SurfaceView) view.findViewById(R.id.game_surface);


        return view;
//        return inflater.inflate(R.layout.fragment_surface, null);
//        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
