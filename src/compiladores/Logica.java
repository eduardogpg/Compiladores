
package compiladores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;


/**
 *
 * @author Eduardo
 */

public class Logica {
    String codigo;
    StringTokenizer tokens;
 
 LinkedList posibles_Reservadas = new LinkedList();
 LinkedList Variables = new LinkedList();
 LinkedList SoloVariables = new LinkedList();
 LinkedList Numeros = new LinkedList();
 
 private HashMap Valores_Variables = new HashMap();
 
 //Palbras recervadas
    Mostrar print;
    Hacer_Mientras hacer;

   
 
   private void Lexico(String codigo){
        codigo = codigo.replaceAll("[\n\r\t]","");
        // Agregar espacios a simbolos necesarios
        codigo = codigo.replaceAll(">=", " >= ");
        codigo = codigo.replaceAll("<=", " <= ");
        codigo = codigo.replaceAll(">", " > ");
        codigo = codigo.replaceAll("<", " < ");
        codigo = codigo.replaceAll("\\*", " * ");
        codigo = codigo.replaceAll("/", " / ");
        codigo = codigo.replaceAll("\\+", " + ");
        codigo = codigo.replaceAll("-", " - ");
        codigo = codigo.replaceAll(" ", "♫");
        String[] lineas = codigo.split("(:3| )");
        int i=0; 
        int hacer= this.Buscar("hacer", codigo);
        while(i<lineas.length) {
            if(esVariable(lineas[i])) {
                if(correctaVariable(lineas[i])) {
                    if(this.saberVariable(lineas[i])){
                         this.Variables.add(lineas[i]); // Guardamos la Variable en una lista de Variables
                         this.AsignarValor((lineas[i]));//Agignamos el valor a la variable
                    }else 
                        System.out.println("Variable ya Inicializada ");
                } else {
                   System.err.println("Error 1: Variable con nombre no válido, use solo caracteres alfanumericos.\n"); 
                }
                } else if(hacer>0) {
                   //System.out.println("Palabra recervada >w<");
                   posibles_Reservadas.add(lineas[i]);
                   
                } else if (esNumero(lineas[i])) {
                     System.out.println(lineas[i]);
                } else if (esOperadorRelacional(lineas[i])) {
                     System.out.println(lineas[i]);
                } else if (esOperadorAritmetico(lineas[i])) {
                   System.out.println(lineas[i]);
                } else {
                   // System.err.println("Error en la linea : " + lineas[i]);
                }
            i++;
        } 
        
    }
    
   public void Archivo(String codigo){
        this.hacer = new Hacer_Mientras();
        File f = new File( codigo );
        
        int contador=0; boolean Bandera= false;
        int x=0,y=0, hacer = 0, mientras = 0;
        int mostrar=0;
        BufferedReader entrada;
        try {
           
        entrada = new BufferedReader( new FileReader( f ) );
        String linea;
            while(entrada.ready()){
                contador++;
                linea = entrada.readLine();
                this.Lexico(linea);
                
                x= this.Buscar("hacer", linea);
                mostrar = this.Buscar("mostrar", linea);
                if(mostrar>0){
                    Mostrar(linea);
                }
                if(x>0){
                    if(mientras==0){
                    hacer = contador;
                    Bandera =this.hacer.buscarLlave(linea);
                 }}
                if(Bandera){
                    if(hacer>0){
                    y= this.Buscar("mientras", linea);
                    if(y>0){
                        mientras= contador;
                        Bandera=this.hacer.buscarllaveCerrar(linea);
                    }}
                    }
                }//Fin del while
                if ((Bandera) && (mientras > hacer)){
                    String condicion = this.hacer.BienCondicional();
                    if(!condicion.equals("Error")){
                        if(!this.saberVariable(condicion)){
                             System.out.println("Sentencia correcta");
                        }else{ System.err.println("Variable no inizializada");}
                    }else{
                        System.err.println("Problemas con la sentencia");}
                }else{
                    System.err.println("Problemas con la sentencia");
                }
            
        }catch (IOException e) {}
    }
                             
   public void Mostrar(String codigo){
       int x=0, y =0;
        String Sen = "";boolean bandera= false;
        char [] sentencia = codigo.toCharArray();
        int tres = this.Buscar(":3", codigo+".");
        if(tres==1){
            for(int w=0;w<sentencia.length;w++){
                String aux =Character.toString(sentencia[w]);
                if(aux.equals("(")){
                    x++; bandera= true;
                }else{
                    if(bandera){
                        if(!aux.equals(")")) if(!aux.equals(":")) if(!aux.equals("3"))
                        Sen = Sen+ aux; 
                    }
                }if(aux.equals(")"))
                    y++;
            }
         }else
            System.err.println("Falta :3 en la linea "+ codigo);
        if(x==y && (x!=0 && y!=0)){
            if(!this.saberVariable(Sen))
                Sen = (String)this.Valores_Variables.get(Sen);
            System.out.println(Sen);
        }else
             System.err.println("Problemas en la sentencia mostrar Linea : "+codigo);
       }
      
   private void AsignarValor(String Variable){
    boolean bandera= false;int pos=0;
    String nombre = "", valor = "";
    int interacciones = this.Buscar("=", Variable);
    if(interacciones== 1){
        char [] buscador= Variable.toCharArray();
                  for(int y=0; y<buscador.length;y++){
                            String aux =Character.toString(buscador[y]);    
                            if(aux.equals("=")){
                                bandera= true;
                            }else{
                                if(bandera== false){
                                   nombre = nombre+aux; 
                                 }else{
                                    if(aux.equals("♫") )
                                        valor= valor+ " ";
                                    else
                                        valor = valor+aux;
                                }
                            }
                        }//Fin del  for
                  if(Valores_Variables.containsKey(nombre)){ //La variable ya se encuentra ?
                            if(Valores_Variables.get(nombre) == valor) //Comparamos si su valor es nuevo
                                System.out.println("Variable ya declarada"+ nombre);
                            else{
                                this.Valores_Variables.put(nombre, valor); System.out.println("Nuevo valor a: "+ nombre);
                            } 
                        }else{ //Si no se encuentra se guarda
                            SoloVariables.add(nombre);
                        } //Reiniciamos valores
                        Valores_Variables.put(nombre, valor);
                        nombre=""; valor=""; bandera= false;
    }else{
        System.err.println("Problemas con la asignacion de valores");
    }
  }
    
   private boolean saberVariable(String variable){
        if(!Valores_Variables.containsKey(variable))
            return true;
        else
            return false;
        
    }
    
   private int Buscar(String Palabra ,String Parrafo){ 
   	if (!(Parrafo.compareTo("")==0) && (!(Palabra.compareTo("")==0))){
			int Ocurrencias=0;
			for(int i=0;i<(Parrafo.length()-Palabra.length());i++){
				if(Parrafo.substring(i,(i+Palabra.length())).compareTo(Palabra)==0){
					Ocurrencias++;
					}
			}
			return Ocurrencias;
		}
		return 0;
    }
    
   private boolean esVariable(String texto) {
        char resp[] = texto.toCharArray();
        if(resp[0]=='_'){
            return true;
        }
        return false;
    }
    
   private boolean correctaVariable(String texto) {
        Pattern p = Pattern.compile("^_[A-Za-z0-9 =?]"); //Cualquier caracter excepto _ y = una cero vecez
        Matcher m = p.matcher(texto);
        if (m.find()) {
            return true;
        }
        return false;

    }
    
    private boolean esNumero(String texto) {
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(texto);
        if (m.find()) {
            return true;
        }
        return false;

    }
    
    private boolean esOperadorRelacional(String texto) {
        if(texto.matches("(>=|<=|>|<|==|<>)")) {
            return true;
        } 
        return false;
    }
    
    private boolean esOperadorAritmetico(String texto) {
        if(texto.matches("(\\*|/|\\+|-)")) {
            return true;
        } 
        return false;
    }
}
