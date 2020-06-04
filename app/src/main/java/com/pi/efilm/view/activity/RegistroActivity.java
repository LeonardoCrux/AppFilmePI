package com.pi.efilm.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pi.efilm.R;
import com.pi.efilm.util.AppUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static com.pi.efilm.util.Constantes.NOME_IMAGEM;

public class RegistroActivity extends AppCompatActivity {
    private TextInputLayout inputSenha, inputEmail, inputConfirmar, inputNome;
    private Button botaoRegistrar;
    private ProgressBar progressBar;
    private CircleImageView imagemCadastrar;
    private static final int PERMISSION_CODE = 101;
    private InputStream inputStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        initView();
        botaoRegistrar.setOnClickListener(v -> validarLogin());
        imagemCadastrar.setOnClickListener(v -> capturaImagem());
    }


    private void initView() {
        inputSenha = findViewById(R.id.inputSenhaRegistro);
        inputEmail = findViewById(R.id.inputEmailRegistro);
        inputConfirmar = findViewById(R.id.inputConfirmarSenha);
        botaoRegistrar = findViewById(R.id.botaoRegistrar);
        inputNome = findViewById(R.id.inputNome);
        imagemCadastrar = findViewById(R.id.imagemcadastrar);
        progressBar = findViewById(R.id.progressBarRegistro);
    }

    private void validarLogin() {
        String nome = inputNome.getEditText().getText().toString();
        String email = inputEmail.getEditText().getText().toString();
        String senha = inputSenha.getEditText().getText().toString();
        String confirmar = inputConfirmar.getEditText().getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(this, R.string.digite_email, Toast.LENGTH_SHORT).show();
            inputEmail.requestFocus();
            return;
        }
        if (senha.isEmpty()) {
            Toast.makeText(this, R.string.digite_senha, Toast.LENGTH_SHORT).show();
            inputSenha.requestFocus();
            return;
        }
        if (nome.isEmpty()) {
            Toast.makeText(this, R.string.digite_nome, Toast.LENGTH_SHORT).show();
            inputNome.requestFocus();
            return;
        }
        if (!senha.equals(confirmar)) {
            Toast.makeText(this, R.string.senha_nao_confere, Toast.LENGTH_SHORT).show();
            inputConfirmar.requestFocus();
            return;
        }
        if (!AppUtil.segurançaSenha(senha)) {
            Toast.makeText(this, R.string.requisitos_senha, Toast.LENGTH_SHORT).show();
            inputSenha.requestFocus();
            return;
        }
        registrarUsuario(nome, email, senha);
    }

    private void capturaImagem() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            EasyImage.openCameraForImage(this, MODE_PRIVATE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
        }
    }

    private void registrarUsuario(String nome, String email, String senha) {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(nome).build();


                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        AppUtil.salvarIdUsuario(getApplicationContext(), FirebaseAuth.getInstance().getCurrentUser().getUid());
                        salvarImagemFirebase(inputStream);
                    }
                });

            } else {
                Snackbar.make(botaoRegistrar, getString(R.string.erro_cadastrar) + task.getException().getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
            }
            progressBar.setVisibility(View.INVISIBLE);

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        capturaImagem();
    }

    private void salvarImagemFirebase(InputStream stream) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(AppUtil.getIdUsuario(this) + "image/profile" + NOME_IMAGEM);
        if (stream == null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            return;
        }
        UploadTask uploadTask = storageReference.putStream(stream);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            startActivity(new Intent(this, MainActivity.class));
        }).addOnFailureListener(e -> {
            Snackbar snackbar = Snackbar.make(imagemCadastrar, e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                for (File file : imageFiles) {
                    try {
                        inputStream = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
