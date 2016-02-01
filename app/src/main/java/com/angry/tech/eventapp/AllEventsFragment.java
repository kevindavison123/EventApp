package com.angry.tech.eventapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dan.team.eventapp.R;

/*
    This class is a fragment for the main page activity that retrieves All events from the database on the server
 */
public class AllEventsFragment extends Fragment {
    private static final String ARG_PAGE_NUMBER = "allEvents";

    public AllEventsFragment() {
    }

    public static AllEventsFragment newInstance(int page) {
        AllEventsFragment fragment = new AllEventsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_allevents_layout, container, false);
        MainPage mainPage = (MainPage) getActivity();
        mainPage.allEvents();


        return rootView;
    }


}