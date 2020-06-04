package com.pi.efilm.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pi.efilm.R;
import com.pi.efilm.util.AppUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static com.pi.efilm.util.Constantes.IMAGEM_PROFILE;
import static com.pi.efilm.util.Constantes.NOME_IMAGEM;

public class PerfilActivity extends AppCompatActivity {
    private TextView nomePerfil, emailPerfil;
    private CircleImageView imagemPerfil;
    private Button botaoSair;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        initView();
        carregarPerfil(user);

        botaoSair.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            startActivity(new Intent(this, MainActivity.class));
        });

        imagemPerfil.setOnClickListener(v -> {
            capturaImagem();
        });

    }

    private void initView() {
        nomePerfil = findViewById(R.id.nomePerfil);
        emailPerfil = findViewById(R.id.emailPerfil);
        botaoSair = findViewById(R.id.botaoSair);
        imagemPerfil = findViewById(R.id.imagemPerfil);
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void carregarPerfil(FirebaseUser user) {
        if (user != null) {
            nomePerfil.setText(user.getDisplayName());
            emailPerfil.setText(user.getEmail());
            if (user.getPhotoUrl() != null) {
                String foto = user.getPhotoUrl().toString();
                Picasso.get().load(foto).into(imagemPerfil);
            } else {
                StorageReference storage = FirebaseStorage
                        .getInstance()
                        .getReference()
                        .child(AppUtil.getIdUsuario(this) + IMAGEM_PROFILE + NOME_IMAGEM);

                storage.getDownloadUrl()
                        .addOnSuccessListener(uri -> Picasso.get().load(uri).into(imagemPerfil));
            }
        }
    }

    private void capturaImagem() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            EasyImage.openCameraForImage(this, MODE_PRIVATE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        capturaImagem();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                for (File file : imageFiles) {
                    try {
                        InputStream inputStream = new FileInputStream(file);
                        AppUtil.salvarImagemFirebase(inputStream, getApplicationContext(), imagemPerfil);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
