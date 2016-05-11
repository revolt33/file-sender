package NetworkActivity;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.util.*;
import StartUp.*;
import java.awt.*;
import javax.swing.*;
public class Network {
    public static FileIntf obj;
    static String ip;
    public static Token token;
    static boolean sig = false;
    static TreeMap<Token, FileIntf> list = new TreeMap<>(new Token(0));
    public static HashSet<String> ips;
    
    public static void create () {
        try {
            if ( sig == false ) {
                LocateRegistry.createRegistry(9000);
                obj = new ServiceProvider();
                Naming.bind("rmi://localhost:9000/files", obj);
                sig = true;
            } else {
                Naming.rebind("rmi://localhost:9000/files", obj);
            }
            token = new Token(1);
            list.put(token, obj);
            ParentWindow.card.show(ParentWindow.cardMenu[3], "menu3");
        } catch (RemoteException | AlreadyBoundException | MalformedURLException ex) {
            Exceptions.exceptions("NetworkActivity\\Network", ex);
        }
    }
    public static void join (){
        JComboBox com = new JComboBox();
        com.setEditable(true);
        if ( Network.ips != null ) {
            Iterator<String> it = ips.iterator();
            while ( it.hasNext() ) {
                com.addItem(it.next());
            }
        }
        JDialog jd = new JDialog(ParentWindow.parent, "Join Network", true);
        jd.setLayout(new GridBagLayout());
        jd.setSize(250, 150);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point center = ge.getCenterPoint();
        center.x -= 125;
        center.y -= 75;
        jd.setLocation(center);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel label = new JLabel("Enter Server's IP:");
        jd.add(label, gbc);
        gbc.gridx = 1;
        jd.add(com, gbc);
        JButton ok = new JButton("Join");
        JButton cancel = new JButton("Cancel");
        ok.addActionListener(new JoinNetwork(com, 1, jd));
        cancel.addActionListener(new JoinNetwork(com, 2, jd));
        gbc.gridy = 1;
        gbc.gridx = 0;
        jd.add(ok, gbc);
        gbc.gridx = 1;
        jd.add(cancel, gbc);
        jd.validate();
        jd.setVisible(true);
    }
    public static void close () {
        try {
            TreeMap<Token, FileIntf> localList = new TreeMap<>(token);
            localList.putAll(list);
            localList.remove(token);
            Set peers = localList.entrySet();
            Iterator it = peers.iterator();
            while (it.hasNext()) {
                Map.Entry<Token, FileIntf> entry = (Map.Entry<Token, FileIntf>) it.next();
                FileIntf serviceProvider = entry.getValue();
                serviceProvider.leave();
            }
            Naming.unbind("rmi://localhost:9000/files");
            ParentWindow.card.show(ParentWindow.cardMenu[3], "menu1");
            ParentWindow.peerList.removeAll();
            ParentWindow.peerList.updateUI();
            Receiver.obj = null;
            Receiver.prevPanel = null;
            Receiver.token = null;
        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
            Exceptions.exceptions("NetworkActivity\\Network", ex);
        }
    }

    public static void refresh() {
        try {
            ParentWindow.peerList.removeAll();
            TreeMap<Token, FileIntf> localList;
            localList = new TreeMap<>(token);
            if (token.equals(new Token(1))){
                localList.putAll(list);
            } else {
                localList.putAll(obj.getUsers());
            }
            localList.remove(token);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.insets = new Insets(5, 3, 5, 3);
            Set peers = localList.entrySet();
            Iterator it = peers.iterator();
            int i = 0;
            boolean check = true;
            while (it.hasNext()) {
                Map.Entry<Token, FileIntf> entry = (Map.Entry) it.next();
                FileIntf serviceProvider = entry.getValue();
                JPanel peer = new JPanel(new BorderLayout());
                peer.setForeground(Color.yellow);
                JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JLabel img = new JLabel(serviceProvider.getUserData().profile);
                JLabel name = new JLabel(serviceProvider.getUserData().name);
                bottom.add(name);
                peer.add(img, BorderLayout.CENTER);
                peer.add(bottom, BorderLayout.SOUTH);
                gbc.gridy = i;
                peer.addMouseListener(new Receiver(i, peer, serviceProvider));
                i++;
                if (Receiver.obj != null) {
                    if (serviceProvider.askToken().equals((Receiver.token))) {
                        peer.setOpaque(true);
                        Receiver.prevPanel = peer;
                        check = false;
                    }
                }
                
                ParentWindow.peerList.add(peer, gbc);

            }
            if (check) {
                Receiver.obj = null;
                if (Receiver.prevPanel != null) {
                    Receiver.prevPanel.setOpaque(false);
                }
                Receiver.prevPanel = null;
                Receiver.token = null;
            }
            ParentWindow.peerList.updateUI();
        } catch (RemoteException ex) {
            Exceptions.exceptions("NetworkActivity\\Network", ex);
        }
        
    }

    public static void leave() {
        if ( obj != null ) {
            try {
                obj.removeUser(token);
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(ParentWindow.parent, "This Network seems unreachable!");
            }
        } else {
            JOptionPane.showMessageDialog(ParentWindow.parent, "This Network is already closed!");
        }
        
        ParentWindow.card.show(ParentWindow.cardMenu[3], "menu1");
        ParentWindow.peerList.removeAll();
        ParentWindow.peerList.updateUI();
        Receiver.obj = null;
        Receiver.prevPanel = null;
        Receiver.token = null;
    }
}