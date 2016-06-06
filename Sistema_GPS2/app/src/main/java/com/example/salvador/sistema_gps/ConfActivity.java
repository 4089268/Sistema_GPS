package com.example.salvador.sistema_gps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ConfActivity extends AppCompatActivity {

    EditText ip1oct,ip2oct,ip3oct,ip4oct,minutos,transaccion,estado;
    Button config,guardar,cancelar;
    Spinner camiones_lst;
    ToggleButton servicio_btn;

    ConexionServicioWeb con;
    private ProgressDialog dialogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf);

        inicializarElementos();

    }

    private void cargarListaCamiones(String[] camiones){
        camiones_lst.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,camiones));
    }

    private void inicializarElementos(){
        ip1oct = (EditText)findViewById(R.id.ip1);
        ip2oct = (EditText)findViewById(R.id.ip2);
        ip3oct = (EditText)findViewById(R.id.ip3);
        ip4oct = (EditText)findViewById(R.id.ip4);
        minutos =(EditText)findViewById(R.id.mints);
        camiones_lst = (Spinner) findViewById(R.id.lista_camiones);
        transaccion = (EditText)findViewById(R.id.transac);
        estado = (EditText)findViewById(R.id.estado);

        config= (Button)findViewById(R.id.config);
        guardar=(Button)findViewById(R.id.btn_guardar);
        cancelar = (Button)findViewById(R.id.btn_cancelar);

        servicio_btn = (ToggleButton) findViewById(R.id.btn_serv);
        activarElementos(false);


        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarConf_Btn();
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar_Btn();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar_Btn();
            }
        });
        servicio_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Servicio_btn();
            }
        });

        con = new ConexionServicioWeb("192.168.173.1");
        mostrarMensaje("hola");
        new asyResObtenerCamiones().execute();

    }

    private void editarConf_Btn(){
        activarElementos(true);
        config.setEnabled(false);
        servicio_btn.setEnabled(false);
    }
    private void guardar_Btn(){
        activarElementos(false);
        config.setEnabled(true);
        servicio_btn.setEnabled(true);
    }
    private void cancelar_Btn(){
        activarElementos(false);
        config.setEnabled(true);
        servicio_btn.setEnabled(true);
    }

    private Boolean elementosOk(){
        Boolean ok = true;

        if(ip1oct.getText() != null && ip1oct.getText().toString() != " "){
            ok = false;
        }
        if(ip2oct.getText() != null && ip2oct.getText().toString() != " " && ok){
            ok = false;
        }
        if(ip3oct.getText() != null && ip3oct.getText().toString() != " " && ok){
            ok = false;
        }
        if(ip4oct.getText() != null && ip4oct.getText().toString() != " " && ok){
            ok = false;
        }
        if(minutos.getText() != null && minutos.getText().toString() != " " && ok){
            ok = false;
        }

        return true;
    }

    private void Servicio_btn(){

        if(servicio_btn.isChecked() && elementosOk()){
            Intent intent = new Intent(ConfActivity.this,ServiceGPS.class);
            //Bundle bundle = new Bundle();
            //bundle.putString("ip","192.168.173.1");
            //bundle.putInt("camon",3);
            //bundle.putInt("trasnsaccion",9);
            //intent.putExtras(bundle);

            startService(intent);

            activarElementos(false);
            config.setEnabled(false);

        }else{
            stopService(new Intent(ConfActivity.this, ServiceGPS.class));
            config.setEnabled(true);
        }
    }

    private void activarElementos(Boolean x){
        ip1oct.setEnabled(x);
        ip2oct.setEnabled(x);
        ip3oct.setEnabled(x);
        ip4oct.setEnabled(x);
        minutos.setEnabled(x);
        camiones_lst.setEnabled(x);
        transaccion.setEnabled(x);
        estado.setEnabled(x);

        guardar.setEnabled(x);
        cancelar.setEnabled(x);

        if(x){
            guardar.setVisibility(View.VISIBLE);
            cancelar.setVisibility(View.VISIBLE);

        }else{
            guardar.setVisibility(View.INVISIBLE);
            cancelar.setVisibility(View.INVISIBLE);
        }

    }


    public void mostrarMensaje(String texto){
        Toast.makeText(this,texto, Toast.LENGTH_SHORT).show();

    }

    class asyResObtenerCamiones extends AsyncTask<String,String,String> {
        @Override

        protected void onPreExecute() {
            dialogo = new ProgressDialog(ConfActivity.this);
            dialogo.setMessage("Cargando lsita de Camiones disponibles...");
            dialogo.setIndeterminate(false);
            dialogo.setCancelable(false);
            dialogo.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String [] c = con.ObtenerCamiones();
            if (c != null){
                cargarListaCamiones(c);
                return "ok";
            }else{
                return "err";
            }


        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            dialogo.dismiss();
            if(s.equals("ok")){
                mostrarMensaje("CoordenadasOk");

            }else {
                mostrarMensaje("Error mostrar coordenadas");
            }

        }
    }

    class asyResObtenerObtenerUltTrans extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            dialogo = new ProgressDialog(ConfActivity.this);
            dialogo.setMessage("Cargando lsita de Camiones disponibles...");
            dialogo.setIndeterminate(false);
            dialogo.setCancelable(false);
            dialogo.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String [] c = con.ObtenerCamiones();
            if (c != null){
                cargarListaCamiones(c);
                return "ok";
            }else{
                return "err";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            dialogo.dismiss();
            if(s.equals("ok")){
                mostrarMensaje("CoordenadasOk");

            }else {
                mostrarMensaje("Error mostrar coordenadas");
            }

        }
    }


}
