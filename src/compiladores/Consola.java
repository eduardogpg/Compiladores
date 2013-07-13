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
  Compilador compilador = new Compilador();
  
  public void MensajeConsola(String mensaje){
  compilador.Consola(mensaje);
  }
}
