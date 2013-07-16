/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 *
 * @author Eduardo
 */
public class ShuntingYard {
 String s = "";
    ArrayList lista;
    String [] numeros = {"0","1","2","3","4","5","6","7","8","9"};
    String [] o1 = {"+","-"};
    String [] o2 = {"*","/"};
    String [] seperadores = {"(",")"};
    ArrayList salida = new ArrayList();
    Stack operadores = new Stack();
    
    
    ShuntingYard(){
    }
    public void Init(String s){
        lista = new ArrayList();
        lista = this.convertirALista(s);
    }
    
    public String SoloLexico(String allText){
    StringTokenizer st = new StringTokenizer(allText,":3") ;
    String line="";
    while (st.hasMoreTokens()) {
             line = st.nextToken();
           }
    return line;
    }
    
    public String aRPN(){
        if(lista == null){
            return "";
        }
        for(Object objeto : lista){
            String s = objeto.toString();
            if(esNumero(s)){
                salida.add(s);
            }
            else if(esOperador2(s)){
                if(operadores.empty()) operadores.add(s);
                else{
                    while(esOperador2(operadores.peek().toString())){
                        String a = (String) operadores.pop();
                        salida.add(a);
                        if(operadores.empty()) break;
                    }
                    operadores.add(s);
                }
            }
            else if(esOperador1(s)){
                
                if(operadores.empty()) operadores.add(s);
                else{
                    while(esOperador2(operadores.peek().toString())){
                        String a = (String) operadores.pop();
                        salida.add(a);
                        if(operadores.empty()) break;
                    }
                    
                    operadores.add(s);
                }
            }
            else if(esSeperador(s)){
                if(s.equals("(")){
                    operadores.add(s);
                }
                else if(s.equals(")")){
                    boolean b = true;
                    while(b){
                        if(operadores.empty()){
                            System.err.println("Error de sintáxis");
                            break;
                        }
                        String ay = (String) operadores.pop();
                        if(ay.equals("(")){
                            b = false;
                        }
                        else{
                            salida.add(ay);
                        }
                    }
                }
            }
        }
        while(!operadores.empty()){
            String ay = (String) operadores.pop();
            if(ay.equals("(") || ay.equals(")")) {
                System.err.println("Error de sintáxis");
                return null;
            }
            salida.add(ay);
        }
        //Collections.reverse(salida);
        
       
        return arrayString(salida);
        
    }
    private String pasoDos(){
        String r = "";
        
        return r;
    }
    private String arrayString(ArrayList al){
        String r = "";
        for(Object o: al){
            String ay = (String) o;
            r += ay;
        }
        return r;
    }
    private boolean esNumero(String s){
        boolean b = false;
        for(String i : numeros){
            if(i.equals(s)) b = true;
        }
        return b;
    }
    private boolean esOperador1(String s){
        boolean b = false;
        for(String i : o1){
            if(i.equals(s)) b = true;
        }
        return b;
    }
    private boolean esOperador2(String s){
        boolean b = false;
        for(String i : o2){
            if(i.equals(s)) b = true;
        }
        return b;
    }
    private boolean esSeperador(String s){
        boolean b = false;
        for(String i : seperadores){
            if(i.equals(s)) b = true;
        }
        return b;
    }
    private ArrayList convertirALista(String s){
        ArrayList r = new ArrayList();
        String a;
        //char [] c = s.toCharArray();
        for(Character c : s.toCharArray()){
             a = c.toString();
            r.add(a);
        }
        return r;
    }
    public int Arbol(String valor){
       Arbol arbol = new Arbol(valor);
       int nuevoValor  = arbol.postOrdenInverso(arbol.root);
       return nuevoValor;
    }

}
