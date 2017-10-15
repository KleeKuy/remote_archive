package remote_host;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Connection {

	public Connection(String portstring)
	{
		final int port = Integer.parseInt(portstring);
		try 
		{
			ServerSocket serverSocket =
					new ServerSocket (port);
			Socket clientSocket = serverSocket.accept();   
			
			BufferedReader in = new BufferedReader(
	                new InputStreamReader(clientSocket.getInputStream()));
			
			JOptionPane.showMessageDialog(null,in.readLine());
		}
		catch(IOException e)
		{
			System.out.println("Exception caught when trying to listen on port "
	                + port + " or listening for a connection");
		}
		
	}
}
