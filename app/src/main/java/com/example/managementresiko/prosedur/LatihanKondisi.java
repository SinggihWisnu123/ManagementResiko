package com.example.managementresiko.prosedur;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.managementresiko.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LatihanKondisi.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LatihanKondisi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LatihanKondisi extends Fragment {

    public LatihanKondisi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_latihan_kondisi, container, false);
    }
}
