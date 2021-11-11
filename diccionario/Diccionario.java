/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diccionario;

import java.io.IOException;
import java.util.Scanner;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 *
 * @author Gustavo
 */
public class Diccionario {

    //public static final String url = "https://es.wikipedia.org/wiki/";
	
    public static void main (String args[]) {
        String urlPrincipal = "https://es.wikipedia.org/wiki/";
        Scanner entrada = new Scanner(System.in);
        String palabra;
        palabra = entrada.nextLine();
        String url = urlPrincipal + palabra;
        System.out.println("url: " + url );
		
        String urlPage = url;//String.format(url, i);
        System.out.println("Comprobando entradas de: "+urlPage);		
        // Compruebo si me da un 200 al hacer la petición
        if (getStatusConnectionCode(urlPage) == 200) {
            // Obtengo el HTML de la web en un objeto Document2
            Document document = getHtmlDocument(urlPage);
            // Busco todas las historias de meneame que estan dentro de:
            
            Elements entradas = document.select(".infobox");
            for (Element entrada1 : entradas) {
                String definicion = entrada1.getElementsByClass("image").toString();
                //String definicion = entrada1.getElementsByClass("div.dashed-bottom-box").toString();
                System.out.println(definicion);
            }
            
        }else{
            System.out.println("El Status Code no es OK es: "+getStatusConnectionCode(urlPage));
        }

    }
	
	
    /**
     * Con esta método compruebo el Status code de la respuesta que recibo al hacer la petición
     * EJM:
     * 		200 OK			300 Multiple Choices *****Estatus Code para que funcione***
     * 		301 Moved Permanently	305 Use Proxy
     * 		400 Bad Request		403 Forbidden
     * 		404 Not Found		500 Internal Server Error
     * 		502 Bad Gateway		503 Service Unavailable
     * @param url
     * @return Status Code
     */
    public static int getStatusConnectionCode(String url) {	
        Response response = null;	
        try {
            response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
        } catch (IOException ex) {
            System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
        }
        return response.statusCode();
    }
	
    /**
     * Con este método devuelvo un objeto de la clase Document con el contenido del
     * HTML de la web que me permitirá parsearlo con los métodos de la librelia JSoup
     * @param url
     * @return Documento con el HTML
     */
    public static Document getHtmlDocument(String url) {
        Document doc = null;//Document es una clase que guarda el HTML
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
        } catch (IOException ex) {
            System.out.println("Excepción al obtener el HTML de la página" + ex.getMessage());
        }
        return doc;
    }
    
}


//https://jsoup.org/apidocs/index.html?org/jsoup/select/Elements.html

/**                 SINTAXIS 
 * Pattern  Matches                                         Example
 *  *       cualquier elemento                              *
 *  tag     elementos con el nombre de tiqueta dado         div
 * #id      elementos con el atributo ID                    #logo
 * .class   elementos con el nombre de clase                div.left.result
*/

/**                COMBINADORES 
 *  E F     un elemento F desciende de un elemento E        div a, .logo h1
 *  E > F   un hijo directo F de E                          ol > li
 *  E, F, G todos los elementos coincidentes E,F,G          a[href], div, h3
 */