package com.example.ibeaconexperiencedays;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class IntroFragment extends Fragment {
	 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup bla, Bundle savedInstanceState) {
        View rootView2 = inflater.inflate(R.layout.intro, bla,false);
        return rootView2;
    }
}