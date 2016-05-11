package NetworkActivity;
import java.io.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import StartUp.*;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.util.*;
import javax.swing.*;
public class ServiceProvider extends UnicastRemoteObject implements FileIntf {

    FileOutputStream fos;
    UserData data;
    static int tokenCount = 1;

    public ServiceProvider() throws RemoteException {
        FileInputStream fis = null;
        File file = new File ("UserData");
        try {
            if (file.isFile()) {
                fis = new FileInputStream("UserData");
                ReadFile read = new ReadFile(fis);
                data = (UserData) read.readObject();
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException | ClassNotFoundException ex) {
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Exceptions.exceptions("NetworkActivity\\ServiceProvider", ex);
            }
        }
    }
    @Override
    public boolean fileOpen (String file) throws RemoteException{
        try {
            if (!(new File(file).isFile())) {
                fos = new FileOutputStream(file);
                return true;
            }
        } catch (FileNotFoundException ex) {
            Exceptions.exceptions("NetworkActivity\\ServiceProvider", ex);
        }
        return false;
    }
    @Override
    public void store (byte[] b) throws RemoteException{
        try {
            fos.write(b);
        } catch (IOException ex) {
            Exceptions.exceptions("NetworkActivity\\ServiceProvider", ex);
        }
    }
    @Override
    public void closeFile () throws RemoteException{
        try {
            fos.close();
        } catch (IOException ex) {
            Exceptions.exceptions("NetworkActivity\\ServiceProvider", ex);
        }
    }
    @Override
    public Token addUser (FileIntf ser) throws RemoteException{
        tokenCount++;
        Token token = new Token(tokenCount);
        Network.list.put(token, ser);
        return token;
    }
    @Override
    public TreeMap<Token, FileIntf> getUsers () throws RemoteException {
        return Network.list;
    }
    @Override
    public UserData getUserData () throws RemoteException {
        return data;
    }
    @Override
    public void refreshAll() throws RemoteException {
        Set peers = Network.list.entrySet();
        Iterator it = peers.iterator();
            int i = 0;
            while (it.hasNext()) {
                if (i==0) {
                    i++;
                    continue;
                }
                Map.Entry<Token, FileIntf> entry = (Map.Entry) it.next();
                FileIntf serviceProvider = entry.getValue();
                serviceProvider.refresh();
                serviceProvider.refreshIP(Network.ips);
            }
        Network.refresh();
    }
    @Override
    public void refresh () throws RemoteException{
        Network.refresh();
    }
    @Override
    public Token askToken () throws RemoteException {
        return Network.token;
    }
    @Override
    public void receiveMessage (String msg, Token token) throws RemoteException {
        Message message = new Message(msg, token);
        if (ChatWindow.msgpanes.containsKey(token) == false) {
            ChatWindow.msgpanes.put(token, new ChatWindow(token));
            String str = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
            GridBagConstraints gbc = ChatWindow.msgpanes.get(token).gbc;
            JLabel label = new JLabel(str);
            label.setForeground(Color.WHITE);
            ChatWindow.msgpanes.get(token).messages.add(label, gbc);
            gbc.gridy++;
        }
        ChatWindow.msgpanes.get(token).addMsg(message);
    }

    @Override
    public void addIP(String ip) throws RemoteException {
        if (Network.ips == null) {
            Network.ips = new HashSet<>();
        }
        Network.ips.add(ip);
    }
    @Override
    public void refreshIP (HashSet<String> list) throws RemoteException {
        if ( Network.ips == null)
            Network.ips = new HashSet<>();
        Network.ips.addAll(list);
    }
    @Override
    public void leave () throws RemoteException {
        Network.obj = null;
        ParentWindow.label.setText("Status: Not Connected");
        JOptionPane.showMessageDialog(ParentWindow.parent, "Connection Broken!");
    }
    @Override
    public void removeUser (Token token) throws RemoteException {
        Network.list.remove(token);
        refreshAll();
    }
    @Override
    public boolean connection () throws RemoteException{
        return true;
    }
}