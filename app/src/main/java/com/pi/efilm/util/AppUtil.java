package com.pi.efilm.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.pi.efilm.R;

import java.security.MessageDigest;

public class AppUtil {

    public static void salvarIdUsuario(Context context, String userId) {
        SharedPreferences preferences = context.getSharedPreferences("APP", Context.MODE_PRIVATE);
        preferences.edit().putString("UID", userId).apply();
    }

    public static boolean verificarLogado(){
        if (AccessToken.isCurrentAccessTokenActive() || FirebaseAuth.getInstance().getCurrentUser() != null){
            return true;
        } return false;
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
