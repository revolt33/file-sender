package NetworkActivity;

import StartUp.ParentWindow;
import javax.swing.*;
import java.awt.event.*;
import java.rmi.*;
import java.net.*;

public class JoinNetwork implements ActionListener{
    JComboBox<String> com;
    int mode;
    JDialog jd;
    static String ip;
    public JoinNetwork (JComboBox<String> com, int mode, JDialog jd) {
        this.com = com;
        this.mode = mode;
        this.jd = jd;
    }
    @Override
    public void actionPerformed (ActionEvent ae) {
        boolean status = true;
        if ( mode == 1) {
            ip = (String)com.getEditor().getItem();
            if (ip != null) {
                try {
                    Network.obj = (FileIntf) Naming.lookup("rmi://" + ip + ":9000/files");
                    ServiceProvider ser = new ServiceProvider();
                    Network.token = Network.obj.addUser(ser);
                    Network.obj.addIP(Inet4Address.getLocalHost().getHostAddress());
                    Network.obj.refreshAll();
                } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                    JOptionPane.showMessageDialog(ParentWindow.parent, "Some Error Occured!");
                    status = false;
                } catch (java.net.UnknownHostException ex) {
                    JOptionPane.showMessageDialog(ParentWindow.parent, "Some Error Occured!");
                    status = false;
                }
            }
        } else
            status = false;
        jd.dispose();
        if (status) {
            ParentWindow.card.show(ParentWindow.cardMenu[3], "menu2");
            ParentWindow.label.setText("Status: Connected");
        }
    }
}