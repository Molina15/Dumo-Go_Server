/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domu.go_server;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
 * @author Adrià Molina Inglada
 */
public class funcionsAux {
    
    public static ArrayList llistaResultat(ResultSet rs, String missatge, int codi_resposta_ok) throws SQLException{
        ArrayList respostaArrayMap = new ArrayList();
        HashMap<String, String> aux_user_map = null;
        ResultSetMetaData md = rs.getMetaData();
        String codi_resposta = Integer.toString(codi_resposta_ok);
        int columns = md.getColumnCount();
        int i = 1;
        int d = 0;
        while(rs.next()){
            aux_user_map = new HashMap<String, String>();
            for(i=1; i<=columns; ++i){
                aux_user_map.put(md.getColumnName(i), rs.getString(i));
            } 
            respostaArrayMap.add(aux_user_map);
            d++;
        }
        
        if(respostaArrayMap.size() == 0){
            respostaArrayMap.add(aux_user_map);
        }

        aux_user_map = (HashMap) respostaArrayMap.get(0);
        aux_user_map.put("codi_retorn", codi_resposta);
        respostaArrayMap.set(0, aux_user_map); 
        int j = 0;
        
        System.out.println(missatge);
        while(j < respostaArrayMap.size()){
            System.out.println(respostaArrayMap.get(j));
            j++;
        }
        return respostaArrayMap;
    }
    
    public static boolean trobaCorreu(Statement stmt, String correu, String taula) throws SQLException{
         boolean resposta = false;
        String sentencia = "SELECT correu FROM "+taula+" WHERE correu = '"+correu+"';";
        System.out.println(sentencia.toString());
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
    
    public static boolean comprobaPassword(String password){
        String null_str = "null";
        return (password.length() >= 4 && !password.equals(null_str));
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
    
    public static boolean trobaDNI(Statement stmt, String dni, String taula) throws SQLException{
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
    
    public static boolean comprobaFormatEmail(String correu){
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
    
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
            }
            return hashtext;
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
}
