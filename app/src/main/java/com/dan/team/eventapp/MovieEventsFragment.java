package com.dan.team.eventapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MovieEventsFragment extends Fragment {
    private static final String ARG_PAGE_NUMBER = "page_number";

    public MovieEventsFragment() {
    }

    public static MovieEventsFragment newInstance(int page) {
        MovieEventsFragment fragment = new MovieEventsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movieevents_layout, container, false);

//        TextView txt = (TextView) rootView.findViewById(R.id.page_number_label);
//        int page = getArguments().getInt(ARG_PAGE_NUMBER, -1);
//        txt.setText(String.format("Page %d", page));

        return rootView;
    }
}