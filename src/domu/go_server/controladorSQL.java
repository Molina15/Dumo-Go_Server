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
import domu.go_server.comprovadors;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;
import java.util.Arrays;  


/**
 *
 * @author Adrià Molina Inglada
 */
public class controladorSQL {
    
    private static String host, port, DB, USER, PSW;

    private static Connection connexio;
    
    private static List<String> noms_admin_connectats = new ArrayList<String>();
    
    private static List<String> codis_admin_connectats = new ArrayList<String>();
    private static List<String> codis_usuaris_connectats = new ArrayList<String>();
    
    private static HashMap<String, String[]> llista_codis_admin = new HashMap<String, String[]>();
    
    private static final String taula_codis_admin[][] = new String[100][3];
    
    private static final int SESSIO_CADUCADA = 10;
    private static final int SESSIO_TANCADA = 20;
    
    private static final int CODI_INICI = 10000;
    private static final int CODI_FINAL = 10100;
    
    public static void obrir() throws SQLException, JSchException{
        
        /**
        String sshUser = "ubuntu"; // SSH loging username
        String sshPassword = "ubuntu"; // SSH login password
        String sshHost = "3.93.172.93"; // hostname or ip or SSH server
        int sshPort = 22; // remote SSH host port number
        
        final JSch jsch = new JSch();
        Session session = jsch.getSession(sshUser, sshHost, sshPort);
        session.setPassword(sshPassword);
        
        final Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        
        session.connect();
        **/
        
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
    
    public static int realitza_accio(HashMap<String, String> dades) throws SQLException{
        String sentencia = null;
        Statement stmt = connexio.createStatement(); //Usamos create... no prepare...
        int resposta = 0;
        int resultat = 0;
        System.out.println(dades);

        try{
            switch(dades.get("accio")){
                // rep HashMap amb les claus(accio, nom_user, password)
                case "comprobar_usuari":
                    resultat = comprovadors.comprobarUsuari(stmt, dades);
                    if (resultat == 8000){
                        resposta = generaCodi(); //funcio que generara el codi
                        codis_usuaris_connectats.add(String.valueOf(resposta));
                       
                        System.out.println(codis_usuaris_connectats);
                    }else{
                        //enviem codi d'error
                        resposta = resultat;
                    }
                    break;
                
                // rep HashMap amb les claus(accio, nom_admin, password)
                case "comprobar_admin":
                    resultat = comprovadors.comprobarAdmin(stmt, dades);
                    if (resultat == 7000){
                        resposta = generaCodi(); //funcio que generara el codi
                        
                        codis_admin_connectats.add(String.valueOf(resposta));
                        //noms_admin_connectats.add(dades.get("nom_admin"));
                        System.out.println("Codis admin: " + codis_admin_connectats);
                        //System.out.println("Noms  admin: " + noms_admin_connectats);
                        
                        /**
                        String dades_admin[] = {dades.get("nom_admin"), ""}; //l'ultim parametre sera el temps de conexio;
                        llista_codis_admin.put(String.valueOf(resposta), dades_admin);
                        String info[] = llista_codis_admin.get(String.valueOf(resposta));
                        System.out.println(llista_codis_admin.keySet() + " -> " + Arrays.asList(info));
                        * **/
                        
                        /**
                        int posicioX = taula_codis_admin.length;
                        taula_codis_admin[posicioX][0] = String.valueOf(resposta);
                        taula_codis_admin[posicioX][1] = dades.get("nom_admin");
                        taula_codis_admin[posicioX][2] = "contador";
                        
                        for(int row = 0; row < 100; row++){
                            for(int col = 0; col < 3; col++)
                                System.out.print(taula_codis_admin[row][col] + " ");
                            System.out.println();
                        }
                        * **/
                        
                    }else{ 
                        //enviem codi d'error
                        resposta = resultat;
                    }
                    break;
                     
                case "afegir_llibre":
                    System.out.println(dades.get("codi"));
                    System.out.println(codis_admin_connectats.contains(dades.get("codi")));
                    if (trobaCodiAdmin(dades.get("codi"))){
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

                case "esborrar_llibre":
                    if (trobaCodiAdmin(dades.get("codi"))){
                        sentencia = "DELETE FROM llibres WHERE nom = '"+dades.get("nom")+"';";
                        stmt.executeUpdate(sentencia);
                        System.out.print(sentencia.toString());
                        resposta = 1;
                    }else{
                        resposta = SESSIO_CADUCADA;
                    }
                    break;
                    
                case "esborrar_usuari":
                    if (trobaCodiAdmin(dades.get("codi"))){
                        resposta = comprovadors.esborraUsuari(stmt, dades);
                    }else{
                       resposta = SESSIO_CADUCADA; 
                    }
                    break;
                    
                case "afegir_usuari":
                    if (trobaCodiAdmin(dades.get("codi"))){
                        resposta = comprovadors.afegeixNouUsuari(stmt, dades);
                    }else{
                        resposta = SESSIO_CADUCADA;
                    }
                    break;
                    
                case "afegir_admin":
                    if (trobaCodiAdmin(dades.get("codi"))){
                        System.out.println("gola");
                        resposta = comprovadors.afegeixNouAdmin(stmt, dades);
                    }else{
                        resposta = SESSIO_CADUCADA;
                    }
                    break;
                    
                case "esborrar_admin":
                    if (trobaCodiAdmin(dades.get("codi"))){
                        resposta = comprovadors.esborraAdmin(stmt, dades);
                    }else{
                        resposta = SESSIO_CADUCADA;
                    }
                    break;
                
                case "tancar_sessio":
                    if (trobaCodiAdmin(dades.get("codi"))){
                        
                        resultat = obtenirIndexAdmin(dades.get("codi"));
                        codis_admin_connectats.remove(dades.get("codi"));
                        //noms_admin_connectats.remove(resultat);
                        
                        /**
                        llista_codis_admin.remove(dades.get("codi"));
                        * **/
                        
                        resposta = SESSIO_TANCADA;
                    }else if(trobaCodiUsuari(dades.get("codi"))){
                        codis_usuaris_connectats.remove(dades.get("codi"));
                        resposta = SESSIO_TANCADA;
                    }else{
                        resposta = SESSIO_CADUCADA;
                    }
            }

        }catch (SQLException ex) {
            System.out.println("Error: "+ ex);
        }
        
        
        return resposta;
    }
    
    private static boolean trobaCodiAdmin(String codi){
        return codis_admin_connectats.contains(codi);
    }
    
    private static boolean trobaCodiUsuari(String codi){
        return codis_usuaris_connectats.contains(codi);
    }
    
    private static void esborraCodiAdmin(String codi){
        codis_admin_connectats.remove(codi);
    }
    private static void esborraCodiUsuari(String codi){
        codis_usuaris_connectats.remove(codi);
    }
    
    private static int generaCodi(){
        int codi;
        do{
            codi = (int) Math.floor(Math.random()*(CODI_FINAL - CODI_INICI + 1) + CODI_INICI);
        } while (trobaCodiAdmin(String.valueOf(codi)) != false && trobaCodiUsuari(String.valueOf(codi)) != false);
        return codi;    
    }
    
    private static int obtenirIndexAdmin(String codi) {
        
        int indexCodi = codis_admin_connectats.indexOf(codi);
        return indexCodi;

    }
   
}
