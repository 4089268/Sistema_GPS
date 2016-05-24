package com.example.salvador.sistema_gps;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Salvador on 11/05/2016.
 */
public class ConexionServicioWeb {

    private static String url;
    private static  String suburl = "/SistemaRastreoGPS/Service.asmx?WSDL";
    private static  String namespace = "salvador.org";

    private static  String accionSoap = "salvador.org/nuevaReservacion";

    private static  String metodo1 = "AgregarCoordenadas";
    private static  String metodo2 = "ObtnerCamiones";
    private static  String metodo3 = "ObtnerUltimaTransacc";

    public ConexionServicioWeb(int ip1, int ip2, int ip3, int ip4) {
        url = "http://"+ip1+"."+ip2+"."+ip3+"."+ip4+suburl;

    }

    public void AgregarCoordenadas(String altitud, String longitud, int hora, int minuto,int dia, int mes, int ano, int idcamion, int transac){

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
            transporte.call(accionSoap, sobre);

        }catch (IOException e) {} catch (XmlPullParserException e) {}

    }

}
