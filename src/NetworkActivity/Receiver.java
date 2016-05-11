package NetworkActivity;
import java.awt.event.*;
import javax.swing.*;
import StartUp.*;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.rmi.RemoteException;
public class Receiver extends MouseAdapter{
    int no;
    JPanel panel;
    static JPanel prevPanel;
    public static FileIntf obj;
    public static Token token;
    FileIntf current;

    public Receiver(int no, JPanel panel, FileIntf current) {
        this.no = no;
        this.panel = panel;
        this.current = current;
    }
    
    @Override
    public void mouseClicked (MouseEvent me) {
        if (prevPanel != null)
            prevPanel.setOpaque(false);
        if (prevPanel == panel) {
            panel.setOpaque(false);
            prevPanel = null;
            obj = null;
        } else {
            panel.setOpaque(true);
            prevPanel = panel;
            obj = current;
            try {
                token = obj.askToken();
                if (ChatWindow.msgpanes.containsKey(token) == false) {
                    String str = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
                    ChatWindow.msgpanes.put(token, new ChatWindow(Receiver.obj.askToken()));
                    GridBagConstraints gbc = ChatWindow.msgpanes.get(token).gbc;
                    JLabel label = new JLabel(str);
                    label.setForeground(Color.WHITE);
                    ChatWindow.msgpanes.get(token).messages.add(label, gbc);
                    gbc.gridy++;
                    ParentWindow.card4.show(ParentWindow.chats, "" + current.askToken().getToken());
                }
            } catch (RemoteException ex) {
                Exceptions.exceptions("NetworkActivity\\Receiver", ex);
            }
        }
        affect();
    }

    @Override
    public void mouseEntered (MouseEvent me) {
        panel.setOpaque(true);
        affect();
    }
    @Override
    public void mouseExited (MouseEvent me) {
        if (panel != prevPanel)
            panel.setOpaque(false);
        affect();
    }
    void affect () {
        ParentWindow.peerList.updateUI();
    } 
}