import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;

@SuppressWarnings("serial")
class Home extends JFrame {

    public Home(){
    }
    private static JPanel totalGUI = new JPanel();
    private static JPanel panel = new JPanel();
    private static JLabel urlLabel = new JLabel("url ");
    private static JTextField urlField = new JTextField();
    private static JLabel depthLabel = new JLabel("Depth");
    private static JTextField depthField = new JTextField("0");
    private static JButton submitButton = new JButton("Clone");
    private static JLabel pathLabel = new JLabel("Path ");
    private static JTextField pathField = new JTextField();
    private static JLabel folderLabel = new JLabel("Name ");
    private static JTextField folderField = new JTextField();
    private static JRadioButton select=new JRadioButton(" check if you want to change the domain to yours");
    private static JTextField domain=new JTextField();
    private static JButton pathBrowse = new JButton("Browse ");



    private JPanel FirstPage(){


        totalGUI.setLayout(null);




        panel.setLayout(null);
        panel.setLocation(10, 0);
        panel.setSize(500, 400);
       // panel.setBackground(Color.red);

        totalGUI.add(panel);


        urlLabel.setLocation(0, 0);
        urlLabel.setSize(100, 30);
        urlLabel.setHorizontalAlignment(0);
        urlLabel.setForeground(Color.black);
        panel.add(urlLabel);


        urlField.setLocation(110, 8);
        urlField.setSize(250, 25);
        urlField.setHorizontalAlignment(0);
        urlField.setForeground(Color.black);
        panel.add(urlField);

        depthLabel.setLocation(9, 35);
        depthLabel.setSize(100, 30);
        depthLabel.setHorizontalAlignment(0);
        depthLabel.setForeground(Color.black);
        panel.add(depthLabel);


        depthField.setLocation(110, 40);
        depthField.setSize(50, 25);
        depthField.setHorizontalAlignment(0);
        depthField.setForeground(Color.black);
        panel.add(depthField);

        pathLabel.setLocation(7, 68);
        pathLabel.setSize(100, 30);
        pathLabel.setHorizontalAlignment(0);
        pathLabel.setForeground(Color.black);
        panel.add(pathLabel);


        pathField.setLocation(110, 73);
        pathField.setSize(200, 25);
        pathField.setHorizontalAlignment(0);
        pathField.setForeground(Color.black);
        panel.add(pathField);

        pathBrowse.setLocation(320, 70);
        pathBrowse.setSize(90, 25);
        panel.add(pathBrowse);

        folderLabel.setLocation(9, 100);
        folderLabel.setSize(100, 30);
        folderLabel.setHorizontalAlignment(0);
        folderLabel.setForeground(Color.black);
        panel.add(folderLabel);


        folderField.setLocation(110, 105);
        folderField.setSize(200, 25);
        folderField.setHorizontalAlignment(0);
        folderField.setForeground(Color.black);
        panel.add(folderField);

        select.setLocation(30, 140);
        select.setSize(400, 30);
        select.setHorizontalAlignment(0);
        select.setForeground(Color.black);
        panel.add(select);


        domain.setLocation(80, 170);
        domain.setSize(220, 25);
        domain.setHorizontalAlignment(0);
        domain.setForeground(Color.black);
        domain.setEnabled(false);

        panel.add(domain);

        submitButton.setLocation(190, 250);
        submitButton.setSize(200, 30);
        panel.add(submitButton);

        totalGUI.setOpaque(true);

        return totalGUI;

    }

    public void pathBrowseClicked(ActionEvent e)
    {
        String fileID;
        if (e.getSource() == pathBrowse)
        {
           JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.home") + "\\Downloads")); //Downloads Directory as default
            chooser.setDialogTitle("Select Location");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                fileID = chooser.getSelectedFile().getPath();
                pathField.setText(fileID+"/");
            }
        }
    }

    public void createFirstPage() {

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Web Cloner");


        frame.setContentPane(FirstPage());

        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setVisible(true);
    }

    public void initEvent(){


        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitButtonClicked(e);
            }
        });

        pathBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pathBrowseClicked(e);
            }
        });

        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                domain.setEnabled(true);
            }
        });


    }




    private void submitButtonClicked(ActionEvent e)
    {
        String url_string=urlField.getText();
        String path=pathField.getText();
        String folder=folderField.getText();
        CloningPage cloningPage=new CloningPage();
        Main main=new Main();

        URL url=main.checkProtocolAndSetURL(url_string);

            Thread guiThread=new Thread(new CloningPage());
            this.setVisible(false);
            guiThread.start();
            CloningPage.textArea.append("connecting ...  "+url+"\n");


            new Thread(new Runnable() {
                @Override
                public void run() {
                    URLConnection urlConnection=main.connection(url);
                    if (!(Main.connectionError.equals("")))
                    {
                        JOptionPane.showMessageDialog(null, "please enter valid url\n can't connect to "+url+"\n"+main.pro ,
                                "Connection error", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {}
                    File file=new File(path+folder);
                    file.mkdirs();
                    cloningPage.parse(urlConnection,file);
                    if (!cloningPage.successfulMessage.equals(""))
                    {
                        JOptionPane.showMessageDialog(null, cloningPage.successfulMessage,
                                "Finished", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }).start();

    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Home home=new Home();
                home.createFirstPage();
                home.initEvent();
            }
        });



    }


}