package com.dan.team.eventapp;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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