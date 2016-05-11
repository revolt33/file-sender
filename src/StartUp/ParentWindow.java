package StartUp;
import NetworkActivity.Sender;
import javax.swing.border.*;
import javax.swing.tree.*;
import java.awt.BorderLayout;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.net.*;
public class ParentWindow extends JFrame{
    public static ParentWindow parent;
    public static JPanel shareArea, peerList, temp, progress, cardMenu[];
    public static JTextField text;
    public static CardLayout card, card1, card2, card4;
    public static JPanel chats;
    public static JLabel label;
    public ParentWindow (String title) {
        super(title);
        parent = this;
    }
    public void showFrame () {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        centerPoint.x -= 450;
        centerPoint.y -= 250;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(centerPoint);
        this.setLayout(new BorderLayout());
        this.setSize(900, 500);
        JPanel top = new JPanel(new BorderLayout());
        JPanel topChildEast = new JPanel(new GridBagLayout());
        topChildEast.setBackground(new Color(0, 47, 255));
        topChildEast.setOpaque(true);
        card = new CardLayout();
        JPanel topChildWest = new JPanel(card);
        JButton share = new JButton("Share");
        share.addMouseListener(new Actions(13));
        JButton chat = new JButton("Chat");
        chat.addMouseListener(new Actions(15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 5, 0);
        topChildEast.add(share, gbc);
        gbc.gridy = 1;
        topChildEast.add(chat, gbc);
        top.add(topChildEast, BorderLayout.EAST);
        cardMenu = new JPanel[4];
        cardMenu[0] = new JPanel(new GridBagLayout());
        cardMenu[1] = new JPanel(new GridBagLayout());
        cardMenu[2] = new JPanel(new GridBagLayout());
        cardMenu[3] = topChildWest;
        cardMenu[0].setBackground(new Color(0, 47, 255));
        cardMenu[0].setOpaque(true);
        cardMenu[1].setBackground(new Color(0, 47, 255));
        cardMenu[1].setOpaque(true);
        cardMenu[2].setBackground(new Color(0, 47, 255));
        cardMenu[2].setOpaque(true);
        JButton search = new JButton("Join Network");
        search.addActionListener(new Actions(2));
        JButton create = new JButton("Create Network");
        create.addActionListener(new Actions(1));
        gbc.insets = new Insets(0, 25, 0, 25);
        gbc.gridy = 0;
        cardMenu[0].add(search, gbc);
        gbc.gridx = 1;
        cardMenu[0].add(create, gbc);
        topChildWest.add(cardMenu[0], "menu1");
        topChildWest.add(cardMenu[1], "menu2");
        topChildWest.add(cardMenu[2], "menu3");
        top.add(topChildWest, BorderLayout.CENTER);
        addNetworkPanel();
        createNetworkPanel();
        this.add(top, BorderLayout.NORTH);
        JPanel center = new JPanel(new BorderLayout(20, 15));
        center.setBackground(new Color(0, 47, 255));
        center.setOpaque(true);
        JPanel peers = new JPanel(new BorderLayout());
        peerList = new JPanel(new GridBagLayout());
        JScrollPane peerPane = new JScrollPane(peerList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        peerList.setSize(150, 250);
        peerList.setBackground(Color.white);
        peerList.setOpaque(true);
        peers.add(peerPane, BorderLayout.CENTER);
        JPanel peerTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 18));
        peerTitle.setBackground(new Color(0, 47, 255));
        peerTitle.setOpaque(true);
        JLabel peerText = new JLabel("Peers");
        peerText.setForeground(Color.yellow);
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        peerText.setFont(font);
        gbc.gridx = 0;
        peerTitle.add(peerText);
        peers.add(peerTitle, BorderLayout.NORTH);
        center.add(peers, BorderLayout.WEST);
        card1 = new CardLayout();
        JPanel tasks = new JPanel(card1);
        JPanel sharePane = new JPanel(new BorderLayout());
        card2 = new CardLayout();
        JPanel shareAppContainer = new JPanel(card2);
        JPanel shareOptions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));
        JLabel send = new JLabel("Send");
        send.setForeground(Color.yellow);
        send.setFont(font);
        send.setCursor(new Cursor(Cursor.HAND_CURSOR));
        send.addMouseListener(new Sender());
        JLabel back = new JLabel("Back");
        back.addMouseListener(new Actions(14));
        back.setForeground(Color.yellow);
        back.setFont(font);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel progress = new JLabel("Progress");
        progress.setForeground(Color.yellow);
        progress.setFont(font);
        progress.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel folder = new JLabel("Folders");
        folder.setForeground(Color.yellow);
        folder.setFont(font);
        folder.setCursor(new Cursor(Cursor.HAND_CURSOR));
        folder.addMouseListener(new Actions(13));
        progress.addMouseListener(new Actions(12));
        shareOptions.add(send);
        shareOptions.add(back);
        shareOptions.add(progress);
        shareOptions.add(folder);
        sharePane.add(shareOptions, BorderLayout.NORTH);
        JPanel tree = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 20));
        JScrollPane scrollTree = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tree.setBackground(Color.WHITE);
        tree.setOpaque(true);
        JPanel folders = new JPanel(new GridBagLayout());
        shareArea = folders;
        JPanel folderLay = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JScrollPane folderPane = new JScrollPane(folderLay, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        folderLay.add(folders);
        folderPane.setOpaque(true);
        folderLay.setBackground(Color.WHITE);
        folderPane.setOpaque(true);
        LineBorder border = new LineBorder(Color.yellow, 3);
        folderPane.setBorder(border);
        JSplitPane shareApp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, scrollTree, folderPane);
        shareApp.setDividerSize(5);
        shareApp.setBorder(border);
        shareApp.setDividerLocation(150);
        scrollTree.setBorder(border);
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Computer");
        File[] roots = File.listRoots();
        for (File root : roots) {
            DefaultMutableTreeNode drives = new DefaultMutableTreeNode(root.toString().substring(0, 1));
            File[] dir = root.listFiles();
            try {
                for (File subDir : dir) {
                    if (!subDir.isHidden()) {
                        if (subDir.isDirectory()) {
                            DefaultMutableTreeNode subD = new DefaultMutableTreeNode(subDir.getName());
                            drives.add(subD);
                        }
                    }
                }
            } catch (NullPointerException ex) {
                Exceptions.exceptions("StartUp\\ParentWindow", ex);
            }
            rootNode.add(drives);
        }
        JTree showTree = new JTree(rootNode);
        showTree.addMouseListener(new UpdateDirectory(showTree, folders));
        tree.add(showTree);
        shareAppContainer.add(shareApp, "folders");
        sharePane.add(shareAppContainer, BorderLayout.CENTER);
        addChat(shareAppContainer);
        tasks.add(sharePane, "share");
        addProgress(shareAppContainer);
        center.add(tasks, BorderLayout.CENTER);
        this.add(center, BorderLayout.CENTER);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        bottom.setBackground(new Color(0, 200, 0));
        bottom.setOpaque(true);
        JLabel you = new JLabel("You");
        you.setCursor(new Cursor(Cursor.HAND_CURSOR));
        you.addMouseListener(new Actions(11));
        you.setFont(font);
        you.setForeground(Color.red);
        JLabel help = new JLabel("Help");
        help.setFont(font);
        help.setCursor(new Cursor(Cursor.HAND_CURSOR));
        help.setForeground(Color.red);
        help.addMouseListener(new Actions(16));
        JLabel about = new JLabel("About");
        about.setFont(font);
        about.setCursor(new Cursor(Cursor.HAND_CURSOR));
        about.setForeground(Color.red);
        about.addMouseListener(new Actions(17));
        bottom.add(you);
        bottom.add(help);
        temp = shareAppContainer;
        bottom.add(about);
        this.add(bottom, BorderLayout.SOUTH);
        this.addWindowListener(new Termination());
        this.setVisible(true);
    }

    void addPeers ( JPanel parent ) {
        
    }
    void addProgress(JPanel panel) {
        progress = new JPanel(new GridBagLayout());
        JScrollPane pane = new JScrollPane(progress, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        progress.setBackground(Color.WHITE);
        progress.setOpaque(true);
        panel.add(pane, "progress");
    }

    void addChat(JPanel panel) {
        JPanel chatPanel = new JPanel(new BorderLayout());
        JPanel chatOptions = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        CardLayout card3 = new CardLayout();
        JPanel chatCenter = new JPanel(card3);
        JPanel chatArea = new JPanel(new BorderLayout());
        chatArea.setBackground(Color.WHITE);
        chatArea.setOpaque(true);
        text = new JTextField();
        text.addKeyListener(new PressEnterToSend());
        JButton send = new JButton("Send");
        send.addMouseListener(new Actions(6));
        JPanel textArea = new JPanel(new BorderLayout());
        textArea.add(text, BorderLayout.CENTER);
        textArea.add(send, BorderLayout.EAST);
        card4 = new CardLayout();
        chats = new JPanel(card4);
        chats.setBackground(Color.WHITE);
        chats.setOpaque(true);
        chatArea.add(chats, BorderLayout.CENTER);
        chatArea.add(textArea, BorderLayout.SOUTH);
        chatCenter.add(chatArea, "chat");
        chatPanel.add(chatCenter, BorderLayout.CENTER);
        chatPanel.add(chatOptions, BorderLayout.NORTH);
        panel.add(chatPanel, "chat");

    }

    void addNetworkPanel() {
        label = new JLabel("Status: Disconnected");
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        label.setForeground(Color.yellow);
        label.setFont(font);
        JButton leave = new JButton("Leave");
        leave.addActionListener(new Actions(5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 25, 0, 25);
        gbc.gridx = 0;
        gbc.gridy = 0;
        cardMenu[1].add(label, gbc);
        gbc.gridx = 1;
        cardMenu[1].add(leave, gbc);
        cardMenu[1].validate();
    }

    void createNetworkPanel() {
        JLabel label = null;
        try {
            label = new JLabel("Your Local IP: " + Inet4Address.getLocalHost().getHostAddress());
        } catch (UnknownHostException ex) {
            Exceptions.exceptions("StartUp\\ParentWindow", ex);
        }
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        label.setForeground(Color.yellow);
        label.setFont(font);
        JButton leave = new JButton("Close");
        leave.addActionListener(new Actions(3));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 25, 0, 25);
        gbc.gridx = 0;
        gbc.gridy = 0;
        cardMenu[2].add(label, gbc);
        gbc.gridx = 1;
        cardMenu[2].add(leave, gbc);
        cardMenu[2].validate();
    }
}