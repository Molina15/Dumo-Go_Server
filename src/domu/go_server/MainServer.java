/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domu.go_server;

import com.jcraft.jsch.JSchException;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 *
 * @author Adrià Molina Inglada
 */
public class MainServer {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
       
        System.setProperty("javax.net.ssl.keyStore", "src/certificates/servidor/serverKey.jks");
        System.setProperty("javax.net.ssl.keyStorePassword","dumogo2022");
        System.setProperty("javax.net.ssl.trustStore", "src/certificates/servidor/serverTrustedCerts.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "dumogo2022");
        
        int port = 7777;
        int maxConnexions = 100;
        SSLSocket socket = null;
        
        ServerSocket serverSocket = null;
        
        try {
                AccionsServidor.obrir();
                AccionsServidor.executaComprovador();
        } catch (SQLException | JSchException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            // Creant socket servidor     
            //serverSocket = ssf.createServerSocket(port, maxConnexions);
            SSLServerSocketFactory serverFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            serverSocket = serverFactory.createServerSocket(port);
            //Bucle infinit esperant connexions
            int i=0;
            while (true) {
                System.out.println("Servidor a l'espera de connexions.");
                socket = (SSLSocket)serverSocket.accept();
                System.out.println("Client "+i+ " amb la IP " + socket.getInetAddress().getHostName() + " connectat.");
                //instancia a connexió client
                ThreadClient cc = new ThreadClient(socket, i);
                cc.start();
                i++;
            }  
            

        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            try {
                socket.close();
                serverSocket.close();
            } catch (IOException ex) {
                System.out.println("Error al tancar el servidor: " + ex.getMessage());
            }
        }
    }
    
}