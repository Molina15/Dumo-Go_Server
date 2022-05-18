/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domu.go_server;

import com.jcraft.jsch.JSchException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrià Molina Inglada
 */
public class MainServer {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        
        int port = 7777;
        int maxConnexions = 100;
        Socket socket = null;
        ServerSocket serverSocket = null;
        
        try {
                AccionsServidor.obrir();
                AccionsServidor.executaComprovador();
        } catch (SQLException | JSchException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            // Creant socket servidor     
            serverSocket = new ServerSocket(port, maxConnexions);
            //Bucle infinit esperant connexions
            int i=0;
            while (true) {
                System.out.println("Servidor a l'espera de connexions.");
                socket = serverSocket.accept();
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