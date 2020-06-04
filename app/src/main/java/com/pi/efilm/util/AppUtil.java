package com.pi.efilm.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pi.efilm.view.activity.MainActivity;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.security.MessageDigest;

import static com.pi.efilm.util.Constantes.NOME_IMAGEM;

public class AppUtil {

    public static void salvarIdUsuario(Context context, String userId) {
        SharedPreferences preferences = context.getSharedPreferences("APP", Context.MODE_PRIVATE);
        preferences.edit().putString("UID", userId).apply();
    }

    public static boolean verificarLogado(){
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            return true;
        } return false;
    }

    public static boolean verificaConexaoComInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;

        if (connectivityManager != null){
            networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected() &&
                    (networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                            || networkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
        }

        return false;
    }

    public static void botaoHome(Context context){
        context.startActivity(new Intent(context, MainActivity.class));
    }

    public static void compartilhar(String stringCompartilhar, ProgressBar progressBar, Context context){
        Intent sendIntent = new Intent();
        progressBar.setVisibility(View.VISIBLE);
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, stringCompartilhar);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        context.startActivity(shareIntent);
    }



    public static boolean segurançaSenha(String senha){
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

    public static String getIdUsuario(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("APP", Context.MODE_PRIVATE);
        Log.i("TAG", "getIdUsuario: " + preferences.toString());
        return preferences.getString("UID", "");

    }

    public static void salvarImagemFirebase(InputStream stream, Context context, ImageView view) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(AppUtil.getIdUsuario(context) + "image/profile" + NOME_IMAGEM);
        if (stream == null) {
            context.startActivity(new Intent(context, MainActivity.class));
            return;
        }
        UploadTask uploadTask = storageReference.putStream(stream);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            storageReference.getDownloadUrl().addOnSuccessListener(uri -> {Picasso.get().load(uri).into(view);});
//            Picasso.get().load(storageReference.getDownloadUrl().toString());
            Toast.makeText(context, "Foto alterada", Toast.LENGTH_LONG).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
        });
    }


    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("TAG", "printHashKey() -> Hash Key: " + hashKey);
            }
        } catch (Exception e) {
            Log.e("TAG", "printHashKey() -> Error: ", e);
        }
    }
}
