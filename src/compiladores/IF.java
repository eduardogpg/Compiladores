
package compiladores;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Eduardo
 */
public class IF {
    private boolean bandera= false; 
    private boolean error = false;
    
    LinkedList SoloVariables = new LinkedList();
    String primeraCondicion= "", segundaCondicion="", comparador= "";
    
   Consola c = new Consola();
    
    String [] numeros = {"0","1","2","3","4","5","6","7","8","9"};
     private HashMap Valores_Variables = new HashMap();
     
    public boolean lexico(String cadena){
         if(cadena.matches("(\\*|/|\\+|-)")) {
            return true;
        } 
        return false;
    }
        
    public boolean getBandera(){
    return this.bandera;
    }
    
    public boolean getError(){
    return error;
    }
    public boolean init(String linea){
        return this.BuscarCondicion(linea);
    }
    public void LimpiarHashMap(){
        Valores_Variables.clear();
    }
    
    private boolean BuscarCondicion(String linea){
        char [] sentencia = linea.toCharArray();
        String condicion = ""; 
        int pa=0, pc=0; boolean b=false;
        for(int x=0; x<sentencia.length;x++){
            String aux =Character.toString(sentencia[x]);
            if((aux.equals("(")) || (aux.equals(")")) || (aux.equals("s")) || (aux.equals("i")) || (aux.equals("{")) ||(aux.equals("}"))){
                if (aux.equals("("))
                    pa++;
                if (aux.equals(")"))
                    pc++;
            } else
                condicion = condicion+ aux; //separamos toda para que quede solo lo que hay dentro del parentesis
        }
        if (pa==pc) //Comparamos si el numero de parentesis son iguales
            bandera=this.CheckCondicional(condicion); //Nos aseguraremos que las condicionesle esten bien
        else 
            this.bandera= false;
        b= this.bandera;
        return b; //Regresamos el valor dependiendo de las condiciones
    }
   
    public void variables(LinkedList var){
      this.SoloVariables = var;
    }
    
    private boolean CheckCondicional(String condicion){
       boolean bandera= true;
        segundaCondicion=this.segundaCondicion(condicion, true);
        if(primeraCondicion.equals("") && segundaCondicion.equals("")  && (comparador.equals("")))
            bandera=false;
        return bandera;
    }
    
    public void tablavalores(HashMap mapa){ //Metodo que me sirve para saber si la condicion contiene variables
        Valores_Variables = mapa;
        if(this.Valores_Variables.containsKey(this.primeraCondicion))
            this.primeraCondicion = (String)this.Valores_Variables.get(this.primeraCondicion);
        if(this.Valores_Variables.containsKey(this.segundaCondicion))
            this.segundaCondicion = (String) this.Valores_Variables.get(this.segundaCondicion);
        
        this.verdaderoIf();
    }
    private void verdaderoIf(){
      try{
        int numero=Integer.parseInt(this.primeraCondicion);
        int dos = Integer.parseInt(this.segundaCondicion);
        if(this.comparador.equals(">")){
            if(numero>dos)
                bandera= true;
             else
                bandera= false;
        }if(this.comparador.equals("<")){
              if(numero<dos)
                bandera= true;
             else
                bandera= false;
         }if(this.comparador.equals("=")){
            if(numero==dos)
                bandera= true;
             else
                bandera= false;
         }    
      }catch(NumberFormatException ex){
                c.settMensaje("Problemas con el fin");
                error= true;
    	}
    }
    
    private String segundaCondicion(String condicional, boolean numero){
        char [] sentencia = condicional.toCharArray();
        String condicionString = "";
        String primeracondicion = "";
        boolean bandera= false;
        for(int x=0; x<sentencia.length;x++){
            String aux =Character.toString(sentencia[x]);
            if((aux.equals(">")) || (aux.equals("<")) || (aux.equals("="))){
                bandera=true;
                this.comparador = aux; //En este mismo proceso obtenemos el separador 
            } else{
                if(bandera)
                    condicionString = condicionString+ aux;
                else
                    primeracondicion= primeracondicion + aux;
            }
          
        }
        if(numero)
            this.primeraCondicion= primeracondicion;
        return condicionString;
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
}
