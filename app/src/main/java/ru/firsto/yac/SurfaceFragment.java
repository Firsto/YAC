package ru.firsto.yac;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by razor on 28.04.15.
 * Surface
 **/
public class SurfaceFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_surface, null);
//        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
