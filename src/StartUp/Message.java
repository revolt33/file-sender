package StartUp;

import NetworkActivity.Token;

public class Message {
    private String msg;
    private Token token;
    public Message (String msg, Token token) {
        this.msg = msg;
        this.token = token;
    }
    public String getMessage () {
        return msg;
    }
    public Token getToken () {
        return token;
    }
}
