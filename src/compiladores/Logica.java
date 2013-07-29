
package compiladores;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;

/**
 *
 * @author Eduardo
 */

public class Logica {
/*
  Problemas a resolver , mostrar debe de estar al principio
 * mientras debe validar :3 al final
 * 
 */
 
 LinkedList posibles_Reservadas = new LinkedList();
 LinkedList Variables = new LinkedList();
 LinkedList SoloVariables = new LinkedList();
 LinkedList Numeros = new LinkedList();
 
 private HashMap Valores_Variables = new HashMap();
 
    int posHacer =0, posMientras=0;
    boolean hacerMientras=false;
    String mensajeConsola = "";
  
    //Palbras recervadas
    Mostrar mostrar = new Mostrar();
    Hacer_Mientras hacer_mientras = new Hacer_Mientras();
    IF si = new IF();
    Consola c = new Consola();
    
    ShuntingYard shuntingYard = new ShuntingYard();
    
   public boolean main(String cadena, boolean llave){
      boolean bandera= false;
      if(llave == false){
            if(this.Buscar("Comienzo(){", cadena+".")==1)
                bandera= true;
      }else{
          if(this.Buscar("}", cadena+".")==1)
                bandera= true;
      }
          
      return bandera;
   }
    
   private void Lexico(String codigo, int linea){
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
        String[] lineas = codigo.split(":3");
        int i=0; 

        while(i<lineas.length) {

            if(esVariable(lineas[i])) {
                if(correctaVariable(lineas[i])) {
                    if(!this.saberVariable(lineas[i])){
                         this.Variables.add(lineas[i]); // Guardamos la Variable en una lista de Variables
                         this.AsignarValor((lineas[i]));//Agignamos el valor a la variable
                    }
                } else {
                   this.mensajeConsola= this.mensajeConsola +("Error 1: Variable con nombre no válido, use solo caracteres alfanumericos.\n"); 
                }
                
           } else if (esNumero(lineas[i])) {
                this.mensajeConsola= this.mensajeConsola +(lineas[i]+"\n");
          } else if (esOperadorRelacional(lineas[i])) {
                this.mensajeConsola= this.mensajeConsola +(lineas[i]+"\n");
          } else if (esOperadorAritmetico(lineas[i])) {
                this.mensajeConsola= this.mensajeConsola +(lineas[i]+"\n");
           } else {
              if((linea==1) || lineas[i].equals("}")){//Problemas con las negaciones
               }else{
                 this.mensajeConsola= this.mensajeConsola +("Error en la linea "+linea+"\n");
                  System.err.println(lineas[i]);}
              }
            i++;
        } 
        
    }
    
   public void Principal(String linea, int contador){
       linea = linea.replaceAll("[\n\r\t]","");
      if(this.Buscar("mostrar", linea)>0)
          this.Mostrar(linea);    
      else{
          if(this.Buscar("hacer", linea)==1){
                posHacer= contador;
                hacerMientras =this.hacer_mientras.buscarLlave(linea); //Se busca la primera llave
          }else if(this.Buscar("mientras", linea)==1){
                 posMientras = contador;
                if((posHacer< posMientras) && hacerMientras== true){
                    hacerMientras= this.hacer_mientras.buscarllaveCerrar(linea);
                    if(hacerMientras) {// Se encontro la llave de cierre
                        String mensaje =this.hacer_mientras.chekCondicional();
                        if(!mensaje.equals("Error")){
                            if(this.saberVariable(mensaje)){//Aqui debo cambiarle para conocer las vaiables que existe
                                 this.mensajeConsola= this.mensajeConsola +("Sentencia hacer mientas correcta\n");
                            }else{ this.mensajeConsola= this.mensajeConsola +("Variable no inizializada\n");}
                            }else{this.mensajeConsola= this.mensajeConsola +("Problemas con la sentencia\n");}
                        }else{this.mensajeConsola= this.mensajeConsola +("Problemas con la sentencia (posible errror llaves)\n");}
                    
                }else{this.mensajeConsola= this.mensajeConsola +("Problemas con la sentencia (posible errror llaves)\n");}
          }else if((this.Buscar("si",linea)==1)){
              if(this.confirmarIf(linea))
                 this.IF(linea);
          }else {
             this.Lexico(linea, contador);
          }
      }//Fin de la comparación en caso de que sea Mostrar

   }
   
   private boolean IF(String linea){
       boolean bandera= false;
       if(this.Buscar("{", linea+".")==1){ //Me ayuda a saber que las llaves
           if(this.Buscar("{", linea)==0){ // de apertura  deben de estar al final de la linea
                si.variables(this.SoloVariables); //Paso las variables a una lista que se encuentra en la clas IF
                if(si.init(linea)){
                    si.tablavalores(this.Valores_Variables);
                    if(!si.getError()) {
                         if(si.getBandera()){
                             bandera= true;
                              this.mensajeConsola= this.mensajeConsola +("La condicional "+bandera+"\n");
                         }else  this.mensajeConsola= this.mensajeConsola +("La condicional "+bandera+"\n");

                    }else  this.mensajeConsola= this.mensajeConsola +("Problemas con las variables  del si\n");
                }else
                     this.mensajeConsola= this.mensajeConsola +("Problemas con la sintaxis del si\n");
           }else
                this.mensajeConsola= this.mensajeConsola +("Problemas con la sintaxis del si\n");
       }else 
            this.mensajeConsola= this.mensajeConsola +("Problemas con la estructura de el si\n");
    
       return bandera;
   }
   
   public void Mostrar(String codigo){
    if(!this.esVariable(codigo)){ //Preguntamos si es una variable, puede darse el caso
    if(this.Buscar(":3", codigo+".")==1){
        String mensaje = this.mostrar.correctarEstructura(codigo);
        System.out.println("El mensaje que se imprimira es "+ mensaje);
        if(!mensaje.equals("N_aDMiTibLe")){
            if(this.saberVariable(mensaje))
                mensaje = (String)this.Valores_Variables.get(mensaje);
            this.mensajeConsola =  this.mensajeConsola+ mensaje+"\n";
        }else
              this.mensajeConsola= this.mensajeConsola +("Problemas con la estructura de Mostrar\n");
       }else{
          this.mensajeConsola= this.mensajeConsola +("Problemas con la estructura de Mostrar(Problema quizas :3 )\n");
    }
   }else
         this.mensajeConsola= this.mensajeConsola +("La vaiable no puede llamarse con ese nombre\n");
   }
     
   public String  MostrarConsola(){
       c.settMensaje(this.mensajeConsola);
       String mensajeconso =c.getmensaje();
       return mensajeconso;
   }
   
   public void limpiarConsola(){
       mensajeConsola = "";
       this.Valores_Variables.clear();
       c.limpiarConsola();
       si.LimpiarHashMap();
   }
   
   private void AnalizadorSantactico(String nombre,String valor){
       this.shuntingYard.Init(this.shuntingYard.SoloLexico(valor)); //Separamos :3 y le asignamos el valor
       int nuevoValor =this.shuntingYard.Arbol(this.shuntingYard.aRPN()); //Obtenemos el nuevo valor
       String enteroString = Integer.toString(nuevoValor); // 
       this.Valores_Variables.put(nombre, enteroString);
       SoloVariables.add(nombre);
   }
   
   private void AsignarValor(String Variable){
    boolean bandera= false, ShuntingYarda= false;
    String nombre = "", valor = "";
    if((this.Buscar("-", Variable)>0)|| (this.Buscar("+", Variable)>0)
            ||(this.Buscar("/", Variable)>0) || (this.Buscar("*", Variable)>0)) //Buscando si estamos asiganado un valor a la variable
                ShuntingYarda= true;   
    if(this.Buscar("=", Variable)== 1){ //Ciclo for para poder separara el nombre  de su valor
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
                      this.AnalizadorSantactico(nombre,valor);
                  }
                  else{//La variable ya tiene un nuevo valor asiganado ya no usaremos el ShutingYard
                         Valores_Variables.put(nombre, valor);
                         this.SoloVariables.add(nombre);
                         nombre=""; valor=""; bandera= false;
                       } //Reiniciamos valores
                       
                  
    }else{
        this.mensajeConsola= this.mensajeConsola +("Problemas con la asignacion de valores");
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
        Pattern p = Pattern.compile("^_[A-Za-z0-9 =?]"); //Cualquier caracter excepto _ y = una o cero vecez
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
   
   private boolean confirmarIf(String si){
   boolean bandera= false;
    char resp[] = si.toCharArray();
        if((resp[0]=='s') && (resp[1]=='i')){
            return true;
        }
   return bandera;
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
