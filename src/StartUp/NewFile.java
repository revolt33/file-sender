package StartUp;
import java.io.*;
public class NewFile extends ObjectOutputStream{
    public NewFile(OutputStream o) throws IOException
    {
        super(o);
    }
    @Override
    public void writeStreamHeader()
    {
        
    }
}
