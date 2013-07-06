
package compiladores;

/**
 *
 * @author Eduardo
 */
public class Mostrar {
  
    public Mostrar(){
    
    }
    public void Parentesis(String codigo){
        int x=0, y =0;
        String Sen = "";boolean bandera= false;
        char [] sentencia = codigo.toCharArray();
        for(int w=0;w<sentencia.length;w++){
            String aux =Character.toString(sentencia[x]);
            if(aux.equals("(")){
                x++; bandera= true;
            }else{
                if(bandera && ((!aux.equals("("))&&(aux.equals(")"))))
                    Sen = Sen+ aux;
            }if(aux.equals(")"))
                y++;
        }
        if(x==y)
           System.out.println(Sen);
        else
             System.err.println("Problemas en la sentencia mostrar");
    }
     public void Final(String codigo){
    
    }
             
}
