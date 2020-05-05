package com.pi.efilm.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pi.efilm.R;
import com.pi.efilm.util.AppUtil;
import com.pi.efilm.view.activity.FavoritosActivity;
import com.pi.efilm.view.activity.LoginActivity;
import com.pi.efilm.view.activity.MainActivity;
import com.pi.efilm.view.activity.PerfilActivity;

public class MaisFragment extends Fragment {
    private TextView textFavoritos, textLogin, textLogout;


    public MaisFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mais, container, false);
        initView(view);
        textFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FavoritosActivity.class));
            }
        });

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        textLogout.setOnClickListener(v -> startActivity(new Intent(getContext(), PerfilActivity.class)));
        textLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            startActivity(new Intent(getContext(), MainActivity.class));
        });

        return view;
    }

    private void initView(View view){
        textFavoritos = view.findViewById(R.id.textFavoritos);
        textLogin = view.findViewById(R.id.textView6);
        textLogout = view.findViewById(R.id.textLogout);
    }
}
