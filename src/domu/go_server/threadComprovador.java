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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fx
 */
public class threadComprovador extends Thread {
    private static int milisecondsByDay = 86400000;
    private Statement stmt;
    private ArrayList llistaPrestecs;
    private Date data_actual;
    private long miliseconds;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    public threadComprovador(Statement stmt, ArrayList llistaPrestecs){
        this.stmt=stmt;
        this.llistaPrestecs=llistaPrestecs;
    }
    
    @Override
    public void run(){
        
        while (true){
            miliseconds = System.currentTimeMillis();
            data_actual= new Date(miliseconds);
            System.out.print("Hora actual: [ "+ data_actual +" ] ");
            System.out.println("Comprovacio diaria de prestecs iniciada...");
            try {
                HashMap<String, String> prestec = new HashMap();
                int j = 0;
                String data = null;
                int dias = 0;
                while(j < llistaPrestecs.size()){
                    System.out.println(llistaPrestecs.get(j));
                    prestec =(HashMap) llistaPrestecs.get(j);
                    data = prestec.get("data_retorn_teoric");
                    Date data_final = null;
                    try {
                        data_final = formatter.parse(data);
                    } catch (ParseException ex) {
                        Logger.getLogger(threadComprovador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    dias = (int) ((data_final.getTime()-data_actual.getTime()) / milisecondsByDay);
                    if (dias < 3){
                        try {
                            String id_llibre = prestec.get("id_llibre");
                            controladorSQL.marcaPrestec(stmt, id_llibre);
                        } catch (SQLException ex) {
                            Logger.getLogger(threadComprovador.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    j++;
                }
                System.out.println("Comprovacio diaria de prestecs finalitzada...");
                //Thread.sleep(milisecondsByDay);
                Thread.sleep(1000*60);
            } catch (InterruptedException ex) {
                Logger.getLogger(threadComprovador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
