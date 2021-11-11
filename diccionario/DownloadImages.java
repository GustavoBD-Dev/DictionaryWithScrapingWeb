
package diccionario;


 
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
 
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URL;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;

public class DownloadImages {
    //The url of the website. This is just an example
    private static String webSiteURL = "https://pixabay.com/es/images/search/platano/";//"https://es.wikipedia.org/wiki/Python";
    //The path of the folder that you want to save the images to
    private static String IMAGE_DESTINATION_FOLDER = "C:\\Users\\pc\\Documents\\UAEMex\\Analisis de lenguajes de programacion\\Diccionario\\src\\Images";

    public static void main(String[] args) {
         Document doc = null;
        try {
            
            String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";
            //Connection.Response loginResponse = Jsoup.connect("yourWebsite.com/loginUrl")            
            Connection.Response loginResponse = Jsoup.connect("https://pixabay.com/es/accounts/login/?source=main_nav")
                                        .userAgent(USER_AGENT)
                                        .data("username", "GuztavoMX")
                                        .data("password", "MSD9BN45")
                                        .method(Method.POST)
                                        .execute();
            Document loginDoc = loginResponse.parse(); // this is the document containing response html
            /**
             * GuztavoMX
             * exterminador.duran@gmail.com
             * MSD9BN45
             */
            
            //Connect to the website and get the html
            //doc = Jsoup.connect(webSiteURL).userAgent("Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/20100101 Firefox/32.0").ignoreHttpErrors(true).get();
            Elements img = loginDoc.getElementsByTag("img");
            //Get all elements with img tag ,
            //Elements img = doc.getElementsByTag("img");
            //Elements img = doc.getElementsByClass("image");
            for (Element el : img) {
                //for each element get the srs url
                String src = el.absUrl("src");
                System.out.println("Image Found!");
                System.out.println("src attribute is : "+src);   
                downloadImage(src);
                String strImageName = src.substring( src.lastIndexOf("/") + 1 );
                break;
            }
        } catch (IOException ex) {
            System.err.println("There was an error");
            Logger.getLogger(DownloadImages.class.getName()).log(Level.SEVERE, null, ex);
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
 
    /*private static void getImages(String src) throws IOException {
 
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
 
    }*/
}
