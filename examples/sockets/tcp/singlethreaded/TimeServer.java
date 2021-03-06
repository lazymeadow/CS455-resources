package tcp.singlethreaded;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A single-threaded time server that sends Date objects to clients. 
 * You may need to open port 5005 in the firewall on the host machine.
 * 
 * @author amit
 */

public class TimeServer
{
    private InputStream in;
    private OutputStream out;
    private int port = 5005;
    private ServerSocket s;

    public static void main(String args[])
    {
	TimeServer server = new TimeServer();
	server.serviceClients();
    }

    public TimeServer()
    {
	try {
	    s = new ServerSocket(port);
	} catch (IOException e) {
	    System.err.println(e);
	}
    }

    /**
     * The method that handles the clients, one at a time.
     */
    public void serviceClients()
    {
	Socket sock;

	while (true) {
	    try {
		sock = s.accept();
		in = sock.getInputStream();
		out = sock.getOutputStream();
		// Note that client gets a temporary/transient port on it's side
		// to talk to the server on its well known port
		System.out.println("Received connect from " + sock.getInetAddress().getHostAddress()
			           + ": " + sock.getPort());
		
		ObjectOutputStream oout = new ObjectOutputStream(out);
		oout.writeObject(new java.util.Date());
		oout.flush();
		
		/* Thread.sleep(4000); //4 secs */
		sock.close();
		/* } catch (InterruptedException e) { */
		/* System.err.println(e); */
	    } catch (IOException e) {
		System.err.println(e);
	    }
	}
    }
}
