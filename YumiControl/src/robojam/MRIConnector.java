package robojam;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by dbe on 20.05.2017.
 */
public class MRIConnector {

    private String _hostname;
    private int _port;
    private Socket _socket;

    public MRIConnector(String hostname, int port)
    {
        _hostname=hostname;
        _port=port;
    }

    public void connect()
    {

        try {
            _socket = new Socket("localhost", _port);
            System.out.println("Connected to port "+_port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disconnect()
    {
        try {
            _socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendCommand(IRobotMessage cmd)
    {
        byte[] bytes = ProtocolProcessor.Serialize(cmd);
        if(bytes==null)
        {
            System.out.println("Buffer is null");
            return;
        }
    try {
        OutputStream outputStream = _socket.getOutputStream();

        System.out.println("Writing " + bytes.length + " bytes");
        outputStream.write(bytes);
        outputStream.flush();
        Thread.sleep(5000);
        System.out.println("Command sent");
    }
         catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
