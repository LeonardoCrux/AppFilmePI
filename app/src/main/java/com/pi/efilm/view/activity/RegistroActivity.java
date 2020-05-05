package com.pi.efilm.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.pi.efilm.R;
import com.pi.efilm.util.AppUtil;

public class RegistroActivity extends AppCompatActivity {
    private TextInputLayout inputSenha, inputEmail, inputConfirmar;
    private Button botaoRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        initView();
        botaoRegistrar.setOnClickListener(v -> validarLogin());
    }

    private void initView(){
        inputSenha = findViewById(R.id.inputSenhaRegistro);
        inputEmail = findViewById(R.id.inputEmailRegistro);
        inputConfirmar =findViewById(R.id.inputConfirmarSenha);
        botaoRegistrar = findViewById(R.id.botaoRegistrar);
    }

    private void validarLogin(){
        String email = inputEmail.getEditText().getText().toString();
        String senha = inputSenha.getEditText().getText().toString();
        String confirmar = inputConfirmar.getEditText().getText().toString();
        if(email.isEmpty()){
            Toast.makeText(this, "Digite seu email", Toast.LENGTH_SHORT).show();
            inputEmail.requestFocus();
            return;
        } if (senha.isEmpty()){
            Toast.makeText(this, "Digite sua senha", Toast.LENGTH_SHORT).show();
            inputSenha.requestFocus();
            return;
        }
        if (!senha.equals(confirmar)){
            Toast.makeText(this, "A confirmação de senha não confere", Toast.LENGTH_SHORT).show();
            inputConfirmar.requestFocus();
            return;
        }
        if(!segurançaSenha(senha)){
            Toast.makeText(this, "A senha deve conter ao menos uma letra maiúscula e um numero", Toast.LENGTH_SHORT).show();
            inputSenha.requestFocus();
            return;
        }
        registrarUsuario(email, senha);
    }

    private boolean segurançaSenha(String senha){
        char charCheck;
        boolean maiscula = false;
        boolean numero = false;
        for(int i=0; i< senha.length(); i++){
            charCheck = senha.charAt(i);
            if(Character.isUpperCase(charCheck)){
                maiscula = true;
            } if (Character.isDigit(charCheck)){
                numero = true;
            }
            if(maiscula && numero){
                return true;
            }
        }
        return false;
    }

    private void registrarUsuario(String email, String senha) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                AppUtil.salvarIdUsuario(this, FirebaseAuth.getInstance().getCurrentUser().getUid());
                startActivity(new Intent(this, MainActivity.class));
            } else {
                Snackbar.make(botaoRegistrar, "Erro ao cadastrar usuário: " + task.getException().getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }
}
