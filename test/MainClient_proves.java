/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;


public class MainClient_proves {
    
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
            HashMap<String, String> respostaMap = new HashMap<String, String>();
            ArrayList respostaArrayMap = new ArrayList();
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
            HashMap<String, String> canvia_password_admin = new HashMap<String, String>();
            HashMap<String, String> canvia_password_usuari = new HashMap<String, String>();
            HashMap<String, String> mostra_usuari = new HashMap<String, String>();
            HashMap<String, String> mostra_admin = new HashMap<String, String>();
            HashMap<String, String> llista_usuaris = new HashMap<String, String>();
            HashMap<String, String> llista_admins = new HashMap<String, String>();
            HashMap<String, String> modifica_admin = new HashMap<String, String>();
            HashMap<String, String> modifica_usuari = new HashMap<String, String>();

            
            while(online){
                
                //Llegim del servidor
                System.out.print("\nQue vols fer? \n 1 = afegir llibre (beta) \t 11 = mostra usuari \n 2 = esborrar_llibre (beta) \t 12 = mostra admin \n 3 = comprobar usuari \t\t 13 = llista usuaris" 
                + "\n 4 = comprobar admin \t\t 14 = llista admins \n 5 = afegir usuari \t\t 15 = modifica admin \n 6 = esborra_usuari \t\t 16 = modifica usuari \n 7 = afegir_admin \n 8 = esborra_admin \n 9 = desconectar client \n"
                        + " 10 = canvia_password \n -> ");
                
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
                    comprobar_usuari.put("user_name","Joan25");
                    comprobar_usuari.put("password", "canviat");
                    mapOutputStream.writeObject(comprobar_usuari);
                    System.out.println("Esperant confirmacio...");
                    resposta = (int) mapInputStream.readObject();
                    codi = String.valueOf(resposta);
                    System.out.println("Servidor (codi de sessió): " + resposta);
                
                }else if ("4".equals(entrada)){
                    comprobar_admin.put("accio", "comprobar_admin");
                    comprobar_admin.put("nom_admin","marçal3535");
                    comprobar_admin.put("password", "1234");
                    mapOutputStream.writeObject(comprobar_admin);
                    System.out.println("Esperant confirmacio...");
                    resposta = (int) mapInputStream.readObject();
                    codi = String.valueOf(resposta);
                    System.out.println("Servidor (codi de sessió): " + resposta);
                
                }else if ("5".equals(entrada)){
                    long miliseconds = System.currentTimeMillis();
                    Date date = new Date(miliseconds);
                    afegir_usuari.put("codi", codi);
                    afegir_usuari.put("accio", "afegir_usuari");
                    afegir_usuari.put("user_name", "Helena34");
                    afegir_usuari.put("password", "1234");
                    afegir_usuari.put("dni", "77733773H");
                    afegir_usuari.put("data_alta", date.toString());
                    afegir_usuari.put("correu", "helena23@gmail.com");
                    afegir_usuari.put("admin_alta", "molina15");
                    afegir_usuari.put("nom", "Helena");
                    afegir_usuari.put("cognoms", "Ruiz");
                    afegir_usuari.put("direccio", "Av de la pera");
                    afegir_usuari.put("pais", "Honduras");
                    afegir_usuari.put("telefon", "989878675");
                    afegir_usuari.put("data_naixement", "1999-12-23");
                    
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(afegir_usuari);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                                                                   
                }else if ("6".equals(entrada)){
                    esborrar_usuari.put("codi", codi);
                    esborrar_usuari.put("accio", "esborrar_usuari");
                    esborrar_usuari.put("user_name", "Marc45");
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
                    afegir_admin.put("nom", "Marçal");
                    afegir_admin.put("cognoms", "Perez");
                    afegir_admin.put("data_naixement", "1989-11-11");
                    afegir_admin.put("direccio", "Av del paraiso");
                    afegir_admin.put("pais", "Moldavia");
                    afegir_admin.put("telefon", "989876745");
                    
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
                    System.out.println(tancar_sessio);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    online = false;
                }
                
                else if ("10".equals(entrada)){
                    canvia_password_admin.put("codi", codi);
                    canvia_password_admin.put("accio", "canvia_password");
                    canvia_password_admin.put("password", "1234");
                    canvia_password_admin.put("password_nou", "12345");
                    mapOutputStream.writeObject(canvia_password_admin);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                }
                
                else if ("11".equals(entrada)){
                    mostra_usuari.put("codi", codi);
                    mostra_usuari.put("accio", "mostra_usuari");
                    mostra_usuari.put("user_name", "Pep25");
                    mapOutputStream.writeObject(mostra_usuari);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    Object respostaObj = mapInputStream.readObject();
                    respostaMap = ( HashMap<String, String>) respostaObj;
                    System.out.println("Servidor: " + respostaMap);
                    
                    resposta = Integer.valueOf(respostaMap.get("codi_retorn"));
                }
                
                else if ("12".equals(entrada)){
                    mostra_admin.put("codi", codi);
                    mostra_admin.put("accio", "mostra_admin");
                    mostra_admin.put("nom_admin", "marçal35");
                    mapOutputStream.writeObject(mostra_admin);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    Object respostaObj = mapInputStream.readObject();
                    respostaMap = ( HashMap<String, String>) respostaObj;
                    System.out.println("Servidor: " + respostaMap);
                    
                    resposta = Integer.valueOf(respostaMap.get("codi_retorn"));
                }
                
                else if ("13".equals(entrada)){
                    llista_usuaris.put("codi", codi);
                    llista_usuaris.put("accio", "llista_usuaris");
                    mapOutputStream.writeObject(llista_usuaris);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor 
                    Object respostaObj = mapInputStream.readObject();
                    respostaArrayMap = (ArrayList) respostaObj;
                    int j = 0;
                    while(j < respostaArrayMap.size()){
                        System.out.println(respostaArrayMap.get(j));
                        j++;
                    }
                    
                    respostaMap = (HashMap) respostaArrayMap.get(0);
                    resposta = Integer.valueOf(respostaMap.get("codi_retorn"));
                }
                
                
                else if ("14".equals(entrada)){
                    llista_admins.put("codi", codi);
                    llista_admins.put("accio", "llista_admins");
                    mapOutputStream.writeObject(llista_admins);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    Object respostaObj = mapInputStream.readObject();
                    respostaArrayMap = (ArrayList) respostaObj;
                    int j = 0;
                    while(j < respostaArrayMap.size()){
                        System.out.println(respostaArrayMap.get(j));
                        j++;
                    }
                    
                    respostaMap = (HashMap) respostaArrayMap.get(0);
                    resposta = Integer.valueOf(respostaMap.get("codi_retorn"));
                }
                
                else if ("15".equals(entrada)){
                    long miliseconds = System.currentTimeMillis();
                    modifica_admin.put("codi", codi);
                    modifica_admin.put("accio", "modifica_admin");
                    modifica_admin.put("nom_admin", "marça3333");
                    modifica_admin.put("nou_nom_admin", "marçal3535");
                    modifica_admin.put("password", "1234");
                    modifica_admin.put("dni", "97789554h");
                    modifica_admin.put("correu", "marcal135@gmail.com");
                    modifica_admin.put("nom", "Marçal");
                    modifica_admin.put("cognoms", "Perez");
                    modifica_admin.put("data_naixement", "1989-11-11");
                    modifica_admin.put("direccio", "Av del paraiso");
                    modifica_admin.put("pais", "Moldavia");
                    modifica_admin.put("telefon", "989876745");
                    
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(modifica_admin);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                }
                
                else if ("16".equals(entrada)){
                    long miliseconds = System.currentTimeMillis();
                    modifica_usuari.put("codi", codi);
                    modifica_usuari.put("accio", "modifica_usuari");
                    modifica_usuari.put("user_name", "Helena89");
                    modifica_usuari.put("nou_user_name", "Helena8989");
                    modifica_usuari.put("password", "1234");
                    modifica_usuari.put("dni", "97789554h");
                    modifica_usuari.put("correu", "helena@gmail.com");
                    modifica_usuari.put("nom", "Marçal");
                    modifica_usuari.put("cognoms", "Perez");
                    modifica_usuari.put("data_naixement", "1989-11-11");
                    modifica_usuari.put("direccio", "Av del paraiso");
                    modifica_usuari.put("pais", "Moldavia");
                    modifica_usuari.put("telefon", "989616161");
                    
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(modifica_usuari);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                }
                    
                if (resposta == SESSIO_CADUCADA){
                    System.out.println("Tancant sessio...");
                    online = false;
                }
                
                
            }
            socket.close();
            yourOutputStream.close();
            mapInputStream.close();
            
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(MainClient_proves.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainClient_proves.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}