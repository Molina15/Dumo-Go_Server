/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domu.go_server;

import com.jcraft.jsch.JSchException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Fx
 */
public class controladorSQL {
    
    public static int ERROR_EN_EL_SERVIDOR = 0;

    private static int USUARI_OK = 8000;
    private static int USUARI_NO_EXISTEIX = 8010;
    private static int PASSWORD_INCORRECTE = 8020;
    
    private static int ADMIN_OK = 7000;
    private static int ADMIN_NO_EXISTEIX = 7010;
    private static final int CONTRASENYA_ADMIN_INCORRECTE = 7020;
    
    private static int USUARI_AFEGIT = 1000;
    private static int NOM_USUARI_NO_VALID = 1010;
    private static int FORMAT_PASSWORD_NO_VALID = 1020;
    private static int FORMAT_DNI_INCORRECTE = 1030;
    private static int DNI_JA_EXISTENT = 1031;
    private static int FORMAT_EMAIL_INCORRECTE = 1040;
    private static int EMAIL_JA_EXISTENT = 1041;
    
    private static int ADMIN_AFEGIT = 2000;
    private static int NOM_ADMIN_NO_VALID = 2010;
    private static int FORMAT_PASSWORD_ADMIN_NO_VALID = 2020;
    private static int FORMAT_DNI_ADMIN_INCORRECTE = 2030;
    private static int DNI_ADMIN_JA_EXISTENT = 2031;
    private static int FORMAT_EMAIL_ADMIN_INCORRECTE = 2040;
    private static int EMAIL_ADMIN_JA_EXISTENT = 2041;
    
    private static int USUARI_ESBORRAT = 3000;
    private static int USUARI_INEXISTENT = 3010;
    
    private static int ADMIN_ESBORRAT = 4000;
    private static int ADMIN_INEXISTENT = 4010;
    
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

    public static int comprobarUsuari(Statement stmt, HashMap<String, String> dades) throws SQLException{
        int resposta;
        String nom_user = dades.get("user_name");
        String password = dades.get("password");
        String MD5password = funcionsAux.getMD5(password);
        String sentencia = "SELECT nom_user, password FROM usuaris WHERE nom_user = '"+nom_user+"';";
        System.out.println(sentencia.toString());
        stmt.executeQuery(sentencia);
        try{
            ResultSet rs = stmt.executeQuery(sentencia);
            if (rs.next()){
                System.out.println(rs.getString(2)+" "+MD5password);
                if (rs.getString(2).equals(MD5password)){
                    //si l'usuari existeix i la contrasenya és correcta
                    resposta = USUARI_OK;

                }else{
                    //contrasenya incorrecta
                    resposta = PASSWORD_INCORRECTE;

                }
            }else{
                //el nom d'usuari no existeix
                resposta = USUARI_NO_EXISTEIX;

            }
        }catch (SQLException ex){
            System.out.println(ex);
            //error en el servidor
            resposta = ERROR_EN_EL_SERVIDOR;
        }
        return resposta;
    }
    
    public static int comprobarAdmin(Statement stmt, HashMap<String, String> dades) throws SQLException{
        int resposta;
        String nom_admin = dades.get("nom_admin");
        String password = dades.get("password");
        String MD5password = funcionsAux.getMD5(password);
        String sentencia = "SELECT nom_admin, password FROM administradors WHERE nom_admin = '"+nom_admin+"';";
        System.out.println(sentencia.toString());
        stmt.executeQuery(sentencia);
        try{
            ResultSet rs = stmt.executeQuery(sentencia);
            if (rs.next()){
                System.out.println(rs.getString(2)+" "+MD5password);
                if (rs.getString("password").equals(MD5password)){
                    //si l'usuari existeix i la contrasenya es correcta
                    resposta = ADMIN_OK;

                }else{
                    //contrasenya incorrecta
                    resposta = CONTRASENYA_ADMIN_INCORRECTE;

                }
            }else{
                //el nom d'usuari no existeix
                resposta = ADMIN_NO_EXISTEIX;

            }
        }catch (SQLException ex){
            System.out.println(ex);
            //error en el servidor
            resposta = ERROR_EN_EL_SERVIDOR;
        }
        System.out.println (resposta);
        return resposta;
    }
    
    
    public static int afegeixNouUsuari(Statement stmt, HashMap<String, String> dades) throws SQLException{
        int resposta = 0;
        int comprobador;
        String taula = "usuaris";
        
        String nom_user = dades.get("user_name");
        String password = dades.get("password");
        String dni = dades.get("dni").toUpperCase();
        String data_alta = dades.get("data_alta");
        String correu = dades.get("correu");
        String admin_alta = dades.get("admin_alta");
        String nom = dades.get("nom");
        String cognoms = dades.get("cognoms");
        String direccio = dades.get("direccio");
        String pais = dades.get("pais");
        String telefon = dades.get("telefon");
        String data_naixement = dades.get("data_naixement");
        
        String sentencia = "INSERT INTO public.usuaris(nom_user, password, dni, data_alta, correu, admin_alta, nom, cognoms, direccio, pais, telefon, data_naixement)"
                + " VALUES ('"+nom_user+"',MD5('"+password+"'),'"+dni+"','"+data_alta+"','"+correu+"','"+admin_alta+"','"+nom+"',"
                + "'"+cognoms+"','"+direccio+"','"+pais+"','"+telefon+"','"+data_naixement+"');";
        
        if (trobaUsuari(stmt, nom_user)){
            if (funcionsAux.comprobaPassword(password)){
                if(funcionsAux.comprobaFormatDNI(dni)){
                    if(funcionsAux.trobaDNI(stmt, dni, taula) == false){
                        if(funcionsAux.comprobaFormatEmail(correu)){
                            if(funcionsAux.trobaCorreu(stmt, correu, taula) == false){
                                try{
                                    stmt.executeUpdate(sentencia);
                                    resposta = USUARI_AFEGIT;
                                }catch (SQLException ex) {
                                    System.out.println("Error: "+ ex);
                                    resposta = ERROR_EN_EL_SERVIDOR;
                                }
                            }else{
                                resposta = EMAIL_JA_EXISTENT;
                            }
                        }else{
                            resposta = FORMAT_EMAIL_INCORRECTE;
                        }
                    }else{
                        resposta = DNI_JA_EXISTENT;
                    }
                }else{
                    resposta = FORMAT_DNI_INCORRECTE;
                }
            }else{
                resposta = FORMAT_PASSWORD_NO_VALID;
            }
        }else{
            resposta = NOM_USUARI_NO_VALID;
        }
        
        return resposta;
    }
    
    public static int afegeixNouAdmin(Statement stmt, HashMap<String, String> dades) throws SQLException{
        int resposta = 0;
        int comprobador;
        String taula = "administradors";
        
        String nom_admin = dades.get("nom_admin");
        String password = dades.get("password");
        String nom = dades.get("nom");
        String dni = dades.get("dni").toUpperCase();
        String correu = dades.get("correu");
        String admin_alta = dades.get("admin_alta");
        String cognoms = dades.get("cognoms");
        String direccio = dades.get("direccio");
        String pais = dades.get("pais");
        String telefon = dades.get("telefon");
        String data_naixement = dades.get("data_naixement");
        
        String sentencia = "INSERT INTO "+ taula + "(nom_admin, password, nom, dni, correu, admin_alta, cognoms, direccio, pais, telefon, data_naixement)"
                + " VALUES ('"+nom_admin+"',MD5('"+password+"'),'"+nom+"','"+dni+"','"+correu+"','"+admin_alta+"',"
                + "'"+cognoms+"','"+direccio+"','"+pais+"','"+telefon+"','"+data_naixement+"');";
        
        if (trobaAdmin(stmt, nom_admin)){
            if (funcionsAux.comprobaPassword(password)){
                if(funcionsAux.comprobaFormatDNI(dni)){
                    if(funcionsAux.trobaDNI(stmt, dni, taula) == false){
                        if(funcionsAux.comprobaFormatEmail(correu)){
                            if(funcionsAux.trobaCorreu(stmt, correu, taula) == false){
                                try{
                                    stmt.executeUpdate(sentencia);
                                    resposta = ADMIN_AFEGIT;
                                }catch (SQLException ex) {
                                    System.out.println("Error: "+ ex);
                                    resposta = ERROR_EN_EL_SERVIDOR;
                                }
                            }else{
                                resposta = EMAIL_ADMIN_JA_EXISTENT;
                            }
                        }else{
                            resposta = FORMAT_EMAIL_ADMIN_INCORRECTE;
                        }
                    }else{
                        resposta = DNI_ADMIN_JA_EXISTENT;
                    }
                }else{
                    resposta = FORMAT_DNI_ADMIN_INCORRECTE;
                }
            }else{
                resposta = FORMAT_PASSWORD_ADMIN_NO_VALID;
            }
        }else{
            resposta = NOM_ADMIN_NO_VALID;
        }
        return resposta;
    }
    
    public static int esborraUsuari(Statement stmt, HashMap<String, String> dades) throws SQLException{
        String nom_user = dades.get("user_name");
        int resposta;
        try{
            if (trobaUsuari(stmt, nom_user)){
                String sentencia = "DELETE FROM usuaris WHERE nom_user = '"+ nom_user +"';";
                stmt.executeUpdate(sentencia);
                resposta = USUARI_ESBORRAT;
            }else{
                resposta = USUARI_INEXISTENT;
            }
        }catch (SQLException ex) {
            System.out.println("Error: "+ ex);
            resposta = ERROR_EN_EL_SERVIDOR;
        }
        return resposta;
    }
    
    public static Boolean trobaUsuari(Statement stmt, String user_name) throws SQLException{
        String sentencia = "select * from usuaris where nom_user = '"+user_name+"';";
        ResultSet rs = stmt.executeQuery(sentencia);
        if (rs.next()){
            return true;
        }else{
            return false;
        }
    }
    
    public static int esborraAdmin(Statement stmt, HashMap<String, String> dades) throws SQLException{
        String nom_admin = dades.get("nom_admin");
        int resposta;
        try{
            if (trobaAdmin(stmt, nom_admin)){
                String sentencia = "DELETE FROM administradors WHERE nom_admin = '"+ nom_admin +"';";
                stmt.executeUpdate(sentencia);
                resposta = ADMIN_ESBORRAT;
            }else{
                resposta = ADMIN_INEXISTENT;
            }
        }catch (SQLException ex) {
            System.out.println("Error: "+ ex);
            resposta = ERROR_EN_EL_SERVIDOR;
        }
        return resposta;
    }
    
    public static Boolean trobaAdmin(Statement stmt, String nom_admin) throws SQLException{
        String sentencia = "select * from administradors where nom_admin = '"+nom_admin+"';";
        ResultSet rs = stmt.executeQuery(sentencia);
        if (rs.next()){
            return true;
        }else{
            return false;
        }   
    }

    public static int canviaPasswordAdmin(Statement stmt, HashMap<String, String> dades) throws SQLException {
        String nom_admin = dades.get("nom_admin");
        int resposta;
        if (trobaAdmin(stmt, nom_admin)){
            String novaPassword = dades.get("password_nou");
            String sentencia = "UPDATE administradors SET password = MD5('"+novaPassword+"') WHERE nom_admin = '"+nom_admin+"';";
            System.out.println(sentencia.toString());
            try{
                stmt.executeUpdate(sentencia);
                resposta = CANVI_PASSWORD_OK;
            }catch (SQLException ex) {
                System.out.println("Error: "+ ex);
                resposta = ERROR_EN_EL_SERVIDOR;
            }
        }else{
            resposta = CONTRASENYA_NO_VALIDA;
        }
        
        return resposta;
        
    }
    
    public static int canviaPasswordUsuari(Statement stmt, HashMap<String, String> dades) throws SQLException {
        String nom_user = dades.get("user_name");
        int resposta;
        if (trobaUsuari(stmt, nom_user)){
            String novaPassword = dades.get("password_nou");
            String sentencia = "UPDATE usuaris SET password = MD5('"+novaPassword+"') WHERE nom_user = '"+nom_user+"';";
            System.out.println(sentencia.toString());
            try{
                stmt.executeUpdate(sentencia);
                resposta = CANVI_PASSWORD_OK;
            }catch (SQLException ex) {
                System.out.println("Error: "+ ex);
                resposta = ERROR_EN_EL_SERVIDOR;
            }
        }else{
            resposta = CONTRASENYA_NO_VALIDA;
        }
        
        return resposta;
        
    }
    
   public static HashMap<String, String> mostraUsuari(Statement stmt, HashMap<String, String> dades) throws SQLException { 
        HashMap<String, String> respostaMap = new HashMap<String, String>();;
        String codi_resposta;
        String nom_user = dades.get("user_name");
        String sentencia = "SELECT * FROM usuaris WHERE nom_user = '"+nom_user+"';";
        System.out.println(sentencia.toString());
        try{
            ResultSet rs = stmt.executeQuery(sentencia);
            if (rs.next()){
                codi_resposta = Integer.toString(MOSTRA_USUARI_OK);
                String numero_soci = rs.getString("id");
                String nom = rs.getString("nom");
                String dni = rs.getString("dni");
                String data_alta = rs.getString("data_alta");
                String correu = rs.getString("correu");
                String admin_alta = rs.getString("admin_alta");
                String cognoms = rs.getString("cognoms");
                String direccio = rs.getString("direccio");
                String pais = rs.getString("pais");
                String telefon = rs.getString("telefon");
                String data_naixement = rs.getString("data_naixement");
                
                System.out.println("Nom trobat: "+ nom +" "+ cognoms);

                respostaMap.put("numero_soci", numero_soci);
                respostaMap.put("user_name", nom_user);
                respostaMap.put("nom", nom);
                respostaMap.put("cognoms", cognoms);
                respostaMap.put("dni", dni);
                respostaMap.put("data_naixement", data_naixement);
                respostaMap.put("direccio", direccio);
                respostaMap.put("pais", pais);
                respostaMap.put("data_alta", data_alta);
                respostaMap.put("telefon", telefon);
                respostaMap.put("correu", correu);
                respostaMap.put("admin_alta", admin_alta);
                
            }else{
                codi_resposta = Integer.toString(USUARI_NO_VALID);
            }
        }catch(SQLException ex){
            System.out.println("Error: "+ ex);
            codi_resposta = Integer.toString(ERROR_EN_EL_SERVIDOR);
        }
        
        
        respostaMap.put("codi_retorn", codi_resposta);
        System.out.println(respostaMap);
        return respostaMap;
   }
   
   public static HashMap<String, String> mostraAdmin(Statement stmt, HashMap<String, String> dades) throws SQLException { 
        HashMap<String, String> respostaMap = new HashMap<String, String>();
        String codi_resposta;
        String nom_admin = dades.get("nom_admin");
        String sentencia = "SELECT * FROM administradors WHERE nom_admin = '"+nom_admin+"';";
        System.out.println(sentencia.toString());
        try{
            ResultSet rs = stmt.executeQuery(sentencia);
            if (rs.next()){
                codi_resposta = Integer.toString(MOSTRA_ADMIN_OK);
                String numero_soci = rs.getString("id");
                String nom = rs.getString("nom");
                String dni = rs.getString("dni");
                String correu = rs.getString("correu");
                String admin_alta = rs.getString("admin_alta");
                String cognoms = rs.getString("cognoms");
                String direccio = rs.getString("direccio");
                String pais = rs.getString("pais");
                String telefon = rs.getString("telefon");
                String data_naixement = rs.getString("data_naixement");
                
                System.out.println("Nom admin trobat: "+ nom +" "+ cognoms);

                respostaMap.put("numero_soci", numero_soci);
                respostaMap.put("nom_admin", nom_admin);
                respostaMap.put("nom", nom);
                respostaMap.put("cognoms", cognoms);
                respostaMap.put("dni", dni);
                respostaMap.put("data_naixement", data_naixement);
                respostaMap.put("direccio", direccio);
                respostaMap.put("pais", pais);
                respostaMap.put("telefon", telefon);
                respostaMap.put("correu", correu);
                respostaMap.put("admin_alta", admin_alta);
                
            }else{
                codi_resposta = Integer.toString(ADMIN_NO_VALID);
            }
        }catch(SQLException ex){
            System.out.println("Error: "+ ex);
            codi_resposta = Integer.toString(ERROR_EN_EL_SERVIDOR);
        }
        
        respostaMap.put("codi_retorn", codi_resposta);
        System.out.println(respostaMap);
        return respostaMap;
   }
   
      public static ArrayList llistaUsuaris(Statement stmt, HashMap<String, String> dades) throws SQLException { 
        String codi_resposta = null;
        String sentencia = "select nom_user as \"user_name\",* from usuaris order by nom_user;";
        System.out.println(sentencia);
        String missatge= "\n Usuaris per enviar: ";
        ResultSet rs = stmt.executeQuery(sentencia);
        
        return funcionsAux.llistaResultat(rs, missatge, LLISTA_USUARIS_OK);
    }
      
    public static ArrayList llistaAdmins(Statement stmt, HashMap<String, String> dades) throws SQLException { 
        String sentencia = "select * from administradors order by nom_admin;";
        System.out.println(sentencia);
        String missatge= "\n Administradors per enviar: ";
        ResultSet rs = stmt.executeQuery(sentencia);
        
        return funcionsAux.llistaResultat(rs, missatge, LLISTA_ADMINS_OK);
    }
        
    
    public static int modificaUsuari(Statement stmt, HashMap<String, String> dades) throws SQLException { 
        String taula = "usuaris";
        int resposta = modificaCamps(stmt, dades, taula);
        return resposta;                
    }
    
    public static int modificaAdmin(Statement stmt, HashMap<String, String> dades) throws SQLException { 
        String taula = "administradors";
        System.out.println("Modificant dades de l'administrador...");
        int resposta = modificaCamps(stmt, dades, taula);
        return resposta;                
    }
          
    public static int modificaCamps(Statement stmt, HashMap<String, String> dades, String taula) throws SQLException{
        int resposta = 0;
        String nouNom, user_name = "";
        String nom_admin = "";
        if (taula == "usuaris"){
            nouNom = dades.get("nou_user_name");
            user_name = dades.get("user_name");
            resposta = comprobarUsuari(stmt, dades);
        }else{
            nouNom = dades.get("nou_nom_admin");
            nom_admin = dades.get("nom_admin");
            resposta = comprobarAdmin(stmt, dades);
        }
        String numero_soci = dades.get("numero_soci");
        String password = dades.get("password");
        String dni = dades.get("dni").toUpperCase();
        String correu = dades.get("correu");
        String nom = dades.get("nom");
        String cognoms = dades.get("cognoms");
        String direccio = dades.get("direccio");
        String pais = dades.get("pais");
        String telefon = dades.get("telefon");
        String data_naixement = dades.get("data_naixement");

        if(funcionsAux.comprobaFormatDNI(dni)){
            if(funcionsAux.comprobaFormatEmail(correu)){
                resposta = MODIFICACIO_OK;
            }else{
                resposta = FORMAT_EMAIL;
            }
        }else{
            resposta = FORMAT_DNI;
        }

        if (resposta == MODIFICACIO_OK){
            String sentencia;
            if (taula == "usuaris"){
                sentencia = "UPDATE "+ taula + " SET (nom_user, nom, dni, correu, cognoms, direccio, pais, telefon, data_naixement)"
                + " = ('"+nouNom+"','"+nom+"','"+dni+"','"+correu+"','"+cognoms+"','"+direccio+"','"+pais+"','"+telefon+"',"
                + "'"+data_naixement+"') WHERE nom_user = '" +user_name+"';";
            }else{
                sentencia = "UPDATE "+ taula + " SET (nom_admin, nom, dni, correu, cognoms, direccio, pais, telefon, data_naixement)"
                + " = ('"+nouNom+"','"+nom+"','"+dni+"','"+correu+"','"+cognoms+"','"+direccio+"','"+pais+"','"+telefon+"',"
                + "'"+data_naixement+"') WHERE nom_admin = '" +nom_admin+"';";
            }   
            System.out.println(sentencia.toString());
            stmt.executeUpdate(sentencia);
        }

        return resposta;

    }
    
    public static int afegirLlibre(Statement stmt, HashMap<String, String> dades) throws SQLException{
        String sentencia; 
        int resposta = 0;
        sentencia = "INSERT INTO llibres (nom, autor, any_publicacio, tipus, data_alta, admin_alta, descripcio, caratula) "
        + "VALUES ('"+dades.get("nom")+"','"+dades.get("autor")+"','"+dades.get("any_publicacio")+"',"
        + "'"+dades.get("tipus")+"','"+dades.get("data_alta")+"','"+dades.get("admin_alta")+"','"+dades.get("descripcio")+"','"+dades.get("caratula")+"');";
        
        stmt.executeUpdate(sentencia);
        System.out.println(sentencia.toString());
        resposta = LLIBRE_AFEGIT;
        
        return resposta;
    }
        
    public static int esborraLlibre(Statement stmt, HashMap<String, String> dades) throws SQLException{
        int resposta = 0;
        String sentencia;
        sentencia = "DELETE FROM llibres WHERE id = '"+dades.get("id")+"';";
        int resultat = stmt.executeUpdate(sentencia);
        System.out.println(sentencia.toString()+ " "+ resultat);
        if (resultat == 0){
            resposta = LLIBRE_NO_TROBAT;
        }else{
            resposta = LLIBRE_ESBORRAT;
        }
        return resposta;
    }
    
    public static HashMap<String, String> mostraLlibre(Statement stmt, HashMap<String, String> dades) throws SQLException{
        HashMap<String, String> respostaMap = new HashMap<String, String>();
        String codi_resposta = "0";
        String sentencia = "SELECT * FROM llibres WHERE id = '"+dades.get("id")+"';";
        ResultSet rs = stmt.executeQuery(sentencia);
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        System.out.println(columns+ " "+ md.getColumnName(1));
        int i = 0;
        try{
            while (rs.next()){
                for( i=1; i<=columns; ++i){
                    respostaMap.put(md.getColumnName(i), rs.getString(i));
                }
                codi_resposta = Integer.toString(MOSTRA_LLIBRE_OK);
            }
        }catch(SQLException ex){
            System.out.println("Error: "+ ex);
            codi_resposta = Integer.toString(ERROR_EN_EL_SERVIDOR);
        }
        if (i == 0){
            codi_resposta = Integer.toString(MOSTRA_LLIBRE_NO_TROBAT);
        }
        System.out.println(respostaMap);
        respostaMap.put("codi_retorn", codi_resposta);
        return respostaMap;
    }
    
    public static ArrayList llistaLlibres(Statement stmt, HashMap<String, String> dades) throws SQLException { 
        String sentencia = "select * from llibres order by nom;";
        System.out.println(sentencia);
        String missatge= "\n Llibres per enviar: ";
        ResultSet rs = stmt.executeQuery(sentencia);
        
        return funcionsAux.llistaResultat(rs, missatge, LLISTA_LLIBRES_OK);
    }
    
    public static int modificaLlibre(Statement stmt, HashMap<String, String> dades) throws SQLException { 
        String referencia = dades.get("nom");
        String nom = dades.get("nou_nom");
        String autor = dades.get("autor");
        String any_publicacio = dades.get("any_publicacio");
        String tipus= dades.get("tipus");
        String descripcio = dades.get("descripcio");
        String caratula = dades.get("caratula");

        String sentencia = sentencia = "UPDATE llibres SET (nom, autor, any_publicacio, tipus, descripcio, caratula)"
                + " = ('"+nom+"','"+autor+"','"+any_publicacio+"','"+tipus+"','"+descripcio+"','"+caratula+"') WHERE nom = '" +referencia+"';";
        int i = stmt.executeUpdate(sentencia);
        int resposta = 0;
        if (i != 0){
            resposta = MODIFICA_LLIBRE_OK;
        }else{
            resposta = MODIFICA_NO_TROBAT;
        }
        System.out.print(i);
        return resposta; 
    }
    
    public static int puntuaLlibre(Statement stmt, HashMap<String, String> dades) throws SQLException{
        int valoracio_usuari = Integer.parseInt(dades.get("valoracio_usuari"));
        String id_llibre = dades.get("id_llibre");
        System.out.println("Introduïnt valoracio.");
        String sentencia = "Select valoracio, vots from llibres where id = '"+id_llibre+"';";
        System.out.println(sentencia.toString());
        ResultSet rs = stmt.executeQuery(sentencia);
        int valoracio = -1;
        int vots = -1;
        while(rs.next()){
            valoracio= rs.getInt(1);
            vots = rs.getInt(2);
        }
        System.out.println("Valoracio = "+valoracio+" Vots= "+vots);
        int suma_valoracio = valoracio*vots;
        int vots_actualitzats = vots + 1;
        int valoracio_actualitzada = (suma_valoracio + valoracio_usuari)/vots_actualitzats;
        sentencia = "Update llibres set (valoracio, vots) = ("+valoracio_actualitzada+", "+vots_actualitzats+") where id = '"+id_llibre+"';";
        stmt.executeUpdate(sentencia);
        return VALORACIO_OK;
    }
    
    public static int reservaLLibre(Statement stmt, HashMap<String, String> dades, String userName) throws SQLException{
        String id_llibre = dades.get("id_llibre");
        String sentencia = "select DNI from usuaris where nom_user = '"+ userName +"';";
        System.out.println(sentencia.toString());
        ResultSet rs = stmt.executeQuery(sentencia);
        String DNI = null;
        while (rs.next()){
            DNI = rs.getString(1);
        }
        if (DNI != null){
            sentencia = "Update llibres set user_name = '"+userName+"' where id = "+id_llibre+" and user_name = 'LLIURE';";
            System.out.println(sentencia.toString());
            int milisecondsByDay = 86400000;
            int resultat;
            resultat = stmt.executeUpdate(sentencia);
            if (0 < resultat){
                long miliseconds = System.currentTimeMillis();
                Date data_actual= new Date(miliseconds);
                Date data_retorn_teoric = new Date(data_actual.getTime() + (milisecondsByDay * 14));
                sentencia = "insert into prestecs (id_llibre, data_reserva, data_retorn_teoric) values ('"+id_llibre+"','"+data_actual+"','"+data_retorn_teoric+"');";
                resultat = stmt.executeUpdate(sentencia);
                if (0 < resultat){
                    return RESERVAT_OK; //llibre reservat
                }
            }else{
                return LLIBRE_JA_RESERVAT; //llibre ja reservat
            }
        }
        
        return 0;
    }
    
    public static int retornaLlibre(Statement stmt, HashMap<String, String> dades) throws SQLException{
        String id_llibre = dades.get("id_llibre");
        long miliseconds = System.currentTimeMillis();
        Date data_actual= new Date(miliseconds);
        String sentencia = "Update prestecs set data_retorn_real = '"+data_actual+"' where id_llibre = "+id_llibre+";";
        int resultat = stmt.executeUpdate(sentencia);
        if (0 < resultat){
            sentencia = "Update llibres set user_name = 'LLIURE' where id = "+id_llibre+";";
            resultat = stmt.executeUpdate(sentencia);
             if (0 < resultat){
                    sentencia = "update prestecs set avis_programat = false where id_llibre = "+id_llibre+";";
                    resultat = stmt.executeUpdate(sentencia);
                    return RETORN_OK;
                }
        }else{
            return LLIBRE_NO_PRESTAT; //llibre no prestat
        }
        return 0;
    }
    
    public static ArrayList llistaPrestecs(Statement stmt, HashMap<String, String> dades) throws SQLException{
        String sentencia = "select L.nom as \"nom_llibre\", P.* from prestecs P, llibres L where P.id_llibre = L.id order by data_retorn_teoric ASC;";
        
        System.out.println(sentencia);
        String missatge= "\nPrestecs per enviar: ";
        ResultSet rs = stmt.executeQuery(sentencia);
        
        return funcionsAux.llistaResultat(rs, missatge, LLISTA_PRESTECS_OK);
    }
    
    public static ArrayList llistaPrestecsNoRetornats(Statement stmt) throws SQLException{
        String sentencia = "select L.nom as \"nom_llibre\", P.* from prestecs P, llibres L where P.id_llibre = L.id and P.data_retorn_real is null order by data_retorn_teoric ASC;";
        
        System.out.println(sentencia);
        String missatge= "\nPrestecs per enviar: ";
        ResultSet rs = stmt.executeQuery(sentencia);
        
        return funcionsAux.llistaResultat(rs, missatge, LLISTA_PRESTECS_OK);
    }
    
    public static ArrayList llistaPrestecsUsuari(Statement stmt, HashMap<String, String> dades, String userName) throws SQLException{
        String sentencia = "select L.nom as \"nom_llibre\", P.* from prestecs P, llibres L where P.id_llibre = L.id and P.data_retorn_real "
                + "is null and P.user_name = '"+userName+"' order by data_retorn_teoric ASC;";
        
        System.out.println(sentencia);
        String missatge= "\n Prestecs usuari per enviar: ";
        ResultSet rs = stmt.executeQuery(sentencia);
        
        return funcionsAux.llistaResultat(rs, missatge, LLISTA_PRESTECS_USUARI_OK);
    }
    
     public static ArrayList llistaLlegitsUsuari(Statement stmt, HashMap<String, String> dades, String userName) throws SQLException{
        String sentencia = "select L.nom as \"nom_llibre\", P.* from prestecs P, llibres L where P.id_llibre = L.id and "
                + "P.user_name = '"+userName+"' and data_retorn_real is not null order by data_retorn_teoric ASC;";
        
        System.out.println(sentencia);
        String missatge= "\n Llegits per enviar: ";
        ResultSet rs = stmt.executeQuery(sentencia);
        
        return funcionsAux.llistaResultat(rs, missatge, LLISTA_LLEGITS_OK);
    }
     
    public static void marcaPrestec(Statement stmt, String id_llibre) throws SQLException{
        String sentencia = "Update prestecs set avis_programat = 'true' where id_llibre = "+id_llibre+";";
        stmt.executeUpdate(sentencia);
    }
    
    public static ArrayList llistaPrestecsUrgents(Statement stmt, HashMap<String, String> dades, String userName) throws SQLException{
        
        String sentencia = "Select * from prestecs where user_name = '"+userName+"' and avis_programat = true;";
        
        System.out.println(sentencia);
        String missatge= "\n Prestecs usuari per enviar: ";
        ResultSet rs = stmt.executeQuery(sentencia);
        
        return funcionsAux.llistaResultat(rs, missatge, LLISTA_PRESTECS_URGENTS_OK);
        
    }
    
    public static int afegeixComentari(Statement stmt, HashMap<String, String> dades) throws SQLException{
        System.out.println("hola");
        String sentencia; 
        int resposta = 0;
        System.out.println("hola");
        long miliseconds = System.currentTimeMillis();
        Date data= new Date(miliseconds);
        System.out.println(data.toString());
        System.out.println("hola");
        sentencia = "INSERT INTO comentaris (id_llibre, user_name, comentari, data) "
        + "VALUES ("+dades.get("id_llibre")+",'"+dades.get("user_name")+"','"+dades.get("comentari")+"','"+ data +"');";
        System.out.println("hola");
        stmt.executeUpdate(sentencia);
        System.out.println(sentencia.toString());
        resposta = COMENTARI_AFEGIT;
        
        return resposta;
    }
    
    public static int eliminaComentari(Statement stmt, HashMap<String, String> dades) throws SQLException{
        int resposta = 0;
        String id_comentari = dades.get("id_comentari");
        String sentencia = "DELETE FROM comentaris WHERE id = '"+ id_comentari +"';";
        int rs = stmt.executeUpdate(sentencia);
        if (rs == 1){
            resposta = COMENTARI_ESBORRAT;
        }else{
            resposta = COMENTARI_INEXISTENT;
        }
        return resposta;
    }
    
    public static int eliminaComentariUsuari(Statement stmt, HashMap<String, String> dades) throws SQLException{
        String id_comentari = dades.get("id_comentari");
        String sentencia = "select user_name from comentaris where id = '"+ id_comentari +"';";
        ResultSet rs = stmt.executeQuery(sentencia);
        String userNameRs = null;
        int resposta;
        while(rs.next()){
            userNameRs= rs.getString(1);
        }
        if (userNameRs.equals(dades.get("user_name"))){
            resposta = eliminaComentari(stmt, dades);
        }else {
            resposta = USUARI_SENSE_PERMISOS;
        }
        
        return resposta;
    }
    
    public static ArrayList llistaComentaris(Statement stmt, HashMap<String, String> dades) throws SQLException{
        String nom_llibre = dades.get("nom_llibre");
        String sentencia = "select C.* from comentaris C, llibres L where C.id_llibre = L.id "
                + "and L.nom = '"+nom_llibre+"' order by data ASC;";
        
        System.out.println(sentencia);
        String missatge= "\nComentaris per enviar: ";
        ResultSet rs = stmt.executeQuery(sentencia);
        
        return funcionsAux.llistaResultat(rs, missatge, LLISTA_COMENTARIS_OK);
    }
}
