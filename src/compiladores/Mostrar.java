package compiladores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Eduardo
 */
public class Mostrar {
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
                    if((x==y) && (x>0)){
                        return Sen;
                    }else
                        return Sen="N_aDMiTibLe";
    }
    public boolean Lexico(String texto){
    Pattern p = Pattern.compile("^mostrar("); 
        Matcher m = p.matcher(texto);
        if (m.find()) {
            return true;
        }else
            return true;

    }
        public boolean LexicoFin(String texto){
        Pattern p = Pattern.compile("):3$"); //Cualquier caracter excepto _ y = una o cero vecez
            Matcher m = p.matcher(texto);
            if (m.find()) {
                return true;
            }else
                return false;

    
        }

}
