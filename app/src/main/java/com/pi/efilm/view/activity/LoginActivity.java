package com.pi.efilm.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.google.firebase.auth.FacebookAuthProvider;
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
    private ProgressBar progressBar;
    private Button botaoLogin, botaoGoogle, botaoFacebook;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

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

    private void loginFacebook() {
        progressBar.setVisibility(View.VISIBLE);
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(getString(R.string.permission_profile), getString(R.string.email)));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
                        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(task -> {
                            if (task.isSuccessful())
                                irParaHome(firebaseAuth.getCurrentUser().getUid());
                            else
                                Snackbar.make(botaoFacebook, R.string.erro_facebook_email, Snackbar.LENGTH_SHORT).show();

                            progressBar.setVisibility(View.INVISIBLE);

                        });
                    }

                    @Override
                    public void onCancel() {
                        progressBar.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onError(FacebookException error) {
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                });
    }

    private void initView() {
        inputEmail = findViewById(R.id.inputEmail);
        inputSenha = findViewById(R.id.inputSenha);
        botaoLogin = findViewById(R.id.botaoLogin);
        botaoGoogle = findViewById(R.id.botaoGoogle);
        botaoFacebook = findViewById(R.id.botaoFacebook);
        textRegistrar = findViewById(R.id.textRegistrar);
        callbackManager = CallbackManager.Factory.create();
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBarLogin);
    }

    private void validarLogin() {
        String email = inputEmail.getEditText().getText().toString();
        String senha = inputSenha.getEditText().getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        validarCampos(email, senha);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                irParaHome(FirebaseAuth.getInstance().getUid());
            } else {
                Snackbar.make(botaoLogin, R.string.email_invalido, Snackbar.LENGTH_SHORT).show();
                inputEmail.requestFocus();
                inputSenha.requestFocus();
            }
            progressBar.setVisibility(View.INVISIBLE);

        });
    }

    private void irParaHome(String userId) {
        AppUtil.salvarIdUsuario(this, userId);
        startActivity(new Intent(this, MainActivity.class));

    }

    private void clickRegistrar() {
        startActivity(new Intent(this, RegistroActivity.class));
    }

    private void validarCampos(String email, String senha) {
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
        if (!AppUtil.segurançaSenha(senha)) {
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
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        progressBar.setVisibility(View.VISIBLE);

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Snackbar.make(botaoFacebook, R.string.autenticacao_falhou, Snackbar.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.INVISIBLE);

                    }

                });
    }
}
