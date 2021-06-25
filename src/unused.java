import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;

public class unused {


public String content=null;

    public void parse(URLConnection urlConnection, File path) {
        String inputLine;


        File dir = path;

        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            while ((inputLine = bufferedReader.readLine()) != null) {
                content += inputLine;
            }


            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String pageLinksRegex = "(https?|http?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

        String imageLinksRegex = "\"[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]\\.(png|jpg|gif|jpeg|ico|PNG|JPG|GIF|JPEG|ICO)";
        //String cssLinkRegex = "\"[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]\\.(css)";
        String cssLinkRegex="\"*.css";
        String jsLinkRegex = "\"[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]\\.(js)";




        File imgFolder = new File(path,"images");
        if (!imgFolder.exists()) {
            imgFolder.mkdirs();
        }

        // content=imageSave(imgFolder,urlConnection.getURL().toString(),content,imageLinksRegex);

        File cssFolder = new File(path,"css");
        if (!cssFolder.exists()) {
            cssFolder.mkdirs();
        }

        // content=cssSave(cssFolder,urlConnection.getURL().toString(),content,cssLinkRegex);

        File file = new File(path,"index.html");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // pageSave(file,content,urlConnection.getURL().toString());



    }


    /*

    public String imageSave(File imgFolder,String imgUrlStr,String content,String imageLinksRegex)
    {

        Pattern pattern = Pattern.compile(imageLinksRegex);
        Matcher matcher = pattern.matcher(content);


        URL url=null;
        InputStream inputStream=null;
        ByteArrayOutputStream byteArrayOutputStream=null;
        byte[] response=new byte[1024];

        while (matcher.find()) {

            String imgPath=content.substring(matcher.start()+1,matcher.end());

            String imageName=imgPath.substring(imgPath.lastIndexOf("/"));
            String toreplace=matcher.group().substring(matcher.group().indexOf("\"")+1);

            content=content.replaceFirst(toreplace,(imgFolder.toPath()+imageName));

            String pathToDownloadImage=imgUrlStr+imgPath;

            File file=null;
            try {
               file=new File(imgFolder,imageName);
                if (!file.exists())
                    file.createNewFile();

                url = new URL(pathToDownloadImage);
                inputStream = new BufferedInputStream(url.openStream());
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

        }

        return content;
    }

    public String cssSave(File cssFolder,String cssURLstr,String content,String cssLinkregex)
    {

        Pattern pattern = Pattern.compile(cssLinkregex);
        Matcher matcher = pattern.matcher(content);


        URL url=null;
        InputStream inputStream=null;
        textArea.append("this is css\n");
        while (matcher.find()) {

            //String cssPath = content.substring(matcher.start() + 1, matcher.end());
            String path=matcher.group();
            textArea.append(matcher.group());

             String cssFileName = cssPath.substring(cssPath.lastIndexOf("/"));
            String toReplacePath = matcher.group().substring(matcher.group().indexOf("\"") + 1);

            StringBuilder builder=new StringBuilder(content);
            builder.replace(matcher.start()+matcher.group().indexOf("\""),
                    matcher.start()+matcher.group().lastIndexOf("s"),cssFolder.toPath()+cssFileName);
            //content = content.replaceFirst(toReplacePath, (cssFolder.toPath() + cssFileName));
            content=builder.toString();
            String pathToDownload=null;
            textArea.append(cssFileName+"\n");
            textArea.append(cssPath+"\n"+cssURLstr);
            if(cssURLstr.startsWith("http")){
                pathToDownload=cssPath;
            }
            else
            {
                pathToDownload= cssURLstr + cssPath;
            }


            File file = null;
            FileWriter fileWriter= null;
            BufferedWriter bufferedWriter = null;
            BufferedReader bufferedReader=null;
            ByteArrayOutputStream byteArrayOutputStream=null;
            String inputLine=null;
            try {

                file=new File(cssFolder,cssFileName);
                if (!file.exists())
                    file.createNewFile();

                url = new URL(pathToDownload);
                inputStream=url.openStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


                fileWriter=new FileWriter(file.getAbsoluteFile());
                bufferedWriter=new BufferedWriter(fileWriter);
                textArea.append("0% mirroring "+pathToDownload+"...\n");
                while ((inputLine = bufferedReader.readLine()) != null) {

                    bufferedWriter.write(inputLine);

                }

                bufferedWriter.close();
                bufferedReader.close();

            } catch (IOException e) {
                e.printStackTrace();

        }
        return content;

    }
    public void pageSave(File file, String content,String url)
    {
        FileWriter fileWriter= null;
        BufferedWriter bufferedWriter = null;

        try {

            fileWriter=new FileWriter(file.getAbsoluteFile());
            bufferedWriter=new BufferedWriter(fileWriter);
            textArea.append("0% mirroring "+url+"...\n");
            bufferedWriter.write(content);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        textArea.append("100% ... Cloning Finished\n");
    }





progress=processes/100;
        InputStream inputStream=null;

        FileWriter fileWriter= null;
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader=null;
        String inputLine=null;

            String cssName = cssSrcUrl.substring(cssSrcUrl.lastIndexOf("/") + 1);
            String CssFormat = null;
            CssFormat = cssSrcUrl.substring(cssSrcUrl.lastIndexOf(".") + 1);
            String cssPath = null;
            cssPath =cssName + "";
        URL cssUrl = null;
        try {
            cssUrl = new URL(cssSrcUrl);
            inputStream=cssUrl.openStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
try{
            File file = new File(path,cssPath);
            fileWriter=new FileWriter(file.getAbsoluteFile());
            bufferedWriter=new BufferedWriter(fileWriter);
            textArea.append(" mirroring "+cssSrcUrl+"\n");
            textArea.append(cssUrl+"\n");
            while ((inputLine = bufferedReader.readLine()) != null) {

                bufferedWriter.write(inputLine);
            }
            content=content.replaceAll(orgUrl,file.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }


         Pattern pattern = Pattern.compile(cssLinkRegex);
        Matcher matcher = pattern.matcher(content);
        File cssFolder1 = new File(path,"css");
        if (!cssFolder1.exists()) {
            cssFolder1.mkdirs();
        }
        while (matcher.find())
        {
            String url="";
            String strUrl="";
            String cssContainedUrl=content.substring(0,matcher.end());
            String cssUrl=cssContainedUrl.substring(cssContainedUrl.lastIndexOf("\"")+1);
            if (!(cssUrl.startsWith("http"))) {
                strUrl = urlConnection.getURL().toString() + cssUrl;
            } else {
                strUrl = cssUrl;

                //url=strUrl.replaceAll("\\\\","");
                //url=url.replaceAll("\\\\","");
            }
            textArea.append(url+"\n");
            downloadCss(url,strUrl,cssFolder1);
        }



*/





















    /*
            if (imgSrc != null && (imgSrc.endsWith(".jpg") || (imgSrc.endsWith(".jpeg"))
                    || (imgSrc.endsWith(".png")) || (imgSrc.endsWith(".ico"))
                    || (imgSrc.endsWith(".bmp")))) {

                File imgFolder = new File(path,"images");
                if (!imgFolder.exists()) {
                    imgFolder.mkdirs();
                }

               // downloadImage(urlConnection.getURL().toString(), imgSrc,imgFolder);
            }
        }


        for (HTMLDocument.Iterator iterator = htmlDoc.getIterator(HTML.Tag.SCRIPT);
             iterator.isValid(); iterator.next()) {
            AttributeSet attributes = iterator.getAttributes();
            String jsSrc = (String) attributes.getAttribute(HTML.Attribute.SRC);

            if (jsSrc != null && (jsSrc.endsWith(".JS") || (jsSrc.endsWith(".js")))) {

                File jsFolder = new File(path,"js");
                if (!jsFolder.exists()) {
                    jsFolder.mkdirs();
                }

                //downloadJs(urlConnection.getURL().toString(), jsSrc,jsFolder);
            }
        }*/




















}
