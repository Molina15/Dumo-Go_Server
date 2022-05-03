/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domu.go_server;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.JSchException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import domu.go_server.controladorSQL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;
import java.util.Arrays;  
import domu.go_server.controladorUsuaris;
import java.sql.Array;


/**
 *
 * @author Adrià Molina Inglada
 */
public class AccionsServidor {
    
    private static String host, port, DB, USER, PSW;

    private static Connection connexio;
    
    private static String taula_admin_connectats[][] = new String[10][3];
    private static String taula_usuaris_connectats[][] = new String[100][3];
    private static int totalUsuaris = 0;
    private static int totalAdmin= 0;
    
    private static final int SESSIO_CADUCADA = 10;
    private static final int SESSIO_TANCADA = 20;
    
    private static final int CODI_INICI = 10000;
    private static final int CODI_FINAL = 10100;
    
    
    
    public static void obrir() throws SQLException, JSchException{
        
        host = "dumogo.c1zr1mmgguen.eu-west-3.rds.amazonaws.com";
        port = "5432";
        DB = "dumogo";
        USER = "dumogo";
        PSW = "dumogo2022";
        
        String URL = "jdbc:postgresql://"+host+":"+port+"/"+DB;
      
        connexio = DriverManager.getConnection(URL, USER, PSW);
        connexio.setAutoCommit(true);
        boolean valid = connexio.isValid(50000);
        System.out.println(valid ? "CONNEXIO OK" : "CONNEXIO FAIL");
    }
    
    public static void tancar() throws SQLException{
        if (connexio != null){
            connexio.close();
        }
    }
   
    public static Object realitza_accio(HashMap<String, String> dades) throws SQLException{
        String sentencia = null;
        Statement stmt = connexio.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                  ResultSet.CONCUR_UPDATABLE); //Usamos create... no prepare...
        Object resposta = null;
        HashMap<String, String> respostaMap = new HashMap<String, String>();
        ArrayList respostaArrayMap = new ArrayList();
        int resultat = 0;
        String strCodi;
        String nom_usuari;
        String nom_admin;
        int posicioAdmin = controladorUsuaris.trobaCodi(taula_admin_connectats, totalAdmin, dades.get("codi"));
        int posicioUsuari = controladorUsuaris.trobaCodi(taula_usuaris_connectats, totalUsuaris, dades.get("codi"));
        System.out.println("Map d'entrada: " + dades);

        try{
            switch(dades.get("accio")){
                // rep HashMap amb les claus(accio, nom_user, password)
                
                case "comprobar_usuari":
                    resultat = controladorSQL.comprobarUsuari(stmt, dades);
                    if (resultat == 8000){
                        posicioUsuari = controladorUsuaris.trobaUsuari(taula_usuaris_connectats, totalUsuaris, dades.get("user_name"));
                        if(posicioUsuari == -1){
                            resposta = generaCodi(); //funcio que generara el codi
                            strCodi = String.valueOf(resposta);
                            nom_usuari = dades.get("user_name");
                            taula_usuaris_connectats = controladorUsuaris.afegirSessio(taula_usuaris_connectats, totalUsuaris, strCodi, nom_usuari);
                            totalUsuaris++;
                            controladorUsuaris.mostra(taula_usuaris_connectats, totalUsuaris);
                        }else{
                            resposta = 8030;
                        }
                    }else{
                        //enviem codi d'error
                        resposta = resultat;
                    }
                    break;
                
                // rep HashMap amb les claus(accio, nom_admin, password)
                case "comprobar_admin":
                    resultat = controladorSQL.comprobarAdmin(stmt, dades);
                    if (resultat == 7000){
                        posicioAdmin = controladorUsuaris.trobaUsuari(taula_admin_connectats, totalAdmin, dades.get("nom_admin"));
                        if(posicioAdmin == -1){
                            resposta = generaCodi(); //funcio que generara el codi
                            strCodi = String.valueOf(resposta);
                            nom_admin = dades.get("nom_admin");
                            taula_admin_connectats = controladorUsuaris.afegirSessio(taula_admin_connectats, totalAdmin, strCodi, nom_admin);
                            totalAdmin++;
                            controladorUsuaris.mostra(taula_admin_connectats, totalAdmin);
                        }else{
                            resposta = 7030;
                        }
                    }else{ 
                        //enviem codi d'error
                        resposta = resultat;
                    }
                    break;
                
                //Funcio en estat BETA
                case "afegir_llibre":
                    posicioAdmin = controladorUsuaris.trobaCodi(taula_admin_connectats, totalAdmin, dades.get("codi"));
                    if (posicioAdmin != -1){
                        sentencia = "INSERT INTO llibres (nom, autor, any_publicacio, tipus, data_alta, reservat_dni, admin_alta) "
                                + "VALUES ('"+dades.get("nom")+"','"+dades.get("autor")+"','"+dades.get("any_publicacio")+"',"
                                + "'"+dades.get("tipus")+"','"+dades.get("data_alta")+"',"
                                + "'"+dades.get("reservat_dni")+"','"+dades.get("admin_alta")+"');";
                        stmt.executeUpdate(sentencia);
                        System.out.print(sentencia.toString());
                        resposta = 1;
                    }else{
                        resposta = SESSIO_CADUCADA;
                    }
                    break;
                        
                //Funcio en estat BETA
                case "esborrar_llibre":
                    posicioAdmin = controladorUsuaris.trobaCodi(taula_admin_connectats, totalAdmin, dades.get("codi"));
                    if (posicioAdmin != -1){
                        sentencia = "DELETE FROM llibres WHERE nom = '"+dades.get("nom")+"';";
                        stmt.executeUpdate(sentencia);
                        System.out.print(sentencia.toString());
                        resposta = 1;
                    }else{
                        resposta = SESSIO_CADUCADA;
                    }
                    break;
                    
                case "esborrar_usuari":
                    posicioAdmin = controladorUsuaris.trobaCodi(taula_admin_connectats, totalAdmin, dades.get("codi"));
                    if (posicioAdmin != -1){
                        resposta = controladorSQL.esborraUsuari(stmt, dades);
                        posicioUsuari = controladorUsuaris.trobaUsuari(taula_usuaris_connectats, totalUsuaris, dades.get("user_name"));
                        if (posicioUsuari != -1){
                            controladorUsuaris.eliminarSessio(taula_usuaris_connectats, totalUsuaris, posicioUsuari);
                            totalUsuaris--;
                        }
                    }else{
                       resposta = SESSIO_CADUCADA; 
                    }
                    break;
                    
                case "esborrar_admin":
                    posicioAdmin = controladorUsuaris.trobaCodi(taula_admin_connectats, totalAdmin, dades.get("codi"));
                    if (posicioAdmin != -1){
                        resposta = controladorSQL.esborraAdmin(stmt, dades);
                        posicioAdmin = controladorUsuaris.trobaUsuari(taula_admin_connectats, totalAdmin, dades.get("nom_admin"));
                        System.out.println(posicioAdmin);
                        if (posicioAdmin != -1){
                            controladorUsuaris.eliminarSessio(taula_admin_connectats, totalAdmin, posicioAdmin);
                            totalAdmin--;
                        }
                    }else{
                        resposta = SESSIO_CADUCADA;
                    }
                    break;
                    
                case "afegir_usuari":
                    posicioAdmin = controladorUsuaris.trobaCodi(taula_admin_connectats, totalAdmin, dades.get("codi"));
                    if (posicioAdmin != -1){
                        resposta = controladorSQL.afegeixNouUsuari(stmt, dades);
                    }else{
                        resposta = SESSIO_CADUCADA;
                    }
                    break;
                    
                case "afegir_admin":
                    posicioAdmin = controladorUsuaris.trobaCodi(taula_admin_connectats, totalAdmin, dades.get("codi"));
                    if (posicioAdmin != -1){
                        resposta = controladorSQL.afegeixNouAdmin(stmt, dades);
                    }else{
                        resposta = SESSIO_CADUCADA;
                    }
                    break;
                    
                case "tancar_sessio":
                    System.out.println("Tancant la sessio...");                   
                    System.out.println("Posicio admin: "+posicioAdmin);                 
                    System.out.println("Posicio usuari: "+ posicioUsuari);
                    if (posicioAdmin != -1){
                        
                        controladorUsuaris.eliminarSessio(taula_admin_connectats, totalAdmin, posicioAdmin);
                        totalAdmin--;
                        
                        resposta = SESSIO_TANCADA;
                    }else if(posicioUsuari != -1){
                        
                        controladorUsuaris.eliminarSessio(taula_usuaris_connectats, totalUsuaris, posicioUsuari);
                        totalUsuaris--;
                        
                        resposta = SESSIO_TANCADA;
                    }else{
                        resposta = SESSIO_CADUCADA;
                    }
                    break;
                    
                case "canvia_password":
                    if (posicioAdmin != -1){
                        
                        dades.put("nom_admin", taula_admin_connectats[posicioAdmin][1]);
                        resposta = controladorSQL.canviaPasswordAdmin(stmt, dades);
                        
                    }else if(posicioUsuari != -1){
                        
                        dades.put("user_name", taula_usuaris_connectats[posicioUsuari][1]);
                        resposta = controladorSQL.canviaPasswordUsuari(stmt, dades);
                        
                    }else{
                        resposta = SESSIO_CADUCADA;
                    } 
                    
                    break;
                    
                         
                case "modifica_usuari":
                    posicioAdmin = controladorUsuaris.trobaCodi(taula_admin_connectats, totalAdmin, dades.get("codi"));
                    posicioUsuari = controladorUsuaris.trobaCodi(taula_usuaris_connectats, totalUsuaris, dades.get("codi"));
                    if (posicioAdmin != -1){
                        //TODO
                    }
                    break;
                    
                case "mostra_usuari":
                    //System.out.println((posicioAdmin != -1) +" "+ (posicioUsuari != -1));
                    if (posicioAdmin != -1 || posicioUsuari != -1){
                        respostaMap = controladorSQL.mostraUsuari(stmt, dades);
                    }else{
                        respostaMap.put("codi_retorn", String.valueOf(SESSIO_CADUCADA));
                    } 
                    resposta = respostaMap;
                    break;
                    
                case "mostra_admin":
                    //System.out.println((posicioAdmin != -1) +" "+ (posicioUsuari != -1));
                    if (posicioAdmin != -1 || posicioUsuari != -1){
                        respostaMap = controladorSQL.mostraAdmin(stmt, dades);
                    }else{
                        respostaMap.put("codi_retorn", String.valueOf(SESSIO_CADUCADA));
                    } 
                    resposta = respostaMap;
                    break;
                    
                case "llista_usuaris":
                    if (posicioAdmin != -1 || posicioUsuari != -1){
                        System.out.println("Buscant a la llista d'usuaris...");
                        respostaArrayMap = controladorSQL.llistaUsuaris(stmt, dades);
                    }else{
                        respostaMap.put("codi_retorn", String.valueOf(SESSIO_CADUCADA));
                        respostaArrayMap.set(0, respostaMap);
                    } 
                    resposta = respostaArrayMap;
                    break;
                 
                case "llista_admins":
                    if (posicioAdmin != -1){
                        System.out.println("Buscant a la llista d'administradors...");
                        respostaArrayMap = controladorSQL.llistaAdmins(stmt, dades);
                    }else{
                        respostaMap.put("codi_retorn", String.valueOf(SESSIO_CADUCADA));
                        respostaArrayMap.set(0, respostaMap);
                    } 
                    resposta = respostaArrayMap;
                    break;
                    
            }

        }catch (SQLException ex) {
            System.out.println("Error: "+ ex);
        }
        
        return resposta;
        /**
        if (resposta != -10){
            System.out.println("Resposta numerica: "+resposta);
            return resposta;
        }else if(respostaMap != null){
            System.out.println("Resposta Map: "+respostaMap);
            return respostaMap;
        }else{
            System.out.println("Resposta d'ArrayMap: "+respostaArrayMap);
            return respostaArrayMap;
        }
        //return resposta;
        **/
    }
    
    
    private static int generaCodi(){
        int codi;
        int codiAdminRepetit;
        int codiUsuariRepetit;
        do{
            codi = (int) Math.floor(Math.random()*(CODI_FINAL - CODI_INICI + 1) + CODI_INICI);
            codiAdminRepetit = controladorUsuaris.trobaCodi(taula_admin_connectats, totalAdmin, Integer.toString(codi));
            codiUsuariRepetit = controladorUsuaris.trobaCodi(taula_usuaris_connectats, totalUsuaris, Integer.toString(codi));
        } while (codiAdminRepetit != -1 && codiUsuariRepetit != -1);
        return codi;    
    }
    
 
     
   
}
