package com.pi.appfilme.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pi.appfilme.R;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout inputSenha, inputEmail;
    private Button botaoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarLogin();
            }
        });

    }

    private void initView(){
        inputEmail = findViewById(R.id.inputEmail);
        inputSenha = findViewById(R.id.inputSenha);
        botaoLogin = findViewById(R.id.botaoLogin);
    }

    private void validarLogin(){
        String email = inputEmail.getEditText().getText().toString();
        String senha = inputSenha.getEditText().getText().toString();
        if(email.isEmpty()){
            Toast.makeText(this, "Digite seu email", Toast.LENGTH_SHORT).show();
            inputEmail.requestFocus();
            return;
        } if (senha.isEmpty()){
            Toast.makeText(this, "Digite sua senha", Toast.LENGTH_SHORT).show();
            inputSenha.requestFocus();
            return;
        }
        if(!segurançaSenha(senha)){
            Toast.makeText(this, "A senha deve conter ao menos uma letra maiúscula e um numero", Toast.LENGTH_SHORT).show();
            inputSenha.requestFocus();
            return;
        }
        startActivity(new Intent(this, MainActivity.class));
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
}
