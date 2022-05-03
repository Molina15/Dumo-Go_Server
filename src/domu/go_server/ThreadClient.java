/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domu.go_server;

/**
 *
 * @author Adrià Molina Inglada
 */
import com.jcraft.jsch.JSchException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import domu.go_server.AccionsServidor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * 
 */
public class ThreadClient extends Thread {
    private Socket client;
    private Scanner in;
    private InputStream yourInputStream;
    private ObjectInputStream mapInputStream;
    private OutputStream yourOutputStream;
    private ObjectOutputStream mapOutputStream;
    //private PrintWriter out;
    private String enter;
    //private int resposta_servidor;
    private Object resposta_servidor;
    private static final int SESSIO_CADUCADA = 10;
    private static final int SESSIO_TANCADA = 20;
    private int i;
    

    public ThreadClient(Socket client, int numero) throws Exception{
        try {
            this.client = client;
            //this.in = new Scanner(client.getInputStream());   
            //this.out = new PrintWriter(client.getOutputStream(), true);
            this.yourInputStream = client.getInputStream(); // InputStream from where to receive the map, in case of network you get it from the Socket instance.
            this.mapInputStream = new ObjectInputStream(yourInputStream);
            this.yourOutputStream = client.getOutputStream();
            this.mapOutputStream = new ObjectOutputStream(yourOutputStream);
            this.i = numero;
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }         
    }
    
    @Override
    public void run() {
        HashMap<String, String> msg = new HashMap<>();
        boolean online = true;
        int codi = 0;
        String str_codi_resposta = null;
        int codi_resposta = 0;
        HashMap<String, String> respostaMap = new HashMap<>();
        ArrayList respostaArrayMap = new ArrayList();
        Boolean esUnNumero = false;
        Boolean esUnMap = false;

        try {
                AccionsServidor.obrir();
                //AccionsServidor.obrir();   
        } catch (SQLException | JSchException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(online){
            try{
                
                msg = (HashMap) mapInputStream.readObject(); //rep la peticio del client
                resposta_servidor = AccionsServidor.realitza_accio(msg); //envia la processar la peticio
                
                esUnNumero = false;
                esUnMap = false;
                //comproba si la resposta del client es un enter o un map
                if (resposta_servidor.getClass() == Integer.class){
                    esUnNumero = true;
                }
                if (resposta_servidor.getClass() == HashMap.class){
                    esUnMap = true;
                }
                
                
                //si el codi resposta es un enter, el copia en una variable int, sino en una variable map i extreu el codi enter
                if (esUnNumero){
                    codi_resposta = (int) resposta_servidor;
                }else if(esUnMap){
                    respostaMap = (HashMap) resposta_servidor;
                    str_codi_resposta = respostaMap.get("codi_retorn");
                    codi_resposta = Integer.valueOf(str_codi_resposta);
                }else{
                    respostaArrayMap = (ArrayList) resposta_servidor;
                    respostaMap = (HashMap) respostaArrayMap.get(0);
                    str_codi_resposta = respostaMap.get("codi_retorn");
                    codi_resposta = Integer.valueOf(str_codi_resposta);
                }
                
                
                //comproba que el codi no hagi caducat
                if (codi_resposta == SESSIO_CADUCADA || codi_resposta == SESSIO_TANCADA){
                    System.out.println("Codi de sessio caducat: "+ codi_resposta);
                    //enviem resposta de codi de sesio no valid. Sempre es un int (revisar)
                    //puc enviar l'enter o puc enviar el map amb el codi d'error, depenent el que esperi el client
                    if(esUnNumero){
                        mapOutputStream.writeObject(codi_resposta);
                    }else if(esUnMap){
                        System.out.println("Resposta Map: "+ respostaMap);
                        mapOutputStream.writeObject(respostaMap);
                    }else{
                        mapOutputStream.writeObject(respostaArrayMap);
                    }
                    client.close();
                }else{
                    if(esUnNumero){
                        System.out.println("Resposta Numerica: "+ codi_resposta);
                        mapOutputStream.writeObject(codi_resposta);
                    }else if(esUnMap){
                        System.out.println("Resposta Map: "+ respostaMap);
                        mapOutputStream.writeObject(respostaMap);
                    }else{
                        mapOutputStream.writeObject(respostaArrayMap);
                    }
                }

                
            } catch (Exception ex){
                System.out.println("Client amb numero " + i + " i la IP " + client.getInetAddress().getHostName() + " desconnectat.");
                online = false;
                //Si s0ha produit un error es tanca la connexió
                try {
                    mapInputStream.close();
                } catch (Exception ex2) {
                    System.out.println("Error al tancar els stream d'entrada i salida :" + ex2.getMessage());
                }
            }
            
        }
    }    
    
}