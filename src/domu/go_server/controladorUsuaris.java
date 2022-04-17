/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domu.go_server;

import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author Adrià Molina Inglada
 */
public class controladorUsuaris {
    
    public static String[][] afegirSessio(String[][] llistaUsuarisConnectats, 
           int totalUsuaris, String codi_sessio, String nom_usuari){
        
           llistaUsuarisConnectats[totalUsuaris][0] = codi_sessio;
           llistaUsuarisConnectats[totalUsuaris][1] = nom_usuari;
           llistaUsuarisConnectats[totalUsuaris][2] = ""; //aqui ira el contador 
      
        return llistaUsuarisConnectats;
    }
    
    public static int trobaCodi(String[][] llistaUsuarisConnectats, int totalUsuaris, String codiBuscat){
        int posicioUsuari = 0;
        String codi_usuari = "";
        int posicio = 0;
        
        for (int x=0; x < totalUsuaris; x++){
            codi_usuari = llistaUsuarisConnectats[x][0]; //la segona columna sempre contindra el nom dels usuaris
            System.out.println(codi_usuari+" - "+codiBuscat);
            System.out.println(codi_usuari.equals(codiBuscat));
            if (codi_usuari.equals(codiBuscat)){
                return posicioUsuari;
            }
            posicioUsuari++;
        }
        return -1;
    }
    
    public static int trobaUsuari(String[][] llistaUsuarisConnectats, int totalUsuaris, String usuariBuscat){
        int posicioUsuari = 0;
        String nom_usuari = "";
        
        for (int x=0; x < totalUsuaris; x++){
            nom_usuari = llistaUsuarisConnectats[x][1]; //la segona columna sempre contindra el nom dels usuaris
            System.out.println(nom_usuari+" - "+usuariBuscat);
            System.out.println(nom_usuari.equals(usuariBuscat));
            if (nom_usuari.equals(usuariBuscat)){
                return posicioUsuari;
            }
            posicioUsuari++;
        }
        return -1;
    }
    
    public static String[][] eliminarSessio(String[][] llistaUsuarisConnectats, 
            int totalUsuaris, int posicioUsuari){
        mostra(llistaUsuarisConnectats, totalUsuaris);
        
        for(int j = 0; j != llistaUsuarisConnectats[posicioUsuari].length; ++j)    
        {
           llistaUsuarisConnectats[posicioUsuari][j] = llistaUsuarisConnectats[totalUsuaris - 1][j];
           llistaUsuarisConnectats[totalUsuaris - 1][j] = null;
        }
        System.out.println("admin esborrat...");
        mostra(llistaUsuarisConnectats, totalUsuaris);
        return llistaUsuarisConnectats;
    }

    public static void mostra(String[][]matriz, int contador) 
    {
        int i,j;
        for (i = 0; i < contador;i ++) 
        {
            for (j = 0; j < matriz[i].length; j++) 
                System.out.print(matriz[i][j]+"-");
            System.out.print("\n");
        }
        System.out.println("");
    }

    
}
