
package compiladores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Compilador extends javax.swing.JFrame {
   
   String PATH, contenido;
   String path="";
   Logica Logico = new Logica();
   boolean baderaOpen = false;
    public Compilador() {
        initComponents();
    }
      @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Franklin Gothic Book", 0, 14)); // NOI18N
        jLabel1.setText("Compilador POSH");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Compilar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jButton2.setText("Abrir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                        .addComponent(jScrollPane2))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int contador = 0;
        try{ //Boton para compilar el Archivo
       
           // Logico.Principal(PATH); //Metodo principal de la clase Logica pasamos la ruta para que lo bsuque
           String allText = this.jTextArea1.getText() ;
           StringTokenizer st = new StringTokenizer(allText,"\n") ;
           while (st.hasMoreTokens()) {
               contador++;
                String line = st.nextToken();
                this.Logico.Principal(line, contador);
           }
        }catch(Exception  e){
            this.Consola("Error al Compilar el Archivo");
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    public void Consola(String mensaje){
        this.jTextArea2.append(mensaje+ "\n");
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       try {//Boton para abrir el archivo
          abrir();
          Logico = new Logica();
        }catch (IOException ex) {
           System.out.println("Problemas con :"+ex);
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed
  
    
    private void abrir() throws IOException {

        JFileChooser JFC = new JFileChooser();
        JFC.setFileFilter(new FileNameExtensionFilter("todos los archivos *.txt", "txt","TXT"));
        int abrir = JFC.showDialog(null, "Abrir");
        if (abrir == JFileChooser.APPROVE_OPTION) {
            FileReader FR = null;
            BufferedReader BR = null;
            this.baderaOpen = true;
         try {
    
                File archivo = JFC.getSelectedFile();//abre un archivo .lengf
                
                PATH = JFC.getSelectedFile().getAbsolutePath();
                System.out.println(this.PATH);
                if(PATH.endsWith(".txt")||PATH.endsWith(".TXT")){
                    
                    FR = new FileReader(archivo);
                    BR = new BufferedReader(FR);
                    String linea;//variable para leer linea por linea el archivo de entrada
                    if(path.compareTo(archivo.getAbsolutePath())==0){
                        JOptionPane.showMessageDialog(this, "Archivo Abierto","Oops! Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        path = archivo.getAbsolutePath();
                        jTextArea1.setText(null);
                        while((linea=BR.readLine())!=null){
                           this.setTitle(PATH);
                            jTextArea1.append(linea+"\n");
                        }
                    }
                }else{//En caso no terminara con .txt
                    JOptionPane.showMessageDialog(this, "Archivo no soportado","Oops! Error", JOptionPane.ERROR_MESSAGE);
                    abrir();
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if(null!= FR){
                        FR.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                   
                }
            }
        }else{
            System.out.println("no de abrio nada");
        }
    }
    public static void main(String args[]) {
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Compilador().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
