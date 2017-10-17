package remote_client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;

import javax.swing.JOptionPane;

public class Connection {

	public Connection()
	{
		//TODO possibly read from config port and ip valuse
		//hence in the future connection class will be storing
		//port and ip as class variables?
	}

	public void connect(final String hostName, String portname)
	{
		final int port = Integer.parseInt(portname);
		
		try 
		{
			Socket clientSocket = new Socket(hostName, port);
			PrintWriter out =
	                new PrintWriter(clientSocket.getOutputStream(), true);       
			BufferedReader inHost = new BufferedReader(
	                new InputStreamReader(clientSocket.getInputStream()));
			OutputStream outs = clientSocket.getOutputStream();

			//TODO this is subject to change, as of now functionality is that
			//we connect to host, send stuff and disconnect, future versions
			//will support keeping connection for some time to enable more
			//actions
	        out.println(Protocol.N_FILES);
	        out.println(Location.getInstance().getFileList().length);
	        
	        for(int i=0; i<Location.getInstance().getFileList().length; i++)
	        {
	        	int count;
	        	out.println(Protocol.SIZE);
	        	out.println((int) Location.getInstance().getFileList()[i].length());
	        	out.println(Protocol.NAME);
	        	out.println(Location.getInstance().getFileList()[i].getName());
	        	
	        	byte[] bytes = new byte[4096];
	        	InputStream in = new FileInputStream(Location.getInstance().getFileList()[i]);
	            while ((count = in.read(bytes)) > 0)
	            {
	                outs.write(bytes, 0, count);
	            }
	            
	            String response = inHost.readLine();
	            System.out.println(response);
	            in.close();

	        }
	        out.close();
	        outs.close();
	        clientSocket.close();
		}
		catch(UnknownHostException e)
		{
			JOptionPane.showMessageDialog(null,"Don't know about host " + hostName);
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null,"Couldn't get I/O for the connection to " + hostName);
		}
		JOptionPane.showMessageDialog(null,"Conection succesful");
	}

}
