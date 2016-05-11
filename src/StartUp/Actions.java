package StartUp;
import java.awt.event.*;
import java.io.*;
import NetworkActivity.*;
import java.rmi.RemoteException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
public class Actions extends MouseAdapter implements ActionListener{
    int mode;
    
    public Actions (int mode) {
        this.mode = mode;
    }
    @Override
    public void actionPerformed (ActionEvent ae) {
        switch (mode) {
            case 1:
                Network.create();
                break;
            case 2:
                Network.join();
                break;
            case 3:
                Network.close();
                break;
            case 4:
                saveUserData();
                break;
            case 5:
                Network.leave();
                break;
            case 6:
                if (Receiver.obj != null) {
                    try {
                        if (Receiver.obj.connection()) {
                            sendMessage();
                        }
                    } catch (RemoteException ex) {
                        Exceptions.exceptions("StartUp\\Actions", ex);
                    }
                }
                break;
        }
    }
    @Override
    public void mouseClicked (MouseEvent me) {
        switch (mode) {
            case 11:
                new UserAttributes(ParentWindow.parent).showDialog();
                break;
            case 12:
                ParentWindow.card2.show(ParentWindow.temp, "progress");
                break;
            case 13:
                ParentWindow.card2.show(ParentWindow.temp, "folders");
                break;
            case 14:
                backButton();
                break;
            case 15:
                ParentWindow.card2.show(ParentWindow.temp, "chat");
                break;
            case 16:
                String str = "<html><h2>How to use.</h2><br><b><p>Creating Network:</p></b><ul><li>You can create your network by clicking on \"Create Network\" button.</li><li>Your profile name and picture will be visible to other user.</li><li>After you create your network your local ip will be visible(other users can join your network by using this ip) and a close button beside it.</li></ul><br><b><p>Joining a network:</p></b><ul><li>Once you click on \"Join Network\", a dialog box will be visible which requires the server's ip(this ip will be visible when you create network).</li><li>After you enter the ip and click on \"Join\" button, your application will attempt to connect to the server.</li><li>If connection is successful a list of users will be available which are currently connected to this network.</li></ul><br><b><p>Sharing a file:</p></b><ul><li>You need to select a user first whom you want to send file.</li><li>Once you click on of the partitions a list of folders will be visible.</li><li>If you left click on a folder you will dive into that folder, if you want a select a file you need to right click on the folder from which you want to send the file.</li><li>You can select multiple files to send.</li><li>After you are done with selecting files you can click on \"send\".</li><li>You can watch the progress in Progress List by clicking on \"Progress\".</li></ul><br><p><b>Chatting:</p></b><ul><li>First you need to select a user with whom you want to chat.</li><li>You need to click on \"Chat\" button, in order to chat with someone.</li></ul></html>";
                JLabel label = new JLabel(str);
                JOptionPane.showMessageDialog(ParentWindow.parent, label, "Help", JOptionPane.PLAIN_MESSAGE);
                break;
            case 17:
                String message = "<html><b><p>Version: FileSender 1.0 <br>Developer: Ayush Jaiswal</p></b></html>";
                JLabel about = new JLabel(message);
                JOptionPane.showMessageDialog(ParentWindow.parent, about, "About", JOptionPane.PLAIN_MESSAGE);
                break;
        }
    }
    void saveUserData () {
        FileOutputStream fos = null;
        try {
            UserData data = new UserData(UserAttributes.you.getText(), (ImageIcon) UserAttributes.image.getIcon());
            fos = new FileOutputStream("UserData");
            NewFile obj = new NewFile(fos);
            obj.writeObject(data);
            fos.close();
        } catch (FileNotFoundException ex) {
            Exceptions.exceptions("StartUp\\Actions", ex);
        } catch (IOException ex) {
            Exceptions.exceptions("StartUp\\Actions", ex);
        }
        UserAttributes.window.dispose();
    }
    static void sendMessage () {
        String msg = ParentWindow.text.getText();
        ParentWindow.text.setText("");
        try {
            Receiver.obj.receiveMessage(msg, Network.token);
        } catch (RemoteException ex) {
            Exceptions.exceptions("StartUp\\Actions", ex);
        }
        Message message = new Message(msg, Network.token);
        try {
            ChatWindow.msgpanes.get(Receiver.obj.askToken()).addMsg(message);
        } catch (RemoteException ex) {
            Exceptions.exceptions("StartUp\\Actions", ex);
        }
    }
    static void backButton () {
        int size = DiveInDirectories.history.size();
        DiveInDirectories folder = null;
        if ( (size - 1) >= 0)
            folder = DiveInDirectories.history.remove(size - 1);
        if ( (size - 2) >= 0)
            folder = DiveInDirectories.history.remove(size - 2);
        folder.update();
        
    }
}