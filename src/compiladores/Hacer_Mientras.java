/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Eduardo
 */

public class Hacer_Mientras {
    
private String mientras = "";
  public Hacer_Mientras(){}
    
 public void BuscarSentencias(int x, int y, String cadena){
 
File f = new File( cadena );
        BufferedReader entrada; int contador=0; String Sentencias= "";
        try {
        entrada = new BufferedReader( new FileReader( f ) );
        String linea;
            while(entrada.ready()){
                contador++;
             linea = entrada.readLine();
            if(contador > x){
                    if(contador <= y){
                        Sentencias = Sentencias + linea;
                    }
                }
            }
     
            System.out.println("Contenido : "+ Sentencias);
        }catch (IOException e) {
            e.printStackTrace();
        }
                 
 
 }

 public boolean buscarLlave(String busqueda){
     boolean bandera= false;
     char [] sentencias = busqueda.toCharArray();
     for(int x=0; x<sentencias.length;x++){
         String aux =Character.toString(sentencias[x]);
         if(aux.equals("{")){
             bandera= true;
         }
     }
     return bandera;
 }
 
 public boolean buscarllaveCerrar(String busqueda){
 boolean bandera= false;
 String condicion=""; 
     char [] sentencias = busqueda.toCharArray();
     for(int x=0; x<sentencias.length;x++){
         String aux =Character.toString(sentencias[x]);
         if(aux.equals("}")){
             bandera= true;
         }
         if(bandera== true){
              if(!aux.equals("}"))
                condicion = condicion + aux;
         }
     }
     this.mientras = condicion;
     return bandera;
 }
      
 public String BienCondicional(){

    boolean bandera= false; String condi = "";
    boolean CON = false; int contador=0;
    char [] con = this.mientras.toCharArray();
    for(int x= 0; x<con.length;x++){
        String aux =Character.toString(con[x]);
    
            if(aux.equals("("))
                bandera= true;
            if(bandera== true){
               if(aux.equals("(") || aux.equals(")") || aux.equals(":") || aux.equals("3"))
                   contador++;
               else
                   condi= condi+ aux;
            }
        }//Fin del For
    CON = this.condicionCorrecta(condi);
        if(CON== true){
            return condi;
        }else{
           return "Error";
        }
    
 }
 private boolean condicionCorrecta(String condicion){
 boolean bandera= false;
 if(this.esVariable(condicion)){
     bandera= true;
 }
 return bandera;
 
 }
 private boolean esVariable(String texto) {
        char resp[] = texto.toCharArray();
        if(resp[0]=='_'){
            return true;
        }
        return false;
    }

}