package StartUp;
import java.awt.Image;
import javax.swing.*;
import java.io.*;
import java.rmi.RMISecurityManager;
import java.util.*;
public class MainClass {
    public static void main (String[] s) {
        if (System.getSecurityManager() == null)
		System.setSecurityManager(new RMISecurityManager());
        System.setProperty("Quaqua.tabLayoutPolicy", "wrap");
        try {
            UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Exceptions.exceptions("StartUp\\MainClass", ex);
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        File ips = new File("ips"),pakg1 = new File("Errors\\NetworkActivity"), pakg2 = new File("Errors\\StartUp"), user = new File("UserData");
        if ( !pakg1.isDirectory() )
            pakg1.mkdirs();
        if ( !pakg2.isDirectory() ) 
            pakg2.mkdirs();
        if ( !user.isFile() ) {
            ImageIcon icon = new ImageIcon("Icon.png");
            Image image = icon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            UserData data = new UserData("New User", icon);
            try {
                FileOutputStream fos = new FileOutputStream(user);
                NewFile obj = new NewFile(fos);
                obj.writeObject(data);
            } catch (FileNotFoundException ex) {
                Exceptions.exceptions("StartUp\\MainClass", ex);
            } catch (IOException ex) {
                Exceptions.exceptions("StartUp\\MainClass", ex);
            }
        }
        if ( ips.isFile()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(ips);
                ObjectInputStream obj = new ObjectInputStream(fis);
                NetworkActivity.Network.ips = (HashSet<String>) obj.readObject();
            } catch (FileNotFoundException ex) {
                Exceptions.exceptions("StartUp\\MainClass", ex);
            } catch (IOException | ClassNotFoundException ex) {
                Exceptions.exceptions("StartUp\\MainClass", ex);
            } finally {
                try {
                    fis.close();
                } catch (IOException ex) {
                    Exceptions.exceptions("StartUp\\MainClass", ex);
                }
            }
        }
        new ParentWindow("FileSender").showFrame();
    }
}
