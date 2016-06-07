package com.example.salvador.sistema_gps;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {
    EditText acount,pass;
    ConexionServicioWeb con;
    private ProgressDialog dialogo;

    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn = (Button)findViewById(R.id.signinbtn);
        btn.setOnClickListener(this);

        acount = (EditText)findViewById(R.id.edt_acccnt);
        pass = (EditText)findViewById(R.id.adt_pass);

    }

    @Override
    public void onClick(View v) {

        if (!acount.getText().toString().equals("") && !pass.getText().toString().equals("")) {

            new asyRes().execute();
        }else{
            mostrarMensaje("Faltan datos...");
        }

    }


    private int ComprobarUsuario(){

        con = new ConexionServicioWeb();
        int res = con.VerificarUsuario(acount.getText().toString(), Integer.parseInt(pass.getText().toString()));
        return res;
    }
    private void iniciarApp(){
        Intent i = new Intent(this, ConfActivity.class);
        startActivity(i);
    }


    public void mostrarMensaje(String texto){
        Toast.makeText(this,texto, Toast.LENGTH_SHORT).show();

    }


    class asyRes extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            dialogo = new ProgressDialog(LoginActivity.this);
            dialogo.setMessage("Comprobando Datos...");
            dialogo.setIndeterminate(false);
            dialogo.setCancelable(false);
            dialogo.show();
        }

        @Override
        protected String doInBackground(String... params) {

            if (ComprobarUsuario()== 1){
                return "ok";
            }else{
                return "err";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            dialogo.dismiss();
            if (s.equals("ok")){
                iniciarApp();
            }else {
                mostrarMensaje("Usuario y/o contraseña incorrectos");
            }



        }

    }


}

