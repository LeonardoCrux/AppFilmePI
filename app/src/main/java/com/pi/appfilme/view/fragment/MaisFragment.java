package com.pi.appfilme.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pi.appfilme.R;
import com.pi.appfilme.view.activity.FavoritosActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaisFragment extends Fragment {
    private TextView textFavoritos;


    public MaisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mais, container, false);
        textFavoritos = view.findViewById(R.id.textFavoritos);
        textFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FavoritosActivity.class));
            }
        });


        return view;
    }
}
