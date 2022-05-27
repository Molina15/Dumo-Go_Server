/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domu.go_server;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author amolinai
 */
public class threadComprovadorSessio extends Thread {
    private static int milisecondsByDay = 86400000;
    private Statement stmt;
    private Date data_actual;
    private long miliseconds;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    public threadComprovadorSessio(){
        
    }
    
    //BETA
    @Override
    public void run(){
        while (true){
            miliseconds = System.currentTimeMillis();
            data_actual= new Date(miliseconds);
            
        }
    }
    
    
}
