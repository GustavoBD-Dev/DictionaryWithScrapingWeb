
package diccionario;
/*import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
 
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URL;
 
public class JsoupDownloadImagesExample {
 
    //The url of the website. This is just an example
    private static final String webSiteURL = "https://www.freepik.es/search?dates=any&format=search&page=1&query=manzana&sort=popular";
 
    //The path of the folder that you want to save the images to
    private static final String folderPath = "C:\\Users\\pc\\Documents\\UAEMex\\Analisis de lenguajes de programacion\\Diccionario\\src\\Images";
 
    public static void main(String[] args) {
 
        try {
 
            //Connect to the website and get the html
            Document doc = Jsoup.connect(webSiteURL).get();
 
            //Get all elements with img tag ,
            Elements img = doc.getElementsByTag("img");
 
            for (Element el : img) {
 
                //for each element get the srs url
                String src = el.absUrl("src");
 
                System.out.println("Image Found!");
                System.out.println("src attribute is : "+src);
 
                getImages(src);
                break;
            }
 
        } catch (IOException ex) {
            System.err.println("There was an error");
            Logger.getLogger(DownloadImages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    private static void getImages(String src) throws IOException {
 
        String folder = null;
 
        //Exctract the name of the image from the src attribute
        int indexname = src.lastIndexOf("/");
 
        if (indexname == src.length()) {
            src = src.substring(1, indexname);
        }
 
        indexname = src.lastIndexOf("/");
        String name = src.substring(indexname, src.length());
 
        System.out.println(name);
 
        //Open a URL Stream
        URL url = new URL(src);
        InputStream in = url.openStream();
 
        OutputStream out = new BufferedOutputStream(new FileOutputStream( folderPath+ name));
 
        for (int b; (b = in.read()) != -1;) {
            out.write(b);
        }
        out.close();
        in.close();
 
    }
}
*/
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
public class JsoupDownloadImagesExample {
 
    private static String IMAGE_DESTINATION_FOLDER = "C:\\Users\\pc\\Documents\\UAEMex\\Analisis de lenguajes de programacion\\Diccionario\\src\\Images";
    
    public static void main(String[] args) throws IOException {
        
        //replace it with your URL 
        String index = "perro";
        String strURL = "https://www.freepik.es/search?dates=any&format=search&page=1&query=" + index + "&sort=popular";
        //String strURL = "https://pixabay.com/es/images/search/"+index +"/";
        
        System.out.println("URL" + strURL);
        //connect to the website and get the document
        Document document = Jsoup
                .connect(strURL)
                .userAgent("Mozilla/5.0")
                .timeout(10 * 1000)
                .get();
        
        //select all img tags
        Elements imageElements = document.select("img .lazyload lazyload.done");
        //iterate over each image
        for(Element imageElement : imageElements){
            
            //make sure to get the absolute URL using abs: prefix
            String strImageURL = imageElement.attr("abs:src");
            
            //download image one by one
            downloadImage(strImageURL);
            
       
            
            
        }
 
    }
    
    private static void downloadImage(String strImageURL){
        
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
        
    }
}