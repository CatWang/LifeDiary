package com.id12533030.lifediary.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.id12533030.lifediary.R;
import com.id12533030.lifediary.model.Homepage;
import com.id12533030.lifediary.util.ImageTool;

import java.io.IOException;

/**
 * Created by LENOVO on 2016/5/29.
 */
public class MainFragment extends Fragment {

    private Homepage mHomepage;
    private static final String PAGE_KEY = "PAGE";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public static MainFragment newInstance(Homepage homepage) {
        MainFragment fragment = new MainFragment();

        // Supply homepage input as an argument.
        Bundle args = new Bundle();
        args.putSerializable(PAGE_KEY, homepage);
        fragment.setArguments(args);


        return fragment;
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomepage = getArguments() != null ? (Homepage) getArguments().getSerializable(PAGE_KEY) : Homepage.findById(Homepage.class, 1);
    }

    @Override
    public void onStart() {
        super.onStart();
        ImageView imageView = (ImageView) this.getView().findViewById(R.id.fragment_main_photo_imageview);
        try {
            ImageTool.showImage(mHomepage.getmPhotoUrl(), imageView);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextView textView = (TextView) this.getView().findViewById(R.id.fragment_main_title_textview);
        textView.setText(mHomepage.getTitle());

    }


}
