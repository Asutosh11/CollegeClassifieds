package com.example.asu.collegeclassifieds;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	final int PAGE_COUNT = 3;
	// Tab Titles
	private String tabtitles[] = new String[] { "Electronics", "Study Materials", "Others" };
	Context context;

	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {

			// Open FragmentTab1.java
		case 0:
			ClassifiedsListingFragment fragmenttab1 = new ClassifiedsListingFragment();
			return fragmenttab1;

			// Open FragmentTab2.java
		case 1:
			ClassifiedsListingFragment2 fragmenttab2 = new ClassifiedsListingFragment2();
			return fragmenttab2;

			// Open FragmentTab3.java
		case 2:
			ClassifiedsListingFragment3 fragmenttab3 = new ClassifiedsListingFragment3();
			return fragmenttab3;
		}
		return null;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return tabtitles[position];
	}
}
