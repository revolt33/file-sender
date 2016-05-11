package StartUp;
import java.io.*;
public class ReadFile extends ObjectInputStream{
    public ReadFile(InputStream is) throws IOException
    {
        super(is);
    }
    @Override
    public void readStreamHeader() throws  IOException, StreamCorruptedException
    {
        
    }
}
