package com.example.ibeaconexperiencedays.adapter;

import com.example.ibeaconexperiencedays.FloorPlanFragment;
import com.example.ibeaconexperiencedays.HelpFragment;
import com.example.ibeaconexperiencedays.InfoFragment;
import com.example.ibeaconexperiencedays.IntroFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new IntroFragment();
		case 1:
			// Games fragment activity
			return new HelpFragment();
		case 2:
			// Movies fragment activity
			return new FloorPlanFragment();
		case 3:
			// Movies fragment activity
			return new InfoFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}