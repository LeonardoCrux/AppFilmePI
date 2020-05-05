package com.pi.efilm.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.pi.efilm.R;
import com.pi.efilm.util.AppUtil;
import java.util.Arrays;

import io.reactivex.annotations.NonNull;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout inputSenha, inputEmail;
    private TextView textRegistrar;
    private Button botaoLogin, botaoGoogle, botaoFacebook;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);


        botaoLogin.setOnClickListener(v -> validarLogin());

        textRegistrar.setOnClickListener(v -> clickRegistrar());
        botaoFacebook.setOnClickListener(v -> {
            loginFacebook();
        });

        botaoGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    private void loginFacebook(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(getString(R.string.permission_profile)));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        irParaHome(loginResult.getAccessToken().getUserId());

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), R.string.cancelado_usuario, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Snackbar.make(botaoFacebook, getString(R.string.erro_logar_facebook) + exception.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }


    private void initView(){
        inputEmail = findViewById(R.id.inputEmail);
        inputSenha = findViewById(R.id.inputSenha);
        botaoLogin = findViewById(R.id.botaoLogin);
        botaoGoogle = findViewById(R.id.botaoGoogle);
        botaoFacebook = findViewById(R.id.botaoFacebook);
        textRegistrar = findViewById(R.id.textRegistrar);
        callbackManager = CallbackManager.Factory.create();
    }

    private void validarLogin(){
        String email = inputEmail.getEditText().getText().toString();
        String senha = inputSenha.getEditText().getText().toString();
        validarCampos(email, senha);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                irParaHome(FirebaseAuth.getInstance().getUid());
            } else {
                Snackbar.make(botaoLogin, R.string.email_invalido, Snackbar.LENGTH_SHORT).show();
                inputEmail.requestFocus();
                inputSenha.requestFocus();
            }
        });

    }

    private void irParaHome(String userId){
        AppUtil.salvarIdUsuario(this, userId);
        startActivity(new Intent(this, MainActivity.class));

    }


    private void clickRegistrar(){
        startActivity(new Intent(this, RegistroActivity.class));
    }

    private void validarCampos(String email, String senha){
        if(email.isEmpty()){
            Toast.makeText(this, R.string.digite_email, Toast.LENGTH_SHORT).show();
            inputEmail.requestFocus();
            return;
        } if (senha.isEmpty()){
            Toast.makeText(this, R.string.digite_senha, Toast.LENGTH_SHORT).show();
            inputSenha.requestFocus();
            return;
        }
        if(!AppUtil.segurançaSenha(senha)){
            Toast.makeText(this, R.string.alerta_senha, Toast.LENGTH_SHORT).show();
            inputSenha.requestFocus();
            return;
        }
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("LOGLOGIN", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("LOGLOGIN", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("LOGLOGIN", "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LOGLOGIN", "signInWithCredential:failure", task.getException());
                            Snackbar.make(botaoFacebook, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    }
}
