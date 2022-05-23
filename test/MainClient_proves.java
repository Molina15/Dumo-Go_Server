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
import java.io.EOFException;
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
import java.text.SimpleDateFormat;
//import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import static java.util.concurrent.TimeUnit.DAYS;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Date;
import java.util.Locale;

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
            HashMap<String, String> mostra_llibre = new HashMap<String, String>();
            HashMap<String, String> llista_llibres = new HashMap<String, String>();
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
                System.out.print("\nQue vols fer? \n 1 = afegir llibre \t\t 11 = mostra usuari \n 2 = esborrar_llibre \t\t 12 = mostra admin \n 3 = comprobar usuari \t\t 13 = llista usuaris" 
                + "\n 4 = comprobar admin \t\t 14 = llista admins \n 5 = afegir usuari \t\t 15 = modifica admin \n 6 = esborra_usuari \t\t 16 = modifica usuari \n 7 = afegir_admin \t\t 17 = mostra llibre \n 8 = esborra_admin \t\t 18 = llista llibres \n 9 = desconectar client \t 19 = modifica llibre \n"
                        + " 10 = canvia_password \t\t 20 = puntua llibre \n 21 = reserva_llibre \t\t 22 = retorna llibre \n 23 = llista prestecs \t\t 24 = llista prestecs usuari \n 25 = llista no retornats \t 26 = llista llegits per usuari \n- > ");
                
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
                    afegir_llibre.put("admin_alta", "molina15");
                    afegir_llibre.put("descripcio", "Un llibre molt guapo.");
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(afegir_llibre);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    
                }else if ("2".equals(entrada)){
                    esborra_llibre.put("codi", codi);
                    esborra_llibre.put("accio", "esborrar_llibre");
                    esborra_llibre.put("id", "56");
                    System.out.println(esborra_llibre);
                    mapOutputStream.writeObject(esborra_llibre);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    
                }else if ("3".equals(entrada)){
                    comprobar_usuari.put("accio", "comprobar_usuari");
                    comprobar_usuari.put("user_name","Pep25");
                    comprobar_usuari.put("password", "1234");
                    mapOutputStream.writeObject(comprobar_usuari);
                    System.out.println("Esperant confirmacio...");
                    resposta = (int) mapInputStream.readObject();
                    codi = String.valueOf(resposta);
                    System.out.println("Servidor (codi de sessió): " + resposta);
                
                }else if ("4".equals(entrada)){
                    comprobar_admin.put("accio", "comprobar_admin");
                    comprobar_admin.put("nom_admin","marçal99");
                    comprobar_admin.put("password", "12345");
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
                    afegir_usuari.put("user_name", "Kilian34");
                    afegir_usuari.put("password", "1234");
                    afegir_usuari.put("dni", "77733790H");
                    afegir_usuari.put("data_alta", date.toString());
                    afegir_usuari.put("correu", "kilian23@gmail.com");
                    afegir_usuari.put("admin_alta", "molina15");
                    afegir_usuari.put("nom", "kilian");
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
                    afegir_admin.put("nom_admin", "marçal99");
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
                
                else if ("17".equals(entrada)){
                    mostra_llibre.put("codi", codi);
                    mostra_llibre.put("accio", "mostra_llibre");
                    mostra_llibre.put("id", "58");
                    mapOutputStream.writeObject(mostra_llibre);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    Object respostaObj = mapInputStream.readObject();
                    respostaMap = (HashMap<String, String>) respostaObj;
                    System.out.println("Servidor: " + respostaMap);
                    
                    resposta = Integer.valueOf(respostaMap.get("codi_retorn"));
                }
                    
                if (resposta == SESSIO_CADUCADA){
                    System.out.println("Tancant sessio...");
                    online = false;
                }
                
                else if ("18".equals(entrada)){
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
                }
                
                else if ("19".equals(entrada)){
                    long miliseconds = System.currentTimeMillis();
                    modifica_admin.put("codi", codi);
                    modifica_admin.put("accio", "modifica_llibre");
                    modifica_admin.put("nom", "Harry Potter y el Càliz de fuego");
                    modifica_admin.put("nou_nom", "Harry Potter y el Càliz de fuego");
                    modifica_admin.put("autor", "JK. Rowling");
                    modifica_admin.put("any_publicacio", "1997");
                    modifica_admin.put("tipus", "Fantàsia/Infantil");
                    modifica_admin.put("descripcio", "Un llibre sobre un jove mag i els seus amics");
                    
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(modifica_admin);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                }
                
                else if ("20".equals(entrada)){
                    puntua_llibre.put("codi", codi);
                    puntua_llibre.put("accio", "puntua_llibre");
                    puntua_llibre.put("id_llibre", "69");
                    puntua_llibre.put("valoracio_usuari", "5");
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(puntua_llibre);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                }
                
                else if("21".equals(entrada)){
                    reserva_llibre.put("codi", codi);
                    reserva_llibre.put("accio", "reserva_llibre");
                    reserva_llibre.put("id_llibre", "75" );
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(reserva_llibre);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    
                }
                
                else if("22".equals(entrada)){
                    retorna_llibre.put("codi", codi);
                    retorna_llibre.put("accio", "retorna_llibre");
                    retorna_llibre.put("id_llibre", "75" );
                    //enviem la consulta al servidor
                    mapOutputStream.writeObject(retorna_llibre);
                    System.out.println("Esperant confirmacio...");
                    //rebem la resposta del servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    
                }
                
                else if("23".equals(entrada)){
                    llista_prestecs.put("codi", codi);
                    llista_prestecs.put("accio", "llista_prestecs");
                    mapOutputStream.writeObject(llista_prestecs);
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
                    
                }
                
                else if("24".equals(entrada)){
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
                    
                }
                
                else if("25".equals(entrada)){
                    llista_prestecs_no_retornats.put("codi", codi);
                    llista_prestecs_no_retornats.put("accio", "llista_prestecs_no_retornats");
                    mapOutputStream.writeObject(llista_prestecs_no_retornats);
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
                    
                }
                
                else if("26".equals(entrada)){
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
                    
                }
                
                else if("27".equals(entrada)){
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
                    
                }
                
                else if("28".equals(entrada)){
                    afegeix_comentari.put("codi", codi);
                    afegeix_comentari.put("accio", "afegeix_comentari");
                    afegeix_comentari.put("id_llibre", "59");
                    afegeix_comentari.put("comentari", "Esta bé.");
                    mapOutputStream.writeObject(afegeix_comentari);
                    System.out.println("Esperant confirmacio...");
                    //enviem la consulta al servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);
                    
                }
                
                else if("29".equals(entrada)){
                    elimina_comentari.put("codi", codi);
                    elimina_comentari.put("accio", "elimina_comentari");
                    elimina_comentari.put("id_comentari", "1");
                    mapOutputStream.writeObject(elimina_comentari);
                    System.out.println("Esperant confirmacio...");
                    //enviem la consulta al servidor
                    resposta = (int) mapInputStream.readObject();
                    System.out.println("Servidor: " + resposta);   
                }
                
                else if("30".equals(entrada)){
                    llista_comentaris.put("codi", codi);
                    llista_comentaris.put("accio", "llista_comentaris");
                    llista_comentaris.put("nom_llibre", "El nom del vent");
                    
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
                    
                }
                
                
                
                else if("90".equals(entrada)){
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    long miliseconds = System.currentTimeMillis();
                    Date data_actual = new Date(miliseconds);
                    System.out.println(data_actual.toString());
                    String data_string = "2022-05-17";
                    Date data_final = formatter.parse(data_string);
                    
                    System.out.println(data_actual.toString()+ " / "+ data_final.toString());
                    int milisecondsByDay = 86400000;
                    int dias = (int) ((data_final.getTime()-data_actual.getTime()) / milisecondsByDay);
                    System.out.println(dias);
                    int aux = (int)  data_final.getTime() + (7*milisecondsByDay);
                    Date tomorrow = new Date(data_final.getTime() + (1000 * 60 * 60 * 24*7));
                    //data_final = formatter.parse(Integer.toString(aux));
                    //System.out.println(data_final.toString());
                    System.out.println(tomorrow.toString());
                    
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