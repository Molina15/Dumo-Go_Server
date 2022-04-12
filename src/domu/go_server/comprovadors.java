/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domu.go_server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Fx
 */
public class comprovadors {
    
    private static int ERROR_EN_EL_SERVIDOR = 0000;

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
    private static int DNI_JA_EXISTENT = 1041;
    private static int FORMAT_EMAIL_INCORRECTE = 1040;
    private static int EMAIL_JA_EXISTENT = 1041;
    
    private static int ADMIN_AFEGIT = 2000;
    /**
    Clase que permite validar un DNI.  
    Se crea un objeto del tipo ValidadorDNI y se le pasa un String a validar.
    @return true si DNI es correcto.
    Desarrollado por Manuel Mato.
    */
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
        String nom_user = dades.get("nom_user");
        String password = dades.get("password");
        String sentencia = "SELECT nom_user, password FROM usuaris WHERE nom_user = '"+nom_user+"';";
        //System.out.println(sentencia.toString());
        stmt.executeQuery(sentencia);
        try{
            ResultSet rs = stmt.executeQuery(sentencia);
            if (rs.next()){
                //System.out.println(password+", "+rs.getString(2));
                password = rs.getString(2);
                if (password.toString().equals(password.toString())){
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
                //System.out.println(password+", "+rs.getString(2));
                password = rs.getString(2);
                if (password.toString().equals(password.toString())){
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
        
        String nom_user = dades.get("nom_user");
        String password = dades.get("password");
        String dni = dades.get("dni").toUpperCase();
        String data_alta = dades.get("data_alta");
        String correu = dades.get("correu");
        String admin_alta = dades.get("admin_alta");
        String nom_cognoms = dades.get("nom_cognoms");
        
        String sentencia = "INSERT INTO public.usuaris(nom_user, password, dni, data_alta, correu, admin_alta, nom_cognoms)"
                + " VALUES ('"+nom_user+"','"+password+"','"+dni+"','"+data_alta+"','"+correu+"','"+admin_alta+"','"+nom_cognoms+"');";
        
        comprobador = comprobarUsuari(stmt, dades);
        
        if (comprobador == 8010){
            if (password.length() >= 4){
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
        String nom_cognoms = dades.get("nom_cognoms");
        String dni = dades.get("dni").toUpperCase();
        String correu = dades.get("correu");
        String admin_alta = dades.get("admin_alta");
        
        String sentencia = "INSERT INTO "+ taula + "(nom_admin, password, nom_cognoms, dni, correu, admin_alta)"
                + " VALUES ('"+nom_admin+"','"+password+"','"+nom_cognoms+"','"+dni+"','"+correu+"','"+admin_alta+"');";
        
        comprobador = comprobarAdmin(stmt, dades);
        
        if (comprobador == 7010){
            if (password.length() >= 4){
                if(comprobaFormatDNI(dni)){
                    if(trobaDNI(stmt, dni, taula) == false){
                        if(comprobaFormatEmail(correu)){
                            if(trobaCorreu(stmt, correu, taula) == false){
                                try{
                                    stmt.executeUpdate(sentencia);
                                    resposta = 2000;
                                }catch (SQLException ex) {
                                    System.out.println("Error: "+ ex);
                                    resposta = 2050;
                                }
                            }else{
                                resposta = 2041;
                            }
                        }else{
                            resposta = 2040;
                        }
                    }else{
                        resposta = 2031;
                    }
                }else{
                    resposta = 2030;
                }
            }else{
                resposta = 2020;
            }
        }else{
            resposta = 2010;
        }
        
        return resposta;
    }
    
    public static int esborraUsuari(Statement stmt, HashMap<String, String> dades) throws SQLException{
        String nom_user = dades.get("nom_user");
        int resposta = 3020;
        int comprobador = comprobarUsuari(stmt, dades);
        try{
            if (comprobador != 8010){
                String sentencia = "DELETE FROM usuaris WHERE nom_user = '"+ nom_user +"';";
                stmt.executeUpdate(sentencia);
                resposta = 3000;
            }else{
                resposta = 3010;
            }
        }catch (SQLException ex) {
            System.out.println("Error: "+ ex);
            resposta = 3020;
        }
        return resposta;
        
    }
    
    public static int esborraAdmin(Statement stmt, HashMap<String, String> dades) throws SQLException{
        String nom_admin = dades.get("nom_admin");
        int resposta = 3020;
        int comprobador = comprobarAdmin(stmt, dades);
        try{
            if (comprobador != 7010){
                String sentencia = "DELETE FROM administradors WHERE nom_admin = '"+ nom_admin +"';";
                stmt.executeUpdate(sentencia);
                resposta = 4000;
            }else{
                resposta = 4010;
            }
        }catch (SQLException ex) {
            System.out.println("Error: "+ ex);
            resposta = 4020;
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
 
        if (mather.find() == true) {
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
                resposta = true;
            }else{
                resposta = false;
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
        return resposta;
     } 
        

    
}
