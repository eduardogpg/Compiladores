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
    
private String condicion = "";
  public Hacer_Mientras(){}
    


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
     this.condicion = condicion;
     return bandera;
 }
      
 public String chekCondicional(){

    boolean bandera= false; String condi = "";
    boolean CON = false; int contador=0;
    char [] con = this.condicion.toCharArray();
    for(int x= 0; x<con.length;x++){
        String aux =Character.toString(con[x]);
            if(aux.equals("("))
                bandera= true;
            if(bandera== true){
               if(aux.equals("(") || aux.equals(")") || aux.equals(":") || aux.equals("3"))
                   contador++; //Aun no se para que sirve pero sirve.
               else
                   condi= condi+ aux; //Guarda la condicional 
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