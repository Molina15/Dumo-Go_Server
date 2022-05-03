/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domu.go_server;

import com.jcraft.jsch.JSchException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
    
    private static int ERROR_EN_EL_SERVIDOR = 0;

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
    
    private static int CANVI_PASSWORD_OK = 9000;
    private static int CONTRASENYA_NO_VALIDA = 9010;
    
    public static int MAX_LLISTA = 5;
    
    
 
    /**
    Clase que permite validar un DNI.  
    Se crea un objeto del tipo ValidadorDNI y se le pasa un String a validar.
    @return true si DNI es correcto.
    Desarrollado por Manuel Mato.
    */
    
    public static boolean comprobaPassword(String password){
        return (password.length() >= 4);
    }
    
    public static boolean comprobaFormatDNI(String dni) {
        
        boolean resposta;

        String letraMayuscula = ""; //Guardaremos la letra introducida en formato mayúscula

        // Aquí excluimos cadenas distintas a 9 caracteres que debe tener un dni/NIE y también si el último caracter no es una letra
        if(dni.length() != 9 || Character.isLetter(dni.charAt(8)) == false ) {
            resposta = false;
        }else{
            //comprobem si és un NIE
            if (Character.isLetter(dni.charAt(0))){
                //revisem la part on hi hauria d'haver numeros
                String PartNumerica = dni.substring(1,7);
                boolean isNumeric = PartNumerica.chars().allMatch( Character::isDigit );
                //Si a la part numerica tot son numeros és un NIE
                if (isNumeric){
                    resposta = true;
                }else{
                    resposta = false;
                }
            }else{
                //sino te una lletra al principi, el considerem un possible DNI
                String PartNumerica = dni.substring(0,7);
                boolean isNumeric = PartNumerica.chars().allMatch( Character::isDigit );
                //Si a la part numerica tot son numeros és un NIE
                if (isNumeric){
                    resposta = true;
                }else{
                    resposta = false;
                }
            }
        }
        return resposta;
       
    }
    
    public static int comprobarUsuari(Statement stmt, HashMap<String, String> dades) throws SQLException{
        int resposta;
        String nom_user = dades.get("user_name");
        String password = dades.get("password");
        String sentencia = "SELECT nom_user, password FROM usuaris WHERE nom_user = '"+nom_user+"';";
        //System.out.println(sentencia.toString());
        stmt.executeQuery(sentencia);
        try{
            ResultSet rs = stmt.executeQuery(sentencia);
            if (rs.next()){
                //System.out.println(password+", "+rs.getString(2));
                if (rs.getString(2).equals(password)){
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
        String sentencia = "SELECT nom_admin, password FROM administradors WHERE nom_admin = '"+nom_admin+"';";
        System.out.println(sentencia.toString());
        stmt.executeQuery(sentencia);
        try{
            ResultSet rs = stmt.executeQuery(sentencia);
            if (rs.next()){
                System.out.println(password+", "+rs.getString(2));
                if (rs.getString(2).equals(password)){
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
                + " VALUES ('"+nom_user+"','"+password+"','"+dni+"','"+data_alta+"','"+correu+"','"+admin_alta+"','"+nom+"',"
                + "'"+cognoms+"','"+direccio+"','"+pais+"','"+telefon+"','"+data_naixement+"');";
        
        comprobador = comprobarUsuari(stmt, dades);
        
        if (comprobador == USUARI_NO_EXISTEIX){
            if (comprobaPassword(password)){
                if(comprobaFormatDNI(dni)){
                    if(trobaDNI(stmt, dni, taula) == false){
                        if(comprobaFormatEmail(correu)){
                            if(trobaCorreu(stmt, correu, taula) == false){
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
                + " VALUES ('"+nom_admin+"','"+password+"','"+nom+"','"+dni+"','"+correu+"','"+admin_alta+"',"
                + "'"+cognoms+"','"+direccio+"','"+pais+"','"+telefon+"','"+data_naixement+"');";
        
        comprobador = comprobarAdmin(stmt, dades);
        
        if (comprobador == ADMIN_NO_EXISTEIX){
            if (comprobaPassword(password)){
                if(comprobaFormatDNI(dni)){
                    if(trobaDNI(stmt, dni, taula) == false){
                        if(comprobaFormatEmail(correu)){
                            if(trobaCorreu(stmt, correu, taula) == false){
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
        int comprobador = comprobarUsuari(stmt, dades);
        try{
            if (comprobador != USUARI_NO_EXISTEIX){
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
    
    
    public static int esborraAdmin(Statement stmt, HashMap<String, String> dades) throws SQLException{
        String nom_admin = dades.get("nom_admin");
        int resposta;
        int comprobador = comprobarAdmin(stmt, dades);
        try{
            if (comprobador != ADMIN_NO_EXISTEIX){
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
    
    private static boolean trobaDNI(Statement stmt, String dni, String taula) throws SQLException{
        boolean resposta = false;
        String sentencia = "SELECT dni FROM "+taula+" WHERE dni = '"+dni+"';";
        System.out.println(sentencia.toString());
        stmt.executeQuery(sentencia);
        try{
            ResultSet rs = stmt.executeQuery(sentencia);
            if (rs.next()){
                resposta = true;
            }else{
                resposta = false;
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
        return resposta;
    }
    
    private static boolean comprobaFormatEmail(String correu){
        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
 
        Matcher mather = pattern.matcher(correu);
 
        if (mather.find() == true){
            return true;
        } else {
            return false;
        }
    }
    
     private static boolean trobaCorreu(Statement stmt, String correu, String taula) throws SQLException{
         boolean resposta = false;
        String sentencia = "SELECT correu FROM "+taula+" WHERE correu = '"+correu+"';";
        System.out.println(sentencia.toString());
        stmt.executeQuery(sentencia);
        try{
            ResultSet rs = stmt.executeQuery(sentencia);
            if (rs.next()){
                System.out.println("Correu: " + rs.next());
                resposta = true;
            }else{
                resposta = false;
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
        return resposta;
     } 

    public static int canviaPasswordAdmin(Statement stmt, HashMap<String, String> dades) throws SQLException {
        int comproba = comprobarAdmin(stmt, dades);
        int resposta;
        if (comproba == ADMIN_OK){
            String novaPassword = dades.get("password_nou");
            String nom_admin = dades.get("nom_admin");
            String sentencia = "UPDATE administradors SET password = '"+novaPassword+"' WHERE nom_admin = '"+nom_admin+"';";
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
        int comproba = comprobarUsuari(stmt, dades);
        int resposta;
        if (comproba == USUARI_OK){
            String novaPassword = dades.get("password_nou");
            String nom_usuari = dades.get("user_name");
            String sentencia = "UPDATE usuaris SET password = '"+novaPassword+"' WHERE nom_user = '"+nom_usuari+"';";
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
    
    public static int modificaUsuari(Statement stmt, HashMap<String, String> dades, String taula) throws SQLException {
        return 0;
        
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
                codi_resposta = "5000";
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
                codi_resposta = Integer.toString(USUARI_NO_EXISTEIX);
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
                codi_resposta = "6000";
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
                codi_resposta = Integer.toString(ADMIN_NO_EXISTEIX);
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
        String sentencia = "select nom_user from usuaris order by nom_user;";
        System.out.println(sentencia.toString());
        ArrayList respostaArrayMap = new ArrayList();
        String nom_user;
        HashMap<String, String> aux_user_map = new HashMap<String, String>();
        try{
            ResultSet rs = stmt.executeQuery(sentencia);
            ArrayList llistaNoms = new ArrayList();
            while(rs.next()){
                llistaNoms.add(rs.getString("nom_user"));
            }
            int i = 0;
            while (i < llistaNoms.size()){
                nom_user = (String) llistaNoms.get(i);
                System.out.println("nom: "+nom_user);
                aux_user_map.put("user_name", nom_user);
                respostaArrayMap.add(mostraUsuari(stmt, aux_user_map));
                i++;
            }
            codi_resposta = "1100";
        }catch(SQLException ex){
            System.out.println("Error: "+ ex);
            codi_resposta = Integer.toString(ERROR_EN_EL_SERVIDOR);
        }
        aux_user_map = (HashMap) respostaArrayMap.get(0);
        aux_user_map.put("codi_retorn", codi_resposta);
        respostaArrayMap.set(0, aux_user_map);
        int j = 0;
        System.out.println("\n Llista d'usuaris per enviar:");
        while(j < respostaArrayMap.size()){
            System.out.println(respostaArrayMap.get(j));
            j++;
        }
        
        return respostaArrayMap;
    }
      
     public static ArrayList llistaAdmins(Statement stmt, HashMap<String, String> dades) throws SQLException { 
        String codi_resposta = null;
        String sentencia = "select nom_admin from administradors order by nom_admin;";
        System.out.println(sentencia.toString());
        ArrayList respostaArrayMap = new ArrayList();
        String nom_admin;
        HashMap<String, String> aux_admin_map = new HashMap<String, String>();
        try{
            ResultSet rs = stmt.executeQuery(sentencia);
            ArrayList llistaNoms = new ArrayList();
            while(rs.next()){
                llistaNoms.add(rs.getString("nom_admin"));
            }
            int i = 0;
            while (i < llistaNoms.size()){
                nom_admin = (String) llistaNoms.get(i);
                System.out.println("nom: "+nom_admin);
                aux_admin_map.put("nom_admin", nom_admin);
                respostaArrayMap.add(mostraAdmin(stmt, aux_admin_map));
                i++;
            }
            codi_resposta = "1100";
        }catch(SQLException ex){
            System.out.println("Error: "+ ex);
            codi_resposta = Integer.toString(ERROR_EN_EL_SERVIDOR);
        }
        aux_admin_map = (HashMap) respostaArrayMap.get(0);
        aux_admin_map.put("codi_retorn", codi_resposta);
        respostaArrayMap.set(0, aux_admin_map);
        int j = 0;
        System.out.println("\n Llista d'admins per enviar:");
        while(j < respostaArrayMap.size()){
            System.out.println(respostaArrayMap.get(j));
            j++;
        }
        
        return respostaArrayMap;
    }

        
}
