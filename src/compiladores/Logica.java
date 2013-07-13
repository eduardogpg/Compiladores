
package compiladores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;



/**
 *
 * @author Eduardo
 */

public class Logica {

 
 LinkedList posibles_Reservadas = new LinkedList();
 LinkedList Variables = new LinkedList();
 LinkedList SoloVariables = new LinkedList();
 LinkedList Numeros = new LinkedList();
 
 private HashMap Valores_Variables = new HashMap();
 
 int posHacer =0, posMientras=0;
 boolean hacerMientras=false;
 
 //Palbras recervadas
    Mostrar mostrar = new Mostrar();
    Hacer_Mientras hacer_mientras = new Hacer_Mientras();

 
    ShuntingYard shuntingYard;
 
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

        while(i<lineas.length) {
            if(esVariable(lineas[i])) {
                if(correctaVariable(lineas[i])) {
                    if(!this.saberVariable(lineas[i])){
                         this.Variables.add(lineas[i]); // Guardamos la Variable en una lista de Variables
                         this.AsignarValor((lineas[i]));//Agignamos el valor a la variable
                    }else 
                        System.out.println("Variable ya Inicializada ");
                } else {
                   System.err.println("Error 1: Variable con nombre no válido, use solo caracteres alfanumericos.\n"); 
                }
                
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
    
   public void Principal(String linea, int contador){
      if(this.Buscar("mostrar", linea)>0)
          this.Mostrar(linea);    
      else{
          if(this.Buscar("hacer", linea)==1){
                posHacer= contador;
                hacerMientras =this.hacer_mientras.buscarLlave(linea); //Se busca la primera llave
          }else if(this.Buscar("mientras", linea)==1){
                 posMientras = contador;
                if ((posHacer< posMientras) && hacerMientras== true){
                    hacerMientras= this.hacer_mientras.buscarllaveCerrar(linea);
                    if(hacerMientras) {// Se encontro la llave de cierre
                        String mensaje =this.hacer_mientras.chekCondicional();
                        if(!mensaje.equals("Error")){
                            if(this.saberVariable(mensaje)){
                                 System.out.println("Sentencia correcta");
                            }else{ System.err.println("Variable no inizializada");}
                            }else{System.err.println("Problemas con la sentencia");}
                        }else{System.err.println("Problemas con la sentencia (posible errror llaves)");}
                    
                }else{System.err.println("Problemas con la sentencia (posible errror llaves)");}
          }else {
             this.Lexico(linea);
          }
      }//Fin de la comparación en caso de que sea Mostrar
          
 
   
   }
 
   public void Mostrar(String codigo){
    if(this.Buscar(":3", codigo+".")==1){
        String mensaje = this.mostrar.correctarEstructura(codigo);
        if(!mensaje.equals("N_aDMiTibLe")){
            if(this.saberVariable(mensaje))
                mensaje = (String)this.Valores_Variables.get(mensaje);
            //this.mostrar.imprimir(mensaje);
        this.MostrarConsola(mensaje);
        }else
            System.err.println("Problemas con la estructura de Mostrar");
       }else{
        System.err.println("Problemas con la estructura de Mostrar(Problema quizas :3 )");
    }}
     
   public void MostrarConsola(String mensaje){
        //this.consola.MensajeConsola(mensaje);
       System.out.println(mensaje);
   }
   
   private void AnalizadorSantactico(String nombre,String valor){
       this.shuntingYard = new ShuntingYard(valor);
       valor = this.shuntingYard.aRPN();
       Arbol arbol = new Arbol(valor);
       int nuevoValor  = arbol.postOrdenInverso(arbol.root);
       Valores_Variables.put(nombre, nuevoValor);
       System.out.println(nuevoValor);
       
   }
   
   private void AsignarValor(String Variable){
    boolean bandera= false, ShuntingYarda= false;
    String nombre = "", valor = "";
   
    if(this.Buscar("(", Variable)>0) //Buscando si estamos asiganado un valor a la variable
       ShuntingYarda= true;
                
    if(this.Buscar("=", Variable)== 1){
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
                  if(ShuntingYarda){
                      //this.AnalizadorSantactico(nombre,valor);
                  }
                  else{//La variable ya tiene un nuevo valor asiganado ya no usaremos el ShutingYard
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
                  }
    }else{
        System.err.println("Problemas con la asignacion de valores");
    }
  }
    
   private boolean saberVariable(String variable){
        if(Valores_Variables.containsKey(variable))
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
