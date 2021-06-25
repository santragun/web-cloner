import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CloningPage extends JFrame implements Runnable{
    public CloningPage()
    {
       // createCloningPageGUI();
    }


    public String content="";
    public String newContent="";
    public int progress=0;

    public int processes=0;
    public  String savingError="";
    public  String successfulMessage="";

    private static JPanel totalGUI = new JPanel();
    private static JPanel panel = new JPanel();

    public static JTextArea textArea = new JTextArea(5, 50);
    private static JScrollPane scrollPane = new JScrollPane(textArea);
    private static JButton cancelButton=new JButton("Cancel");

    private static JPanel cloningPageGUI()
    {
        totalGUI.setLayout(null);


        panel.setLayout(null);
        panel.setLocation(10, 0);
        panel.setSize(600, 400);
        totalGUI.add(panel);


        scrollPane.setLocation(10, 10);
        scrollPane.setSize(560, 300);
        scrollPane.setForeground(Color.blue);
        textArea.setForeground(Color.blue);
        textArea.setEditable(false);
        panel.add(scrollPane);

        cancelButton.setLocation(470, 320);
        cancelButton.setSize(100, 25);
        panel.add(cancelButton);

        totalGUI.setOpaque(true);

        return totalGUI;

    }

    public static void createCloningPageGUI() {


    }

    public void parse(URLConnection urlConnection, File path)
    {
        String inputLine="";
        InputStream inputStream=null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader =null;
        try {
            inputStream = urlConnection.getInputStream();
            inputStreamReader=new InputStreamReader(inputStream);
            bufferedReader= new BufferedReader(inputStreamReader);


                while ((inputLine = bufferedReader.readLine()) != null) {
                    content += inputLine;
                }

        } catch (IOException e) {
            e.printStackTrace();
        }


        newContent=content;
        //////////////////////////////////// Saving CSS Files/////////////////////////////////////////////////

        String cssLinkRegex="[^\"]+\\.css";

        Pattern pattern = Pattern.compile(cssLinkRegex);
        Matcher matcher = pattern.matcher(content);
        File cssFolder = new File(path,"css");
        if (!cssFolder.exists()) {
            cssFolder.mkdirs();
        }

        while (matcher.find())
        {
            String url="";
            String cssContainedUrl=content.substring(0,matcher.end());
            String cssUrl=cssContainedUrl.substring(cssContainedUrl.lastIndexOf("\"")+1);
            String orgUrl=cssUrl;
            if (!(cssUrl.startsWith("http"))) {
                cssUrl=cssUrl.replaceAll("\\\\","");
                cssUrl=cssUrl.replaceAll("\\\\","");
                if(!(cssUrl.startsWith("/")))
                {
                    url= urlConnection.getURL().toString() +"/"+ cssUrl;
                }
                else
                {
                    url= urlConnection.getURL().toString() + cssUrl;
                }

            } else {
                url=cssUrl.replaceAll("\\\\","");
                url=url.replaceAll("\\\\","");
            }
            URL URL=null;
            try {
                URL=new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            textArea.append("trying ...   "+url+"\n");
            downloadCss(URL,cssFolder,orgUrl);
        }


        ////////////////////////////////////////End of CSS///////////////////////////////////////////////////



        //////////////////////////////////////////Saving images///////////////////////////////////////////////////

        String imgLinkRegex="[^\"]+\\.(jpg|jpeg|png|gif|JPG|JPEG|PNG|GIF|bmp|ico|ICO)";

        pattern = Pattern.compile(imgLinkRegex);
        matcher = pattern.matcher(content);
        File imgFolder = new File(path,"images");
        if (!imgFolder.exists()) {
            imgFolder.mkdirs();
        }


        while (matcher.find())
        {
            String url="";
            String imgContainedUrl=content.substring(0,matcher.end());
            String imgUrl=imgContainedUrl.substring(imgContainedUrl.lastIndexOf("\"")+1);
            String orgUrl=imgUrl;
            if (imgUrl.contains("background:url"))
            {
                imgUrl=imgUrl.substring(imgUrl.lastIndexOf("background:url")+16);
            }
            if (imgUrl.contains("'"))
            {
                if(imgUrl.indexOf("'")>imgUrl.indexOf("\""))
                {
                    imgUrl=imgUrl.substring(imgUrl.indexOf("'")+2);
                }
            }
            if (!(imgUrl.startsWith("http"))) {
                imgUrl=imgUrl.replaceAll("\\\\","");
                imgUrl=imgUrl.replaceAll("\\\\","");
                if(!(imgUrl.startsWith("/")))
                {
                    url= urlConnection.getURL().toString() +"/"+ imgUrl;
                }
                else
                {
                    url= urlConnection.getURL().toString() + imgUrl;
                }

            } else {
                url=imgUrl.replaceAll("\\\\","");
                url=url.replaceAll("\\\\","");
            }
            URL URL=null;
            try {
                URL=new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            textArea.append("trying ...   "+url+"\n");
            downloadImage(URL,imgFolder,orgUrl);
        }
        ///////////////////////////////////////////End of images/////////////////////////////////////////////



        ///////////////////////////////////////////Saving js/////////////////////////////////////////////////

        String jsLinkRegex="[^\"]+\\.js(\"|[?])";

        pattern = Pattern.compile(jsLinkRegex);
        matcher = pattern.matcher(content);
        File jsFolder = new File(path,"js");
        if (!jsFolder.exists()) {
            jsFolder.mkdirs();
        }

        while (matcher.find())
        {
            String url="";
            String jsContainedUrl=content.substring(0,matcher.end()-1);
            String jsUrl=jsContainedUrl.substring(jsContainedUrl.lastIndexOf("\"")+1);
            String orgUrl=jsUrl;
            if (!(jsUrl.startsWith("http"))) {
                jsUrl=jsUrl.replaceAll("\\\\","");
                jsUrl=jsUrl.replaceAll("\\\\","");
                if(!(jsUrl.startsWith("/")))
                {
                    url= urlConnection.getURL().toString() +"/"+ jsUrl;
                }
                else
                {
                    url= urlConnection.getURL().toString() + jsUrl;
                }

            } else {
                url=jsUrl.replaceAll("\\\\","");
                url=url.replaceAll("\\\\","");
            }
            URL URL=null;
            try {
                URL=new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            textArea.append("trying ...   "+url+"\n");
            downloadJs(URL,jsFolder,orgUrl);
        }



        //////////////////////////////////////End of Js/////////////////////////////////////////

        downloadContent(newContent,path,urlConnection.getURL());



    }

    public void downloadImage(URL imgSrc,File path,String orgUrl)
    {

        BufferedImage image = null;

        String imgName = imgSrc.toString().substring(imgSrc.toString().lastIndexOf("/") + 1);
        String imgFormat = null;
        imgFormat = imgSrc.toString().substring(imgSrc.toString().lastIndexOf(".") + 1);
        String imgPath =imgName + "";
        File file = new File(path,imgPath);
        InputStream inputStream=null;
        ByteArrayOutputStream byteArrayOutputStream=null;
        byte[] response=new byte[1024];
        try {
            inputStream = new BufferedInputStream(imgSrc.openStream());
            byteArrayOutputStream = new ByteArrayOutputStream();

            byte[] buf = new byte[2048];
            int n = 0;
            while (-1!=(n=inputStream.read(buf)))
            {
                byteArrayOutputStream.write(buf, 0, n);
            }
            byteArrayOutputStream.close();
            inputStream.close();
            response = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
        }
        FileOutputStream fileOutputStream=null;

        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(response);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        newContent=newContent.replace(orgUrl,file.toString());


    }



    public String  downloadCss(URL cssSrc,File path,String orgUrl)
    {

        InputStream inputStream=null;

        FileWriter fileWriter= null;
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader=null;
        String inputLine=null;

        try {

            String cssName = cssSrc.toString().substring(cssSrc.toString().lastIndexOf("/") + 1);
            String cssPath =cssName + "";

            inputStream=cssSrc.openStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            File file = new File(path,cssPath);
            if(!(file.exists()))
            {
                file.createNewFile();
            }
            fileWriter=new FileWriter(file.getAbsoluteFile());
            bufferedWriter=new BufferedWriter(fileWriter);
            while ((inputLine = bufferedReader.readLine()) != null) {

                bufferedWriter.append(inputLine);
            }
            newContent=newContent.replace(orgUrl,file.toString());


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            bufferedReader.close();
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return newContent;

    }

    public String downloadJs(URL jsSrc,File path,String orgUrl)
    {
        InputStream inputStream=null;

        FileWriter fileWriter= null;
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader=null;
        String inputLine=null;

        try {

            String jsName = jsSrc.toString().substring(jsSrc.toString().lastIndexOf("/") + 1);
            String cssPath =jsName + "";

            inputStream=jsSrc.openStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            File file = new File(path,cssPath);
            fileWriter=new FileWriter(file.getAbsoluteFile());
            bufferedWriter=new BufferedWriter(fileWriter);

            while ((inputLine = bufferedReader.readLine()) != null) {

                bufferedWriter.append(inputLine);
            }
            newContent=newContent.replace(orgUrl,file.toString());


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            bufferedReader.close();
            bufferedWriter.close();
            //bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return newContent;
    }

    public void downloadContent(String content,File path,URL url)
    {

        String fileName = url.toString().substring(url.toString().indexOf(".") + 1,url.toString().lastIndexOf("."));
        File file=new File(path,fileName+".html");
        if(!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter fileWriter= null;
        BufferedWriter bufferedWriter=null;
        String inputLine="";
        try {
            fileWriter = new FileWriter(file.getAbsoluteFile());
            bufferedWriter=new BufferedWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            bufferedWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void run() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Web Cloner");

        frame.setContentPane(cloningPageGUI());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(610, 400);
        frame.setVisible(true);
    }
}
