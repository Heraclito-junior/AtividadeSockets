package exemplo.twitter;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class chatActivity extends Activity {

    private EditText textoEntrada = null;
    private TextView textoSaida = null;
    private EditText editIP = null;
    private Button botao = null;
    private TextView textoIP= null;
    private static InetAddress ia;
    private static String ip;
    private String leitura;
    private static int numero=0;


    private static final String TAG = chatActivity.class.getSimpleName();

    String saida;
    String memoria;
    atualizacao att;






    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ip="";
        setContentView(R.layout.chat);

        textoEntrada = (EditText) findViewById(R.id.texto);
        textoSaida = (TextView) findViewById(R.id.texto_saida);
        textoIP = (TextView) findViewById(R.id.ip);
        editIP = (EditText) findViewById(R.id.editTextIP);
        editIP.setText("10.0.2.2");

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        botao = (Button) findViewById(R.id.botao);


        numero=0;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {

            } else {
                numero=extras.getInt("numeroSocket");
            }
        } else {
            numero= (int) savedInstanceState.getSerializable("numeroSocket");
        }



        retrieveIP();
        textoIP.setText("Device IP: "+ip);

        saida=null;
        memoria=null;

        //att=new atualizacao();
        new Thread(new Runnable(){

            public void run(){


                while(true){
                    saida=null;
                    //memoria=null;

                    textoSaida = (TextView) findViewById(R.id.texto_saida);
                    saida=textoSaida.getText().toString();
                    retrieveIP();
                    System.out.println("Servidor Iniciado!!");
                    ServerSocket serverSocket;
                    boolean listening = true;
                    try {
                        /**
                         * se apertar direct numero=1
                         * se apertar direct2 numero=2;
                         */
                        if(numero==1) {
                            serverSocket = new ServerSocket(6000);
                        }else{
                            serverSocket = new ServerSocket(8000);
                        }



                        while (listening) {



                            Socket socket = serverSocket.accept();
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));



                            String leitura = in.readLine();

                            BufferedWriter out = new BufferedWriter(new
                                    OutputStreamWriter(socket.getOutputStream()));
                            out.write("A String obtida foi: \n"+leitura );

                            saida=leitura;

                            socket.close();

                            break;
                        }
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(-1);
                    }
                    saida="Outro : " + saida;
                    if(memoria==null){
                        memoria = saida;

                    }else {
                        memoria = saida + "\n" + memoria;
                    }
                    att=new atualizacao();
                    att.execute();

                }


            }
        }).start();

    }

    public void enviar(View v){
        String texto=textoEntrada.getText().toString();
        if(texto.equals("")){

            return;
        }


        SocketClient clientela = new SocketClient();


        if(numero==1) {
            clientela.execute(editIP.getText().toString(),"2000",textoEntrada.getText()+"");
        }else{
            clientela.execute(editIP.getText().toString(),"5000",textoEntrada.getText()+"");

        }


    }


    private static void retrieveIP(){

        ip = null;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                InetAddress addr = addresses.nextElement();
                ip = addr.getHostAddress();
                System.out.println(iface.getDisplayName() + " " + ip);
                break;

            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }



        System.out.println("ip: "+ ip);
    }
    private class SocketClient extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {

            Log.w(TAG,"pre ");

        }

        protected String doInBackground(String... params) {
            String adicionar="";

            Socket socket = null;
            StringBuffer data = new StringBuffer();
            try {

                socket = new Socket(params[0], Integer.parseInt(params[1]));
                PrintWriter pw = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream()),
                        true);
                pw.println(params[2]);

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                String rawData;

                while ((rawData = br.readLine()) != null) {
                    data.append(rawData);

                }

                adicionar=params[2];

                if(adicionar.equals("")){
                    return "deuRuim";
                }

                adicionar="Eu : " + adicionar;

            } catch (Exception e) {

            }
            return adicionar; // close socket …
        }
        protected void onPostExecute(String result) {
            if(result.equals("deuRuim")){
                return;
            }
            if(result.equals("")){
                return;
            }

            if(memoria==null){
                memoria=result;
            }else{
                memoria = result + "\n" + memoria;

            }
            textoSaida.setText(memoria);


        }
    }


    private class atualizacao extends AsyncTask<String, Void, String>{


        @Override
        protected void onPreExecute() {

            Log.w(TAG,"atualizando");
        }


        protected String doInBackground(String... params) {
            return "nada";
        }
        protected void onPostExecute(String result) {
            textoSaida.setText(memoria);
            return ;
        }
    }
}


