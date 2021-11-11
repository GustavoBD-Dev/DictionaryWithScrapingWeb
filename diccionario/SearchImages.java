package diccionario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SearchImages {

    private static Scanner input;
    private static String fileName = "vocabulary.txt";
    private static ArrayList<String> listaUrl;
    private static ArrayList<String> listaPalabras;
    protected Formatter salida;
    private static String nombreArchivo = "urlImages.txt";

    public static void main(String[] args) {
        listaPalabras = new ArrayList<>();
        listaUrl = new ArrayList<>();
        leerArchivo(); // el arrayList contiene los nombres 
        System.out.println("Se ha cerrado el archivo");
        System.out.println("Obteniendo url de imagenes...");
        String url = "";
        for (String palabra : listaPalabras) {
            System.out.println(palabra);
            url = urlImage(palabra);
            listaUrl.add(urlImage(palabra));
            
        }
        System.out.println("gusrdando url de imagenes en archivo");
        EscribirArchivo();
    } // fin de main 

    public static void leerArchivo() {
        
        try {
            input = new Scanner(new File(fileName));
        } // end try
        catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Error al abrir el archivo.");
            System.exit(1);
        }
        try { // read records from file using Scanner object
            while (input.hasNext()) {
                String linea = input.nextLine();
                if ( linea.startsWith("11")) {
                    break;
                }
                String valores[] = linea.split("\\|");
                    listaPalabras.add(valores[1]);
                
            }
        } catch (NoSuchElementException elementException) {
            System.err.println("Formato de archivo incorrecto.");
            input.close();
            System.exit(1);
        } catch (IllegalStateException stateException) {
            System.err.println("Error al leer de archivo.");
            System.exit(1);
        } // end catch
        if (input != null) {
            input.close(); // cierra el archivo
        }
    }

    public static String urlImage(String index) {
        //replace it with your URL 
        String strURL = "https://foter.com/search/instant/?q=" + index;
        int contador = 0;
        String src = "";
        //System.out.println("URL: " + strURL);
        try {
            //Connect to the website and get the html
            Document doc = Jsoup.connect(strURL).timeout(10000).get();
            //Get all elements with img tag ,
            Elements img = doc.getElementsByTag("img");
            //Elements img = doc.getElementsByClass("image");
            int c = 0;
            for (Element el : img) {
                c++;
                if (c == 2) {
                    //for each element get the srs url
                    src = el.absUrl("src");
                    //String src = el.absUrl("abs:src");  
                    System.out.println(src);
                    return src;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return src;
    } // fin de urlImage

    public static void EscribirArchivo() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(nombreArchivo);
            pw = new PrintWriter(fichero);
            for (String string : listaUrl) {
                //System.out.println(string);
                pw.println(string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }//EscribirArchivo
}
