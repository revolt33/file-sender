package NetworkActivity;
import java.util.*;
import java.io.*;
public class Token implements Comparator,Serializable{
    private int token;
    public Token (int token) {
        this.token = token;
    }
    @Override
    public int compare (Object newToken, Object oldToken) {
        return ( ((Token)oldToken).token-((Token)newToken).token);
    }
    @Override
    public boolean equals (Object ref) {
        if (this.token == ((Token)ref).token)
            return true;
        else
            return false;
    }
    @Override
    public int hashCode () {
        return this.token;
    }
    public int getToken () {
        return this.token;
    }
}
