package NetworkActivity;
import StartUp.UserData;
import java.rmi.*;
import java.util.*;
public interface FileIntf extends Remote{
    boolean fileOpen (String file) throws RemoteException;
    void closeFile () throws RemoteException;
    void store (byte[] b) throws RemoteException;
    public Token addUser (FileIntf ser) throws RemoteException;
    public TreeMap<Token, FileIntf> getUsers () throws RemoteException;
    UserData getUserData () throws RemoteException;
    void refresh () throws RemoteException;
    void refreshAll () throws RemoteException;
    Token askToken() throws RemoteException;
    void receiveMessage (String msg, Token token) throws RemoteException;
    void addIP (String ip) throws RemoteException;
    void refreshIP (HashSet<String> list) throws RemoteException;
    void leave () throws RemoteException;
    void removeUser (Token token) throws RemoteException;
    boolean connection () throws RemoteException;
}
