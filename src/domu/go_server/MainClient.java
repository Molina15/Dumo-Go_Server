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
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;


public class MainClient {
    
    public static void main(String[] args) throws Exception {
        //Socket socket = null; 
        DataInputStream dadesEntrada;
        PrintWriter output;
        int port = 7777;
        String codi = null;
        int SESSIO_CADUCADA = 10;
        
        
        try {
            System.out.println("Conectant al servidor...");
            InetAddress addr1 = InetAddress.getByName("localhost"); 
            Socket socket = new Socket(addr1, port);
            System.out.print("Conectat. ");
            
            Scanner sc = new Scanner(System.in);
            
            OutputStream yourOutputStream = socket.getOutputStream();
            ObjectOutputStream mapOutputStream = new ObjectOutputStream(yourOutputStream);
            
            InputStream yourInputStream = socket.getInputStream();
            ObjectInputStream mapInputStream = new ObjectInputStream(yourInputStream);
            
            boolean online = true;
            int resposta = 0;
            String entrada;
            
            HashMap<String, String> afegir_llibre = new HashMap<String, String>();
            HashMap<String, String> esborra_llibre = new HashMap<String, String>();
            HashMap<String, String> comprobar_usuari = new HashMap<String, String>();
            HashMap<String, String> comprobar_admin = new HashMap<String, String>();
            HashMap<String, String> afegir_usuari = new HashMap<String, String>();
            HashMap<String, String> esborrar_usuari = new HashMap<String, String>();
            HashMap<String, String> afegir_admin = new HashMap<String, String>();
            HashMap<String, String> esborrar_admin = new HashMap<String, String>();
            HashMap<String, String> tancar_sessio = new HashMap<String, String>();
             
            while(online){
                
                //Llegim del servidor
                System.out.println("Que vols fer? \n\n 1 = afegir llibre \n 2 = esborrar_llibre \n 3 = comprobar usuari"
                + "\n 4 = comprobar admin \n 5 = afegir usuari \n 6 = esborra_usuari \n 7 = afegir_admin \n 8 = esborra_admin \n 9 = desconectar client \n-> ");
                
                entrada = sc.nextLine();
                if ("1".equals(entrada)){
                    
                    long miliseconds = System.currentTimeMillis();
                    Date date = new Date(miliseconds);
                    afegir_llibre.put("codi", codi);
                    afegir_llibre.put("accio", "afegir_llibre");
                    afegir_llibre.put("nom", "Harry Potter y el Càliz de fuego");
                    afegir_llibre.put("autor", "JK. Rowling");
                    afegir_llibre.put("any_publicacio", "1999");
                    afegir_llibre.put("tipus", "Fantàsia");
                    afegir_llibre.put("data_alta", date.toString());
                    afegir_llibre.put("reservat_dni", null);
                    afegir_llibre.put("admin_alta", "molina15");
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(afegir_llibre);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    
                }else if ("2".equals(entrada)){
                    esborra_llibre.put("codi", codi);
                    esborra_llibre.put("accio", "esborrar_llibre");
                    esborra_llibre.put("nom", "Harry Potter y el Càliz de fuego");
                    System.out.println(esborra_llibre);
                    mapOutputStream.writeObject(esborra_llibre);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    
                }else if ("3".equals(entrada)){
                    comprobar_usuari.put("accio", "comprobar_usuari");
                    comprobar_usuari.put("nom_user","Oscar39");
                    comprobar_usuari.put("password", "1234");
                    mapOutputStream.writeObject(comprobar_usuari);
                    System.out.println("Esperant confirmacio...");
                    resposta = (int) mapInputStream.readObject();
                    codi = String.valueOf(resposta);
                    System.out.println("Servidor: " + resposta);
                
                }else if ("4".equals(entrada)){
                    comprobar_admin.put("accio", "comprobar_admin");
                    comprobar_admin.put("nom_admin","molina15");
                    comprobar_admin.put("password", "1234");
                    mapOutputStream.writeObject(comprobar_admin);
                    System.out.println("Esperant confirmacio...");
                    resposta = (int) mapInputStream.readObject();
                    codi = String.valueOf(resposta);
                    System.out.println("Servidor: " + resposta);
                
                }else if ("5".equals(entrada)){
                    long miliseconds = System.currentTimeMillis();
                    Date date = new Date(miliseconds);
                    afegir_usuari.put("codi", codi);
                    afegir_usuari.put("accio", "afegir_usuari");
                    afegir_usuari.put("nom_user", "Oscar39");
                    afegir_usuari.put("password", "1234");
                    afegir_usuari.put("dni", "d7766555h");
                    afegir_usuari.put("data_alta", date.toString());
                    afegir_usuari.put("correu", "oscar@gmail.com");
                    afegir_usuari.put("admin_alta", "molina15");
                    afegir_usuari.put("nom_cognoms", "Oscar Tomas");
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(afegir_usuari);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                                                                   
                }else if ("6".equals(entrada)){
                    esborrar_usuari.put("coid", codi);
                    esborrar_usuari.put("accio", "esborrar_usuari");
                    esborrar_usuari.put("nom_user", "Oscar39");
                    mapOutputStream.writeObject(esborrar_usuari);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    
                }else if ("7".equals(entrada)){
                    long miliseconds = System.currentTimeMillis();
                    Date date = new Date(miliseconds);
                    afegir_admin.put("codi", codi);
                    afegir_admin.put("accio", "afegir_admin");
                    afegir_admin.put("nom_admin", "marçal35");
                    afegir_admin.put("password", "1234");
                    afegir_admin.put("dni", "97766554h");
                    afegir_admin.put("correu", "marcal@gmail.com");
                    afegir_admin.put("admin_alta", "molina15");
                    afegir_admin.put("nom_cognoms", "Marc Marc");
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(afegir_admin);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    
                }else if ("8".equals(entrada)){
                    esborrar_admin.put("codi", codi);
                    esborrar_admin.put("accio", "esborrar_admin");
                    esborrar_admin.put("nom_admin", "marçal35");
                    mapOutputStream.writeObject(esborrar_admin);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    
                }else if ("9".equals(entrada)){
                    tancar_sessio.put("codi", codi);
                    tancar_sessio.put("accio", "tancar_sessio");
                    mapOutputStream.writeObject(tancar_sessio);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    online = false;
                }
                
                if (resposta == SESSIO_CADUCADA){
                    online = false;
                }
            }
            socket.close();
            yourOutputStream.close();
            mapInputStream.close();
            
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(MainClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}