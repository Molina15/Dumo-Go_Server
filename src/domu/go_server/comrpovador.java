/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domu.go_server;

import java.util.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Fx
 */
public class comrpovador extends Thread {
    
    public void run(Statement stmt, HashMap<String, String> dades) throws SQLException, ParseException{
        
        ArrayList llistaPrestecs = controladorSQL.llistaPrestecsNoRetornats(stmt, dades);
        HashMap<String, String> prestec = new HashMap();
        int j = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        long miliseconds = System.currentTimeMillis();
        Date data_actual= new Date(miliseconds);
        String data = null;
        int milisecondsByDay = 86400000;
        int dias = 0;
        
        while(j < llistaPrestecs.size()){
            System.out.println(llistaPrestecs.get(j));
            prestec =(HashMap) llistaPrestecs.get(j);

            data = prestec.get("data_retorn_real");
            Date data_final = formatter.parse(data);
            dias = (int) ((data_final.getTime()-data_actual.getTime()) / milisecondsByDay);
            if (dias < 3){
                String sentencia = "INSERT INTO avisos_prestecs (id_llibre, nom_user, data_prestec, data_retorn"
            }
            j++;
        }
    }
    
}
