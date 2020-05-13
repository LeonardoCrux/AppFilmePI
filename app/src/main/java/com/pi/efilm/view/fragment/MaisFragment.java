package com.pi.efilm.view.fragment;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.pi.efilm.R;
import com.pi.efilm.util.AppUtil;
import com.pi.efilm.view.activity.FavoritosActivity;
import com.pi.efilm.view.activity.ListaExpandidaActivity;
import com.pi.efilm.view.activity.LoginActivity;
import com.pi.efilm.view.activity.MainActivity;
import com.pi.efilm.view.activity.PerfilActivity;

import static com.pi.efilm.util.Constantes.BILHETERIAS;
import static com.pi.efilm.util.Constantes.CLICK;

public class MaisFragment extends Fragment {
    private TextView textFavoritos, textLogin, textLogout, textPerfil, textBilheteria;


    public MaisFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mais, container, false);
        initView(view);
        if(AppUtil.verificarLogado()){
            textPerfil.setVisibility(View.VISIBLE);
            textLogout.setVisibility(View.VISIBLE);
            textLogin.setVisibility(View.INVISIBLE);
        }
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

        textBilheteria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListaExpandidaActivity.class);
                intent.putExtra(CLICK , BILHETERIAS);
                startActivity(intent);
            }
        });

        textPerfil.setOnClickListener(v -> startActivity(new Intent(getContext(), PerfilActivity.class)));

        return view;
    }

    private void initView(View view){
        textFavoritos = view.findViewById(R.id.textFavoritos);
        textLogin = view.findViewById(R.id.textLoginMais);
        textLogout = view.findViewById(R.id.textLogout);
        textPerfil =  view.findViewById(R.id.perfilText);
        textBilheteria = view.findViewById(R.id.textBilheteria);
    }
}
