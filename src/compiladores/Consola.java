/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

/**
 *
 * @author Eduardo
 */
public class Consola {

    public String mensaje = "";
    
    public String getmensaje(){
        String mensajeMostrar= this.mensaje;
           System.out.println(mensajeMostrar);
        return mensajeMostrar;
     }
    public void settMensaje(String mensaje){
        this.mensaje = this.mensaje + mensaje+ "\n";
    }
    public void limpiarConsola(){
    this.mensaje = "";
    }
 
}
