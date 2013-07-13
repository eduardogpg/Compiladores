
package compiladores;

/**
 *
 * @author Eduardo
 */
public class Mostrar {
  private String Sentencia="";
    public Mostrar(){
       
    }
    public String correctarEstructura(String codigo){
        int x=0, y =0; //Variables para el control de los parentesis
        String Sen = "";int contador=0;
        boolean bandera= false;
        char [] sentencia = codigo.toCharArray();
        for(int w=0;w<sentencia.length;w++){
            String aux =Character.toString(sentencia[w]);
            
            if(aux.equals("(")){
                x++; bandera= true;
            }else{
                if(bandera){
                    if( (aux.equals("("))||(aux.equals(")")) ||(aux.equals(":")) ||(aux.equals("3")))
                        contador++;
                    else
                        Sen = Sen+ aux;
                }
                        
            }if(aux.equals(")"))
                y++;
        }//Fin del for
        if(x==y){
            return Sen;
        }else
            return Sen="N_aDMiTibLe";
    }
    public void imprimir(String sentencia){
        System.out.println(sentencia);
    }
    public void comillas(String sentencia){
    
    }     
}
