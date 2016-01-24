package com.dan.team.eventapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
Another fragment for the main page, to display only the events posted for this week.
 */
public class ThisWeekEventsFragment extends Fragment {
    private static final String ARG_PAGE_NUMBER = "weeklyEvents";

    public ThisWeekEventsFragment() {
    }

    public static ThisWeekEventsFragment newInstance(int page) {
        ThisWeekEventsFragment fragment = new ThisWeekEventsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_thisweekevents_layout, container, false);
        MainPage mainPage = (MainPage) getActivity();
        mainPage.getWeekly();


        return rootView;
    }
}