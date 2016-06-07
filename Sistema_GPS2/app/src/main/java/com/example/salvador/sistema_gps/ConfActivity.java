package com.example.salvador.sistema_gps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ConfActivity extends AppCompatActivity {

    EditText minutos,transaccion;
    Button config,guardar,cancelar;
    Spinner camiones_lst;
    ToggleButton servicio_btn;

    ConexionServicioWeb con;
    ProgressDialog dialogo;

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

        minutos =(EditText)findViewById(R.id.mints);
        camiones_lst = (Spinner) findViewById(R.id.lista_camiones);
        transaccion = (EditText)findViewById(R.id.transac);

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

        con = new ConexionServicioWeb();
        new asyResObtenerCamiones().execute();

        transaccion.setText("1");

        camiones_lst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //new asyResObtenerObtenerUltTrans().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        minutos.setEnabled(x);
        camiones_lst.setEnabled(x);
        transaccion.setEnabled(x);

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

    private int obtenerViaje(){
        int r = con.ObtenerUltimaTrans(Integer.parseInt(camiones_lst.getSelectedItem().toString()));
        try{
            if (r != -1){
                transaccion.setText(""+r);
            }
        }
        catch (Exception e){};
        return r;
    }

    public void mostrarMensaje(String texto){
        Toast.makeText(this,texto, Toast.LENGTH_SHORT).show();

    }






    class asyResObtenerCamiones extends AsyncTask<String,String,String> {
        @Override

        protected void onPreExecute() {
            dialogo = new ProgressDialog(ConfActivity.this);
            dialogo.setMessage("Cargando datos...");
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
            if(s.equals("err")){
                mostrarMensaje("Error al cargar lista de camiones");

            }

        }
    }

    class asyResObtenerObtenerUltTrans extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            dialogo = new ProgressDialog(ConfActivity.this);
            dialogo.setMessage("Cargando viajes...");
            dialogo.setIndeterminate(false);
            dialogo.setCancelable(false);
            dialogo.show();
        }

        @Override
        protected String doInBackground(String... params) {
            if (obtenerViaje() != -1 ){
                return "ok";
            }else {
                return "err";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            dialogo.dismiss();

            if(s.equals("err")){
                mostrarMensaje("Error cargar ultimo viaje...");
            }
        }
    }


}
