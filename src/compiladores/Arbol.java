/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

import java.util.Stack;

/**
 *
 * @author Eduardo
 */
public class Arbol {
     String s = "";
    String r = "";
    Nodo root = null;
    Stack tokens;
    String [] numeros = {"0","1","2","3","4","5","6","7","8","9"};
    String [] operadores = {"-","+","*","/"};
    
    Arbol(String s){
        tokens = this.convertirALista(s);
        this.s = s;
        buildTree(root,tokens);
        
    }
    public void buildTree(Nodo r,Stack t){
        if(t.empty()) return;
        String ay = t.pop().toString();       
        
        Nodo actual = new Nodo(ay);
        if(this.root == null) {
            this.root = actual;
            buildTree(actual,t);
        }
        else{
            if(this.esOperador(r.v)){
                r.i = actual;
                actual.p = r;
                buildTree(actual,t);
            }
            else if(this.esNumero(r.v)){
                if(r.p.d == null){
                    r.p.d = actual;
                    actual.p = r.p;
                    buildTree(actual,t);
                }
                else{
                    Nodo help = r.p;
                    while(help.d != null && help != null){
                        if(help.p != null) help = help.p;
                        else break;
                    }
                    
                    help.d = actual;
                    actual.p = help;
                    buildTree(actual,t);
                }
            }
            
        }    
        
    }
    public int postOrdenInverso(Nodo n){
        if(n == null) return 0;
        int vDer = postOrdenInverso(n.d);
        int vIzq = postOrdenInverso(n.i);
        if(this.esOperador(n.v)){
            switch(n.v){
                case "*":
                    return vDer * vIzq;
               case "/":
                    return vDer / vIzq;
               case "+":
                    return vDer + vIzq;
               case "-":
                    return vDer - vIzq;
               default:
                   return 0;                    
            }
        }
        else{
            return Integer.parseInt(n.v);
        }
    }
    public void ArbolString(Nodo n){
        if(n == null) return;
        ArbolString(n.d);
        ArbolString(n.i);
        r += n.v;
    }
    private Stack convertirALista(String s){
        Stack r = new Stack();
        String a;
        //char [] c = s.toCharArray();
        for(Character c : s.toCharArray()){
             a = c.toString();
            r.add(a);
        }
        return r;
    }
    private boolean esOperador(String s){
        boolean b = false;
        for(String i : operadores){
            if(i.equals(s)) b = true;
        }
        return b;
        
    }
    private boolean esNumero(String s){
        boolean b = false;
        for(String i : numeros){
            if(i.equals(s)) b = true;
        }
        return b;
        
    }
}

