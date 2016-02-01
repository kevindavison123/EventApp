package com.angry.tech.eventapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/*
    Designates what tabs are created, and their title of the text.
    Manages all the tabs.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return AllEventsFragment.newInstance(position + 1);
            case 1:
                return ThisWeekEventsFragment.newInstance(position + 1);
            case 2:
                return MovieEventsFragment.newInstance(position + 1);
            default:
                return AllEventsFragment.newInstance(position + 1);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "All";
        else if (position == 1)
            return "Zoo Flicks";
        else
            return "Zoo After Dark";
    }
}