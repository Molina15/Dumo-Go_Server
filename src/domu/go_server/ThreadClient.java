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
import domu.go_server.controladorSQL;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.SQLException;
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
    private int resposta_servidor;
    private int SESSIO_CADUCADA = 10;
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
        HashMap<String, String> msg = new HashMap<String, String>();
        boolean online = true;
        int codi = 0;
        
        try {
                controladorSQL.obrir();   
        } catch (SQLException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSchException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(online){
            try{
                msg = (HashMap) mapInputStream.readObject();
                resposta_servidor = controladorSQL.realitza_accio(msg);
                if (resposta_servidor == SESSIO_CADUCADA){
                    //enviem resposta de codi de sesio no valid. 
                    mapOutputStream.writeObject(resposta_servidor);
                    client.close();
                }else{
                    mapOutputStream.writeObject(resposta_servidor);
                    
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