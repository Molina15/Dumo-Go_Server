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


public class MainClient_auto_proves {
    
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
            
            
            while(online){
                
                //Llegim del servidor
                System.out.print("\n----- Pulsa enter per executar la prova ----- ");
                
                sc.nextLine();
                entrada = "11";
                
                if ("11".equals(entrada)){
                    System.out.println("\nInici de sessió com a admin.");
                    comprobar_admin.put("accio", "comprobar_admin");
                    comprobar_admin.put("nom_admin","marçal");
                    comprobar_admin.put("password", "1234");
                    mapOutputStream.writeObject(comprobar_admin);
                    System.out.println("Esperant confirmacio...");
                    resposta = (int) mapInputStream.readObject();
                    codi = String.valueOf(resposta);
                    System.out.println("Servidor (codi de sessió): " + resposta);
                }
                
                if ("11".equals(entrada)){
                    System.out.println("\nProva afegir_llibre");
                    long miliseconds = System.currentTimeMillis();
                    Date date = new Date(miliseconds);
                    afegir_llibre.put("codi", codi);
                    afegir_llibre.put("accio", "afegir_llibre");
                    afegir_llibre.put("nom", "Harry Potter y el Càliz de fuego 3");
                    afegir_llibre.put("autor", "JK. Rowling");
                    afegir_llibre.put("any_publicacio", "1999");
                    afegir_llibre.put("tipus", "Fantàsia");
                    afegir_llibre.put("data_alta", date.toString());
                    afegir_llibre.put("admin_alta", "molina15");
                    afegir_llibre.put("descripcio", "Un llibre molt guapo.");
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(afegir_llibre);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    
                    if (resposta == LLIBRE_AFEGIT){
                       System.out.println("Prova afegeix_llibre OK");
                    }
                }    
                if ("11".equals(entrada)){
                    System.out.println("\nProva esborrar_llibre");
                    esborra_llibre.put("codi", codi);
                    esborra_llibre.put("accio", "esborrar_llibre");
                    esborra_llibre.put("id", "68");
                    System.out.println(esborra_llibre);
                    mapOutputStream.writeObject(esborra_llibre);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    
                    if (resposta == LLIBRE_ESBORRAT){
                       System.out.println("Prova esborra_llibre OK");
                    }else{
                        System.out.println("Llibre no trobat");
                    }
                }
                
                if ("11".equals(entrada)){
                    System.out.println("\nProva esborrar_llibre 2");
                    esborra_llibre2.put("codi", codi);
                    esborra_llibre2.put("accio", "esborrar_llibre");
                    esborra_llibre2.put("id", "10");
                    System.out.println(esborra_llibre);
                    mapOutputStream.writeObject(esborra_llibre2);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    
                    if (resposta == LLIBRE_ESBORRAT){
                       System.out.println("Prova esborra_llibre OK");
                    }else{
                        System.out.println("Llibre no trobat");
                    }
                }
                
                if ("11".equals(entrada)){
                    System.out.println("\nProva mostra_usuari");
                    mostra_usuari2.put("codi", codi);
                    mostra_usuari2.put("accio", "mostra_usuari");
                    mostra_usuari2.put("user_name", "Pep25");
                    mapOutputStream.writeObject(mostra_usuari2);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    Object respostaObj = mapInputStream.readObject();
                    respostaMap = ( HashMap<String, String>) respostaObj;
                    System.out.println("Servidor: " + respostaMap);
                    
                    resposta = Integer.valueOf(respostaMap.get("codi_retorn"));
                    if (resposta == MOSTRA_USUARI_OK){
                       System.out.println("Prova mostra_usuari OK");
                    }else{
                        System.out.println("Usuari no trobat");
                    }
                }
                
                if ("11".equals(entrada)){
                    System.out.println("\nProva mostra_usuari 2");
                    mostra_usuari2.put("codi", codi);
                    mostra_usuari2.put("accio", "mostra_usuari");
                    mostra_usuari2.put("user_name", "Ignasi25");
                    mapOutputStream.writeObject(mostra_usuari2);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    Object respostaObj = mapInputStream.readObject();
                    respostaMap = ( HashMap<String, String>) respostaObj;
                    System.out.println("Servidor: " + respostaMap);
                    
                    resposta = Integer.valueOf(respostaMap.get("codi_retorn"));
                    if (resposta == MOSTRA_USUARI_OK){
                       System.out.println("Prova mostra_usuari OK");
                    }else{
                        System.out.println("Usuari no trobat");
                    }
                }
                
                if ("11".equals(entrada)){
                    System.out.println("\nProva mostra_admin");
                    mostra_admin.put("codi", codi);
                    mostra_admin.put("accio", "mostra_admin");
                    mostra_admin.put("nom_admin", "marçal");
                    mapOutputStream.writeObject(mostra_admin);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    Object respostaObj = mapInputStream.readObject();
                    respostaMap = ( HashMap<String, String>) respostaObj;
                    System.out.println("Servidor: " + respostaMap);
                    
                    resposta = Integer.valueOf(respostaMap.get("codi_retorn"));
                    if (resposta == MOSTRA_ADMIN_OK){
                       System.out.println("Prova mostra_admin OK");
                    }else{
                        System.out.println("Admin no trobat");
                    }
                }
                
                if ("11".equals(entrada)){
                    System.out.println("\nProva mostra_admin 2");
                    mostra_admin2.put("codi", codi);
                    mostra_admin2.put("accio", "mostra_admin");
                    mostra_admin2.put("nom_admin", "joan35");
                    mapOutputStream.writeObject(mostra_admin2);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    Object respostaObj = mapInputStream.readObject();
                    respostaMap = ( HashMap<String, String>) respostaObj;
                    System.out.println("Servidor: " + respostaMap);
                    
                    resposta = Integer.valueOf(respostaMap.get("codi_retorn"));
                    if (resposta == MOSTRA_ADMIN_OK){
                       System.out.println("Prova mostra_admin OK");
                    }else{
                        System.out.println("Admin no trobat");
                    }
                }
                
                if ("11".equals(entrada)){
                     System.out.println("\nProva llista_usuaris");
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
                    if (resposta == LLISTA_USUARIS_OK){
                       System.out.println("Prova llista_usuaris OK");
                    }else{
                        System.out.println("No s'ha pogut llistar");
                    }
                }
                
                
               if ("11".equals(entrada)){
                    System.out.println("\nProva modifica_admin");
                    long miliseconds = System.currentTimeMillis();
                    modifica_admin.put("codi", codi);
                    modifica_admin.put("accio", "modifica_admin");
                    modifica_admin.put("nom_admin", "marçal");
                    modifica_admin.put("nou_nom_admin", "marçal99");
                    modifica_admin.put("password", "1234");
                    modifica_admin.put("dni", "97789554h");
                    modifica_admin.put("correu", "marcal188@gmail.com");
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
                    if (resposta == MODIFICACIO_OK){
                       System.out.println("Prova modifica_admin OK");
                    }else if (resposta == FORMAT_EMAIL){
                        System.out.println("Format EMAIL no vàlid");
                    }else if (resposta == FORMAT_DNI){
                        System.out.println("Format DNI no vàlid");
                    }else if (resposta == FORMAT_PASSWORD){
                        System.out.println("Format PASSWORD no vàlid");
                    }
                    
                }
               
                if ("11".equals(entrada)){
                    System.out.println("\nProva modifica_admin 2");
                    long miliseconds = System.currentTimeMillis();
                    modifica_admin2.put("codi", codi);
                    modifica_admin2.put("accio", "modifica_admin");
                    modifica_admin2.put("nom_admin", "marçal99");
                    modifica_admin2.put("nou_nom_admin", "marçal99");
                    modifica_admin2.put("password", "1234");
                    modifica_admin2.put("dni", "97789554h");
                    modifica_admin2.put("correu", "marcal135gmail.com");
                    modifica_admin2.put("nom", "Marçal");
                    modifica_admin2.put("cognoms", "Perez");
                    modifica_admin2.put("data_naixement", "1989-11-11");
                    modifica_admin2.put("direccio", "Av del paraiso");
                    modifica_admin2.put("pais", "Moldavia");
                    modifica_admin2.put("telefon", "989876745");
                    
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(modifica_admin2);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    if (resposta == MODIFICACIO_OK){
                       System.out.println("Prova modifica_admin OK");
                    }else if (resposta == FORMAT_EMAIL){
                        System.out.println("Format EMAIL no vàlid");
                    }else if (resposta == FORMAT_DNI){
                        System.out.println("Format DNI no vàlid");
                    }else if (resposta == FORMAT_PASSWORD){
                        System.out.println("Format PASSWORD no vàlid");
                    }
                    
                }
                
                if ("11".equals(entrada)){
                    System.out.println("\nProva modifica_admin 3");
                    long miliseconds = System.currentTimeMillis();
                    modifica_admin3.put("codi", codi);
                    modifica_admin3.put("accio", "modifica_admin");
                    modifica_admin3.put("nom_admin", "marçal99");
                    modifica_admin3.put("nou_nom_admin", "marçal99");
                    modifica_admin3.put("password", "1234");
                    modifica_admin3.put("dni", "977e89554h");
                    modifica_admin3.put("correu", "marcal135@gmail.com");
                    modifica_admin3.put("nom", "Marçal");
                    modifica_admin3.put("cognoms", "Perez");
                    modifica_admin3.put("data_naixement", "1989-11-11");
                    modifica_admin3.put("direccio", "Av del paraiso");
                    modifica_admin3.put("pais", "Moldavia");
                    modifica_admin3.put("telefon", "989876745");
                    
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(modifica_admin3);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    if (resposta == MODIFICACIO_OK){
                       System.out.println("Prova modifica_admin OK");
                    }else if (resposta == FORMAT_EMAIL){
                        System.out.println("Format EMAIL no vàlid");
                    }else if (resposta == FORMAT_DNI){
                        System.out.println("Format DNI no vàlid");
                    }else if (resposta == FORMAT_PASSWORD){
                        System.out.println("Format PASSWORD no vàlid");
                    }
                    
                }
                
                if ("11".equals(entrada)){
                    System.out.println("\nProva modifica_admin 4");
                    long miliseconds = System.currentTimeMillis();
                    modifica_admin4.put("codi", codi);
                    modifica_admin4.put("accio", "modifica_admin");
                    modifica_admin4.put("nom_admin", "marçal99");
                    modifica_admin4.put("nou_nom_admin", "marçal99");
                    modifica_admin4.put("password", "124");
                    modifica_admin4.put("dni", "97789554h");
                    modifica_admin4.put("correu", "marcal135@gmail.com");
                    modifica_admin4.put("nom", "Marçal");
                    modifica_admin4.put("cognoms", "Perez");
                    modifica_admin4.put("data_naixement", "1989-11-11");
                    modifica_admin4.put("direccio", "Av del paraiso");
                    modifica_admin4.put("pais", "Moldavia");
                    modifica_admin4.put("telefon", "989876745");
                    
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(modifica_admin4);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    if (resposta == MODIFICACIO_OK){
                       System.out.println("Prova modifica_admin OK");
                    }else if (resposta == FORMAT_EMAIL){
                        System.out.println("Format EMAIL no vàlid");
                    }else if (resposta == FORMAT_DNI){
                        System.out.println("Format DNI no vàlid");
                    }else if (resposta == FORMAT_PASSWORD){
                        System.out.println("Format PASSWORD no vàlid");
                    }
                    
                }
                
                if ("11".equals(entrada)){
                    System.out.println("\nProva mostra_llibre");
                    mostra_llibre.put("codi", codi);
                    mostra_llibre.put("accio", "mostra_llibre");
                    mostra_llibre.put("id", "60");
                    mapOutputStream.writeObject(mostra_llibre);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    Object respostaObj = mapInputStream.readObject();
                    respostaMap = (HashMap<String, String>) respostaObj;
                    System.out.println("Servidor: " + respostaMap);
                    
                    resposta = Integer.valueOf(respostaMap.get("codi_retorn"));
                    
                    if (resposta == MOSTRA_LLIBRE_OK){
                       System.out.println("Prova mostra_llibre OK");
                    }else{
                        System.out.println("Llibre no trobat");
                    }
                }
                
                if ("11".equals(entrada)){
                    System.out.println("\nProva mostra_llibre 2");
                    mostra_llibre2.put("codi", codi);
                    mostra_llibre2.put("accio", "mostra_llibre");
                    mostra_llibre2.put("id", "10");
                    mapOutputStream.writeObject(mostra_llibre2);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    Object respostaObj = mapInputStream.readObject();
                    respostaMap = (HashMap<String, String>) respostaObj;
                    System.out.println("Servidor: " + respostaMap);
                    
                    resposta = Integer.valueOf(respostaMap.get("codi_retorn"));
                    
                    if (resposta == MOSTRA_LLIBRE_OK){
                       System.out.println("Prova mostra_llibre OK");
                    }else{
                        System.out.println("Llibre no trobat");
                    }
                }
                    
                if ("11".equals(entrada))
                    {System.out.println("\nProva llista_llibre");
                    llista_llibres.put("codi", codi);
                    llista_llibres.put("accio", "llista_llibres");
                    mapOutputStream.writeObject(llista_llibres);
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
                    
                    if (resposta == LLISTA_LLIBRES_OK){
                       System.out.println("Prova llista_llibres OK");
                    }else{
                        System.out.println("Llibres no trobat");
                    }
                }
                
                if ("11".equals(entrada)){
                     System.out.println("\nProva modifica_llibre");
                    long miliseconds = System.currentTimeMillis();
                    modifica_llibre.put("codi", codi);
                    modifica_llibre.put("accio", "modifica_llibre");
                    modifica_llibre.put("nom", "Harry Potter y el Càliz de fuego");
                    modifica_llibre.put("nou_nom", "Harry Potter y el Càliz de fuego");
                    modifica_llibre.put("autor", "JK. Rowling");
                    modifica_llibre.put("any_publicacio", "1997");
                    modifica_llibre.put("tipus", "Fantàsia");
                    modifica_llibre.put("descripcio", "Un llibre sobre un jove mag i els seus amics");
                    
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(modifica_llibre);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    
                    if (resposta == MODIFICA_LLIBRE_OK){
                       System.out.println("Prova modifica_llibre OK");
                    }else{
                        System.out.println("Llibre no trobat");
                    }
                }
                
                if ("11".equals(entrada)){
                    System.out.println("\nProva modifica_llibre 2");
                    long miliseconds = System.currentTimeMillis();
                    modifica_llibre2.put("codi", codi);
                    modifica_llibre2.put("accio", "modifica_llibre");
                    modifica_llibre2.put("nom", "Harry Potter y el Càliz de fuego ZZ");
                    modifica_llibre2.put("nou_nom", "Harry Potter y el Càliz de fuego");
                    modifica_llibre2.put("autor", "JK. Rowling");
                    modifica_llibre2.put("any_publicacio", "1997");
                    modifica_llibre2.put("tipus", "Fantàsia/Infantil");
                    modifica_llibre2.put("descripcio", "Un llibre sobre un jove mag i els seus amics");
                    
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(modifica_llibre2);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    
                    if (resposta == MODIFICA_LLIBRE_OK){
                       System.out.println("Prova modifica_llibre OK");
                    }else{
                        System.out.println("Llibre no trobat");
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