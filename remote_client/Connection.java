package remote_client;

import java.io.IOException;
import java.net.*;

public class Connection {

	public static String Connect(final String hostName, String portname)
	{
		final int port = Integer.parseInt(portname);
		
		try 
		{
			Socket clientSocket = new Socket(hostName, port);
		}
		catch(UnknownHostException e)
		{
			return "Don't know about host " + hostName;
		}
		catch(IOException e)
		{
			return "Couldn't get I/O for the connection to " +
					hostName;
		}
		return "Connection Succesful";
	}

}
