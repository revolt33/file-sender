package StartUp;

import javax.swing.*;
import NetworkActivity.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.*;
public class ChatWindow extends JPanel{
    public static TreeMap<Token, ChatWindow> msgpanes = new TreeMap<>(new Token(0));
    Token token;
    int msgs;
    public JPanel messages;
    JScrollPane scPane;
    public GridBagConstraints gbc;
    public ChatWindow(Token token) {
        this.token = token;
        this.setLayout(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel name = null;
        try {
            name = new JLabel(Network.obj.getUsers().get(token).getUserData().name);
        } catch (RemoteException ex) {
            Exceptions.exceptions("StartUp\\ChatWindow", ex);
        }
        name.setForeground(Color.blue);
        name.setFont (new Font(Font.SERIF, Font.BOLD, 20));
        top.add(name);
        messages = new JPanel(new GridBagLayout());
        scPane = new JScrollPane(messages, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        messages.setBackground(Color.white);
        messages.setOpaque(true);
        this.add(top, BorderLayout.NORTH);
        this.add(scPane, BorderLayout.CENTER);
        ParentWindow.chats.add(this, ""+token.getToken());
        msgpanes.put(token, this);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 5, 0);
    }
    public void addMsg (Message message) {
        JPanel msgPane;
        String text = message.getMessage();
        int len = text.length(), div;
        div = len/50;
        if ( div > 0) {
            String dummy, edited = "<html><p>";
            int i;
            for ( i = 0; i < div; i++) {
                dummy = text.substring(i*50, (i+1)*50);
                edited += dummy + "<br>";
            }
            dummy = text.substring(i*50, text.length());
            edited += dummy + "</p></html>";
            text = edited;
        }
        JLabel msg = new JLabel(text);
        if ( Network.token.equals(message.getToken())) {
            msgPane = new MsgBar('r');
            gbc.anchor = GridBagConstraints.LINE_END;
        } else {
            msgPane = new MsgBar('l');
            gbc.anchor = GridBagConstraints.LINE_START;
        }
        msgPane.add(msg);
        msgPane.setOpaque(true);
        messages.add(msgPane, gbc);
        messages.updateUI();
        gbc.gridy++;
        msgs++;
        this.invalidate();
        this.validate();
        this.updateUI();
        scPane.getVerticalScrollBar().setValue(scPane.getVerticalScrollBar().getMaximum());
        
    }
}