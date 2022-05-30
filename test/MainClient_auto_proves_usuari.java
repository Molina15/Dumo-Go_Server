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


public class MainClient_auto_proves_usuari {
    
     private static int MOSTRA_USUARI_OK = 5000;
    private static int USUARI_NO_VALID = 5010;
    
    private static int MOSTRA_ADMIN_OK = 6000;
    private static int ADMIN_NO_VALID = 6010;
    
    private static int CANVI_PASSWORD_OK = 9000;
    private static int CONTRASENYA_NO_VALIDA = 9010;
    
    private static int LLISTA_USUARIS_OK = 1100;
    private static int LLISTA_ADMINS_OK = 1200;
    
    private static int MODIFICACIO_OK = 1300;
    private static int FORMAT_EMAIL = 1310;
    private static int FORMAT_DNI = 1320;
    private static int FORMAT_PASSWORD = 1330;
    
    private static int LLIBRE_AFEGIT = 1400;
    
    private static int LLIBRE_ESBORRAT = 1500;
    private static int LLIBRE_NO_TROBAT = 1510;
    
    private static int MOSTRA_LLIBRE_OK = 1600;
    private static int MOSTRA_LLIBRE_NO_TROBAT = 1610;
    
    private static int LLISTA_LLIBRES_OK = 1700;

    private static int MODIFICA_LLIBRE_OK = 1800;
    private static int MODIFICA_NO_TROBAT = 1810;
    
    private static int VALORACIO_OK = 1900;
    
    private static int RESERVAT_OK = 2100;
    private static int LLIBRE_JA_RESERVAT = 2110;
    
    private static int RETORN_OK = 2200;
    private static int LLIBRE_NO_PRESTAT = 2210;
    
    private static int LLISTA_PRESTECS_OK = 2300;
    
    private static int LLISTA_PRESTECS_USUARI_OK = 2400;
    
    private static int LLISTA_LLEGITS_OK = 2500;
     
    private static int LLISTA_PRESTECS_URGENTS_OK = 2600;

    private static int COMENTARI_AFEGIT = 2700;
    
    private static int COMENTARI_ESBORRAT = 2800;
    private static int COMENTARI_INEXISTENT = 2810;
    private static int USUARI_SENSE_PERMISOS = 2820;
    
    private static int LLISTA_COMENTARIS_OK = 2900;
    
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
            HashMap<String, String> esborra_llibre2 = new HashMap<String, String>();
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
            HashMap<String, String> mostra_usuari2 = new HashMap<String, String>();
            HashMap<String, String> mostra_admin = new HashMap<String, String>();
            HashMap<String, String> mostra_admin2 = new HashMap<String, String>();
            HashMap<String, String> llista_usuaris = new HashMap<String, String>();
            HashMap<String, String> llista_usuaris2 = new HashMap<String, String>();
            HashMap<String, String> llista_admins = new HashMap<String, String>();
            HashMap<String, String> llista_admins2 = new HashMap<String, String>();
            HashMap<String, String> modifica_admin = new HashMap<String, String>();
            HashMap<String, String> modifica_admin2 = new HashMap<String, String>();
            HashMap<String, String> modifica_admin3 = new HashMap<String, String>();
            HashMap<String, String> modifica_admin4 = new HashMap<String, String>();
            HashMap<String, String> modifica_usuari = new HashMap<String, String>();
            HashMap<String, String> mostra_llibre = new HashMap<String, String>();
            HashMap<String, String> mostra_llibre2 = new HashMap<String, String>();
            HashMap<String, String> llista_llibres = new HashMap<String, String>();
            HashMap<String, String> llista_llibres2 = new HashMap<String, String>();
            HashMap<String, String> modifica_llibre = new HashMap<String, String>();
            HashMap<String, String> modifica_llibre2 = new HashMap<String, String>();
            HashMap<String, String> puntua_llibre = new HashMap<String, String>();
            HashMap<String, String> reserva_llibre = new HashMap<String, String>();
            HashMap<String, String> retorna_llibre = new HashMap<String, String>();
            HashMap<String, String> llista_prestecs = new HashMap<String, String>();
            HashMap<String, String> llista_prestecs_usuari = new HashMap<String, String>();
            HashMap<String, String> llista_prestecs_no_retornats = new HashMap<String, String>();
            HashMap<String, String> llista_llegits = new HashMap<String, String>();
            HashMap<String, String> llista_prestecs_urgents = new HashMap<String, String>();
            HashMap<String, String> afegeix_comentari = new HashMap<String, String>();
            HashMap<String, String> elimina_comentari = new HashMap<String, String>();
            HashMap<String, String> llista_comentaris = new HashMap<String, String>();
            
            
            while(online){
                
                //Llegim del servidor
                System.out.print("\n----- Pulsa enter per executar la prova ----- ");
                
                sc.nextLine();
                entrada = "11";
                
                if ("11".equals(entrada)){
                    System.out.println("\nInici de sessió com a USUARI.");
                    comprobar_admin.put("accio", "comprovar_usuari");
                    comprobar_admin.put("user_name","Kilian34");
                    comprobar_admin.put("password", "1234");
                    mapOutputStream.writeObject(comprobar_admin);
                    System.out.println("Esperant confirmacio...");
                    resposta = (int) mapInputStream.readObject();
                    codi = String.valueOf(resposta);
                    System.out.println("Servidor (codi de sessió): " + resposta);
                }
                
                if ("11".equals(entrada)){
                    System.out.println("\nProva puntua_llibre");
                    puntua_llibre.put("codi", codi);
                    puntua_llibre.put("accio", "puntua_llibre");
                    puntua_llibre.put("id_llibre", "67");
                    puntua_llibre.put("valoracio_usuari", "5");
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(puntua_llibre);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    
                    if (resposta == VALORACIO_OK){
                       System.out.println("Prova puntua_llibre OK");
                    }
                }    
                if ("11".equals(entrada)){
                    System.out.println("\nProva reserva_llibre");
                    reserva_llibre.put("codi", codi);
                    reserva_llibre.put("accio", "reserva_llibre");
                    reserva_llibre.put("id_llibre", "59" );
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(reserva_llibre);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    
                    if (resposta == RESERVAT_OK){
                       System.out.println("Prova reserva_llibre OK");
                    }else if(resposta == LLIBRE_JA_RESERVAT){
                        System.out.println("Llibre ja reservat");
                    }
                }
                
                 if ("11".equals(entrada)){
                    reserva_llibre = new HashMap();
                    System.out.println("\nProva reserva_llibre 2");
                    reserva_llibre.put("codi", codi);
                    reserva_llibre.put("accio", "reserva_llibre");
                    reserva_llibre.put("id_llibre", "59" );
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(reserva_llibre);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    
                    if (resposta == RESERVAT_OK){
                       System.out.println("Prova reserva_llibre OK");
                    }else if(resposta == LLIBRE_JA_RESERVAT){
                        System.out.println("Llibre ja reservat");
                    }
                }
                
                
                if ("11".equals(entrada)){
                    System.out.println("\nProva llista_prestecs_usuari");
                    llista_prestecs_usuari.put("codi", codi);
                    llista_prestecs_usuari.put("accio", "llista_prestecs_usuari");
                    mapOutputStream.writeObject(llista_prestecs_usuari);
                    System.out.println("Esperant confirmacio...");
                    //enviem la consulta al servidor
                    Object respostaObj = mapInputStream.readObject();
                    respostaArrayMap = (ArrayList) respostaObj;
                    int j = 0;
                    while(j < respostaArrayMap.size()){
                        System.out.println(respostaArrayMap.get(j));
                        j++;
                    }
                    respostaMap = (HashMap) respostaArrayMap.get(0);
                    resposta = Integer.valueOf(respostaMap.get("codi_retorn"));
                    
                    if (resposta == LLISTA_PRESTECS_USUARI_OK){
                       System.out.println("Prova llista_prestecs OK");
                    }else{
                        System.out.println("Error");
                    }
                }
                
                if ("11".equals(entrada)){
                    System.out.println("\nProva llista_llegits");
                    llista_llegits.put("codi", codi);
                    llista_llegits.put("accio", "llista_llegits");
                    mapOutputStream.writeObject(llista_llegits);
                    System.out.println("Esperant confirmacio...");
                    //enviem la consulta al servidor
                    Object respostaObj = mapInputStream.readObject();
                    respostaArrayMap = (ArrayList) respostaObj;
                    int j = 0;
                    while(j < respostaArrayMap.size()){
                        System.out.println(respostaArrayMap.get(j));
                        j++;
                    }
                    respostaMap = (HashMap) respostaArrayMap.get(0);
                    resposta = Integer.valueOf(respostaMap.get("codi_retorn"));
                    if (resposta == LLISTA_LLEGITS_OK){
                       System.out.println("Prova mostra_usuari OK");
                    }else{
                        System.out.println("Error");
                    }
                }
                
                if ("11".equals(entrada)){
                    System.out.println("\nProva llista_prestecs_urgents");
                    llista_prestecs_urgents.put("codi", codi);
                    llista_prestecs_urgents.put("accio", "llista_prestecs_urgents");
                    mapOutputStream.writeObject(llista_prestecs_urgents);
                    System.out.println("Esperant confirmacio...");
                    //enviem la consulta al servidor
                    Object respostaObj = mapInputStream.readObject();
                    respostaArrayMap = (ArrayList) respostaObj;
                    int j = 0;
                    while(j < respostaArrayMap.size()){
                        System.out.println(respostaArrayMap.get(j));
                        j++;
                    }
                    respostaMap = (HashMap) respostaArrayMap.get(0);
                    resposta = Integer.valueOf(respostaMap.get("codi_retorn"));
                    
                    resposta = Integer.valueOf(respostaMap.get("codi_retorn"));
                    if (resposta == LLISTA_PRESTECS_URGENTS_OK){
                       System.out.println("Prova llista_prestecs_urgents OK");
                    }else{
                        System.out.println("Error");
                    }
                }
                
                if ("11".equals(entrada)){
                    System.out.println("\nProva afegeix_comentari");
                    afegeix_comentari.put("codi", codi);
                    afegeix_comentari.put("accio", "afegeix_comentari");
                    afegeix_comentari.put("id_llibre", "59");
                    afegeix_comentari.put("comentari", "Esta bé (auto_test).");
                    mapOutputStream.writeObject(afegeix_comentari);
                    System.out.println("Esperant confirmacio...");
                    //enviem la consulta al servidor
                    resposta = (int) mapInputStream.readObject();
                    if (resposta == COMENTARI_AFEGIT){
                       System.out.println("Prova afegeix_comentari OK");
                    }else{
                        System.out.println("Error");
                    }
                }
                
                if ("11".equals(entrada)){
                     System.out.println("\nProva elimina_comentari_usuari ");
                     elimina_comentari.put("codi", codi);
                    elimina_comentari.put("accio", "elimina_comentari");
                    elimina_comentari.put("id_comentari", "20");
                    mapOutputStream.writeObject(elimina_comentari);
                    System.out.println("Esperant confirmacio...");
                    //enviem la consulta al servidor
                    resposta = (int) mapInputStream.readObject();
                   
                    if (resposta == COMENTARI_ESBORRAT){
                       System.out.println("Prova elimina_comentari OK");
                    }else if (resposta == COMENTARI_INEXISTENT){
                        System.out.println("Comentari inexistent");
                    }else if (resposta == USUARI_SENSE_PERMISOS){
                        System.out.println("Usuari sense permisos");
                    }
                }
                
                if ("11".equals(entrada)){
                    elimina_comentari = new HashMap();
                     System.out.println("\nProva elimina_comentari_usuari 2");
                     elimina_comentari.put("codi", codi);
                    elimina_comentari.put("accio", "elimina_comentari");
                    elimina_comentari.put("id_comentari", "11");
                    mapOutputStream.writeObject(elimina_comentari);
                    System.out.println("Esperant confirmacio...");
                    //enviem la consulta al servidor
                    resposta = (int) mapInputStream.readObject();
                    
                    if (resposta == COMENTARI_ESBORRAT){
                       System.out.println("Prova elimina_comentari OK");
                    }else if (resposta == COMENTARI_INEXISTENT){
                        System.out.println("Comentari inexistent");
                    }else if (resposta == USUARI_SENSE_PERMISOS){
                        System.out.println("Usuari sense permisos");
                    }
                }
                
                if ("11".equals(entrada)){
                    elimina_comentari = new HashMap();
                     System.out.println("\nProva elimina_comentari_usuari 3");
                     elimina_comentari.put("codi", codi);
                    elimina_comentari.put("accio", "elimina_comentari");
                    elimina_comentari.put("id_comentari", "10");
                    mapOutputStream.writeObject(elimina_comentari);
                    System.out.println("Esperant confirmacio...");
                    //enviem la consulta al servidor
                    resposta = (int) mapInputStream.readObject();
                    
                    if (resposta == COMENTARI_ESBORRAT){
                       System.out.println("Prova elimina_comentari OK");
                    }else if (resposta == COMENTARI_INEXISTENT){
                        System.out.println("Comentari inexistent");
                    }else if (resposta == USUARI_SENSE_PERMISOS){
                        System.out.println("Usuari sense permisos");
                    }
                }
                
                
               if ("11".equals(entrada)){
                    System.out.println("\nProva llista_comentaris");
                    llista_comentaris.put("codi", codi);
                    llista_comentaris.put("accio", "llista_comentaris");
                    llista_comentaris.put("nom", "La vida de Belen Esteban");
                    
                    mapOutputStream.writeObject(llista_comentaris);
                    System.out.println("Esperant confirmacio...");
                    //enviem la consulta al servidor
                    Object respostaObj = mapInputStream.readObject();
                    respostaArrayMap = (ArrayList) respostaObj;
                    int j = 0;
                    while(j < respostaArrayMap.size()){
                        System.out.println(respostaArrayMap.get(j));
                        j++;
                    }
                    respostaMap = (HashMap) respostaArrayMap.get(0);
                    resposta = Integer.valueOf(respostaMap.get("codi_retorn"));
                    if (resposta == LLISTA_COMENTARIS_OK){
                       System.out.println("Prova llista_comentaris OK");
                    }else {
                        System.out.println("Error");
                    }
                    
                }

                System.out.println("\nTancant sessio...");
                tancar_sessio.put("codi", codi);
                tancar_sessio.put("accio", "tancar_sessio");
                mapOutputStream.writeObject(tancar_sessio);
                resposta = (int) mapInputStream.readObject();
                System.out.println("Servidor: " + resposta);
                online = false;
                  
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