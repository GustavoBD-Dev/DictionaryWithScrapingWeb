package diccionario;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import org.jsoup.select.Elements;

public class wordreferenceScraper {

    public static void main(String[] args) {
        Scanner entrada = new Scanner (System.in);
        String word = entrada.nextLine();
        String wordUrl = "definicion/" + word;
        scrapeTopic(wordUrl);
    }

    public static void scrapeTopic(String url) {
        //obtener la informacion 
        String finalurl = "https://www.wordreference.com/" + url;
        String html = getUrl(finalurl);
        Document doc = Jsoup.parse(html);
        String contentText = doc.select(".entry").text();
        System.out.println(contentText);
    }

    public static String getUrl(String url) {
        URL urlObj = null;
        try {
            urlObj = new URL(url);
        } catch (MalformedURLException e) {
            System.out.println("The url was malformed!");
            return "";
        }
        URLConnection urlCon = null;
        BufferedReader in = null;
        String outputText = "";
        try {
            urlCon = urlObj.openConnection();
            in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            String line = "";
            while ((line = in.readLine()) != null) {
                outputText += line;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("There was an error connecting to the URL");
            return "";
        }
        return outputText;
    }
}
