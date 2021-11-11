
package diccionario;
import com.sun.glass.events.KeyEvent;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SearchWord extends javax.swing.JFrame { 
    //The path of the folder that you want to save the images to
    private static String IMAGE_DESTINATION_FOLDER = 
            "C:\\Users\\pc\\Documents\\UAEMex\\Analisis de lenguajes "
            + "de programacion\\Diccionario\\src\\Images";
    private int contImagen = 2;
    public void accionBuscar(){
        String word = StringWord.getText();
        try {
            DownloadImages(word,contImagen);
            String url = "definicion/" + word;
            //jTextArea1.setText(scrapeTopic(url));
            String scraping = scrapeTopic(url);
            if (scraping.trim().equals("")){
                jTextArea1.setText("No se encontro información acerca de " + word);
                JOptionPane.showMessageDialog(null, 
                        "Verifique que la palabra se encuentre bien escrita");
            } else {
                jTextArea1.setText(scraping);
                jLabel1.setText("Referencia: https://www.wordreference.com/" + url);
            } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Verificar campo de busqueda");
        }
    }
    public String scrapeTopic(String url) {
        //obtener la informacion 
        String finalurl = "https://www.wordreference.com/" + url;
        String html = getUrl(finalurl);
        Document doc = Jsoup.parse(html);
        String contentText = doc.select(".entry").text();
        return contentText;
    }
    public String getUrl(String url) {
        URL urlObj;// = null;
        try {
            urlObj = new URL(url);
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "The url was malformed!");
            return "";
        }
        URLConnection urlCon;// = null;
        BufferedReader in;// = null;
        String outputText = "";
        try {
            urlCon = urlObj.openConnection();
            in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            String line;// = "";
            while ((line = in.readLine()) != null) {
                outputText += line;
            }
            in.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                    "There was an error connecting to the URL" );
            return "";
        }
        return outputText;
    }
    
    public void DownloadImages (String word, int cont){
        String firstChar = word.substring(0,1); //primer letra de la palabra
        String cadena1 = word.replaceFirst(firstChar, firstChar.toUpperCase());
        System.out.println(cadena1);
        String webSiteURL  = "https://es.wikipedia.org/wiki/" + cadena1;
        //String webSiteURL  = "https://pixabay.com/es/images/search/"+word+"/";
        try {
            //Connect to the website and get the html
            Document doc = Jsoup.connect(webSiteURL).get();
            //Get all elements with img tag ,
            Elements img = doc.getElementsByTag("img");
            //Elements img = doc.getElementsByClass("image");
            int c = 0;
            for (Element el : img) {
                c++;
                if (c == cont){
                    //for each element get the srs url
                    String src = el.absUrl("src");
                    //String src = el.absUrl("abs:src");
                    System.out.println("Image Found!");
                    System.out.println("src attribute is : "+src);   
                    String dImage = downloadImage(src);
                    System.out.println(dImage);
                    jLabel2.setIcon(new ImageIcon("C:\\Users\\pc\\Documents\\UAEMex"
                        + "\\Analisis de lenguajes de programacion\\Diccionario"
                        + "\\src\\Images\\"+dImage));
                    String strImageName = src.substring( src.lastIndexOf("/") + 1 );
                    break;
                }
            }
        } catch (IOException ex) {
            System.err.println("There was an error");
            Logger.getLogger(DownloadImages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String downloadImage(String strImageURL){
        //get file name from image path
        String strImageName = 
                strImageURL.substring( strImageURL.lastIndexOf("/") + 1 );
        System.out.println("Saving: " + strImageName + ", from: " + strImageURL);
        try {
            //open the stream from URL
            URL urlImage = new URL(strImageURL);
            InputStream in = urlImage.openStream();  
            byte[] buffer = new byte[4096];
            int n = -1;
            OutputStream os = 
                new FileOutputStream( IMAGE_DESTINATION_FOLDER + "/" + strImageName );
            //write bytes to the output stream
            while ( (n = in.read(buffer)) != -1 ){
                os.write(buffer, 0, n);
            }
            //close the stream
            os.close(); 
            System.out.println("Image saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strImageName;
    }
    
    
    /**
     * Creates new form SearchWord
     */
    public SearchWord() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        StringWord = new javax.swing.JTextField();
        ButtonSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        buscarOtraImagen = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Diccionario");
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setType(java.awt.Window.Type.POPUP);

        jLabel1.setText("Referencia: ");
        jLabel1.setEnabled(false);

        StringWord.setFont(new java.awt.Font("Comic Sans MS", 0, 16)); // NOI18N
        StringWord.setForeground(new java.awt.Color(0, 51, 102));
        StringWord.setToolTipText("Inserte Palabra");
        StringWord.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                StringWordKeyReleased(evt);
            }
        });

        ButtonSearch.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 12)); // NOI18N
        ButtonSearch.setForeground(new java.awt.Color(0, 51, 102));
        ButtonSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/www.png"))); // NOI18N
        ButtonSearch.setText("Buscar");
        ButtonSearch.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        ButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSearchActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setEnabled(false);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/borrador.png"))); // NOI18N
        jButton1.setText("Borrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText(".");

        buscarOtraImagen.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 11)); // NOI18N
        buscarOtraImagen.setForeground(new java.awt.Color(0, 51, 102));
        buscarOtraImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/seo.png"))); // NOI18N
        buscarOtraImagen.setText("Quiero otra imagen");
        buscarOtraImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarOtraImagenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StringWord, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buscarOtraImagen))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StringWord, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarOtraImagen))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSearchActionPerformed
        // TODO add your handling code here:
        accionBuscar();
    }//GEN-LAST:event_ButtonSearchActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jTextArea1.setText("");
        StringWord.setText("");
        jLabel2.setIcon(null);
        jLabel1.setText("Referencia:");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void buscarOtraImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarOtraImagenActionPerformed
        // TODO add your handling code here:
        contImagen++;
        String word = StringWord.getText();
        try {
            DownloadImages(word,contImagen);
        } catch (Exception e) {
            jLabel2.setIcon(new ImageIcon("C:\\Users\\pc\\Documents\\"
                    + "UAEMex\\Analisis de lenguajes de programacion\\"
                    + "Diccionario\\src\\pagina-no-encontrada.png"));
        }
    }//GEN-LAST:event_buscarOtraImagenActionPerformed

    private void StringWordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StringWordKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            accionBuscar();
        }
    }//GEN-LAST:event_StringWordKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SearchWord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchWord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchWord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchWord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SearchWord().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonSearch;
    private javax.swing.JTextField StringWord;
    private javax.swing.JButton buscarOtraImagen;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
