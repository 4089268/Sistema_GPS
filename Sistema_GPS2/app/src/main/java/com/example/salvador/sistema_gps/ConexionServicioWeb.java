package com.example.salvador.sistema_gps;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Salvador on 11/05/2016.
 */
public class ConexionServicioWeb {

    private static String url ;
    private static String suburl = "/SistemaRastreoGPS/Service.asmx?WSDL";
    private static String namespace = "www.salvador.org";

    private static String accionSoap1 = "www.salvador.org/AgregarCoordenadas";
    private static String accionSoap2 = "www.salvador.org/ObtnerCamiones";
    private static String accionSoap3 = "www.salvador.org/ObtnerUltimaTransacc";
    private static String accionSoap4 = "www.salvador.org/ComprobarUsuario";

    private static String metodo1 = "AgregarCoordenadas";
    private static String metodo2 = "ObtnerCamiones";
    private static String metodo3 = "ObtnerUltimaTransacc";
    private static String metodo4 = "ComprobarUsuario";

    public ConexionServicioWeb() {
        url = "http://192.168.173.1"+suburl;

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
        String[] camiones = null;

        try {
            SoapObject request = new SoapObject(namespace, metodo2);
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.dotNet = true;
            sobre.setOutputSoapObject(request);

            HttpTransportSE transporte = new HttpTransportSE(url);
            transporte.call(accionSoap2, sobre);

            SoapObject res= (SoapObject)sobre.getResponse();
            camiones = new String[res.getPropertyCount()];

            for(int i = 0;i<camiones.length;i++){
                camiones[i]= res.getProperty(i).toString();
            }

        }catch (IOException e) {return null;} catch (XmlPullParserException e) {return null;}

        return camiones;
    }

    public int ObtenerUltimaTrans(int id_camion){
        try {
            SoapObject request = new SoapObject(namespace, metodo3);
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.dotNet = true;

            sobre.setOutputSoapObject(request);
            request.addProperty("id_camion", id_camion);

            HttpTransportSE transporte = new HttpTransportSE(url);
            transporte.call(accionSoap3, sobre);

            SoapPrimitive result = (SoapPrimitive)sobre.getResponse();
            return Integer.parseInt(result.toString());

        }catch (IOException e) {return -1;} catch (XmlPullParserException e) {return -1;}
    }

    public int VerificarUsuario(String acount, int pass){

        try {
            SoapObject request = new SoapObject(namespace, metodo4);
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.dotNet = true;

            sobre.setOutputSoapObject(request);
            request.addProperty("acount", acount);
            request.addProperty("pass", pass);

            HttpTransportSE transporte = new HttpTransportSE(url);
            transporte.call(accionSoap4, sobre);

            SoapPrimitive result = (SoapPrimitive)sobre.getResponse();
            return Integer.parseInt(result.toString());

        }catch (IOException e) {return -1;} catch (XmlPullParserException e) {return -1;}
    }

}
