package com.example.salvador.sistema_gps;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class ConfActivity extends AppCompatActivity {

    EditText ip1oct,ip2oct,ip3oct,ip4oct,minutos,transaccion,estado;
    Button config,guardar,cancelar;
    Spinner camiones_lst;
    ToggleButton servicio_btn;

    String[] datos = {"Camion1","Camion2","Camion3","Camion4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf);

        inicializarElementos();

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

        camiones_lst.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,datos));

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

    private void Servicio_btn(){

        if(servicio_btn.isChecked()){
            startService(new Intent(ConfActivity.this, ServiceGPS.class));

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


}
