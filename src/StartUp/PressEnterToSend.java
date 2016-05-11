package StartUp;

import NetworkActivity.Receiver;
import java.awt.event.*;
import java.rmi.RemoteException;

public class PressEnterToSend extends KeyAdapter {

    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getKeyCode() == 10) {
            if (Receiver.obj != null) {
                try {
                    if (Receiver.obj.connection()) {
                        Actions.sendMessage();
                    }
                } catch (RemoteException ex) {
                    Exceptions.exceptions("StartUp\\PressEnterToSend", ex);
                }
            }
        }
    }
}
