import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Main {
    public static String connectionError="";
    String pro="";


    public  URL checkProtocolAndSetURL(String urlString)
    {
        URL URL=null;


     if(!(urlString.contains("http://") || urlString.contains("https://")))
     {
         try {
             URL=new URL("https://"+urlString);
         } catch (MalformedURLException e) {
             e.printStackTrace();
         }
     }

     else
     {
         try {
             URL=new URL(urlString);
         } catch (MalformedURLException e) {
             e.printStackTrace();
         }
     }
        return URL;
    }

    public  URLConnection connection(URL url)
    {
        URLConnection urlConnection=null;
        try {
            urlConnection=url.openConnection();
            urlConnection.connect();
        } catch (IOException e) {
            String urlString=url.toString();
            if(urlString.contains("http://"))
            {
                urlString=urlString.replace("http://","https://");
            }
            else if (urlString.contains("https://"))
            {
                urlString=urlString.replace("https://","http://");
            }
            try {
                url=new URL(urlString);
                urlConnection=url.openConnection();
                urlConnection.connect();
            } catch (MalformedURLException e1) {

                e1.printStackTrace();
            } catch (IOException e1) {
                connectionError="can't connect";
                e1.printStackTrace();
            }

        }
        return urlConnection;
    }


}
