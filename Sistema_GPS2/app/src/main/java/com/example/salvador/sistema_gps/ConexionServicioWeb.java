package com.example.salvador.sistema_gps;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Salvador on 11/05/2016.
 */
public class ConexionServicioWeb {

    private static String url;
    private static  String suburl = "/SistemaRastreoGPS/Service.asmx?WSDL";
    private static  String namespace = "www.salvador.org";

    private static  String accionSoap1 = "www.salvador.org/AgregarCoordenadas";

    private static  String metodo1 = "AgregarCoordenadas";
    private static  String metodo2 = "ObtnerCamiones";
    private static  String metodo3 = "ObtnerUltimaTransacc";

    public ConexionServicioWeb(String ip) {
        url = "http://"+ip+suburl;

    }

    public Boolean AgregarCoordenadas(String altitud, String longitud, int hora, int minuto,int dia, int mes, int ano, int idcamion, int transac){

        try {
            SoapObject request = new SoapObject(namespace, metodo1);
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.dotNet = true;
            sobre.setOutputSoapObject(request);
            request.addProperty("altitud", altitud);
            request.addProperty("longitud", longitud);
            request.addProperty("hora", hora);
            request.addProperty("minuto", minuto);
            request.addProperty("dia", dia);
            request.addProperty("mes", mes);
            request.addProperty("ano", ano);
            request.addProperty("id_camion", idcamion);
            request.addProperty("n_transacc", transac);

            HttpTransportSE transporte = new HttpTransportSE(url);
            transporte.call(accionSoap1, sobre);

            return true;

        }catch (IOException e) {return false;} catch (XmlPullParserException e) {return false;}


    }

    public String[] ObtenerCamiones(){
        String[] camiones ;

        try {
            SoapObject request = new SoapObject(namespace, metodo2);
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.dotNet = true;
            sobre.setOutputSoapObject(request);

            HttpTransportSE transporte = new HttpTransportSE(url);
            transporte.call(accionSoap1, sobre);

            SoapObject respuesta = (SoapObject)sobre.getResponse();
            camiones = new String[respuesta.getPropertyCount()];

            for(int i =0; i<camiones.length;i++){
                camiones[i] = respuesta.getProperty(i).toString();
            }


        }catch (IOException e) {return null;} catch (XmlPullParserException e) {return null;}

        return camiones;
    }

    public int ObtenerUltimaTrans(int id_camion){
        int x = -1;

        return x;
    }

}
